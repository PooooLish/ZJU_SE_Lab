<template>
  <div id="app">
    <table>
      <tr><td colspan="16" style="height: 20px"></td></tr>
      <tr>
        <th>课时/日期</th>
        <th v-for="day in days" :key="day">{{ day }}</th>
      </tr>
      <tr v-for="(time, index) in times" :key="index">
        <td>{{ time }}</td>
        <template v-for="day in days">
          <td>{{  }}</td>
        </template>
      </tr>
    </table>
    <ul>
      <li v-for="course in courses" :key="course.course_id" class="course-item">
        <span class="course-name">{{ course.course_name }}</span> - 
        <span class="course-description">{{ course.description }}</span> - 
        <span class="course-instructor">讲师: {{ course.instructor }}</span>
        <span class="course-info">（{{ course.num_students }}/{{ course.max_students }} 已选）</span>
        <button @click="dropCourse(course.course_id)">退选</button>
      </li>
    </ul>
  </div>

</template>

<script>
import axios from 'axios';

export default {
  name: 'ShowComponent',
  data() {
    return {
      days: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'],
      times: ['第1节', '第2节', '第3节', '第4节', '第5节', '第6节', '第7节', '第8节', '第9节', '第10节', '第11节', '第12节', '第13节'],
      courses: [],
      schedule: {}
    };
  },
  methods: {
    fetchStudentCourses() {
      axios.get('/usr/student/courseSelectionInformation')
      .then(response => {
        this.courses = response.data.data.info;
      })
      .catch(error => {
        console.error('There was an error fetching the courses:', error);
      });
    },
  },
  created() {
    this.fetchStudentCourses();
    const randomDayIndex = Math.floor(Math.random() * this.days.length);
      const randomTimeIndex = Math.floor(Math.random() * this.times.length);
      const day = this.days[randomDayIndex];
      const time = this.times[randomTimeIndex];
      if (!this.schedule[day]) {
        this.schedule[day] = {};
      }
      this.schedule[day][time] = course.id;
  }
};
</script>

<style scoped>
table {
  width: 100%;
  border-collapse: collapse;
}
th, td {
  border: 1px solid black;
  padding: 8px;
  text-align: center;
}
</style>
