import { useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import { FilterBar } from "components/Common/FilterBar/FilterBar";
import { TabButton, Tab } from "components/Common/Button/TabButton";
import { Button } from "components/Common/Button/Button";
import { Icon } from "components/Common/Icon/Icon";
import { DropdownPanel } from "components/Dropdown/DropdownPanel";

const FILTERBAR_DATA = [
  { id: 1, name: "열린 이슈", filter: "is:open" },
  { id: 2, name: "내가 작성한 이슈", filter: "author:@me" },
  { id: 3, name: "나에게 할당된 이슈", filter: "assignee:@me" },
  { id: 4, name: "내가 댓글을 남긴 이슈", filter: "mentions:@me " },
  { id: 5, name: "닫힌 이슈", filter: "is:closed" },
];

interface IssueListHeaderProps {
  labelCount: number;
  milestoneCount: number;
}

export const IssueHeader: React.FC<IssueListHeaderProps> = ({
  labelCount,
  milestoneCount,
}) => {
  const navigate = useNavigate();
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);

  const handleFilterDropdownClick = () => {
    setIsDropdownOpen((prev) => !prev);
  };

  return (
    <HeaderLayout>
      <FilterBar filterTitle="필터" onFilterClick={handleFilterDropdownClick} />
      {isDropdownOpen && (
        <DropdownPanel
          title="필터"
          $position="left"
          onClose={() => setIsDropdownOpen(false)}
          items={FILTERBAR_DATA}
        />
      )}

      <RightSideActions>
        <Tab>
          <TabButton
            icon="Label"
            text="레이블"
            count={labelCount}
            onClick={() => navigate("/labels")}
          />
          <TabButton
            icon="Milestone"
            text="마일스톤"
            count={milestoneCount}
            onClick={() => navigate("/milestones/open")}
          />
        </Tab>
        <Button size="S" variant="contained" onClick={() => navigate("/new")}>
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
  position: relative;
`;

const RightSideActions = styled.div`
  display: flex;
  gap: 16px;
`;
