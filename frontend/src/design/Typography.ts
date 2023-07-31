import {} from 'styled-components';

const Typography = {
  $bold: {
    32: `
      font-size: 32px;
      font-weight: 700;
      font-style: normal;
      line-height: 48px;
    `,
    24: `
      font-size: 24px;
      font-weight: 700;
      font-style: normal;
      line-height: 36px;
    `,
    20: `
      font-size: 20px;
      font-weight: 700;
      font-style: normal;
      line-height: 32px;
    `,
    16: `
      font-size: 16px;
      font-weight: 700;
      font-style: normal;
      line-height: 24px;
    `,
    12: `
      font-size: 12px;
      font-weight: 700;
      font-style: normal;
      line-height: 16px;
    `,
  },
  $medium: {
    32: `
      font-size: 32px;
      font-weight: 500;
      font-style: normal;
      line-height: 48px;
    `,
    24: `
      font-size: 24px;
      font-weight: 500;
      font-style: normal;
      line-height: 36px;
    `,
    20: `
      font-size: 20px;
      font-weight: 500;
      font-style: normal;
      line-height: 32px;
    `,
    16: `
      font-size: 16px;
      font-weight: 500;
      font-style: normal;
      line-height: 24px;
    `,
    12: `
      font-size: 12px;
      font-weight: 500;
      font-style: normal;
      line-height: 16px;
    `,
  },
} as const;

export default Typography;
