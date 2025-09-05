# BestMeat
![로고](https://github.com/jihoons1/2025_PJ3_4team_project/blob/master/src/main/resources/static/img/logo.png)

# 1. 개요 
## 주제
- 정육점별 고기 가격 비교 사이트

## 프로젝트 소개
- 'BestMeat'라는 우리 플랫폼은 사용자가 원하는 정육을 가장 합리적인 가격으로, 가장 가까운 매장에서 쉽게 찾을 수 있도록 돕는 정육점 가격 비교·검색 서비스입니다.
- 본 플랫폼은 각 정육점에서 취급하는 정보를 투명하게 공개하여 사용자의 정보 접근성을 높이고, 불필요한 시간과 비용 낭비를 줄이는 것을 목표로 합니다.

## 프로젝트 일정
### 2025.08.22~2025.09.12 (22일)
- [ 프로젝트 일정(Jira) ](https://jeonghoonahn0510.atlassian.net/jira/software/projects/BESTMEAT/boards/3/timeline?timeline=WEEKS)
- [ 프로젝트 일정(SpreadSheets) ](https://docs.google.com/spreadsheets/d/1DkasuA2cf582ZsWsjgIm8LCOt13cGOy6s99kYl3EMps/edit?gid=926708291#gid=926708291)

## 구성원
- #### 민성호
  - [Github](https://github.com/msh-94)
- #### 송지훈
  - [Github](https://github.com/jihoons1)
- #### 안정훈
  - [Github](https://github.com/JeonghoonAHN0510)

# 2. 구현 기능
- #### 회원가입( 송지훈 )
  - 회원가입 시 DB에 회원정보가 등록됩니다.
- #### 로그인( 송지훈 )
  - 다양한 유효성 검사와 우편번호 API를 통해 로그인합니다.
- #### 아이디 | 비밀번호 찾기( 송지훈 )
  - 이메일 API를 통해 임시 비밀번호 발급이 진행되고, DB에 저장됩니다.
- #### 알림 관리( 안정훈 )
  - 원하는 제품의 원하는 가격을 입력하면, 포인트가 차감되고 알림이 DB에 저장됩니다.
  - 지정한 조건을 충족한다면, SMS API를 통해 알림이 전송됩니다.
  - 알림 수정 및 삭제 기능을 제공합니다.
- #### 재고 관리( 안정훈 )
  - 정육점을 소유한 회원이 제품과 가격을 입력하면, DB에 저장됩니다.
  - 재고 수정 및 삭제 기능을 제공합니다.
- #### 리뷰 관리( 민성호 )
  - 해당 정육점에서 3KM 이내에서만 리뷰를 작성할 수 있습니다.
  - 리뷰 수정 및 삭제 기능을 제공합니다.
- #### 검색 기능( 민성호 )
  - 키워드를 입력하면, 해당하는 제품을 조회합니다.
  - 정렬 기준을 선택하면, 해당하는 기준으로 정렬하여 조회합니다.
- #### 지도 기능( 안정훈, 민성호 )
  - 정육점의 주소에 기반한 위치를 네이버지도 API를 활용하여 표시합니다.
  - 지오코더 API를 활용하여, 주소 기반의 위도/경도를 조회합니다.
- #### QR 기능( 민성호 )
  - 각 주소에 기반한 네이버 길찾기 URL을 QR코드를 생성하고, 사용자에게 제공합니다.
- #### 결제 기능( 안정훈 )
  - 결제 API를 통해, 사용자가 포인트를 구매할 수 있습니다.
  - 로그인 | 회원가입 시, 일정 포인트를 지급합니다.
 
# 3. 기술 스택
## 3-1. Back
- Java ( Amazon Corretto 17.0.15 )
- Spring Boot

## 3-2. Front
- JSP
- JavaScript
- CSS3

## 3-3. API · Library
- Lombok
- Bootstrap( 5.3.8 )
- NaverMap API
- Zxing API
- Geocoder API
- PortOne API
- Daum Postcode API
- Java Mail Sender
- Solapi API
