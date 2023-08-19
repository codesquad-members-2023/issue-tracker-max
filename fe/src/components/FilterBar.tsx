import { Theme, css, useTheme } from '@emotion/react';
import { ReactComponent as ChevronDownIcon } from '../assets/icon/chevronDown.svg';
import { ReactComponent as SearchIcon } from '../assets/icon/search.svg';
import { border, font, radius } from '../styles/styles';
import { useContext } from 'react';
import { IssueContext } from './Context/IssueContext';

export default function FilterBar() {
  const theme = useTheme();
  const { ...context } = useContext(IssueContext);

  return (
    <div css={filterBar(theme)}>
      <button className="filter-button" disabled>
        필터
        <ChevronDownIcon />
      </button>
      <div className="filter-input">
        <SearchIcon />
        {context.filter ? context.filter : 'is:issue is:open'}
      </div>
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
    display: flex;
    align-items: center;
    gap: 8px;
    width: 440px;
    height: 40px;
    padding: 0 24px;
    border: none;
    border-left: ${border.default} ${theme.neutral.borderDefault};
    border-radius: 0 ${radius.medium} ${radius.medium} 0;
    color: ${theme.neutral.textWeak};
    background-color: ${theme.neutral.surfaceBold};
    font: ${font.displayMedium16};
    cursor: not-allowed;
  }
`;
