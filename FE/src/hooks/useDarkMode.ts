import { useState, useEffect } from "react";

export type Theme = "light" | "dark";

export const useDarkMode = (): [Theme, () => void] => {
  const [theme, setTheme] = useState<Theme>("light");

  const toggleTheme = (): void => {
    theme === "dark" ? setTheme("light") : setTheme("dark");
  };

  useEffect(() => {
    const storedTheme = localStorage.getItem("theme");
    if (storedTheme && (storedTheme === "light" || storedTheme === "dark")) {
      setTheme(storedTheme);
    } else {
      const isDarkMode =
        window.matchMedia &&
        window.matchMedia("(prefers-color-scheme: dark)").matches;
      const userTheme: Theme = isDarkMode ? "dark" : "light";
      setTheme(userTheme);
    }
  }, []);

  useEffect(() => {
    localStorage.setItem("theme", theme);
  }, [theme]);

  return [theme, toggleTheme];
};
