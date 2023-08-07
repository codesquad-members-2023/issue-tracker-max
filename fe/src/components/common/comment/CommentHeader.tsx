import { useTheme } from '@emotion/react';
import { InformationTag } from '../InformationTag';
import { Button } from '../Button';
import { ReactComponent as Edit } from '@assets/icons/edit.svg';
import { ReactComponent as Smile } from '@assets/icons/smile.svg';

type Props = {
  image: string;
  loginId: string;
  createdAt: string;
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
  const theme = useTheme() as any;
  return (
    <div
      css={{
        height: '64px',
        width: '100%',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'space-between',
        padding: '0 24px',
        background: theme.neutral.surface.default,
      }}
    >
      <div
        css={{
          display: 'flex',
          alignItems: 'center',
          gap: '8px',
        }}
      >
        <img
          src={image}
          alt="작성자이미지"
          css={{
            width: '32px',
            height: '32px',
            borderRadius: theme.radius.half,
          }}
        />
        <span
          css={{
            font: theme.fonts.displayMedium16,
            color: theme.neutral.text.default,
          }}
        >
          {loginId}
        </span>
        <span
          css={{
            font: theme.fonts.displayMedium16,
            color: theme.neutral.text.weak,
          }}
        >
          {createdAt}
        </span>
      </div>

      <div
        css={{
          display: 'flex',
          alignItems: 'center',
          gap: '16px',
        }}
      >
        {isAuthor && (
          <InformationTag size="S" typeVariant="default">
            <span
              css={{
                color: theme.neutral.text.weak,
              }}
            >
              작성자
            </span>
          </InformationTag>
        )}
        <Button
          onClick={onClickEdit}
          typeVariant="ghost"
          size="S"
          css={{
            width: 'fit-content',
            padding: '0',
          }}
        >
          <Edit stroke={theme.neutral.text.default} />
          편집
        </Button>
        <Button
          typeVariant="ghost"
          size="S"
          css={{
            width: 'fit-content',
            padding: '0',
          }}
        >
          <Smile stroke={theme.neutral.text.default} />
          반응
        </Button>
      </div>
    </div>
  );
};
