import { Theme, css, useTheme } from '@emotion/react';
import { ReactComponent as UserImageLargeIcon } from '../../../assets/icon/userImageLarge.svg';
import { border, font } from '../../../styles/styles';

export default function IssueCreate() {
  const theme = useTheme();

  return (
    <div css={issueCreate(theme)}>
      <div className="header">새로운 이슈 작성</div>
      <div className="divider" />
      <div className="body">
        <UserImageLargeIcon />
        <main className="main">
          <div className="title">
            <input type="text" placeholder="제목" />
          </div>
          <div className="content">
            <textarea placeholder="코멘트를 입력하세요" />
            <input type="file" />
          </div>
        </main>
        <div className="side-bar">사이드바</div>
      </div>
      <div className="divider" />
      <div className="buttons">
        <button>작성 취소</button>
        <button>완료</button>
      </div>
    </div>
  );
}

const issueCreate = (theme: Theme) => css`
  display: flex;
  flex-direction: column;
  gap: 24px;
  width: 1280px;
  margin: 32px auto 0;

  .header {
    font: ${font.displayBold32};
    color: ${theme.neutral.textStrong};
  }

  .divider {
    width: 100%;
    height: 1px;
    background-color: ${theme.neutral.borderDefault};
  }

  .body {
    display: flex;
    gap: 24px;

    .main {
      display: flex;
      flex-direction: column;
      gap: 8px;
      width: 912px;

      .title {
        input {
          width: 100%;
          padding: 0 16px;
          box-sizing: border-box;
          height: 56px;
          border: none;
          border-radius: ${border};
        }
      }

      .content {
        display: flex;
        flex-direction: column;
        width: 100%;
      }
    }
  }
`;
