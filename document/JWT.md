# JWT
## JWT란?

- json web token은 웹표준으로서 두개체에서 json 객체를 사용하여 가볍고 자가수영적인 방식으로 정보를 안정성 있게 전달
- 다양항 프로그래밍 언어에서 지원
- 자가 수용적 : 필요한 정보를 자체 보유(기본정보, 전달 정보, 증명 정보(signature))
- 주로 인증을 위해서 사용(회원인증, 정보 교류(정보 조작여부 검증 등))
### 구성
  - header
    - jwt임을 명시
    - 사용된 암호화 알고리즘 : 보통 해싱 알고리즘이 사용됨 (hmac sha256 나 rsa) 
```
    {   
        "alg" : "HS256",
        "typ" : "JWT"
    }
```
  - payload
    - 실제 사용자가 사용하는 정보
```
    {
        "iss" : "토큰 발급자",
        "sub" : "토큰제목",
        "aud" : "토큰대상자",
        "exp" : "토큰 만료시간",
        "iat" : "토큰 발급시간"
        ...
    }
```
  - signature
    - 암호화 키

  - 전체 형태
    - base64(header) + base64(payload) + 암호화키
    - ***header.payload.signature***
    - ex) eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.KMUFsIDTnFmyG3nMiGM6H9FNFUROf3wh7SmqJp-QV30

### 특징
  - 내부 정보를 단순 base64 방식으로 인코딩 -> 외부에서 쉽게 디코딩 가능
  - 외부에서 열람할 때 봐도 무관한 정보만 입력할 것
  - 발급처 보장 및 검증이 확실함

