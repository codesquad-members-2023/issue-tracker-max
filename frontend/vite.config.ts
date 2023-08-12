import react from "@vitejs/plugin-react";
import {defineConfig} from "vite";
import svgr from "vite-plugin-svgr";

// https://vitejs.dev/config/
export default defineConfig({
    plugins: [react(), svgr()],
    server: {
        proxy: {
            "/api": {
                target: "http://3.36.120.124:8080",
                changeOrigin: true,
                secure: false,
            }
        },
        host: true, // needed for the Docker Container port mapping to work
        port: 5173, // you can replace this port with any port
    },
});
