import { Theme, css, useTheme } from '@emotion/react';
import { border, font, radius } from '../../../styles/styles';
import Button from '../../common/Button';
import { ReactComponent as EditIcon } from '../../../assets/icon/edit.svg';
import { ReactComponent as ArchiveIcon } from '../../../assets/icon/archive.svg';
import { ReactComponent as AlertCircleIcon } from '../../../assets/icon/alertCircle.svg';
import { getTimeLine } from '../../../util/getTimeLine';

type Props = {
  issue: Issue;
  commentCount: number;
};

export default function IssueDetailHeader({ issue, commentCount }: Props) {
  const theme = useTheme();

  return (
    <div css={issueDetailHeader(theme)}>
      <div className="header">
        <div className="title">
          <div className="title-info">FE 이슈트래커 디자인 시스템 구현</div>
          <div className="title-id">#{issue.id}</div>
        </div>
        <div className="buttons">
          <Button
            color={theme.brand.borderDefault}
            backgroundColor="inherit"
            border={`${border.default} ${theme.brand.borderDefault}`}
            value="제목 편집"
          >
            <EditIcon />
          </Button>
          <Button
            color={theme.brand.borderDefault}
            backgroundColor="inherit"
            border={`${border.default} ${theme.brand.borderDefault}`}
            value="이슈 닫기"
          >
            <ArchiveIcon />
          </Button>
        </div>
      </div>
      <div className="issue-info">
        <div className="issue-status">
          <AlertCircleIcon />
          {issue.isOpen ? '열린 이슈' : '닫힌 이슈'}
        </div>
        <div className="issue-detail">
          이 이슈가 {getTimeLine(issue.history.modifiedAt)}에{' '}
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
      background-color: ${theme.palette.blue};
      color: ${theme.brand.textDefault};
      font: ${font.displayMedium12};

      svg path {
        stroke: ${theme.brand.textDefault};
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
