<template>
  <div style="width: 100%">
    <el-menu
      style="width: 100%; min-height: calc(100vh - 50px); overflow-x: hidden; height: 100%" 
      unique-opened router>

      <el-submenu index="1">
        <template slot="title">成绩与排名管理</template>
        <el-menu-item index="/student-score">
          学生成绩查询
        </el-menu-item>
        <el-menu-item index="/teacher-score">
          教师成绩查询
        </el-menu-item>
        <el-menu-item index="/rank">
          排名查询
        </el-menu-item>
      </el-submenu>

      <el-submenu index="2">
        <template slot="title">考试管理</template>
        <el-menu-item index="/student-exam">
          学生课程查询
        </el-menu-item>
        <el-menu-item index="/teacher-exam">
          教师课程查询
        </el-menu-item>
      </el-submenu>

      <el-submenu index="3">
        <template slot="title">课程与课本管理</template>
        <el-menu-item index="/courseinfo">
        <template #title>课程信息管理</template>
        </el-menu-item>
        <el-menu-item index="/bookinfo">
        <template #title>教材信息管理</template>
        </el-menu-item>
        <el-menu-item index="/getinfo">
        <template #title>课程课本查询</template>
        </el-menu-item>
      </el-submenu>
      <el-submenu index="4">
        <template slot="title">学生选课</template>
        <el-menu-item index="/AllCoursesComponent">
        <template #title>所有课程</template>
        </el-menu-item>
        <el-menu-item index="/BySelectionComponent">
        <template #title>补选</template>
        </el-menu-item>
        <el-menu-item index="/SearchComponent">
        <template #title>搜索</template>
        </el-menu-item>
        <el-menu-item index="/ShowComponent">
        <template #title>课表展示</template>
        </el-menu-item>
        <el-menu-item index="/teacherbyelection">
        <template #title>教师补选处理</template>
        </el-menu-item>
        <el-menu-item index="/teacherselectset">
        <template #title>教师选课设置</template>
        </el-menu-item>
      </el-submenu>

      <el-submenu index="5">
        <template slot="title">问卷评论</template>
        <el-menu-item index="/admin-que">
        <template #title>问卷评论管理</template>
        </el-menu-item>
        <el-menu-item index="/teacher-que">
        <template #title>教师问卷评论查询</template>
        </el-menu-item>
        <el-menu-item index="/student-que">
        <template #title>学生问卷评论提交</template>
        </el-menu-item>
      </el-submenu>

      <el-submenu index="6">
        <template slot="title">论坛</template>
<!--        <el-menu-item index="/forum/login">-->
<!--          <template #title>欢迎来到论坛</template>-->
<!--        </el-menu-item>-->
        <el-menu-item index="/forum">
          <template #title>学生入口</template>
        </el-menu-item>
        <el-menu-item index="/forum/manager">
          <template #title>管理员入口</template>
        </el-menu-item>
      </el-submenu>

    </el-menu>
  </div>
</template>
<script>
export default {
  name: "Aside",
  data() {
    return {
      user: {},
      path: this.$route.path,
    };
  }
};
</script>

<style scoped>
@import url("//unpkg.com/element-ui@2.15.14/lib/theme-chalk/index.css");
</style>