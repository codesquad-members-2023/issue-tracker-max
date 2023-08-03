import { css, useTheme } from '@emotion/react';
import { ReactComponent as UserImageSmall } from '@assets/icons/userImageSmall.svg';
import { ReactComponent as CheckOnCircle } from '@assets/icons/checkOnCircle.svg';
import { ReactComponent as CheckOffCircle } from '@assets/icons/checkOffCircle.svg';

type DropDownItem = {
  loginId?: string;
  id?: string;
  image?: string;
  name?: string;
  backgroundColor?: string;
};

type Props = {
  item: DropDownItem;
  isSelected?: boolean;
  onSelected: (index: number) => void;
  index: number;
};

export const DropDownList: React.FC<Props> = ({
  item,
  onSelected,
  isSelected,
  index,
}) => {
  const theme = useTheme() as any;
  const commonStyles = css`
    flex: 1 0 0;
  `;
  return (
    <li
      onClick={() => {
        onSelected(index);
      }}
      css={{
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'flex-start',
        gap: '8px',
        background: theme.neutral.surface.strong,
        padding: '8px 16px',
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
        <div
          css={{
            position: 'relative',
          }}
        >
          <UserImageSmall fill={theme.neutral.surface.bold} />
          <img
            alt="userImage"
            src={item.image}
            css={{
              width: '20px',
              height: '20px',
              position: 'absolute',
              borderRadius: theme.radius.half,
              top: 0,
              left: 0,
            }}
          />
        </div>
      )}
      <span css={commonStyles}>{item.id || item.name || item.loginId}</span>
      {isSelected ? (
        <CheckOnCircle stroke={theme.neutral.text.default} />
      ) : (
        <CheckOffCircle stroke={theme.neutral.text.default} />
      )}
    </li>
  );
};
