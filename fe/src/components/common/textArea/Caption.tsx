import { useTheme } from '@emotion/react';
import { ReactComponent as Grip } from '@assets/icons/grip.svg';

type Props = {
  isDisplayingCount: boolean;
  letterCount?: number;
};

export const Caption: React.FC<Props> = ({
  isDisplayingCount,
  letterCount,
}) => {
  const theme = useTheme() as any;

  return (
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
      {isDisplayingCount && (
        <span
          css={{
            color: theme.neutral.text.weak,
            font: theme.fonts.availableMedium12,
          }}
        >
          띄어쓰기 포함 {letterCount}자
        </span>
      )}
      <Grip stroke={theme.neutral.text.weak} />
    </div>
  );
};
