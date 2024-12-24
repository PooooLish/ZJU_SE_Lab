<template>
  <div>
    <input v-model="searchTerm" type="text" placeholder="搜索课程名称、课程ID或授课老师" style="width: 1500px; height: 80px; font-size: 30px;">
    <button @click="search" style="font-size: 30px; padding: 20px 40px;">搜索</button>
    <ul>
      <li v-for="course in filteredCourses" :key="course.course_id" style="border: 1px solid #000; background-color: #fff; margin: 10px; padding: 10px; font-size: 20px;">
        <span class="course-name">{{ course.course_name }}</span> - 
        <span class="course-id">{{ course.course_id }}</span> - 
        <span class="course-description">{{ course.description }}</span> - 
        <span class="course-instructor">讲师: {{ course.instructor }}</span>
        <span class="course-info">（{{ course.num_students }}/{{ course.max_students }} 已选）</span>
        <button @click="selectCourse(course.course_id)" :disabled="course.num_students >= course.max_students">选课</button>
        <div v-if="selectedCourseId === course.course_id">
          <textarea v-model="reasons[course.course_id]" placeholder="填写补选理由" style="width: 100%; height: 100px; font-size: 20px;"></textarea>
          <button @click="submitReason(course.course_id)" style="font-size: 20px; padding: 10px 20px;">提交补选理由</button>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'BySelectionComponent',
  data() {
    return {
      searchTerm: '',
      selectedCourseId: null,
      reasons: {},
      courses: [],
      filteredCourses: []
    };
  },
  methods: {
    fetchCourses() {
      axios.get('/usr/student/course/category/all')
        .then(response => {
          this.courses = response.data.data.info;
          this.filteredCourses = this.courses;
        })
        .catch(error => {
          console.error('There was an error fetching the courses:', error);
        });
    },
    search() {
      let searchUrl;
      if (isNaN(this.searchTerm)) {
        searchUrl = `/usr/student/course/course_name/${this.searchTerm}`;
      } else {
        searchUrl = `/usr/student/course/id/${this.searchTerm}`;
      }

      axios.get(searchUrl)
        .then(response => {
          this.filteredCourses = Array.isArray(response.data.data.info) ? response.data.data.info : [response.data.data.info];
        })
        .catch(error => {
          console.error('There was an error searching the courses:', error);
        });
    },
    selectCourse(courseId) {
      this.selectedCourseId = courseId;
    },
    submitReason(courseId) {
      const course = this.courses.find(c => c.id === courseId);
      if (course.selected < course.capacity) {
        axios.post('/usr/student/selectCourse', { courseId, reason: this.reasons[courseId] })
          .then(response => {
            alert(`课程 ${course.name} 补选理由提交成功：${this.reasons[courseId]}`);
            this.fetchCourses(); // Refresh the course list to get updated data
            this.reasons[courseId] = '';
            this.selectedCourseId = null;
          })
          .catch(error => {
            console.error('There was an error selecting the course:', error);
          });
      } else {
        alert('课程已满，无法补选');
      }
    }
  },
  created() {
    this.fetchCourses();
  }
};
</script>

<style scoped>
/* 添加你的样式 */
</style>