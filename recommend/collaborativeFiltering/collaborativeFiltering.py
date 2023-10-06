import pymysql
import pandas as pd
import numpy as np
from sklearn.metrics.pairwise import cosine_similarity
from pymongo import MongoClient
from datetime import datetime

# user_preferred_category = 0  # 원하는 카테고리 ID로 변경
# user_preferred_company = 2  # 원하는 언론사 ID로 변경

# Mongo DB에 저장
client = MongoClient("mongodb://root:reality@j9a303.p.ssafy.io:27017/?authMechanism=DEFAULT")
db = client['curious']
collection_name = 'recommend_collaborative_filtering'  # name of your collection
collection = db[collection_name]

# MySQL DB 연결 설정
db_config = {
    'host': 'j9a303.p.ssafy.io',
    'port': 3306,
    'user': 'root',
    'password': 'reality',
    'db': 'curious',
    'charset': 'utf8mb4',
    'cursorclass': pymysql.cursors.DictCursor
}

# MySQL DB 연결
conn = pymysql.connect(**db_config)


# 유저가 본 기사 테이블에서 데이터 가져오기
cursor = conn.cursor()
query = "SELECT member_id, articleId, company, category1 FROM history"
cursor.execute(query)
result = cursor.fetchall()
df = pd.DataFrame(result)
print(df)

# 데이터 전처리: 피벗 테이블 생성 (유저-기사 매트릭스)
user_article_matrix = pd.pivot_table(df, index='member_id', columns='articleId', values='category1', aggfunc='first', fill_value=0)

# 협업 필터링을 위한 기사 유사도 계산
article_similarity = cosine_similarity(user_article_matrix.T)

# 추천 기사를 위한 함수 정의
def recommend_articles(member_id, num_recommendations=5):    
    user_history = user_article_matrix.loc[member_id]
    article_scores = article_similarity.dot(user_history)
    
    # 이미 본 기사는 추천 대상에서 제외
    user_history_indices = user_history.index.get_indexer_for(user_history[user_history == 1].index)
    article_scores[user_history_indices] = -1
    
    # 가장 높은 점수를 가진 기사 순으로 정렬
    recommended_article_indices = np.argsort(article_scores)[::-1]
    
    # 사용자의 선호 언론사 및 카테고리를 고려하여 필터링
    recommended_articles = []
    for article_index in recommended_article_indices:
        article_id = user_article_matrix.columns[article_index]
        print(article_id)
        article_info = df[df['articleId'] == article_id].iloc[0]
        print(article_info)

        # if article_info['company'] == user_preferred_company or article_info['category1'] == user_preferred_category:
        recommended_articles.append(article_id)

        if len(recommended_articles) == num_recommendations:
            break
    
    return recommended_articles



# 유저들 가져오기
cursor = conn.cursor()
query = "SELECT member_id FROM member"
cursor.execute(query)
members = cursor.fetchall()

for member in members:
    member_id = member['member_id']
    print(member_id)
    if not member_id in user_article_matrix.index:
        continue

    recommends = {}
    recommends["member_id"] = member_id
    recommends["created_at"] = datetime.now()
    recommends["recommend_articles"] = recommend_articles(member_id)

    print("추천 기사: ", recommends)
    collection.insert_one(recommends)






client.close()

# MySQL 연결 종료
conn.close()
