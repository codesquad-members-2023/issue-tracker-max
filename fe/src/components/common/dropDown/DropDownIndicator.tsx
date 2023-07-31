import { css, useTheme } from '@emotion/react';
import { ReactComponent as ChevronDown } from '@assets/icons/chevronDown.svg';
import { DropDownIndicatorName } from './types';

type Props = {
  onPanelOpen: () => void;
  name: DropDownIndicatorName;
};

export const DropDownIndicator: React.FC<Props> = ({ onPanelOpen, name }) => {
  return (
    <div css={container} onClick={onPanelOpen}>
      <button>{DropDownIndicatorName[name]}</button>
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
