# Henstagram

**2018. 08.23~09.05 기준**

- [인스타그램 홈페이지](http://instagram.com)를 Spring boot 프로젝트로 클론합니다.
- 작성중인 코드는 [깃허브](https://github.com/HenrySungnamKim/henstagram)에 보관합니다.

---

## 기술 스택

- Spring boot 2.04 (Apache tomcat 8.xx)
- Maven(library management)
- JPA
- mustache(template engine)
- h2 DB(test)

## 눈여겨 볼 점

- Ajax (기능 작업중) ⏳

  메인페이지 검색 : 회원 리스트 출력

  웹 소켓 : 실시간 채팅

  계층형 댓글 처리

- 바닐라 자바스크립트 사용 (Jquery사용 x)
- DB 관계 설계

  JPA Entity, JPA Repository, Follow Relation

## 이 기술을 사용한 이유와 장/단점

- Spring boot / JPA

  최근 Spring을 사용하는 업계에서 바꾸려고 하는 경향이 있다고 해서 준비하게 되었다.

  - 장점

    비즈니스 로직에만 집중하게끔 도와준다. 생산성이 좋아진다.

  - 단점

    많은 회사에서 여전히 Spring + Mybatis를 사용하고 있으므로 실무 적용률이 낮다.

- mustache

  View에만 집중한 템플릿 엔진

  - 장점

    로직이 혼재되지 않은 깔끔해진 뷰 페이지를 얻는다. 

  - 단점

    데이터의 중간 가공이 힘들어진다. JSTL의 core태그의 여러 기능들

- h2 DB

  서비스 출시 전 test용 DB로 사용하기 좋다. 하지만 아직 TDD를 적용하기엔 시간과 실력이 적합하지 않아서 잘 활용하지 못하고 있다.

## DB구성

- User

  ID(PK), UserName, UserEmail, UserID, UserPassword, UserProfileImage(예정), UserIntro, Set<User> followers, Set<User> following;

- Article

  ID(PK), ArticleName, UserName, ArticlePlace, ArticlePicture,   

- Heart

  ID(PK), Article.ID, User.ID

- Follow Relation
  - follower

    @ManyToMany(cascade = CascadeType.**ALL**)

    @JoinTable(name = "USER_RELATIONS", joinColumns = @JoinColumn(name = "FOLLOWED_ID"), inverseJoinColumns = @JoinColumn(name = "FOLLOWER_ID"))

  - following

    @ManyToMany(mappedBy = "followers")

- ProfileImage

  ID(PK), ImageName, User.ID

## 메인 페이지 기능 / 디자인

- 메인페이지

<img width="1437" alt="1" src="https://user-images.githubusercontent.com/37807838/45080321-98cdbf00-b12f-11e8-85d0-8eb26dc61a1b.png">

- navigation
  - searchBar (기능 작업 중) ⏳
    - 회원 목록 리스트, 화면 이동 없이(Ajax)
  - explore (기능 작업중) ⏳
    - 화면 이동, 상단에 네비, 친구 추천, 게시글 랜덤하게 가져오기
  - heart (기능 작업중) ⏳
    - 하트 누른 회원 사진, '팔로워'가 회원님의 사진을 좋아합니다. 1주전, 게시글 사진
    - 리스트 사이 친구 추천 기능
  - personal inf
    - 회원 이름, 프로필 편집 버튼, 환경설정(로그아웃, 취소) ✅
    - 게시물 숫자, 팔로워 숫자, 팔로우 숫자 ✅
    - 개인 정보(별명, 자기소개) ✅
    - 내 게시물 리스트 n * 3열 ⏳
- Body
  - 게시글  (기능 작업중) ⏳
    - 헤더(프로필사진, 아이디, 장소)
    - 사진
    - 하트, 댓글 보기, 아카이브
    - 댓글 모두 보기
    - 아이디, 댓글
    - 시간 전
    - 댓글 달기
    - 오른쪽 ... 팔로우 취소, 부적절한 게시물 신고
  - 실시간 친구와 채팅  (기능 작업중) ⏳
    - 웹 소켓
    - 맨 위 헤더 : 프로필 사진, 아이디, 이름
    - 실시간 채팅, 더 보기
    - 친구 프로필 사진, 아이디, 채팅
- Footer ✅

## 로그인, 회원가입 페이지

- 로그인 페이지 ✅

<img width="1440" alt="2" src="https://user-images.githubusercontent.com/37807838/45080455-e4806880-b12f-11e8-863c-621680fda381.png">

- 회원가입 페이지 ✅

  <img width="1440" alt="3" src="https://user-images.githubusercontent.com/37807838/45080459-e64a2c00-b12f-11e8-857c-9b3b6fd71ba7.png">

## 개인 정보 페이지

- 회원이름, 프로필 편집 버튼, 환경설정(로그아웃, 취소) ✅
- 프로필 사진 바꾸기 (기능 작업중) ⏳

<img width="1440" alt="4" src="https://user-images.githubusercontent.com/37807838/45080461-e77b5900-b12f-11e8-8353-122d5b7e0502.png">

 <img width="1440" alt="5" src="https://user-images.githubusercontent.com/37807838/45080467-ec400d00-b12f-11e8-8263-c87e1e522143.png">

<img width="1440" alt="6" src="https://user-images.githubusercontent.com/37807838/45080473-ee09d080-b12f-11e8-90d7-ae4754826eba.png">

- 게시물 숫자, 팔로워 숫자, 팔로우 숫자 ✅
- 개인 정보(별명, 자기소개) ✅
- 내 게시물 리스트 n * 3열 (기능 작업중) ⏳

## 친구 정보 페이지

- 팔로잉 / 팔로우 버튼. (클릭시 자동 전환 / 디비연동) (기능 작업중) ⏳
- 개인정보 페이지에 내 친구가 팔로우하고 있습니다. (기능 작업중) ⏳

## 질문하기 게시판

- 질문 작성 ✅

<img width="1438" alt="7" src="https://user-images.githubusercontent.com/37807838/45080477-ef3afd80-b12f-11e8-9108-64abff499d79.png">

- 질문 상세보기 ✅

<img width="1440" alt="8" src="https://user-images.githubusercontent.com/37807838/45080479-f19d5780-b12f-11e8-83c4-48f31a690fa5.png">

<img width="1440" alt="9" src="https://user-images.githubusercontent.com/37807838/45080484-f3671b00-b12f-11e8-8aec-575ffa3a210c.png">

## 개인 게시물 작성

- 메인페이지에 게시물 작성하기 버튼 (기능 작업중) ⏳
- 파일 업로드 + 사진 미리보기 (기능 작업중) ⏳
