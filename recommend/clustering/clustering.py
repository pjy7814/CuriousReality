from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.cluster import DBSCAN
from pymongo import MongoClient
import numpy as np
import pandas as pd
import json


### Todo: 파일 경로명 서버에 맞게, 날짜에 맞게 수정 ###
with open('C:/Users/SSAFY/heuristic/cluster/articles/20230902.json', 'r', encoding='utf-8') as file:
    data = json.load(file)

df = pd.DataFrame(data, columns=['title', 'category1', 'company', 'original_url', 'thumbnail', 'article'])
text = [" ".join(article["preprocessed"]) for article in data]
total_article_cnt = len(text)
print("기사 총 개수: ", total_article_cnt)

# 각 카테고리(정치, 경제...)별로 데이터 분리
categories = df['category1'].unique()
recommend_df = pd.DataFrame()
total_recommend_list = []
for category in categories:
    category_df = df[df['category1'] == category]
    category_text = [" ".join(article["preprocessed"]) for article in data if article['category1'] == category]
    
    # TF-IDF Vector화
    tfidf_vectorizer = TfidfVectorizer(min_df=5, max_features=30)
    tfidf_vectorizer.fit(category_text)
    vector = tfidf_vectorizer.transform(category_text).toarray()
    vector = np.array(vector)

    # DBSCAN Clustering
    # eps가 낮으면 군집으로 판단하는 범위가 좁아진다.
    # min_samples 값 이하의 군집은 노이즈로 판단한다.
    model = DBSCAN(eps=0.1, min_samples=5, metric="cosine")
    cluster = model.fit_predict(vector)
    category_df['cluster'] = cluster
    print(f'카테고리: {category}, 군집 개수: {cluster.max()}')

    # 각 카테고리별 대표 기사 출력
    # cluster_scale = defaultdict(list)
    cluster_scale = list()
    for cluster_num in set(cluster):
        # -1,0은 노이즈 판별이 났거나 클러스터링이 안된 경우
        if cluster_num == -1 or cluster_num == 0:
            continue
        else:
            print(f"cluster num : {cluster_num}")
            temp_df = category_df[category_df['cluster'] == cluster_num]  # cluster num 별로 조회
            scale = len(temp_df)
            cluster_scale.append((scale, cluster_num))
            print(f"cluster scale : {scale}")
            for title in temp_df['title']:
                print(title)  # 제목 출력
            print()
    
    # 각 카테고리의 최대 클러스터에 속하는 기사들을 모아 DB에 저장
    cluster_scale.sort(reverse=True)
    max_cluster_idx = cluster_scale[0][1] # 에러 처리 필요
    recommend_df = pd.concat([recommend_df, category_df[category_df['cluster'] == max_cluster_idx]], axis=0)
    total_recommend_list.extend(category_df[category_df['cluster'] == max_cluster_idx].to_dict(orient='records'))
print(recommend_df)

# 데이터 리스트를 하나의 도큐먼트로 묶기
aggregated_document = {
    "article_list": total_recommend_list,
    "created_at": "1988-09-21T01:37:16.176Z"  # 생성일자 설정
}

# Mongo DB에 저장
client = MongoClient("mongodb://root:reality@j9a303.p.ssafy.io:27017/?authMechanism=DEFAULT")
db = client['curious']
collection_name = 'recommend_pool_cluster'  # name of your collection
collection = db[collection_name]
collection.insert_one(aggregated_document)
client.close()