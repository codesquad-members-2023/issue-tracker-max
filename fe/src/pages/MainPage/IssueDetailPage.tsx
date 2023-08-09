import IssueDetailBody from "@components/Issues/IssueDetail/IssueDetailBody";
import IssueDetailHeader from "@components/Issues/IssueDetail/IssueDetailHeader";
import { IssueDetails } from "@customTypes/index";
import useFetch from "@hooks/useFetch";
import { getIssueDetails } from "api";
import { useCallback } from "react";
import { useParams } from "react-router-dom";

export default function IssueDetailPage() {
  const { issueId } = useParams();
  const issueNumber = parseInt(issueId!);

  const { data: issueDetails, setData: setIssueDetails } =
    useFetch<IssueDetails>(
      useCallback(() => getIssueDetails(issueNumber), [issueNumber])
    );

  const updateIssueTitle = (newTitle: string) => {
    setIssueDetails((prev) => {
      return prev ? { ...prev, title: newTitle } : prev;
    });
  };

  const updateIssueIsOpen = () => {
    setIssueDetails((prev) => {
      return prev ? { ...prev, isOpen: !prev.isOpen } : prev;
    });
  };

  const updateIssueContent = (newContent: string) => {
    setIssueDetails((prev) => {
      return prev ? { ...prev, content: newContent } : prev;
    });
  };

  // TODO: comment 추가 전달
  // const updateIssueCommentCount = () => {
  //   setIssueDetails((prev) => {
  //     return prev ? { ...prev, commentCount: prev.commentCount + 1 } : prev;
  //   });
  // };

  return (
    <>
      <IssueDetailHeader
        {...{
          issueDetails,
          updateIssueTitle,
          updateIssueIsOpen,
          numComments: 0,
        }}
      />
      <IssueDetailBody {...{ issueNumber, issueDetails, updateIssueContent }} />
    </>
  );
}
