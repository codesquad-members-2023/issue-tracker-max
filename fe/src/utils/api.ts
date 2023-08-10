export const fetchData = async (path: string) => {
  try {
    const response = await fetch(
      `${import.meta.env.VITE_APP_BASE_URL}/${path}`,
      {
        method: 'GET',
      },
    );

    const data = await response.json();

    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }

    return data;
  } catch (error) {
    console.error(`There was a problem with the fetch operation: ${error}`);
  }
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
