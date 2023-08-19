import { css, useTheme } from '@emotion/react';
import { ReactComponent as PlusIcon } from '../../../assets/icon/plus.svg';
import { useContext } from 'react';
import { IssueContext } from '../../Context/IssueContext';
import FilterBar from '../../FilterBar';
import Taps from '../../common/Taps';
import Button from '../../common/Button';

type Props = {
  buttonValue: string;
  onClick: () => void;
};

export default function IssueNavBar({ buttonValue, onClick }: Props) {
  const theme = useTheme();
  const { ...context } = useContext(IssueContext);

  return (
    <div css={navBar}>
      <FilterBar />
      <div className="nav-container">
        <Taps
          labelCount={context.issueList.labelCount}
          milestoneCount={context.issueList.milestoneCount}
        />
        <Button
          icon={<PlusIcon />}
          value={buttonValue}
          onClick={onClick}
          color={theme.brand.textDefault}
          backgroundColor={theme.brand.surfaceDefault}
        />
      </div>
    </div>
  );
}

const navBar = () => css`
  display: flex;
  justify-content: space-between;
  width: 1280px;
  margin: 0 auto 24px;
  padding: 0 40px;

  .nav-container {
    width: auto;
    display: flex;
    justify-content: space-between;
    gap: 15px;
  }
`;
