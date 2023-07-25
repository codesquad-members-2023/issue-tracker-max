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
    displayBold32: `${typo.weight.bold} ${typo.size[32]}/${typo.lineHeight[48]} Arial, sans-serif`,
    displayBold20: `${typo.weight.bold} ${typo.size[20]}/${typo.lineHeight[32]} Arial, sans-serif`,
    displayBold16: `${typo.weight.bold} ${typo.size[16]}/${typo.lineHeight[24]} Arial, sans-serif`,
    displayBold12: `${typo.weight.bold} ${typo.size[12]}/${typo.lineHeight[16]} Arial, sans-serif`,
    displayMedium20: `${typo.weight.medium} ${typo.size[20]}/${typo.lineHeight[32]} Arial, sans-serif`,
    displayMedium16: `${typo.weight.medium} ${typo.size[16]}/${typo.lineHeight[24]} Arial, sans-serif`,
    displayMedium12: `${typo.weight.medium} ${typo.size[12]}/${typo.lineHeight[16]} Arial, sans-serif`,
    selectedBold20: `${typo.weight.bold} ${typo.size[20]}/${typo.lineHeight[32]} Arial, sans-serif`,
    selectedBold16: `${typo.weight.bold} ${typo.size[16]}/${typo.lineHeight[24]} Arial, sans-serif`,
    selectedBold12: `${typo.weight.bold} ${typo.size[12]}/${typo.lineHeight[16]} Arial, sans-serif`,
    availableMedium20: `${typo.weight.medium} ${typo.size[20]}/${typo.lineHeight[32]} Arial, sans-serif`,
    availableMedium16: `${typo.weight.medium} ${typo.size[16]}/${typo.lineHeight[24]} Arial, sans-serif`,
  },
};
