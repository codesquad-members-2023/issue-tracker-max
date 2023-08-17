export const validateId = (loginId: string) => {
  return (loginId.length > 0 && loginId.length < 6) || loginId.length > 17;
};

export const validatePassword = (password: string) => {
  return (password.length > 0 && password.length < 6) || password.length > 12;
};
