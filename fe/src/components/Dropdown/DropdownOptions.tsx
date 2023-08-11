import styled from "styled-components";
import { useState } from "react";
import { Icon } from "components/Common/Icon/Icon";
import { ProfileImg } from "components/Common/Profile/Profile";
interface SubFilterItem {
  id: number;
  name: string;
  filter: string;
  profile?: string;
  backgroundColor?: string;
}

interface DropdownOptionsProps {
  items: SubFilterItem[] | null;
  onOptionClick?: (item: SubFilterItem) => void;
}

export const DropdownOptions: React.FC<DropdownOptionsProps> = ({
  items,
  onOptionClick,
}) => {
  const [selectedId, setSelectedId] = useState<number | null>(null);

  const handleOptionClick = (item: SubFilterItem) => {
    setSelectedId(item.id);
    onOptionClick && onOptionClick(item);
  };

  return (
    <OptionLists>
      {items?.map((item) => (
        <OptionItem
          key={item.id}
          onClick={() => handleOptionClick(item)}
          $isSelected={selectedId === item.id}
        >
          <div>
            {item.profile && <ProfileImg size={20} $url={item.profile} />}
            {item.backgroundColor && (
              <Circle style={{ backgroundColor: item.backgroundColor }} />
            )}
            <p>{item.name}</p>
          </div>

          <Icon
            icon={selectedId === item.id ? "CheckOnCircle" : "CheckOffCircle"}
          />
        </OptionItem>
      ))}
    </OptionLists>
  );
};

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
