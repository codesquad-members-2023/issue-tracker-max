const USER_ID = 'user-id';
const LOGIN_ID = 'login-id';
const IMAGE_URL = 'image-url';

const getLocalStorage = (key: string) => {
  return localStorage.getItem(key);
};

const setLocalStorage = (key: string, value: string) => {
  localStorage.setItem(key, value);
};

export const getLocalStorageUserId = () => {
  const id = getLocalStorage(USER_ID);

  if (!id) {
    // throw new Error('사용자의 로컬스토리지에 userId 가 없습니다.');
    return;
  }

  return id;
};

export const getLocalStorageLoginId = () => {
  const id = getLocalStorage(LOGIN_ID);

  if (!id) {
    // throw new Error('사용자의 로컬스토리지에 loginId 가 없습니다.');
    return;
  }

  return id;
};

export const setLocalStorageUserId = (value: string) => {
  return setLocalStorage(USER_ID, value);
};

export const setLocalStorageLoginId = (value: string) => {
  return setLocalStorage(LOGIN_ID, value);
};

export const setLocalStorageImageUrl = (value: string) => {
  return setLocalStorage(IMAGE_URL, value);
};
