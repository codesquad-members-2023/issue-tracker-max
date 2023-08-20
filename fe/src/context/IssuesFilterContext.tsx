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
      const newIssuesFilterState = {
        ...issuesFilter.state,
        author: action.payload,
      };
      const updatedIssuesFilterText = generateFilterText(newIssuesFilterState);

      return {
        state: newIssuesFilterState,
        text: updatedIssuesFilterText,
      };
    }
    case "SET_ASSIGNEES": {
      const newIssuesFilterState = {
        ...issuesFilter.state,
        assignees: action.payload,
      };
      const updatedIssuesFilterText = generateFilterText(newIssuesFilterState);

      return {
        state: newIssuesFilterState,
        text: updatedIssuesFilterText,
      };
    }

    case "SET_LABELS": {
      const newIssuesFilterState = {
        ...issuesFilter.state,
        labels: action.payload,
      };
      const updatedIssuesFilterText = generateFilterText(newIssuesFilterState);

      return {
        state: newIssuesFilterState,
        text: updatedIssuesFilterText,
      };
    }
    case "SET_MILESTONE": {
      const newIssuesFilterState = {
        ...issuesFilter.state,
        milestone: action.payload,
      };
      const updatedIssuesFilterText = generateFilterText(newIssuesFilterState);

      return {
        state: newIssuesFilterState,
        text: updatedIssuesFilterText,
      };
    }
    case "SET_STATUS": {
      const newIssuesFilterState = {
        ...issuesFilter.state,
        status: action.payload,
      };
      const updatedIssuesFilterText = generateFilterText(newIssuesFilterState);

      return {
        state: newIssuesFilterState,
        text: updatedIssuesFilterText,
      };
    }
    case "SET_FILTER_BAR": {
      const newIssuesFilterState = { ...issuesFilter.state };

      if (action.payload === "open" || action.payload === "closed") {
        newIssuesFilterState.filterBar = action.payload;
        newIssuesFilterState.status = action.payload;
      }

      if (action.payload === "writtenByMe") {
        newIssuesFilterState.filterBar = action.payload;
        newIssuesFilterState.author = "@me";
      }

      if (action.payload === "assignedToMe") {
        newIssuesFilterState.filterBar = action.payload;
        newIssuesFilterState.assignees.add("@me");
      }

      if (action.payload === "commentedByMe") {
        newIssuesFilterState.filterBar = action.payload;
      }

      const updatedIssuesFilterText = generateFilterText(newIssuesFilterState);
      return {
        state: newIssuesFilterState,
        text: updatedIssuesFilterText,
      };
    }
    case "RESET_FILTER": {
      const newIssuesFilterState = {
        status: "open" as const,
        filterBar: "open" as const,
        author: null,
        assignees: new Set() as Set<string>,
        labels: new Set() as Set<string>,
        milestone: null,
      };

      return {
        state: newIssuesFilterState,
        text: INITIAL_FILTER_TEXT,
      };
    }

    // TODO: 개선 필요
    case "SET_FILTER_TEXT": {
      const newIssuesFilterState: IssuesFilter["state"] = {
        status: null,
        author: null,
        filterBar: null,
        assignees: new Set(),
        labels: new Set(),
        milestone: null,
      };
      const filterTextArray = parseFilterTextToArray(action.payload);

      filterTextArray.forEach(({ key, value }) => {
        if (key === "author") {
          newIssuesFilterState.author = value;
        }
        if (key === "assignee") {
          newIssuesFilterState.assignees.add(value);
        }
        if (key === "label") {
          newIssuesFilterState.labels.add(value);
        }
        if (key === "milestone") {
          newIssuesFilterState.milestone = value;
        }
        if (key === "is" && value === "open") {
          newIssuesFilterState.status = value;
          newIssuesFilterState.filterBar = value;
        }
        if (key === "is" && value === "closed") {
          newIssuesFilterState.status = value;
          newIssuesFilterState.filterBar = value;
        }
      });

      return {
        state: newIssuesFilterState,
        text: action.payload,
      };
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
