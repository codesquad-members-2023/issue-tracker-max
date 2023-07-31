import {} from 'styled-components';

const fontFamily = {
  Pretendard:
    '"Pretendard Variable", Pretendard, -apple-system, BlinkMacSystemFont, system-ui, Roboto, "Helvetica Neue", "Segoe UI", "Apple SD Gothic Neo", "Noto Sans KR", "Malgun Gothic", "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol", sans-serif;',
} as const;

const Typography = {
  $bold: {
    32: `
      font-family: ${fontFamily.Pretendard};
      font-size: 32px;
      font-weight: 700;
      font-style: normal;
      line-height: 48px;
    `,
    24: `
    font-family: ${fontFamily.Pretendard};
      font-size: 24px;
      font-weight: 700;
      font-style: normal;
      line-height: 36px;
    `,
    20: `
      font-family: ${fontFamily.Pretendard};
      font-size: 20px;
      font-weight: 700;
      font-style: normal;
      line-height: 32px;
    `,
    16: `
      font-family: ${fontFamily.Pretendard};
      font-size: 16px;
      font-weight: 700;
      font-style: normal;
      line-height: 24px;
    `,
    12: `
      font-family: ${fontFamily.Pretendard};
      font-size: 12px;
      font-weight: 700;
      font-style: normal;
      line-height: 16px;
    `,
  },
  $medium: {
    32: `
      font-family: ${fontFamily.Pretendard};
      font-size: 32px;
      font-weight: 500;
      font-style: normal;
      line-height: 48px;
    `,
    24: `
      font-family: ${fontFamily.Pretendard};
      font-size: 24px;
      font-weight: 500;
      font-style: normal;
      line-height: 36px;
    `,
    20: `
      font-family: ${fontFamily.Pretendard};
      font-size: 20px;
      font-weight: 500;
      font-style: normal;
      line-height: 32px;
    `,
    16: `
      font-family: ${fontFamily.Pretendard};
      font-size: 16px;
      font-weight: 500;
      font-style: normal;
      line-height: 24px;
    `,
    12: `
      font-family: ${fontFamily.Pretendard};
      font-size: 12px;
      font-weight: 500;
      font-style: normal;
      line-height: 16px;
    `,
  },
} as const;

export default Typography;
