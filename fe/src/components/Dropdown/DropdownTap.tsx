import React, { useState } from "react";
import styled from "styled-components";
import { DropdownPanel } from "./DropdownPanel";
import { Icon } from "components/Common/Icon/Icon";

type PositionType = "center" | "left" | "right";

type SubFilterItem = {
  id: number;
  name: string;
  filter: string;
  profile?: string;
  backgroundColor?: string;
};

interface DropdownDataType {
  id: number;
  title: string;
  filter: string;
  items?: SubFilterItem[];
}

interface DropdownTapProps {
  dropdownData: DropdownDataType[];
  dropdownPosition: PositionType;
  dropdownTitle?: (title: string) => string;
  onTabClick?: (filter: string) => void;
}

export const DropdownTap: React.FC<DropdownTapProps> = ({
  dropdownData,
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
      {dropdownData.map((item) => (
        <React.Fragment key={item.id}>
          <TabItem>
            <IndicatorBox onClick={() => handleLabelClick(item.filter)}>
              <p>{item.title}</p>
              <Icon icon="ChevronDown" />
            </IndicatorBox>
            {activeTab === item.filter && (
              <DropdownPanel
                items={item.items}
                title={dropdownTitle ? dropdownTitle(item.title) : item.title}
                $position={dropdownPosition}
                filter={item.filter}
                onClose={handleDropdownClose}
              />
            )}
          </TabItem>
        </React.Fragment>
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

const IndicatorBox = styled.button`
  display: flex;
  height: 32px;
  justify-content: space-between;
  align-items: center;
  gap: 4px;
  height: 64px;
  transition: opacity 0.3s ease;
  color: ${({ theme: { color } }) => color.nuetralTextDefault};

  &:hover {
    opacity: ${({ theme: { opacity } }) => opacity.hover};
  }
  &:active {
    opacity: ${({ theme: { opacity } }) => opacity.press};
  }
  &:disabled {
    pointer-events: none;
    opacity: ${({ theme: { opacity } }) => opacity.disabled};
  }
`;
