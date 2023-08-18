import { Theme, css, useTheme } from '@emotion/react';
import CheckBox from './CheckBox';
import { ReactComponent as AlertCircleIcon } from '../../../assets/icon/alertCircle.svg';
import { ReactComponent as ArchiveIcon } from '../../../assets/icon/archive.svg';
import { border, font, radius } from '../../../styles/styles';
import DropdownIndicator from './DropdownIndicator';
import { filterType } from '../../../constant/constant';
import { useContext } from 'react';
import { IssueContext } from '../../Context/IssueContext';

type Props = {
  activeIssue: 'open' | 'closed';
  onIssueFilterClick: (filter: 'open' | 'closed') => void;
};

export default function IssueFilter({
  activeIssue,
  onIssueFilterClick,
}: Props) {
  const theme = useTheme();
  const { ...context } = useContext(IssueContext);

  const onAllCheck = (checked: boolean) => {
    if (checked) {
      context.setCheckedItemIdList(
        context.issueList.issues.map((issue) => issue.id)
      );
    } else {
      context.setCheckedItemIdList([]);
    }
  };

  const onOpenIssueFilterClick = () => {
    context.setIssueIsOpen(true);
    onIssueFilterClick('open');
  };

  const onClosedIssueFilterClick = () => {
    context.setIssueIsOpen(false);
    onIssueFilterClick('closed');
  };

  const checkedItemLength = context.checkedItemIdList.length;
  const allItemCount = context.issueList.issues.length;
  const isAllItemChecked =
    allItemCount !== 0 && allItemCount === checkedItemLength;

  return (
    <div css={issueFilter(theme)}>
      {checkedItemLength === 0 ? (
        <div className="issue-filter">
          <div className="left">
            <CheckBox
              className="check-box"
              checkBoxType="selectAll"
              onChange={(e) => onAllCheck(e.currentTarget.checked)}
              checked={isAllItemChecked}
            />
            <div className="issue">
              <div
                className={`open-issue ${
                  activeIssue === 'open' ? 'active' : ''
                }`}
                onClick={onOpenIssueFilterClick}
              >
                <AlertCircleIcon color="default" />
                {`열린 이슈(${context.issueList.openIssueCount})`}
              </div>
              <div
                className={`close-issue ${
                  activeIssue === 'closed' ? 'active' : ''
                }`}
                onClick={onClosedIssueFilterClick}
              >
                <ArchiveIcon />
                {`닫힌 이슈(${context.issueList.closedIssueCount})`}
              </div>
            </div>
          </div>
          <div className="filter-button">
            {filterType.map((filter) => (
              <DropdownIndicator key={filter.id} filterText={filter.name} />
            ))}
          </div>
        </div>
      ) : (
        <div className="issue-filter">
          <div className="check-info">
            <CheckBox
              className="check-box"
              checkBoxType="selectAll"
              onChange={(e) => onAllCheck(e.currentTarget.checked)}
              checked={isAllItemChecked}
            />
            <div className="selected-item-info">{`${checkedItemLength}개 이슈 선택`}</div>
          </div>
          <DropdownIndicator filterText="상태 수정" />
        </div>
      )}
    </div>
  );
}

const issueFilter = (theme: Theme) => css`
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 64px;
  padding: 0 32px;
  border-radius: ${radius.medium} ${radius.medium} 0 0;
  border: ${border.default} ${theme.neutral.borderDefault};
  background-color: ${theme.neutral.surfaceDefault};

  .issue-filter {
    width: 100%;
    display: flex;
    justify-content: space-between;

    .left {
      display: flex;
      align-items: center;
      gap: 36px;
      cursor: pointer;

      .check-box {
        display: flex;
      }

      .issue {
        display: flex;
        gap: 24px;

        .open-issue,
        .close-issue {
          display: flex;
          align-items: center;
          gap: 4px;
          font: ${font.availableMedium16};
        }

        & .active {
          font: ${font.selectedBold16};
          color: ${theme.neutral.textStrong};

          svg path {
            stroke: ${theme.neutral.textStrong};
          }
        }
      }
    }

    .filter-button {
      display: flex;
      gap: 32px;
    }

    .check-info {
      display: flex;
      align-items: center;
      gap: 36px;

      .check-box {
        display: flex;
      }
    }

    .selected-item-info {
      font: ${font.displayBold16};
      color: ${theme.neutral.textDefault};
    }
  }
`;
