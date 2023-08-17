import { css, useTheme } from '@emotion/react';
import { useState, useRef } from 'react';
import { DropDownContainer } from '../dropDown/DropDownContainer';
import { DropDownPanel } from '../dropDown/DropDownPanel';
import { ListAssignee } from './ListAssignee';
import { ListLabel } from './ListLabel';
import { ListMilestone } from './ListMilestone';
import {
  getLabelPreviews,
  getMilestonePreviews,
  getUserPreviews,
} from 'apis/api';
import { DropDownList } from '../dropDown/DropDownList';

type FetchPath = 'users' | 'labels' | 'milestones';

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
  issueDetailPageData?: IssueDetailPageData;
  selections: SelectionState['newIssuePage'];
  onSingleSelectedMilestone: (id: number) => void;
  onMultipleSelectedAssignee: (id: number) => void;
  onMultipleSelectedLabel: (id: number) => void;
  onChangeSelect?: (key: string) => void;
};

export const ListSideBar: React.FC<Props> = ({
  issueDetailPageData,
  selections,
  onSingleSelectedMilestone,
  onMultipleSelectedAssignee,
  onMultipleSelectedLabel,
  onChangeSelect,
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

  //ref 리렌더가 필요x
  const isFetched = useRef({
    users: false,
    labels: false,
    milestones: false,
  });

  const openPanel = async (
    fetchDataFunction: () => Promise<
      UserData[] | LabelData[] | MilestoneData[]
    >,
    panelName: FetchPath,
  ) => {
    if (!isFetched.current[panelName]) {
      const data = await fetchDataFunction();

      setListData((prev) => ({
        ...prev,
        [panelName]: data,
      }));

      isFetched.current[panelName] = true;
    }

    setIsPanelOpen(panelName);
  };

  const closePanel = (key: string) => {
    setIsPanelOpen(null);

    if (onChangeSelect) {
      onChangeSelect(key);
    }
  };

  const assigneeOptions = listData.users
    .map((user) => ({ id: user.userId, ...user }))
    .slice(1);
  const labelOptions = listData.labels.slice(1);
  const milestoneOptions = listData.milestones.slice(1);

  // 패널 리스트를 페치하지 않은 상태일 때, 초기 값으로 선택되어있는 항목을 렌더링 데이터로 씀
  const selectedAssigneesData =
    listData.users.length !== 0
      ? listData.users
          .map((user) => ({ id: user.userId, ...user }))
          .filter((user) => selections.assignees.includes(user.id))
      : issueDetailPageData?.assignees;

  const selectedLabelsData =
    listData.labels.length !== 0
      ? labelOptions.filter((label) => selections.labels.includes(label.id))
      : issueDetailPageData?.labels;

  const selectedMilestonesData =
    listData.milestones.length !== 0
      ? milestoneOptions.find(
          (milestone) => selections.milestones === milestone.id,
        )
      : issueDetailPageData?.milestone;

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
          onClick={() => openPanel(getUserPreviews, 'users')}
        >
          <DropDownPanel
            panelHeader="담당자 설정"
            position="center"
            onOutsideClick={() => closePanel('assignees')}
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
        {selectedAssigneesData && (
          <ListAssignee selectedAssigneesData={selectedAssigneesData} />
        )}
      </div>
      <div css={commonStyles}>
        <DropDownContainer
          indicator="레이블"
          size="L"
          isPanelOpen={isPanelOpen === 'labels'}
          onClick={() => openPanel(getLabelPreviews, 'labels')}
        >
          <DropDownPanel
            panelHeader="레이블 설정"
            position="center"
            onOutsideClick={() => closePanel('labels')}
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
        {selectedLabelsData && (
          <ListLabel selectedLabelsData={selectedLabelsData} />
        )}
      </div>
      <div css={commonStyles}>
        <DropDownContainer
          indicator="마일스톤"
          size="L"
          isPanelOpen={isPanelOpen === 'milestones'}
          onClick={() => openPanel(getMilestonePreviews, 'milestones')}
        >
          <DropDownPanel
            panelHeader="마일스톤 설정"
            position="center"
            onOutsideClick={() => closePanel('milestones')}
          >
            {milestoneOptions?.map((item) => (
              <DropDownList
                key={item.id}
                item={item}
                onClick={() => onSingleSelectedMilestone(item.id)}
                isSelected={selections.milestones === item.id}
              />
            ))}
          </DropDownPanel>
        </DropDownContainer>
        {selectedMilestonesData && (
          <ListMilestone selectedMilestonesData={selectedMilestonesData} />
        )}
      </div>
    </>
  );
};
