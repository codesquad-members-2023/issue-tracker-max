import { styled } from "styled-components";
import { Button } from "../../components/Button";
import { ContentBox } from "../../components/ContentBox";
import { InformationTag } from "../../components/InformationTag";
import { getElapsedSince } from "../../utils/getElapsedSince";

type IssueContentProps = {
  id: number;
  content: string;
  createdAt: Date;
  modifiedAt: Date | null;
  writer: {
    id: number;
    name: string;
    avatarUrl: string;
  };
};

export function IssueContent({
  id,
  content,
  createdAt,
  modifiedAt,
  writer,
}: IssueContentProps) {
  // TODO: 이슈 내용 수정

  const writtenAt = modifiedAt ?? createdAt;

  return (
    <ContentBox
      header={
        <>
          <WriterInfo>
            {writer.avatarUrl && <img src={writer.avatarUrl} alt="아바타" />}
            <h3>{writer.name}</h3>
            <TimeStamp>{getElapsedSince(writtenAt)} 전</TimeStamp>
          </WriterInfo>
          <ControlButtons>
            <InformationTag
              value="작성자"
              size="S"
              fill="neutralSurfaceBold"
              stroke="Default"
            />
            <Button height={32} size="S" buttonType="Ghost" icon="Edit">
              편집
            </Button>
            <Button height={32} size="S" buttonType="Ghost" icon="Smile">
              반응
            </Button>
          </ControlButtons>
        </>
      }
      body={<ContentDiv>{content}</ContentDiv>}
    />
  );
}

const WriterInfo = styled.div`
  display: flex;
  align-items: center;
  gap: 8px;
  font: ${({ theme }) => theme.font.displayMedium16};
  color: ${({ theme }) => theme.color.neutralTextDefault};
`;

const TimeStamp = styled.span`
  color: ${({ theme }) => theme.color.neutralTextWeak};
`;

const ControlButtons = styled.div`
  display: flex;
  align-items: center;
  gap: 16px;

  & button span {
    padding: 0;
    padding-left: 4px;
  }
`;

const ContentDiv = styled.div`
  flex: 1 0 0;
  font: ${({ theme }) => theme.font.displayMedium16};
  color: ${({ theme }) => theme.color.neutralTextDefault};
`;
