import { styled } from "styled-components";
import Button from "../Button/Button";

export default function IssueList() {
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
              <IssueTitle>FE 이슈 트래커 개발</IssueTitle>
              <div></div>
            </Title>
            <Caption>
              <span>#1</span>
              <span>이 이슈가 8분 전, sam님에 의해 작성되었습니다</span>
              <Button
                icon={"milestone"}
                label={"그룹프로젝트:이슈트래커"}
                type={"ghost"}
                size={"medium"}
                onClick={() => {}}
              />
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

const IssueTitle = styled.span`
  font: ${({ theme }) => theme.font.availableMedium20};
  color: ${({ theme }) => theme.colorSystem.neutral.text.strong};
`;
