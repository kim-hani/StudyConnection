#  Study Connection

**Study Connection** 
은 저희가 만들 웹 어플리케이션 이름입니다.

저희는 다양한 분야에 대해 공부를 하고 싶은 사용자들의 니즈를 해소하고

여러 사람들이 함께 모여 공부하며 성장할 수 있는 커뮤니티를 구축하고자 합니다.

## :computer: 프로젝트 소개
Java Spring과 Vue.js로 만드는 스터디 그룹 매칭 사이트입니다.

이 어플리케이션은 사용자들 다양한 사용자들과 학습 관심사와 목표를 공유하며 

적절한 스터디 그룹을 형성해주는 것이 목표입니다.

<br><br>

### 📆 개발 일정
- 24년 4월 4일 ~ 24년 6월 20일
- [위키에서 개발 계획 보기](https://github.com/kim-hani/StudyConnection/wiki)
<br>

### 🧑‍🤝‍🧑멤버 구성
- 김종빈 : BackEnd , DB 설계 , 게시글 CRUD , 댓글 CRUD
- 김한이 : BackEnd , DB 설계 , 사용자 CRUD , 평가 CRUD
- 조성현 : FrontEnd , UI/UX 설계 및 개발
<br>

### 📝 사용 될 기술들
- Vue.js : 프론트 개발
- Spring Boot & Spring Data JPA : 서버 개발 및 데이터 통신
- AWS Lightsail : 서버 배포 및 유지
- MySql : 데이터 베이스
- Swagger : API 문서화
- GitHub , Slack : 협업
<br>

### 📄 서버 아키텍처 
![image](https://github.com/kim-hani/StudyConnection/assets/102886829/b1274d24-cc0e-46ad-97c0-3e6e50cb2df5)
<br>
<br>

### 🗃️ ERD

![image](https://github.com/kim-hani/StudyConnection/assets/102886829/b63e42c7-232e-40ef-9f0b-09da32f2287c)


## 📌 주요 서비스
**사용자 CRUD**

- 회원가입 / 로그아웃
- 사용자 정보 조회
- 사용자가 참여했거나 참여중인 스터디 조회
- 스터디를 클릭하면 스터디 멤버에게 받은 평가를 리스트로 조회

**게시글 CRUD**

- 게시글 생성 / 수정
- 메인페이지에 게시글 전체 로딩
- 게시글 검색
- 게시글 지원 버튼을 통한 참가 신청
- 게시글 마감 버튼을 통해 멤버 확정

**댓글 CRUD**

- 댓글 작성/수정/삭제
- 댓글 조회

**평가 CRUD**
- 회원/스터디 평가 작성
- 회원/스터디 평가 조회
