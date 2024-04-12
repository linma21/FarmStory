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
- **Branch 전략이란?**
	- Git Branch를 통해 매일 commit과 merge를 하며 팀 단위의 업무를 할 때 Github Branch 전략이 어떤 방식으로 운영되어야하는지 알게 되었다.
- **배울 점은 선생님에게만 있는 것이 아니다**
	- 다른 팀원들이 코딩한 것을 보면서, 이 코드는 어떤 기능을 하는지, 왜 이런 방식을 사용했는지 고민해보고 나라면 어떤 방식의 코딩을 했을 지 생각하며 코드를 리뷰하니까 '효율적인 코드란 무엇인가'에 대한 폭 넓은 생각을 가지게 되었다. 다른 사람의 코드를 보고 의견을 나누는 게 이렇게 성장에 도움이 될 줄이야!
 - **주석의 중요성**
 	- 팀 프로젝트를 시작 할 때 자신이 맡은 분야의 코드에 주석으로 간단한 기능 설명을 해놓자고 팀원들 간에 규칙으로 정했다. 기능 구현이 마무리되어 갈 때 코드 리뷰를 해보니 확실히 주석이 있으니 어떤 기능인지, 왜 사용했는지 이해하기가 훨씬 편했다. 협업을 할 때도, 혼자 코딩을 할 때도 주석으로 설명을 달아놓자!
## 아쉬운 점
- **데이터 모델링은 개발 설계의 핵심이다**
	- 가장 먼저 생각나는 아쉬운 점은 모델링. 팀원 모두가 처음 해보는 프로젝트라 모델링 설계의 중요성을 잘 알지는 못했다.<br>2주짜리 단기 프로젝트라 모델링은 반나절만에 끝내야 한다고 생각해서 빨리 설계했다. 그랬더니 기본적인 기능은 일주일만에 구현했는데 서브기능들을 추가할 때 ERD를 수정하는 것 때문에 시간과 코드를 많이 갈아 넣어야 했다.
단기 프로젝트라도 초반에. 어떤 기능을 구현할 지 미리 정리해두고 모델링을 만드는 것에 더 많을 시간을 투자해야 할 것 같다.
- **서버에서 처리 할 수 있는 건 서버에서!**
	- 우리 조는 조회한 DB 데이터를 출력할 때 Controller에서 해당 DTO를 Model로 보내서 필요한 연산 (금액, 날짜 계산 등)을 JavaScript에서 처리했는데 마지막 날 다른 조의 발표를 보면서 서버에서 처리할 수 있는 연산들은 되도록 백엔드에서 처리해서 view로 넘겨주는 방식을 사용했음을 알게 되었다. 그제야 우리가 js에서 처리한 연산들이 "우린 값 넘겨줬으니까 프론트에서 알아서 해!" 라는 말과 다를 바 없음을 알게 되었다. 우린 백엔드니까 다음 프로젝트에선 **백엔드에서 처리할 수 있는 부분은 백엔드에서!** 라는 말을 가슴에 새기고 코딩해야겠다.     
