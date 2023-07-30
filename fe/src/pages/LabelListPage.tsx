import { useState } from 'react';
import { SubNav } from '@components/labelListPage/SubNav';
import { Body } from '@components/labelListPage/Body';
type Props = {};

export const LabelListPage: React.FC = ({}: Props) => {
  const [isAddTableOpen, setIsAddTableOpen] = useState(false);
  const [isEditLabelOpen, setIsEditLabelOpen] = useState(false);

  const onAddTableClick = () => {};

  const onEditLabelClick = () => {};

  return (
    <>
      <SubNav onAddTableClick={onAddTableClick} />
      <Body onEditLabelClick={onEditLabelClick} />
    </>
  );
};
