import { useState } from 'react';
import { SubNav } from '@components/labelListPage/SubNav';
import { Body } from '@components/labelListPage/Body';
type Props = {};

const mockData: Label[] = [
  {
    id: 1,
    name: 'feat',
    textColor: 'light',
    backgroundColor: '#FF0000',
    description: '새로운 기능을 추가한다.',
  },
  {
    id: 2,
    name: 'fix',
    textColor: 'dark',
    backgroundColor: '#800000',
    description: '버그를 고친다.',
  },
  {
    id: 3,
    name: 'BE',
    textColor: 'dark',
    backgroundColor: '#600000',
    description: '',
  },
];

export const LabelListPage: React.FC<Props> = ({}) => {
  const [isAddTableOpen, setIsAddTableOpen] = useState(false);

  const onAddTableOpen = () => {
    setIsAddTableOpen(true);
  };
  const onAddTableClose = () => {
    setIsAddTableOpen(false);
  };

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
        onAddTableClose={onAddTableClose}
      />
    </div>
  );
};
