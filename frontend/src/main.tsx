import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App.tsx';
import { worker } from './mocks/browser';

export const API_URL = import.meta.env.DEV ? '' : import.meta.env.VITE_API_URL;

if (import.meta.env.DEV) {
  worker.start({
    onUnhandledRequest: 'bypass',
  });
}

const appContext = {
  util: {} as Record<string, () => unknown>,
  control: {} as Record<string, () => void>,
};

export const AppContext = React.createContext(appContext);

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <AppContext.Provider value={appContext}>
      <App />
    </AppContext.Provider>
  </React.StrictMode>
);
