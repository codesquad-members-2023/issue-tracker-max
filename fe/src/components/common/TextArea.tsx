import { css, useTheme } from '@emotion/react';
import { ReactComponent as Grip } from '@assets/icons/grip.svg';

type Props = {
  isDisabled?: boolean;
  size?: 'defaultSize' | 'S';
  letterCount?: number;
  isTyping?: boolean;
  onTyping?: () => void;
};

export const TextArea: React.FC<Props> = ({
  isDisabled,
  size = 'defaultSize',
  letterCount,
  isTyping,
  onTyping,
}) => {
  const theme = useTheme() as any;

  const SIZE = {
    S: {
      height: '184px',
    },
    defaultSize: {
      height: '552px',
    },
  };

  return (
    <div
      css={{
        width: '912px',
        display: 'flex',
        flexDirection: 'column',
        boxSizing: 'border-box',
        borderRadius: theme.radius.l,
        paddingTop: '16px',
        overflow: 'hidden',
        border: `${theme.border.default} ${theme.neutral.surface.bold}`,
        background: theme.neutral.surface.bold,
        opacity: isDisabled ? theme.opacity.disabled : 1,
        '&:focus-within': {
          background: theme.neutral.surface.strong,
          border: `${theme.border.default} ${theme.neutral.border.defaultActive}`,
        },
        ...SIZE[size],
      }}
    >
      {isTyping && !isDisabled && (
        <div
          css={{
            padding: '0 16px 8px 16px',
            font: theme.fonts.displayMedium12,
            color: theme.neutral.text.weak,
          }}
        >
          코멘트를 입력하세요
        </div>
      )}
      <textarea
        onChange={onTyping}
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
        }}
        placeholder="코멘트를 입력하세요"
      />

      <div
        css={{
          borderBottom: `${theme.border.dash} ${theme.neutral.border.default}`,
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'flex-end',
          gap: '8px',
          padding: '8px 16px',
          boxSizing: 'border-box',
          width: '100%',
          height: '52px',
        }}
      >
        <span
          css={{
            color: theme.neutral.text.weak,
            font: theme.fonts.availableMedium12,
          }}
        >
          띄어쓰기 포함 {letterCount}자
        </span>
        <Grip stroke={theme.neutral.text.weak} />
      </div>

      <div
        css={{
          display: 'flex',
          alignItems: 'center',

          padding: '8px 16px',
          boxSizing: 'border-box',
          width: '100%',
          height: '52px',
        }}
      >
        <label htmlFor="file" css={{ border: '1px solid  black' }}>
          <button>파일 첨부하기</button>
        </label>
        <input type="file" name="file" id="file" css={{ display: 'none' }} />
      </div>
    </div>
  );
};
