type Props = {
  subUrl: string;
  method?: string;
  header?: any;
  contentType?: string;
  body?: any;
};

export async function customFetch<T>({
  subUrl,
  method,
  header,
  contentType = 'application/json',
  body,
}: Props): Promise<T> {
  const baseUrl = import.meta.env.VITE_APP_BASE_URL;
  const authorization = 'Bearer ' + localStorage.getItem('accessToken');

  const url = new URL(subUrl, baseUrl);
  const options = {
    method: method,
    headers: {
      authorization: authorization,
      'Content-Type': contentType,
      ...header,
    },
    body: body,
  };

  try {
    const res = await fetch(url, options);
    const json = await res.json();

    return json;
  } catch (error) {
    throw error;
  }
}
