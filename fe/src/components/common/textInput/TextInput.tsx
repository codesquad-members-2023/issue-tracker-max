import { useTheme } from '@emotion/react';
import { ReactComponent as XSquare } from '@assets/icons/xSquare.svg';
import { InputContainer } from './InputContainer';
import { Input } from './Input';

type Props = {
  value: string;
  label?: string;
  height?: 56 | 40;
  inputType?: React.HTMLInputTypeAttribute;
  placeholder?: string;
  isError?: boolean;
  captionString?: string;
  disabled?: boolean;
  onChange: (value: string) => void;
  onClearInput?: () => void;
};

export const TextInput: React.FC<Props> = ({
  value,
  label,
  height = 56,
  inputType,
  placeholder,
  isError,
  captionString,
  disabled,
  onChange,
  onClearInput,
}) => {
  const theme = useTheme() as any;
  const isInputEmpty = Boolean(value);

  return (
    <div
      css={{
        width: '100%',
        userSelect: 'none',
        opacity: disabled && theme.opacity.disabled,
        display: 'flex',
        flexDirection: 'column',
        gap: '4px',
      }}
    >
      <InputContainer {...{ label, height, disabled, isError }}>
        <>
          {(isInputEmpty || height === 40) && label && (
            <div
              css={{
                minWidth: '64px',
                font: theme.fonts.displayMedium12,
                color: theme.neutral.text.weak,
                padding: '0px 16px',
              }}
            >
              {label}
            </div>
          )}
          <div
            css={{
              display: 'flex',
              alignItems: 'center',
              width: '100%',
              padding: '0px 16px',
            }}
          >
            <Input
              {...{
                value,
                label,
                inputType,
                placeholder,
                disabled,
                onChange,
              }}
            />
            {onClearInput && (
              <XSquare
                stroke={theme.neutral.text.default}
                onClick={onClearInput}
                css={{ cursor: 'pointer' }}
              />
            )}
          </div>
        </>
      </InputContainer>
      {/* 외부로 옮기기 */}
      <div
        css={{
          font: theme.fonts.displayMedium12,
          color: theme.danger.text.default,
          padding: '0px 16px',
          height: '16px',
        }}
      >
        {isError && captionString && <div>{captionString}</div>}
      </div>
    </div>
  );
};
