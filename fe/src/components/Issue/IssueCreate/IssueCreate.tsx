import { useContext, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { UserContext } from '../../Context/UserContext';
import { customFetch } from '../../../util/customFetch';
import { Theme, css, useTheme } from '@emotion/react';
import { border, font, radius } from '../../../styles/styles';
import { ReactComponent as XSquareIcon } from '../../../assets/icon/xSquare.svg';
import IssueContent from '../IssueContent';
import SidebarCreate from './SidebarCreate';
import Button from '../../common/Button';
import UserImageIcon from '../../UserImageIcon';
import { PostNewIssueRes } from '../../../type/Response.type';

export default function IssueCreate() {
  const theme = useTheme();
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const navigate = useNavigate();
  const { ...context } = useContext(UserContext);

  const onCancelClick = () => {
    navigate('/');
  };

  const onNewIssueSubmit = async () => {
    try {
      const response = await customFetch<PostNewIssueRes>({
        subUrl: 'api/issues',
        method: 'POST',
        body: JSON.stringify({
          title: title,
          content: content,
          writerId: context.user?.member.id,
          assigneeIds: [],
          labelIds: [],
          milestoneId: null,
        }),
      });

      if (response.success && response.data) {
        navigate(`/issue/${response.data.id}`);
      }
    } catch (error) {
      //Memo: 에러 핸들링 필요
      console.log(error);
    }
  };

  const onTitleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setTitle(e.target.value);
  };

  const onContentChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setContent(e.target.value);
  };

  const onAddImg = (imgMarkdown: string) => {
    setContent((prev) => `${prev}\n${imgMarkdown}\n`);
  };

  return (
    <div css={issueCreate(theme)}>
      <div className="header">새로운 이슈 작성</div>
      <div className="divider" />
      <div className="body">
        <UserImageIcon size="M" url={context.user?.member.imageUrl} />
        <main className="main">
          <div className={`title ${title ? 'filled' : ''}`}>
            <input
              type="text"
              placeholder="제목"
              value={title}
              onChange={onTitleChange}
            />
            {title && <div className="label">제목</div>}
          </div>
          <IssueContent
            value={content}
            onChange={onContentChange}
            onAddImg={onAddImg}
            height="448px"
            withInfo
          />
        </main>
        <SidebarCreate />
      </div>
      <div className="divider" />
      <div className="buttons">
        <Button
          icon={<XSquareIcon />}
          color={theme.neutral.textDefault}
          size="S"
          fontSize="M"
          value="작성 취소"
          onClick={onCancelClick}
        />
        <button type="button" className="submit" onClick={onNewIssueSubmit}>
          완료
        </button>
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
        position: relative;

        input {
          width: 100%;
          padding: 0 16px;
          box-sizing: border-box;
          height: 56px;
          border: none;
          border-radius: ${radius.large};
          color: ${theme.neutral.textDefault};
          background-color: ${theme.neutral.surfaceBold};
          font: ${font.displayMedium16};

          &:focus {
            outline: ${border.default} ${theme.neutral.borderDefaultActive};
            background: ${theme.neutral.surfaceStrong};
          }
        }

        .label {
          position: absolute;
          top: 10px;
          left: 16px;
          font: ${font.displayMedium12};
          color: ${theme.neutral.textWeak};
        }
      }
      .filled {
        input {
          padding: 14px 16px 0;
        }
      }
    }
  }

  .buttons {
    display: flex;
    align-items: center;
    gap: 32px;
    justify-content: end;

    .submit {
      width: 240px;
      height: 56px;
      border-radius: ${radius.large};
      background-color: ${theme.brand.surfaceDefault};
      color: ${theme.brand.textDefault};
      font: ${font.availableMedium20};
    }
  }
`;
