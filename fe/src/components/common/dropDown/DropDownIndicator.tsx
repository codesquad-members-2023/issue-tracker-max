import { css, useTheme } from '@emotion/react';
import { ReactComponent as ChevronDown } from '@assets/icons/chevronDown.svg';

type Props = {
  onPanelOpen: () => void;
  indicator: string;
};

export const DropDownIndicator: React.FC<Props> = ({
  onPanelOpen,
  indicator,
}) => {
  return (
    <div css={container} onClick={onPanelOpen}>
      <button>{indicator}</button>
      <ChevronDown stroke="#4E4B66" />
    </div>
  );
};

const container = css`
  display: flex;
  align-items: center;
  position: relative;
  width: fit-content;
`;
