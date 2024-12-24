<template>
  <div>
    <el-container>
      <el-main>
        <div class = "查询框" style="margin-bottom: 10px;">
          <el-input
                placeholder="请输入 CourseId 查询该课程统计信息"
                v-model="searchQuery_stastic"
                clearable
                size="small"
                style="margin-right: 10px; width: 300px;">
              </el-input>
              <el-button 
                  type="primary"
                  size = "small"
                  icon="el-icon-search"
                  @click="filterData_stastic" >
                </el-button>
        </div>
        <el-row :gutter="20" class = "三个图">
          <el-col :span="8">
          <div style="background-color: white;box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
                  border-radius: 2px;height: 250px;width: 100%;">
                <div class="tag-container">
                  <el-tag type="danger" effect="dark">各课程均分</el-tag>
                </div>
                <div id ="meanscore" class="mean-score" ref="echarts" style="height: 250px;width: 100%;min-width: 100px;">
                </div>
              </div>
          </el-col>
          <el-col :span="8">
            <div class="评价维度分数" style="background-color: white;box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
                border-radius: 2px;height: 250px;width: 100%;">
                <div class="tag-container">
                <el-tag type="warning" effect="dark">该课程评价维度</el-tag>
                </div>
                <div id ="typescore" class="type_score" ref="echarts" style="height: 240px;width:100%;">
              </div>
            </div>
          </el-col>
          <el-col :span="8">
            <div style="background-color: white;box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
                border-radius: 2px;height: 250px;width: 100%;">
              <div class="tag-container">
                <el-tag type="success" effect="dark">该课程分数比例</el-tag>
              </div>
              <div id ="highscore" class="高分比例" ref="echarts" style="height: 240px;width:100%;">
              </div>
            </div>
          </el-col>
        </el-row>
       
        <div class = "近期评论">
          <el-row type="flex" justify="space-between" class="comment-header">
            <el-col class="left">
              <span>近期评论</span>
              <span class="comment-instruction">点击评论内容可以查看评论详细信息</span>
            </el-col>
            <el-col class="right" style="text-align: right">
              <!-- 搜索框 -->
              <el-input
                placeholder="请输入 CourseId"
                v-model="searchQuery"
                clearable
                size="small"
                style="margin-right: 10px; width: 200px;">
              </el-input>
                <!-- 筛选按钮 -->
                <el-button 
                  type="primary"
                  size = "small"
                  icon="el-icon-search"
                  @click="filterData" >
                </el-button>
            </el-col>
          </el-row>
      </div>
      <el-table class = "评论表格"
      ref="filterTable"
      :data="tableData_que.slice((currentPage-1)*pageSize,currentPage*pageSize)"
      :row-style="{ height: '55px' }"
      :cell-style="{ padding: '0px' }"
      style="width: 100%">
      
      <el-table-column
        prop="ques_start_date"
        label="开始时间"
        width="120"
        column-key="date">
      </el-table-column>
      <el-table-column
        prop="ques_end_date"
        label="结束时间"
        width="120"
        column-key="date">
      </el-table-column>
      
      <el-table-column
        prop="student_name"
        label="学生姓名"
        width="120">
      </el-table-column>
      
      <el-table-column
        prop="course_name"
        label="课程"
        width="120"
        column-key="course">
      </el-table-column>
      
      <el-table-column
        prop="ques_comment"
        label="评论"
        width="280"
        column-key="comment">
        <template slot-scope="scope">
          <span @click.stop="showComment(scope.row)">{{ scope.row.ques_comment }}</span>
        </template>
      </el-table-column>
      
      <el-table-column
        prop="ques_grade"
        label="评分"
        width="200"
        column-key="ques_grade"
        :formatter="formatter">
        <template slot-scope="scope">
          <el-rate
            v-model="scope.row.ques_grade" 
            disabled
            show-score
            text-color="#ff9900"
            score-template="{value}">
          </el-rate>
        </template>
      </el-table-column>
    </el-table>
         <!-- 对话框组件 -->
    <el-dialog
      title="评论详情"
      :visible.sync="dialogVisible"
      custom-class="custom-dialog"
      width="40%">
      <div class="dialog-content">
        <div class="info-row">
          <span>学生ID:</span>
          <span>{{ selectedComment.student_id }}</span>
        </div>
        <el-divider></el-divider>
        <div class="info-row">
          <span>学生姓名:</span>
          <span>{{ selectedComment.student_name }}</span>
        </div>
        <el-divider></el-divider>
        <div class="info-row">
          <span>课程名称:</span>
          <span>{{ selectedComment.course_name }}</span>
        </div>
        <el-divider></el-divider>
        <div class="info-row">
          <span>教学方法分数:</span>
          <span>{{ selectedComment.ques_aspect1 }}</span>
        </div>
        <el-divider></el-divider>
        <div class="info-row">
          <span>课堂管理分数:</span>
          <span>{{ selectedComment.ques_aspect2 }}</span>
        </div>
        <el-divider></el-divider>
        <div class="info-row">
          <span>学生参与和动力分数:</span>
          <span>{{ selectedComment.ques_aspect3 }}</span>
        </div>
        <el-divider></el-divider>
        <div class="info-row">
          <span>评估和反馈分数:</span>
          <span>{{ selectedComment.ques_aspect4 }}</span>
        </div>
        <el-divider></el-divider>
        <div class="info-row">
          <span>成绩:</span>
          <span>{{ selectedComment.ques_grade }}</span>
        </div>
        <el-divider></el-divider>
        <div class="info-row">
          <span>评论:</span>
          <span>{{ selectedComment.ques_comment }}</span>
        </div>
        
      </div>
      <div slot="footer">
        <el-button @click="dialogVisible = false">关闭</el-button>
      </div>
    </el-dialog>
        <div class="pagination-container">
          <el-pagination
            @current-change="handlePageChange"
            background
            layout="prev, pager, next"
            :current-page="currentPage" 
            :page-size="pageSize"
            :total="tableData_que.length">
          </el-pagination>
      </div>
      </el-main>
    </el-container>
  </div>
