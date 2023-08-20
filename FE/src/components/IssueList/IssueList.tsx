import { styled } from "styled-components";
import { IssueListProps } from "../../type";
import LabelItem from "../SideBar/LabelItem";
import { calculateTime } from "../../utils/calculateTime";
import { useEffect, useState } from "react";

type Props = {
  issue: IssueListProps;
  selectAll: boolean;
  addSelectList(id: number): void;
  removeSelectList(id: number): void;
};

export default function IssueList({
  issue,
  selectAll,
  addSelectList,
  removeSelectList,
}: Props) {
  const [isChecked, setIsChecked] = useState<boolean>(false);

  const toggleCheckbox = () => {
    if (!isChecked) {
      addSelectList(issue.id);
      setIsChecked(true);
      return;
    }
    removeSelectList(issue.id);
    setIsChecked(false);
  };

  useEffect(() => {
    selectAll ? setIsChecked(true) : setIsChecked(false);
  }, [selectAll]);

  return (
    <Wrapper>
      <CheckboxWrapper>
        <Checkbox
          type="checkbox"
          checked={isChecked}
          onChange={toggleCheckbox}
        />
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
                issue.labels.map((label) => (
                  <LabelItem
                    key={label.id}
                    label={label.title}
                    backgroundColor={label.backgroundColor}
                    textColor={label.textColor}
                  />
                ))}
            </Title>
            <Caption>
              <span>#{issue.id}</span>
              <span>
                이 이슈가 {calculateTime(issue.createAt)}, {issue.author}
                님에 의해 작성되었습니다
              </span>
              {issue.milestone && (
                <MilestoneInfo>
                  <MilestoneIcon src="/icons/milestone.svg" />
                  <span>{issue.milestone.title}</span>
                </MilestoneInfo>
              )}
            </Caption>
          </InfoContents>
        </IssueInfo>
        <ImgWrapper>
          <AssigneesProfileImg src={issue.authorProfileUrl} />
        </ImgWrapper>
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
  appearance: none;
  width: 16px;
  height: 16px;
  background-image: url("/icons/checkBoxInitial.svg");
  &:checked {
    border-color: transparent;
    background-image: url("/icons/checkBoxActive.svg");
    background-size: 100% 100%;
    background-repeat: no-repeat;
  }
  &:indeterminate {
    border-color: transparent;
    background-image: url("/icons/checkBoxDisable.svg");
    background-size: 100% 100%;
    background-repeat: no-repeat;
  }
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

const AssigneesProfileImg = styled.img`
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

const ImgWrapper = styled.div`
  display: flex;
  gap: -20px;
`;

const MilestoneInfo = styled.div`
  display: flex;
  align-items: center;
  gap: 8px;
`;

const MilestoneIcon = styled.img`
  width: 16px;
  height: 16px;
  filter: ${({ theme }) => theme.filter.neutral.text.default};
`;
