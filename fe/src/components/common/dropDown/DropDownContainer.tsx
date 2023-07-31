import { css, useTheme } from '@emotion/react';
import { useState } from 'react';
import { DropDownPanel } from './DropDownPanel';
import { DropDownIndicator } from './DropDownIndicator';
// import {
//   ContributorType,
//   DropDownIndicatorName,
//   DropDownIndicatorNameType,
//   DropDownOptionsType,
//   IssueFilterType,
//   IssueStateType,
//   LabelType,
//   MilestoneType,
//   TextColorType,
// } from './types';

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
  // name: string;
  options: DropDownItem[];
  alignment: string;
  indicator: string;
  panelHeader: string;
};
// type Props =
//   | {
//       name: DropDownIndicatorName.issueFilter;
//       options: string[];
//       alignment: 'left';
//       panelHeader: string;
//       indicator: string;
//     }
//   | {
//       name: DropDownIndicatorName.assignee;
//       options: ContributorType[];
//       alignment: 'right';
//       panelHeader: string;
//       indicator: string;
//     }
//   | {
//       name: DropDownIndicatorName.author;
//       options: ContributorType[];
//       alignment: 'right';
//       panelHeader: string;
//       indicator: string;
//     }
//   | {
//       name: DropDownIndicatorName.label;
//       options: LabelType[];
//       alignment: 'right';
//       panelHeader: string;
//       indicator: string;
//     }
//   | {
//       name: DropDownIndicatorName.milestone;
//       options: MilestoneType[];
//       alignment: 'right';
//       panelHeader: string;
//       indicator: string;
//     }
//   | {
//       name: DropDownIndicatorName.issueState;
//       options: IssueStateType[];
//       alignment: 'right';
//       panelHeader: string;
//       indicator: string;
//     }
//   | {
//       name: DropDownIndicatorName.textColor;
//       options: TextColorType[];
//       alignment: 'left';
//       panelHeader: string;
//       indicator: string;
//     };

export const DropDownContainer: React.FC<Props> = ({
  // name,
  options,
  alignment,
  indicator,
  panelHeader,
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
            // name={name}
            panelHeader={panelHeader}
            alignment={alignment}
            options={options}
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
