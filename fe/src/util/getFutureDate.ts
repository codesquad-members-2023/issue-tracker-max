export function getFutureDate(years: number) {
  const today = new Date();
  const futureDate = new Date(
    today.getFullYear() + years,
    today.getMonth(),
    today.getDate()
  );
  const year = futureDate.getFullYear();
  const month = futureDate.getMonth() + 1;
  const day = futureDate.getDate();
  const future = `${year}-${month < 10 ? '0' : ''}${month}-${
    day < 10 ? '0' : ''
  }${day}`;

  return future;
}
