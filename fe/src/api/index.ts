import {
  IssueDetails,
  IssueItem,
  IssueSidebar,
  Label,
  Milestone,
  User,
} from "@customTypes/index";
import {
  fetcher,
  fetcherFormDataWithBearer,
  fetcherWithBearer,
} from "./fetcher";
import {
  EditAssigneesBody,
  EditLabelsBody,
  EditMilestoneBody,
  NewIssueBody,
} from "./type";

export const postSignup = async (username: string, password: string) => {
  return await fetcher.post("/auth/signup", { loginId: username, password });
};

export const postLogin = async (username: string, password: string) => {
  return await fetcher.post("/auth/login", { loginId: username, password });
};

export const getIssues = async () => {
  return await fetcherWithBearer.get<IssueItem[]>("/issues");
};

export const getIssueDetails = async (issueId: number) => {
  return await fetcherWithBearer.get<IssueDetails>(
    `/issues/${issueId}/details`
  );
};

export const putIssueTitle = async (
  issueId: number,
  body: { title: string }
) => {
  return await fetcherWithBearer.put(`/issues/${issueId}/title`, body);
};

export const putIssueIsOpen = async (
  issueId: number,
  body: { isOpen: boolean }
) => {
  return await fetcherWithBearer.put(`/issues/${issueId}/isOpen`, body);
};

export const putIssueContent = async (
  issueId: number,
  body: { content: string }
) => {
  return await fetcherWithBearer.put(`/issues/${issueId}/content`, body);
};

export const putIssueComment = async (
  issueId: number,
  commentId: number,
  body: { content: string }
) => {
  return await fetcherWithBearer.put(
    `/issues/${issueId}/comments/${commentId}`,
    body
  );
};

export const getIssueSidebar = async (issueId: number) => {
  return await fetcherWithBearer.get<IssueSidebar>(
    `/issues/${issueId}/sidebar`
  );
};

export const getLabels = async () => {
  return await fetcherWithBearer.get<Label[]>("/labels");
};

export const getMilestones = async () => {
  return await fetcherWithBearer.get<Milestone[]>("/milestones");
};

export const getUsers = async () => {
  return await fetcherWithBearer.get<User[]>("/users");
};

export const postIssue = async (body: NewIssueBody) => {
  return await fetcherWithBearer.post<{ issueId: number }>("/issues", body);
};

export const postImage = async (file: File) => {
  const formData = new FormData();
  formData.append("image", file);

  return await fetcherFormDataWithBearer.post<{ fileUrl: string }>(
    "/images/upload",
    formData
  );
};

export const postEditField = async (
  issuesId: number,
  field: "assignees" | "labels" | "milestone",
  body: EditAssigneesBody | EditLabelsBody | EditMilestoneBody
) => {
  return await fetcherWithBearer.post(`/issues/${issuesId}/${field}`, body);
};

export const getComments = async (issueId: number, cursor: number) => {
  return await fetcherWithBearer.get(
    `/issues/${issueId}/comments?cursor=${cursor}`
  );
};

export const postComment = async (
  issueId: number,
  body: { content: string }
) => {
  return await fetcherWithBearer.post(`/issues/${issueId}/comments`, body);
};
