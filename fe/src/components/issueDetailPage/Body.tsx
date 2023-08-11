import { useTheme } from '@emotion/react';
import { SideBarRightPanel } from './SideBarRightPanel';
import { CommentContainer } from './CommentContainer';
type Props = {};

const mockData = {
  id: 1,
  title: '이슈 제목',
  contents: '이슈 내용',
  status: 'open',
  createdAt: '2023-07-26T02:05:00',
  author: {
    userId: 1,
    loginId: 'hana1234',
    image: '이미지url',
  },
  assignees: [
    {
      userId: 2,
      loginId: 'bono1234',
      image: '이미지url',
    },
    {
      userId: 3,
      loginId: 'jian1234',
      image: '이미지url',
    },
  ],
  labels: [
    {
      id: 1,
      name: 'feat',
      textColor: 'light',
      backgroundColor: '#FF0000',
    },
    {
      id: 3,
      name: 'fix',
      textColor: 'dark',
      backgroundColor: '#800000',
    },
  ],
  milestone: {
    id: 1,
    name: '마일스톤1',
    progress: 34,
  },
  comments: [
    {
      id: 1,
      author: {
        userId: 4,
        loginId: 'ayaan1234',
        image: '이미지url',
      },
      contents: '코멘트 내용',
      createdAt: '2023-07-26T02:13:00',
    },
    {
      id: 2,
      author: {
        userId: 3,
        loginId: 'jian1234',
        image: '이미지url',
      },
      contents: '코멘트 내용',
      createdAt: '2023-07-26T02:13:30',
    },
  ],
};

export const Body: React.FC = ({}: Props) => {
  const theme = useTheme() as any;

  return (
    <div
      css={{
        width: '100%',
        display: 'flex',
        gap: '32px',
        borderTop: `${theme.border.default} ${theme.neutral.border.default}`,
        paddingTop: '24px',
      }}
    >
      <CommentContainer
        contents={mockData.contents}
        createdAt={mockData.createdAt}
        author={mockData.author}
        comments={mockData.comments}
      />
      <SideBarRightPanel />
    </div>
  );
};
