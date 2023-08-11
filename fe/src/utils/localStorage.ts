export const getLocalStorageUserId = () => {
  const id = getLocalStorage('userId');

  if (!id) {
    throw new Error('사용자의 로컬스토리지에 userId 가 없습니다.');
  }

  return id;
};

export const getLocalStorageLoginId = () => {
  const id = getLocalStorage('loginId');

  if (!id) {
    throw new Error('사용자의 로컬스토리지에 loginId 가 없습니다.');
  }

  return id;
};

export const setLocalStorageUserId = (value: string) => {
  return setLocalStorage('userId', value);
};

const getLocalStorage = (key: string) => {
  return localStorage.getItem(key);
};

const setLocalStorage = (key: string, value: string) => {
  localStorage.setItem(key, value);
};
