import { ReactNode, createContext, useEffect, useState } from 'react';
import { User } from '../../type/User.type';
import { Theme, ThemeProvider, css } from '@emotion/react';
import { themes } from '../../styles/styles';

type UserProviderProps = {
  children: ReactNode;
};

export const UserContext = createContext<{
  user?: User;
  setUser?: React.Dispatch<React.SetStateAction<User>>;
  darkMode?: boolean;
  onToggleTheme?: () => void;
} | null>(null);

export default function UserProvider({ children }: UserProviderProps) {
  const [user, setUser] = useState<User>(() => {
    return {
      accessToken: localStorage.getItem('accessToken') || '',
      refreshToken: localStorage.getItem('refreshToken') || '',
      member: {
        id: Number(localStorage.getItem('id')) || 0,
        name: localStorage.getItem('name') || '',
        imageUrl: localStorage.getItem('imageUrl') || '',
      },
    };
  });
  const [darkMode, setDarkMode] = useState(() => {
    const storedDarkMode = localStorage.getItem('darkMode');
    return storedDarkMode ? JSON.parse(storedDarkMode) : false;
  });

  useEffect(() => {
    setUser({
      accessToken: localStorage.getItem('accessToken') || '',
      refreshToken: localStorage.getItem('refreshToken') || '',
      member: {
        id: Number(localStorage.getItem('id')) || 0,
        name: localStorage.getItem('name') || '',
        imageUrl: localStorage.getItem('imageUrl') || '',
      },
    });
  }, []);

  useEffect(() => {
    localStorage.setItem('darkMode', String(darkMode));
  }, [darkMode]);

  const onToggleTheme = () => {
    setDarkMode((prevMode: boolean) => !prevMode);
  };

  const theme = darkMode ? themes.dark : themes.light;

  return (
    <UserContext.Provider value={{ user, setUser, darkMode, onToggleTheme }}>
      <ThemeProvider theme={theme}>
        <div css={body(theme)}>{children}</div>
      </ThemeProvider>
    </UserContext.Provider>
  );
}

const body = (theme: Theme) => css`
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 100%;
  flex-grow: 1;
  overflow: auto;

  background-color: ${theme.neutral.surfaceDefault};

  & svg path {
    stroke: ${theme.neutral.textDefault};
  }
`;
