import { AxiosError, AxiosResponse } from "axios";
import { Dispatch, SetStateAction, useEffect, useState } from "react";

export default function useFetch<T>(fetchFn: () => Promise<AxiosResponse<T>>): {
  data: T | null;
  isLoading: boolean;
  errorMessage: string;
  setData: Dispatch<SetStateAction<T | null>>;
} {
  const [data, setData] = useState<T | null>(null);
  const [isLoading, setIsLoading] = useState(false);
  const [errorMessage, setErrorMessage] = useState<string>("");

  useEffect(() => {
    const fetchData = async () => {
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
    };

    fetchData();
  }, [fetchFn]);

  return { data, isLoading, errorMessage, setData };
}
