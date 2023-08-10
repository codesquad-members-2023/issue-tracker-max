import { styled } from "styled-components";
import Button from "../common/Button/Button";
import { IssueListProps } from "../../type";
import LabelItem from "../SideBar/LabelItem";
import { calculateTime } from "../../utils/calculateTime";

type Props = {
  issue: IssueListProps;
};

export default function IssueList({ issue }: Props) {
  return (
    <Wrapper>
      <CheckboxWrapper>
        <Checkbox type={"checkbox"} />
      </CheckboxWrapper>
      <Contents>
        <IssueInfo>
          <InfoContents>
            <Title>
              <IssueIcon src={"/icons/alertCircle.svg"} alt="" />
              <IssueTitle href={`/detail/${issue.id}`}>
                {issue.title}
              </IssueTitle>
              {issue.labels &&
                issue.labels.map((label, key) => (
                  <LabelItem
                    key={key}
                    label={label.title}
                    color={label.color}
                  />
                ))}
            </Title>
            <Caption>
              <span>#{issue.id}</span>
              <span>
                이 이슈가 {calculateTime(issue.createAt)}, {issue.author}
                님에 의해 작성되었습니다
              </span>
              {issue.milestone !== null && (
                <Button
                  icon={"milestone"}
                  label={issue.milestone.title}
                  type={"ghost"}
                  size={"medium"}
                  onClick={() => {}}
                />
              )}
            </Caption>
          </InfoContents>
        </IssueInfo>
        <AuthorProfileImg src={"/logo/profile.jpg"} />
      </Contents>
    </Wrapper>
  );
}

const Wrapper = styled.li`
  position: relative;
  display: flex;
  padding: 0px 32px;
  width: 100%;
  height: 96px;
  gap: 32px;
  background-color: ${({ theme }) => theme.colorSystem.neutral.surface.strong};
  &::after {
    content: "";
    position: absolute;
    top: 0px;
    left: 0px;
    height: 1px;
    width: 100%;
    background-color: ${({ theme }) =>
      theme.colorSystem.neutral.border.default};
  }
`;

const CheckboxWrapper = styled.div`
  padding: 24px 0px 56px 0px;
  width: 16px;
  height: 100%;
`;

const Checkbox = styled.input`
  margin: 0px;
  width: 16px;
  height: 16px;
`;

const Contents = styled.div`
  display: flex;
  width: 1168px;
  height: 100%;
  justify-content: space-between;
  align-items: center;
`;

const IssueInfo = styled.div``;

const InfoContents = styled.div`
  display: flex;
  flex-direction: column;
  gap: 8px;
`;

const AuthorProfileImg = styled.img`
  width: 20px;
  height: 20px;
  border-radius: ${({ theme }) => theme.radius.half};
`;

const Title = styled.div`
  display: flex;
  gap: 8px;
  align-items: center;
`;

const Caption = styled.div`
  display: flex;
  align-items: center;
  height: 24px;
  gap: 16px;
  font: ${({ theme }) => theme.font.displayMedium16};
  color: ${({ theme }) => theme.colorSystem.neutral.text.weak};
`;

const IssueIcon = styled.img`
  width: 16px;
  height: 16px;
  filter: ${({ theme }) => theme.filter.brand.text.weak};
`;

const IssueTitle = styled.a`
  font: ${({ theme }) => theme.font.availableMedium20};
  color: ${({ theme }) => theme.colorSystem.neutral.text.strong};
`;
