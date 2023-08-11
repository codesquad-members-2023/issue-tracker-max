import { useTheme } from '@emotion/react';
import { Button } from '../Button';
import { useLocation, useNavigate } from 'react-router-dom';
import { ReactComponent as Label } from '@assets/icons/label.svg';
import { ReactComponent as Milestone } from '@assets/icons/milestone.svg';

type Props = {
  type: 'label' | 'milestone';
  count: number;
};

export const Tab: React.FC<Props> = ({ type, count = 0 }) => {
  const theme = useTheme() as any;
  const { pathname } = useLocation();
  const navigate = useNavigate();

  const firstPathName = pathname.split('/')[1];
  const isMatching = firstPathName === type;
  const icon =
    type === 'label' ? (
      <Label
        stroke={
          isMatching ? theme.neutral.text.strong : theme.neutral.text.default
        }
      />
    ) : (
      <Milestone
        fill={
          isMatching ? theme.neutral.text.strong : theme.neutral.text.default
        }
      />
    );
  const buttonText = type === 'label' ? '레이블' : '마일스톤';

  return (
    <div
      className={isMatching ? 'active' : ''}
      onClick={() => navigate(`/${type}`)}
      css={{
        borderRight: `${theme.border.default} ${theme.neutral.border.default}`,

        backgroundColor: theme.neutral.surface.default,

        '& button': {
          font: theme.fonts.availableMedium16,
        },

        '&:last-child': {
          borderRight: '0px',
        },

        '&:hover button': {
          backgroundColor: theme.neutral.surface.bold,
        },

        '&.active button': {
          backgroundColor: theme.neutral.surface.bold,
          font: theme.fonts.selectedBold16,
          color: theme.neutral.text.strong,
        },
      }}
    >
      <Button typeVariant="ghost" css={{ width: '160px' }}>
        <div
          css={{
            display: 'flex',
            gap: '4px',
          }}
        >
          <span
            css={{
              display: 'flex',
              alignItems: 'center',
            }}
          >
            {icon}
          </span>
          <span>{`${buttonText}(${count})`}</span>
        </div>
      </Button>
    </div>
  );
};
