import { css, useTheme } from '@emotion/react';

import { ReactComponent as UserImageSmall } from '@assets/icons/userImageSmall.svg';
import { ReactComponent as CheckOnCircle } from '@assets/icons/checkOnCircle.svg';
import { ReactComponent as CheckOffCircle } from '@assets/icons/checkOffCircle.svg';
import {
  DropDownIndicatorNameType,
  LabelType,
  IssueFilterType,
  ContributorType,
} from './types';

type Props = {
  option: LabelType & IssueFilterType & ContributorType;
  name: DropDownIndicatorNameType;
  onSelected?: () => void;
  isSelected?: boolean;
};

export const DropDownList: React.FC<Props> = ({
  option,
  name,
  onSelected,
  isSelected,
}) => {
  const theme = useTheme() as any;

  return (
    <li
      onClick={onSelected}
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
      <ListItemContent name={name} option={option} />
      {isSelected ? (
        <CheckOnCircle stroke="#4E4B66" />
      ) : (
        <CheckOffCircle stroke="#4E4B66" />
      )}
    </li>
  );
};

const ListItemContent: React.FC<Props> = ({ name, option }) => {
  const commonStyles = css`
    flex: 1 0 0;
  `;

  switch (name) {
    case 'label':
      return (
        <>
          {option.backgroundColor && (
            <UserImageSmall fill={option.backgroundColor} />
          )}
          <span css={commonStyles}>{option.name}</span>
        </>
      );
    case 'assignee':
    case 'author':
      return (
        <div
          css={{
            display: 'flex',
            alignItems: 'center',
            flex: '1 0 0',
            gap: '8px',
            position: 'relative',
          }}
        >
          {option.image && (
            <>
              <UserImageSmall fill="#EFF0F6" />
              <img
                alt="userImage"
                src={option.image}
                css={{
                  width: '20px',
                  height: '20px',
                  position: 'absolute',
                  top: 0,
                  left: 0,
                }}
              />
            </>
          )}
          <span>{option.id}</span>
        </div>
      );

    case 'milestone':
      return <span css={commonStyles}>{option.name}</span>;
    default:
      return <span css={commonStyles}>{option}</span>;
  }
};
