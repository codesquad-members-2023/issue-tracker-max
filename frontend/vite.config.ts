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
        watch: {
            usePolling: true, // 폴링은 주기적으로 파일 시스템을 체크하여 변경 사항을 감지하는 방식
        },
    }
});
