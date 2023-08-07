import { useState } from 'react';
import { Theme, css, useTheme } from '@emotion/react';
import { border, radius } from '../../styles/styles';
import SubNavBar from '../SubNavbar';
import TableContainer from '../TableContainer';
import MilestoneFilter from './MilestoneFilter';
import MilestoneItem from './MilestoneItem';

const mockData = {
  success: 'true',
  data: {
    labelCount: 2,
    closedMilestoneCount: 0,
    milestones: [
      {
        id: 1,
        name: '프로젝트1',
        description: '4주 프로젝트 입니다',
        dueDate: '2023.08.07',
        openIssueCount: 2,
        closedIssueCount: 1,
      },
      {
        id: 2,
        name: '프로젝트2',
        description: '',
        dueDate: '',
        openIssueCount: 1,
        closedIssueCount: 0,
      },
    ],
  },
};

export default function MilestoneList() {
  const theme = useTheme();
  const [activeMilestone, setActiveMilestone] = useState<'open' | 'close'>(
    'open'
  );

  const onMilestoneFilterClick = (milestoneFilter: 'open' | 'close') => {
    setActiveMilestone(milestoneFilter);
  };

  return (
    <>
      <SubNavBar
        isIssue={false}
        labelCount={mockData.data.labelCount}
        milestoneCount={
          mockData.data.milestones.length + mockData.data.closedMilestoneCount
        }
        buttonValue="마일스톤 추가"
      />
      <TableContainer>
        <div css={milestoneTable(theme)}>
          <div className="header">
            <MilestoneFilter
              openMilestoneCount={mockData.data.milestones.length}
              closedMilestoneCount={mockData.data.closedMilestoneCount}
              filterState={activeMilestone}
              onClick={onMilestoneFilterClick}
            />
          </div>
          <ul className="item-container">
            {mockData.data.milestones.map((milestone) => (
              <MilestoneItem key={milestone.id} {...milestone} />
            ))}
          </ul>
        </div>
      </TableContainer>
    </>
  );
}

const milestoneTable = (theme: Theme) => css`
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
