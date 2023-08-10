import calendarIcon from "@assets/icon/calendar.svg";
import milestoneIcon from "@assets/icon/milestone.svg";
import { Milestone } from "@customTypes/index";
import styled from "styled-components";
import { TableBodyItem } from "../Table.style";

export default function MilestonesTableItem({
  milestone,
}: {
  milestone: Milestone;
}) {
  const { milestoneName, dueDate, description } = milestone;

  return (
    <StyledMilestoneItem>
      <LeftWrapper>
        <div className="milestone-info">
          <img src={milestoneIcon} alt="마일스톤 아이콘" />
          <MilestoneName>{milestoneName}</MilestoneName>
          <MilestoneDueDate>
            <img src={calendarIcon} alt="캘린더 아이콘" />
            <span>{dueDate}</span>
          </MilestoneDueDate>
        </div>
        <div className="milestone-description">{description}</div>
      </LeftWrapper>

      <RightWrapper>
        <div>오른쪽</div>
      </RightWrapper>
    </StyledMilestoneItem>
  );
}

const StyledMilestoneItem = styled(TableBodyItem)`
  padding: 32px;
  gap: 32px;
`;

const LeftWrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 8px;

  .milestone-info {
    display: flex;
    width: 100%;
    gap: 8px;

    > img {
      filter: ${({ theme: { filter } }) => filter.neutralTextDefault};
    }
  }

  .milestone-description {
    font: ${({ theme: { font } }) => font.availableMD16};
    color: ${({ theme: { neutral } }) => neutral.text.weak};
  }
`;

const RightWrapper = styled.div`
  display: flex;
  gap: 24px;

  .tab-button-icon {
    filter: ${({ theme: { filter } }) => filter.neutralTextDefault};

    &.delete {
      filter: ${({ theme: { filter } }) => filter.dangerTextDefault};
    }
  }

  .tab-button-text {
    font: ${({ theme: { font } }) => font.availableMD12};
    color: ${({ theme: { neutral } }) => neutral.text.default};

    &.delete {
      color: ${({ theme: { danger } }) => danger.text.default};
    }
  }
`;

const MilestoneName = styled.span`
  font: ${({ theme: { font } }) => font.availableMD16};
  color: ${({ theme: { neutral } }) => neutral.text.strong};
`;

const MilestoneDueDate = styled.div`
  display: flex;
  align-items: center;
  gap: 8px;
  font: ${({ theme: { font } }) => font.availableMD12};
  color: ${({ theme: { neutral } }) => neutral.text.weak};
`;
