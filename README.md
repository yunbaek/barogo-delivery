# 배달파트 Back-end(Java) 실무 과제

## 요구사항
1. 회원가입 API 구현
2. 로그인 API 구현
3. 배달 조회 API 구현
4. 배달 주문 수정 API 구현

## 요구사항 설명
### 1. 회원 가입
#### 인수 조건
```text
Feature: 회원 가입
  Scenario: 회원 가입 성공
    Given 회원 가입 정보 입력
    When 회원 가입 요청
    Then 회원 가입 성공 응답
    
  Scenario: 회원 가입 실패
    Given: 이미 존재하는 회원 정보 입력
    When: 회원 가입 요청
    Then: 회원 가입 실패 응답
 
  Scenario: 회원 가입 실패
    Given: 요구사항에 맞지 않는 회원 정보 입력
    When: 회원 가입 요청
    Then: 회원 가입 실패 응답
```

#### 요청 / 응답
- request
```http request
POST /api/v1/members HTTP/1.1
content-type: application/json; charset=UTF-8
accept: application/json

{
  "loginId": "loginId",
  "password": "password",
  "name": "name"
}
```

- response
```http request
HTTP/1.1 201 Created
content-type: application/json; charset=UTF-8
accept: application/json

{
  "loginId": "loginId",
  "name": "name"
}
```

### 2. 로그인
#### 인수 조건
```text
Feature: 로그인
  Scenario: 로그인 성공
    Given: 회원 등록되어 있음
    When: 로그인 요청
    Then: 로그인 성공 응답

  Scenario: 로그인 실패
    Given: 존재하지 않은 회원정보
    When: 로그인 요청
    Then: 로그인 실패 응답

  Scenario: 로그인 실패
    Given: 회원 등록되어 있음
    And: 비밀번호가 틀린 회원정보
    When: 로그인 요청
    Then: 로그인 실패 응답

  Scenario: 로그인 실패
    Given: 유효하지 않은 토큰 정보
    When: 로그인 요청
    Then: 로그인 실패 응답
```

#### 요청 / 응답
- request
```http request
POST /api/v1/members/login
content-type: application/json; charset=UTF-8
accept: application/json

{
  "password": "password",
  "email": "email@email.com"
}
```

- response
```http request
HTTP/1.1 200
content-type: application/json
transfer-encoding: chunked
date: Mon, 6 Feb 2023 00:00:00 GMT
keep-alive: timeout=60
connection: keep-alive

{
  "accessToken": "accessToken"
}
```
