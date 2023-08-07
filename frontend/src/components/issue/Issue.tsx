import { styled, useTheme } from "styled-components";
import { IssueData } from "../../page/main/Main";
import { getElapsedSince } from "../../utils/getElapsedSince";
import { Icon } from "../Icon";
import { InformationTag } from "../InformationTag";

export function Issue({ issue }: { issue: IssueData }) {
  const theme = useTheme();
  const iconColors = {
    issueTitle: theme.color.paletteBlue,
    issueInfo: theme.color.neutralTextWeak,
  };

  return (
    <Div>
      <div>
        <CheckboxLabel>
          <input type="checkbox" />
        </CheckboxLabel>
      </div>
      <IssueContent>
        <IssueTitle>
          <Icon
            name="alertCircle"
            fill={iconColors.issueTitle}
            stroke={iconColors.issueTitle}
          />
          <TitleAnchor>{issue.title}</TitleAnchor>
          {issue.labels.map((label, index) => (
            <InformationTag
              key={index}
              value={label.name}
              size="S"
              fill={label.background}
              fontColor="LIGHT"
            />
          ))}
        </IssueTitle>
        <IssueInfo>
          <span>#{issue.id}</span>
          <span>
            {`이 이슈가 ${getElapsedSince(issue.createdAt)} 전 ${
              issue.writer.name
            }님에 의해 작성되었습니다.`}
          </span>
          {issue.milestone && (
            <span>
              <Icon
                name="milestone"
                fill={iconColors.issueInfo}
                stroke={iconColors.issueInfo}
              />
              {issue.milestone.name}
            </span>
          )}
        </IssueInfo>
      </IssueContent>
      <AssigneesDiv>
        <img
          style={{ width: "20px" }}
          src="https://avatars.githubusercontent.com/u/41321198?v=4"
        />
      </AssigneesDiv>
      <CommentDiv>
        {issue.commentCount !== 0 && (
          <>
            <Icon
              name="comment"
              fill={iconColors.issueInfo}
              stroke={iconColors.issueInfo}
            />
            <span>{issue.commentCount}</span>
          </>
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
  width: 96px;
  display: flex;
  justify-content: right;
  align-items: center;
`;

const CommentDiv = styled.div`
  width: 64px;
  display: flex;
  padding-right: 16px;
  justify-content: right;
  align-items: center;
  gap: 4px;
  cursor: pointer;
`;
