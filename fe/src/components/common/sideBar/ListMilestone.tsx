import { ProgressBar } from '../ProgressBar';
import { ProgressLabel } from '../ProgressLabel';

type selectedMilestonesData = {
  milestoneId: number;
  name: string;
  progress: number;
};

type Props = {
  selectedMilestonesData: selectedMilestonesData[];
};

export const ListMilestone: React.FC<Props> = ({ selectedMilestonesData }) => {
  return (
    <>
      {selectedMilestonesData.map((milestone) => (
        <div key={milestone.milestoneId}>
          <ProgressBar progress={milestone.progress} />
          <ProgressLabel name={milestone.name} />
        </div>
      ))}
    </>
  );
};
