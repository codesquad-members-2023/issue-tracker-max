import { Theme, css, useTheme } from '@emotion/react';
import { ReactComponent as ChevronDownIcon } from '/src/assets/icon/chevronDown.svg';
import { font } from '../../../styles/styles';

type Props = {
  filterText: string;
};

export default function DropdownIndicator({ filterText }: Props) {
  const theme = useTheme();

  return (
    <button css={button(theme)}>
      {filterText}
      <ChevronDownIcon />
    </button>
  );
}

const button = (theme: Theme) => css`
  background-color: inherit;
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 80px;
  height: 32px;
  font: ${font.availableMedium16};
  color: ${theme.neutral.textDefault};
`;
