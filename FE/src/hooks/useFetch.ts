import { useEffect, useState } from "react";

type FetchResponse<T> = {
  data: T | null;
  loading: boolean;
  error: Error | Response | null; // Change the error type
};

function useFetch<T>(
  url: string,
  method: "GET" | "POST" | "PUT" | "DELETE",
  body?: object,
): FetchResponse<T> {
  const [data, setData] = useState<T | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<Error | Response | null>(null); // Change the error type

  useEffect(() => {
    const fetchData = async () => {
      try {
        const options: RequestInit = {
          method,
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(body),
        };

        const response = await fetch(url, options);

        if (!response.ok) {
          throw new Error("데이터를 가져오는 데 실패했습니다.");
        }

        const jsonData = await response.json();
        setData(jsonData);
        setLoading(false);
      } catch (error) {
        if (error instanceof Error) {
          setError(error);
          setLoading(false);
        }
      }
    };

    fetchData();
  }, [url, method, body]);

  return { data, loading, error };
}

export default useFetch;
