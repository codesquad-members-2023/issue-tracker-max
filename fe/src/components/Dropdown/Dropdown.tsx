import { useState, useEffect, useRef } from "react";
import styled from "styled-components";
import { DropdownTitle } from "./DropdownTitle";
import { DropdownOptions } from "./DropdownOptions";

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
  filter: string;
  position: PositionType;
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

export const Dropdown: React.FC<DropdownProps> = ({
  items = [],
  title,
  position,
  filter,
  onClose,
}) => {
  const [selectedItem, setSelectedItem] = useState<SubFilterItem | null>(null);
  const dropdownRef = useRef(null);

  useEffect(() => {
    if (selectedItem !== null) {
      console.log(filter);
      console.log(items);
      console.log(selectedItem);
      onClose();
    }
  }, [selectedItem, onClose, filter, items]);

  const handleOptionClick = (item: SubFilterItem) => {
    setSelectedItem(item);
  };

  useOutsideClick(dropdownRef, onClose);

  return (
    <DropdownPanel {...{ position }} ref={dropdownRef}>
      <DropdownTitle title={title} />
      <DropdownOptions items={items} onOptionClick={handleOptionClick} />
    </DropdownPanel>
  );
};

const DropdownPanel = styled.div<Pick<DropdownProps, "position">>`
  position: absolute;
  z-index: 10;
  display: flex;
  justify-content: center;
  flex-direction: column;
  ${(props) => DropdownPosition[props.position]}
  width: 240px;
  border: ${({ theme: { border } }) => border.default};
  border-color: ${({ theme: { color } }) => color.nuetralBorderDefault};
  border-radius: ${({ theme: { radius } }) => radius.large};
`;

const DropdownPosition: Record<PositionType, string> = {
  left: `left: 0;`,
  right: `right: 0;`,
  center: `left: 50%; transform: translateX(-50%);`,
};
