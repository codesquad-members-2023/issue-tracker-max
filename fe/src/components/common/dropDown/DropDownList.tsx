import { css, useTheme } from '@emotion/react';
import { ReactComponent as UserImageSmall } from '@assets/icons/userImageSmall.svg';
import { ReactComponent as CheckOnCircle } from '@assets/icons/checkOnCircle.svg';
import { ReactComponent as CheckOffCircle } from '@assets/icons/checkOffCircle.svg';

type DropDownItem = {
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
          <UserImageSmall fill="#EFF0F6" />
          <img
            alt="userImage"
            src={item.image}
            css={{
              width: '20px',
              height: '20px',
              position: 'absolute',
              top: 0,
              left: 0,
            }}
          />
        </div>
      )}
      <span css={commonStyles}>{item.id || item.name}</span>
      {isSelected ? (
        <CheckOnCircle stroke="#4E4B66" />
      ) : (
        <CheckOffCircle stroke="#4E4B66" />
      )}
    </li>
  );
};
