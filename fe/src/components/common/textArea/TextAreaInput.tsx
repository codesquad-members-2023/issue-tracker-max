import { useTheme } from '@emotion/react';
import ReactMarkdown from 'react-markdown';

type Props = {
  textAreaValue?: string;
  isDisabled: boolean;
  placeholder?: string;
  onChangeTextArea?: (e: React.ChangeEvent<HTMLTextAreaElement>) => void;
  typeVariant: 'default' | 'add';
  fileUrls?: string[];
};

export const TextAreaInput: React.FC<Props> = ({
  textAreaValue,
  isDisabled,
  placeholder,
  onChangeTextArea,
  typeVariant,
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
            whiteSpace: 'pre-line',
            boxSizing: 'border-box',
          }}
        >
          <p
            css={{
              color: theme.neutral.text.default,
              font: theme.fonts.displayMedium16,
            }}
          >
            {textAreaValue && <MarkdownRenderer content={textAreaValue} />}
          </p>
        </div>
      )}
    </>
  );
};

const markdownImageStyle = {
  maxWidth: '100%',
  maxHeight: '200px',
};

const MarkdownRenderer: React.FC<{ content?: string }> = ({ content }) => (
  <ReactMarkdown
    css={{
      img: markdownImageStyle,
    }}
  >
    {content || ''}
  </ReactMarkdown>
);
