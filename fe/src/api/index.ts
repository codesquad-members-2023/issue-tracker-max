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
  Pagination,
} from "./type";

export const postSignup = async (username: string, password: string) => {
  return await fetcher.post("/auth/signup", { loginId: username, password });
};

export const postLogin = async (username: string, password: string) => {
  return await fetcher.post("/auth/login", { loginId: username, password });
};

export const getGitHubLogin = async (code: string) => {
  return await fetcher.get(`/auth/login/oauth/github?code=${code}`);
};

export const postOAuthUsername = async (body: {
  username: string;
  email: string;
}) => {
  return await fetcher.post("/auth/signup/oauth", body);
};

export const getIssues = async (filterQuery?: string, pageIndex?: number) => {
  const queryParams = new URLSearchParams();

  if (filterQuery) {
    queryParams.append("q", filterQuery);
  }

  if (pageIndex) {
    queryParams.append("page", pageIndex.toString());
  }

  const query = queryParams.toString();
  const queryString = query ? `?${query}` : "";

  const response = await fetcherWithBearer.get<{
    pagination: Pagination;
    data: IssueItem[];
  }>(`/issues${queryString}`);

  return response;
};

export const getIssueDetails = async (issueId: number) => {
  return await fetcherWithBearer.get<IssueDetails>(`/issues/${issueId}`);
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

export const putIssuesIsOpen = async (body: {
  issueIds: number[];
  state: "open" | "closed";
}) => {
  return await fetcherWithBearer.put(`/issues`, body);
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

export const deleteIssue = async (issueId: number) => {
  return await fetcherWithBearer.delete(`/issues/${issueId}`);
};

export const getIssueSidebar = async (issueId: number) => {
  return await fetcherWithBearer.get<IssueSidebar>(
    `/issues/${issueId}/sidebar`
  );
};

export const getLabels = async () => {
  return await fetcherWithBearer.get<Label[]>("/labels");
};

export const postLabel = async (body: {
  name: string;
  description: string;
  fontColor: string;
  backgroundColor: string;
}) => {
  return await fetcherWithBearer.post("/labels", body);
};

export const putLabel = async (
  labelId: number,
  body: {
    name: string;
    description: string;
    fontColor: string;
    backgroundColor: string;
  }
) => {
  return await fetcherWithBearer.put(`/labels/${labelId}`, body);
};

export const deleteLabel = async (labelId: number) => {
  return await fetcherWithBearer.delete(`/labels/${labelId}`);
};

export const getLabelCount = async () => {
  return await fetcherWithBearer.get<{ count: number }>(`/labels/count`);
};

export const getMilestones = async (state: "open" | "closed" = "open") => {
  return await fetcherWithBearer.get<Milestone[]>(`/milestones?state=${state}`);
};

export const getMilestoneCount = async () => {
  return await fetcherWithBearer.get<{ count: number }>(`/milestones/count`);
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

export const postMilestone = async (body: {
  milestoneName: string;
  dueDate: string;
  description: string;
}) => {
  return await fetcherWithBearer.post("/milestones", body);
};

export const putMilestoneContent = async (
  milestoneId: number,
  body: {
    milestoneName: string;
    dueDate: string;
    description: string;
  }
) => {
  return await fetcherWithBearer.put(`/milestones/${milestoneId}`, body);
};

export const putMilestoneState = async (
  milestoneId: number,
  state: "open" | "closed"
) => {
  return await fetcherWithBearer.put(
    `/milestones/${milestoneId}?state=${state}`
  );
};

export const deleteMilestone = async (milestoneId: number) => {
  return await fetcherWithBearer.delete(`/milestones/${milestoneId}`);
};
