import { css, useTheme } from '@emotion/react';
import { useState, useEffect } from 'react';
import { DropDownIndicator } from '../dropDown/DropDownIndicator';
import { DropDownPanel } from '../dropDown/DropDownPanel';
import { ListAssignee } from './ListAssignee';
import { ListLabel } from './ListLabel';
import { ListMilestone } from './ListMilestone';

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
  onSingleSelectedMilestone: (index: number) => void;
  onMultipleSelectedAssignee: (index: number) => void;
  onMultipleSelectedLabel: (index: number) => void;
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
  // 비슷한 자료 형태라 묶어주는 작업이 필요할듯

  const [listData, setListData] = useState<{
    users: UserData[];
    labels: LabelData[];
    milestones: MilestoneData[];
  }>({
    users: [],
    labels: [],
    milestones: [],
  });

  const onFetchData = async (path: string) => {
    // todo 데이터가 같으면 페치하지 않기
    try {
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

      return data;
    } catch (error) {
      console.error(`There was a problem with the fetch operation: ${error}`);
    }
  };

  const assigneeOptions = listData.users.slice(1);
  const labelOptions = listData.labels.slice(1);
  const milestoneOptions = listData.milestones.slice(1);

  const selectedAssigneeIds = Object.keys(selectedAssignees)
    .filter((id) => selectedAssignees[parseInt(id)])
    .map((id) => parseInt(id) + 1);

  const selectedAssigneesData = assigneeOptions.filter((users) =>
    selectedAssigneeIds.includes(users.userId),
  );

  const selectedLabelIds = Object.keys(selectedLabels)
    .filter((id) => selectedLabels[parseInt(id)])
    .map((id) => parseInt(id) + 1);

  const selectedLabelsData = labelOptions.filter((label) =>
    selectedLabelIds.includes(label.id),
  );

  const selectedMilestoneIds = Object.keys(selectedMilestones)
    .filter((id) => selectedMilestones[parseInt(id)])
    .map((id) => parseInt(id) + 1);

  const selectedMilestonesData = milestoneOptions.filter((milestone) =>
    selectedMilestoneIds.includes(milestone.id),
  );

  // 페이지에서 기능 붙이면서 문제 없으면 배열로 만들어서 렌더링하는 방식으로 바꿔보기
  return (
    <>
      <div
        css={{
          display: 'flex',
          flexDirection: 'column',
          gap: '16px',
          padding: '32px',
          borderBottom: `${theme.border.default} ${theme.neutral.border.default}`,
          '&:last-child': {
            borderBottom: 'none',
          },
        }}
      >
        <DropDownIndicator
          indicator="담당자"
          size="L"
          onFetchData={onFetchData}
          fetchPath="users"
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
      <div
        css={{
          display: 'flex',
          flexDirection: 'column',
          gap: '16px',
          padding: '32px',
          borderBottom: `${theme.border.default} ${theme.neutral.border.default}`,
          '&:last-child': {
            borderBottom: 'none',
          },
        }}
      >
        <DropDownIndicator
          indicator="레이블"
          size="L"
          onFetchData={onFetchData}
          fetchPath="labels"
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
      <div
        css={{
          display: 'flex',
          flexDirection: 'column',
          gap: '16px',
          padding: '32px',
          borderBottom: `${theme.border.default} ${theme.neutral.border.default}`,
          '&:last-child': {
            borderBottom: 'none',
          },
        }}
      >
        <DropDownIndicator
          indicator="마일스톤"
          size="L"
          onFetchData={onFetchData}
          fetchPath="milestones"
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
