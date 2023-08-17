import { css, useTheme } from '@emotion/react';
import { useState } from 'react';
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
  selections: SelectionState['newIssuePage'];
  onSingleSelectedMilestone: (id: number) => void;
  onMultipleSelectedAssignee: (id: number) => void;
  onMultipleSelectedLabel: (id: number) => void;
  onChangeSelect?: (key: string) => void;
  selectionsOptions?: SelectionState['detailPage'];
};

export const ListSideBar: React.FC<Props> = ({
  selections,
  onSingleSelectedMilestone,
  onMultipleSelectedAssignee,
  onMultipleSelectedLabel,
  onChangeSelect,
  selectionsOptions,
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
  console.log(selections);

  const [isPanelOpen, setIsPanelOpen] = useState<
    null | 'users' | 'labels' | 'milestones'
  >(null);

  // todo
  const [isFetched, setIsFetched] = useState({
    //ref 리렌더가 필요x
    users: false,
    labels: false,
    milestones: false,
  });

  const modifiedUserData = listData.users.map((item) => {
    const { userId, ...rest } = item;
    return { id: userId, ...rest };
  });

  const modifiedAssigneesData = selectionsOptions?.assignees.map((item) => {
    const { userId, ...rest } = item;
    return { id: userId, ...rest };
  });

  const assigneeOptions = modifiedUserData.slice(1);
  const labelOptions = listData.labels.slice(1);
  const milestoneOptions = listData.milestones.slice(1);

  const selectedAssigneesData =
    listData.users.length !== 0
      ? modifiedUserData.filter((users) =>
          selections.assignees.includes(users.id),
        )
      : modifiedAssigneesData;

  const selectedLabelsData =
    listData.labels.length !== 0
      ? labelOptions.filter((label) => selections.labels.includes(label.id))
      : selectionsOptions?.labels;

  const selectedMilestonesData =
    listData.milestones.length !== 0
      ? milestoneOptions.find(
          (milestone) => selections.milestones === milestone.id,
        )
      : selectionsOptions?.milestones;

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

  const closePanel = (key: string) => {
    setIsPanelOpen(null);

    if (onChangeSelect) {
      onChangeSelect(key);
    }
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