</template>

<script>

import * as echarts from 'echarts'
import request from '@/utils/request'
export default {
  data() {
    return {
        dialogVisible: false,
        searchQuery: '',
        searchQuery_stastic: '',
        selectedComment: '',
        searchQuery_satatic:'',
        currentPage: 1,
        pageSize: 10,
        itemNum: 0 ,
        activeNames: ['1'],
        //tableData: []
        tableData_que:[],
        statistic:{},
        statistic_course:[],
        statistic_ProportionScore:[],
        statistic_typescore:[],
    };
    
  },
  methods: {
    async fetchTableData_que() {
        // 发送 HTTP GET 请求获取表格数据
        try {
        const response = await request.get('/Questionnaire/teacher/search', {
            params: {
              teacher_id: 2001,  //this.$store.state.dev_id
              course_id:''//为空怎么表示
            }
        });
        this.tableData_que = response.data.map(item => ({
          ques_id: item.ques_id||'',
          ques_start_date: item.ques_start_date||'',
          ques_end_date:item.ques_end_date||'',
          ques_status:item.ques_status||'',
          ques_aspect1:item.ques_aspect1||'',
          ques_aspect2:item.ques_aspect2||'',
          ques_aspect3:item.ques_aspect3||'',
          ques_aspect4:item.ques_aspect4||'',
          grade:item.ques_grade||'',
          ques_comment:item.ques_comment||'',
          student_id:item.student_id||'',
          teacher_id: item.teacher_id || '',   
          course_id: item.course_id || '',
          course_name: item.course_name || '',
          teacher_name:item.teacher_name|| '',
          student_name:item.student_name|| '',
        }));
        this.$message({
            message: '数据获取成功',
            type: 'success'
          });
        } catch (error) {
          console.error('获取数据失败:', error);
          // 弹出失败框框
          this.$message.error('数据获取失败，请重试');
        }
      },
      async filterData(){
        try {
        const response = await request.get('/Questionnaire/teacher/search', {//筛选条件的param后端需要规定一下
            params: {
              teacher_id: 2001,//this.$store.state.dev_id
              course_id:this.searchQuery
            }
        });
        console.log(response)
          this.tableData_que = response.data.map(item => ({
            ques_id: item.ques_id||'',
            ques_start_date: item.ques_start_date||'',
            ques_end_date:item.ques_end_date||'',
            ques_status:item.ques_status||'',
            ques_aspect1:item.ques_aspect1||'',
            ques_aspect2:item.ques_aspect2||'',
            ques_aspect3:item.ques_aspect3||'',
            ques_aspect4:item.ques_aspect4||'',
            grade:item.ques_grade||'',
            ques_comment:item.ques_comment||'',
            student_id:item.student_id||'',
            teacher_id: item.teacher_id || '',   
            course_id: item.course_id || '',
            course_name: item.course_name || '',
            teacher_name:item.teacher_name|| '',
            student_name:item.student_name|| '',
          }));
          this.$message({
            message: '数据获取成功',
            type: 'success'
          });
        } catch (error) {
          console.error('获取数据失败:', error);
          // 弹出失败框框
          this.$message.error('数据获取失败，请重试');
        }
      },    
      async fecthCourse_stastic(){
        // 发送 HTTP GET 请求获取表格数据
        try {
        const response = await request.get('/Questionnaire/teacher/statistics', {//筛选条件的param后端需要规定一下
            params: {
              teacher_id: 1,//this.$store.state.dev_id
              course_id:'',
            }
        });
        var statistic = response.data.map(item => ({//0分占比，1分占比...
          grade:item.grade,
          course_id:item.course_id,
          course_name:item.course_name
        }));
        this.statistic_course = statistic
        fetchmeanscore_true()
        } catch (error) {
          // 弹出失败框
          this.$message.error('课程统计数据获取失败，请重试');
        }
      },
      async filterData_stastic() {
        // 发送 HTTP GET 请求获取表格数据
        try {
        const response = await request.get('/Questionnaire/teacher/statistics', {//筛选条件的param后端需要规定一下
            params: {
              teacher_id: 2001,
              course_id:this.searchQuery_stastic,
            }
        });
        console.log(response)
        const dataArray = Array.isArray(response.data) ? response.data : [response.data];

    // 使用 map 方法处理数据
    var statistic = dataArray.map(item => ({
      average1: item.average1,
      average2: item.average2,
      average3: item.average3,
      average4: item.average4,
      course_id: item.course_id,
      course_name: item.course_name,
      grade: item.grade,
      num1: item.num1,
      num2: item.num2,
      num3: item.num3,
      num4: item.num4,
      num5: item.num5,
      teacher_id: item.teacher_id
    }));
        this.statistic = statistic[0]
        console.log(statistic)
        this.fetchProportionScore_true()
        this.fetchtypescore_true()
        this.$message({
            message: '数据获取成功',
            type: 'success'
          });
        } catch (error) {
          // 弹出失败框框
          //this.fetchhighscore()
          //this.fetchtypescore()
          this.$message.error('数据获取失败，请重试');
          console.log(error)
        }
      },
      fetchProportionScore_true() {
        // 发送 HTTP GET 请求获取表格数据
        this.statistic_ProportionScore =  [
            { value: this.statistic.num1, name: '0-1分' },
            { value: this.statistic.num2, name: '1-2分' },
            { value: this.statistic.num3, name: '2-3分' },
            { value: this.statistic.num4, name: '3-4分' },
            { value: this.statistic.num5, name: '4-5分' },
          ]
        // 更新图表
        this.setHighScore(this.statistic_ProportionScore);
      },
      fetchmeanscore_true() {
        //是一个数组
        var statistic = this.statistic_course.map(item => ({//每年的平均分
          course:item.course_name,
          mean_score:item.grade
        }));
        var x_data = statistic.course;
          this.x_data = x_data;
          var y_data = statistic.mean_score;
          this.y_data = y_data;
        // 更新图表
        this.setMeanScore(this.x_data,this.y_data);
      },
      fetchtypescore_true() {
        this.statistic_typescore =  [
            { value: this.statistic.average1, name: '教学方法' },
            { value: this.statistic.average2, name: '课堂管理' },
            { value: this.statistic.average3, name: '学生参与和动力' },
            { value: this.statistic.average4, name: '评估和反馈' },
          ]
        // 更新图表
        this.setTypeScore(this.statistic_typescore);
      },
      setHighScore(statistic){
        var chartDom = document.getElementById("highscore");
        var myChart = echarts.init(chartDom);
        var option;
        option = {
          tooltip: {
            trigger: 'item'
          },
          legend: {
            top: '5%',
            left: 'center'
          },
          series: [
            {
              name: 'Access From',
              type: 'pie',
              radius: ['40%', '70%'],
              avoidLabelOverlap: false,
              padAngle: 5,
              itemStyle: {
                borderRadius: 10
              },
              label: {
                show: false,
                position: 'center'
              },
              emphasis: {
                label: {
                  show: true,
                  fontSize: 40,
                  fontWeight: 'bold'
                }
              },
              labelLine: {
                show: false
              },
              data:statistic
            }
          ]
        };
          myChart.setOption(option);
      },
      setMeanScore(x_data,y_data){//近五年的平均分
        var chartDom = document.getElementById("meanscore");
        var myChart = echarts.init(chartDom);
        var option;
        option = {
          xAxis: {
            type: 'category',
            data:x_data
          },
          yAxis: {
            type: 'value'
          },
          series: [
            {
              type: 'line',
              smooth: true,
              data:y_data
            }
          ]
        };
        myChart.setOption(option);
        //对图表进行自适应处理        
      },
      setTypeScore(statistic){//近五年的平均分
        var chartDom = document.getElementById("typescore");
        var myChart = echarts.init(chartDom);
        var option;
        option = {
          tooltip: {
            trigger: 'item'
          },
          legend: {
            top: '5%',
            left: 'center'
          },
          series: [
            {
              name: 'Access From',
              type: 'pie',
              radius: ['40%', '70%'],
              avoidLabelOverlap: false,
              padAngle: 5,
              itemStyle: {
                borderRadius: 10
              },
              label: {
                show: false,
                position: 'center'
              },
              emphasis: {
                label: {
                  show: true,
                  fontSize: 40,
                  fontWeight: 'bold'
                }
              },
              labelLine: {
                show: false
              },
              data: statistic
            }
          ]
        };
        myChart.setOption(option);
        //对图表进行自适应处理        
      },
     
      handleRowClick(row, column, event) {
      // 当表格行被点击时触发，获取被点击的评论内容并显示在对话框中
      this.selectedComment = row.comment;
      this.dialogVisible = true;
    },
    showComment(row) {
      this.selectedComment = row;
      this.dialogVisible = true;
    },
    formatter(row, column, cellValue, index) {
      return cellValue;
    },
    handlePageChange(page) {
      // 处理页码变化
      this.currentPage = page;
      }
    },
  mounted(){
    this.fetchmeanscore_true();
     // this.fetchhighscore();
     // this.fetchtypescore(); 
     this.fetchTableData_que()
     //this.fecthCourse_stastic()
  }
};
</script>

