import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App.tsx';
// import { worker } from './mocks/browser.ts';
import { BrowserRouter } from 'react-router-dom';
import { AuthProvider } from './context/AuthProvider.tsx';

// if (import.meta.env.DEV) {
//   worker.start({
//     onUnhandledRequest: 'bypass',
//   });
// }

const appContext = {
  util: {} as Record<string, () => unknown>,
  control: {} as Record<string, () => void>,
};

export const AppContext = React.createContext(appContext);

ReactDOM.createRoot(document.getElementById('root')!).render(
  <BrowserRouter>
    <AppContext.Provider value={appContext}>
      <AuthProvider>
        <App />
      </AuthProvider>
    </AppContext.Provider>
  </BrowserRouter>
);
