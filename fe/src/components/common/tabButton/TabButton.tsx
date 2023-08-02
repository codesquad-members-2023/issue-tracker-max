import { Tab } from './Tab';
import { TabContainer } from './TabContainer';

type Props = {
  labelCount: number;
  milestoneCount: number;
};

export const TabButton: React.FC<Props> = ({ labelCount, milestoneCount }) => {
  return (
    <TabContainer>
      <Tab type="label" count={labelCount} />
      <Tab type="milestone" count={milestoneCount} />
    </TabContainer>
  );
};
