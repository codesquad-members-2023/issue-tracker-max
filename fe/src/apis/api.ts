import { getAccessToken } from './localStorage';

const BASE_URL = import.meta.env.VITE_APP_BASE_URL;

export const fetchData = async (path: string, options?: RequestInit) => {
  console.log(path, options);

  const response = await fetch(BASE_URL + path, options);

  if (!response.ok) {
    throw new Error(`HTTP error! status: ${response.status}`);
  }

  if (response.headers.get('content-type') === 'application/json') {
    const data = await response.json();

    return data;
  }
};

export const getIssuesWithQuery = (query: string) => {
  const accessToken = getAccessToken();

  return fetchData('/issues' + query, {
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
  });
};

export const postNewIssue = (
  title: string,
  contents: string,
  authorId: number,
  assigneeIds: number[],
  labelIds: number[],
  milestoneId: number,
) => {
  return fetchData('/issues/new', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${accessToken}`,
    },
    body: JSON.stringify({
      title,
      contents,
      authorId,
      assigneeIds,
      labelIds,
      milestoneId,
    }),
  });
};

export const patchIssueTitle = (id: number, title: string) => {
  return fetchData('/issues/title', {
    method: 'PATCH',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${accessToken}`,
    },
    body: JSON.stringify({
      id,
      title,
    }),
  });
};

export const patchIssueContents = (id: number, contents: string) => {
  return fetchData('/issues/contents', {
    method: 'PATCH',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${accessToken}`,
    },
    body: JSON.stringify({
      id,
      contents,
    }),
  });
};

export const editIssueLabel = (id: string, labelIds: number[]) => {
  return fetchData(`/issues/${id}/labels`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${accessToken}`,
    },
    body: JSON.stringify({
      labelIds,
    }),
  });
};

export const editIssueMilestone = (id: string, milestoneId: number | null) => {
  return fetchData(`/issues/${id}/milestone`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${accessToken}`,
    },
    body: JSON.stringify({
      milestoneId,
    }),
  });
};

export const editIssueAssignees = (id: string, assigneeIds: number[]) => {
  return fetchData(`/issues/${id}/assignees`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${accessToken}`,
    },
    body: JSON.stringify({
      assigneeIds,
    }),
  });
};

export const editIssueStatus = (
  issueIds: number[],
  status: 'open' | 'closed',
) => {
  return fetchData('/issues/status', {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${accessToken}`,
    },
    body: JSON.stringify({
      issueIds,
      status,
    }),
  });
};

export const getIssueDetail = (id: string | number) => {
  const accessToken = getAccessToken();

  return fetchData(`/issues/${id}`, {
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
  });
};

export const deleteIssue = (id: number) => {
  return fetchData(`/issues/${id}`, {
    method: 'DELETE',
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
  });
};

export const loginUser = (id: string, password: string) => {
  return fetchData('/users/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      loginId: id,
      password,
    }),
  });
};

export const signUpUser = (loginId: string, password: string) => {
  return fetchData('/users/signup', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      loginId,
      password,
    }),
  });
};

export const getUserPreviews = () => {
  const accessToken = getAccessToken();

  return fetchData('/users/previews', {
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
  });
};

export const getLabelPreviews = () => {
  const accessToken = getAccessToken();

  return fetchData('/labels/previews', {
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
  });
};
export const getMilestonePreviews = () => {
  const accessToken = getAccessToken();

  return fetchData('/milestones/previews', {
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
  });
};
export const getLabelList = () => {
  const accessToken = getAccessToken();

  return fetchData('/labels', {
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
  });
};
export const postNewLabel = (
  name: string,
  textColor: string,
  backgroundColor: string,
  description: string,
) => {
  const accessToken = getAccessToken();

  return fetchData('/labels', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${accessToken}`,
    },
    body: JSON.stringify({
      name,
      textColor,
      backgroundColor,
      description,
    }),
  });
};

export const editLabel = (
  id: number,
  name: string,
  textColor: string,
  backgroundColor: string,
  description: string,
) => {
  const accessToken = getAccessToken();

  return fetchData('/labels', {
    method: 'PATCH',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${accessToken}`,
    },
    body: JSON.stringify({
      id,
      name,
      textColor,
      backgroundColor,
      description,
    }),
  });
};

export const deleteLabel = (id: string | number) => {
  const accessToken = getAccessToken();

  return fetchData(`/labels/${id}`, {
    method: 'DELETE',
  });
};

export const getMilestonesWithQuery = (query: string) => {
  const accessToken = getAccessToken();

  return fetchData('/milestones' + query, {
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
  });
};

export const postNewMilestone = (
  name: string,
  deadline: string,
  description: string,
) => {
  const accessToken = getAccessToken();

  return fetchData('/milestones', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${accessToken}`,
    },
    body: JSON.stringify({
      name,
      deadline,
      description,
    }),
  });
};

export const editMilestone = (
  id: number,
  name: string,
  deadline: string,
  description: string,
) => {
  const accessToken = getAccessToken();

  return fetchData('/milestones', {
    method: 'PATCH',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${accessToken}`,
    },
    body: JSON.stringify({
      id,
      name,
      deadline,
      description,
    }),
  });
};

export const deleteMilestone = (id: string | number) => {
  const accessToken = getAccessToken();

  return fetchData(`/milestones/${id}`, {
    method: 'DELETE',
  });
};

export const editMilestoneStatus = (id: number, status: 'open' | 'closed') => {
  const accessToken = getAccessToken();

  return fetchData(`/milestones/${status}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${accessToken}`,
    },
    body: JSON.stringify({
      id,
      status,
    }),
  });
};

export const postNewComment = (
  issueId: number,
  authorId: number,
  contents: string,
) => {
  const accessToken = getAccessToken();

  return fetchData('/comments', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${accessToken}`,
    },
    body: JSON.stringify({
      issueId,
      authorId,
      contents,
    }),
  });
};

export const editComment = (id: number | undefined, contents: string) => {
  return fetchData(`/comments`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${accessToken}`,
    },
    body: JSON.stringify({
      id,
      contents,
    }),
  });
};

export const deleteComment = (id: string | number) => {
  const accessToken = getAccessToken();

  return fetchData(`/comments/${id}`, {
    method: 'DELETE',
  });
};
