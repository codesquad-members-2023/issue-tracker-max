import { Theme, css, useTheme } from '@emotion/react';
import { border, font, radius } from '../../styles/styles';
import { NavLink } from 'react-router-dom';
import { ReactComponent as LabelIcon } from '../../assets/icon/label.svg';
import { ReactComponent as MilestoneIcon } from '../../assets/icon/milestone.svg';

type Props = {
  labelCount: number;
  milestoneCount: number;
};

export default function Taps({ labelCount, milestoneCount }: Props) {
  const theme = useTheme();

  return (
    <div css={taps(theme)}>
      <NavLink to={'/label'}>
        <button className="label-button">
          <LabelIcon />
          레이블({labelCount})
        </button>
      </NavLink>
      <NavLink to={'/milestone'}>
        <button className="milestone-button">
          <MilestoneIcon className="milestone-icon" />
          마일스톤({milestoneCount})
        </button>
      </NavLink>
    </div>
  );
}

const taps = (theme: Theme) => css`
  display: flex;
  border-radius: ${radius.medium};
  border: ${border.default} ${theme.neutral.borderDefault};
  background-color: ${theme.neutral.surfaceDefault};

  a {
    text-decoration: none;
  }

  button {
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 4px;
    width: 160px;
    height: 40px;
    font: ${font.availableMedium16};
    color: ${theme.neutral.textDefault};
    background-color: ${theme.neutral.surfaceDefault};
  }

  & .active {
    button {
      font: ${font.selectedBold16};
      color: ${theme.neutral.textStrong};
      background-color: ${theme.neutral.surfaceBold};
    }
  }

  .label-button {
    border-radius: ${radius.medium} 0 0 ${radius.medium};
  }

  .milestone-button {
    border-radius: 0 ${radius.medium} ${radius.medium} 0;
    border-left: ${border.default} ${theme.neutral.borderDefault};

    .milestone-icon path {
      fill: ${theme.neutral.textDefault};
      stroke: none;
    }
  }
`;
