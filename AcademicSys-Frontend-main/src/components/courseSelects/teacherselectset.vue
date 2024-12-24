<template>
  <div id="app" class="container">

    <form @submit.prevent="submitCourseTime" class="course-form">
      <div class="form-group">
        <label for="start_time">选课开始时间:</label>
        <input type="datetime-local" id="start_time" v-model="courseTime.start_time" required>
      </div>
      <div class="form-group">
        <label for="end_time">选课结束时间:</label>
        <input type="datetime-local" id="end_time" v-model="courseTime.end_time" required>
      </div>
      <div class="form-group">
        <label for="turn">选课轮次:</label>
        <select id="turn" v-model="courseTime.turn" required>
          <option value="1">第一轮</option>
          <option value="2">第二轮</option>
          <option value="3">第三轮</option>
        </select>
      </div>
      <button type="submit" class="submit-button">提交</button>
      
      <button type="button" @click="screenStudents" class="screen-button">筛选</button>
    </form>

    <div v-if="message" class="message">
      <p :class="{'success-message': isSuccess, 'error-message': !isSuccess}">
        {{ message }}
      </p>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      courseTime: {
        turn: '1',
        start_time: '',
        end_time: '',
      },
      message: '',
      isSuccess: false
    }
  },
  methods: {
    async submitCourseTime() {
        this.$http.post('/admin/addCourseTime', {
        turn: this.courseTime.turn, 
        start_time: this.formatDateTime(this.courseTime.start_time), 
        end_time: this.formatDateTime(this.courseTime.end_time)})
      .then(response => {
            this.message = '提交成功';
            this.isSuccess = true;
          })
      .catch(error => {
            this.message = '提交出错';
            this.isSuccess = false;
          });
    },
    async screenStudents() {
      this.$http.post('/admin/screenStudents')
      .then(response => {
            this.message = '筛选完成';
            this.isSuccess = true;
          })
      .catch(error => {
            this.message = '提交出错';
            this.isSuccess = false;
          });
    },
    formatDateTime(dateTime) {
      const date = new Date(dateTime);
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, '0');
      const day = String(date.getDate()).padStart(2, '0');
      const hours = String(date.getHours()).padStart(2, '0');
      const minutes = String(date.getMinutes()).padStart(2, '0');
      const seconds = '00'; // Assuming seconds are not needed

      return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
    }
  }
}
</script>

<style>
/* 添加一些全局样式 */

body {
  font-family: 'Arial', sans-serif;
  background-color: #f4f4f4;
  margin: 0;
  padding: 20px;
}

.container {
  max-width: 600px;
  margin: auto;
  background: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0,0,0,0.1);
}

.title {
  text-align: center;
  color: #333;
}

.form-group {
  margin-bottom: 20px;
}

label {
  display: block;
  margin-bottom: 5px;
  color: #666;
}

input[type="datetime-local"],
select {
  width: 100%;
  padding: 10px;
  margin: 10px 0;
  border: 1px solid #ddd;
  border-radius: 4px;
  box-sizing: border-box; /* 防止宽度超出容器宽度 */
}

.submit-button {
  width: 100%;
  padding: 10px;
  border: 5px;
  border-radius: 4px;
  background-color: #5ca0b8;
  color: white;
  cursor: pointer;
  font-size: 16px;
  margin-bottom: 10px;
}
.screen-button{
  width: 100%;
  padding: 10px;
  border: 5px;
  border-radius: 5px;
  background-color: #5ca0b8;
  color: white;
  cursor: pointer;
  font-size: 16px;
}

.submit-button:hover {
  background-color: #4cae4c;
}
.screen-button:hover {
  background-color: #4cae4c;
}
.message {
  text-align: center;
  margin-top: 20px;
}

.success-message {
  color: #3c763d;
  background-color: #dff0d8;
  border-color: #d6e9c6;
  padding: 10px;
  border-radius: 4px;
}

.error-message {
  color: #a94442;
  background-color: #f2dede;
  border-color: #ebccd1;
  padding: 10px;
  border-radius: 4px;
}
</style>