export const getLocalStorageUserId = () => {
  const id = getLocalStorage('userId');

  if (!id) {
    // throw new Error('사용자의 로컬스토리지에 userId 가 없습니다.');
    return;
  }

  return JSON.parse(id);
};

export const getLocalStorageLoginId = () => {
  const id = getLocalStorage('loginId');

  if (!id) {
    // throw new Error('사용자의 로컬스토리지에 loginId 가 없습니다.');
    return;
  }

  return id;
};

export const getAccessToken = () => {
  const token = getLocalStorage('accessToken');

  if (!token) {
    // throw new Error('사용자의 로컬스토리지에 token이 없습니다.');
    console.log('accessToken이 로컬스토리지에 없습니다.');

    return '';
  }

  return token;
};

export const getRefreshToken = () => {
  const token = getLocalStorage('refreshToken');

  if (!token) {
    // throw new Error('사용자의 로컬스토리지에 token이 없습니다.');
    console.log('refreshToken 로컬스토리지에 없습니다.');

    return '';
  }

  return token;
};

export const setLocalStorageUserId = (value: string) => {
  return setLocalStorage('userId', value);
};

export const setLocalStorageLoginId = (value: string) => {
  return setLocalStorage('loginId', value);
};

export const setLocalStorageImage = (value: string) => {
  return setLocalStorage('image', value);
};

export const getLocalStorageImage = () => {
  return getLocalStorage('image');
};

export const setLocalStorageAccessToken = (value: string) => {
  return setLocalStorage('accessToken', value);
};

export const setLocalStorageRefreshToken = (value: string) => {
  return setLocalStorage('refreshToken', value);
};

const getLocalStorage = (key: string) => {
  return localStorage.getItem(key);
};

const setLocalStorage = (key: string, value: string) => {
  localStorage.setItem(key, value);
};
