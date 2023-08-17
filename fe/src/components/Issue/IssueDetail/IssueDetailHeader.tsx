import { Theme, css, useTheme } from '@emotion/react';
import { border, font, radius } from '../../../styles/styles';
import Button from '../../common/Button';
import { ReactComponent as EditIcon } from '../../../assets/icon/edit.svg';
import { ReactComponent as ArchiveIcon } from '../../../assets/icon/archive.svg';
import { ReactComponent as AlertCircleIcon } from '../../../assets/icon/alertCircle.svg';
import { ReactComponent as XSquareIcon } from '../../../assets/icon/xSquare.svg';
import { getTimeLine } from '../../../util/getTimeLine';
import { useState } from 'react';
import { IssueDetailType } from '../../../type/issue.type';
import { customFetch } from '../../../util/customFetch';
import { getKoreanTime } from '../../../util/getKoreanTime';
import { OnlySuccessRes } from '../../../type/Response.type';

type Props = {
  issue: IssueDetailType;
  commentCount: number;
  onTitleChange: (editedTitle: string) => void;
  onStatusChange: (status: boolean) => void;
};

export default function IssueDetailHeader({
  issue,
  commentCount,
  onTitleChange,
  onStatusChange,
}: Props) {
  const theme = useTheme();
  const [isEditing, setIsEditing] = useState(false);
  const [editedTitle, setEditedTitle] = useState(issue.title);

  const onToggleTitleEditStatus = () => {
    setIsEditing(!isEditing);
  };

  const onChangeIssueStatus = async () => {
    const response = await customFetch<OnlySuccessRes>({
      subUrl: `api/issues/status`,
      method: 'PATCH',
      body: JSON.stringify({
        issueIds: [issue.id],
        issueStatus: issue.isOpen ? 'closed' : 'open',
      }),
    });

    if (response.success) {
      onStatusChange(issue.isOpen);
    }
  };

  const onSubmitEditedTitle = async () => {
    const response = await customFetch<OnlySuccessRes>({
      subUrl: `api/issues/${issue.id}/title`,
      method: 'PATCH',
      body: JSON.stringify({ title: editedTitle }),
    });

    if (response && response.success) {
      onTitleChange(editedTitle);
      setIsEditing(!isEditing);
    }
  };

  const isEdited = editedTitle !== issue.title;

  return (
    <div css={issueDetailHeader(theme)}>
      <div className="header">
        {!isEditing ? (
          <>
            <div className="title">
              <div className="title-info">{issue.title}</div>
              <div className="title-id">#{issue.id}</div>
            </div>
            <div className="buttons">
              <Button
                icon={<EditIcon />}
                color={theme.brand.borderDefault}
                border={`${border.default} ${theme.brand.borderDefault}`}
                value="제목 편집"
                onClick={onToggleTitleEditStatus}
              />
              <Button
                icon={<EditIcon />}
                color={theme.brand.borderDefault}
                border={`${border.default} ${theme.brand.borderDefault}`}
                value={`이슈 ${issue.isOpen ? '닫기' : '열기'}`}
                onClick={onChangeIssueStatus}
              />
            </div>
          </>
        ) : (
          <>
            <div className="title-edit">
              <div className="title-label">제목</div>
              <input
                type="text"
                className="title-input"
                value={editedTitle}
                onChange={(e) => setEditedTitle(e.target.value)}
              />
            </div>
            <div className="buttons">
              <Button
                icon={<XSquareIcon />}
                color={theme.brand.borderDefault}
                border={`${border.default} ${theme.brand.borderDefault}`}
                value="편집 취소"
                onClick={onToggleTitleEditStatus}
              />
              <Button
                icon={<ArchiveIcon />}
                color={theme.brand.textDefault}
                backgroundColor={theme.brand.surfaceDefault}
                border={`${border.default} ${theme.brand.borderDefault}`}
                value="편집 완료"
                onClick={onSubmitEditedTitle}
                disabled={!isEdited}
              />
            </div>
          </>
        )}
      </div>
      <div className="issue-info">
        <div className={`issue-status ${issue.isOpen ? 'open' : 'closed'}`}>
          <AlertCircleIcon />
          {issue.isOpen ? '열린 이슈' : '닫힌 이슈'}
        </div>
        <div className="issue-detail">
          이 이슈가 {getTimeLine(getKoreanTime(issue.history.modifiedAt))}에{' '}
          {issue.history.editor}님에 의해 {issue.isOpen ? '열렸' : '닫혔'}습니다
        </div>
        <div className="break-point">∙</div>
        <div className="comment-count">코멘트 {commentCount}개</div>
      </div>
    </div>
  );
}

const issueDetailHeader = (theme: Theme) => css`
  display: flex;
  flex-direction: column;
  gap: 16px;

  .header {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }

  .title {
    display: flex;
    gap: 8px;

    .title-info {
      font: ${font.displayBold32};
      color: ${theme.neutral.textStrong};
    }

    .title-id {
      font: ${font.displayBold32};
      color: ${theme.neutral.textWeak};
    }
  }

  .title-edit {
    display: flex;
    align-items: center;
    gap: 8px;
    width: 990px;
    height: 48px;
    border-radius: ${radius.large};
    padding: 0 16px;
    background-color: ${theme.neutral.surfaceBold};
    box-sizing: border-box;

    &:focus-within {
      outline: ${border.default} ${theme.neutral.borderDefaultActive};
      background-color: ${theme.neutral.surfaceStrong};
    }

    .title-label {
      width: 64px;
      font: ${font.displayMedium12};
      color: ${theme.neutral.textWeak};
    }

    .title-input {
      border: none;
      flex: 1;
      background-color: inherit;
      font: ${font.displayMedium16};
      color: ${theme.neutral.textDefault};

      &:focus {
        outline: none;
      }
    }
  }

  .buttons {
    display: flex;
    gap: 24px;
  }

  .issue-info {
    display: flex;
    align-items: center;
    gap: 8px;

    .issue-status {
      display: flex;
      align-items: center;
      justify-content: center;
      width: 101px;
      height: 32px;
      gap: 8px;
      border-radius: ${radius.large};
      font: ${font.displayMedium12};

      &.open {
        background-color: ${theme.palette.blue};
        color: ${theme.brand.textDefault};

        svg path {
          stroke: ${theme.brand.textDefault};
        }
      }

      &.closed {
        background-color: ${theme.neutral.borderDefault};
        color: ${theme.neutral.textDefault};

        svg path {
          stroke: ${theme.neutral.textDefault};
        }
      }
    }

    .issue-detail,
    .break-point,
    .comment-count {
      font: ${font.displayMedium16};
      color: ${theme.neutral.textWeak};
    }
  }
`;
