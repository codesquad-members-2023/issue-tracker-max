import React, { createContext, useContext, useState } from "react";
import { useNavigate } from "react-router-dom";

/* 수정하기. 필터 선택 후 필터링된 페이지에서 
  뒤로갔을 때 이전 상태가 아닌 선택이 그대로 남아있음 */

interface DropdownFilter {
  [key: string]: string[];
  assignee: string[];
  author: string[];
  label: string[];
  milestone: string[];
}

interface FilterState {
  dropdownFilter: DropdownFilter;
  filterBar: string[];
}

const initialState: FilterState = {
  dropdownFilter: {
    assignee: [],
    author: [],
    label: [],
    milestone: [],
  },
  filterBar: [],
};

interface FilterProviderProps {
  children: React.ReactNode;
}

interface FilterContextType {
  state: FilterState;
  setDropdownFilter: (filter: string, name: string[]) => void;
  setFilterBar: (filter: string[]) => void;
  resetFilters: () => void;
}

const FilterContext = createContext<FilterContextType | undefined>(undefined);

export const useFilter = () => {
  const context = useContext(FilterContext);
  if (!context) {
    throw new Error("useFilter must be used within a FilterProvider");
  }
  return context;
};

export const FilterProvider: React.FC<FilterProviderProps> = ({ children }) => {
  const navigate = useNavigate();

  const [state, setState] = useState<FilterState>(initialState);

  const updateQuery = (newState: FilterState) => {
    const queryParts = [...newState.filterBar];

    for (const filter in newState.dropdownFilter) {
      (
        newState.dropdownFilter[
          filter as keyof typeof newState.dropdownFilter
        ] || []
      ).forEach((name) => {
        queryParts.push(`${filter}:${name}`);
      });
    }

    const newQuery = queryParts.join(" ");

    navigate(`/issues/filtered?q=${newQuery}`);
  };

  const setDropdownFilter = (filter: string, name: string[]) => {
    const deleteSpaceForQuery = name.map((item) => item.split(" ").join(""));

    setState((prev) => {
      const newState = {
        ...prev,
        dropdownFilter: {
          ...prev.dropdownFilter,
          [filter]: name,
        },
      };
      updateQuery({
        ...newState,
        dropdownFilter: {
          ...newState.dropdownFilter,
          [filter]: deleteSpaceForQuery,
        },
      });
      return newState;
    });
  };

  const setFilterBar = (filter: string[]) => {
    setState((prev) => {
      const newState = {
        ...prev,
        filterBar: filter,
      };
      updateQuery(newState);
      return newState;
    });
  };

  const resetFilters = () => {
    setState(initialState);
  };

  return (
    <FilterContext.Provider
      value={{ state, setDropdownFilter, setFilterBar, resetFilters }}
    >
      {children}
    </FilterContext.Provider>
  );
};
