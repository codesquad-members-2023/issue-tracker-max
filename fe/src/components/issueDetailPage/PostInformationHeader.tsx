import { useState } from 'react';
import { useTheme } from '@emotion/react';
import { Button } from '@components/common/Button';
import { ReactComponent as Edit } from '@assets/icons/edit.svg';
import { ReactComponent as Archive } from '@assets/icons/archive.svg';
import { ReactComponent as XSquare } from '@assets/icons/xSquare.svg';
import { TextInput } from '@components/common/textInput/TextInput';

type Props = {
  title: string;
  id: number;
};

export const PostInformationHeader: React.FC<Props> = ({
  title,
  id,
}: Props) => {
  const theme = useTheme() as any;

  const [isEditing, setIsEditing] = useState<boolean>(false);
  const [titleInput, setTitleInput] = useState<string>('');

  const isDisabled = title.length === 0;

  const onEditTitleOpen = () => {
    setIsEditing(true);
  };

  const onEditTitleClose = () => {
    setIsEditing(false);
  };

  const onChangeTitle = (value: string) => {
    setTitleInput(value);
  };

  const onClearInput = () => {
    setTitleInput('');
  };

  const onSubmitTitle = () => {
    console.log('제목 변경');
  };

  const onCloseIssue = () => {
    console.log('이슈 닫기');
  };

  return (
    <div
      css={{
        width: '100%',
        display: 'flex',
        justifyContent: 'space-between',
        alignItems: 'flex-start',
        gap: '16px',
        boxSizing: 'border-box',
      }}
    >
      {isEditing ? (
        <TextInput
          height={40}
          value={titleInput}
          label="제목"
          placeholder={title}
          inputType="text"
          disabled={false}
          onChange={onChangeTitle}
          onClearInput={onClearInput}
        />
      ) : (
        <div
          css={{
            display: 'flex',
            gap: '8px',
          }}
        >
          <h2
            css={{
              color: theme.neutral.text.strong,
              font: theme.fonts.displayBold32,
            }}
          >
            {title}
          </h2>

          <span
            css={{
              color: theme.neutral.text.weak,
              font: theme.fonts.displayBold32,
            }}
          >
            #{id}
          </span>
        </div>
      )}

      <div
        css={{
          display: 'flex',
          gap: '16px',
          alignItems: 'center',
        }}
      >
        {isEditing ? (
          <>
            <Button typeVariant="outline" size="S" onClick={onEditTitleClose}>
              <XSquare stroke={theme.brand.text.weak} />
              편집 취소
            </Button>
            <Button typeVariant="contained" size="S" disabled={isDisabled}>
              <Edit stroke={theme.brand.text.default} onClick={onSubmitTitle} />
              편집 완료
            </Button>
          </>
        ) : (
          <>
            <Button typeVariant="outline" size="S" onClick={onEditTitleOpen}>
              <Edit stroke={theme.brand.text.weak} />
              제목 편집
            </Button>
            <Button typeVariant="outline" size="S" onClick={onCloseIssue}>
              <Archive stroke={theme.brand.text.weak} />
              이슈 닫기
            </Button>
          </>
        )}
      </div>
    </div>
  );
};
