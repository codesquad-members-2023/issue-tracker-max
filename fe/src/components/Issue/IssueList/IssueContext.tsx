import { createContext } from 'react';

export const IssueContext = createContext<IssueData>({
  labelCount: 0,
  milestoneCount: 0,
  openIssueCount: 0,
  closedIssueCount: 0,
  issues: [
    {
      id: 0,
      isOpen: true,
      title: '',
      history: {
        editor: '',
        modifiedAt: '',
      },
      labels: [],
      assignees: [],
      writer: {
        id: 0,
        name: '',
      },
      milestone: {
        id: 0,
        title: '',
      },
    },
  ],
});
