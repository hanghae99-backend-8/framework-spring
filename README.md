# framwork spring
## Overview
개발사항 및 담당자
- sign-up: 강래구
- sign-in: 강래구
- post search: 박종훈
- post CRUD: 한찬희

## 환경변수(application.yaml) 설정방법
- default: local
- 파일 경로: src/main/resources
```bash
# local 프로파일로 실행
./gradlew bootRun

# prd 프로파일로 실행
./gradlew bootRun --args='--spring.profiles.active=prd'

# laeku 프로파일로 실행
./gradlew bootRun --args='--spring.profiles.active=laeku'

# chanhui 프로파일로 실행
./gradlew bootRun --args='--spring.profiles.active=chanhui'

# jonghoon 프로파일로 실행
./gradlew bootRun --args='--spring.profiles.active=jonghoon'
```

# git push test 입니다
