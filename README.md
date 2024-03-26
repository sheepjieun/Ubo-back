# 교내 학생끼리 안전한 중고 거래 플랫폼, 우리학교 보따리상(U-bo)👩‍🎓
<br>

<img width="700" alt="Untitled" src="https://github.com/sheepjieun/Ubo-back/assets/119403764/578f4f4e-8932-49f4-bdcc-426129f24225">


<br>
<br>


## 💻 웹서비스 소개

<br>

**우리학교 보따리상** 은 기존 대학생 커뮤니티의 부족한 중고거래 서비스를 보완하기 위해 교내 재학생 전용 중고 직거래 및 대여 플랫폼을 제작하였습니다. 

대학생들의 수요가 높은 서적, 실습도구, 충전 케이블 등 다양한 중고 물품을 저렴하게 학교에서 직거래할 수 있습니다. 실시간 채팅과 학과 필터링을 통해 맞춤형 이용과, 대학 웹메일 인증을 통한 회원가입으로 재학생끼리 안전한 직거래가 가능합니다. 

<br> 

- **회원가입**
  - 대학교 웹메일 인증 API를 통해 입력한 대학 웹메일로 인증 번호를 입력하여 재학생만 회원가입할 수 있습니다.
- **중고거래, 대여 물품 등록**
  - 판매자는 물품 이미지, 이름, 가격, 상세 정보 등 기본적인 물품의 정보를 입력합니다. 
  - 중고거래의 경우 중고 물품을 서적, 실습도구, 기타, 부동산, 삽니다 및 전체를 포함한 6개의 카테고리에 따라 분류하였습니다.
  - 중고거래 물품의 카테고리로 서적을 선택할 경우, 서적 상태 입력칸이 추가로 생성되며 체크박스 형식으로 밑줄, 필기 흔적, 페이지 훼손 등 서적 상태를 간단히 입력할 수 있습니다. 
  - 대여의 경우 1일당 대여 가격, 보증금, 대여 가능 날짜를 포함하여 물품 상세 정보를 입력할 수 있습니다. 
- **좋아요**
  - 구매자는 물품 상세 정보 페이지에서 좋아요 버튼을 클릭하여 마이페이지-관심목록 페이지에서 확인할 수 있습니다. 
- **채팅**
  - 채팅하기 버튼을 클릭하여 판매자-구매자간 실시간 채팅이 가능합니다. 
- **필터링 및 학과명 검색**
  - 물품 검색, 물품 타입(중고거래/대여), 물품 정렬, 학과명을 검색할 수 있습니다.
  - 이용 중인 유저의 현재 학과명을 학과명 검색란에 기재 시 해당 학과와 관련된 물품으로 재정렬됩니다.
  - 최신순, 가격 낮은 순, 가격 높은 순으로 물품을 정렬할 수 있습니다. 
- **거래내역, 관심물품**
  - 상단의 마이페이지로 이동하여 회원이 등록한 물품, 거래한 물품 내역 및 관심목록에서 좋아요한 물품을 확인할 수 있습니다.
- **회원 정보 변경**
  - 마이페이지-내 정보 에서 유저 이미지, 닉네임, 비밀번호를 변경할 수 있습니다. (아이디, 이메일의 경우 유저의 대학 웹메일과 연동되기 때문에 변경이 불가합니다.) 

<br><br>

## 📕 사이트

**23.12.16 이후로 서버 구동이 중지되었습니다.**

- 서버 링크: https://ourboddari.com

<br><br>

## 📗 기획 & 설계

- [과제 추진 계획서](https://jieun97.notion.site/e3ae6b1c079b4c3b8fcad4e0eac2c54c?pvs=4)
- [기능 명세서](https://jieun97.notion.site/514be2c5f0654f388364cbce677d7dfa?pvs=4)
- [DB 명세서](https://jieun97.notion.site/DB-faf028f8403f4feba4061009b53c7eec?pvs=4)
- [API 명세서](https://jieun97.notion.site/API-3c6e501c4c234dd0bd0bfc6114b4ea6e?pvs=4)
- [페이지 기획서](https://www.figma.com/files/project/106893732/%25EC%259A%25B0%25EB%25A6%25AC%25ED%2595%2599%25EA%25B5%2590-%25EB%25B3%25B4%25EB%2594%25B0%25EB%25A6%25AC%25EC%2583%2581?fuid=1183747069252297383)
- [DB ERDCloud](https://www.erdcloud.com/d/EpqpbwnbrmwPe9xQs)
  

<br><br>

## 📘 개발 기간

- 2023.11.01 - 2023.12.21

<br><br>

## 📙 기술 스택

#### FE
- Javascript
- React
- BootStrap
- Figma

#### BE
- Open JDK 17
- Spring Boot
- Spring Data JPA
- Gradle
- MySQL
- Redis
- AWS - EC2, S3, Route53

<br><br>

## 👩🏻‍💻 팀원 소개

#### FE
- [Jangho Yu](https://github.com/jang2714)

#### BE
- [Jieun Yang](https://github.com/sheepjieun)

<br><br>
