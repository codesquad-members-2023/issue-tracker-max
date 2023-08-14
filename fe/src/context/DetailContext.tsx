import React, { createContext, useContext, useState } from 'react';

type DataType = {
  issueDetailPageData: IssueDetailPageData | null;
  setIssueDetailPageData: (value: IssueDetailPageData) => void;
};

const DetailContext = createContext<DataType>({
  issueDetailPageData: null,
  setIssueDetailPageData: () => {},
});

type DataProviderProps = {
  children: React.ReactNode;
};

export const DetailPageDataProvider: React.FC<DataProviderProps> = ({
  children,
}) => {
  const [issueDetailPageData, setIssueDetailPageData] =
    useState<IssueDetailPageData | null>(null);

  return (
    <DetailContext.Provider
      value={{ issueDetailPageData, setIssueDetailPageData }}
    >
      {children}
    </DetailContext.Provider>
  );
};
export const useData = () => useContext(DetailContext);
