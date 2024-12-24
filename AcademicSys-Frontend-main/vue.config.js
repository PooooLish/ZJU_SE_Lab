const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
})
module.exports = {
  devServer: {
    proxy: {
      '/api': {
        target: 'http://server-addr:port',    // 设置后端接口的访问地址
        changeOrigin: true,
        pathRewrite: {
          '^/api': ''                         // 将请求路径中的 '/api' 替换为空字符串
        }
      }
    }
  }
}
