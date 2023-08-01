import { useTheme } from '@emotion/react';

type Props = {
  progress: number;
};

export const ProgressBar: React.FC<Props> = ({ progress }) => {
  const theme = useTheme() as any;

  return (
    <>
      <div
        css={{
          width: '224px',
          height: '8px',
          borderRadius: '10px',
          background: `linear-gradient(90deg, ${theme.palette.blue} 0%, ${theme.palette.blue} ${progress}%, ${theme.neutral.surface.bold} ${progress}%, ${theme.neutral.surface.bold} 100%)`,
        }}
      ></div>
    </>
  );
};
