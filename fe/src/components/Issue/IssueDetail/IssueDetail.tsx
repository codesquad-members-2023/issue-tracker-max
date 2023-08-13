import { Theme, css, useTheme } from '@emotion/react';
import SidebarDetail from './SidebarDetail';
import IssueDetailHeader from './IssueDetailHeader';
import Comment from './Comment';
import Button from '../../common/Button';
import { ReactComponent as PlusIcon } from '../../../assets/icon/plus.svg';
import IssueContent from '../IssueContent';
import { useState } from 'react';

const mockData = {
  issue: {
    id: 4,
    isOpen: true,
    title: '새로운 이슈',
    labels: [
      {
        id: 1,
        title: 'Backend',
        textColor: 'white',
        backgroundColor: 'black',
      },
    ],
    assignees: [
      {
        id: 1,
        name: 'June',
      },
      {
        id: 2,
        name: 'Movie',
      },
    ],
    writer: {
      id: 4,
      name: 'Jeegu',
    },
    milestone: {
      id: 1,
      title: '프로젝트1',
    },
    history: {
      editor: 'Jeegu',
      modifiedAt: '2023-08-04T17:56:03.238519',
    },
  },
  comments: [
    {
      id: 1,
      writer: {
        id: 4,
        name: 'Jeegu',
      },
      content: '새로운 내용',
      dateTime: '2023-07-25 11:44',
    },
    {
      id: 2,
      writer: {
        id: 2,
        name: 'Movie',
      },
      content: `
        이번 그룹 프로젝트에서 디자인 특징은 아래와 같습니다.
        타이포그래피 시스템에 display, selected, available같은 용법을 함께 표기함
        컬러 시스템에 라이트/다크 모드가 있음
        Components 페이지에 기획서에 없는 선택 미션 두 가지가 있음
        Text Input의 지우기 기능
        Comment Elements의 히스토리 기능
      `,
      dateTime: '2023-08-11 12:44',
    },
  ],
};

export default function IssueDetail() {
  const theme = useTheme();
  const [content, setContent] = useState('');

  const onContentChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setContent(e.target.value);
  };

  return (
    <div css={issueDetail(theme)}>
      <IssueDetailHeader
        issue={mockData.issue}
        commentCount={mockData.comments.length}
      />
      <div className="divider" />
      <main className="main">
        <ul className="comments">
          {mockData.comments.map((comment) => (
            <Comment key={comment.id} commentData={comment} />
          ))}
          <IssueContent
            value={content}
            onChange={onContentChange}
            height={80}
          />
          <Button
            color={theme.brand.textDefault}
            backgroundColor={theme.brand.surfaceDefault}
            value="코멘트 작성"
          >
            <PlusIcon />
          </Button>
        </ul>

        <SidebarDetail />
      </main>
    </div>
  );
}

const issueDetail = (theme: Theme) => css`
  display: flex;
  flex-direction: column;
  gap: 24px;
  width: 1280px;
  margin: 32px auto 0;

  .divider {
    width: 100%;
    height: 1px;
    background-color: ${theme.neutral.borderDefault};
  }

  .main {
    display: flex;
    justify-content: space-between;

    .comments {
      display: flex;
      flex-direction: column;
      gap: 24px;
      width: 960px;

      > button {
        margin-left: auto;
      }
    }
  }
`;
