import { Theme, css, useTheme } from '@emotion/react';
import { font } from '../../../styles/styles';
import CheckBox from './CheckBox';
import Label from '../../Label/Label';
import { ReactComponent as AlertCircleIcon } from '../../../assets/icon/alertCircle.svg';
import { ReactComponent as MilestoneIcon } from '../../../assets/icon/milestone.svg';
import { getTimeLine } from '../../../util/getTimeLine';
import { Link } from 'react-router-dom';
import { Issue } from '../../../type/issue.type';
import { getKoreanTime } from '../../../util/getKoreanTime';
import UserImageIcon from '../../UserImageIcon';
import { useContext } from 'react';
import { IssueContext } from '../../Context/IssueContext';

type Props = {
  issue: Issue;
  checked: boolean;
};

export default function IssueItem({ issue, checked }: Props) {
  const theme = useTheme();
  const { ...context } = useContext(IssueContext);

  const onSingleCheck = (checked: boolean, id: number) => {
    const updatedItemIdList = checked
      ? [...context.checkedItemIdList, id]
      : context.checkedItemIdList.filter((itemId) => itemId !== id);

    context.setCheckedItemIdList(updatedItemIdList);
  };

  return (
    <li css={issueItem(theme)}>
      <div className="detail-wrapper">
        <div className="detail">
          <div className="title-wrapper">
            <CheckBox
              checkBoxType="selectOne"
              onChange={(e) => onSingleCheck(e.currentTarget.checked, issue.id)}
              checked={checked}
            />
            <div className="title">
              <AlertCircleIcon className="open" />
              <Link to={`/issue/${issue.id}`}>{issue.title}</Link>
              {issue.labels.map((label) => {
                return <Label key={label.id} {...label} />;
              })}
            </div>
          </div>
          <div className="info">
            <div>#{issue.id}</div>
            <div>
              이 이슈가 {getTimeLine(getKoreanTime(issue.history.modifiedAt))},{' '}
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
      <UserImageIcon size="S" url={issue.writer.imageUrl} />
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

          a {
            text-decoration: none;
            color: inherit;

            &:hover {
              text-decoration: underline;
            }
          }

          div {
            cursor: default;
          }

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
