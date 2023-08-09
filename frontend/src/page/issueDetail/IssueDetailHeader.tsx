import { styled } from "styled-components";
import { InformationTag } from "../../components/InformationTag";
import { getElapsedSince } from "../../utils/getElapsedSince";
import { IssueDetailTitleInfo } from "./IssueDetailTitleInfo";

type IssueDetailHeaderProps = {
  id: number;
  title: string;
  status: "OPENED" | "CLOSED";
  statusModifiedAt: Date;
  writer: {
    id: number;
    name: string;
    avatarUrl: string;
  };
  comments: {
    id: number;
    userId: string;
    avatarUrl: string;
    content: string;
    createdAt: Date;
    modifiedAt: Date | null;
  }[];
  fetchIssue: () => void;
};

export function IssueDetailHeader({
  id,
  title,
  status,
  statusModifiedAt,
  writer,
  comments,
  fetchIssue,
}: IssueDetailHeaderProps) {
  return (
    <Div>
      <IssueDetailTitleInfo {...{ id, title, status, fetchIssue }} />
      <StatusInfo>
        <InformationTag
          value={`${status === "OPENED" ? "열린" : "닫힌"} 이슈`}
          size="M"
          toolTip={`status: ${status}`}
          icon="AlertCircle"
          fill={status === "OPENED" ? "paletteBlue" : "neutralBorderDefault"}
          fontColor={status === "OPENED" ? "LIGHT" : "DARK"}
        />
        <StatusDescription>
          <p>
            이 이슈가 {getElapsedSince(statusModifiedAt)} 전에 {writer.name}님에
            의해 {status === "OPENED" ? "열렸습니다" : "닫혔습니다"}
          </p>
          <Divider>∙</Divider>
          <p>코멘트 {comments.length}개</p>
        </StatusDescription>
      </StatusInfo>
    </Div>
  );
}

const Div = styled.div`
  align-self: stretch;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 16px;
`;

const StatusInfo = styled.div`
  align-self: stretch;
  display: flex;
  align-items: center;
  gap: 8px;
`;

const StatusDescription = styled.div`
  display: flex;
  align-items: center;
  gap: 8px;
  font: ${({ theme }) => theme.font.displayMedium16};
  color: ${({ theme }) => theme.color.neutralTextWeak};
`;

const Divider = styled.div``;
