import styled from "styled-components";
import { Icon } from "components/Common/Icon/Icon";
import { Tag } from "components/Common/Tag/Tag";

interface IssueItem {
  id: number;
  title: string;
  author: string;
  assigneeProfiles?: string[];
  milestone?: string;
  createdAt: string;
  labels?: { name: string; backgroundColor: string; textColor: string }[];
}

interface IssueTableItemProps {
  issueItem: IssueItem;
}

export const IssueTableItem: React.FC<IssueTableItemProps> = ({
  issueItem,
}) => {
  const getRelativeTime = (timestamp: string) => {
    const now = new Date();
    const time = new Date(timestamp);

    const diffInSeconds = Math.floor((now.getTime() - time.getTime()) / 1000);
    const diffInMinutes = Math.floor(diffInSeconds / 60);
    const diffInHours = Math.floor(diffInMinutes / 60);
    const diffInDays = Math.floor(diffInHours / 24);

    const rtf = new Intl.RelativeTimeFormat("ko", { numeric: "auto" });

    if (diffInDays > 0) {
      return rtf.format(-diffInDays, "day");
    } else if (diffInHours > 0) {
      return rtf.format(-diffInHours, "hour");
    } else if (diffInMinutes > 0) {
      return rtf.format(-diffInMinutes, "minute");
    } else {
      return "just now";
    }
  };

  const relativeTime = getRelativeTime(issueItem.createdAt);

  return (
    <TableItem>
      <div>
        <CheckboxBox>
          <Icon icon="CheckBoxInitial" stroke="paletteBlue" />
        </CheckboxBox>
        <InfoBox>
          <TitleBox>
            <Icon icon="AlertCircle" stroke="paletteBlue" />
            <p>{issueItem.title}</p>
            {issueItem.labels &&
              issueItem.labels.map((label) => (
                <Tag
                  key={label.name}
                  text={label.name}
                  color={label.textColor}
                  $backgroundColor={label.backgroundColor}
                  $border
                  size="S"
                />
              ))}
          </TitleBox>
          <SummaryBox>
            <span>#{issueItem.id}</span>
            <span>
              이 이슈가 {relativeTime}, {issueItem.author}님에 의해
              작성되었습니다.
            </span>
            {issueItem.milestone && (
              <span>
                <Icon icon="Milestone" fill="nuetralTextDefault" />
                <p>{issueItem.milestone}</p>
              </span>
            )}
          </SummaryBox>
        </InfoBox>
      </div>
      <AssigneesProfileBox>
        {issueItem.assigneeProfiles &&
          issueItem.assigneeProfiles.map((url) => (
            <li key={url}>
              <img src={url} width={20} />
            </li>
          ))}
      </AssigneesProfileBox>
    </TableItem>
  );
};

const TableItem = styled.li`
  display: flex;
  padding: 0px 32px;
  height: 96px;
  border: 1px solid black;
  justify-content: space-between;
  border: ${({ theme: { border } }) => border.default};
  border-color: ${({ theme: { color } }) => color.nuetralBorderDefault};
  border-top: none;
  background-color: ${({ theme: { color } }) => color.nuetralSurfaceStrong};

  > div {
    display: flex;
    gap: 32px;
  }
`;

const CheckboxBox = styled.div`
  padding-top: 24px;
`;

const InfoBox = styled.div`
  display: flex;
  flex-direction: column;
  align-content: center;
  justify-content: center;
  gap: 8px;
  :last-child {
    display: flex;
    align-items: center;
    svg {
      margin-right: 8px;
    }
  }
`;

const TitleBox = styled.div`
  display: flex;
  align-items: center;
  gap: 8px;
  font: ${({ theme: { font } }) => font.availableM20};
  color: ${({ theme: { color } }) => color.nuetralTextStrong};
`;

const SummaryBox = styled.div`
  font: ${({ theme: { font } }) => font.displayM16};
  color: ${({ theme: { color } }) => color.nuetralTextWeak};
  display: flex;
  gap: 16px;
`;

const AssigneesProfileBox = styled.ul`
  list-style: none;
  display: flex;
  align-items: center;
  li {
    margin-right: -6px;
  }
  &:last-child {
    margin-right: 0;
  }
`;
