<script>
import axios from "axios";
import {navy as response} from "mockjs";

export default {
  data(){
    return{
      searchInfoTeacher: ''
    }
  },
  methods: {
    goHome() {
      this.$router.push('/forum').catch(error => {
        if (error.name != 'NavigationDuplicated') {
          throw error;
        };}); // 假设使用 Vue Router 进行跳转
      console.log('Menu item clicked');
    },
    goActPost() {
      this.$router.push('/forum/like').catch(error => {
        if (error.name != 'NavigationDuplicated') {
          throw error;
        };}); // 假设使用 Vue Router 进行跳转
      console.log('Menu item clicked');
    },
    goDisPost() {
      this.$router.push('/forum/dislike').catch(error => {
        if (error.name != 'NavigationDuplicated') {
          throw error;
        };}); // 假设使用 Vue Router 进行跳转
      console.log('Menu item clicked');
    },
    goValuePost() {
      this.$router.push('/forum/favor').catch(error => {
        if (error.name != 'NavigationDuplicated') {
          throw error;
        };});  // 假设使用 Vue Router 进行跳转
      console.log('Menu item clicked');
    },
    goMyPost() {
      this.$router.push('/forum/pub').catch(error => {
        if (error.name != 'NavigationDuplicated') {
          throw error;
        };}); // 假设使用 Vue Router 进行跳转
      console.log('Menu item clicked');
    },
    goAi(){
      this.$router.push('/forum/ai'); // 假设使用 Vue Router 进行跳转
      console.log('Menu item clicked');
    },
    submitName() {
      try {
        const result = axios.post('http://localhost:8080/api/getInfo', {
          name: this.searchInfoTeacher
        });
        response.value = result.data; // 和后端统一
      } catch (error) {
        console.error('Error submitting name:', error);
        response.value = 'Error submitting name';
      }
    },
  }
}

</script>

<template>
  <el-menu
      :default-active="activeIndex2"
      class="el-menu-demo"
      mode="vertical"
      @select="handleSelect"
      background-color="rgba(139,179,239,0.06)"
  >
    <el-menu-item index="1" @click="goHome">消息中心</el-menu-item>
    <el-submenu index="2">
      <template slot="title">我的论坛</template>
      <el-menu-item index="2-1" @click="goActPost">我赞过的帖子</el-menu-item>
      <el-menu-item index="2-1" @click="goDisPost">我踩过的帖子</el-menu-item>
      <el-menu-item index="2-2" @click="goValuePost">我收藏的帖子</el-menu-item>
      <el-menu-item index="2-3" @click="goMyPost">我发表的帖子</el-menu-item>
    </el-submenu>
    <el-menu-item index="4" @click="goAi">论坛智能助手</el-menu-item>
  </el-menu>
</template>

<style scoped>
.header-class{
  height: 10px;
}
</style>