import { css, useTheme } from '@emotion/react';

import { ReactComponent as UserImageSmall } from '@assets/icons/userImageSmall.svg';
import { ReactComponent as CheckOnCircle } from '@assets/icons/checkOnCircle.svg';
import { ReactComponent as CheckOffCircle } from '@assets/icons/checkOffCircle.svg';
import {
  DropDownIndicatorNameType,
  LabelType,
  ContributorType,
  MilestoneType,
  IssueStateType,
  TextColorType,
} from './types';

type Props = {
  item: DropDownItem;
  onSelected?: () => void;
  isSelected?: boolean;
};

export type IssueFilterType =
  | '열린 이슈'
  | '내가 작성한 이슈'
  | '나에게 할당된 이슈'
  | '내가 댓글을 남긴 이슈'
  | '닫힌 이슈';

type DropDownItem =
  | {
      name: 'issueFilter';
      value: IssueFilterType;
    }
  | {
      name: 'assignee';
      value: ContributorType;
    }
  | {
      name: 'author';
      value: ContributorType;
    }
  | {
      name: 'label';
      value: LabelType;
    }
  | {
      name: 'milestone';
      value: MilestoneType;
    }
  | {
      name: 'issueState';
      value: IssueStateType;
    }
  | {
      name: 'textColor';
      value: TextColorType;
    };

export const DropDownList: React.FC<Props> = ({
  item,
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
      <ListItemContent item={item} />
      {isSelected ? (
        <CheckOnCircle stroke="#4E4B66" />
      ) : (
        <CheckOffCircle stroke="#4E4B66" />
      )}
    </li>
  );
};

const ListItemContent: React.FC<Props> = ({ item }) => {
  const commonStyles = css`
    flex: 1 0 0;
  `;

  switch (item.name) {
    case 'label':
      return (
        <>
          {item.value.backgroundColor && (
            <UserImageSmall fill={item.value.backgroundColor} />
          )}
          <span css={commonStyles}>{item.value.name}</span>
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
          {item.value.image && (
            <>
              <UserImageSmall fill="#EFF0F6" />
              <img
                alt="userImage"
                src={item.value.image}
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
          <span>{item.value.id}</span>
        </div>
      );

    case 'milestone':
      return <span css={commonStyles}>{item.value.name}</span>;
    default:
      return <span css={commonStyles}>{item.value}</span>;
  }
};
