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
  return fetchData('/issues' + query);
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
    },
    body: JSON.stringify({
      issueIds,
      status,
    }),
  });
};

export const getIssueDetail = (id: string | number) => {
  return fetchData(`/issues/${id}`);
};

export const deleteIssue = (id: number) => {
  return fetchData(`/issues/${id}`, {
    method: 'DELETE',
  });
};

export const loginUser = (id: string, password: string) => {
  return fetchData('login', {
    method: 'POST',
    body: JSON.stringify({
      id,
      password,
    }),
  });
};

export const signUpUser = (loginId: string, password: string) => {
  return fetchData('users', {
    method: 'POST',
    body: JSON.stringify({
      loginId,
      password,
    }),
  });
};

export const getUserPreviews = () => {
  return fetchData('/users/previews');
};

export const getLabelPreviews = () => {
  return fetchData('/labels/previews');
};

export const getMilestonePreviews = () => {
  return fetchData('/milestones/previews');
};

export const getLabelList = () => {
  return fetchData('/labels');
};

export const postNewLabel = (
  name: string,
  textColor: string,
  backgroundColor: string,
  description: string,
) => {
  return fetchData('/labels', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
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
  return fetchData('/labels', {
    method: 'PATCH',
    headers: {
      'Content-Type': 'application/json',
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
  return fetchData(`/labels/${id}`, {
    method: 'DELETE',
  });
};

export const getMilestonesWithQuery = (query: string) => {
  return fetchData('/milestones' + query);
};

export const postNewMilestone = (
  name: string,
  deadline: string,
  description: string,
) => {
  return fetchData('/milestones', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
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
  return fetchData('/milestones', {
    method: 'PATCH',
    headers: {
      'Content-Type': 'application/json',
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
  return fetchData(`/milestones/${id}`, {
    method: 'DELETE',
  });
};

export const editMilestoneStatus = (id: number, status: 'open' | 'closed') => {
  return fetchData(`/milestones/${status}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
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
  return fetchData('/comments', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
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
    },
    body: JSON.stringify({
      id,
      contents,
    }),
  });
};

export const deleteComment = (id: string | number) => {
  return fetchData(`/comments/${id}`, {
    method: 'DELETE',
  });
};
