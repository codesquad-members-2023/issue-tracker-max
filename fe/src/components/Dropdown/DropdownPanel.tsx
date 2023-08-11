import { useState, useEffect, useRef } from "react";
import styled from "styled-components";
import { DropdownOptions } from "./DropdownOptions";
import { useFilter } from "contexts/FilterProvider";

type PositionType = "center" | "left" | "right";

interface SubFilterItem {
  id: number;
  name: string;
  filter: string;
  profile?: string;
  backgroundColor?: string;
}

interface DropdownProps {
  items?: SubFilterItem[];
  title: string;
  filter?: string;
  type: string;
  $position: PositionType;
  onClose: () => void;
}

/* useOutsideClick 분리하기 */
function useOutsideClick(
  ref: React.MutableRefObject<HTMLElement | null>,
  callback: () => void,
) {
  const handleClick = (e: MouseEvent) => {
    if (ref.current && !ref.current.contains(e.target as Node)) {
      callback();
    }
  };

  useEffect(() => {
    document.addEventListener("mousedown", handleClick);

    return () => {
      document.removeEventListener("mousedown", handleClick);
    };
  });
}

export const DropdownPanel: React.FC<DropdownProps> = ({
  items = [],
  title,
  $position,
  filter,
  onClose,
  type,
}) => {
  const [selectedItem, setSelectedItem] = useState<SubFilterItem | null>(null);
  const dropdownRef = useRef(null);
  const { state, setDropdownFilter, setFilterBar } = useFilter();

  useEffect(() => {
    if (selectedItem !== null) {
      if (type === "dropdownFilter" && filter) {
        setDropdownFilter(filter, [selectedItem.name]);
      }
      if (type === "filterBar") {
        setFilterBar([selectedItem.filter]);
      }
      onClose();
    }
  }, [selectedItem, onClose, filter, items]);

  const isItemSelected = (item: SubFilterItem) => {
    if (type === "dropdownFilter" && filter) {
      return state.dropdownFilter[filter]?.includes(item.name);
    } else if (type === "filterBar") {
      return state.filterBar.includes(item.filter);
    }
    return false;
  };

  const handleOptionClick = (item: SubFilterItem) => {
    setSelectedItem(item);
  };

  useOutsideClick(dropdownRef, onClose);

  return (
    <Layout {...{ $position }} ref={dropdownRef}>
      <PanelTitle>{title}</PanelTitle>
      <DropdownOptions
        items={items}
        onOptionClick={handleOptionClick}
        isSelectedFunc={isItemSelected}
      />
    </Layout>
  );
};

const Layout = styled.div<Pick<DropdownProps, "$position">>`
  position: absolute;
  z-index: 10;
  display: flex;
  justify-content: center;
  flex-direction: column;
  ${(props) => DropdownPosition[props.$position]}
  width: 240px;
  border: ${({ theme: { border } }) => border.default};
  border-color: ${({ theme: { color } }) => color.nuetralBorderDefault};
  overflow: hidden;
  border-radius: ${({ theme: { radius } }) => radius.large};
`;

const DropdownPosition: Record<PositionType, string> = {
  left: `left: 0; top: 90%;`,
  right: `right: 0; top: 90%;`,
  center: `left: 50%; transform: translateX(-50%);`,
};

const PanelTitle = styled.p`
  width: 100%;
  padding: 8px 16px;
  background-color: ${({ theme: { color } }) => color.nuetralSurfaceDefault};
  color: ${({ theme: { color } }) => color.nuetralTextWeak};
  font: ${({ theme: { font } }) => font.displayM12};
`;
