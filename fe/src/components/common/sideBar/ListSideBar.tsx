import { css, useTheme } from '@emotion/react';
import { useState, useEffect } from 'react';
import { DropDownIndicator } from '../dropDown/DropDownIndicator';
import { DropDownPanel } from '../dropDown/DropDownPanel';
import { ListAssignee } from './ListAssignee';
import { ListLabel } from './ListLabel';
import { ListMilestone } from './ListMilestone';

type FetchPath = 'users' | 'labels' | 'milestones';
type Indicator = '담당자' | '레이블' | '마일스톤';

type UserData = {
  userId: number;
  loginId: string;
  image: string;
};

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
  selectedAssignees: { [key: number]: boolean };
  selectedLabels: { [key: number]: boolean };
  selectedMilestones: { [key: number]: boolean };
  onSingleSelectedMilestone: (id: number) => void;
  onMultipleSelectedAssignee: (id: number) => void;
  onMultipleSelectedLabel: (id: number) => void;
};

export const ListSideBar: React.FC<Props> = ({
  selectedAssignees,
  selectedLabels,
  selectedMilestones,
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

  const [isFetched, setIsFetched] = useState({
    users: false,
    labels: false,
    milestones: false,
  });

  const indicatorMapping: Record<Indicator, FetchPath> = {
    담당자: 'users',
    레이블: 'labels',
    마일스톤: 'milestones',
  };

  const onFetchData = async (indicator: Indicator) => {
    const path = indicatorMapping[indicator];

    try {
      if (isFetched[path]) return;

      const response = await fetch(
        `https://cb8d8d5e-a994-4e94-b386-9971124d22e2.mock.pstmn.io/${path}/previews`,
        {
          method: 'GET',
        },
      );

      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      const data = await response.json();

      setListData((prev) => ({
        ...prev,
        [path]: data,
      }));

      setIsFetched((prev) => ({
        ...prev,
        [path]: true,
      }));

      return data;
    } catch (error) {
      console.error(`There was a problem with the fetch operation: ${error}`);
    }
  };

  const modifiedUserData = listData.users.map((item) => {
    const { userId, ...rest } = item;
    return { id: userId, ...rest };
  });
  const assigneeOptions = modifiedUserData.slice(1);
  const labelOptions = listData.labels.slice(1);
  const milestoneOptions = listData.milestones.slice(1);

  const selectedAssigneeIds = Object.keys(selectedAssignees)
    .filter((id) => selectedAssignees[parseInt(id)])
    .map((id) => parseInt(id));

  const selectedAssigneesData = modifiedUserData.filter((users) =>
    selectedAssigneeIds.includes(users.id),
  );

  const selectedLabelIds = Object.keys(selectedLabels)
    .filter((id) => selectedLabels[parseInt(id)])
    .map((id) => parseInt(id));

  const selectedLabelsData = labelOptions.filter((label) =>
    selectedLabelIds.includes(label.id),
  );

  const selectedMilestoneIds = Object.keys(selectedMilestones)
    .filter((id) => selectedMilestones[parseInt(id)])
    .map((id) => parseInt(id));

  const selectedMilestonesData = milestoneOptions.filter((milestone) =>
    selectedMilestoneIds.includes(milestone.id),
  );

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
        <DropDownIndicator
          indicator="담당자"
          size="L"
          onFetchData={onFetchData}
        >
          <DropDownPanel
            panelHeader="담당자 설정"
            alignment="center"
            options={assigneeOptions}
            onSelected={onMultipleSelectedAssignee}
            selectedItems={selectedAssignees}
          />
        </DropDownIndicator>
        <ListAssignee selectedAssigneesData={selectedAssigneesData} />
      </div>
      <div css={commonStyles}>
        <DropDownIndicator
          indicator="레이블"
          size="L"
          onFetchData={onFetchData}
        >
          <DropDownPanel
            panelHeader="레이블 설정"
            alignment="center"
            options={labelOptions}
            onSelected={onMultipleSelectedLabel}
            selectedItems={selectedLabels}
          />
        </DropDownIndicator>
        <ListLabel selectedLabelsData={selectedLabelsData} />
      </div>
      <div css={commonStyles}>
        <DropDownIndicator
          indicator="마일스톤"
          size="L"
          onFetchData={onFetchData}
        >
          <DropDownPanel
            panelHeader="마일스톤 설정"
            alignment="center"
            options={milestoneOptions}
            onSelected={onSingleSelectedMilestone}
            selectedItems={selectedMilestones}
          />
        </DropDownIndicator>
        <ListMilestone selectedMilestonesData={selectedMilestonesData} />
      </div>
    </>
  );
};
