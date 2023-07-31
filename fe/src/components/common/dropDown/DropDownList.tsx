import { css, useTheme } from '@emotion/react';
import { useState } from 'react';

import { ReactComponent as UserImageSmall } from '@assets/icons/userImageSmall.svg';
import { ReactComponent as CheckOnCircle } from '@assets/icons/checkOnCircle.svg';
import { ReactComponent as CheckOffCircle } from '@assets/icons/checkOffCircle.svg';

// type ListType = 'default' | 'label' | 'milestone' | 'assignee' | 'author';

type Props = {
  // listType: ListType;
  options: any;
  name: string;
  onSelected?: () => void;
  isSelected?: boolean;
};

export const DropDownList: React.FC<Props> = ({
  options,
  name,
  onSelected,
  isSelected,
}) => {
  const theme = useTheme() as any;
  // const [isSelected, setIsSelected] = useState(false);

  // const onSelected = () => {
  //   setIsSelected((prev) => !prev);
  // };

  //셀렉티드 된 것만 모아서 요청 보내야함
  return (
    <li
      onClick={onSelected}
      css={css`
        display: flex;
        align-items: center;
        justify-content: flex-start;
        gap: 8px;
        background: ${theme.neutral.surface.strong};
        padding: 8px 16px;
        font: ${isSelected
          ? theme.fonts.selectedBold16
          : theme.fonts.availableMedium16};
        color: ${isSelected
          ? theme.neutral.text.strong
          : theme.neutral.text.default};
        &:hover {
          background: ${theme.neutral.surface.bold};
        }
        &:last-child {
          border-radius: 0 0 ${theme.radius.l} ${theme.radius.l};
          borderbottom: none;
        }
      `}
    >
      <ListItemContent name={name} options={options} />
      {isSelected ? (
        <CheckOnCircle stroke="#4E4B66" />
      ) : (
        <CheckOffCircle stroke="#4E4B66" />
      )}
    </li>
  );
};

const ListItemContent = ({ name, options }: Props) => {
  const commonStyles = css`
    flex: 1 0 0;
  `;

  switch (name) {
    case 'label':
      return (
        <>
          {options.backgroundColor && (
            <UserImageSmall fill={options.backgroundColor} />
          )}
          <span css={commonStyles}>{options.name}</span>
        </>
      );
    case 'milestone':
      return <span css={commonStyles}>{options.name}</span>;
    case 'assignee':
    case 'author':
      return (
        <div
          css={css`
            display: flex;
            align-items: center;
            gap: 8px;
            position: relative;
          `}
        >
          {options.image && (
            <>
              <UserImageSmall fill="#EFF0F6" />
              <img
                width="20"
                height="20"
                src={options.image}
                alt="userImage"
                style={{
                  position: 'absolute',
                  top: 0,
                  left: 0,
                }}
              />
            </>
          )}
          <span css={commonStyles}>{options.id}</span>
        </div>
      );
    case 'issueFilter':
    case 'issueState':
    case 'textColor':
      return <span css={commonStyles}>{options}</span>;
    default:
      return <span css={commonStyles}>{options}</span>;
  }
};
