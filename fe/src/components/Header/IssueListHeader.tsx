import styled from "styled-components";
import { FilterBar } from "components/Common/FilterBar/FilterBar";
import { TabButton, Tab } from "components/Common/Button/TabButton";
import { Button } from "components/Common/Button/Button";
import { Icon } from "components/Common/Icon/Icon";

interface IssueListHeaderProps {
  labelCount: number;
  milestoneCount: number;
}

export const IssueListHeader: React.FC<IssueListHeaderProps> = ({
  labelCount,
  milestoneCount,
}) => {
  const handleIndecatorClick = () => {};
  return (
    <HeaderLayout>
      <FilterBar onIndicatorClick={handleIndecatorClick} />
      <RightSideActions>
        <Tab>
          <TabButton icon="Label" text="레이블" count={labelCount} />
          <TabButton icon="Milestone" text="마일스톤" count={milestoneCount} />
        </Tab>
        <Button size="S" variant="contained">
          <Icon icon="Plus" size="M" stroke="brandTextDefault" />
          이슈 작성
        </Button>
      </RightSideActions>
    </HeaderLayout>
  );
};

const HeaderLayout = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 24px;
`;

const RightSideActions = styled.div`
  display: flex;
  gap: 16px;
`;
