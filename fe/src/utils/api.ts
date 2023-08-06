export const onFetchData = async (path: string) => {
  try {
    const response = await fetch(
      // `${import.meta.env.VITE_REACT_APP_API_URL}/${path}`,
      `${path}`,
      {
        method: 'GET',
      },
    );
    const data = await response.json();
    console.log(data);
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }

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
