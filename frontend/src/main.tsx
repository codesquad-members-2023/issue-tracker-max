import ReactDOM from "react-dom/client";
import App from "./App.tsx";
import { worker } from "./mocks/browser";
import "./styles/index.css";

if (process.env.NODE_ENV === "development") {
  worker.start({
    onUnhandledRequest(req, print) {
      const excludedRoutes = [
        "/BugBusters.jpeg",
        "chrome-extension://cofdbpoegempjloogbagkncekinflcnj/fonts/OpenSans_VariableFont_wdth_wght.ttf",
      ];

      const isExcluded = excludedRoutes.some((route) =>
        req.url.pathname.includes(route),
      );

      if (isExcluded) {
        return;
      }

      print.warning();
    },
  });
}

ReactDOM.createRoot(document.getElementById("root")!).render(<App />);
