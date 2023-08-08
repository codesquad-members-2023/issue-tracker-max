import { useState } from 'react';
import { SubNav } from '@components/labelListPage/SubNav';
import { Body } from '@components/labelListPage/Body';
type Props = {};

const mockData: Label[] = [
  {
    labelId: 1,
    name: 'feat',
    textColor: 'light',
    backgroundColor: '#FF0000',
    description: '새로운 기능을 추가한다.',
  },
  {
    labelId: 2,
    name: 'fix',
    textColor: 'dark',
    backgroundColor: '#800000',
    description: '버그를 고친다.',
  },
  {
    labelId: 3,
    name: 'BE',
    textColor: 'dark',
    backgroundColor: '#600000',
    description: '',
  },
];

export const LabelListPage: React.FC<Props> = ({}) => {
  const [isAddTableOpen, setIsAddTableOpen] = useState(false);
  const [isEditLabelOpen, setIsEditLabelOpen] = useState(false);

  const onAddTableOpen = () => {
    setIsAddTableOpen(true);
  };
  const onAddTableClose = () => {
    setIsAddTableOpen(false);
  };
  const onEditLabelClick = () => {};

  return (
    <div
      css={{
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        width: '100%',
        gap: '24px',
      }}
    >
      <SubNav
        onAddTableOpen={onAddTableOpen}
        labelCount={mockData.length}
        milestoneCount={13}
      />
      <Body
        isAddTableOpen={isAddTableOpen}
        labelList={mockData}
        labelCount={mockData.length}
        onEditLabelClick={onEditLabelClick}
        onAddTableClose={onAddTableClose}
      />
    </div>
  );
};
