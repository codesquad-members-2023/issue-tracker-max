export function getTodayDate() {
  const now = new Date();
  const year = now.getFullYear();
  const month = now.getMonth() + 1;
  const day = now.getDate();
  const today = `${year}-${month < 10 ? '0' : ''}${month}-${
    day < 10 ? '0' : ''
  }${day}`;

  return today;
}
