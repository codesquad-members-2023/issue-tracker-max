import { Theme, css, useTheme } from '@emotion/react';
import Button from '../../common/Button';
import { border, font, radius } from '../../../styles/styles';
import { ReactComponent as XSquareIcon } from '../../../assets/icon/xSquare.svg';
import { ReactComponent as PlusIcon } from '../../../assets/icon/plus.svg';
import { customFetch } from '../../../util/customFetch';
import { useState } from 'react';
import MilestoneInputForm from '../MilestoneInputForm';
import { MilestoneType } from '../../../type/milestone.type';
import { PostNewMilestoneRes } from '../../../type/Response.type';

type Props = {
  onChangeStatus: () => void;
  onAddNewMilestone: (newMilestone: MilestoneType) => void;
};

export default function MilestoneCreate({
  onChangeStatus,
  onAddNewMilestone,
}: Props) {
  const theme = useTheme();
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [dueDate, setDueDate] = useState('');

  const onPostNewMilestone = async () => {
    const formattedDueDate = new Date(dueDate);
    formattedDueDate.setHours(23, 59, 59, 999);

    try {
      const response = await customFetch<PostNewMilestoneRes>({
        method: 'POST',
        subUrl: 'api/milestones',
        body: JSON.stringify({
          title: title,
          description: description,
          dueDate: formattedDueDate,
        }),
      });

      if (response.success) {
        if (response.data) {
          onAddNewMilestone({
            id: response.data.id,
            openIssueCount: 0,
            closedIssueCount: 0,
            title: title,
            description: description,
            dueDate: dueDate,
          });
          onChangeStatus();
        }
      }
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div css={milestoneCreate(theme)}>
      <div className="title">새로운 마일스톤 추가</div>
      <MilestoneInputForm
        title={title}
        description={description}
        dueDate={dueDate}
        onChangeTitle={setTitle}
        onChangeDescription={setDescription}
        onChangeDueDate={setDueDate}
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
          onClick={onPostNewMilestone}
          value="완료"
          icon={<PlusIcon />}
        />
      </div>
    </div>
  );
}

const milestoneCreate = (theme: Theme) => css`
  width: 1280px;
  margin: 0 auto 24px;
  display: flex;
  flex-direction: column;
  gap: 22px;
  padding: 32px;
  background-color: ${theme.neutral.surfaceStrong};
  box-sizing: border-box;
  border-radius: ${radius.large};
  border: ${border.default} ${theme.neutral.borderDefault};

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
