import { css } from '@emotion/react';
import ChevronDownIcon from '../../../assets/Icons/ChevronDownIcon';

type Props = {
  filterText: string;
};

export default function DropdownIndicator({ filterText }: Props) {
  return (
    <button css={filterButton}>
      {filterText}
      <ChevronDownIcon />
    </button>
  );
}

const filterButton = css`
  background-color: inherit;
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 80px;
  height: 32px;
`;
