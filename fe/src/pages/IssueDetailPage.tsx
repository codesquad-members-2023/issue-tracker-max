import { useState, useEffect } from 'react';
import { Body } from '@components/issueDetailPage/Body';
import { PostInformation } from '@components/issueDetailPage/PostInformation';
import { getIssueDetail } from 'apis/api';
import { useParams } from 'react-router-dom';

type Props = {};

export const IssueDetailPage: React.FC = ({}: Props) => {
  const [issueDetailPageData, setIssueDetailPageData] =
    useState<IssueDetailPageData>(initialPageData);
  const { id } = useParams<{ id?: string }>();

  const fetchLabelList = async () => {
    if (id) {
      const pageData = await getIssueDetail(id);
      setIssueDetailPageData(pageData);
    }
  };

  useEffect(() => {
    fetchLabelList();
  }, []);

  return (
    <div
      css={{
        width: '100%',
        display: 'flex',
        flexDirection: 'column',
        gap: '24px',
      }}
    >
      <PostInformation issueDetailPageData={issueDetailPageData} />
      <Body issueDetailPageData={issueDetailPageData} />
    </div>
  );
};

const initialPageData: IssueDetailPageData = {
  id: 0,
  title: '',
  contents: '',
  status: 'open',
  createdAt: '',
  author: {
    userId: 0,
    loginId: '',
    image: '',
  },
  assignees: [],
  labels: [],
  milestone: {
    id: 0,
    name: '',
    progress: 0,
  },
  comments: [],
};
