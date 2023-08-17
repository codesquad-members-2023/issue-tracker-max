export function getKoreanTime(dateString: string) {
  const time = new Date(dateString);
  const koreanTime = new Date(time.getTime() + 9 * 60 * 60 * 1000);

  return koreanTime;
}
