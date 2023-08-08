export const generateEncodedQuery = (queryKey: string, queryValue: string) => {
  const filteredQuery =
    `${queryKey}:${queryValue} ` + getFilteredQuery(queryKey);
  const trimmedQuery = removeDuplicateSpaces(filteredQuery);

  return '?query=' + encodeURIComponent(trimmedQuery);
};

const getFilteredQuery = (query: string) => {
  const pattern = new RegExp(`${query}:[^\\s]+`, 'g');

  return decodeURIComponent(location.search)
    .replace('?query=', '')
    .replace(pattern, '');
};

const removeDuplicateSpaces = (str: string) => {
  return str.replace(/\s+/g, ' ').trim();
};
