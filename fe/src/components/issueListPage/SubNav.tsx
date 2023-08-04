import { FilterBar } from '@components/common/FilterBar';
import { Button } from '@components/common/Button';
import { TabButton } from '@components/common/tabButton/TabButton';
import { ReactComponent as PlusIcon } from '@assets/icons/plus.svg';
import { useTheme } from '@emotion/react';

type Props = {
  labelCount: IssuePageData['labelCount'];
  milestoneCount: IssuePageData['milestoneCount'];
  filterValue: string;
  onChangeFilterValue: (value: string) => void;
};

export const SubNav: React.FC<Props> = ({
  labelCount,
  milestoneCount,
  filterValue,
  onChangeFilterValue,
}) => {
  const theme = useTheme() as any;

  return (
    <div
      css={{
        display: 'flex',
        justifyContent: 'space-between',
        marginBottom: '24px',
      }}
    >
      <div css={{ width: '560px' }}>
        <FilterBar {...{ filterValue, onChangeFilterValue }} />
      </div>

      <div
        className="right-side-container"
        css={{ display: 'flex', gap: '16px' }}
      >
        <TabButton {...{ labelCount, milestoneCount }} />

        <Button className="add-issue-button" size="S">
          <PlusIcon stroke={theme.brand.text.default} />
          <span>이슈 작성</span>
        </Button>
      </div>
    </div>
  );
};
