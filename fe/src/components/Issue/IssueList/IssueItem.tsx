import { Theme, css, useTheme } from '@emotion/react';
import { font } from '../../../styles/styles';
import CheckBoxIcon from './CheckBox';
import Label from '../../Label/Label';
import { ReactComponent as AlertCircleIcon } from '../../../assets/icon/alertCircle.svg';
import { ReactComponent as MilestoneIcon } from '../../../assets/icon/milestone.svg';
import { ReactComponent as UserImageSmallIcon } from '../../../assets/icon/userImageSmall.svg';

type Props = {
  issue: Issue;
  onSingleCheck: (checked: boolean, id: number) => void;
  checked: boolean;
};

export default function IssueItem({ issue, onSingleCheck, checked }: Props) {
  const theme = useTheme();

  const getTimeLine = (timestamp: string) => {
    const now = new Date();
    const pastDate = new Date(timestamp);
    const timeDifference = now.getTime() - pastDate.getTime();

    const minute = 60 * 1000;
    const hour = 60 * minute;
    const day = 24 * hour;

    if (timeDifference < minute) {
      return `${Math.floor(timeDifference / 1000)}초 전`;
    } else if (timeDifference < hour) {
      return `${Math.floor(timeDifference / minute)}분 전`;
    } else if (timeDifference < day) {
      return `${Math.floor(timeDifference / hour)}시간 전`;
    } else {
      return `${Math.floor(timeDifference / day)}일 전`;
    }
  };

  return (
    <li css={issueItem(theme)}>
      <div className="detail-wrapper">
        <div className="detail">
          <div className="title-wrapper">
            <CheckBoxIcon
              id={issue.id.toString()}
              onChange={(e) => onSingleCheck(e.currentTarget.checked, issue.id)}
              checked={checked}
            />
            <div className="title">
              <AlertCircleIcon className="open" />
              {issue.title}
              {issue.labels.map((label) => {
                return <Label key={label.id} {...label} />;
              })}
            </div>
          </div>
          <div className="info">
            <div>#{issue.id}</div>
            <div>
              이 이슈가 {getTimeLine(issue.history.modifiedAt)},{' '}
              {issue.history.editor}님에 의해 수정되었습니다
            </div>
            <div className="milestone-info">
              {!!issue.milestone && (
                <>
                  <MilestoneIcon className="milestone-icon" />
                  {issue.milestone.title}
                </>
              )}
            </div>
          </div>
        </div>
      </div>
      <UserImageSmallIcon />
    </li>
  );
}

const issueItem = (theme: Theme) => css`
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 96px;

  .detail-wrapper {
    display: flex;

    .detail {
      display: flex;
      flex-direction: column;
      gap: 14px;

      .title-wrapper {
        display: flex;
        align-items: center;
        gap: 36px;

        .title {
          display: flex;
          align-items: center;
          gap: 8px;
          font: ${font.availableMedium20};
          color: ${theme.neutral.textStrong};

          & .open path {
            stroke: ${theme.palette.blue};
          }
        }
      }

      .info {
        display: flex;
        align-items: center;
        margin-left: 52px;
        gap: 16px;
        font: ${font.displayMedium16};
        color: ${theme.neutral.textWeak};

        .milestone-info {
          display: flex;
          align-items: center;
          gap: 8px;

          .milestone-icon path {
            fill: ${theme.neutral.textDefault};
            stroke: none;
          }
        }
      }
    }
  }
`;
