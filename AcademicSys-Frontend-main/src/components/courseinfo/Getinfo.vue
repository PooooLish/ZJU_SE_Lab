<template>
    <el-container direction="vertical">
    <div class="bg-container" id="head-container">
        <el-row :gutter="20">
            <el-col :span="6">
                <el-select v-model="searchType" placeholder="查询类别" @change="updatePlaceholder"
                    popper-class="popper-select" :popper-append-to-body="true">
                    <el-option :label="'课程'" :value="'course'"></el-option>
                    <el-option :label="'课本'" :value="'book'"></el-option>
                </el-select>
            </el-col>
            <el-col :span="6">
                <!-- 课程搜索框 -->
                <div v-if="searchType === 'course'">
                    <el-select v-model="selectedCourseIndex" @change="updateCoursePlaceholder">
                        <el-option :label="'课程编号'" :value="'code'"></el-option>
                        <el-option :label="'课程名称'" :value="'name'"></el-option>
                        <el-option :label="'教师'" :value="'teacher'"></el-option>
                    </el-select>
                </div>
                <!-- 课本搜索框 -->
                <div v-if="searchType === 'book'">
                    <el-select v-model="selectedBookIndex" @change="updateBookPlaceholder">
                        <el-option :label="'教材编号'" :value="'code'"></el-option>
                        <el-option :label="'教材名称'" :value="'title'"></el-option>
                        <el-option :label="'ISBN编码'" :value="'isbn'"></el-option>
                        <el-option :label="'作者'" :value="'author'"></el-option>
                    </el-select>
                </div>
            </el-col>
            <el-col :span="6">
                <el-input v-model="inputvalue" :placeholder="requirementPlaceholder" />
            </el-col>
            <el-col :span="6">
                <el-button class="picker-width" icon="el-icon-search" @click="search()">查询</el-button>
            </el-col>
        </el-row>
    </div>



    <div class="bg-container" id="table-container">
        <!-- 搜索结果展示 -->
        <ul v-if="searchType === 'course'">
            <el-table :data="filteredCourseResults" max-height="500px">
                <el-table-column prop="code" label="课程编号" align="center"></el-table-column>
                <el-table-column prop="name" label="课程名称" align="center"></el-table-column>
                <el-table-column prop="teacher" label="教师" align="center"></el-table-column>
                <el-table-column prop="details" label="详情" align="center"></el-table-column>
            </el-table>
        </ul>
        <ul v-if="searchType === 'book'">
            <el-table :data="filteredBookResults" max-height="500px">
                <el-table-column prop="code" label="教材编号" align="center"></el-table-column>
                <el-table-column prop="title" label="教材名称" align="center"></el-table-column>
                <el-table-column prop="isbn" label="ISBN编码" align="center"></el-table-column>
                <el-table-column prop="author" label="作者" align="center"></el-table-column>
                <el-table-column prop="details" label="详情" align="center"></el-table-column>
            </el-table>
        </ul>
    </div>
    </el-container>
</template>

