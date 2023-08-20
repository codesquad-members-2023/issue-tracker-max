import { useNavigate } from "react-router-dom";
import { styled } from "styled-components";
import { Avatar } from "../../components/Avatar";
import { InformationTag } from "../../components/InformationTag";
import { Icon } from "../../components/icon/Icon";
import { Color, ThemeColorKeys } from "../../types/colors";
import { getElapsedSince } from "../../utils/getElapsedSince";
import { IssueData } from "./Main";

export function MainTableElement({
  issue,
  inputChecked,
  handleCheckedIssue,
}: {
  issue: IssueData;
  inputChecked: boolean;
  handleCheckedIssue: (value: number) => void;
}) {
  const navigate = useNavigate();

  const iconColors: {
    [key: string]: ThemeColorKeys;
  } = {
    issueTitle: "paletteBlue",
    issueInfo: "neutralTextWeak",
  };

  const navigateToIssueDetail = () => {
    navigate(`/issues/${issue.id}`);
  };

  return (
    <Div>
      <div>
        <CheckboxLabel>
          <input
            type="checkbox"
            onChange={() => handleCheckedIssue(issue.id)}
            checked={inputChecked}
          />
        </CheckboxLabel>
      </div>
      <IssueContent>
        <IssueTitle>
          <Icon name="AlertCircle" color={iconColors.issueTitle} />
          <TitleAnchor onClick={navigateToIssueDetail}>
            {issue.title}
          </TitleAnchor>
          {issue.labels.map((label, index) => (
            <InformationTag
              key={index}
              value={label.name}
              size="S"
              fill={label.background as Color}
              fontColor="LIGHT"
            />
          ))}
        </IssueTitle>
        <IssueInfo>
          <span>#{issue.id}</span>
          <span>
            {`이 이슈가 ${getElapsedSince(issue.createdAt)} 전 ${
              issue.author.name
            }님에 의해 작성되었습니다.`}
          </span>
          {issue.milestones && (
            <span>
              <Icon name="Milestone" color={iconColors.issueInfo} />
              {issue.milestones.name}
            </span>
          )}
        </IssueInfo>
      </IssueContent>
      <AssigneesDiv>
        {issue.assignees.map((assignee, index) => {
          return (
            <Avatar
              key={index}
              size="S"
              src={assignee.avatarUrl}
              userId={assignee.name}
            />
          );
        })}
      </AssigneesDiv>
      <CommentDiv>
        {issue.commentCount !== 0 && (
          <a onClick={navigateToIssueDetail}>
            <Icon name="Comment" color={iconColors.issueInfo} />
            <span>{issue.commentCount}</span>
          </a>
        )}
      </CommentDiv>
    </Div>
  );
}

const Div = styled.div`
  width: 100%;
  height: 96px;
  display: flex;
  align-self: stretch;
  padding: 16px 0px;
  box-sizing: border-box;
  border-top: solid 1px ${({ theme }) => theme.color.neutralBorderDefault};
`;

const CheckboxLabel = styled.label`
  display: block;
  text-align: center;
  width: 48px;
  height: 100%;
  padding: 8px;
  box-sizing: border-box;
`;

const TitleAnchor = styled.a`
  cursor: pointer;
  font: ${({ theme }) => theme.font.displayMedium20};
  color: ${({ theme }) => theme.color.neutralTextStrong};
`;

const IssueContent = styled.div`
  display: flex;
  flex-direction: column;
  gap: 8px;
  flex: 1;
`;

const IssueTitle = styled.div`
  display: flex;
  align-items: center;
  gap: 8px;
`;

const IssueInfo = styled.div`
  display: inline-flex;
  align-items: flex-start;
  gap: 16px;

  span {
    display: flex;
    align-items: center;
    gap: 8px;
    font: ${({ theme }) => theme.font.displayMedium16};
    color: ${({ theme }) => theme.color.neutralTextWeak};
  }
`;

const AssigneesDiv = styled.div`
  display: flex;
  flex: 1;
  justify-content: flex-end;
  align-items: center;
  position: relative;
  overflow: hidden;

  & > div {
    margin-left: -10px;
    transition: all 0.3s ease;
  }

  &:hover > div {
    margin-left: 0;
  }
`;

const CommentDiv = styled.div`
  width: 64px;
  display: flex;
  padding-right: 16px;
  justify-content: right;
  align-items: center;
  gap: 4px;
  cursor: pointer;

  & a {
    display: flex;
    gap: 5px;
  }

  & span {
    color: ${({ theme }) => theme.color.neutralTextStrong};
  }
`;
