<template>
  <el-container direction="vertical">
    
    <!-- 下拉选择界面 -->
    <div class="bg-container" id="head-container">
      <div class="selection">
        <el-select v-model="period" placeholder="选择学期" popper-class="popper-select"
          transfer="true" :popper-append-to-body="true" clearable>
          <el-option v-for="(item, index) in semester_list" 
            :key="index" 
            :label="`${item.year}${item.semester}`" 
            :value="`${item.year}/${item.semester}`">
          </el-option>
        </el-select>
      </div>
      <div class="querybutton">
        <el-button icon="el-icon-search" @click="handleClick()">查询</el-button>
      </div>
    </div>

    <!-- 表格部分 -->
    <div class="bg-container" id="table-container">
      <el-table :data="tableData" max-height="500px">
        <el-table-column prop="year" label="学年" align="center"></el-table-column>
        <el-table-column prop="term" label="学期" align="center"></el-table-column>
        <el-table-column prop="course" label="课程" align="center"></el-table-column>
        <el-table-column prop="time" label="考试时间" align="center"></el-table-column>
        <el-table-column prop="location" label="考试地点" align="center"></el-table-column>
      </el-table>
    </div>
  </el-container>
</template>


<script>
export default {
  data() {
    return {
      // 假初始数据在这，你需要添加实际数据或调用API获取数据
      period: undefined,
      semester_list: [
        {year: "2021", semester: "春夏"},
        {year: "2021", semester: "秋冬"},
        {year: "2022", semester: "春夏"}
      ],
      tableData: [
        {
          year: '2022-2023',
          term: '春夏学期',
          course: '数据结构',
          time: '2022年6月22日',
          location: '北教103'
        },
        {
          year: '2023-2024',
          term: '秋冬学期',
          course: '计算机组成',
          time: '2024年1月21日',
          location: '西教303'
        },
      ],
    }
  },

  created() {
    this.$http({
      url: "",    // TODO: fill in the url
      method: "GET",
    }).then(function(res) {
      this.semester_list = res.data.data;   // TODO: to validate the data structure
    })
  },

  methods: {
    handleClick() {
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
    }
  }
}
</script>

<style scoped src="./exam.css"></style>
<style scoped>
#head-container { 
  height: 60px;
  width: 35%;
  position: absolute;
  top: 15%;
  left: 40%;
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
  left: 15%;
  width: 40%;
}
.querybutton {
  position: absolute;
  left: 70%;
  width: 20%;
}
</style>