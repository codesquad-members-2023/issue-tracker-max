import { useState } from 'react';

import { useTheme } from '@emotion/react';
import { InformationTag } from '@components/common/InformationTag';
import { Button } from '@components/common/Button';
import { ReactComponent as Edit } from '@assets/icons/edit.svg';
import { ReactComponent as Trash } from '@assets/icons/trash.svg';
import { TableContainer } from '@components/common/Table/TableContainer';
import { TableHeader } from '@components/common/Table/TableHeader';

type Props = {
  name: string;
  textColor: ThemeType;
  backgroundColor: string;
  description: string;
  // onEditLabelClick?: (id: number) => void;
};

export const LabelItem: React.FC<Props> = ({
  name,
  textColor,
  backgroundColor,
  description,
  // onEditLabelClick,
}) => {
  const theme = useTheme() as any;
  const [isEditing, setIsEditing] = useState(false);

  const onEditLabelOpen = () => {
    setIsEditing(true);
  };

  const onEditLabelClose = () => {
    setIsEditing(false);
  };

  return (
    <li
      css={{
        // height: '96px',
        width: '100%',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'space-between',
        padding: '32px',

        boxSizing: 'border-box',
      }}
    >
      {isEditing ? (
        <TableContainer
          tableVariant="label"
          typeVariant="edit"
          onAddTableClose={onEditLabelClose}
          header={<TableHeader title="레이블 편집" />}
        ></TableContainer>
      ) : (
        <>
          <div
            css={{
              width: '176px',
              displayf: 'flex',
              alignItems: 'center',
            }}
          >
            <InformationTag
              size="S"
              typeVariant="filled"
              fillColor={backgroundColor}
              textColor={textColor}
            >
              {name}
            </InformationTag>
          </div>

          <span
            css={{
              width: '870px',
              color: theme.neutral.text.weak,
              font: theme.fonts.displayMedium16,
            }}
          >
            {description}
          </span>
          <div
            css={{
              display: 'flex',
              alignItems: 'center',
              gap: '24px',
            }}
          >
            <Button
              typeVariant="ghost"
              size="S"
              css={{
                width: 'fit-content',
                padding: '0',
              }}
              onClick={onEditLabelOpen}
            >
              <Edit stroke={theme.neutral.text.default} />
              편집
            </Button>
            <Button
              typeVariant="ghost"
              size="S"
              css={{
                width: 'fit-content',
                padding: '0',
                color: theme.danger.text.default,
              }}
            >
              <Trash stroke={theme.danger.text.default} />
              삭제
            </Button>
          </div>
        </>
      )}
    </li>
  );
};
