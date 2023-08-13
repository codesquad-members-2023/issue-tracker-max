import { createContext } from 'react';

export const LabelContext = createContext<LabelData>({
  milestoneCount: 0,
  labels: [
    {
      id: 0,
      title: '',
      description: '',
      textColor: '',
      backgroundColor: '',
    },
  ],
});
