import { Theme, css, useTheme } from '@emotion/react';
import { ReactComponent as ChevronDownIcon } from '/src/assets/icon/chevronDown.svg';
import { border, font, radius } from '../styles/styles';

export default function FilterBar() {
  const theme = useTheme();

  return (
    <div css={filterBar(theme)}>
      <button className="filter-button">
        필터
        <ChevronDownIcon />
      </button>
      <input
        className="filter-input"
        type="text"
        value="is:issue is:open"
        onChange={() => {}}
      />
    </div>
  );
}

const filterBar = (theme: Theme) => css`
  display: flex;
  align-items: center;
  border-radius: ${radius.medium};
  border: ${border.default} ${theme.neutral.borderDefault};
  border-radius: ${radius.medium};

  .filter-button {
    display: flex;
    align-items: center;
    justify-content: space-between;
    width: 128px;
    height: 40px;
    padding: 0px 24px;
    border-radius: ${radius.medium} 0 0 ${radius.medium};
    background-color: ${theme.neutral.surfaceDefault};
    font: ${font.availableMedium16};
    color: ${theme.neutral.textDefault};
  }

  .filter-input {
    position: relative;
    width: 440px;
    height: 40px;
    padding-left: 40px;
    border: none;
    border-left: ${border.default} ${theme.neutral.borderDefault};
    border-radius: 0 ${radius.medium} ${radius.medium} 0;
    color: ${theme.neutral.textWeak};
    background-color: ${theme.neutral.surfaceBold};
    font: ${font.displayMedium16};
    background-image: url('/src/assets/icon/search.svg');
    background-repeat: no-repeat;
    background-position: 15px center;
  }
`;
