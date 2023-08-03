import { ProgressBar } from '../ProgressBar';
import { ProgressLabel } from '../ProgressLabel';

type selectedMilestonesData = {
  id: number;
  name: string;
  progress: number;
};

type Props = {
  selectedMilestonesData: selectedMilestonesData[];
};

export const ListMilestone: React.FC<Props> = ({ selectedMilestonesData }) => {
  console.log(selectedMilestonesData);

  return (
    <>
      {selectedMilestonesData.map((milestone) => (
        <div key={milestone.id}>
          <ProgressBar progress={milestone.progress} />
          <ProgressLabel name={milestone.name} />
        </div>
      ))}
    </>
  );
};