<style scoped>
.comment-instruction {
  font-size: 11px;
  color: #666;
  margin-left: 10px;
}
.tag-container {
  padding-left: 10px;
  margin-bottom: 0; /* 确保没有底部间距 */
  padding-top: 10px;
}
.pagination-container {
  display: flex;
  justify-content: flex-end;
  margin-top: 10px;
}
.mean-score {
  margin-top: 0; /* 确保没有顶部间距 */
}

.comment-header .left span {
  font-size: 16px;
  font-weight: bold;
  text-align: left;
}

.comment-header .right a {
  font-size: 14px;
  color: #409EFF; /* Element UI 的默认主题色 */
  text-align: right;
  text-decoration: none;
}

.user {
  font-weight: bold;
}

.timestamp {
  font-size: 0.8em;
  color: #666;
}
.el-main {
  background-color: #fff;
  color: #333;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  border-radius: 2px;
}

.el-row {
  margin-bottom: 20px;
  &:last-child {
    margin-bottom: 0;
  }
}
.text {
    font-size: 14px;
  }

  .item {
    margin-bottom: 18px;
    padding-left: 11px;
    padding-top: 15px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both
  }
  .comment-header {
  padding: 10px 0;
  border-bottom: 1px solid #e4e7ed; /* 分割线，可根据需要调整 */
}

