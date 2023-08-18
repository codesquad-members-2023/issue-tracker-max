import { useState } from 'react';
import { Theme, css, useTheme } from '@emotion/react';
import { border, radius } from '../../../styles/styles';
import TableContainer from '../../TableContainer';
import IssueFilter from './IssueFilter';
import { useNavigate } from 'react-router-dom';
import { FilterProvider } from '../../Context/IssueContext';
import IssueItemContainer from './IssueItemContainer';
import IssueNavBar from './IssueNavBar';

export default function IssueList() {
  const theme = useTheme();
  const navigate = useNavigate();
  const [activeIssue, setActiveIssue] = useState<'open' | 'closed'>('open');

  const onIssueFilterClick = (issueFilter: 'open' | 'closed') => {
    setActiveIssue(issueFilter);
  };

  const onClickToCreate = () => {
    navigate('/issue-create');
  };

  return (
    <>
      <FilterProvider>
        <IssueNavBar buttonValue="이슈 작성" onClick={onClickToCreate} />
        <TableContainer>
          <div css={issueTable(theme)}>
            <div className="header">
              <IssueFilter
                activeIssue={activeIssue}
                onIssueFilterClick={onIssueFilterClick}
              />
            </div>
            <IssueItemContainer />
          </div>
        </TableContainer>
      </FilterProvider>
    </>
  );
}

const issueTable = (theme: Theme) => css`
  display: flex;
  flex-direction: column;
  border-radius: ${radius.medium};
  color: ${theme.neutral.textDefault};

  .item-container {
    display: flex;
    flex-direction: column;
    border: ${border.default} ${theme.neutral.borderDefault};
    border-top: none;
    border-radius: 0 0 ${radius.medium} ${radius.medium};
    background-color: ${theme.neutral.surfaceStrong};

    li {
      box-sizing: border-box;
      padding: 0 32px;
      border-bottom: ${border.default} ${theme.neutral.borderDefault};

      &:last-child {
        border-bottom: none;
        border-radius: 0 0 ${radius.medium} ${radius.medium};
      }
    }
  }
`;
