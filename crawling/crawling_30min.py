from kafka import KafkaProducer
from bs4 import BeautifulSoup
from itertools import count
import requests, re
import random
import datetime,time
import json
import konlpy.tag
from collections import OrderedDict

# base_dir = "/home/ubuntu/crawling"
base_dir = "C:/Users/SSAFY/Desktop"
hrd = {"User-Agent":"Mozilla/5.0", "referer":"http://naver.com"}
# producer = KafkaProducer(acks = 1, compression_type = None, bootstrap_servers = ['master:9092'])
producer = KafkaProducer(acks = 1, compression_type = None, bootstrap_servers = ['43.201.51.98:9092'])

def preprocess(article):
    filtered_content = article.replace('.', '').replace(',', '').replace("'", "").replace('·', ' ').replace('=','').replace('\n', '')
    Okt = konlpy.tag.Okt()
    Okt_morphs = Okt.pos(filtered_content)  # 튜플반환

    Noun_words = []
    for word, pos in Okt_morphs:
        if pos == 'Noun':
            Noun_words.append(word)

    return Noun_words

# 뉴스 본문 크롤링 함수
def news(news_url):
    global cdic, cid, id, files
    response = requests.get(news_url, headers=hrd)
    if response.status_code == 200 :
        soup = BeautifulSoup(response.text, 'html.parser')

        # [1] 뉴스 정보 가져오기 - 제목, 작성 일시, 원본 링크, 본문
        category1 = cdic[cid][0]
        category2 = cdic[cid][1][id]
        title = ""
        thumbnail = ""
        created_at = ""
        original_url = ""
        company = ""
        article = ""

        # [2] 예외 처리
        if soup.find("h2", {"class":"media_end_head_headline"}):
            title = soup.select_one("#title_area > span").get_text()
        if soup.select_one("#ct > div.media_end_head.go_trans > div.media_end_head_info.nv_notrans > div.media_end_head_info_datestamp"):
            created_at = soup.select_one("#ct > div.media_end_head.go_trans > div.media_end_head_info.nv_notrans > div.media_end_head_info_datestamp > div > span").get("data-date-time")
        if soup.find("a", {"class":"media_end_head_origin_link"}) :
            original_url = soup.select_one("#ct > div.media_end_head.go_trans > div.media_end_head_info.nv_notrans > div.media_end_head_info_datestamp > a").get("href")
        if soup.find("div",{"class":"media_end_head_top"}):
            company = soup.select_one("#ct > div.media_end_head.go_trans > div.media_end_head_top > a > img.media_end_head_top_logo_img.light_type").get("title")
        if soup.find("article", {"id":"dic_area"}):
            article = soup.select_one("#dic_area").get_text()
            if soup.find("img", {"id":"img1"}):
                thumbnail = soup.select_one("#img1").get("data-src")

        # [3] 전처리
        # preprocessed = preprocess(article)

        # [4] 파일 내보내기
        file_data = OrderedDict()
        file_data['category1'] = category1
        file_data['category2'] = category2
        file_data['title'] = title
        file_data['created_at'] = created_at
        file_data['original_url'] = original_url
        file_data['thumbnail'] = thumbnail
        file_data['company'] = company
        file_data['article'] = article
        # file_data['preprocessed'] = preprocessed

        files.append(file_data)

    else: 
        print(response.status_code)
    
# 뉴스 목록 크롤링 함수
def news_list(id, cid, today):
    global latest_data

    # [1] 마지막 페이지 구하기
    last_page = 999
    check_url = f"https://news.naver.com/main/list.naver?mode=LS2D&sid2={id}&sid1={cid}&mid=shm&date={today}&page={last_page}"
    check_response = requests.get(check_url, headers=hrd)
    if check_response.status_code == 200 :
        soup = BeautifulSoup(check_response.text, 'html.parser')
        pages = soup.find("div",{"class":"paging"})
        last_page = int(pages.find("strong").get_text())

    else: 
        print(check_response.status_code)

    news_urls = []
    # [2] 뉴스 목록에 있는 url, 언론사 구하기
    for page in range(1,last_page+1):
        url = f"https://news.naver.com/main/list.naver?mode=LS2D&sid2={id}&sid1={cid}&mid=shm&date={today}&page={page}"
        response = requests.get(url, headers=hrd)

        if response.status_code == 200 :
            soup = BeautifulSoup(response.text, 'html.parser')
            uls = soup.find_all("ul",{"class": re.compile("type06*")})

            for ul in uls :
                links = ul.select("li > dl > dt:nth-child(1)> a")

                for link in links : 
                    news_url = link.get("href")

                    # 1) 여기까지 스크랩했어요 저장
                    # (1) 가장 최근에 크롤링한 기사까지 도달한 경우
                    if link.get("href") == latest_data[str(id)]['latest'] : 
                        latest_data[str(id)]['latest'] = news_urls[0]
                        with open(f"{base_dir}/latest.json","w", encoding="UTF8") as update :
                            json.dump(latest_data, update,default=str, indent="\t")
                        return
                    
                    # (2) 최근 크롤링한 기사가 없는 경우
                    if latest_data[str(id)]['latest'] == "":
                        latest_data[str(id)]['latest'] = news_url
                        with open(f"{base_dir}/latest.json","w", encoding="UTF8") as update :
                            json.dump(latest_data, update,default=str, indent="\t")
                    
                    # 2) 뉴스 본문 크롤링 함수 호출
                    news(news_url)
                    news_urls.append(news_url)
                    time.sleep(random.uniform(0.07,1.5))

        else: 
            print(response.status_code)

# 필요한 모든 대분류별 중분류 가져오기
cdic = {}
with open(f"{base_dir}/config.json","r", encoding="UTF8") as file :
    data = json.load(file)
    categories = data['categories']
    for c in range(len(categories)):
        cid = categories[c]['cid']
        cname = categories[c]['name']
        dic = {}
        for element in categories[c]['elements']:
            dic[element['id']] = element['name']
        cdic[cid] = [cname, dic]

# url 에는 id만 필요하므로 id 값만 따로 저장
cids = list(cdic.keys())    

# 어디까지 크롤링했는지 기록하는 파일 불러오기
with open(f"{base_dir}/latest.json","r", encoding="UTF8") as latest :
    latest_data = json.load(latest)
    
# 현재 시각 구하기
today = str(datetime.datetime.now())
date,krtime = today.split(" ")
year,month,day = date.split("-")
hour,minute,second = krtime.split(":")
today = year + month + day
now = year + month + day + hour+minute
print(now)

# 분류 별 현재 기사 크롤링
started_at = datetime.datetime.now()

print("시작 시각 : ", started_at)
files = []
for i in range(len(cids)):
    cid = cids[i]
    ids = list(cdic[cid][1].keys())
    for j in range(len(ids)):
        id = ids[j]
        # 뉴스 목록 크롤링 함수 호출
        news_list(id, cid, today)
        print(cdic[cid][0], ">", cdic[cid][1][id], "완료 시각 : ", datetime.datetime.now())

ended_at = datetime.datetime.now()
print("종료 시각 : ", ended_at)

# [5] json 파일로 변환

json_data = json.dumps(files,default=str, ensure_ascii=False, indent="\t")
path = f'{base_dir}/{now}.json'
with open(path,'w',encoding='utf-8') as make_file:
    make_file.write(json_data)
producer.send("Reality", value = path.encode('utf-8'))
producer.flush()