<template>
  <el-container direction="vertical">

    <!-- query bar -->
    <div class="bg-container" id="head-container">
      <div id="selection1">
        <el-select v-model="period" placeholder="选择学期" @change="getCourses" 
          popper-class="popper-select" :popper-append-to-body="true">
          <el-option v-for="(item, index) in semester_list" 
            :key="index" 
            :label="`${item.year}${item.semester}`" 
            :value="`${item.year}/${item.semester}`"></el-option>
        </el-select>
      </div>
      <div id="selection2">
        <el-select v-model="course" placeholder="选择课程" popper-class="popper-select" :popper-append-to-body="true">
          <el-option v-for="course in course_list" :key="course" :label="course" :value="course"></el-option>
        </el-select>
      </div>
      <div id="onlyfailed">
        <el-checkbox v-model="only_failed">只显示未通过的学生</el-checkbox>
      </div>
      <div id="querybutton">
        <el-button @click="getScores()">查询</el-button>
      </div>
    </div>

    <!-- table of results -->
    <div class="bg-container" id="table-container">
      <el-table :data="filteredTableData" 
        ref="tbl" 
        :row-class-name="tableRowClassName">
        <el-table-column prop="student_id" label="学号" width="120px" align="center"></el-table-column>
        <el-table-column prop="ord_grade" width="120px" label="平时成绩" align="center"></el-table-column>
        <el-table-column prop="exam_grade" width="120px" label="考试成绩" align="center"></el-table-column>
        <el-table-column prop="grade" width="120px" label="总成绩" align="center"></el-table-column>
        <el-table-column prop="failed" width="120px" label="是否通过" align="center" :formatter="resolveFailed"></el-table-column>
        <el-table-column prop="delayed" width="120px" label="是否缓考" align="center" :formatter="resolveDelay"></el-table-column>
        <el-table-column prop="makeup" width="120px" label="是否补考" align="center" :formatter="resolveMakeup"></el-table-column>
        <el-table-column prop="remake" width="120px" label="是否重修" align="center" :formatter="resolveRemake"></el-table-column>
        <el-table-column label="操作" width="120px" fixed="right" align="center">
          <template slot-scope="scope">
            <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">
              编辑
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- edit dialog -->
    <el-dialog title="编辑成绩" :visible.sync="editDialogVisible" width="30%">
      <el-form :model="edits">
        <el-form-item label="平时成绩">
          <el-input v-model="edits.usual_score" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="考试成绩">
          <el-input v-model="edits.exam_score" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="总成绩">
          <el-input v-model="edits.total_score" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleSubmit()">确 定</el-button>
      </div>
    </el-dialog>
  </el-container>
</template>
  
<script>

export default {
  data() {
    return {
      period: undefined,          // selected year + semester
      course: undefined,          // selected course
      only_failed: false,         // only show failed students (checkbox)
      editDialogVisible: false,   // how to jump out
      edits: {                    // how to change the score of a student
        chosen_stu: 0,
        usual_score: 0,
        exam_score: 0,
        total_score: 0
      },
      // mock data
      semester_list: [
        {year: "2021", semester: "春夏"},
        {year: "2021", semester: "秋冬"},
        {year: "2022", semester: "春夏"}
      ],
      course_list: [
        "软件工程", "算法设计", "数据结构", "计算机网络", "数据库系统", "人工智能", "软件测试"
      ],
      tableData: [
        // TODO: match the backend
        {student_id: "3210101102", ord_grade: 90.5, exam_grade: 80, grade: 66, failed: false, delayed: false, makeup: false, remake: false},
        {student_id: "3210101103", ord_grade: 87, exam_grade: 77, grade: 64, failed: true, delayed: false, makeup: false, remake: false},
        {student_id: "3210101104", ord_grade: 92, exam_grade: 85, grade: 77, failed: false, delayed: false, makeup: false, remake: false},
        {student_id: "3210101105", ord_grade: 90, exam_grade: 88, grade: 78, failed: true, delayed: false, makeup: false, remake: false},
        {student_id: "3210101106", ord_grade: 85, exam_grade: 80, grade: 65, failed: false, delayed: false, makeup: false, remake: false},
        {student_id: "3210101107", ord_grade: 89, exam_grade: 91, grade: 80, failed: false, delayed: false, makeup: false, remake: false}
      ]
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

  computed: {
    // used to filter out all the failed students
    filteredTableData() {
      return this.tableData.filter((row) => {
        if (this.only_failed) {
          return row.failed;
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

    handleEdit(index, row) {
      this.editDialogVisible = true;
      this.edits.chosen_stu = row.id;
      this.edits.usual_score = row.ord_grade;
      this.edits.exam_score = row.exam_grade;
      this.edits.total_score = row.grade;
      console.log(index, row);
    },

    handleSubmit() {
      if (this.edits.exam_score < 0 || this.edits.usual_score < 0 || this.edits.total_score < 0 || 
          this.edits.usual_score > 100 || this.edits.exam_score > 100 || this.edits.total_score > 100) {
        this.$message.error("输入的成绩不合法");
        return;
      }
      this.editDialogVisible = false;
      this.$http({
        url: "",    // TODO: fill in the url
        method: "PUT",
        data: {
          chosen_stu: this.edits.chosen_stu,
          chosen_course: this.course,
          usual_score: this.edits.usual_score,
          exam_score: this.edits.exam_score
        }
      })
    },

    // split the year/semester string and get the corresponding courses
    getCourses(semester) {
      const period_array = this.period.split("/");
      console.log(period_array);
      this.$http.get({
        url: "",    // TODO: fill in the url
        method: "GET",
        params: {
          year: period_array[0],
          semester: period_array[1]
        }
      }).then(function(res) {
        this.course_list = res.data.data;   // TODO: to validate the data structure
      })
    },

    getScores() {
      if (this.period === undefined || this.course === undefined) {
        this.$message.error("请选择学年和学期和课程");
        return;
      }
      const period_array = this.period.split("/");
      console.log(period_array);
      this.$http({
        url: "",    // TODO: fill in the url
        method: "GET",
        params: {
          year: period_array[0],
          semester: period_array[1],
          course_name: this.course
        }
      }).then(function(res) {
        this.tableData = res.data.data;   // TODO: to validate the data structure
      })
    },

    // formatter functions
    resolveFailed(row, column) {
      if (row.failed) {
        return "否";
      } else {
        return "是";
      }
    },
    resolveDelay(row, column) {
      if (row.delayed) {
        return "是";
      } else {
        return "否";
      }
    },
    resolveMakeup(row, column) {
      if (row.makeup) {
        return "是";
      } else {
        return "否";
      }
    },
    resolveRemake(row, column) {
      if (row.remake) {
        return "是";
      } else {
        return "否";
      }
    }
  }
}
</script>
  
<style scoped src="./scores.css"></style>
<style scoped>
#head-container {
  height: 7%;
  width: 55%;
  position: absolute;
  top: 15%;
  left: 30%;
}

#table-container {
  height: 60%;
  width: 65%;
  position: absolute;
  top: 30%;
  left: 25%;
}

#selection1 {
  position: absolute;
  left: 5%;
  width: 20%;
}

#selection2 {
  position: absolute;
  left: 30%;
  width: 20%;
}

#onlyfailed {
  position: absolute;
  left: 55%;
}

#querybutton {
  position: absolute;
  left: 80%;
}
</style>