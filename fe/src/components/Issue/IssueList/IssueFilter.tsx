import { Theme, css, useTheme } from '@emotion/react';
import CheckBoxIcon from './CheckBox';
import { ReactComponent as AlertCircleIcon } from '../../../assets/icon/alertCircle.svg';
import { ReactComponent as ArchiveIcon } from '../../../assets/icon/archive.svg';
import { border, font, radius } from '../../../styles/styles';
import DropdownIndicator from './DropdownIndicator';
import { filterType } from '../../../constant/constant';

type Props = {
  activeIssue: 'open' | 'closed';
  onCheckBoxClick: (checked: boolean) => void;
  isAllItemChecked: boolean;
  onIssueFilterClick: (filter: 'open' | 'closed') => void;
  openIssueCount: number;
  closedIssueCount: number;
  checkedItemLength: number;
};

export default function IssueFilter({
  activeIssue,
  onCheckBoxClick,
  isAllItemChecked,
  onIssueFilterClick,
  openIssueCount,
  closedIssueCount,
  checkedItemLength,
}: Props) {
  const theme = useTheme();

  const isCheckedListEmpty = checkedItemLength === 0;

  return (
    <div css={issueFilter(theme)}>
      {isCheckedListEmpty ? (
        <div className="issue-filter">
          <div className="left">
            <CheckBoxIcon
              className="check-box"
              id="selectAll"
              onChange={(e) => onCheckBoxClick(e.currentTarget.checked)}
              checked={isAllItemChecked}
            />
            <div className="issue">
              <div
                className={`open-issue ${
                  activeIssue === 'open' ? 'active' : ''
                }`}
                onClick={() => onIssueFilterClick('open')}
              >
                <AlertCircleIcon color="default" />
                {`열린 이슈(${openIssueCount})`}
              </div>
              <div
                className={`close-issue ${
                  activeIssue === 'closed' ? 'active' : ''
                }`}
                onClick={() => onIssueFilterClick('closed')}
              >
                <ArchiveIcon />
                {`닫힌 이슈(${closedIssueCount})`}
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
            <CheckBoxIcon
              className="check-box"
              id="selectAll"
              onChange={(e) => onCheckBoxClick(e.currentTarget.checked)}
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
