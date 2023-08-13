import { useTheme } from '@emotion/react';

type Props = {
  textAreaValue?: string;
  isDisabled: boolean;
  placeholder?: string;
  onChangeTextArea?: (e: React.ChangeEvent<HTMLTextAreaElement>) => void;
  typeVariant: 'default' | 'add';
  fileUrl?: string;
};

export const TextAreaInput: React.FC<Props> = ({
  textAreaValue,
  isDisabled,
  placeholder,
  onChangeTextArea,
  typeVariant,
  fileUrl,
}) => {
  const theme = useTheme() as any;

  const placeholderText = placeholder ? placeholder : '코멘트를 입력하세요';

  return (
    <>
      {typeVariant === 'add' ? (
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
      ) : (
        <div
          css={{
            flex: '1 0 0',
            padding: '0 16px 16px 16px',
            width: '100%',
            background: 'transparent',
            overflowY: 'auto',
          }}
        >
          <p
            css={{
              color: theme.neutral.text.default,
              font: theme.fonts.displayMedium16,
            }}
          >
            {textAreaValue}
          </p>
          {fileUrl && (
            /* 배열로 만들어서 여러개 추가 */
            <>
              <img src={fileUrl} alt="image" />
            </>
          )}
        </div>
      )}
    </>
  );
};
