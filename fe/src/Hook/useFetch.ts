import { useEffect, useState } from 'react';

type Props = {
  subUrl: string;
  method?: string;
  header?: any;
  body?: any;
};

type Response<T> = {
  response: T | null;
  isLoading: boolean;
  errorMessage: Error | null;
};

export function useFetch<T>({
  subUrl,
  method,
  header,
  body,
}: Props): Response<T> {
  const [response, setResponse] = useState<T | null>(null);
  const [isLoading, setIsLoading] = useState(false);
  const [errorMessage, setErrorMessage] = useState<Error | null>(null);

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      const res = await fetch(url, options);
      const json = await res.json();

      setResponse(json);
    } catch (error) {
      // Memo: error가 Error 객체 형식이 아니면???
      if (error instanceof Error) {
        setErrorMessage(error);
      }

      console.error('Error fetching data:', error);
    } finally {
      setIsLoading(false);
    }
  };

  const baseUrl = import.meta.env.VITE_APP_BASE_URL;
  const authorization = 'Bearer ' + localStorage.getItem('accessToken');

  const url = new URL(subUrl, baseUrl);
  const options = {
    method: method,
    headers: {
      authorization: authorization,
      'Content-Type': 'application/json',
      ...header,
    },
    body: JSON.stringify(body),
  };

  return { response, isLoading, errorMessage };
}
