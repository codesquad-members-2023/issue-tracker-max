export default function parseParam(value: string | undefined): string {
  return value === undefined ? "" : value.replace(/&/g, " ");
}
