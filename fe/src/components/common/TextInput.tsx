import { useTheme } from '@emotion/react';
import { ReactComponent as XSquare } from '@assets/icons/xSquare.svg';
import { ReactComponent as RefreshCcw } from '@assets/icons/refreshCcw.svg';

type Props = {
  value: string;
  label?: string;
  height?: 56 | 40;
  inputType?: React.HTMLInputTypeAttribute;
  placeholder?: string;
  isError?: boolean;
  captionString?: string;
  disabled?: boolean;
  onChange: ({ target }: { target: HTMLInputElement }) => void;
  onClearInput?: () => void;
  onRandomButtonClick?: () => void;
  children?: React.ReactNode;
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
  onRandomButtonClick,
  children,
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
      <label
        htmlFor={label}
        css={{
          width: '100%',
          display: 'flex',
          flexDirection: height === 56 ? 'column' : 'row',
          alignItems: height === 56 ? '' : 'center',
          justifyContent: 'center',
          height,
          padding: '0px 16px',
          borderRadius: theme.radius.l,
          cursor: disabled ? 'default' : 'text',
          backgroundColor: theme.neutral.surface.bold,
          gap: '8px',

          border: isError
            ? `${theme.border.default} ${theme.danger.border.default}`
            : `${theme.border.default} ${theme.neutral.surface.bold}`,

          '&:focus-within': {
            backgroundColor: theme.neutral.surface.strong,
            border: `${theme.border.default} ${theme.neutral.border.defaultActive}`,
          },
        }}
      >
        <>{children}</>

        {(isInputEmpty || height === 40) && label && (
          <div
            css={{
              minWidth: '64px',
              font: theme.fonts.displayMedium12,
              color: theme.neutral.text.weak,
            }}
          >
            {label}
          </div>
        )}
        <div css={{ display: 'flex', alignItems: 'center', width: '100%' }}>
          <input
            id={label}
            type={inputType}
            min={inputType === 'date' ? '1000-01-01' : undefined}
            max={inputType === 'date' ? '9999-12-31' : undefined}
            placeholder={placeholder}
            disabled={disabled}
            onChange={onChange}
            css={{
              width: '100%',
              border: '0',
              backgroundColor: 'transparent',
              outline: 'none',
              caretColor: theme.palette.blue,
              font: theme.fonts.displayMedium16,
              color: theme.neutral.text.strong,
            }}
          ></input>
          {onClearInput && (
            <XSquare
              stroke={theme.neutral.text.default}
              onClick={onClearInput}
              css={{ cursor: 'pointer' }}
            />
          )}
          {onRandomButtonClick && (
            <RefreshCcw
              stroke={theme.neutral.text.default}
              onClick={onRandomButtonClick}
              css={{ cursor: 'pointer' }}
            />
          )}
        </div>
      </label>
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
