import {
  Dispatch,
  ReactNode,
  SetStateAction,
  createContext,
  useEffect,
  useState,
} from 'react';
import { IssueData } from '../../type/issue.type';
import { customFetch } from '../../util/customFetch';
import { GetIssueRes } from '../../type/Response.type';
import { useNavigate } from 'react-router-dom';

type IssueProviderProps = {
  children: ReactNode;
};

export const IssueContext = createContext<{
  issueIsOpen: boolean;
  assignee: number;
  writer: number;
  commenter: number;
  label: number;
  milestone: number;
  filter: string;
  issueList: IssueData;
  checkedItemIdList: number[];
  setIssueIsOpen: Dispatch<SetStateAction<boolean>>;
  setAssignee: Dispatch<SetStateAction<number>>;
  setWriter: Dispatch<SetStateAction<number>>;
  setCommenter: Dispatch<SetStateAction<number>>;
  setLabel: Dispatch<SetStateAction<number>>;
  setMilestone: Dispatch<SetStateAction<number>>;
  setFilter: Dispatch<SetStateAction<string>>;
  setCheckedItemIdList: Dispatch<SetStateAction<number[]>>;
} | null>(null);

export function FilterProvider({ children }: IssueProviderProps) {
  const [issueIsOpen, setIssueIsOpen] = useState(true);
  const [assignee, setAssignee] = useState(0);
  const [writer, setWriter] = useState(0);
  const [commenter, setCommenter] = useState(0);
  const [label, setLabel] = useState(0);
  const [milestone, setMilestone] = useState(0);
  const [filter, setFilter] = useState('');
  const [issueList, setIssueList] = useState<IssueData>({
    labelCount: 0,
    milestoneCount: 0,
    openIssueCount: 0,
    closedIssueCount: 0,
    issues: [],
  });
  const [checkedItemIdList, setCheckedItemIdList] = useState<number[]>([]);
  const navigate = useNavigate();

  useEffect(() => {
    const filterConditions = [];

    filterConditions.push(`issueIsOpen=${issueIsOpen}`);

    if (assignee) {
      filterConditions.push(`assignee=${assignee}`);
    }
    if (writer) {
      filterConditions.push(`writer=${writer}`);
    }
    if (commenter) {
      filterConditions.push(`commenter=${commenter}`);
    }
    if (label) {
      filterConditions.push(`label=${label}`);
    }
    if (milestone) {
      filterConditions.push(`milestone=${milestone}`);
    }

    const newFilter =
      filterConditions.length > 0 ? `?${filterConditions.join('&')}` : '';

    setFilter(newFilter);
  }, [issueIsOpen, assignee, writer, commenter, label, milestone]);

  useEffect(() => {
    (async () => {
      try {
        const response = await customFetch<GetIssueRes>({
          subUrl: `api/${filter}`,
        });

        if (!response.success) {
          if (response.errorCode?.status === 401) {
            navigate('/sign-in');
            return;
          }
        }

        if (response.data) {
          setIssueList(response.data);
        }
      } catch (error) {
        navigate('/sign-in');
      }
    })();
  }, [filter]);

  return (
    <IssueContext.Provider
      value={{
        issueIsOpen,
        assignee,
        writer,
        commenter,
        label,
        milestone,
        filter,
        issueList,
        checkedItemIdList,
        setIssueIsOpen,
        setAssignee,
        setWriter,
        setCommenter,
        setLabel,
        setMilestone,
        setFilter,
        setCheckedItemIdList,
      }}
    >
      {children}
    </IssueContext.Provider>
  );
}
