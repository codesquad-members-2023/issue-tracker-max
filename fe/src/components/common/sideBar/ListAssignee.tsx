import { useTheme } from '@emotion/react';

type SelectedAssigneesData = {
  userId: number;
  loginId: string;
  image: string;
};

type Props = {
  selectedAssigneesData: SelectedAssigneesData[];
};

export const ListAssignee: React.FC<Props> = ({ selectedAssigneesData }) => {
  const theme = useTheme() as any;
  const basicImage = 'basic-profile.jpeg';

  return (
    <>
      {selectedAssigneesData.map((assignee) => (
        <div
          key={assignee.userId}
          css={{
            display: 'flex',
            alignItems: 'center',
            gap: '8px',
          }}
        >
          <img
            alt="userImage"
            src={assignee.image || basicImage}
            css={{
              width: '20px',
              height: '20px',
              borderRadius: theme.radius.half,
            }}
          />
          <span
            css={{
              flex: '1 0 0',
              font: theme.fonts.displayMedium12,
              color: theme.neutral.text.strong,
            }}
          >
            {assignee.loginId}
          </span>
        </div>
      ))}
    </>
  );
};
