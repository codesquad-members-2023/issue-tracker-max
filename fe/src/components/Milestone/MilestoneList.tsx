import { useEffect, useState } from 'react';
import { Theme, css, useTheme } from '@emotion/react';
import { border, radius } from '../../styles/styles';
import SubNavBar from '../SubNavbar';
import TableContainer from '../TableContainer';
import MilestoneFilter from './MilestoneFilter';
import MilestoneItem from './MilestoneItem';
import { customFetch } from '../../util/customFetch';
import { useNavigate } from 'react-router-dom';
import { MilestoneData, MilestoneType } from '../../type/milestone.type';
import { GetMilestoneRes } from '../../type/Response.type';
import MilestoneCreate from './MilestoneCreate/MilestoneCreate';

export default function MilestoneList() {
  const theme = useTheme();
  const [milestoneFilter, setMilestoneFilter] = useState<'open' | 'closed'>(
    'open'
  );
  const [milestoneList, setMilestoneList] = useState<MilestoneData>();
  const [isCreating, setIsCreating] = useState<boolean>(false);
  const navigate = useNavigate();

  useEffect(() => {
    (async () => {
      try {
        const response = await customFetch<GetMilestoneRes>({
          subUrl: 'api/milestones/' + milestoneFilter,
        });

        if (!response.success) {
          if (response.errorCode?.status === 401) {
            navigate('/sign-in');
            return;
          }
        }

        if (response.data) {
          setMilestoneList(response.data);
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

  const addNewMilestone = (newMilestone: MilestoneType) => {
    setMilestoneList((prev) => {
      if (prev) {
        return {
          ...prev,
          milestones: [...prev.milestones, { ...newMilestone }],
        };
      }
    });
  };

  const editLabel = (milestoneId: number, editedMilestone: MilestoneType) => {
    setMilestoneList((prev) => {
      if (prev) {
        return {
          ...prev,
          milestones: prev.milestones.map((milestone) => {
            if (milestone.id === milestoneId) {
              return { ...editedMilestone };
            }
            return milestone;
          }),
        };
      }
    });
  };

  const deleteMilestone = (milestoneId: number) => {
    setMilestoneList((prev) => {
      if (prev) {
        return {
          ...prev,
          milestones: prev.milestones.filter(
            (milestone) => milestone.id !== milestoneId
          ),
        };
      }
    });
  };

  const onClickToCreate = () => {
    setIsCreating((prev) => !prev);
  };

  const isOpen = milestoneFilter === 'open';

  return (
    <>
      {milestoneList && (
        <>
          <SubNavBar
            labelCount={milestoneList.labelCount}
            milestoneCount={
              milestoneList.milestones.length + milestoneList.oppositeCount
            }
            buttonValue="마일스톤 추가"
            onClick={onClickToCreate}
          />
          {isCreating && (
            <MilestoneCreate
              onChangeStatus={onClickToCreate}
              onAddNewMilestone={addNewMilestone}
            />
          )}
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
                  <MilestoneItem
                    key={milestone.id}
                    milestone={milestone}
                    onDelete={deleteMilestone}
                    onEdit={editLabel}
                  />
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
    overflow: hidden;

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
