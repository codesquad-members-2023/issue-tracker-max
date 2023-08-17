import { useContext, useId, useState } from 'react';
import { Theme, css, useTheme } from '@emotion/react';
import { border, font, radius } from '../../../styles/styles';
import { ReactComponent as EditIcon } from '../../../assets/icon/edit.svg';
import { ReactComponent as SmileIcon } from '../../../assets/icon/smile.svg';
import { ReactComponent as XSquareIcon } from '../../../assets/icon/xSquare.svg';
import { ReactComponent as ArchiveIcon } from '../../../assets/icon/archive.svg';
import { customFetch } from '../../../util/customFetch';
import { getTimeLine } from '../../../util/getTimeLine';
import { getKoreanTime } from '../../../util/getKoreanTime';
import Label from '../../Label/Label';
import Button from '../../common/Button';
import IssueContent from '../IssueContent';
import UserImageIcon from '../../UserImageIcon';
import { CommentType } from '../../../type/issue.type';
import { UserContext } from '../../Context/UserContext';
import { OnlySuccessRes } from '../../../type/Response.type';

type Props = {
  commentData: CommentType;
  issueId: number;
};

export default function Comment({ commentData, issueId }: Props) {
  const theme = useTheme();
  const id = useId();
  const [isEditing, setIsEditing] = useState(false);
  const [editedComment, setEditedComment] = useState(commentData.content);
  const { ...context } = useContext(UserContext);

  const onChangeEditedComment = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setEditedComment(e.target.value);
  };

  const onToggleCommentEditStatus = () => {
    setIsEditing((prev) => !prev);
  };

  const onSubmitEditedComment = async () => {
    try {
      const response = await customFetch<OnlySuccessRes>({
        method: 'PATCH',
        subUrl: `api/issues/${issueId}/comments/${commentData.id}`,
        body: JSON.stringify({ content: editedComment }),
      });

      if (response.success) {
        setEditedComment(editedComment);
        setIsEditing((prev) => !prev);
      }
    } catch (error) {
      //Memo: 에러 핸들링 필요
      throw error;
    }
  };

  const onAddImg = (imgMarkdown: string) => {
    setEditedComment((prev) => `${prev}\n${imgMarkdown}\n`);
  };

  const userIdString = context.user?.member.id;
  const isWriter = commentData.writer.id === Number(userIdString)!;
  const isEdited = commentData.content !== editedComment;

  return (
    <div css={comment(theme)}>
      <div className="comment">
        <div className="comment-header">
          <div className="writer-info">
            <UserImageIcon size="M" url={commentData.writer.imageUrl} />
            <div className="writer">{commentData.writer.name}</div>
            <div className="time-line">
              {getTimeLine(getKoreanTime(commentData.createdAt))}
            </div>
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
                  icon={<EditIcon />}
                  size="XS"
                  color={theme.neutral.textWeak}
                  value="편집"
                  onClick={onToggleCommentEditStatus}
                />
              </>
            )}
            <Button
              icon={<SmileIcon />}
              size="XS"
              color={theme.neutral.textWeak}
              value="반응"
            />
          </div>
        </div>
        {isEditing ? (
          <IssueContent
            value={editedComment}
            onChange={onChangeEditedComment}
            withInfo={false}
            onAddImg={onAddImg}
          />
        ) : (
          <div className="comment-body">
            {isEdited ? editedComment : commentData.content}
          </div>
        )}
      </div>
      {isEditing && (
        <>
          <div className="edit-buttons">
            <Button
              icon={<XSquareIcon />}
              color={theme.brand.borderDefault}
              border={`${border.default} ${theme.brand.borderDefault}`}
              value="편집 취소"
              onClick={onToggleCommentEditStatus}
            />
            <Button
              icon={<ArchiveIcon />}
              color={theme.brand.textDefault}
              backgroundColor={theme.brand.surfaceDefault}
              border={`${border.default} ${theme.brand.borderDefault}`}
              value="편집 완료"
              onClick={onSubmitEditedComment}
              disabled={!isEdited}
            />
          </div>
        </>
      )}
    </div>
  );
}

const comment = (theme: Theme) => css`
  display: flex;
  flex-direction: column;
  gap: 24px;

  .comment {
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
  }

  .edit-buttons {
    display: flex;
    gap: 16px;
    margin-left: auto;
  }
`;
