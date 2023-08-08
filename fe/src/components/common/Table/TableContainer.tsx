import { useTheme } from '@emotion/react';
import { TableHeader } from './TableHeader';
import { InformationTag } from '../InformationTag';
import { TextInput } from '../textInput/TextInput';
import { ColorCodeInput } from '../ColorCodeInput';
import { DropDownContainer } from '../dropDown/DropDownContainer';
import { DropDownPanel } from '../dropDown/DropDownPanel';
import { DropDownList } from '../dropDown/DropDownList';
import { textColors } from '../dropDown/types';
import { Button } from '../Button';
import { ReactComponent as XSquare } from '@assets/icons/xSquare.svg';
import { ReactComponent as Plus } from '@assets/icons/plus.svg';
import { ReactComponent as Edit } from '@assets/icons/edit.svg';
import { InputContainer } from '../textInput/InputContainer';
import { TableBody } from './TableBody';
type Props = {
  typeVariant: 'add' | 'edit';
  tableVariant: 'label' | 'milestone';
  onAddTableClose?: () => void;
  header: React.ReactNode;
};

export const TableContainer: React.FC<Props> = ({
  typeVariant,
  tableVariant,
  onAddTableClose,
  header,
}) => {
  const theme = useTheme() as any;

  const addTypeStyle = {
    border: `${theme.border.default}  ${theme.neutral.border.default}`,
    borderRadius: theme.radius.l,
    padding: '32px',
  };

  return (
    <div
      css={{
        width: '100%',
        boxSizing: 'border-box',
        height: tableVariant === 'milestone' ? '284px' : '337px', //왜 적용안되는..?
        display: 'flex',

        flexDirection: 'column',
        gap: '24px',
        ...(typeVariant === 'add' ? addTypeStyle : {}),
      }}
    >
      {header}
      <TableBody typeVariant={typeVariant} tableVariant={tableVariant} />

      <div
        css={{
          display: 'flex',
          gap: '16px',
          justifyContent: 'flex-end',
        }}
      >
        <Button
          typeVariant="outline"
          size="S"
          css={{
            color: theme.brand.text.weak,
          }}
          onClick={onAddTableClose}
        >
          <XSquare stroke={theme.brand.text.weak} />
          취소
        </Button>
        <Button
          typeVariant="contained"
          size="S"
          css={{
            color: theme.brand.text.default,
          }}
        >
          {typeVariant === 'edit' ? (
            <Edit stroke={theme.brand.text.default} />
          ) : (
            <Plus stroke={theme.brand.text.default} />
          )}

          {typeVariant === 'edit' ? '편집 완료' : '완료'}
        </Button>
      </div>
    </div>
  );
};
