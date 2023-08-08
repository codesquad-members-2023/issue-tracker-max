import { useTheme } from '@emotion/react';

type Props = {
  textAreaValue?: string;
  isDisabled: boolean;
  placeholder?: string;
  onChangeTextArea?: (e: React.ChangeEvent<HTMLTextAreaElement>) => void;
};

export const TextAreaInput: React.FC<Props> = ({
  textAreaValue,
  isDisabled,
  onChangeTextArea,
}) => {
  const theme = useTheme() as any;
  const placeholderText = textAreaValue ? textAreaValue : '코멘트를 입력하세요';

  return (
    <textarea
      value={textAreaValue}
      onChange={onChangeTextArea}
      disabled={isDisabled}
      css={{
        flex: '1 0 0',
        padding: '0 16px 16px 16px',
        width: '100%',
        background: 'transparent',
        overflowY: 'auto',
        color: theme.neutral.text.strong,
        font: theme.fonts.displayMedium16,
        caretColor: theme.palette.blue,
        '&::placeholder': {
          color: theme.neutral.text.weak,
          font: theme.fonts.displayMedium16,
        },
        '&:focus': {
          background: theme.neutral.surface.strong,
        },
      }}
      placeholder={placeholderText}
    />
  );
};
