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
      </li>
    </ul>
  </div>
</template>


<script>
import axios from 'axios';

export default {
  name: 'SearchComponent',
  data() {
    return {
      searchTerm: '',
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
      const course = this.courses.find(c => c.id === courseId);
      if (course && course.selected < course.capacity) {
        axios.post('/usr/student/selectCourse', { courseId })
        .then(response => {
          console.log('Course selected successfully');
          this.fetchCourses(); // Refresh the course list to get updated data
        })
        .catch(error => {
          console.error('There was an error selecting the course:', error);
        });
      }
    }
  },
  created() {
    this.fetchCourses();
  }
};
</script>

<style scoped>
/* Add your styles here */
</style>