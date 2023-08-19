import { Theme, css, useTheme } from '@emotion/react';
import Button from '../../common/Button';
import { border, font } from '../../../styles/styles';
import { ReactComponent as XSquareIcon } from '../../../assets/icon/xSquare.svg';
import { ReactComponent as PlusIcon } from '../../../assets/icon/plus.svg';
import { customFetch } from '../../../util/customFetch';
import { useState } from 'react';
import MilestoneInputForm from '../MilestoneInputForm';
import { MilestoneType } from '../../../type/milestone.type';
import { OnlySuccessRes } from '../../../type/Response.type';

type Props = {
  milestone: MilestoneType;
  onChangeStatus: () => void;
  onEditMilestone: (
    milestoneId: number,
    editedMilestone: MilestoneType
  ) => void;
};

export default function MilestoneEdit({
  milestone,
  onChangeStatus,
  onEditMilestone,
}: Props) {
  const theme = useTheme();
  const [title, setTitle] = useState(milestone.title);
  const [description, setDescription] = useState(
    (milestone.description ??= '')
  );
  const [dueDate, setDueDate] = useState((milestone.dueDate ??= ''));

  const onPutEditedMilestone = async () => {
    const formattedDueDate = new Date(dueDate);
    formattedDueDate.setHours(23, 59, 59, 999);

    try {
      const response = await customFetch<OnlySuccessRes>({
        method: 'PUT',
        subUrl: `api/milestones/${milestone.id}`,
        body: JSON.stringify({
          title: title,
          description: description,
          dueDate: formattedDueDate,
        }),
      });

      if (response.success) {
        onEditMilestone(milestone.id, {
          id: milestone.id,
          openIssueCount: milestone.openIssueCount,
          closedIssueCount: milestone.closedIssueCount,
          title: title,
          description: description,
          dueDate: dueDate,
        });
        onChangeStatus();
      }
    } catch (error) {
      console.error(error);
    }
  };

  const onChangeTitle = (title: string) => {
    setTitle(title);
  };

  const onChangeDescription = (description: string) => {
    setDescription(description);
  };

  const onChangeDueDate = (dueDate: string) => {
    setDueDate(dueDate);
  };

  return (
    <div css={milestoneCreate(theme)}>
      <div className="title">새로운 마일스톤 추가</div>
      <MilestoneInputForm
        title={title}
        description={description}
        dueDate={dueDate}
        onChangeTitle={onChangeTitle}
        onChangeDescription={onChangeDescription}
        onChangeDueDate={onChangeDueDate}
      />
      <div className="buttons">
        <Button
          color={theme.brand.textWeak}
          border={`${border.default} ${theme.brand.borderDefault}`}
          onClick={onChangeStatus}
          value="취소"
          icon={<XSquareIcon />}
        />
        <Button
          color={theme.brand.textDefault}
          backgroundColor={theme.brand.surfaceDefault}
          onClick={onPutEditedMilestone}
          value="편집 완료"
          icon={<PlusIcon />}
        />
      </div>
    </div>
  );
}

const milestoneCreate = (theme: Theme) => css`
  width: 1280px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  gap: 22px;
  padding: 32px;
  background-color: ${theme.neutral.surfaceStrong};
  box-sizing: border-box;
  border-bottom: ${border.default} ${theme.neutral.borderDefault};
  border-right: ${border.default} ${theme.neutral.borderDefault};

  .title {
    font: ${font.displayBold20};
    color: ${theme.neutral.textStrong};
  }

  .buttons {
    margin-left: auto;
    display: flex;
    gap: 24px;
  }
`;
