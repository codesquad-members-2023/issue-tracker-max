import { useEffect, useState } from 'react';
import { Theme, css, useTheme } from '@emotion/react';
import { border, radius } from '../../styles/styles';
import SubNavBar from '../SubNavbar';
import TableContainer from '../TableContainer';
import MilestoneFilter from './MilestoneFilter';
import MilestoneItem from './MilestoneItem';
import { customFetch } from '../../util/customFetch';
import { useNavigate } from 'react-router-dom';

export default function MilestoneList() {
  const theme = useTheme();
  const [milestoneFilter, setMilestoneFilter] = useState<'open' | 'closed'>(
    'open'
  );
  const [milestoneList, setMilestoneList] = useState<MilestoneData>();
  const navigate = useNavigate();

  useEffect(() => {
    (async () => {
      const subUrl = 'api/milestones/' + milestoneFilter;

      try {
        const milestoneData = await customFetch<MilestoneResponse>({ subUrl });

        if (milestoneData.success && milestoneData.data) {
          setMilestoneList(milestoneData.data);
        }
      } catch (error) {
        navigate('/sign-in');
      }
    })();
  }, [milestoneFilter]);

  const onMilestoneFilterClick = () => {
    switch (milestoneFilter) {
      case 'open':
        setMilestoneFilter('closed');
        break;
      case 'closed':
        setMilestoneFilter('open');
        break;
    }
  };

  const onClickToCreate = () => {};

  const isOpen = milestoneFilter === 'open';

  return (
    <>
      {milestoneList && (
        <>
          <SubNavBar
            isIssue={false}
            labelCount={milestoneList.labelCount}
            milestoneCount={
              milestoneList.milestones.length + milestoneList.oppositeCount
            }
            buttonValue="마일스톤 추가"
            onClick={onClickToCreate}
          />
          <TableContainer>
            <div css={milestoneTable(theme)}>
              <div className="header">
                <MilestoneFilter
                  openMilestoneCount={
                    isOpen
                      ? milestoneList.milestones.length
                      : milestoneList.oppositeCount
                  }
                  closedMilestoneCount={
                    !isOpen
                      ? milestoneList.milestones.length
                      : milestoneList.oppositeCount
                  }
                  filterState={milestoneFilter}
                  onMilestoneFilterClick={onMilestoneFilterClick}
                />
              </div>
              <ul className="item-container">
                {milestoneList.milestones.map((milestone) => (
                  <MilestoneItem key={milestone.id} {...milestone} />
                ))}
              </ul>
            </div>
          </TableContainer>
        </>
      )}
    </>
  );
}

const milestoneTable = (theme: Theme) => css`
  display: flex;
  flex-direction: column;
  border-radius: ${radius.medium};
  color: ${theme.neutral.textDefault};

  .item-container {
    display: flex;
    flex-direction: column;
    border-radius: 0 0 ${radius.medium} ${radius.medium};
    border: ${border.default} ${theme.neutral.borderDefault};
    border-top: none;
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
