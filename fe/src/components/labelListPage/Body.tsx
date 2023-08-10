import { useTheme } from '@emotion/react';
import { Box } from '@components/common/box/Box';
import { LabelItem } from './LabelItem';
import { TableHeader } from '@components/common/Table/TableHeader';
import { LabelEditTable } from './LabelEditTable';

type Props = {
  labelCount: IssuePageData['labelCount'];
  labelList: Label[];
  isAddTableOpen?: boolean;
  onAddTableClose: () => void;
  fetchLabelList: () => Promise<void>;
};

export const Body: React.FC<Props> = ({
  labelCount,
  labelList,
  isAddTableOpen,
  onAddTableClose,
  fetchLabelList,
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
        <LabelEditTable
          header={<TableHeader title="새로운 레이블 추가" />}
          typeVariant="add"
          onAddTableClose={onAddTableClose}
          fetchLabelList={fetchLabelList}
        />
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
        {labelList.length > 0 ? (
          <>
            {labelList.map((label) => (
              <LabelItem
                key={label.id}
                label={label}
                fetchLabelList={fetchLabelList}
              />
            ))}
          </>
        ) : (
          <li
            css={{
              width: '100%',
              display: 'flex',
              alignItems: 'center',
              justifyContent: 'center',
              padding: '32px',
              boxSizing: 'border-box',
              color: theme.neutral.text.weak,
              font: theme.fonts.displayMedium16,
            }}
          >
            레이블이 없습니다.
          </li>
        )}
      </Box>
    </div>
  );
};
