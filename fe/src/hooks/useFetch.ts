import { AxiosError, AxiosResponse } from "axios";
import { useCallback, useEffect, useState } from "react";

export default function useFetch<T>(fetchFn: () => Promise<AxiosResponse<T>>): {
  data: T | null;
  isLoading: boolean;
  errorMessage: string;
  reFetch: () => void;
} {
  const [data, setData] = useState<T | null>(null);
  const [isLoading, setIsLoading] = useState(false);
  const [errorMessage, setErrorMessage] = useState<string>("");

  const fetchData = useCallback(async () => {
    setIsLoading(true);

    try {
      const res = await fetchFn();

      if (res.status === 200) {
        setData(res.data);
        return;
      }

      throw Error((res.data as AxiosError).message);
    } catch (error) {
      setErrorMessage(error as string);
    } finally {
      setIsLoading(false);
    }
  }, [fetchFn]);

  useEffect(() => {
    fetchData();
  }, [fetchData]);

  const reFetch = () => {
    fetchData();
  };

  return {
    data,
    isLoading,
    errorMessage,
    reFetch,
  };
}
