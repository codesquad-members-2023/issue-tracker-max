import { FilterBar } from '@components/common/FilterBar';
import { Button } from '@components/common/Button';
import { TabButton } from '@components/common/tabButton/TabButton';
import { ReactComponent as PlusIcon } from '@assets/icons/plus.svg';
import { useTheme } from '@emotion/react';
import { useNavigate } from 'react-router-dom';

type Props = {
  labelCount: IssuePageData['labelCount'];
  milestoneCount: IssuePageData['milestoneCount'];
};

export const SubNav: React.FC<Props> = ({ labelCount, milestoneCount }) => {
  const theme = useTheme() as any;
  const navigate = useNavigate();

  return (
    <div
      css={{
        display: 'flex',
        justifyContent: 'space-between',
        marginBottom: '24px',
      }}
    >
      <div css={{ width: '560px' }}>
        <FilterBar />
      </div>

      <div
        className="right-side-container"
        css={{ display: 'flex', gap: '16px' }}
      >
        <TabButton {...{ labelCount, milestoneCount }} />

        <Button
          className="add-issue-button"
          size="S"
          onClick={() => navigate('/add')}
        >
          <PlusIcon stroke={theme.brand.text.default} />
          <span>이슈 작성</span>
        </Button>
      </div>
    </div>
  );
};
