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
});
