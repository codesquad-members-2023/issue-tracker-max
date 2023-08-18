import { IssuesFilter, IssuesFilterAction } from "@customTypes/index";
import {
  generateFilterText,
  parseFilterTextToArray,
} from "@utils/generateFilterText";
import {
  Dispatch,
  ReactNode,
  createContext,
  useContext,
  useReducer,
} from "react";

const IssuesFilterContext = createContext<{
  issuesFilter: IssuesFilter;
} | null>(null);

const IssuesFilterDispatchContext =
  createContext<Dispatch<IssuesFilterAction> | null>(null);

export const INITIAL_FILTER_TEXT = "is:issue is:open";

const initialFilter: IssuesFilter = {
  state: {
    status: "open",
    filterBar: "open",
    author: null,
    assignees: new Set(),
    labels: new Set(),
    milestone: null,
  },
  text: INITIAL_FILTER_TEXT,
};

const issuesFilterReducer = (
  issuesFilter: IssuesFilter,
  action: IssuesFilterAction
): IssuesFilter => {
  switch (action.type) {
    case "SET_AUTHOR": {
      const newIssuesFilter = { ...issuesFilter };
      newIssuesFilter.state.author = action.payload;
      const updatedIssuesFilterText = generateFilterText(newIssuesFilter.state);

      return { ...newIssuesFilter, text: updatedIssuesFilterText };
    }
    case "SET_ASSIGNEES": {
      const newIssuesFilter = { ...issuesFilter };
      newIssuesFilter.state.assignees = action.payload;
      const updatedIssuesFilterText = generateFilterText(newIssuesFilter.state);

      return { ...newIssuesFilter, text: updatedIssuesFilterText };
    }
    case "SET_LABELS": {
      const newIssuesFilter = { ...issuesFilter };
      newIssuesFilter.state.labels = action.payload;
      const updatedIssuesFilterText = generateFilterText(newIssuesFilter.state);

      return { ...newIssuesFilter, text: updatedIssuesFilterText };
    }
    case "SET_MILESTONE": {
      const newIssuesFilter = { ...issuesFilter };
      newIssuesFilter.state.milestone = action.payload;
      const updatedIssuesFilterText = generateFilterText(newIssuesFilter.state);

      return { ...newIssuesFilter, text: updatedIssuesFilterText };
    }
    case "SET_STATUS": {
      const newIssuesFilter = { ...issuesFilter };
      newIssuesFilter.state.status = action.payload;
      const updatedIssuesFilterText = generateFilterText(newIssuesFilter.state);

      return { ...newIssuesFilter, text: updatedIssuesFilterText };
    }
    case "SET_FILTER_BAR": {
      const newIssuesFilter = { ...issuesFilter };

      if (action.payload === "open" || action.payload === "closed") {
        newIssuesFilter.state.filterBar = action.payload;
        newIssuesFilter.state.status = action.payload;
      }

      if (action.payload === "writtenByMe") {
        newIssuesFilter.state.filterBar = action.payload;
        newIssuesFilter.state.author = "@me";
      }

      if (action.payload === "assignedToMe") {
        newIssuesFilter.state.filterBar = action.payload;
        newIssuesFilter.state.assignees.add("@me");
      }

      if (action.payload === "commentedByMe") {
        newIssuesFilter.state.filterBar = action.payload;
      }

      const updatedIssuesFilterText = generateFilterText(newIssuesFilter.state);
      return { ...newIssuesFilter, text: updatedIssuesFilterText };
    }
    case "RESET_FILTER": {
      const newIssuesFilter = { ...issuesFilter };
      newIssuesFilter.state = {
        status: "open",
        filterBar: "open",
        author: null,
        assignees: new Set(),
        labels: new Set(),
        milestone: null,
      };
      newIssuesFilter.text = INITIAL_FILTER_TEXT;
      return newIssuesFilter;
    }

    // TODO: 개선 필요
    case "SET_FILTER_TEXT": {
      const newIssuesFilter = { ...issuesFilter, text: action.payload };
      const filterTextArray = parseFilterTextToArray(action.payload);

      filterTextArray.forEach(({ key, value }) => {
        if (key === "author") {
          issuesFilter.state.author = value;
        }
        if (key === "assignee") {
          issuesFilter.state.assignees.add(value);
        }
        if (key === "label") {
          issuesFilter.state.labels.add(value);
        }
        if (key === "milestone") {
          issuesFilter.state.milestone = value;
        }
        if (key === "is" && value === "open") {
          issuesFilter.state.status = value;
          issuesFilter.state.filterBar = value;
        }
        if (key === "is" && value === "closed") {
          issuesFilter.state.status = value;
          issuesFilter.state.filterBar = value;
        }
      });
      return newIssuesFilter;
    }
  }
};

export const IssuesFilterProvider = ({ children }: { children: ReactNode }) => {
  const [issuesFilter, issuesFilterDispatch] = useReducer(
    issuesFilterReducer,
    initialFilter
  );

  return (
    <IssuesFilterContext.Provider value={{ issuesFilter }}>
      <IssuesFilterDispatchContext.Provider value={issuesFilterDispatch}>
        {children}
      </IssuesFilterDispatchContext.Provider>
    </IssuesFilterContext.Provider>
  );
};

export function useIssuesFilter() {
  const context = useContext(IssuesFilterContext);

  if (!context) {
    throw new Error("Cannot find IssuesFilterProvider");
  }

  return context;
}

export function useIssuesFilterDispatch() {
  const context = useContext(IssuesFilterDispatchContext);

  if (!context) {
    throw new Error("Cannot find IssuesFilterProvider");
  }

  return context;
}
