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
  options: DropDownItem[];
  alignment: string;
  panelHeader: string;
  selectedItems: { [key: number]: boolean };
  onSelected: (index: number) => void;
};

export const DropDownPanel: React.FC<Props> = ({
  options,
  alignment,
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
      <ul
        css={{
          boxSizing: 'border-box',
          maxHeight: '288px',
          overflowY: 'auto',
        }}
      >
        {options.length > 0 ? (
          options.map((item) => (
            <DropDownList
              key={item.id}
              item={item}
              onSelected={() => onSelected(item.id)}
              isSelected={selectedItems[item.id]}
              // index={index}
            />
          ))
        ) : (
          <span
            css={{
              marginLeft: '16px',
              font: theme.fonts.displayMedium12,
              color: theme.neutral.text.strong,
            }}
          >
            로딩중
          </span>
        )}
      </ul>
    </div>
  );
};
