import { ProgressBar } from '../ProgressBar';
import { ProgressLabel } from '../ProgressLabel';

type selectedMilestonesData = {
  id: number;
  name: string;
  progress: number;
};

type Props = {
  selectedMilestonesData: selectedMilestonesData;
};

export const ListMilestone: React.FC<Props> = ({ selectedMilestonesData }) => {
  return (
    <div>
      <ProgressBar progress={selectedMilestonesData.progress} />
      <ProgressLabel name={selectedMilestonesData.name} />
    </div>
  );
};
