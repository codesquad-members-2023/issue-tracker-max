export function decodeBase64(string: string) {
  const parts = string.split(".");
  const secondPartBase64 = parts[1];
  const decodedString = atob(secondPartBase64);

  return JSON.parse(decodedString);
}
