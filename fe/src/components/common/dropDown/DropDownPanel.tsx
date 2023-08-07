import { useTheme } from '@emotion/react';
import { DropDownList } from './DropDownList';
import { DropDownHeader } from './DropDownHeader';

type DropDownItem = {
  id: number;
  image?: string;
  name?: string;
  backgroundColor?: string;
  textColor?: string;
  progress?: number;
};

type Props = {
  options?: DropDownItem[];
  position: keyof typeof POSITION;
  panelHeader: string;
  selectedItems: number[];
  onSelected: (index: number) => void;
};

export const DropDownPanel: React.FC<Props> = ({
  options,
  position,
  panelHeader,
  selectedItems,
  onSelected,
}) => {
  const theme = useTheme() as any;

  return (
    <div
      css={{
        position: 'absolute',
        top: '100%',
        zIndex: 50,
        width: '240px',
        borderRadius: theme.radius.l,
        border: `${theme.border.default} ${theme.neutral.border.default}`,
        background: theme.neutral.surface.default,
        ...POSITION[position],
      }}
    >
      <DropDownHeader panelHeader={panelHeader} />
      <ul
        css={{
          boxSizing: 'border-box',
          maxHeight: '288px',
          overflowY: 'auto',
        }}
      >
        {options &&
          options?.map((item) => (
            <DropDownList
              key={item.id}
              item={item}
              onClick={() => onSelected(item.id)}
              isSelected={selectedItems.includes(item.id)}
            />
          ))}
      </ul>
    </div>
  );
};

const POSITION = {
  left: {
    left: 0,
  },

  right: {
    right: 0,
  },

  center: {
    left: 'calc(-120px + 50%)',
  },
};
