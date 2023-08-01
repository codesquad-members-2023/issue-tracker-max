import { css } from '@emotion/react';
import { useState } from 'react';
import { DropDownPanel } from './DropDownPanel';
import { DropDownIndicator } from './DropDownIndicator';

type DropDownItem = {
  id?: string;
  image?: string;
  name?: string;
  backgroundColor?: string;
  filterId?: number;
  textColor?: string;
  progress?: number;
  milestoneId?: number;
};

type Props = {
  options: DropDownItem[];
  alignment: string;
  indicator: string;
  panelHeader: string;
  onSelected: (index: number) => void;
  selectedItems: { [key: number]: boolean };
};

export const DropDownContainer: React.FC<Props> = ({
  options,
  alignment,
  indicator,
  panelHeader,
  onSelected,
  selectedItems,
}) => {
  const [isPanelOpen, setIsPanelOpen] = useState(false);

  const onPanelOpen = () => {
    setIsPanelOpen(true);
  };

  const onPanelClose = () => {
    setIsPanelOpen(false);
  };

  return (
    <div css={container}>
      <DropDownIndicator onPanelOpen={onPanelOpen} indicator={indicator} />
      {isPanelOpen && (
        <>
          <div css={dim} onClick={onPanelClose}></div>
          <DropDownPanel
            panelHeader={panelHeader}
            alignment={alignment}
            options={options}
            onSelected={onSelected}
            selectedItems={selectedItems}
          />
        </>
      )}
    </div>
  );
};

const container = css`
  position: relative;
`;

const dim = css`
  position: fixed;
  top: 0;
  left: 0;
  z-index: 10;
  width: 100vw;
  height: 100vh;
`;
