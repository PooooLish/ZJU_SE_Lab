<template>
  <div>
    <el-container>
      <el-header>Header</el-header>
      <el-container>
        <el-aside width="15%"><sidebar></sidebar></el-aside>
        <el-main><router-view></router-view></el-main>
      </el-container>
    </el-container>
  <!-- 该文件为工程的根组件 -->
  </div>
</template>

<script>

import Sidebar from './components/aside/Aside.vue';
export default {
  components: {
    sidebar: Sidebar
  }
}
</script>

<!-- basic style for the whole page -->
<style>
@import url("//unpkg.com/element-ui@2.15.14/lib/theme-chalk/index.css");
.el-header, .el-footer {
  background-color: #B3C0D1;
  color: #333;
  text-align: center;
  line-height: 60px;
}

.el-aside {
  background-color: #D3DCE6;
  color: #333;
  text-align: center;
  line-height: 200px;
}

.el-main {
  width: 100%;
  height: 100%;
  /*text-align: center;
   background-image: linear-gradient(120deg, #a1c4fd 0%, #d9f0fb 100%); 
  padding: 0px !important;
  line-height: 60px;*/
  align-items: stretch;
}

.el-container {
  height: 100vh;
}

</style>