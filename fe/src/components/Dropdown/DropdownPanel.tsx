import { useState, useEffect, useRef } from "react";
import styled from "styled-components";
import { useFilter } from "contexts/FilterProvider";
import { useOutsideClick } from "../../hook/useOutsideClick";
type PositionType = "center" | "left" | "right";
import { Icon } from "components/Common/Icon/Icon";
import { ProfileImg } from "components/Common/Profile/Profile";
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
      // console.log(state.dropdownFilter[filter]?.includes(item.name));
      return state.dropdownFilter[filter]?.includes(item.name);
    } else if (type === "filterBar") {
      return state.filterBar.includes(item.filter);
    }
    return false;
  };

  const handleOptionClick = (item: SubFilterItem) => {
    setSelectedItem(item);
    console.log(selectedItem);
  };

  useOutsideClick(dropdownRef, onClose);

  return (
    <Layout {...{ $position }} ref={dropdownRef}>
      <PanelTitle>{title}</PanelTitle>

      <OptionLists>
        {items?.map((item) => (
          <OptionItem
            key={item.id}
            onClick={() => handleOptionClick(item)}
            $isSelected={isItemSelected(item)}
          >
            <div>
              {item.profile && <ProfileImg size={20} $url={item.profile} />}
              {item.backgroundColor && (
                <Circle style={{ backgroundColor: item.backgroundColor }} />
              )}
              <p>{item.name}</p>
            </div>

            <Icon
              icon={isItemSelected(item) ? "CheckOnCircle" : "CheckOffCircle"}
            />
          </OptionItem>
        ))}
      </OptionLists>
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

const OptionLists = styled.ul`
  padding: 0;
  width: 100%;
  display: flex;
  flex-direction: column;
  background-color: ${({ theme: { color } }) => color.nuetralSurfaceStrong};
`;

const OptionItem = styled.li<{ $isSelected: boolean }>`
  display: flex;
  align-items: center;
  justify-content: space-between;
  list-style: none;
  padding: 8px 16px;
  border-top: ${({ theme: { border } }) => border.default};
  border-color: ${({ theme: { color } }) => color.nuetralBorderDefault};
  transition: all 0.3s;
  color: ${({ theme: { color } }) => color.nuetralTextDefault};
  font: ${({ theme: { font }, $isSelected }) =>
    $isSelected ? font.selectedB16 : font.availableM16};
  color: ${({ theme: { color }, $isSelected }) =>
    $isSelected ? color.nuetralTextStrong : color.nuetralTextDefault};

  > div {
    display: flex;
    gap: 8px;
  }

  &:hover {
    background-color: ${({ theme: { color } }) => color.nuetralSurfaceBold};
  }
`;

const Circle = styled.div`
  width: 20px;
  height: 20px;
  border-radius: 50%;
  background-size: cover;
  background-position: center;
`;
