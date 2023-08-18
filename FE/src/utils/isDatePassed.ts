export function isDatePassed(data: string) {
  const targetDate = new Date(data);
  const currentDate = new Date();
  return targetDate < currentDate;
}
