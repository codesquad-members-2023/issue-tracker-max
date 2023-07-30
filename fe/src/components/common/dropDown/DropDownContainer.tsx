import { css, useTheme } from '@emotion/react';
import { useState } from 'react';
import { DropDownPanel } from './DropDownPanel';
import { DropDownIndicator } from './DropDownIndicator';

type AssigneeOption = {
  id: string;
  image: string;
};

type LabelOption = {
  labelId: number;
  name: string;
  textColor: 'light' | 'dark';
  backgroundColor: string;
};

type MilestoneOption = {
  milestoneId: number;
  name: string;
  progress: number;
};
type filterType = string[];

type Props = {
  name: string;
  options: any;
  alignment: 'left' | 'right';
};

export const DropDownContainer = ({ name, options, alignment }: Props) => {
  const [isPanelOpen, setIsPanelOpen] = useState(false);

  const theme: any = useTheme();

  const onPanelOpen = () => {
    setIsPanelOpen(true);
  };

  const onPanelClose = () => {
    setIsPanelOpen(false);
  };

  return (
    <div css={container}>
      <DropDownIndicator onPanelOpen={onPanelOpen} name={name} />
      {isPanelOpen && (
        <>
          <div css={dim} onClick={onPanelClose}></div>
          <DropDownPanel name={name} options={options} alignment={alignment} />
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
