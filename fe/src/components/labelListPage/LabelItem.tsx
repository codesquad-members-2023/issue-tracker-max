import { useState } from 'react';

import { useTheme } from '@emotion/react';
import { InformationTag } from '@components/common/InformationTag';
import { Button } from '@components/common/Button';
import { ReactComponent as Edit } from '@assets/icons/edit.svg';
import { ReactComponent as Trash } from '@assets/icons/trash.svg';
import { TableHeader } from '@components/common/Table/TableHeader';
import { LabelEditTable } from './LabelEditTable';

type Props = {
  label: Label;
  fetchLabelList: () => Promise<void>;
};

export const LabelItem: React.FC<Props> = ({ label, fetchLabelList }) => {
  const theme = useTheme() as any;
  const [isEditing, setIsEditing] = useState(false);

  const onEditLabelOpen = () => {
    setIsEditing(true);
  };

  const onEditLabelClose = () => {
    setIsEditing(false);
  };

  const onDeleteLabel = async () => {
    const response = await fetch(
      `${import.meta.env.VITE_APP_BASE_URL}/labels/${label.id}`,
      {
        method: 'DELETE',
      },
    );
    if (response.ok) {
      if (response.headers.get('content-type')?.includes('application/json')) {
        const data = await response.json();
        console.log(data);
      }
      await fetchLabelList();
    }
  };

  return (
    <li
      css={{
        width: '100%',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'space-between',
        padding: '32px',

        boxSizing: 'border-box',
      }}
    >
      {isEditing ? (
        <LabelEditTable
          header={<TableHeader title="레이블 편집" />}
          fetchLabelList={fetchLabelList}
          label={label}
          typeVariant="edit"
          onAddTableClose={onEditLabelClose}
        />
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
              fillColor={label.backgroundColor}
              textColor={label.textColor}
            >
              {label.name}
            </InformationTag>
          </div>

          <span
            css={{
              width: '870px',
              color: theme.neutral.text.weak,
              font: theme.fonts.displayMedium16,
            }}
          >
            {label.description}
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
              onClick={onDeleteLabel}
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
