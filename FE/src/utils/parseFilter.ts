export default function parseFilter(value: string | undefined): string {
  return value === undefined ? "" : `?${value}`.replace(/ /g, "&");
}
