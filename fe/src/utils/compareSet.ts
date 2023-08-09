export const compareSet = (
  prev: Set<number>,
  current: Set<number>
): { addedElements: number[]; removedElements: number[] } => {
  const addedElements = [...current].filter((element) => !prev.has(element));
  const removedElements = [...prev].filter((element) => !current.has(element));

  return { addedElements, removedElements };
};
