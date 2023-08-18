import { useTheme } from '@emotion/react';
import { useState } from 'react';
import { Button } from '@components/common/Button';
import { ReactComponent as XSquare } from '@assets/icons/xSquare.svg';
import { ReactComponent as Plus } from '@assets/icons/plus.svg';
import { ReactComponent as Edit } from '@assets/icons/edit.svg';
import { editMilestone, postNewMilestone } from 'apis/api';
import { MilestoneEditBody } from './MilestoneEditBody';
import { isDateBeforeToday } from '@utils/time';

type Props = {
  milestone?: Milestone;
  typeVariant: 'add' | 'edit';
  header: React.ReactNode;
  onAddTableClose: () => void;
  fetchPageData: () => Promise<void>;
};

export const MilestoneEditTable: React.FC<Props> = ({
  milestone,
  typeVariant,
  header,
  onAddTableClose,
  fetchPageData,
}) => {
  const theme = useTheme() as any;
  const [nameInput, setNameInput] = useState<string>(milestone?.name ?? '');
  const [descriptionInput, setDescriptionInput] = useState<string>(
    milestone?.description ?? '',
  );
  const [deadlineInput, setDeadlineInput] = useState<string>(
    milestone?.deadline ?? '',
  );
  const trimedName = nameInput.trim();

  const onSubmit = async () => {
    const formattedDeadline = formatDeadline(deadlineInput);

    try {
      if (typeVariant === 'edit' && milestone?.id) {
        await editMilestone(
          milestone.id,
          nameInput,
          formattedDeadline || '',
          descriptionInput || '',
        );
      } else {
        await postNewMilestone(
          nameInput,
          formattedDeadline || '',
          descriptionInput || '',
        );
      }
      onAddTableClose();
      await fetchPageData();
    } catch (error) {
      console.error('There was a problem with the fetch operation:');
      // todo 에러처리
    }
  };

  const onNameChange = (value: string) => {
    if (value.length > 21) return;
    setNameInput(value);
  };

  const onDescriptionChange = (value: string) => {
    setDescriptionInput(value);
  };

  const onDeadlineChange = (value: string) => {
    setDeadlineInput(value);
  };

  const formatDeadline = (deadline: string) => {
    if (deadline) {
      const date = new Date(deadline);
      return date.toISOString().split('T')[0] + 'T00:00:00';
    }
  };

  const isDataMissing = !nameInput;

  const isDataUnchanged =
    nameInput === (milestone ? milestone.name : '') &&
    descriptionInput === (milestone ? milestone.description : '') &&
    deadlineInput === (milestone ? milestone.deadline : '');

  const isNameLengthError = nameInput.length > 70;

  const addTypeStyle = {
    border: `${theme.border.default}  ${theme.neutral.border.default}`,
    borderRadius: theme.radius.l,
    padding: '32px',
  };

  return (
    <div
      css={{
        width: '100%',
        boxSizing: 'border-box',
        height: 'fit-content',
        display: 'flex',
        backgroundColor: theme.neutral.surface.strong,
        flexDirection: 'column',
        gap: '24px',
        ...(typeVariant === 'add' ? addTypeStyle : {}),
      }}
    >
      {header}
      <MilestoneEditBody
        nameInput={trimedName}
        descriptionInput={descriptionInput}
        deadlineInput={deadlineInput}
        isNameLengthError={isNameLengthError}
        onNameChange={onNameChange}
        onDescriptionChange={onDescriptionChange}
        onDeadlineChange={onDeadlineChange}
      />

      <div
        css={{
          display: 'flex',
          gap: '16px',
          justifyContent: 'flex-end',
        }}
      >
        <Button
          typeVariant="outline"
          size="S"
          css={{
            color: theme.brand.text.weak,
          }}
          onClick={onAddTableClose}
        >
          <XSquare stroke={theme.brand.text.weak} />
          취소
        </Button>
        <Button
          typeVariant="contained"
          size="S"
          css={{
            color: theme.brand.text.default,
          }}
          disabled={
            isDataMissing || isDataUnchanged || isDateBeforeToday(deadlineInput)
          }
          onClick={onSubmit}
        >
          {typeVariant === 'edit' ? (
            <Edit stroke={theme.brand.text.default} />
          ) : (
            <Plus stroke={theme.brand.text.default} />
          )}
          {typeVariant === 'edit' ? '편집 완료' : '완료'}
        </Button>
      </div>
    </div>
  );
};
