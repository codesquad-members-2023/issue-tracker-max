const typo = {
  weight: {
    bold: 700,
    medium: 500,
  },
  size: {
    32: "32px",
    24: "24px",
    20: "20px",
    16: "16px",
    12: "12px",
  },
  lineHeight: {
    48: "48px",
    36: "36px",
    32: "32px",
    24: "24px",
    16: "16px",
  },
};

export const typoSystem = {
  font: {
    displayBold32: `${typo.weight.bold} ${typo.size[32]}/${typo.lineHeight[48]} Pretendard`,
    displayBold20: `${typo.weight.bold} ${typo.size[20]}/${typo.lineHeight[32]} Pretendard`,
    displayBold16: `${typo.weight.bold} ${typo.size[16]}/${typo.lineHeight[24]} Pretendard`,
    displayBold12: `${typo.weight.bold} ${typo.size[12]}/${typo.lineHeight[16]} Pretendard`,
    displayMedium20: `${typo.weight.medium} ${typo.size[20]}/${typo.lineHeight[32]} Pretendard`,
    displayMedium16: `${typo.weight.medium} ${typo.size[16]}/${typo.lineHeight[24]} Pretendard`,
    displayMedium12: `${typo.weight.medium} ${typo.size[12]}/${typo.lineHeight[16]} Pretendard`,
    selectedBold20: `${typo.weight.bold} ${typo.size[20]}/${typo.lineHeight[32]} Pretendard`,
    selectedBold16: `${typo.weight.bold} ${typo.size[16]}/${typo.lineHeight[24]} Pretendard`,
    selectedBold12: `${typo.weight.bold} ${typo.size[12]}/${typo.lineHeight[16]} Pretendard`,
    availableMedium20: `${typo.weight.medium} ${typo.size[20]}/${typo.lineHeight[32]} Pretendard`,
    availableMedium16: `${typo.weight.medium} ${typo.size[16]}/${typo.lineHeight[24]} Pretendard`,
    availableMedium12: `${typo.weight.medium} ${typo.size[12]}/${typo.lineHeight[16]} Pretendard`,
  },
};
