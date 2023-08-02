import { styled } from "styled-components";
import { DropdownContainer } from "./dropdown/DropdownContainer";

type OptionData = {
  name: string;
  profile?: string;
  background?: string;
  color?: "Light" | "Dark";
  selected: boolean;
  onClick: () => void;
};

export function Sidebar({
  assigneeOptions,
  labelOptions,
  milestoneOptions,
}: {
  assigneeOptions: OptionData[];
  labelOptions: OptionData[];
  milestoneOptions: OptionData[];
}) {
  return (
    <Div>
      <ElementDiv>
        <DropdownContainer
          key="assignees"
          name="담당자"
          optionTitle="담당자 설정"
          options={assigneeOptions}
          type="Long"
          alignment="Left"
        />
      </ElementDiv>
      <ElementDiv>
        <DropdownContainer
          key="labels"
          name="레이블 "
          optionTitle="레이블 설정"
          options={labelOptions}
          type="Long"
          alignment="Left"
        />
      </ElementDiv>
      <ElementDiv>
        <DropdownContainer
          key="milestones"
          name="마일스톤 "
          optionTitle="마일스톤 설정"
          options={milestoneOptions}
          type="Long"
          alignment="Left"
        />
      </ElementDiv>
    </Div>
  );
}

const Div = styled.div`
  display: flex;
  width: 288px;
  flex-direction: column;
  align-items: flex-start;
  gap: 1px;
  flex-shrink: 0;
  border: ${({ theme }) =>
    `${theme.border.default} ${theme.color.neutralBorderDefault}`};
  border-radius: ${({ theme }) => theme.radius.large};
  background-color: ${({ theme }) => theme.color.neutralBorderDefault};
`;

const ElementDiv = styled.div`
  display: flex;
  padding: 32px;
  flex-direction: column;
  align-items: flex-start;
  gap: 16px;
  align-self: stretch;
  background-color: ${({ theme }) => theme.color.neutralSurfaceStrong};

  &:first-child {
    border-radius: ${({ theme }) =>
        `${theme.radius.large} ${theme.radius.large}`}
      0px 0px;
  }

  &:last-child {
    border-radius: 0 0
      ${({ theme }) => `${theme.radius.large} ${theme.radius.large}`};
  }
`;
