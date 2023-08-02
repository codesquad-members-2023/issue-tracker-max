import { useTheme } from '@emotion/react';

type Props = {
  panelHeader: string;
};

export const DropDownHeader: React.FC<Props> = ({ panelHeader }) => {
  const theme = useTheme() as any;

  return (
    <div
      css={{
        borderBottom: `${theme.border.default} ${theme.neutral.border.default}`,
        padding: '8px 16px',
        font: theme.fonts.displayMedium12,
        color: theme.neutral.text.weak,
      }}
    >
      {panelHeader}
    </div>
  );
};
