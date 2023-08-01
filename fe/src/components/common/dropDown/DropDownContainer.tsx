import { useTheme } from '@emotion/react';
import { css } from '@emotion/react';
import React, { useState } from 'react';
import { DropDownPanel } from './DropDownPanel';
import { ReactComponent as ChevronDown } from '@assets/icons/chevronDown.svg';
import { ReactComponent as Plus } from '@assets/icons/plus.svg';
import { DropDownIndicator } from './DropDownIndicator';
import { Button } from '../Button';

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
  isPanelOpen?: boolean;
  onPanelClose?: () => void;
  size: 'L' | 'M' | 'defaultSize';
};

export const DropDownContainer: React.FC<Props> = ({
  options,
  alignment,
  panelHeader,
  onSelected,
  selectedItems,
  size,
  indicator,
}) => {
  const theme = useTheme() as any;

  const [isPanelOpen, setIsPanelOpen] = useState(false);
  const onPanelOpen = () => {
    setIsPanelOpen(true);
  };

  const onPanelClose = () => {
    setIsPanelOpen(false);
  };

  return (
    <div css={container}>
      {/* {React.cloneElement(children as React.ReactElement, { onPanelOpen })} */}
      <DropDownIndicator size={size} onPanelOpen={onPanelOpen}>
        <Button
          typeVariant="ghost"
          css={{
            display: 'flex',
            justifyContent: 'space-between',
            font: theme.fonts.availableMedium16,
            width: '100%',
          }}
        >
          {indicator}
          {size === 'L' ? (
            <Plus stroke={theme.neutral.text.default} />
          ) : (
            <ChevronDown stroke={theme.neutral.text.default} />
          )}
        </Button>
      </DropDownIndicator>
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
