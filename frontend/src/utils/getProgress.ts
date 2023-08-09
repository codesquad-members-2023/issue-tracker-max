export const getProgress = (done: number, todo: number) => {
  const total = done + todo;

  if (total === 0) {
    return 0;
  }

  return Math.floor((done / total) * 100);
};
