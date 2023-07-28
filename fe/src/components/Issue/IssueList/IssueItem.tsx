import { css } from '@emotion/react';
import { color } from '../../../styles/color';
import { font } from '../../../styles/font';
import { border, radius } from '../../../styles/object';
import AlertCircleIcon from '../../../assets/Icons/AlertCircleIcon';
import CheckBoxIcon from '../../../assets/Icons/CheckBoxIcon';
import MilestoneIcon from '../../../assets/Icons/MilestoneIcon';

export default function IssueItem() {
  return (
    <li css={issueItem}>
      <div css={issueItemLeft}>
        <div css={issueInfoWrapper}>
          <div css={issueTitleWrapper}>
            <CheckBoxIcon status="initial" />
            <div css={issueTitle}>
              <AlertCircleIcon color="blue" />
              이슈 제목
            </div>
          </div>
          <div css={issueInfo}>
            <div>#이슈번호</div>
            <div>작성자 및 타임스탬프 정보</div>
            <div css={milestoneInfo}>
              <MilestoneIcon />
              마일스톤
            </div>
          </div>
        </div>
      </div>
      <div css={issueItemRight}></div>
    </li>
  );
}

const issueItem = css`
  display: flex;
  align-items: center;
  height: 96px;
  border-bottom: ${border.default} ${color.neutral.borderDefault};

  &:last-child {
    border-bottom: none;
    border-radius: 0 0 ${radius.medium} ${radius.medium};
  }
`;

const issueItemLeft = css`
  display: flex;
`;

const issueInfoWrapper = css`
  display: flex;
  flex-direction: column;
  gap: 14px;
`;

const issueTitleWrapper = css`
  display: flex;
  align-items: center;
  gap: 36px;
`;

const issueTitle = css`
  display: flex;
  align-items: center;
  gap: 8px;
  font: ${font.availableMedium20};
  color: ${color.neutral.textStrong};
`;

const issueInfo = css`
  display: flex;
  align-items: center;
  margin-left: 52px;
  gap: 16px;
  font: ${font.displayMedium16};
  color: ${color.neutral.textWeak};
`;

const milestoneInfo = css`
  display: flex;
  align-items: center;
  gap: 8px;
`;

const issueItemRight = css`
  display: flex;
  gap: 32px;

  & > button {
    background-color: inherit;
    display: flex;
    align-items: center;
    justify-content: space-between;
    width: 80px;
    height: 32px;
  }
`;
