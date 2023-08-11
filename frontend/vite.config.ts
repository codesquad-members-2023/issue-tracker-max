import react from "@vitejs/plugin-react";
import {defineConfig} from "vite";
import svgr from "vite-plugin-svgr";

// https://vitejs.dev/config/
export default defineConfig({
    plugins: [react(), svgr()],
    server: {
        host: true, // needed for the Docker Container port mapping to work
        port: 5173, // you can replace this port with any port
    },
});
