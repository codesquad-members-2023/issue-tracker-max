import { Theme, css, useTheme } from '@emotion/react';
import { ReactComponent as PlusIcon } from '../../../assets/icon/plus.svg';
import { border, font, radius } from '../../../styles/styles';

export default function SidebarCreate() {
  const theme = useTheme();

  return (
    <div css={sidebar(theme)}>
      <button className="assignee">
        담당자
        <PlusIcon />
      </button>
      <button className="label">
        레이블
        <PlusIcon />
      </button>
      <button className="milestone">
        마일스톤
        <PlusIcon />
      </button>
    </div>
  );
}

const sidebar = (theme: Theme) => css`
  display: flex;
  flex-direction: column;
  width: 288px;
  height: 282px;
  border: ${border.default} ${theme.neutral.borderDefault};
  border-radius: ${radius.large};
  background-color: ${theme.neutral.surfaceStrong};

  button {
    display: flex;
    align-items: center;
    justify-content: space-between;
    flex: 1;
    padding: 0 32px;
    background-color: inherit;
    font: ${font.availableMedium16};
    color: ${theme.neutral.textDefault};
  }

  .assignee {
    border-radius: ${radius.large} ${radius.large} 0 0;
    border-bottom: ${border.default} ${theme.neutral.borderDefault};
  }

  .label {
    border-bottom: ${border.default} ${theme.neutral.borderDefault};
  }

  .milestone {
    border-radius: 0 0 ${radius.large} ${radius.large};
  }
`;
