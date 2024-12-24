<template>
  <div>
    <el-container>
      <el-main>
        <div class = "评论管理">
        <div >
          <el-row type="flex" justify="space-between" class="comment-header">
            <el-col class="left" span = 8>
              <span>评论管理</span>
              <span class="comment-instruction">点击评论内容可以查看评论详细信息</span>
            </el-col>
            <el-col class="right" style="text-align: right" span = 16>
              <div>
                <el-button 
                  type="primary"
                  size="small"
                  icon="el-icon-search"
                  @click="filterDataSensitive">
                  获取含有敏感词问卷
                </el-button>
                <el-button 
                  type="primary"
                  size="small"
                  icon="el-icon-document"
                  @click="filterDataAll">
                  获取所有问卷
                </el-button>
                <el-button 
                  type="primary"
                  size="small"
                  icon="el-icon-document"
                  @click="filterDataOver">
                  获取所有已结束问卷
                </el-button>
                <el-button 
                  type="danger"
                  size="small"
                  icon="el-icon-document"
                  @click="deleteAllInvalidQuestionnaire">
                  删除所有无效问卷
                </el-button>
              </div>                          
            </el-col>
          </el-row>
      </div>
      <el-table class = "评论表格"
            ref="filterTable"
            :data="tableData_que.slice((currentPage-1)*pageSize,currentPage*pageSize)"
            @row-click="handleCellClick"
            :row-style="{height:'55px'}"
            :cell-style="{padding:'0px'}"
            cell-style="cellStyle"
            style="width: 100%">
            
                <el-table-column
            prop="ques_start_date"
            label="开始时间"
            width="180"
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

            <el-table-column label="操作" width="100">
              <template slot-scope="scope">
                <div>
                  <el-button 
                    size="mini" 
                    type="danger" 
                    @click="deleteRow(scope.$index)">
                    删除
                  </el-button>
                  <!--
                  <el-button type="success" size="small" @click="approve(scope.row)">
                    通过
                  </el-button>--->
                </div>
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
      </div>
        <div class = "questionnaire-issue" style="margin-top: 20px;">
          <el-row type="flex" justify="space-between" class="comment-header">
            <el-col class="left" span = 8>
              <span>问卷发布</span>
            </el-col>
            <el-col class="right" style="text-align: right" span = 16>
               <!-- 过滤器 -->
               <div>
                <el-date-picker  size="small" v-model="startTime" type="date" placeholder="选择开始时间" style="width: 200px; margin-right: 10px;" value-format="yyyy-MM-dd">
                </el-date-picker>

                <el-date-picker  size="small" v-model="endTime" type="date" placeholder="选择结束时间" style="width: 200px; margin-right: 10px;" value-format="yyyy-MM-dd">
                </el-date-picker>

              
                    <el-input
                    v-model="courseId"
                    placeholder="请输入课程ID"
                    clearable
                    size="small"
                    style="width: 200px; margin-right: 10px;">
                  </el-input>

                    <el-button 
                    type="primary"
                    size="small"
                    icon="el-icon-upload"
                    @click="issueQuestionnaire">
                    发布问卷
                  </el-button>
                  </div>
            </el-col>
          </el-row>
          <!-- 描述文本框 -->
            <div style="margin-top: 20px; padding: 10px; background-color: #ffffff; border-radius: 8px; box-shadow: 0 1px 6px rgba(0, 0, 0, 0.1);">
              <p style="margin: 0; color: #666; font-size: 14px;">
                请按照以下步骤发布问卷：
                <ol style="padding-left: 20px;">
                  <li>选择问卷的开始时间和结束时间。</li>
                  <li>输入需要发布问卷的课程ID。</li>
                  <li>点击“发布问卷”按钮提交问卷发布请求。</li>
                </ol>
                确保所有信息填写正确后再进行发布。发布成功后会有提示信息。
              </p>
            </div>
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
        selectedComment: '',
        currentPage: 1,
        pageSize: 2,
        itemNum: 0 ,
        activeNames: ['1'],
        showDropdown: false,  // 控制下拉菜单显示与隐藏的变量
        tableData: [{
          ques_id: 1 || '',
          ques_start_date: '2024-05-02' || '',
          ques_end_date: '2024-05-16' || '',
          ques_status: '已完成' || '',
          ques_aspect1: 4 || '',
          ques_aspect2: 4.5 || '',
          ques_aspect3: 4.8 || '',
          ques_aspect4: 4.7 || '',
          ques_grade: 4.8 || '',
          ques_comment: '上课认真负责，学生反应良好' || '',
          student_id: 'S001' || '',
          teacher_id: 'T001' || '',   
          course_id: 'C001' || '',
          course_name: '数学' || '',
          teacher_name: '' || '',
          student_name: '张三' || '',
        },
        {
          ques_id: 2 || '',
          ques_start_date: '2024-05-04' || '',
          ques_end_date: '2024-05-18' || '',
          ques_status: '已完成' || '',
          ques_aspect1: 3.5 || '',
          ques_aspect2: 4.0 || '',
          ques_aspect3: 4.2 || '',
          ques_aspect4: 3.8 || '',
          ques_grade: 4.6 || '',
          ques_comment: '课堂气氛活跃，学生积极参与' || '',
          student_id: 'S002' || '',
          teacher_id: 'T002' || '',   
          course_id: 'C002' || '',
          course_name: '物理' || '',
          teacher_name: '' || '',
          student_name: '李四' || '',
        },
        {
          ques_id: 3 || '',
          ques_start_date: '2024-05-02' || '',
          ques_end_date: '2024-05-16' || '',
          ques_status: '已完成' || '',
          ques_aspect1: 4 || '',
          ques_aspect2: 4.5 || '',
          ques_aspect3: 4.8 || '',
          ques_aspect4: 4.7 || '',
          ques_grade: 4.8 || '',
          ques_comment: '上课认真负责，学生反应良好' || '',
          student_id: 'S001' || '',
          teacher_id: 'T001' || '',   
          course_id: 'C001' || '',
          course_name: '数学' || '',
          teacher_name: 'll' || '',
          student_name: '张三' || '',
        },
        {
          ques_id: 4 || '',
          ques_start_date: '2024-05-04' || '',
          ques_end_date: '2024-05-18' || '',
          ques_status: '已完成' || '',
          ques_aspect1: 3.5 || '',
          ques_aspect2: 4.0 || '',
          ques_aspect3: 4.2 || '',
          ques_aspect4: 3.8 || '',
          ques_grade: 3.8 || '',
          ques_comment: '课堂气氛活跃，学生积极参与' || '',
          student_id: 'S002' || '',
          teacher_id: 'T002' || '',   
          course_id: 'C002' || '',
          course_name: '物理' || '',
          teacher_name: 'll' || '',
          student_name: '李四' || ''
        }
      ],
        tableData_que:[],
        startTime: '',
        endTime: '',
        courseId: ''
        //tableData: []
    };
    
  },
  methods: {
      async filterDataAll() {
        // 发送 HTTP GET 请求获取表格数据
        try {
          const response = await request.get('/QuestionnaireManagement/getAllQuestionnaire');
          console.log(response)
          this.tableData_que = response.data.questionnaires.map(item => ({
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
      async filterDataSensitive() {
        // 发送 HTTP GET 请求获取表格数据
        try {
          const response = await request.get('/QuestionnaireManagement/getSensitiveQuestionnaire');
          console.log(response)
          this.tableData_que = response.data.questionnaires.map(item => ({
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
      async deleteRow(index) {
        console.log(index)
        const quesID = this.tableData_que[index].ques_id;
        console.log(quesID)
        try {
          const response = await request.post('/QuestionnaireManagement/deleteQuestionnaire', {quesID:quesID});
          //if (response.status === 200) {
            this.tableData_que.splice(index, 1);
            this.$message.success('删除成功');
          //} else {
         //   this.$message.error('删除失败，请检查信息是否正确');
         // }
        } catch (error) {
          this.$message.error('删除失败，请稍后再试');
          console.error('删除问卷失败:', error);
        }
      }, 
      async deleteAllInvalidQuestionnaire(){
        try {
          const response = await request.post('/QuestionnaireManagement/deleteAllInvalidQuestionnaire');
          if (response.status === 200) {
            this.$message.success('删除成功');
          } else {
            this.$message.error('删除失败，请检查信息是否正确');
          }
        } catch (error) {
          this.$message.error('删除失败，请稍后再试');
          console.error('删除问卷失败:', error);
        }
      },
      async filterDataOver(){
        // 发送 HTTP GET 请求获取表格数据
        try {
          const response = await request.get('/QuestionnaireManagement/getAllEndQuestionnaire');
          this.tableData_que = response.data.questionnaires.map(item => ({
            ques_id: item.ques_id||'',
            ques_start_date: item.ques_start_date||'',
            ques_end_date:item.ques_end_date||'',
            ques_status:item.ques_status||'',
            ques_aspect1:item.ques_aspect1||'',
            ques_aspect2:item.ques_aspect2||'',
            ques_aspect3:item.ques_aspect3||'',
            ques_aspect4:item.ques_aspect4||'',
            ques_grade:item.ques_grade||'',
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
      async issueQuestionnaire() {
        const startTime = this.startTime ? this.startTime.toString() : '';
        const endTime = this.endTime ? this.endTime.toString() : '';
        const courseId = this.courseId ? this.courseId.toString() : '';
        // 验证输入信息
        if (!startTime || !endTime || isNaN(courseId)) {
            this.$message.error('请输入正确信息');
            return; // 终止函数执行
        }

        try {
          const response = await request.post('/QuestionnaireManagement/issueQuestionnaire', {
            startTime: startTime,
            endTime: endTime,
            courseId: courseId
          });
          if (response.status === 200) {
            this.$message.success('问卷发布成功');
          } else {
            this.$message.error('问卷发布失败，请检查信息是否正确');
          }
        } catch (error) {
          this.$message.error('问卷发布失败，请检查信息是否正确,稍后再试');
        }
      }
      ,
      handlePageChange(page) {
        // 处理页码改变事件
        this.currentPage = page;
        // 此处可以根据新的页码获取对应的数据
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
    },
    mounted(){
      filterDataAll()
    }
};
</script>

<style scoped>
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
.comment-column{
  cursor: pointer;
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

.comment-instruction {
  font-size: 11px;
  color: #666;
  margin-left: 10px;
}


</style>
