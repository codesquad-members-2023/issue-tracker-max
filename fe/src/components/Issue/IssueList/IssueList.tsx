import { useEffect, useState } from 'react';
import { Theme, css, useTheme } from '@emotion/react';
import { border, radius } from '../../../styles/styles';
import IssueItem from './IssueItem';
import TableContainer from '../../TableContainer';
import SubNavBar from '../../SubNavbar';
import IssueFilter from './IssueFilter';
import { useNavigate } from 'react-router-dom';
import { customFetch } from '../../../util/customFetch';

export default function IssueList() {
  const theme = useTheme();
  const navigate = useNavigate();
  const [activeIssue, setActiveIssue] = useState<'open' | 'closed'>('open');
  const [checkedItemIdList, setCheckedItemIdList] = useState<number[]>([]); // Question: 빈 배열을 넣어서 타입에러를 해결했는데 괜찮을까요?
  const [issueList, setIssueList] = useState<IssueData>();

  useEffect(() => {
    (async () => {
      const subUrl = 'api/';

      try {
        const issueData = await customFetch<IssueResponse>({ subUrl });

        if (issueData.success && issueData.data) {
          setIssueList(issueData.data);
        }
      } catch (error) {
        navigate('/sign-in');
      }
    })();
  }, []);

  const onIssueFilterClick = (issueFilter: 'open' | 'closed') => {
    setActiveIssue(issueFilter);
  };

  const onSingleCheck = (checked: boolean, id: number) => {
    if (checked) {
      setCheckedItemIdList((prev) => [...prev, id]);
    } else {
      setCheckedItemIdList(checkedItemIdList.filter((itemId) => itemId !== id));
    }
  };

  const onAllCheck = (checked: boolean) => {
    if (checked) {
      setCheckedItemIdList(allItemIdList);
    } else {
      setCheckedItemIdList([]);
    }
  };

  const onClickToCreate = () => {
    navigate('/issue-create');
  };

  const allItemIdList = issueList?.issues.map((item: Issue) => item.id) || [];
  const isAllItemChecked =
    allItemIdList.length !== 0 &&
    allItemIdList.length === checkedItemIdList.length;

  return (
    <>
      {!!issueList && (
        <>
          <SubNavBar
            isIssue
            buttonValue="이슈 작성"
            labelCount={issueList.labelCount}
            milestoneCount={issueList.milestoneCount}
            onClick={onClickToCreate}
          />
          <TableContainer>
            <div css={issueTable(theme)}>
              <div className="header">
                <IssueFilter
                  activeIssue={activeIssue}
                  onCheckBoxClick={onAllCheck}
                  isAllItemChecked={isAllItemChecked}
                  onIssueFilterClick={onIssueFilterClick}
                  openIssueCount={issueList.openIssueCount}
                  closedIssueCount={issueList.closedIssueCount}
                  checkedItemLength={checkedItemIdList.length}
                />
              </div>
              <ul className="item-container">
                {issueList.issues.map((item) => {
                  return (
                    <IssueItem
                      key={item.id}
                      issue={item}
                      onSingleCheck={onSingleCheck}
                      checked={checkedItemIdList.includes(item.id)}
                    />
                  );
                })}
              </ul>
            </div>
          </TableContainer>
        </>
      )}
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
