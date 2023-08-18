![](https://img.shields.io/badge/Last_Upadate-2023--08--18-blue)
# 📋 Issue-tracker-max
- 코드스쿼드 2023 마스터즈 맥스 프로젝트 `이슈 트래커 구현`
- 기간 `2023-07-24 ~ 2023-08-18`

<br>

## 👨‍👩‍👦‍👦 멤버
 | <img src="https://avatars.githubusercontent.com/u/101464713?v=4" width="100px" /> | <img src="https://avatars.githubusercontent.com/u/75569293?v=4" width="100px" /> | <img src="https://avatars.githubusercontent.com/u/107015624?v=4" width="100px" /> | <img src="https://avatars.githubusercontent.com/u/98851575?v=4" width="100px" /> |
| :---: | :---: | :---: | :---: |
| [TOKO(FE)](https://github.com/aaaz425) | [June(BE)](https://github.com/JJONSOO) | [Jeegu(BE)](https://github.com/Ojeegu) | [무비(BE)](https://github.com/yhpark95) |

<br>

## 🎡 동작 구현
__회원가입__

![회원가입](https://github.com/codesquad-team4-issue-tracker/issue-tracker-max/assets/107015624/bfaebef7-f019-4ea4-bbe2-bc9584815d4f)

__로그인__

![로그인](https://github.com/codesquad-team4-issue-tracker/issue-tracker-max/assets/107015624/10ad9827-77e1-4862-8335-5c510a2b07dc)


__이슈__

![이슈작성삭제](https://github.com/codesquad-team4-issue-tracker/issue-tracker-max/assets/107015624/6f8a2c4d-c813-4dd5-b2c6-789aa28d10c9)


![이슈수정](https://github.com/codesquad-team4-issue-tracker/issue-tracker-max/assets/107015624/93505216-a162-4142-9639-15fd7804b9f7)


__코멘트__

![코멘트CRUD](https://github.com/codesquad-team4-issue-tracker/issue-tracker-max/assets/107015624/59ad3d1a-219f-4ede-a08c-ffa9c99a8b7a)

![코멘트수정](https://github.com/codesquad-team4-issue-tracker/issue-tracker-max/assets/107015624/2707aa58-90ac-4cf0-be17-39586beffc0f)


__레이블__

![레이블CRUD](https://github.com/codesquad-team4-issue-tracker/issue-tracker-max/assets/107015624/3f5ca2bf-5efe-4705-84df-f7de850ef4d0)


__마일스톤__

![마일스톤CRUD](https://github.com/codesquad-team4-issue-tracker/issue-tracker-max/assets/107015624/0f1f6b34-3421-47bd-8d6e-e12e0ec0bc2e)


__다크모드__

![다크모드](https://github.com/codesquad-team4-issue-tracker/issue-tracker-max/assets/107015624/8534729e-f9a8-41b1-8a0b-ddb3e5bb098b)

<br>

## 🤝 그라운드 룰
1. 의견을 숨기지 말고 솔직하게!
    - 다 좋아요~ 금지
2. 슬랙 보고 리액션 남기기
3. 존중하는 태도로 부드럽게 커뮤니케이션하기
4. 지각하지 말자! 스크럼은 정각에 시작한다.
    - 지각자가 있을 경우 5분까지 기다렸다가 스크럼 진행
5. 구두로 논의한 내용도 슬랙에 모두 기록하고 공유하기
6. 코어타임 (10:00 - 18:00) 게더 4조에서 함께 접속하기

<br>

## 🗣 스크럼 룰
1. 어제 한 일
2. 오늘 할 일
3. 컨디션
4. 기타 공유
<details>
<summary>Details</summary>
<div markdown="1">

1. 어제 한  일: 어제 무엇을 했는지 간단하게 공유. <br>
ex) 어제 계획했던대로, 검색창과 서버를 연결해서 자동완성 기능을 구현했다. <br>
어제 계획했던 사이드바의 메인메뉴와 서브메뉴간의 이동을 ㅇㅇ문제 때문에 아직 구현하지 못했다.

2. 오늘 할 일: 작고 구체적인 오늘의 목표/계획 공유. <br>
ex) 점심시간 전까지 Promise에 대해서 공부하고 내용을 기록하기. <br>
코어타임 마무리 전까지 사이드바의 메인메뉴와 서브메뉴간의 이동을 구현하고 커밋 올리기. 1시간 동안 딤처리 로직을 리팩토링 하기.

3. 컨디션 <br>
ex) 오늘은 잠을 푹 자서 8점이요.

4. 기타 공유 <br>
ex) 이부분이 도무지 이해가 안가고 해결이 안되고 있는데 도와주실 분 있나요?

</div>
</details>

<br>

## ᛋ 브랜치 전략
- `main`
- `dev`
- `be`
- `fe`
- `feat/#이슈번호-기능`

💡 PR은 브랜치를 넘어갈 때 마다 합니다.
```
feat/#3-login → be,fe → dev → main
```

<br>

## 💬 커밋 컨벤션
```
feat #21: 로그인

세션을 통한 로그인 기능을 개발
```
- `feat` : 새로운 기능
- `design`: CSS 와 같은 사용자 UI 변경
- `fix` : 버그 수정
- `docs` : 문서 수정 (README, .gitignore 등)
- `test` : 테스트 코드 추가
- `refactor` : 리팩토링
- `style` : 코드 의미에 영향을 주지 않는 변경사항
- `chore` : 빌드 부분 수정사항

<br>

## 💬 이슈 템플릿
제목: [FE] 프로젝트 초기 세팅 (`[FE/BE] Issue제목`)

```jsx
## Feature 

## Tasks
- [ ]
- [ ]
- [ ]
```

- `Feature` : 새로 추가할 기능
- `Tasks` : 기능 구현을 위한 작업 목록

예시

```html
## Feature
- 카드 조작(생성, 삭제, 수정, 이동) 시 히스토리 생성

## Tasks
- [x] RequestDTO 구현
- [x] 히스토리 생성 sql문 및 로직 구현
- [x] 테스트코드 작성
```

<br>

## 💬 PR 템플릿
제목: [FE] 프로젝트 초기 세팅 (`[FE/BE] pr제목`)
```markdown
## What is this PR? 👓

## Key changes 🔑

## To reviewers 👋
```

<br>

## 💻 코딩 컨벤션
- 백엔드 : 네이버 코딩 컨벤션 적용
  - [참고 블로그](https://bestinu.tistory.com/64)
- 프론트엔드 : 클린코드 지향
