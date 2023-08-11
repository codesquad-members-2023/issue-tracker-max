import { css, useTheme } from '@emotion/react';
import { useState } from 'react';
import { DropDownContainer } from '../dropDown/DropDownContainer';
import { DropDownPanel } from '../dropDown/DropDownPanel';
import { ListAssignee } from './ListAssignee';
import { ListLabel } from './ListLabel';
import { ListMilestone } from './ListMilestone';
import { getLabels, getMilestones, getUsers } from '@utils/api';
import { DropDownList } from '../dropDown/DropDownList';

type SelectionState = {
  assignees: number[];
  labels: number[];
  milestones: number[];
};

type FetchPath = 'users' | 'labels' | 'milestones';

type UserData = {
  userId: number;
  loginId: string;
  image: string;
};

// type ModifiedUserData = {
//   id: number;
//   loginId: string;
//   image: string;
// };

type LabelData = {
  id: number;
  name: string;
  backgroundColor: string;
  textColor: string;
};

type MilestoneData = {
  id: number;
  name: string;
  progress: number;
};

type Props = {
  selections: SelectionState;
  onSingleSelectedMilestone: (id: number) => void;
  onMultipleSelectedAssignee: (id: number) => void;
  onMultipleSelectedLabel: (id: number) => void;
};

export const ListSideBar: React.FC<Props> = ({
  selections,
  onSingleSelectedMilestone,
  onMultipleSelectedAssignee,
  onMultipleSelectedLabel,
}) => {
  const theme = useTheme() as any;

  const [listData, setListData] = useState<{
    users: UserData[];
    labels: LabelData[];
    milestones: MilestoneData[];
  }>({
    users: [],
    labels: [],
    milestones: [],
  });

  const [isPanelOpen, setIsPanelOpen] = useState<
    null | 'users' | 'labels' | 'milestones'
  >(null);

  const [isFetched, setIsFetched] = useState({
    users: false,
    labels: false,
    milestones: false,
  });

  const modifiedUserData = listData.users.map((item) => {
    const { userId, ...rest } = item;
    return { id: userId, ...rest };
  });

  const assigneeOptions = modifiedUserData.slice(1);
  const labelOptions = listData.labels.slice(1);
  const milestoneOptions = listData.milestones.slice(1);
  const selectedAssigneesData = modifiedUserData.filter((users) =>
    selections.assignees.includes(users.id),
  );

  const selectedLabelsData = labelOptions.filter((label) =>
    selections.labels.includes(label.id),
  );

  const selectedMilestonesData = milestoneOptions.filter((milestone) =>
    selections.milestones.includes(milestone.id),
  );

  const openPanel = async (
    fetchDataFunction: () => Promise<
      UserData[] | LabelData[] | MilestoneData[]
    >,
    panelName: FetchPath,
  ) => {
    if (!isFetched[panelName]) {
      const data = await fetchDataFunction();

      setListData((prev) => ({
        ...prev,
        [panelName]: data,
      }));

      setIsFetched((prev) => ({
        ...prev,
        [panelName]: true,
      }));
    }

    setIsPanelOpen(panelName);
  };

  const closePanel = () => {
    setIsPanelOpen(null);
  };

  const commonStyles = css`
    display: flex;
    flex-direction: column;
    gap: 16px;
    padding: 32px;
    borderbottom: ${theme.border.default} ${theme.neutral.border.default};
    &:last-child {
      borderbottom: none;
    }
  `;

  return (
    <>
      <div css={commonStyles}>
        <DropDownContainer
          indicator="담당자"
          size="L"
          isPanelOpen={isPanelOpen === 'users'}
          onClick={() => openPanel(getUsers, 'users')}
        >
          <DropDownPanel
            panelHeader="담당자 설정"
            position="center"
            onOutsideClick={closePanel}
          >
            {assigneeOptions?.map((item) => (
              <DropDownList
                key={item.id}
                item={item}
                onClick={() => onMultipleSelectedAssignee(item.id)}
                isSelected={selections.assignees.includes(item.id)}
              />
            ))}
          </DropDownPanel>
        </DropDownContainer>
        <ListAssignee selectedAssigneesData={selectedAssigneesData} />
      </div>
      <div css={commonStyles}>
        <DropDownContainer
          indicator="레이블"
          size="L"
          isPanelOpen={isPanelOpen === 'labels'}
          onClick={() => openPanel(getLabels, 'labels')}
        >
          <DropDownPanel
            panelHeader="레이블 설정"
            position="center"
            onOutsideClick={closePanel}
          >
            {labelOptions?.map((item) => (
              <DropDownList
                key={item.id}
                item={item}
                onClick={() => onMultipleSelectedLabel(item.id)}
                isSelected={selections.labels.includes(item.id)}
              />
            ))}
          </DropDownPanel>
        </DropDownContainer>
        <ListLabel selectedLabelsData={selectedLabelsData} />
      </div>
      <div css={commonStyles}>
        <DropDownContainer
          indicator="마일스톤"
          size="L"
          isPanelOpen={isPanelOpen === 'milestones'}
          onClick={() => openPanel(getMilestones, 'milestones')}
        >
          <DropDownPanel
            panelHeader="마일스톤 설정"
            position="center"
            onOutsideClick={closePanel}
          >
            {milestoneOptions?.map((item) => (
              <DropDownList
                key={item.id}
                item={item}
                onClick={() => onSingleSelectedMilestone(item.id)}
                isSelected={selections.milestones.includes(item.id)}
              />
            ))}
          </DropDownPanel>
        </DropDownContainer>
        <ListMilestone selectedMilestonesData={selectedMilestonesData} />
      </div>
    </>
  );
};
