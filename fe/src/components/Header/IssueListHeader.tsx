import styled from "styled-components";
import { FilterBar } from "components/FilterBar/FilterBar";
import { TabButton, Tab } from "components/Button/TabButton";
import { Button } from "components/Button/Button";
import { Icon } from "components/Icon/Icon";

export const IssueListHeader = () => {
  const handleIndecatorClick = () => {};
  return (
    <HeaderLayout>
      <FilterBar onIndicatorClick={handleIndecatorClick}></FilterBar>
      <RightSideActions>
        <Tab>
          <TabButton icon="Label" text="레이블" count={3} />
          <TabButton icon="Milestone" text="마일스톤" count={2} />
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