<script>
export default {
    data() {
        return {
            searchType: 'course',
            selectedCourseIndex: 'code',
            selectedBookIndex: 'code',
            inputvalue: '',
            requirementPlaceholder: '搜索课程编号',
            courses: [],
            books: []
        };
    },
    computed: {
        filteredCourseResults() {
            return this.courses;
        },
        filteredBookResults() {
            return this.books;
        }
    },
    methods: {
        updatePlaceholder() {
            if (this.searchType === 'course') {
                this.updateCoursePlaceholder();
            } else {
                this.updateBookPlaceholder();
            }
        },
        updateCoursePlaceholder() {
            switch (this.selectedCourseIndex) {
                case 'name':
                    this.requirementPlaceholder = '搜索课程名称';
                    break;
                case 'code':
                    this.requirementPlaceholder = '搜索课程编号';
                    break;
                case 'teacher':
                    this.requirementPlaceholder = '搜索教师名称';
                    break;
                default:
                    this.requirementPlaceholder = '搜索';
            }
        },
        updateBookPlaceholder() {
            switch (this.selectedBookIndex) {
                case 'title':
                    this.requirementPlaceholder = '搜索教材名称';
                    break;
                case 'code':
                    this.requirementPlaceholder = '搜索教材编号';
                case 'isbn':
                    this.requirementPlaceholder = '搜索ISBN编码';
                    break;
                case 'author':
                    this.requirementPlaceholder = '搜索作者';
                    break;
                default:
                    this.requirementPlaceholder = '搜索';
            }
        },
        search() {
            if (this.searchType === 'course') {
                this.searchCourses();
            } else {
                this.searchBooks();
            }
        },
        updateCourses(coursesToBeUpdated) {
            console.log(coursesToBeUpdated);
            this.courses = [];
            for (var i = 0; i < coursesToBeUpdated.data.length; i++) {
                var teacher = '';
                if (coursesToBeUpdated.data[i].teachers.length === 0) {
                    teacher = '暂无教师';
                } else {
                    // 将 coursesToBeUpdated.data[i].teachers 里的 teacherName 拼接成字符串
                    teacher = coursesToBeUpdated.data[i].teachers[0].teacherName;
                    for (var j = 1; j < coursesToBeUpdated.data[i].teachers.length; j++) {
                        teacher += '，' + coursesToBeUpdated.data[i].teachers[j].teacherName;
                    }
                }
                this.courses.push({
                    code: coursesToBeUpdated.data[i].courseId,
                    name: coursesToBeUpdated.data[i].courseName,
                    teacher: teacher,
                    details: coursesToBeUpdated.data[i].description
                });
            }
        },
        updateBooks(booksToBeUpdated) {
            console.log(booksToBeUpdated);
            this.books = [];
            for (var i = 0; i < booksToBeUpdated.data.length; i++) {
                this.books.push({
                    code: booksToBeUpdated.data[i].textbookId,
                    title: booksToBeUpdated.data[i].bookTitle,
                    isbn: booksToBeUpdated.data[i].isbn,
                    author: booksToBeUpdated.data[i].author,
                    details: booksToBeUpdated.data[i].bookDescription
                });
            }
            console.log(this.books);
        },
        searchCourses() {
            // 触发课程搜索，filteredCourseResults 会自动更新
            // 向 /api/course/getCourse 发送请求
            var myHeaders = new Headers();
            myHeaders.append("Content-Type", "application/json");

            var raw = JSON.stringify({});
            // 如果 inputvalue 不为空，根据 selectedCourseIndex 的值构造请求体
            if (this.inputvalue !== '') {
                if (this.selectedCourseIndex === 'name') {
                    raw = JSON.stringify({ "courseName": this.inputvalue });
                } else if (this.selectedCourseIndex === 'code') {
                    raw = JSON.stringify({ "courseId": this.inputvalue });
                } else if (this.selectedCourseIndex === 'teacher') {
                    raw = JSON.stringify({ "teacherName": this.inputvalue });
                }
            }

            var requestOptions = {
                method: 'POST',
                headers: myHeaders,
                body: raw,
                redirect: 'follow'
            };

            fetch("/api/course/getCourse", requestOptions)
                .then(response => response.text())
                .then(result => this.updateCourses(JSON.parse(result)))
                .catch(error => console.log('error', error));
        },
        searchBooks() {
            // 触发课本搜索，filteredBookResults 会自动更新
            // 向 /api/book/getTextbook 发送请求
            var myHeaders = new Headers();
            myHeaders.append("Content-Type", "application/json");

            var raw = JSON.stringify({});
            if (this.inputvalue !== '') {
                if (this.selectedBookIndex === 'code') {
                    raw = JSON.stringify({ "textbookId": this.inputvalue });
                } else if (this.selectedBookIndex === 'isbn') {
                    raw = JSON.stringify({ "isbn": this.inputvalue });
                } else if (this.selectedBookIndex === 'title') {
                    raw = JSON.stringify({ "bookTitle": this.inputvalue });
                } else if (this.selectedBookIndex === 'author') {
                    raw = JSON.stringify({ "author": this.inputvalue });
                }
            }

            var requestOptions = {
                method: 'POST',
                headers: myHeaders,
                body: raw,
                redirect: 'follow'
            };

            fetch("/api/Textbook/getTextbook", requestOptions)
                .then(response => response.text())
                .then(result => this.updateBooks(JSON.parse(result)))
                .catch(error => console.log('error', error));
        }
    }
};
</script>

<style scoped src="./info.css"></style>