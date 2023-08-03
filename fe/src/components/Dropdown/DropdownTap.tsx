import { useState } from "react";
import styled from "styled-components";
import { DropdownIndicator } from "./DropdownIndicator";
import { Dropdown } from "./Dropdown";

type PositionType = "center" | "left" | "right";

type SubFilterItem = {
  id: number;
  name: string;
  filter: string;
  profile?: string;
  backgroundColor?: string;
};

interface IndicatorItem {
  id: number;
  title: string;
  filter: string;
  items?: SubFilterItem[];
}

interface IndicatorProps {
  indicatorlList: IndicatorItem[];
  dropdownPosition: PositionType;
  dropdownTitle?: (title: string) => string;
  onTabClick?: (filter: string) => void;
}

export const DropdownTap: React.FC<IndicatorProps> = ({
  indicatorlList,
  dropdownPosition,
  dropdownTitle,
  onTabClick,
}) => {
  const [activeTab, setActiveTab] = useState<string | null>(null);

  const handleLabelClick = (filter: string) => {
    setActiveTab(filter);
    onTabClick && onTabClick(filter);
  };

  const handleDropdownClose = () => {
    setActiveTab(null);
  };

  return (
    <TabLayout>
      {indicatorlList.map((item) => (
        <TabItem key={item.id}>
          <DropdownIndicator
            id={item.filter}
            title={item.title}
            onLabelClick={handleLabelClick}
          />
          {activeTab === item.filter && (
            <Dropdown
              items={item.items}
              title={dropdownTitle ? dropdownTitle(item.title) : item.title}
              position={dropdownPosition}
              filter={item.filter}
              onClose={handleDropdownClose}
            />
          )}
        </TabItem>
      ))}
    </TabLayout>
  );
};

const TabItem = styled.li`
  position: relative;
`;

const TabLayout = styled.ul`
  display: flex;
  list-style: none;
  gap: 32px;
  padding: 0;
`;
