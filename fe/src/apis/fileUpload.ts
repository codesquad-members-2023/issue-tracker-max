import { fetchData } from 'apis/api';

export const uploadFile = async (file: File) => {
  const formData = new FormData();
  formData.append('file', file);

  return fetchData('/files-upload', {
    method: 'POST',
    body: formData,
  });
};
