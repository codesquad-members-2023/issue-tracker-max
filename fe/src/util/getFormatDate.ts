export const getFormatDate = (dateString: string) => {
  const date = new Date(dateString);
  const formattedDate = `
  ${date.getFullYear()}. 
  ${String(date.getMonth() + 1).padStart(2, '0')}. 
  ${String(date.getDate()).padStart(2, '0')}
  `;

  return formattedDate;
};
