export function calculateTime(createdAt: string): string {
  const createdTime = new Date(Date.parse(createdAt));
  const currentTime = new Date();
  const timeDifference = currentTime.getTime() - createdTime.getTime();
  const minutes = Math.floor(timeDifference / (1000 * 60));

  if (minutes < 1) {
    return "방금 전";
  } else if (minutes < 60) {
    return `${minutes}분 전`;
  } else if (minutes < 1440) {
    const hours = Math.floor(minutes / 60);
    return `${hours}시간 전`;
  } else {
    const days = Math.floor(minutes / 1440);
    return `${days}일 전`;
  }
}
