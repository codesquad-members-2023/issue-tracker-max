import { css, useTheme } from '@emotion/react';
import { ReactComponent as UserImageSmall } from '@assets/icons/userImageSmall.svg';

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
  return (
    <>
      {selectedAssigneesData.map((assignee) => (
        <div
          key={assignee.userId}
          css={{
            position: 'relative',
            display: 'flex',
            gap: '8px',
          }}
        >
          <UserImageSmall fill={theme.neutral.surface.bold} />
          <img
            alt="userImage"
            src={assignee.image}
            css={{
              width: '20px',
              height: '20px',
              position: 'absolute',
              borderRadius: theme.radius.half,
              top: 0,
              left: 0,
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
