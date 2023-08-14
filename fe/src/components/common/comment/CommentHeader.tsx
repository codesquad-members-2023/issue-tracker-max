import { Theme, css } from '@emotion/react';
import { InformationTag } from '../InformationTag';
import { Button } from '../Button';
import { ReactComponent as Edit } from '@assets/icons/edit.svg';
import { ReactComponent as Smile } from '@assets/icons/smile.svg';

type Props = {
  image?: string;
  loginId?: string;
  createdAt?: string;
  isAuthor?: boolean;
  onClickEdit?: () => void;
};

export const CommentHeader: React.FC<Props> = ({
  image,
  loginId,
  createdAt,
  isAuthor,
  onClickEdit,
}) => {
  return (
    <div css={commentHeaderStyle}>
      <div className="header-left">
        <img className="user-image" src={image} alt="작성자이미지" />
        <span className="user-name">{loginId}</span>
        <span className="created-at">{createdAt}</span>
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
    }
  }
`;
