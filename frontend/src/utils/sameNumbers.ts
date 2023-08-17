export const sameNumbers = (
  numbers: number[],
  comparedNumbers: number[],
) => {
  if (numbers.length !== comparedNumbers.length) {
    return false;
  }

  return numbers.every((id) => comparedNumbers.includes(id));
};