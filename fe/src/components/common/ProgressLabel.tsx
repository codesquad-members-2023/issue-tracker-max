import { useTheme } from '@emotion/react';

type Props = {
  name?: string;
  progress?: number;
  openIssueCount?: number;
  closeIssueCount?: number;
};

export const ProgressLabel: React.FC<Props> = ({
  name,
  progress = 0,
  openIssueCount = 0,
  closeIssueCount = 0,
}) => {
  const theme = useTheme() as any;

  return (
    <>
      {name ? (
        <p
          css={{
            marginTop: '8px',
            font: theme.fonts.displayMedium12,
            color: theme.neutral.text.strong,
          }}
        >
          {name}
        </p>
      ) : (
        <div
          css={{
            marginTop: '8px',
            width: '100%',
            display: 'flex',
            justifyContent: 'space-between',
            font: theme.fonts.displayMedium12,
            color: theme.neutral.text.weak,
          }}
        >
          <p>{progress}%</p>
          <div
            css={{
              display: 'flex',
              gap: '8px',
            }}
          >
            <p>열린 이슈 {openIssueCount}</p>
            <p>닫힌 이슈 {closeIssueCount}</p>
          </div>
        </div>
      )}
    </>
  );
};
