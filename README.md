# suggest project documantation

google cloud platform(GCP) setting

1. cloud.google.com  회원 가입
2. project 생성
    1. search-gokgok -> 검색 엔진 용
    2. suggest-life 웹서버용
3. dbms 생성
    1. suggest-life project 선택
    2. 저장소 ->  sql 
        1. region : Asia-NorthEast
        2. db명 : suggest-mydb
        3.  비번은 적당희, 사양도 적당히
        4. 적당한 환경설정
            1.  root 계정 비번 확인 한번 하고 
            2. 사용할 데이터 베이스(suggestDB)
            3. 승인된 네트워크 설정 (ALL (0.0.0.0/0))
            4. DB 연결 확인 및 sql schema 적용
        5. 기존 소스 source code db conn url 변경
            1. api-server , search-server
4. 로컬 glcoud 설정
    1. https://cloud.google.com/sdk/docs/quickstart-macos
    2. http://lng1982.tistory.com/274
5. cloud 배포
    1. service, node , serarch 배포
        1. search-gokgok App Engine 활성화 (메뉴 클릭으로..) -> search-gokgok 초기화(지역설정 등.. 잡다한거 하고)
        2. project search-gokgok  확인
            1. api-service  -> app engine flexible environment  -> appengineDeploy 
            2. node-service -> gcloud app deploy
            3. search-service  -> app engine flexible environment  -> appengineDeploy  ( app engine - default)
            4. router policy deploy => gcloud app deploy src/main/appengine/dispatch.yaml
6. domain 설정
    1. App Engine -> 설정 -> 맞춤 도메인 설정 
7. static web content 배포
    1. firebase 프로젝트 생성
    2. firebase local 기본설정 
        1. https://firebase.google.com/docs/hosting/deploying?hl=ko
    3. .firebaserc  프로젝트 명 설정
    4. 기존 프로젝트에 배포 될 경우가 있어서 배포  프로젝트 다시 확인(firebase use --add)
    5. domain 연결


기타
1. google map 사용을 위한 api 키 생성
    1. https://console.cloud.google.com/google/maps-apis


# 개별 프로젝트

## static code project (react.js)
    https://github.com/codelifehoon/sugguesweb

## server extension project (node.js)
    https://github.com/codelifehoon/suggues-fr

## api server project (SpringBoot)
    https://github.com/codelifehoon/suggues-api

## use appengine api project(SpringBoot) 
    https://github.com/codelifehoon/suggest-appengine-api

## ML api project (tensorflow)