.comment-header span {
  font-size: 16px;
  font-weight: bold;
}

.comment-header a {
  font-size: 14px;
  color: #409EFF; /* Elment UI 的默认主题色 */
  text-decoration: none;
}

.comment-header a:hover {
  text-decoration: underline;
}

.el-col {
  border-radius: 4px;
}

.bg-purple-dark {
  background: #99a9bf;
}

.bg-purple {
  background: #d3dce6;
}

.bg-purple-light {
  background: #e5e9f2;
}

.grid-content {
  border-radius: 4px;
  min-height: 36px;
  color: #333;
  line-height: 36px;
}

.row-bg {
  padding: 10px 0;
  background-color: #f9fafc;
}

.el-table {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  border-radius: 2px;
}

/* 在你的样式文件中添加以下样式 */
.custom-dialog .el-dialog__header,
.custom-dialog .el-dialog__body {
  border-radius: 8px; /* 设置边框圆角 */
}

.custom-dialog .el-dialog__wrapper {
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1); /* 添加阴影效果 */
}

.custom-dialog .el-dialog__header {
  background: linear-gradient(to right, #4e54c8, #8f94fb); /* 添加渐变背景 */
  color: #fff; /* 设置标题颜色为白色 */
  font-weight: bold; /* 设置标题字体加粗 */
}

.custom-dialog .el-divider {
  margin: 10px 0; /* 设置分割线的上下间距 */
}

.custom-dialog .info-row span:first-child {
  color: #4e54c8; /* 设置信息行标题的颜色 */
}

.custom-dialog .info-row span:last-child {
  color: #666; /* 设置信息行内容的颜色 */
}

</style>
