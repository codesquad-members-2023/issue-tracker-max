import { createContext } from 'react';

export const MilestoneContext = createContext<MilestoneData>({
  labelCount: 0,
  oppositeCount: 0,
  milestones: [
    {
      id: 0,
      title: '',
      description: '',
      dueDate: '',
      openIssueCount: 0,
      closedIssueCount: 0,
    },
  ],
});
