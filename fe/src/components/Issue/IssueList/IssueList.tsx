import { useState } from 'react';
import { Theme, css, useTheme } from '@emotion/react';
import { border, radius } from '../../../styles/styles';
import IssueItem from './IssueItem';
import TableContainer from '../../TableContainer';
import SubNavBar from '../../SubNavbar';
import IssueFilter from './IssueFilter';

const mockData = {
  success: 'true',
  data: {
    labelCount: 2,
    milestoneCount: 2,
    openIssueCount: 2,
    closedIssueCount: 0,
    issues: [
      {
        id: 1,
        status: 'open',
        title: '이슈1',
        history: {
          modifier: 'June',
          dateTime: '2023-07-22 12:01',
        },
        labels: [
          {
            id: 1,
            name: 'Backend',
            description: '백엔드',
            textColor: '#6E7191',
            backgroundColor: '#FEFEFE',
          },
        ],
        number: '#1',
        writer: 'June',
        assignees: [
          {
            id: 2,
            name: 'Movie',
          },
        ],
        milestone: '프로젝트1',
      },
      {
        id: 2,
        status: 'open',
        title: '이슈2',
        history: {
          modifier: 'Movie',
          dateTime: '2023-07-23 12:01',
        },
        labels: [
          {
            id: 2,
            name: 'Frontend',
            description: '프론트엔드',
            textColor: '#FEFEFE',
            backgroundColor: '#0025E6',
          },
        ],
        number: '#2',
        writer: 'Movie',
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
        milestone: '',
      },
    ],
  },
};

export default function IssueList() {
  const theme = useTheme();
  const [activeIssue, setActiveIssue] = useState<'open' | 'close'>('open');
  const [checkedItemIdList, setCheckedItemIdList] = useState<number[]>([]);

  const allItemIdList = mockData.data.issues.map((item) => item.id);
  const isAllItemChecked = allItemIdList.length === checkedItemIdList.length;

  const onIssueFilterClick = (issueFilter: 'open' | 'close') => {
    setActiveIssue(issueFilter);
  };

  const onSingleCheck = (checked: boolean, id: number) => {
    if (checked) {
      setCheckedItemIdList((prev) => [...prev, id]);
    } else {
      setCheckedItemIdList(checkedItemIdList.filter((itemId) => itemId !== id));
    }
  };

  const onAllCheck = (checked: boolean) => {
    if (checked) {
      setCheckedItemIdList(allItemIdList);
    } else {
      setCheckedItemIdList([]);
    }
  };

  return (
    <>
      <SubNavBar
        isIssue
        labelCount={mockData.data.labelCount}
        milestoneCount={mockData.data.milestoneCount}
        buttonValue="이슈 작성"
      />
      <TableContainer>
        <div css={issueTable(theme)}>
          <div className="header">
            <IssueFilter
              activeIssue={activeIssue}
              onCheckBoxClick={onAllCheck}
              isAllItemChecked={isAllItemChecked}
              onIssueFilterClick={onIssueFilterClick}
              openIssueCount={mockData.data.openIssueCount}
              closedIssueCount={mockData.data.closedIssueCount}
              checkedItemLength={checkedItemIdList.length}
            />
          </div>
          <ul className="item-container">
            {mockData.data.issues.map((item) => {
              return (
                <IssueItem
                  key={item.id}
                  issue={item}
                  onSingleCheck={onSingleCheck}
                  checkedItemIdList={checkedItemIdList}
                />
              );
            })}
          </ul>
        </div>
      </TableContainer>
    </>
  );
}

const issueTable = (theme: Theme) => css`
  display: flex;
  flex-direction: column;
  border-radius: ${radius.medium};
  border: ${border.default} ${theme.neutral.borderDefault};
  color: ${theme.neutral.textDefault};

  .item-container {
    display: flex;
    flex-direction: column;
    border-radius: 0 0 ${radius.medium} ${radius.medium};
    background-color: ${theme.neutral.surfaceStrong};

    li {
      box-sizing: border-box;
      padding: 0 32px;
      border-bottom: ${border.default} ${theme.neutral.borderDefault};

      &:last-child {
        border-bottom: none;
        border-radius: 0 0 ${radius.medium} ${radius.medium};
      }
    }
  }
`;
