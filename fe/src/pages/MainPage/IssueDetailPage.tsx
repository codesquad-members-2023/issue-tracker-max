import IssueDetailBody from "@components/IssueDetail/IssueDetailBody";
import IssueDetailHeader from "@components/IssueDetail/IssueDetailHeader";
import { IssueDetails } from "@customTypes/index";
import useFetch from "@hooks/useFetch";
import { getIssueDetails } from "api";
import { useCallback } from "react";
import { useParams } from "react-router-dom";

export default function IssueDetailPage() {
  const { issueId } = useParams();
  const issueNumber = parseInt(issueId!);

  const { data: issueDetails, reFetch: updateIssueDetails } =
    useFetch<IssueDetails>(
      useCallback(() => getIssueDetails(issueNumber), [issueNumber])
    );

  return (
    <>
      {issueDetails && (
        <>
          <IssueDetailHeader
            {...{
              issueDetails,
              updateIssueDetails,
            }}
          />
          <IssueDetailBody
            {...{
              issueNumber,
              issueDetails,
              updateIssueDetails,
            }}
          />
        </>
      )}
    </>
  );
}
