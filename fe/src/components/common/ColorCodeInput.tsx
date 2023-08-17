import { useTheme } from '@emotion/react';
import { Input } from './textInput/Input';
import { InputContainer } from './textInput/InputContainer';
import { ReactComponent as RefreshCcw } from '@assets/icons/refreshCcw.svg';

type Props = {
  value: string;
  isError?: boolean;
  caption?: string;
  onChange: (value: string) => void;
  onRandomButtonClick: () => void;
};

export const ColorCodeInput: React.FC<Props> = ({
  isError,
  value,
  caption,
  onChange,
  onRandomButtonClick,
}) => {
  const theme = useTheme() as any;

  return (
    <div
      css={{
        display: 'flex',
        flexDirection: 'column',
        gap: '8px',
        minWidth: '240px',
      }}
    >
      <InputContainer height={40} isError={isError}>
        <div
          css={{
            width: '100%',
            padding: '16px',
            display: 'flex',
            alignItems: 'center',
          }}
        >
          <div
            css={{
              minWidth: '64px',
              font: theme.fonts.displayMedium12,
              color: theme.neutral.text.weak,
              userSelect: 'none',
            }}
          >
            배경 색상
          </div>

          <Input {...{ value, onChange }} />

          <RefreshCcw
            stroke={theme.neutral.text.default}
            onClick={onRandomButtonClick}
            css={{
              minWidth: '16px',
              cursor: 'pointer',
            }}
          />
        </div>
      </InputContainer>
      <div css={{ height: '16px' }}>
        {isError && (
          <span
            css={{
              font: theme.fonts.availableMedium12,
              color: theme.danger.text.default,
            }}
          >
            {caption}
          </span>
        )}
      </div>
    </div>
  );
};
