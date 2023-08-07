import ReactDOM from "react-dom/client";
import App from "./App.tsx";
import { worker } from "./mocks/browser";
import "./styles/index.css";

if (process.env.NODE_ENV === "development") {
  worker.start({
    onUnhandledRequest: "bypass",
  });
}

ReactDOM.createRoot(document.getElementById("root")!).render(<App />);
