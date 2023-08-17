import { Theme, css } from '@emotion/react';
import { useRef, useState } from 'react';
import { useTheme } from '@emotion/react';
import { Button } from '@components/common/Button';
import { ReactComponent as Edit } from '@assets/icons/edit.svg';
import { ReactComponent as Archive } from '@assets/icons/archive.svg';
import { ReactComponent as XSquare } from '@assets/icons/xSquare.svg';
import { TextInput } from '@components/common/textInput/TextInput';
import { patchIssueTitle } from 'apis/api';

type Props = {
  title: string;
  id: number;
  status: string;
  onToggleIssueStatus: (status: string, id: number) => void;
};

export const PostInformationHeader: React.FC<Props> = ({
  title,
  id,
  status,
  onToggleIssueStatus,
}: Props) => {
  const theme = useTheme() as any;

  const [isEditing, setIsEditing] = useState<boolean>(false);
  const [titleInput, setTitleInput] = useState<string>(title);
  const [isSubmiting, setIsSubmiting] = useState<boolean>(false);

  const placeholderValueRef = useRef<string>(title);

  const onEditTitleOpen = () => {
    placeholderValueRef.current = titleInput;
    setIsEditing(true);
  };

  const onEditTitleCancel = () => {
    setTitleInput(placeholderValueRef.current);
    setIsEditing(false);
  };

  const onChangeTitle = (value: string) => {
    setTitleInput(value);
  };

  const onClearInput = () => {
    setTitleInput('');
  };

  const onSubmitTitle = async () => {
    try {
      setIsSubmiting(true);
      await patchIssueTitle(id, titleInput);
      setIsEditing(false);
    } catch (error) {
      console.log(error);
      //에러처리
    } finally {
      setIsSubmiting(false);
    }
  };

  return (
    <div css={postInformationHeaderStyle}>
      {isEditing ? (
        <TextInput
          height={40}
          value={titleInput}
          label="제목"
          inputType="text"
          disabled={isSubmiting}
          onChange={onChangeTitle}
          onClearInput={onClearInput}
        />
      ) : (
        <div className="title-container">
          <h2>{titleInput || title}</h2>
          <span>#{id}</span>
        </div>
      )}

      <div className="button-container">
        {isEditing ? (
          <>
            <Button typeVariant="outline" size="S" onClick={onEditTitleCancel}>
              <XSquare stroke={theme.brand.text.weak} />
              편집 취소
            </Button>
            <Button
              typeVariant="contained"
              size="S"
              disabled={
                titleInput === placeholderValueRef.current ||
                title.length === 0 ||
                titleInput.length > 70
              }
              onClick={onSubmitTitle}
            >
              <Edit stroke={theme.brand.text.default} />
              편집 완료
            </Button>
          </>
        ) : (
          <>
            <Button typeVariant="outline" size="S" onClick={onEditTitleOpen}>
              <Edit stroke={theme.brand.text.weak} />
              제목 편집
            </Button>
            <Button
              typeVariant="outline"
              size="S"
              onClick={() => onToggleIssueStatus(status, id)}
            >
              <Archive stroke={theme.brand.text.weak} />
              {status === 'open' ? '이슈 닫기' : '이슈 열기'}
            </Button>
          </>
        )}
      </div>
    </div>
  );
};

const postInformationHeaderStyle = (theme: Theme) => css`
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  gap: 16px;
  box-sizing: border-box;

  .title-container {
    display: flex;
    gap: 8px;
  }

  h2 {
    color: ${theme.neutral.text.strong};
    font: ${theme.fonts.displayBold32};
  }

  span {
    color: ${theme.neutral.text.weak};
    font: ${theme.fonts.displayBold32};
  }

  .button-container {
    display: flex;
    gap: 16px;
    alignitems: center;
  }
`;
