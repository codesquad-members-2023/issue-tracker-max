import { useTheme } from '@emotion/react';
import React, { useState } from 'react';
import { DropDownList } from './DropDownList';
import { DropDownHeader } from './DropDownHeader';

type DropDownItem = {
  id?: number;
  image?: string;
  name?: string;
  backgroundColor?: string;
  textColor?: string;
  progress?: number;
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
        marginLeft: alignment === 'center' ? '-8px' : '0',
        zIndex: 50,
        width: '240px',
        borderRadius: theme.radius.l,
        border: `${theme.border.default} ${theme.neutral.border.default}`,
        background: theme.neutral.surface.default,
      }}
    >
      <DropDownHeader panelHeader={panelHeader} />
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
