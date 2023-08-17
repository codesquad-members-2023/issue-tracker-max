import { useTheme } from '@emotion/react';
type Props = {
  title: string;
};

export const TableHeader: React.FC<Props> = ({ title }) => {
  const theme = useTheme() as any;
  return (
    <span
      css={{
        font: theme.fonts.displayBold20,
        color: theme.neutral.text.strong,
      }}
    >
      {title}
    </span>
  );
};
