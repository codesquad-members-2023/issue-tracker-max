import { useTheme } from '@emotion/react';
import { Box } from '@components/common/box/Box';
import { LabelItem } from './LabelItem';
import { TableContainer } from '@components/common/Table/TableContainer';
import { TableHeader } from '@components/common/Table/TableHeader';

type Props = {
  labelCount: IssuePageData['labelCount'];
  labelList: Label[];
  isAddTableOpen?: boolean;
  onEditLabelClick?: (id: number) => void;
  onAddTableClose?: () => void;
};

export const Body: React.FC<Props> = ({
  labelCount,
  labelList,
  isAddTableOpen,
  onEditLabelClick,
  onAddTableClose,
}) => {
  const theme = useTheme() as any;

  return (
    <div
      css={{
        width: '100%',
        display: 'flex',
        flexDirection: 'column',
        gap: '24px',
      }}
    >
      {isAddTableOpen && (
        <TableContainer
          tableVariant="label"
          typeVariant="add"
          onAddTableClose={onAddTableClose}
          header={<TableHeader title="새로운  레이블 추가" />}
        ></TableContainer>
      )}

      <Box
        header={
          <span
            css={{
              marginLeft: '32px',
              color: theme.neutral.text.default,
              font: theme.fonts.displayBold16,
            }}
          >
            {labelCount}개의 레이블
          </span>
        }
      >
        {labelList.map((label) => (
          <LabelItem
            key={label.id}
            name={label.name}
            textColor={label.textColor}
            backgroundColor={label.backgroundColor}
            description={label.description}
            // onEditLabelClick={onEditLabelClick}
          />
        ))}
      </Box>
    </div>
  );
};
