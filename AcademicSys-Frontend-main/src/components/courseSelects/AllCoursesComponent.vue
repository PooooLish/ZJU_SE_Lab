<template>
  <div>
    <ul>
      <li v-for="course in courses" :key="course.course_id" class="course-item">
        <span class="course-name">{{ course.course_name }}</span> - 
        <span class="course-description">{{ course.description }}</span> - 
        <span class="course-instructor">讲师: {{ course.instructor }}</span>
        <span class="course-info">（{{ course.num_students }}/{{ course.max_students }} 已选）</span>
        <button @click="selectCourse(course.course_id)">选课</button>
      </li>
    </ul>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'AllCoursesComponent',
  data() {
    return {
      courses: []
    };
  },
  methods: {
    fetchCourses() {
      axios.get('/usr/student/course')
      .then(response => {
        this.courses = response.data.data.info;
      })
      .catch(error => {
        console.error('There was an error fetching the courses:', error);
      });
    },
    selectCourse(courseId) {
      // 在这里添加选课逻辑
      axios.post('/usr/student/selectCourse', { course_id: courseId })
      .then(response => {
        alert('选课成功');
        this.fetchCourses(); // 重新获取课程列表以更新状态
      })
      .catch(error => {
        console.error('There was an error selecting the course:', error);
      });
    }
  },
  created() {
    this.fetchCourses();
  }
};
</script>

<style scoped>
ul {
  padding: 0;
  margin: 0;
}

.course-item {
  border: 1px solid #ddd;
  padding: 5px;
  margin: 5px 0;
  border-radius: 3px;
  background-color: #f9f9f9;
  list-style-type: none;
  font-size: 0.85em;
  line-height: 1.2;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.course-name {
  font-weight: bold;
}

.course-description,
.course-instructor {
  font-size: 0.85em;
}

.course-info {
  margin-right: 10px;
}

button {
  padding: 5px 10px;
  border: none;
  border-radius: 3px;
  background-color: #4CAF50;
  color: white;
  cursor: pointer;
}

button:hover {
  background-color: #45a049;
}
</style>