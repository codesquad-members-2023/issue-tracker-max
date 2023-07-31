import { css, useTheme } from '@emotion/react';
import React, { useState } from 'react';
import { DropDownList } from './DropDownList';
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
  panelHeader: string;
};

// type Props = {
//   name: DropDownIndicatorNameType;
//   options: DropDownItem;
//   alignment: 'left' | 'right';
// };

// type DropDownItem =
//   | {
//       name: 'issueFilter';
//       value: IssueFilterType[];
//     }
//   | {
//       name: 'assignee';
//       value: ContributorType[];
//     }
//   | {
//       name: 'author';
//       value: ContributorType[];
//     }
//   | {
//       name: 'label';
//       value: LabelType[];
//     }
//   | {
//       name: 'milestone';
//       value: MilestoneType[];
//     }
//   | {
//       name: 'issueState';
//       value: IssueStateType[];
//     }
//   | {
//       name: 'textColor';
//       value: TextColorType[];
//     };

type SelectedItems = {
  [key: number]: boolean;
};

export const DropDownPanel: React.FC<Props> = ({
  // name,
  options,
  alignment,
  panelHeader,
}) => {
  const theme = useTheme() as any;
  const [selectedItems, setSelectedItems] = useState<SelectedItems>({}); //페이지로

  console.log(selectedItems);

  // const newOptions = ['assignee', 'label', 'milestone'].includes(name)
  //   ? [{ id: `${name}이 없는 이슈`, name: `${name} 없는 이슈` }, ...options]
  //   : options;

  // const onSelected = (index: number) => {
  //   if (name === 'milestone' || name === 'issueState' || name === 'textColor') {
  //     setSelectedItems({ [index]: true });
  //   } else {
  //     setSelectedItems((prev) => ({ ...prev, [index]: !prev[index] }));
  //   }
  // };

  return (
    <div
      css={{
        position: 'absolute',
        top: '100%',
        [alignment]: '0',
        zIndex: 50,
        width: '240px',
        borderRadius: theme.radius.l,
        border: `${theme.border.default} ${theme.neutral.border.default}`,
        background: theme.neutral.surface.default,
      }}
    >
      <div
        css={{
          borderBottom: `${theme.border.default} ${theme.neutral.border.default}`,
          padding: '8px 16px',
          font: theme.fonts.displayMedium12,
          color: theme.neutral.text.weak,
        }}
      >
        {panelHeader}
      </div>
      <ul>
        {options.map((item, index: number) => (
          <DropDownList
            key={index}
            item={item}
            onSelected={() => onSelected(index)}
            isSelected={selectedItems[index]}
          />
        ))}
      </ul>
    </div>
  );
};
