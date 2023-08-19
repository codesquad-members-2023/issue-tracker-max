export function getTimeLine(timestamp: Date) {
  const now = new Date();
  const pastDate = new Date(timestamp);
  const timeDifference = now.getTime() - pastDate.getTime();

  const minute = 60 * 1000;
  const hour = 60 * minute;
  const day = 24 * hour;
  const month = 30 * day; // Notice: 1년에 최대 5~6일 차이 발생 가능

  switch (true) {
    case timeDifference < minute:
      return `${Math.floor(timeDifference / 1000)}초 전`;
    case timeDifference < hour:
      return `${Math.floor(timeDifference / minute)}분 전`;
    case timeDifference < day:
      return `${Math.floor(timeDifference / hour)}시간 전`;
    case timeDifference < month:
      return `${Math.floor(timeDifference / day)}일 전`;
    default:
      return `${Math.floor(timeDifference / month)}개월 전`;
  }
}
