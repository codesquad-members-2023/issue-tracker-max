import { css } from '@emotion/react';
import { color } from '../../styles/color';
import { font } from '../../styles/font';
import { border, radius } from '../../styles/object';
import LabelIcon from '../../assets/Icons/LabelIcon';
import MilestoneIcon from '../../assets/Icons/MilestoneIcon';
import { NavLink } from 'react-router-dom';

export default function Taps() {
  return (
    <div css={taps}>
      <NavLink to={'/label'}>
        <button
          css={css`
            border-radius: ${radius.medium} 0 0 ${radius.medium};
          `}
        >
          <LabelIcon />
          레이블(0)
        </button>
      </NavLink>
      <NavLink to={'/milestone'}>
        <button
          css={css`
            border-radius: 0 ${radius.medium} ${radius.medium} 0;
            border-left: ${border.default} ${color.neutral.borderDefault};
          `}
        >
          <MilestoneIcon />
          마일스톤(0)
        </button>
      </NavLink>
    </div>
  );
}

const taps = css`
  display: flex;
  border-radius: ${radius.medium};
  border: ${border.default} ${color.neutral.borderDefault};
  background-color: ${color.neutral.surfaceDefault};

  & a {
    text-decoration: none;
  }

  & button {
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 4px;
    width: 160px;
    height: 40px;
    font: ${font.availableMedium16};
    color: ${color.neutral.textDefault};
    background-color: ${color.neutral.surfaceDefault};
  }

  & .active {
    & button {
      font: ${font.selectedBold16};
      color: ${color.neutral.textStrong};
      background-color: ${color.neutral.surfaceBold};
    }
  }
`;
