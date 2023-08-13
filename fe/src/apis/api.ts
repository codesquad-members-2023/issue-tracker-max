const BASE_URL = import.meta.env.VITE_APP_BASE_URL;

export const fetchData = async (path: string, options?: RequestInit) => {
  const response = await fetch(BASE_URL + path, options);

  if (!response.ok) {
    throw new Error(`HTTP error! status: ${response.status}`);
  }

  const data = await response.json();

  return data;
};

export const getUsers = () => {
  return fetchData('users/previews');
};

export const getLabels = () => {
  return fetchData('labels/previews');
};

export const getMilestones = () => {
  return fetchData('milestones/previews');
};

export const getIssueListPageData = (query: string) => {
  return fetchData('issues' + query);
};

export const getLabelListPageData = () => {
  return fetchData('labels');
};

export const getIssueDetailPageData = (id: string) => {
  return fetchData(`issues/${id}`);
};
