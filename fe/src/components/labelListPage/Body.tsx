import { AddLabelTable } from './AddLabelTable';
import { InitialLabelTable } from './InitialLabelTable';

type Props = {
  onEditLabelClick?: (id: number) => void;
};

export const Body: React.FC = ({ onEditLabelClick }: Props) => {
  return (
    <>
      <AddLabelTable />
      <InitialLabelTable />
    </>
  );
};
