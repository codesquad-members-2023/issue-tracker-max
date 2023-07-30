import { css, useTheme } from '@emotion/react';
import { useState } from 'react';
import { DropDownList } from './DropDownList';
import { DropDownIndicatorName, DropDownIndicatorNameType } from './types';

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

type Props = {
  name: DropDownIndicatorNameType;
  alignment: 'left' | 'right';
  options: any;
};

export const DropDownPanel = ({ name, options, alignment }: Props) => {
  const theme = useTheme() as any;
  const [selectedItems, setSelectedItems] = useState({});

  console.log(selectedItems);

  const canSelectUnassignedIssues = name !== 'author' && name !== 'issueFilter';

  const onSelected = (index) => {
    if (name === 'milestone' || name === 'issueState' || name === 'textColor') {
      setSelectedItems({ [index]: true });
    } else {
      setSelectedItems((prev) => ({ ...prev, [index]: !prev[index] }));
    }
  };

  return (
    <div
      css={css`
        position: absolute;
        top: 100%;
        ${alignment}: 0;
        z-index: 50;
        width: 240px;
        border-radius: ${theme.radius.l};
        border: ${theme.border.default} ${theme.neutral.border.default};
        background: ${theme.neutral.surface.default};
      `}
    >
      <div
        css={css`
          border-bottom: ${theme.border.default} ${theme.neutral.border.default};
          padding: 8px 16px;
          font: ${theme.fonts.displayMedium12};
          color: ${theme.neutral.text.weak};
        `}
      >
        {DropDownIndicatorName[name]}
      </div>
      <ul>
        {canSelectUnassignedIssues && (
          <DropDownList
            key={-1}
            name={name}
            options={'없는 이슈'}
            onSelected={() => onSelected(-1)}
            isSelected={selectedItems[-1]}
          />
        )}
        {options.map((item, index: number) => (
          <DropDownList
            key={index}
            name={name}
            options={item}
            onSelected={() => onSelected(index)}
            isSelected={selectedItems[index]}
          />
        ))}
      </ul>
    </div>
  );
};
