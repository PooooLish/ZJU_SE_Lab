<template>
  <el-container direction="vertical">

    <div class="bg-container" id="head-container">
      <div class="selection">
        <el-select v-model="period" placeholder="选择学期" popper-class="popper-select"
          transfer="true" :popper-append-to-body="true">
          <el-option v-for="(item, index) in semester_list" 
            :key="index" 
            :label="`${item.year}${item.semester}`" 
            :value="`${item.year}/${item.semester}`">
          </el-option>
        </el-select>
      </div>
      <div class="onlyfailed">
        <el-checkbox v-model="only_failed">只显示未通过课程</el-checkbox>
      </div>
      <div class="querybutton">
        <el-button icon="el-icon-search" @click="getScores()">查询</el-button>
      </div>
    </div>

    <div class="bg-container" id="table-container">
      <el-table :data="filteredScore" 
                max-height="500px"
                :row-class-name="tableRowClassName">
        <el-table-column prop="course_name" label="课程" align="center"></el-table-column>
        <el-table-column prop="category" label="类型" align="center"></el-table-column>
        <el-table-column prop="ord_grade" label="平时成绩" align="center"></el-table-column>
        <el-table-column prop="exam_grade" label="考试成绩" align="center"></el-table-column>
        <el-table-column prop="grade" label="总成绩" align="center"></el-table-column>
        <el-table-column porp="is_pass" label="是否通过" align="center" :formatter="resolvePassed"></el-table-column>
      </el-table>
    </div>

  </el-container>
</template>

<script>
export default {
  data() {
    return {
      period: undefined,  // 2021/春
      only_failed: false,
      // mock data
      semester_list: [
        {year: "2021", semester: "春夏"},
        {year: "2021", semester: "秋冬"},
        {year: "2022", semester: "春夏"}
      ],
      tableData: [
        {year: "2021", semester: "春夏", course_name: "软件工程", category: "专业核心课程", ord_grade: 90.5, exam_grade: 80, grade: 66, is_pass: false},
        {year: "2021", semester: "春夏", course_name: "算法设计", category: "专业核心课程", ord_grade: 87, exam_grade: 77, grade: 64, is_pass: true},
        {year: "2021", semester: "春夏", course_name: "数据结构", category: "专业核心课程", ord_grade: 92, exam_grade: 85, grade: 77, is_pass: false},
        {year: "2021", semester: "春夏", course_name: "计算机网络", category: "选修课", ord_grade: 90, exam_grade: 88, grade: 78, is_pass: true},
        {year: "2021", semester: "春夏", course_name: "数据库系统", category: "专业核心课程", ord_grade: 85, exam_grade: 80, grade: 65, is_pass: false},
        {year: "2021", semester: "春夏", course_name: "人工智能", category: "选修课", ord_grade: 89, exam_grade: 91, grade: 80, is_pass: false},
        {year: "2021", semester: "春夏", course_name: "软件测试", category: "选修课", ord_grade: 88, exam_grade: 90, grade: 78, is_pass: true},
        {year: "2021", semester: "春夏", course_name: "软件测试", category: "选修课", ord_grade: 88, exam_grade: 90, grade: 78, is_pass: true},
        {year: "2021", semester: "春夏", course_name: "软件测试", category: "选修课", ord_grade: 88, exam_grade: 90, grade: 78, is_pass: true},
        {year: "2021", semester: "春夏", course_name: "软件测试", category: "选修课", ord_grade: 88, exam_grade: 90, grade: 78, is_pass: true},
        {year: "2021", semester: "春夏", course_name: "软件测试", category: "选修课", ord_grade: 88, exam_grade: 90, grade: 78, is_pass: true},
      ]
    }
  },

  // get the legal semesters
  created() {
    this.$http({
      url: "",    // TODO: fill in the url
      method: "GET",
    }).then(function(res) {
      this.semester_list = res.data.data;   // TODO: to validate the data structure
    })
  },

  // filter the failed scores
  computed: {
    filteredScore() {
      return this.tableData.filter((row) => {
        if (this.only_failed) {
          return !row.is_pass;
        } else {
          return true;
        }
      })
    }
  },

  methods: {
    tableRowClassName({row, rowIndex}) {
      return row.failed ? 'failed-row' : 'success-row';
    },
    getScores() {
      if (this.period === undefined) {
        this.$message.error("请选择学年和学期");
        return;
      }
      const period_array = this.period.split("/");
      const year = period_array[0];
      const semester = period_array[1];
      console.log(year);
      console.log(semester);
      this.$http({
        url: "",    // TODO: fill in the url
        method: "GET",
        params: {
          year: year,
          semester: semester
        }
      }).then(function(res) {
        this.tableData = res.data.data;   // TODO: to validate the data structure
      })
    },
    resolvePassed(row, column) {
      if (row.is_pass) {
        return "是";
      }
      else {
        return "否";
      }
    }
  }
}
</script>

<style scoped src="./scores.css"></style>
<style scoped>
#head-container {
  height: 60px;
  width: 45%;
  position: absolute;
  top: 15%;
  left: 35%;
}
#table-container {
  height: 60%;
  width: 65%;
  position: absolute;
  top: 30%;
  left: 25%;
}
.selection {
  position: absolute;
  top: 15%;
  left: 15%;
  width: 20%;
}
.onlyfailed {
  position: absolute;
  top: 35%;
  left: 45%;
  width: 20%;
}
.querybutton {
  position: absolute;
  top: 15%;
  left: 70%;
  width: 20%;
}
::v-deep .el-main {
  background-image: linear-gradient(120deg, #a1c4fd 0%, #d9f0fb 100%);
}
</style>