const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  // 配置服务器,添加代理选项
  // devServer :开发 服务器
  devServer: {
    //proxy: 代理
    proxy: {
      // 必须重启服务器, 配置才能生效
      // 如果请求地址是 /douyu 开头的, 则被当前代理处理
      '/backend': {
        // target: 由服务器帮助到此域名中请求数据 -- 代理
        target: 'http://localhost:8081',
        changeOrigin: true, //代表 域名不同,需要启动代理模式
        // 路径重写: 真正发送的请求地址中, 要去掉 /douyu 开头
        pathRewrite: {
          // ^ :正则中的 字符串开头 的意思
          '^/backend': '',

        },
      },

    },
  },
})



