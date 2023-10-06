# 산출물

# **1. 사용한 JVM, 웹서버, WAS 제품 등의 종류와 설정 값, 버전(IDE버전 포함)**

### **BE**

- JAVA 11.0.20.1
- Spring Boot 2.7.15

### **FE**

- nodeJS 16.20.0
- npm 8.19.4
- Visual Studio Code 1.82.2

### **Infra**

- Spark 3.4.1
- Kafka 2.13-3.2.1
- NginX 1.18.0

### **Database**

- MongoDB 4.4
- MySQL 8.1.0-1.el8
- MongoDB Compass 1.39.4
- MySQL Workbench 8.0.21

# **2. 빌드 시 사용되는 환경 변수 등의 내용 상세 기재**

### NGINX

`/etc/nginx/nginx.conf` 에 파일 수정

```bash
user www-data;
worker_processes auto;
pid /run/nginx.pid;
include /etc/nginx/modules-enabled/*.conf;

events {
        worker_connections 768;
        # multi_accept on;
}

http {

        ##
        # Basic Settings
        ##

        sendfile on;
        tcp_nopush on;
        tcp_nodelay on;
        keepalive_timeout 65;
        types_hash_max_size 2048;
        # server_tokens off;

        # server_names_hash_bucket_size 64;
        # server_name_in_redirect off;

        include /etc/nginx/mime.types;
        default_type application/octet-stream;

        ##
        # SSL Settings
        ##

        ssl_protocols TLSv1 TLSv1.1 TLSv1.2 TLSv1.3; # Dropping SSLv3, ref: POODLE
        ssl_prefer_server_ciphers on;

        ##
        # Logging Settings
        ##

        access_log /var/log/nginx/access.log;
        error_log /var/log/nginx/error.log;

        ##
        # Gzip Settings
        ##

        gzip on;

        # gzip_vary on;
        # gzip_proxied any;
        # gzip_comp_level 6;
        # gzip_buffers 16 8k;
        # gzip_http_version 1.1;
        # gzip_types text/plain text/css application/json application/javascript text/xml application/xml application/xml+rss text/javascript;

        ##
        # Virtual Host Configs
        ##

        include /etc/nginx/conf.d/*.conf;
        include /etc/nginx/sites-enabled/*;

        server {
                server_name "curious303.kro.kr";
                listen 80;

                location / {
                        try_files $uri $uri/ /index.html;
                        root /home/ubuntu/front/dist;
                        index index.html index.htm index.nginx-debian.html;
}
                location /api {
                        proxy_set_header HOST $host;
                        proxy_pass http://127.0.0.1:8081;
                        proxy_redirect off;
                }
        }
}

```

### SPARK

`S09P22A303/spark/CuriousAboutReality/src/main/resources/application-secret.properties`에 파일 작성

```bash
spring.kafka.bootstrap-servers=[카프카 서버 host]

# mongoDB
spring.data.mongodb.uri=[몽고디비 uri]
spring.data.mongodb.port=[몽고디비 port]
spring.data.mongodb.database=[데이터베이스 이름]
spring.data.mongodb.collection.tfidf=category_tfidf
spring.data.mongodb.collection.article=article_info
spring.data.mongodb.authentication-database=admin
spring.data.mongodb.username=[id]
spring.data.mongodb.password=[pw]

# bareun
bareun.key=[bareun키]
```

### **BE**

`S09P22A303/be/src/main/resources/application-secret.properties`에 파일 작성 (git에 올라가지 않은 설정 파일)

```bash
spring.kafka.bootstrap-servers=[카프카 서버 host]

# DB
useSSL=false&useUnicode=true&characterEncoding=utf8&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.url=[url]
spring.datasource.username=[id]
spring.datasource.password=[pw]

# mongoDB
spring.data.mongodb.uri=[몽고디비 uri]
spring.data.mongodb.port=[몽고디비 port]
spring.data.mongodb.database=[데이터베이스]
spring.data.mongodb.collection.tfidf=category_tfidf
spring.data.mongodb.collection.article=article_info
spring.data.mongodb.authentication-database=admin
spring.data.mongodb.username=[id]
spring.data.mongodb.password=pw

# session redis
spring.session.store-type=redis
spring.redis.host=[redis host]
spring.redis.password=[pw]
spring.redis.port=[port]

# jwt
secret = [jwt secret]
access-token-expire = 1800000
refresh-token-expire = 3600000
expire = 3600000

# google
spring.mail.username= [googleEmail]
spring.mail.password= [googlePW]
```

### **FE**

`S09P22A303/fe/.env` 에 파일 작성

```bash
VUE_APP_API_URL = http://curious303.kro.kr/api/v1
```

# **3. DB 덤프 파일 최신본**

파일첨부

# **4. 시연 시나리오**

### **Main Page**

- 워드클라우드 키워드 클릭
- 상단 메뉴에서 하단 카테고리 클릭

### **상세 카테고리**

- 워드클라우드 키워드 클릭
- 기사 제목 클릭하여 기사로 접속

### **로그인/회원가입**

- 계정이 없다면 회원가입 진행
- 로그인 수행

### **추천뉴스**

- 상단 메뉴바에서 추천뉴스를 확인
- 기사 읽기를 통해 기사로 접속
- 스크랩 진행

### **스크랩**

- 기사 옆에 있는 북마크를 클릭하여 북마크 진행

### **마이페이지**

- 상단 메뉴바에서 마이페이지에 접속
- 마이페이지에서 선호도 확인
- 북마크한 기사 리스트 확인
