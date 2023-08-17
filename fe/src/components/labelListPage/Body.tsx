import { Theme, css } from '@emotion/react';
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
  return (
    <>
      {labelList && (
        <div css={bodyStyle}>
          {isAddTableOpen && (
            <LabelEditTable
              header={<TableHeader title="새로운 레이블 추가" />}
              typeVariant="add"
              onAddTableClose={onAddTableClose}
              fetchLabelList={fetchLabelList}
            />
          )}

          <Box
            header={<span className="box-header">{labelCount}개의 레이블</span>}
          >
            <ul>
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
                <li className="label-list">레이블이 없습니다.</li>
              )}
            </ul>
          </Box>
        </div>
      )}
    </>
  );
};

const bodyStyle = (theme: Theme) => css`
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 24px;

  .box-header {
    margin-left: 32px;
    color: ${theme.neutral.text.default};
    font: ${theme.fonts.displayBold16};
  }

  .label-list {
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 32px;
    color: ${theme.neutral.text.weak};
    font: ${theme.fonts.displayMedium16};
  }
`;
