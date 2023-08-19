import { css, useTheme } from '@emotion/react';
import Taps from './common/Taps';
import Button from './common/Button';
import { ReactComponent as PlusIcon } from '../assets/icon/plus.svg';

type Props = {
  labelCount: number;
  milestoneCount: number;
  buttonValue: string;
  onClick: () => void;
};

export default function SubNavBar({
  labelCount,
  milestoneCount,
  buttonValue,
  onClick,
}: Props) {
  const theme = useTheme();

  return (
    <div css={navBar}>
      <div className="nav-container">
        <Taps labelCount={labelCount} milestoneCount={milestoneCount} />
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

const navBar = css`
  display: flex;
  justify-content: space-between;
  width: 1280px;
  margin: 0 auto 24px;
  padding: 0 40px;

  .nav-container {
    width: 1280px;
    display: flex;
    justify-content: space-between;
    gap: 15px;
  }
`;
