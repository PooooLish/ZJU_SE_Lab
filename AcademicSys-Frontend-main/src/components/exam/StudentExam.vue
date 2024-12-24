<template>
  <el-container direction="vertical">
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
        <el-button @click="search()">查询考试</el-button>
      </div>
    </div>

  <div class="bg-container" id="table-container">
    <el-table
      :data="tableData"
      :row-class-name="tableRowClassName">
      <el-table-column
        prop="year"
        label="学年"
        width="180">
      </el-table-column>
      <el-table-column
        prop="semester"
        label="学期"
        width="180">
      </el-table-column>
      <el-table-column
        prop="course_name"
        label="课程名">
      </el-table-column>
      <el-table-column
        prop="time"
        label="考试时间">
      </el-table-column>
      <el-table-column
        prop="place"
        label="考试地点">
      </el-table-column>
      <el-table-column
        prop="seat"
        label="座位号">
      </el-table-column>
    </el-table>
  </div>
  
  </el-container>
</template>

<script>
  export default {
    
    data() {
      return {
        period: undefined,
        semester_list: [
          {year: "2021", semester: "春夏"},
          {year: "2021", semester: "秋冬"},
          {year: "2022", semester: "春夏"}
        ],
        tableData: [{
          year: '2020-2021', semester: '春夏', course_name: '计算机组成', time: '6月18日10:00-12:00', place:'教七104', seat:'45' 
        }, {
          year: '2020-2021', semester: '春夏', course_name: 'ADS', time: '6月18日14:00-16:00', place:'教七202', seat:'23'
        }]
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
      tableRowClassName({row, rowIndex}) {
        return row.failed ? 'failed-row' : 'success-row';
      },
      search() {
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
    },
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