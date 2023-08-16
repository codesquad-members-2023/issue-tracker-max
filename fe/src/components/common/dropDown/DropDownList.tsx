import { css, useTheme } from '@emotion/react';
import { ReactComponent as UserImageSmall } from '@assets/icons/userImageSmall.svg';
import { ReactComponent as CheckOnCircle } from '@assets/icons/checkOnCircle.svg';
import { ReactComponent as CheckOffCircle } from '@assets/icons/checkOffCircle.svg';

type DropDownItem = {
  loginId?: string;
  id: number;
  image?: string;
  name?: string;
  backgroundColor?: string;
};

type Props = {
  item: DropDownItem;
  isSelected?: boolean;
  onClick: () => void;
};

export const DropDownList: React.FC<Props> = ({
  item,
  isSelected,
  onClick,
}) => {
  const theme = useTheme() as any;
  const commonStyles = css`
    flex: 1 0 0;
  `;
  const basicImage = 'basic-profile.jpeg';
  return (
    <li
      onClick={onClick}
      css={{
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'flex-start',
        gap: '8px',
        background: theme.neutral.surface.strong,
        padding: '8px 16px',
        cursor: 'pointer',
        font: isSelected
          ? theme.fonts.selectedBold16
          : theme.fonts.availableMedium16,
        color: isSelected
          ? theme.neutral.text.strong
          : theme.neutral.text.default,
        '&:hover': {
          background: theme.neutral.surface.bold,
        },
        '&:last-child': {
          borderRadius: `0 0 ${theme.radius.l} ${theme.radius.l}`,
          borderBottom: 'none',
        },
      }}
    >
      {item.backgroundColor && <UserImageSmall fill={item.backgroundColor} />}
      {item.image && (
        <img
          alt="userImage"
          src={item.image || basicImage}
          css={{
            width: '20px',
            height: '20px',
            borderRadius: theme.radius.half,
            top: 0,
            left: 0,
          }}
        />
      )}
      <span css={commonStyles}>{item.loginId || item.name}</span>
      {isSelected ? (
        <CheckOnCircle stroke={theme.neutral.text.default} />
      ) : (
        <CheckOffCircle stroke={theme.neutral.text.default} />
      )}
    </li>
  );
};
