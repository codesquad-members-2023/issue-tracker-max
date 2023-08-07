import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App.tsx';
import { worker } from './mocks/browser';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { AuthProvider } from './context/AuthProvider.tsx';

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
    <BrowserRouter>
      <AppContext.Provider value={appContext}>
        <AuthProvider>
          <Routes>
            <Route path="/*" element={<App />} />
          </Routes>
        </AuthProvider>
      </AppContext.Provider>
    </BrowserRouter>
  </React.StrictMode>
);
