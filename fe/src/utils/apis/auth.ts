import { fetchData } from '@utils/api';

export const registerUser = (id: string, password: string) => {
  const path = 'users';
  const options = {
    body: JSON.stringify({
      loginId: id,
      password,
    }),
  };

  fetchData(path, options);
};
