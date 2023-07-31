import { css, useTheme } from '@emotion/react';
import { useState } from 'react';
import { DropDownPanel } from './DropDownPanel';
import { DropDownIndicator } from './DropDownIndicator';
import {
  ContributorType,
  DropDownIndicatorName,
  DropDownIndicatorNameType,
  DropDownOptionsType,
  IssueFilterType,
  IssueStateType,
  LabelType,
  MilestoneType,
  TextColorType,
} from './types';

type Props =
  // | {
  // name: DropDownIndicatorNameType;
  //     options: DropDownOptionsType;
  //     alignment: 'left' | 'right';
  //   }
  | {
      name: DropDownIndicatorName.issueFilter;
      options: string[];
      alignment: 'left';
    }
  | {
      name: DropDownIndicatorName.assignee;
      options: ContributorType[];
      alignment: 'right';
    }
  | {
      name: DropDownIndicatorName.author;
      options: ContributorType[];
      alignment: 'right';
    }
  | {
      name: DropDownIndicatorName.label;
      options: LabelType[];
      alignment: 'right';
    }
  | {
      name: DropDownIndicatorName.milestone;
      options: MilestoneType[];
      alignment: 'right';
    }
  | {
      name: DropDownIndicatorName.issueState;
      options: IssueStateType[];
      alignment: 'right';
    }
  | {
      name: DropDownIndicatorName.textColor;
      options: TextColorType[];
      alignment: 'left';
    };

export const DropDownContainer: React.FC<Props> = ({
  name,
  options,
  alignment,
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
