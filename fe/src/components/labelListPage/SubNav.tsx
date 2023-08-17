import { useTheme } from '@emotion/react';
import { TabButton } from '@components/common/tabButton/TabButton';
import { ReactComponent as Plus } from '@assets/icons/plus.svg';
import { Button } from '@components/common/Button';

type Props = {
  labelCount: IssuePageData['labelCount'];
  milestoneCount: IssuePageData['milestoneCount'];
  isAddTableOpen?: boolean;
  onAddTableOpen?: () => void;
};

export const SubNav: React.FC<Props> = ({
  labelCount,
  milestoneCount,
  isAddTableOpen,
  onAddTableOpen,
}) => {
  const theme = useTheme() as any;
  return (
    <div
      css={{
        width: '100%',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'space-between',
      }}
    >
      <TabButton {...{ labelCount, milestoneCount }} />
      <Button
        typeVariant="contained"
        size="S"
        disabled={isAddTableOpen}
        onClick={onAddTableOpen}
      >
        <Plus stroke={theme.brand.text.default} />
        레이블 추가
      </Button>
    </div>
  );
};
