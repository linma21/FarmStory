# FRAMSTORY_PROJECT

---
## 프로젝트 소개
- Spring 웹 쇼핑몰
- 
## 기간 / 인원
- 2024.04.01. ~2024.04.12 (0개월) /4명

## 개발 환경
- Version : Java 17
- IDE : IntelliJ
- Framework : SpringBoot 3.2.3
- ORM : JPA, MyBaties, QueryDsl
- Terminal : ![git](https://img.shields.io/badge/GIT-E44C30?style=for-the-badge&logo=git&logoColor=white)
  
## 기술 스택
- Server : ![AWS](https://img.shields.io/badge/Amazon_AWS-FF9900?style=for-the-badge&logo=amazonaws&logoColor=white)
- DataBase : ![MySQL](https://img.shields.io/badge/MySQL-00000F?style=for-the-badge&logo=mysql&logoColor=4479A1)
- frontend : ![javaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=JavaScript&logoColor=white) ![html](https://img.shields.io/badge/HTML-239120?style=for-the-badge&logo=html5&logoColor=white)  ![css](https://img.shields.io/badge/CSS-239120?&style=for-the-badge&logo=css3&logoColor=white)
- backend : ![java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white) ![spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white) ![gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)

## 모델링 
- User의 포인트와 등급을 가진 유저 상세 테이블이 존재한다.
- User는 여러 Order를 가질 수 있다.
- User는 하나의 Cart를 가질 수 있다.
- Cart는 여러 Product를 포함할 수 있다.
- Order는 여러 Product를 포함 할 수 있다. 
- 주문 제품 상세를 OrderDetail 테이블로 관리한다.
- Article은 다양한 섹션에 속할 수 있다.
- 하나의 제품 Article엔 하나의 Product가 있다.
- ![farmDRD](https://github.com/linma21/farmstory/assets/154877422/0d529357-4f33-4c3d-8eee-75032b42b403)


  
## 주요 기능
- [로그인]
	- 로그인 기능 구현
	- 소셜 로그인 기능 구현
- [약관]
	- 약관 내용 출력 구현
	- 약관 동의 체크 구현
- [회원가입]
	- 중복체크 기능
	- 유효성 검사
	- 이메일 인증 기능
- [게시판]
	- CRUD
- [쇼핑]
	- 장바구니 기능
	- 주문하기 기능
- [회원 관리]
	- 회원가입 및 로그인 기능
	- 회원은 등급별로 구성된다.
	- 관리자는 모든 데이터에 대한 CRUD가 가능하다.
- [관리자 관리]
	- 관리자는 상품 관리 페이지에 (farmstory/admin) 접근 가능하다.
	- 관리자는 회원의 등급을 조정할 수 있다.
- [주문 관리]
	- 관리자에 의한 생성/ 수정/ 삭제가 가능하다.
	- 주문 시 포인트가 적립된다.
- [포인트 관리]
	- 관리자에 의한 생성/ 수정/ 삭제가 가능하다.
	- 포인트 사용 시 결제 금액이 차감된다.
	- 사용자 등급에 따라 적립율이 다르다.
## 배운 점
   - 
## 아쉬운 점
- 
