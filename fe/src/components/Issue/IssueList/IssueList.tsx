import { css } from '@emotion/react';
import { font } from '../../../styles/font';
import { color } from '../../../styles/color';
import { border, radius } from '../../../styles/object';
import CheckBoxIcon from '../../../assets/Icons/CheckBoxIcon';
import AlertCircleIcon from '../../../assets/Icons/AlertCircleIcon';
import ArchiveIcon from '../../../assets/Icons/ArchiveIcon';
import IssueItem from './IssueItem';
import DropdownIndicator from './DropdownIndicator';
import MainWrapper from '../../common/MainWrapper';
import MainWrapperHeader from '../../common/MainWrapperHeader';

export default function IssueList() {
  const filterType = ['담당자', '레이블', '마일스톤', '작성자'];

  return (
    <MainWrapper>
      <MainWrapperHeader variant="issue"></MainWrapperHeader>
      {/* 공통 컴포넌트 재활용 작업 중입니다 */}
      <div css={issueTable}>
        <div css={issueTableHeader}>
          <div css={issueTableHeaderLeft}>
            <CheckBoxIcon status="initial" />
            <div css={openIssue}>
              <AlertCircleIcon color="default" />
              열린 이슈(1)
            </div>
            <div css={closeIssue}>
              <ArchiveIcon />
              닫힌 이슈(0)
            </div>
          </div>
          <div css={issueTableHeaderRight}>
            {/* 임시로 key 값을 index로 지정했습니다  */}
            {filterType.map((filter, index) => (
              <DropdownIndicator key={index} filterText={filter} />
            ))}
          </div>
        </div>
        <ul css={issueItemContainer}>
          <IssueItem />
        </ul>
      </div>
    </MainWrapper>
  );
}

const issueTable = css`
  display: flex;
  flex-direction: column;
  border: ${border.default} ${color.neutral.borderDefault};
  border-radius: ${radius.medium};

  & > * {
    padding: 0 32px;
  }
`;

const issueTableHeader = css`
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 64px;
  border-bottom: ${border.default} ${color.neutral.borderDefault};
  border-radius: ${radius.medium} ${radius.medium} 0 0;
  background-color: ${color.neutral.surfaceDefault};
`;

const issueTableHeaderLeft = css`
  display: flex;
  align-items: center;
  gap: 24px;

  & * {
    display: flex;
    align-items: center;
    gap: 4px;
    cursor: pointer;
  }
`;

const openIssue = css`
  margin-left: 12px;
  font: ${font.selectedBold16};
`;

const closeIssue = css`
  font: ${font.availableMedium16};
`;

const issueTableHeaderRight = css`
  display: flex;
  gap: 32px;
`;

const issueItemContainer = css`
  display: flex;
  flex-direction: column;
  background-color: ${color.neutral.surfaceStrong};
  border-radius: 0 0 ${radius.medium} ${radius.medium};
`;
