import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";
import svgr from "vite-plugin-svgr";
import { resolve } from "path";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react(), svgr()],
  resolve: {
    alias: {
      assets: resolve(__dirname, "./src/assets"),
      components: resolve(__dirname, "./src/components"),
      pages: resolve(__dirname, "./src/pages"),
      styles: resolve(__dirname, "./src/styles"),
      contexts: resolve(__dirname, "./src/contexts"),
      constants: resolve(__dirname, "./src/constants"),
    },
  },
  server: {
    proxy: {
      // "/api": {
      //   target: "http://localhost:8080",
      //   changeOrigin: true,
      //   rewrite: (path) => path.replace(/^\/api/, ""),
      // },
      "/profile": {
        target: "https://source.boringavatars.com/beam/20",
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/profile/, ""),
      },
    },
  },
});
