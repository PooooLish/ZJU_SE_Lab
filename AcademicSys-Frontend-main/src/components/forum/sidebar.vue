<script>
import axios from 'axios';
import {navy as response} from "mockjs";
  export default {
    setup() {
      const submitName = async () => {
        try {
          const result = await axios.post('http://localhost:8080/api/submitName', {
            name: name.value
          });
          response.value = result.data; // Assuming the response data is in result.data
        } catch (error) {
          console.error('Error submitting name:', error);
          response.value = 'Error submitting name';
        }
      };
    },
    data() {
      return {
        searchInfoTeacher: ''
      }
    },
    methods: {
      handleOpen(key, keyPath) {
        console.log(key, keyPath);
      },
      handleClose(key, keyPath) {
        console.log(key, keyPath);
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
      submitInfo(info) {
        try {
          const result = axios.post('http://localhost:8080/api/getInfo', {
            name: info
          });
          response.value = result.data; // 和后端统一
        } catch (error) {
          console.error('Error submitting name:', error);
          response.value = 'Error submitting name';
        }
      }
    }
  }
</script>

<template>
  <div class="sidebar">
    <el-menu
        default-active="2"
        class="side-menu"
        @open="handleOpen"
        @close="handleClose"
        background-color="#000"
        text-color="#fff"
        active-text-color="#ffd04b"
    >
      <el-menu-item style="display: flex; align-items: center;">
        <el-input v-model="searchInfoTeacher" style="margin-right: 10px;" placeholder="请输入课程或名称～"></el-input>
        <el-button type="primary" size="mini" @click="submitName"><i class="el-icon-search" style="color: white;"></i></el-button>
      </el-menu-item>
<!--      <el-submenu index="1">-->
<!--        <template slot="title">-->
<!--          <i class="el-icon-location"></i>-->
<!--          <span>所在院系</span>-->
<!--        </template>-->
<!--        <el-menu-item-group>-->
<!--          <template slot="title">工学部</template>-->
<!--          <el-menu-item index="1-1" >计算机科学与技术</el-menu-item>-->
<!--          <el-menu-item index="1-2" >电气工程与自动化</el-menu-item>-->
<!--        </el-menu-item-group>-->
<!--        <el-menu-item-group title="理学部">-->
<!--          <el-menu-item index="1-3">物理</el-menu-item>-->
<!--        </el-menu-item-group>-->
<!--      </el-submenu>-->
<!--      <el-submenu index="2">-->
<!--        <template slot="title">-->
<!--          <i class="el-icon-menu"></i>-->
<!--          <span slot="title">课程名称</span>-->
<!--        </template>-->
<!--        <el-menu-item-group>-->
<!--          <el-menu-item index="2-1">本科</el-menu-item>-->
<!--          <el-menu-item index="2-2">研究生</el-menu-item>-->
<!--        </el-menu-item-group>-->
<!--      </el-submenu>-->
<!--      <el-menu-item index="4">-->
<!--        <i class="el-icon-setting"></i>-->
<!--        <span slot="title">待定</span>-->
<!--      </el-menu-item>-->
    </el-menu>
  </div>
</template>

<style scoped>
.sidebar {
  flex: 0 0 200px; /* 设置侧边栏宽度为固定值，不随内容而变化 */
  background-color: #f0f2f5;
}
.side-menu {
  background-color: #0f2d4b;
  text-color: #fff;
  active-text-color: #ffd04b;
}
</style>