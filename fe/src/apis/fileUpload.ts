import { fetchData } from 'apis/api';
import { getAccessToken } from './localStorage';

export const uploadFile = async (file: File) => {
  const formData = new FormData();
  formData.append('file', file);
  const accessToken = getAccessToken();

  return fetchData('/file-upload', {
    method: 'POST',
    body: formData,
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
  });
};
