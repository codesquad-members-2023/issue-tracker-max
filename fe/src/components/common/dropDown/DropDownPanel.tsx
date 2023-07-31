import { useTheme } from '@emotion/react';
import React, { useState } from 'react';
import { DropDownList } from './DropDownList';

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
  panelHeader: string;
  onSelected: (index: number) => void;
  selectedItems: { [key: number]: boolean };
};

export const DropDownPanel: React.FC<Props> = ({
  options,
  alignment,
  panelHeader,
  onSelected,
  selectedItems,
}) => {
  const theme = useTheme() as any;

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
            index={index}
          />
        ))}
      </ul>
    </div>
  );
};
