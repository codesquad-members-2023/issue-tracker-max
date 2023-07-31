import { css, useTheme } from '@emotion/react';
import React, { useState } from 'react';
import { DropDownList } from './DropDownList';
import {
  ContributorType,
  DropDownIndicatorName,
  DropDownIndicatorNameType,
  DropDownOptionsType,
  IssueStateType,
  LabelType,
  MilestoneType,
  TextColorType,
} from './types';

type Props = {
  name: DropDownIndicatorNameType;
  options:
    | string[]
    | ContributorType[]
    | LabelType[]
    | MilestoneType[]
    | IssueStateType[]
    | TextColorType[];
  alignment: 'left' | 'right';
};

type SelectedItems = {
  [key: number]: boolean;
};

export const DropDownPanel: React.FC<Props> = ({
  name,
  options,
  alignment,
}) => {
  const theme = useTheme() as any;
  const [selectedItems, setSelectedItems] = useState<SelectedItems>({}); //페이지로

  console.log(selectedItems);

  const newOptions = ['assignee', 'label', 'milestone'].includes(name)
    ? [{ id: `${name}이 없는 이슈`, name: `${name} 없는 이슈` }, ...options]
    : options;

  const onSelected = (index: number) => {
    if (name === 'milestone' || name === 'issueState' || name === 'textColor') {
      setSelectedItems({ [index]: true });
    } else {
      setSelectedItems((prev) => ({ ...prev, [index]: !prev[index] }));
    }
  };

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
        {DropDownIndicatorName[name]}
      </div>
      <ul>
        {['assignee', 'label', 'milestone'].includes(name) && (
          <DropDownList
            name={name}
            option={`${name}이 없는 이슈`}
            // onSelected={() => onSelected(index)}
            // isSelected={selectedItems[index]}
          />
        )}

        {options.map((item, index: number) => (
          <DropDownList 
            key={index}
            name={name}
            option={item}
            // onSelected={() => onSelected(index)}
            // isSelected={selectedItems[index]}
          />
        ))}
      </ul>
    </div>
  );
};
