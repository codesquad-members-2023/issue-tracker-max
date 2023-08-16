import { Theme, css } from '@emotion/react';
import { InformationTag } from '../InformationTag';
import { Button } from '../Button';
import { ReactComponent as Edit } from '@assets/icons/edit.svg';
import { ReactComponent as Smile } from '@assets/icons/smile.svg';
import { ReactComponent as Trash } from '@assets/icons/trash.svg';
import { formatISODateString, getFormattedTimeDifference } from '@utils/time';

type Props = {
  typeVariant: string;
  commentId?: number;
  image?: string;
  loginId?: string;
  createdAt?: string;
  isAuthor?: boolean;
  onClickEdit?: () => void;
  onClickDelete?: (id?: number) => void;
};

export const CommentHeader: React.FC<Props> = ({
  typeVariant,
  commentId,
  image,
  loginId,
  createdAt,
  isAuthor,
  onClickEdit,
  onClickDelete,
}) => {
  const diff = createdAt ? Date.now() - new Date(createdAt).getTime() : 0;
  const date = createdAt
    ? diff < FIVE_DAYS_IN_MS
      ? getFormattedTimeDifference(createdAt)
      : formatISODateString(createdAt)
    : '';

  return (
    <div css={commentHeaderStyle}>
      <div className="header-left">
        <img
          className="user-image"
          src={image || 'basic-profile.jpeg'}
          alt="작성자이미지"
        />
        <span className="user-name">{loginId}</span>
        <span className="created-at">{date}</span>
      </div>

      <div className="header-right">
        {isAuthor && (
          <InformationTag size="S" typeVariant="default">
            <span className="author-label">작성자</span>
          </InformationTag>
        )}
        {isAuthor && (
          <Button
            className="header-right__button"
            onClick={onClickEdit}
            typeVariant="ghost"
            size="S"
          >
            <Edit className="button-icon" />
            편집
          </Button>
        )}
        <Button className="header-right__button" typeVariant="ghost" size="S">
          <Smile className="button-icon" />
          반응
        </Button>
        {isAuthor && typeVariant === 'default' && (
          <Button
            className="header-right__button__delete"
            onClick={() => {
              if (onClickDelete) {
                onClickDelete(commentId);
              }
            }}
            typeVariant="ghost"
            size="S"
          >
            <Trash className="button-icon" />
            삭제
          </Button>
        )}
      </div>
    </div>
  );
};

const commentHeaderStyle = (theme: Theme) => css`
  height: 64px;
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 24px;
  background: ${theme.neutral.surface.default};

  .header-left {
    display: flex;
    align-items: center;
    gap: 8px;

    .user-image {
      width: 32px;
      height: 32px;
      border-radius: ${theme.radius.half};
    }

    .user-name {
      font: ${theme.fonts.displayMedium16};
      color: ${theme.neutral.text.default};
    }

    .created-at {
      font: ${theme.fonts.displayMedium16};
      color: ${theme.neutral.text.weak};
    }
  }

  .header-right {
    display: flex;
    align-items: center;
    gap: 16px;

    .author-label {
      color: ${theme.neutral.text.weak};
    }

    &__button {
      width: fit-content;
      padding: 0;

      .button-icon {
        stroke: ${theme.neutral.text.default};
      }

      &__delete {
        width: fit-content;
        padding: 0;
        color: ${theme.danger.text.default};
        .button-icon {
          stroke: ${theme.danger.text.default};
        }
      }
    }
  }
`;

const FIVE_DAYS_IN_MS = 432000000;
