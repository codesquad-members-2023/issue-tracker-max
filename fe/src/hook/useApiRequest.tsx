import { useState, useCallback } from "react";

type HttpMethod = "GET" | "POST" | "PUT" | "PATCH" | "DELETE";
type RequestBody = Record<string, unknown> | null;

interface FetchState<T> {
  data?: T;
  loading: boolean;
  error?: string;
}

export function useFetch<T>(): {
  data?: T;
  loading: boolean;
  error?: string;
  makeRequest: (
    url: string,
    method: HttpMethod,
    body?: RequestBody,
  ) => Promise<T | undefined>;
} {
  const [state, setState] = useState<FetchState<T>>({
    loading: false,
  });

  const makeRequest = useCallback(
    async (url: string, method: HttpMethod, body?: RequestBody) => {
      setState({ loading: true });

      const headers = new Headers();
      headers.append("Content-Type", "application/json");

      try {
        const response = await fetch(url, {
          method,
          headers,
          body: body ? JSON.stringify(body) : null,
        });

        if (!response.ok) {
          let errorData;
          const contentType = response.headers.get("content-type");
          if (contentType && contentType.indexOf("application/json") !== -1) {
            errorData = await response.json();
            throw {
              data: errorData,
            };
          } else {
            throw new Error(
              `Network response was not ok: ${response.statusText}`,
            );
          }
        }

        const data: T = await response.json();
        setState({ data, loading: false });

        return data;
      } catch (error) {
        setState({
          loading: false,
          error: error instanceof Error ? error.message : "Unknown error",
        });
        // return undefined;
        throw error;
      }
    },
    [],
  );

  return { ...state, makeRequest };
}
