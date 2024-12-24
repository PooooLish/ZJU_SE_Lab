<template>
  <div>
   <el-container>
      <el-main>
      <div>  
        <el-tag v-if="forms.length > 0" type="danger" effect="dark" style="margin-bottom: 10px;">
          请尽快完成评价表单
        </el-tag>
        <el-tag v-else type="info" effect="dark" style="margin-bottom: 10px;">
          无评价表单待完成
        </el-tag>
        <el-collapse v-model="activeNames" class = "评价表单">
          <el-collapse-item
            v-for="(form, index) in forms"
            :key="index"
            :name="index.toString()"
          >
          <template #title>
              {{form.ques_status}}评价&nbsp&nbsp&nbsp&nbsp;
              <span>课程：{{ form.course_name }}</span> &nbsp&nbsp&nbsp&nbsp;
              教师：{{ form.teacher_name }}&nbsp&nbsp&nbsp&nbsp;
            </template>
            <el-form :model="form" ref="elForm" :ref="'form' + index" label-width="120px">
              <el-form-item label="教学方法" class="form-item">
                  <el-rate v-model="form.ques_aspect1" show-score style="padding-top: 10px;"></el-rate>
                  <p class="description">
                    请评价教师在所教学科上的专业知识和掌握程度，包括对知识的深度理解、内容传递的准确性以及清晰解释复杂概念的能力。
                  </p>
                </el-form-item>
                <el-form-item label="课堂管理" class="form-item">
                  <el-rate v-model="form.ques_aspect2" show-score style="padding-top: 10px;"></el-rate>
                  <p class="description">
                    请评价教师在课堂管理上的能力，包括以下方面：
                    <ul>
                      <li>维持课堂纪律</li>
                      <li>有效管理时间</li>
                      <li>促进学生参与</li>
                      <li>处理干扰或冲突的专业能力</li>
                    </ul>
                  </p>
                </el-form-item>
                <el-form-item label="学生参与和动力" class="form-item">
                  <el-rate v-model="form.ques_aspect3" show-score style="padding-top: 10px;"></el-rate>
                  <p class="description">
                    请评价教师激发学生积极参与学习并在整个课堂期间保持他们的兴趣的能力，寻找能够鼓励好奇心、批判性思维及对学科产生热情的策略。
                  </p>
                </el-form-item>
                <el-form-item label="评估和反馈" class="form-item">
                  <el-rate v-model="form.ques_aspect4" show-score style="padding-top: 10px;"></el-rate>
                  <p class="description">
                    请评价教师的评估方法及反馈的质量，包括评估的公平性和清晰性、反馈的及时性和质量以及评估与学习目标的一致性。
                  </p>
                </el-form-item>
                <el-form-item label="评论" class="form-item">
                  <el-input v-model="form.ques_comment" type="textarea" rows="4" placeholder="请输入您的评论" class="input-field"></el-input>
                </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="submitForm(index)">提交</el-button>
              </el-form-item>
            </el-form>
          </el-collapse-item>
        </el-collapse>
      </div>
    </el-main>
    </el-container>
  </div>
</template>

<script>
import request from '@/utils/request'
export default {
  data() {
    return {
      forms: [

      ],
      activeNames: [], // 用于存储当前展开的表单项
    };
  },
  
  methods: {
    async fetchData() {
      try {
        const response = await request.get('/Questionnaire/student', {
          params: {
            student_id: 1001
           // student_id: this.$store.state.dev_id
          }
        });
        console.log(response)
        this.forms = response.data.map(item => ({
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
        // 默认展开第一个表单
        if (this.forms.length > 0) {
          this.activeNames = ['0'];
        }
      } catch (error) {
        console.error('获取数据失败:', error);
      }
    },
    
    async submitForm(index) {
      try {
          const form = this.forms[index];
          console.log(form)
          const response = await request.post('/Questionnaire/student/written',form); //接口已验证
            console.log('提交成功:', response.data);
            // 移除已提交的表单
            this.forms.splice(index, 1);
            // 更新 activeNames 以防止索引错误
            this.activeNames = this.activeNames.filter(name => name !== index.toString());
            this.$message({
            message: '数据获取成功',
            type: 'success'
            });
            
        }
         catch (error) {
          console.error('获取数据失败:', error);
          // 弹出失败框框
          this.$message.error('数据获取失败，请重试');
        }
    },
  },
  mounted() {
    if (this.forms.length > 0) {
      this.activeNames = ['0'];
    }
    this.fetchData()
  },
};
</script>

<style scoped>
.el-main {
  background-color: #fff;
  color: #333;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  border-radius: 2px;
  height: 100%;
}
.form-container {
  margin-bottom: 20px; /* 每个表单块的间距 */
}
</style>
