import styled from "styled-components";
import { DropdownIndicator } from "components/Dropdown/DropdownIndicator";
import { Input } from "components/Input/Input";

/* Indicator 수정해야함~~ */
interface FilterBarProps {
  onIndicatorClick: (id: string) => void;
}

export const FilterBar: React.FC<FilterBarProps> = ({ onIndicatorClick }) => {
  return (
    <FilterBarLayout style={{ margin: "20px" }}>
      <IndicatorLayout>
        <DropdownIndicator
          title="필터"
          onLabelClick={onIndicatorClick}
          id="1"
        />
      </IndicatorLayout>
      <Input type="S" icon="Search" $radiusType="RoundRight" />
    </FilterBarLayout>
  );
};

const IndicatorLayout = styled.div`
  padding: 0px 24px;
  cursor: pointer;
  height: 42px;
  border: ${({ theme: { border } }) => border.default};
  border-color: ${({ theme: { color } }) => color.nuetralBorderDefault};
  border-top-left-radius: ${({ theme: { radius } }) => radius.medium};
  border-bottom-left-radius: ${({ theme: { radius } }) => radius.medium};
  border-right: none;
  display: flex;
  align-items: center;
`;

const FilterBarLayout = styled.div`
  width: 560px;
  display: flex;
  align-items: center;
  height: 40px;
`;
