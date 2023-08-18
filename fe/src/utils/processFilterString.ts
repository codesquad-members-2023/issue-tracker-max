export const processFilterString = (input: string) => {
  const processedInput = input.replace('?query=', '');
  const parts = processedInput.split(' ');
  const validFilterKeys = [
    'status',
    'assignee',
    'author',
    'commentAuthor',
    'label',
    'milestone',
  ];
  const uniqueFilters: Record<string, string> = {};
  let keyword = '';

  for (const part of parts) {
    if (part.includes(':')) {
      const [key, value] = part.split(':');

      if (!key || !value) {
        continue;
      }

      if (validFilterKeys.includes(key)) {
        uniqueFilters[key] = value;
      } else {
        keyword += keyword ? ` ${part}` : part;
      }
    } else {
      keyword += keyword ? ` ${part}` : part;
    }
  }

  const filtersString = Object.entries(uniqueFilters)
    .map(([key, value]) => `${key}:${value}`)
    .join(' ');

  return `${filtersString} ${keyword}`;
};
