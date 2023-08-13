import { Theme, css, useTheme } from '@emotion/react';
import { ReactComponent as UserImageLargeIcon } from '../../../assets/icon/userImageLarge.svg';
import { ReactComponent as EditIcon } from '../../../assets/icon/edit.svg';
import { ReactComponent as SmileIcon } from '../../../assets/icon/smile.svg';
import { getTimeLine } from '../../../util/getTimeLine';
import Label from '../../Label/Label';
import { useId } from 'react';
import Button from '../../common/Button';
import { border, font, radius } from '../../../styles/styles';

// {
//   id: 1,
//   writer: {
//     id: 4,
//     name: 'Jeegu',
//   },
//   content: '새로운 내용',
//   dateTime: '2023-07-25 11:44',
// }

type Props = {
  commentData: CommentType;
};

export default function Comment({ commentData }: Props) {
  const theme = useTheme();
  const id = useId();

  const userIdString = localStorage.getItem('userId');
  const userId = userIdString ? parseInt(userIdString) : null;
  const isWriter = commentData.writer.id === userId;

  return (
    <div css={comment(theme)}>
      <div className="comment-header">
        <div className="writer-info">
          <UserImageLargeIcon />
          <div className="writer">{commentData.writer.name}</div>
          <div className="time-line">{getTimeLine(commentData.dateTime)}</div>
        </div>
        <div className="buttons">
          {isWriter && (
            <>
              <Label
                id={id}
                textColor={theme.neutral.textWeak}
                backgroundColor="inherit"
                title="작성자"
              />
              <Button
                size="XS"
                color={theme.neutral.textWeak}
                backgroundColor="inherit"
                value="편집"
              >
                <EditIcon />
              </Button>
            </>
          )}
          <Label
            id={id}
            textColor={theme.neutral.textWeak}
            backgroundColor="inherit"
            title="작성자"
          />
          <Button
            size="XS"
            color={theme.neutral.textWeak}
            backgroundColor="inherit"
            value="편집"
          >
            <EditIcon />
          </Button>
          <Button
            size="XS"
            color={theme.neutral.textWeak}
            backgroundColor="inherit"
            value="반응"
          >
            <SmileIcon />
          </Button>
        </div>
      </div>
      <div className="comment-body">{commentData.content}</div>
    </div>
  );
}

const comment = (theme: Theme) => css`
  display: flex;
  flex-direction: column;
  border: ${border.default} ${theme.neutral.borderDefault};
  border-radius: ${radius.large};

  .comment-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 0 24px;
    border-radius: ${radius.large} ${radius.large} 0 0;
    border-bottom: ${border.default} ${theme.neutral.borderDefault};

    .writer-info {
      display: flex;
      align-items: center;
      gap: 8px;
      height: 64px;

      .writer {
        font: ${font.displayMedium16};
        color: ${theme.neutral.textDefault};
      }

      .time-line {
        font: ${font.displayMedium16};
        color: ${theme.neutral.textWeak};
      }
    }

    .buttons {
      display: flex;
      align-items: center;
      gap: 16px;

      > div {
        border: ${border.default} ${theme.neutral.textWeak};
      }
    }
  }

  .comment-body {
    padding: 16px 24px 24px;
    border-radius: 0 0 ${radius.large} ${radius.large};
    font: ${font.displayMedium16};
    color: ${theme.neutral.textDefault};
    background-color: ${theme.neutral.surfaceStrong};
  }
`;
