# FRAMSTORY_PROJECT

---
## 🛒 프로젝트 소개
- Spring 웹 쇼핑몰
- Springboot, JPA 를 활용한 팀 프로젝트
  
## ⏱ 기간 / 👫 인원
- 2024.04.01. ~2024.04.12. (14일) /4명

## 🖥 개발 환경
- Version : Java 17
- IDE : IntelliJ
- Framework : SpringBoot 3.2.3
- ORM : JPA, QueryDsl
  
## 📚 기술 스택
- Server : 	![AWS](https://img.shields.io/badge/Amazon_AWS-FF9900?style=for-the-badge&logo=amazonaws&logoColor=white)
- DataBase : ![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
- frontend : ![javaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=JavaScript&logoColor=white) ![html](https://img.shields.io/badge/HTML-239120?style=for-the-badge&logo=html5&logoColor=white)  ![css](https://img.shields.io/badge/CSS-1572B6?&style=for-the-badge&logo=css3&logoColor=white)
- backend : ![java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=openjdk&logoColor=white) ![spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white) ![gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)

## 모델링 
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
	- 게시글 작성/ 수정/ 삭제/ 조회
   	- 댓글 작성/ 수정/ 삭제/ 조회
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

|  메인 페이지                                                                                                             |
|---------------------------------------------------------------------------------------------------------------|
| ![farmstoryIndex](https://github.com/linma21/farmstory/assets/154877422/714b513d-cd05-47c5-86a3-df1117bdcf2d) |

| 로그인                                                                                                        | 주문하기                                                                                                        | 관리자                                                                                                        |
|---------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------|
| ![farmstoryLogin](https://github.com/linma21/farmstory/assets/154877422/b7e6a146-16a2-4547-bf9a-f2711b6b27e5) | ![farmstoryGoOrder](https://github.com/linma21/farmstory/assets/154877422/f5e6c38e-beca-4c62-b0a3-89dedbc76539) | ![farmstoryAdmin](https://github.com/linma21/farmstory/assets/154877422/e3bab38e-6c83-4072-b96f-db0c057110e2) |

## 담당 기능
- 게시글 CRUD
- 댓글 CRUD
- 결제하기 페이지 출력
- 내 주문 목록 조회
- 리뷰 CRUD

| 게시글 목록                                                                                                       | 게시글 쓰기                                                                                                   | 댓글                                                                                                            |
|-------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------|
| ![farmstoryCommunity](https://github.com/linma21/farmstory/assets/154877422/4cf923e7-bb82-4d59-9841-480be7c88618) | ![farmstoryWrite](https://github.com/linma21/farmstory/assets/154877422/387ae920-d8f8-47de-8592-85766232efb2) | ![farmstoryComment](https://github.com/linma21/farmstory/assets/154877422/15959c85-b0cf-457d-834d-1a1af03a744c) |

| 결제하기 + 목록조회                                                                                                     | 리뷰 작성                                                                                                               |
|-------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------|
| ![farmstoryOrder_OrderList](https://github.com/linma21/farmstory/assets/154877422/5f786724-44cc-4037-960f-4b7ffa93b957) | ![farmstoryOrderListReview](https://github.com/linma21/farmstory/assets/154877422/ab328947-a391-4f22-906e-0248dffd22c9) |

| 리뷰 조회                                                                                                         |
|-------------------------------------------------------------------------------------------------------------------|
| ![farmstorReviewList](https://github.com/linma21/farmstory/assets/154877422/b9ddfdb8-f7eb-45ba-a1f3-f03e2c7e1b54) |

## 🏅 결과 : 팀 프로젝트 1위
![KakaoTalk_20240412_232833120](https://github.com/linma21/farmstory/assets/154877422/8eed6fcc-0695-44ca-885a-9bbe47f0c1ac)


## 배운 점
- Git Branch를 통해 매일 commit과 merge를 하며 팀 단위의 업무를 할 때 Github Branch 전략이 어떤 방식으로 운영되어야하는지 알게 되었다.
- 다른 팀원들이 코딩한 것을 보면서, 이 코드는 어떤 기능을 하는지, 왜 이런 방식을 사용했는지 고민해보고 나라면 어떤 방식의 코딩을 했을 지 생각하며 코드를 리뷰하니까 '효율적인 코드란 무엇인가'에 대한 폭 넓은 생각을 가지게 되었다. 다른 사람의 코드를 보고 의견을 나누는 게 이렇게 성장에 도움이 될 줄이야!
## 아쉬운 점
- 가장 먼저 생각나는 건 모델링. 팀원 모두가 처음 해보는 프로젝트라 모델링 단계에서 부터 꼼꼼한 작업이 필요하다는 걸 알지 못했다. 기본적인 기능은 일주일만에 구현했는데 이후에 하나 둘 씩 서브 기능들을 추가하려니 ERD를 손봐야할 일이 너무 많았다. 어떤 기능을 구현할 지 미리 정리해두고 모델링을 만드는 것에 더 많을 시간을 투자해야 할 것 같다.
- 
