import { fetchData } from '@utils/api';

export const uploadFile = async (file: File) => {
  const formData = new FormData();
  formData.append('file', file);

  return fetchData('/file-upload', {
    method: 'POST',
    body: formData,
  });
};
