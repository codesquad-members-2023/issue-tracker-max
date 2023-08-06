export const onFetchData = async (path: string) => {
  try {
    const response = await fetch(`${process.env.REACT_APP_API_URL}/${path}`, {
      method: 'GET',
    });

    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }

    const data = await response.json();

    return data;
  } catch (error) {
    console.error(`There was a problem with the fetch operation: ${error}`);
  }
};

export const getUsers = () => {
  return onFetchData('users/previews');
};

export const getLabels = () => {
  return onFetchData('labels/previews');
};

export const getMilestones = () => {
  return onFetchData('milestones/previews');
};
