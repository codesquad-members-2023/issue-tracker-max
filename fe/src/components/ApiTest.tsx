import { css } from '@emotion/react';

export default function ApiTest() {
  const GetAssignee = () => {};

  const on = () => {};
  return (
    <div css={test}>
      <button type="button" onClick={GetAssignee}>
        GET 담당자를 불러온다
      </button>
      <button type="button" onClick={on}>
        GET 레이블을 불러온다
      </button>
      <button type="button" onClick={on}>
        GET 마일스톤을 불러온다
      </button>
      <button type="button" onClick={on}>
        GET 작성자를 불러온다
      </button>
      <button type="button" onClick={on}>
        POST 이슈의 담당자를 수정한다
      </button>
      <button type="button" onClick={on}>
        POST 이슈 레이블을 수정한다
      </button>
      <button type="button" onClick={on}>
        POST 이슈 마일스톤을 수정한다
      </button>
      <button type="button" onClick={on}>
        DELETE 이슈 상세페이지의 코멘트를 삭제한다
      </button>
      <button type="button" onClick={on}>
        POST 레이블을 추가한다
      </button>
      <button type="button" onClick={on}>
        PUT 레이블을 수정한다
      </button>
      <button type="button" onClick={on}>
        DELETE 레이블을 삭제한다
      </button>
      <button type="button" onClick={on}>
        POST 마일스톤을 추가한다
      </button>
      <button type="button" onClick={on}>
        PUT 마일스톤을 수정한다
      </button>
      <button type="button" onClick={on}>
        DELETE 마일스톤을 삭제한다
      </button>
    </div>
  );
}

const test = css`
  display: flex;
  flex-direction: column;
  gap: 10px;

  > button {
    width: 200px;
    height: 40px;
  }
`;
