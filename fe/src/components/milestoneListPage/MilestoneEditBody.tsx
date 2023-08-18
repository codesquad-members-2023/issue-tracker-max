import { css } from '@emotion/react';
import { TextInput } from '@components/common/textInput/TextInput';
import { isDateBeforeToday } from '@utils/time';

type Props = {
  nameInput: string;
  descriptionInput: string;
  deadlineInput: string;
  isNameLengthError?: boolean;
  onNameChange: (value: string) => void;
  onDescriptionChange: (value: string) => void;
  onDeadlineChange: (value: string) => void;
};

export const MilestoneEditBody: React.FC<Props> = ({
  nameInput,
  descriptionInput,
  deadlineInput,
  isNameLengthError,
  onNameChange,
  onDescriptionChange,
  onDeadlineChange,
}) => {
  return (
    <div css={milestoneEditBodyStyle}>
      <div
        className="milestone-edit-body__header"
        css={{
          display: 'flex',
          gap: '16px',
        }}
      >
        <TextInput
          value={nameInput}
          label="이름"
          inputType="text"
          placeholder="마일스톤의 이름을 입력하세요"
          onChange={onNameChange}
          isError={isNameLengthError}
          height={40}
        />
        <TextInput
          value={deadlineInput}
          label="완료일(선택)"
          inputType="date"
          placeholder="YYYY. MM. DD"
          onChange={onDeadlineChange}
          isError={isDateBeforeToday(deadlineInput)}
          caption="완료일은 오늘 이전으로 설정할 수 없습니다."
          height={40}
        />
      </div>
      <TextInput
        value={descriptionInput}
        label="설명(선택)"
        inputType="text"
        placeholder="마일스톤에 대한 설명을 입력하세요"
        onChange={onDescriptionChange}
        height={40}
      />
    </div>
  );
};

const milestoneEditBodyStyle = css`
width: '100%',
display: 'flex',
flexDirection: 'column',  
 
  .milestone-edit-body__header{
  display: flex;
  gap: 16px;

  }
`;
