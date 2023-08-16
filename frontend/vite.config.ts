import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';
import svgr from 'vite-plugin-svgr';
// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react(), svgr()],
  server: {
    proxy: {
      '/api': {
        target:
          'http://ec2-3-39-194-173.ap-northeast-2.compute.amazonaws.com:8080',
        changeOrigin: true,
        secure: false,
      },
    },
  },
});
