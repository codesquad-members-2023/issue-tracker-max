import { IssuesFilterState } from "@customTypes/index";

export const generateFilterText = (filterState: IssuesFilterState) => {
  const { status, filterBar, author, assignees, labels, milestone } =
    filterState;

  const filterText = ["is:issue"];
  if (status) {
    filterText.push(`is:${status}`);
  }
  if (filterBar === "commentedByMe") {
    filterText.push("commenter:@me");
  }
  if (author) {
    filterText.push(`author:${author}`);
  }
  if (assignees.size) {
    assignees.has("no")
      ? filterText.push("no:assignee")
      : [...assignees].map((assignee) =>
          filterText.push(`assignee:${assignee}`)
        );
  }
  if (labels.size) {
    labels.has("no")
      ? filterText.push("no:label")
      : [...labels].map((label) => filterText.push(`label:"${label}"`));
  }
  if (milestone) {
    milestone === "no"
      ? filterText.push("no:milestone")
      : filterText.push(`milestone:"${milestone}"`);
  }

  return filterText.join(" ");
};

export const parseFilterTextToArray = (
  filterText: string
): { key: string; value: string }[] => {
  const parts = filterText.split(" ");
  const filterTextArray = [];

  for (const part of parts) {
    const [key, value] = part.split(":");
    filterTextArray.push({ key, value });
  }

  return filterTextArray;
};
