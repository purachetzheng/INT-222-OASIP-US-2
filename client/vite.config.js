import { defineConfig, loadEnv } from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'path'
// https://vitejs.dev/config/

export default defineConfig(({ mode }) => {
  process.env = { ...process.env, ...loadEnv(mode, process.cwd()) }
  return defineConfig({
    plugins: [vue()],
    base: '/us2/',
    resolve: {
      alias: {
        '@': path.resolve(__dirname, './src'),
      },
    },
    server: {
      proxy: {
        '/api': {
          target: process.env.VITE_PROXY_URL,
          changeOrigin: true,
        },
      },
    },
  })
})
// export default defineConfig({
//   base: '/us2/',

//   resolve:{
//     alias:{
//       '@' : path.resolve(__dirname, './src')
//     },
//   },
//   plugins: [
//     vue(),
//   ],
//   server: {
//     proxy: {
//       '/api': {
//         // target: "http://localhost:8080",
//         target: process.env.VITE_BASE_URL,
//         changeOrigin: true
//       }
//     }
//   }
// })
