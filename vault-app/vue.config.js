const { defineConfig } = require('@vue/cli-service')
const fs = require('fs');
const path = require('path');
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    https: {
      key: fs.readFileSync(path.resolve(__dirname, 'cert/server.key')),
      cert: fs.readFileSync(path.resolve(__dirname, 'cert/server.crt')),
    },
  },
})




