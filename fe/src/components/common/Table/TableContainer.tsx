import { useTheme } from '@emotion/react';

import { Button } from '../Button';
import { ReactComponent as XSquare } from '@assets/icons/xSquare.svg';
import { ReactComponent as Plus } from '@assets/icons/plus.svg';
import { ReactComponent as Edit } from '@assets/icons/edit.svg';

import { TableBody } from './TableBody';
type Props = {
  typeVariant: 'add' | 'edit';
  tableVariant: 'label' | 'milestone';
  header: React.ReactNode;
  onAddTableClose?: () => void;
};

export const TableContainer: React.FC<Props> = ({
  typeVariant,
  tableVariant,
  header,
  onAddTableClose,
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
        height: 'fit-content',
        display: 'flex',
        backgroundColor: theme.neutral.surface.strong,
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
          // onClick={}
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
