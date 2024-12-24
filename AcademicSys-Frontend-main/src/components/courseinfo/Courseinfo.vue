<template>
    <el-container direction="vertical">
        <div class="bg-container" id="head-container">
            <el-row :gutter="20">
                <el-col :span="6">
                    <el-input v-model="queryCourse.courseId" placeholder="课程编号" />
                </el-col>
                <el-col :span="6">
                    <el-input v-model="queryCourse.courseName" placeholder="课程名称" />
                </el-col>
                <el-col :span="6">
                    <el-input v-model="queryCourse.courseDepartment" placeholder="开课学院" />
                </el-col>
                <el-col :span="6">
                    <el-input v-model="queryCourse.teacher" placeholder="授课教师" />
                </el-col>
            </el-row>
            <!-- 两个 el-row 之间的空行 -->
            <div style="height: 10px;"></div>
            <el-row :gutter="20">
                <el-col :span="6">
                    <!-- 使用年选择器 -->
                    <el-date-picker v-model="queryCourse.year" class="picker-width" type="year" placeholder="开课学年"
                        format="yyyy" value-format="yyyy">
                    </el-date-picker>
                </el-col>
                <el-col :span="6">
                    <!-- 设置 clear 之后的默认值为 null -->
                    <el-select class="picker-width" v-model="queryCourse.semester" placeholder="开课学期" clearable>
                        <el-option label="春学期" value="春学期"></el-option>
                        <el-option label="夏学期" value="夏学期"></el-option>
                        <el-option label="春夏学期" value="春夏学期"></el-option>
                        <el-option label="短学期" value="短学期"></el-option>
                        <el-option label="秋学期" value="秋学期"></el-option>
                        <el-option label="冬学期" value="冬学期"></el-option>
                        <el-option label="秋冬学期" value="秋冬学期"></el-option>
                    </el-select>
                </el-col>
                <el-col :span="6">
                    <el-button class="querybutton" icon="el-icon-search" @click="search()">查询</el-button>
                </el-col>
                <el-col :span="6">
                    <el-button class="querybutton" @click="addCourseDialog()">添加课程</el-button>
                </el-col>
            </el-row>
        </div>

        <div class="bg-container" id="table-container">
            <el-table :data="courses" max-height="500px">
                <el-table-column prop="courseId" label="课程编号" align="center"></el-table-column>
                <el-table-column prop="courseName" label="课程名称" align="center"></el-table-column>
                <el-table-column prop="courseDepartment" label="开课学院" align="center"></el-table-column>
                <el-table-column prop="displaySemester" label="开课学期" align="center"></el-table-column>
                <el-table-column prop="teacher" label="授课教师" align="center"></el-table-column>
                <el-table-column prop="ta" label="课程助教" align="center"></el-table-column>
                <el-table-column label="操作" width="120px" fixed="right" align="center">
                    <template slot-scope="scope">
                        <div class="editbutton">
                            <el-button class="subbutton" size="mini"
                                @click="editCourseDialog(scope.$index)">编辑课程</el-button>
                        </div>
                        <div style="height: 10px;"></div>
                        <div class="editbutton">
                            <el-button class="subbutton" size="mini"
                                @click="manageTeacherDialog(scope.$index)">管理教师</el-button>
                        </div>
                    </template>
                </el-table-column>
            </el-table>
        </div>

        <el-dialog title="添加课程" :visible.sync="addCourseDialogVisible" width="40%">
            <el-form :model="newCourse">
                <el-row :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="课程编号">
                            <el-input v-model="newCourse.courseId" autocomplete="off"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="课程名称">
                            <el-input v-model="newCourse.courseName" autocomplete="off"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="学分">
                            <el-input v-model="newCourse.credit" autocomplete="off"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="类别">
                            <el-input v-model="newCourse.category" autocomplete="off"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="每周课程日">
                            <el-select v-model="newCourse.courseDay" placeholder="请选择">
                                <el-option label="周一" value="周一"></el-option>
                                <el-option label="周二" value="周二"></el-option>
                                <el-option label="周三" value="周三"></el-option>
                                <el-option label="周四" value="周四"></el-option>
                                <el-option label="周五" value="周五"></el-option>
                                <el-option label="周六" value="周六"></el-option>
                                <el-option label="周日" value="周日"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="课程起止时间">
                            <el-time-picker class="picker-width" is-range v-model="newCourse.courseStartEndTime"
                                format="HH:mm" value-format="HH:mm" range-separator="至" start-placeholder="开始时间"
                                end-placeholder="结束时间">
                            </el-time-picker>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="上课地点">
                            <el-input v-model="newCourse.location" autocomplete="off"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="教室最大学生数">
                            <el-input v-model="newCourse.maxStudents" autocomplete="off"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <!-- 开课学年 -->
                    <el-col :span="12">
                        <el-form-item label="开课学年">
                            <el-date-picker v-model="newCourse.year" class="picker-width" type="year" placeholder="选择年份"
                                format="yyyy" value-format="yyyy">
                            </el-date-picker>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="开课学期">
                            <el-select v-model="newCourse.semester" placeholder="请选择开课学期">
                                <el-option label="春学期" value="春学期"></el-option>
                                <el-option label="夏学期" value="夏学期"></el-option>
                                <el-option label="春夏学期" value="春夏学期"></el-option>
                                <el-option label="短学期" value="短学期"></el-option>
                                <el-option label="秋学期" value="秋学期"></el-option>
                                <el-option label="冬学期" value="冬学期"></el-option>
                                <el-option label="秋冬学期" value="秋冬学期"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="考试开始时间">
                            <!-- 使得宽度和之前的 el-input 一致 -->
                            <el-date-picker class="picker-width" v-model="newCourse.testStartTime"
                                format="yyyy-MM-dd HH:mm" value-format="yyyy-MM-dd HH:mm" type="datetime"
                                placeholder="选择考试开始时间">
                            </el-date-picker>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="考试结束时间">
                            <el-date-picker class="picker-width" v-model="newCourse.testEndTime"
                                format="yyyy-MM-dd HH:mm" value-format="yyyy-MM-dd HH:mm" type="datetime"
                                placeholder="选择考试结束时间">
                            </el-date-picker>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="成绩计算方式">
                            <el-input v-model="newCourse.gradeCal" autocomplete="off"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="面向对象">
                            <el-input v-model="newCourse.target" autocomplete="off"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-form-item label="开课学院">
                        <el-input v-model="newCourse.courseDepartment" autocomplete="off"></el-input>
                    </el-form-item>
                </el-row>
                <el-row>
                    <el-form-item label="课程描述">
                        <el-input v-model="newCourse.description" type="textarea" autocomplete="off"></el-input>
                    </el-form-item>
                </el-row>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="addCourseDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="addCourse()">确 定</el-button>
            </div>
        </el-dialog>

        <el-dialog title="编辑课程" :visible.sync="editDialogVisible" width="40%">
            <el-form :model="edits">
                <el-row :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="课程编号">
                            <el-input v-model="edits.courseId" autocomplete="off" disabled></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="课程名称">
                            <el-input v-model="edits.courseName" autocomplete="off"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="学分">
                            <el-input v-model="edits.credit" autocomplete="off"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="类别">
                            <el-input v-model="edits.category" autocomplete="off"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="每周课程日">
                            <el-select v-model="edits.courseDay" placeholder="请选择">
                                <el-option label="周一" value="周一"></el-option>
                                <el-option label="周二" value="周二"></el-option>
                                <el-option label="周三" value="周三"></el-option>
                                <el-option label="周四" value="周四"></el-option>
                                <el-option label="周五" value="周五"></el-option>
                                <el-option label="周六" value="周六"></el-option>
                                <el-option label="周日" value="周日"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="课程起止时间">
                            <el-time-picker class="picker-width" is-range v-model="edits.courseStartEndTime"
                                format="HH:mm" value-format="HH:mm" range-separator="至" start-placeholder="开始时间"
                                end-placeholder="结束时间">
                            </el-time-picker>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="上课地点">
                            <el-input v-model="edits.location" autocomplete="off"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="教室最大学生数">
                            <el-input v-model="edits.maxStudents" autocomplete="off"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="开课学年">
                            <el-date-picker v-model="edits.year" class="picker-width" type="year" placeholder="选择年份"
                                format="yyyy" value-format="yyyy">
                            </el-date-picker>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="开课学期">
                            <el-select v-model="edits.semester" placeholder="请选择开课学期">
                                <el-option label="春学期" value="春学期"></el-option>
                                <el-option label="夏学期" value="夏学期"></el-option>
                                <el-option label="春夏学期" value="春夏学期"></el-option>
                                <el-option label="短学期" value="短学期"></el-option>
                                <el-option label="秋学期" value="秋学期"></el-option>
                                <el-option label="冬学期" value="冬学期"></el-option>
                                <el-option label="秋冬学期" value="秋冬学期"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="考试开始时间">
                            <el-date-picker class="picker-width" v-model="edits.testStartTime" format="yyyy-MM-dd HH:mm"
                                value-format="yyyy-MM-dd HH:mm" type="datetime" placeholder="选择考试开始时间">
                            </el-date-picker>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="考试结束时间">
                            <el-date-picker class="picker-width" v-model="edits.testEndTime" format="yyyy-MM-dd HH:mm"
                                value-format="yyyy-MM-dd HH:mm" type="datetime" placeholder="选择考试结束时间">
                            </el-date-picker>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="成绩计算方式">
                            <el-input v-model="edits.gradeCal" autocomplete="off"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="面向对象">
                            <el-input v-model="edits.target" autocomplete="off"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-form-item label="开课学院">
                        <el-input v-model="edits.courseDepartment" autocomplete="off"></el-input>
                    </el-form-item>
                </el-row>
                <el-row>
                    <el-form-item label="课程描述">
                        <el-input v-model="edits.description" type="textarea" autocomplete="off"></el-input>
                    </el-form-item>
                </el-row>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button class="left-bottom" type="danger" @click="deleteCourseDialog(editingIndex)">删除</el-button>
                <el-button @click="editDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="editCourse(edits)">确 定</el-button>
            </div>
        </el-dialog>

        <el-dialog title="删除课程" :visible.sync="deleteDialogVisible" width="20%">
            <span>确定删除该课程吗？</span>
            <div slot="footer" class="dialog-footer">
                <el-button @click="deleteDialogVisible = false">取 消</el-button>
                <el-button type="danger" @click="deleteCourse()">确 定</el-button>
            </div>
        </el-dialog>

        <el-dialog title="添加教师" :visible.sync="manageTeacherDialogVisible" width="40%">
            <el-row :gutter="20">
                <el-col :span="12">
                    <el-select v-model="addRole" placeholder="选择添加角色">
                        <el-option label="授课教师" value="teacher"></el-option>
                        <el-option label="课程助教" value="ta"></el-option>
                    </el-select>
                </el-col>
                <el-col :span="12">
                    <el-input v-model="managingTeacherOrTAId" autocomplete="off"></el-input>
                </el-col>
            </el-row>
            <div slot="footer" class="dialog-footer">
                <el-button @click="addTeacher()">添加</el-button>
                <el-button @click="deleteTeacher()">删除</el-button>
                <el-button @click="manageTeacherDialogVisible = false">关闭</el-button>
            </div>
        </el-dialog>
    </el-container>



</template>

<script>
import { Message } from 'element-ui';
export default {
    data() {
        return {
            queryCourse: {
                courseId: null,
                courseName: null,
                courseDepartment: null,
                teacher: null,
                year: null,
                semester: null
            },
            newCourse: {
                courseId: null,
                courseName: null,
                credit: null,
                courseDepartment: null,
                category: null,
                courseDay: null,
                courseStartEndTime: null,
                location: null,
                year: null,
                semester: null,
                maxStudents: null,
                testStartTime: null,
                testEndTime: null,
                gradeCal: null,
                target: null,
                description: null
            },
            edits: {
                courseId: null,
                courseName: null,
                credit: null,
                courseDepartment: null,
                category: null,
                courseDay: null,
                courseStartEndTime: null,
                location: null,
                year: null,
                semester: null,
                maxStudents: null,
                testStartTime: null,
                testEndTime: null,
                gradeCal: null,
                target: null,
                description: null,
                teacherId: null,
                taId: null,
            },
            deletingIndex: null,
            editingIndex: null,
            managingTeacherOrTAId: null,
            courses: [],
            error: null,
            addCourseDialogVisible: false,
            editDialogVisible: false,
            deleteDialogVisible: false,
            manageTeacherDialogVisible: false,
            addRole: 'teacher'
        };
    },
    methods: {
        compeleteSemester(semester) {
            if (semester === '春') {
                return '春学期';
            } else if (semester === '夏') {
                return '夏学期';
            } else if (semester === '春夏') {
                return '春夏学期';
            } else if (semester === '短') {
                return '短学期';
            } else if (semester === '秋') {
                return '秋学期';
            } else if (semester === '冬') {
                return '冬学期';
            } else if (semester === '秋冬') {
                return '秋冬学期';
            }
        },
        simplifySemester(semester) {
            if (semester === '春学期') {
                return '春';
            } else if (semester === '夏学期') {
                return '夏';
            } else if (semester === '春夏学期') {
                return '春夏';
            } else if (semester === '短学期') {
                return '短';
            } else if (semester === '秋学期') {
                return '秋';
            } else if (semester === '冬学期') {
                return '冬';
            } else if (semester === '秋冬学期') {
                return '秋冬';
            }
        },
        updateCourses(coursesToBeUpdated) {
            console.log(coursesToBeUpdated);
            this.courses = [];
            for (var i = 0; i < coursesToBeUpdated.data.length; i++) {
                var teacher = null;
                if (coursesToBeUpdated.data[i].teachers.length === 0) {
                    teacher = '暂无教师';
                } else {
                    // 将 coursesToBeUpdated.data[i].teachers 里的 teacherName 拼接成字符串
                    teacher = coursesToBeUpdated.data[i].teachers[0].teacherName;
                    for (var j = 1; j < coursesToBeUpdated.data[i].teachers.length; j++) {
                        teacher += '，' + coursesToBeUpdated.data[i].teachers[j].teacherName;
                    }
                }
                var ta = null;
                if (coursesToBeUpdated.data[i].tas.length === 0) {
                    ta = '暂无助教';
                } else {
                    // 将 coursesToBeUpdated.data[i].tas 里的 taName 拼接成字符串
                    ta = coursesToBeUpdated.data[i].tas[0].taName;
                    for (var j = 1; j < coursesToBeUpdated.data[i].tas.length; j++) {
                        ta += '，' + coursesToBeUpdated.data[i].tas[j].taName;
                    }
                }
                var year = coursesToBeUpdated.data[i].semester.match(/\d+/)[0];
                var semester = this.compeleteSemester(coursesToBeUpdated.data[i].semester.match(/[^\d]+/)[0]);
                var dateFormat = /^\d{4}-\d{2}-\d{2} \d{2}:\d{2}$/;
                var testStartTime = dateFormat.test(coursesToBeUpdated.data[i].testStartTime) ? coursesToBeUpdated.data[i].testStartTime : null;
                var testEndTime = dateFormat.test(coursesToBeUpdated.data[i].testEndTime) ? coursesToBeUpdated.data[i].testEndTime : null;
                this.courses.push({
                    courseId: coursesToBeUpdated.data[i].courseId,
                    courseName: coursesToBeUpdated.data[i].courseName,
                    credit: coursesToBeUpdated.data[i].credit,
                    category: coursesToBeUpdated.data[i].category,
                    courseDay: coursesToBeUpdated.data[i].courseStartTime.split(' ')[0],
                    courseStartEndTime: [coursesToBeUpdated.data[i].courseStartTime.split(' ')[1], coursesToBeUpdated.data[i].courseEndTime.split(' ')[1]],
                    location: coursesToBeUpdated.data[i].location,
                    year: year,
                    semester: semester,
                    maxStudents: coursesToBeUpdated.data[i].maxStudents,
                    testStartTime: testStartTime,
                    testEndTime: testEndTime,
                    gradeCal: coursesToBeUpdated.data[i].gradeCal,
                    target: coursesToBeUpdated.data[i].target,
                    courseDepartment: coursesToBeUpdated.data[i].courseDepartment,
                    description: coursesToBeUpdated.data[i].description,
                    displaySemester: year + ' ' + semester,
                    teacher: teacher,
                    ta: ta
                });
            }
        },
        search() {
            var myHeaders = new Headers();
            myHeaders.append("Content-Type", "application/json");

            var raw = {};
            // 逐步向 raw 里添加查询条件
            if (this.queryCourse.courseId !== null) {
                raw.courseId = this.queryCourse.courseId;
            }
            if (this.queryCourse.courseName !== null) {
                raw.courseName = this.queryCourse.courseName;
            }
            if (this.queryCourse.courseDepartment !== null) {
                raw.courseDepartment = this.queryCourse.courseDepartment;
            }
            if (this.queryCourse.teacher !== null) {
                raw.teacherName = this.queryCourse.teacher;
            }
            if (this.queryCourse.year !== null && (this.queryCourse.semester !== null && this.queryCourse.semester !== '')) {
                raw.semester = this.queryCourse.year + this.queryCourse.semester;
            } else if (this.queryCourse.year !== null || (this.queryCourse.semester !== null && this.queryCourse.semester !== '')) {
                Message.error('请同时选择学年和学期');
                return;
            }
            raw = JSON.stringify(raw);

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
        addCourseDialog() {
            this.addCourseDialogVisible = true;
        },
        solveAddCourseResult(result) {
            console.log(result);
            if (result.code !== 200) {
                // 显示错误信息
                Message.error(result.msg);
            } else {
                Message.success(result.data);
                // 等待 1s 后关闭对话框
                setTimeout(() => {
                    this.addCourseDialogVisible = false;
                    // 刷新当前搜索结果
                    this.search();
                }, 1000);
            }
        },
        addCourse() {
            if (!this.newCourse.courseId ||
                !this.newCourse.courseName ||
                !this.newCourse.credit ||
                !this.newCourse.courseDepartment ||
                !this.newCourse.category ||
                !this.newCourse.courseDay ||
                !this.newCourse.courseStartEndTime ||
                !this.newCourse.location ||
                !this.newCourse.year ||
                !this.newCourse.semester ||
                !this.newCourse.maxStudents ||
                !this.newCourse.gradeCal ||
                !this.newCourse.target ||
                !this.newCourse.description) {
                Message.error('所有课程信息均为必填项（考查课程可不填考试时间）');
            }

            // 检查考试时间是否合法
            if (edits.testStartTime && edits.testEndTime) {
                if (edits.testStartTime >= edits.testEndTime) {
                    Message.error('考试开始时间应早于结束时间');
                    return;
                }
            } else if (edits.testStartTime || edits.testEndTime) {
                Message.error('请同时填写考试开始时间和结束时间');
                return;
            }
            var myHeaders = new Headers();
            myHeaders.append("Content-Type", "application/json");

            var raw = JSON.stringify({
                "courseId": this.newCourse.courseId,
                "courseName": this.newCourse.courseName,
                "credit": this.newCourse.credit,
                "courseDepartment": this.newCourse.courseDepartment,
                "category": this.newCourse.category,
                "courseStartTime": this.newCourse.courseDay + ' ' + this.newCourse.courseStartEndTime[0],
                "courseEndTime": this.newCourse.courseDay + ' ' + this.newCourse.courseStartEndTime[1],
                "location": this.newCourse.location,
                "semester": this.newCourse.year + this.simplifySemester(this.newCourse.semester),
                "maxStudents": this.newCourse.maxStudents,
                "numStudents": 0,
                "testStartTime": this.newCourse.testStartTime ? this.newCourse.testStartTime : "",
                "testEndTime": this.newCourse.testEndTime ? this.newCourse.testEndTime : "",
                "gradeCal": this.newCourse.gradeCal,
                "target": this.newCourse.target,
                "description": this.newCourse.description
            });

            console.log(raw);

            var requestOptions = {
                method: 'POST',
                headers: myHeaders,
                body: raw,
                redirect: 'follow'
            };

            fetch("/api/course/createCourse", requestOptions)
                .then(response => response.text())
                .then(result => this.solveAddCourseResult(JSON.parse(result)))
                .catch(error => console.log('error', error));
        },
        deleteCourseDialog(index) {
            this.deletingIndex = index;
            this.deleteDialogVisible = true;
        },
        solveDeleteResult(result) {
            console.log(result);
            if (result.code !== 200) {
                // 显示错误信息
                Message.error(result.msg);
            } else {
                Message.success(result.data);
                // 等待 1s 后关闭对话框
                setTimeout(() => {
                    this.deleteDialogVisible = false;
                    this.editDialogVisible = false;
                    // 刷新当前搜索结果
                    this.search();
                }, 1000);
            }
        },
        deleteCourse() {
            var myHeaders = new Headers();
            myHeaders.append("Content-Type", "application/json");

            var raw = JSON.stringify({
                "courseId": this.courses[this.deletingIndex].courseId
            });

            var requestOptions = {
                method: 'POST',
                headers: myHeaders,
                body: raw,
                redirect: 'follow'
            };

            fetch("/api/course/delCourse", requestOptions)
                .then(response => response.text())
                .then(result => this.solveDeleteResult(JSON.parse(result)))
                .catch(error => console.log('error', error));
        },
        editCourseDialog(index) {
            this.edits = { ...this.courses[index] }
            this.editingIndex = index;
            this.editDialogVisible = true;
        },
        solveEditCourseResult(result) {
            console.log(result);
            if (result.code !== 200) {
                // 显示错误信息
                Message.error(result.msg);
            } else {
                Message.success(result.data);
                // 等待 1s 后关闭对话框
                setTimeout(() => {
                    this.editDialogVisible = false;
                    // 刷新当前搜索结果
                    this.search();
                }, 1000);
            }
        },
        editCourse(edits) {
            if (!edits.courseId ||
                !edits.courseName ||
                !edits.credit ||
                !edits.courseDepartment ||
                !edits.category ||
                !edits.courseDay ||
                !edits.courseStartEndTime ||
                !edits.location ||
                !edits.year ||
                !edits.semester ||
                !edits.maxStudents ||
                !edits.gradeCal ||
                !edits.target ||
                !edits.description) {
                Message.error('所有课程信息均为必填项（考查课程可不填考试时间）');
            }

            // 检查考试时间是否合法
            if (edits.testStartTime && edits.testEndTime) {
                if (edits.testStartTime >= edits.testEndTime) {
                    Message.error('考试开始时间应早于结束时间');
                    return;
                }
            } else if (edits.testStartTime || edits.testEndTime) {
                Message.error('请同时填写考试开始时间和结束时间');
                return;
            }

            var myHeaders = new Headers();
            myHeaders.append("Content-Type", "application/json");

            var raw = JSON.stringify({
                "courseId": edits.courseId,
                "courseName": edits.courseName,
                "credit": edits.credit,
                "courseDepartment": edits.courseDepartment,
                "category": edits.category,
                "courseStartTime": edits.courseDay + ' ' + edits.courseStartEndTime[0],
                "courseEndTime": edits.courseDay + ' ' + edits.courseStartEndTime[1],
                "location": edits.location,
                "semester": edits.year + this.simplifySemester(edits.semester),
                "maxStudents": edits.maxStudents,
                "numStudents": 0,
                "testStartTime": edits.testStartTime ? edits.testStartTime : "",
                "testEndTime": edits.testEndTime ? edits.testEndTime : "",
                "gradeCal": edits.gradeCal,
                "target": edits.target,
                "description": edits.description
            });

            var requestOptions = {
                method: 'POST',
                headers: myHeaders,
                body: raw,
                redirect: 'follow'
            };

            fetch("/api/course/modCourse", requestOptions)
                .then(response => response.text())
                .then(result => this.solveEditCourseResult(JSON.parse(result)))
                .catch(error => console.log('error', error));
        },
        manageTeacherDialog(index) {
            this.editingIndex = index;
            this.manageTeacherDialogVisible = true;
        },
        solveAddTeacherResult(result) {
            console.log(result);
            if (result.code !== 200) {
                // 显示错误信息
                Message.error(result.msg);
            } else {
                Message.success(result.data);
                this.search();
            }
        },
        addTeacher() {
            var myHeaders = new Headers();
            myHeaders.append("Content-Type", "application/json");

            var raw = {
                "courseId": this.courses[this.editingIndex].courseId
            };
            if (this.addRole === 'teacher') {
                raw.teacherId = this.managingTeacherOrTAId;
            } else if (this.addRole === 'ta') {
                raw.taId = this.managingTeacherOrTAId;
            } else {
                Message.error('请选择添加角色');
                return;
            }
            raw = JSON.stringify(raw);

            var requestOptions = {
                method: 'POST',
                headers: myHeaders,
                body: raw,
                redirect: 'follow'
            };

            var fetchUrl = '/api/course/';
            if (this.addRole === 'teacher') {
                fetchUrl += 'addTeacher';
            } else if (this.addRole === 'ta') {
                fetchUrl += 'addTA';
            }

            fetch(fetchUrl, requestOptions)
                .then(response => response.text())
                .then(result => this.solveAddTeacherResult(JSON.parse(result)))
                .catch(error => console.log('error', error));
        },
        solveDeleteTeacherResult(result) {
            console.log(result);
            if (result.code !== 200) {
                // 显示错误信息
                Message.error(result.msg);
            } else {
                Message.success(result.data);
                this.search();
            }
        },
        deleteTeacher() {
            var myHeaders = new Headers();
            myHeaders.append("Content-Type", "application/json");

            var raw = {
                "courseId": this.courses[this.editingIndex].courseId
            };
            if (this.addRole === 'teacher') {
                raw.teacherId = this.managingTeacherOrTAId;
            } else if (this.addRole === 'ta') {
                raw.taId = this.managingTeacherOrTAId;
            } else {
                Message.error('请选择添加角色');
                return;
            }
            raw = JSON.stringify(raw);

            var requestOptions = {
                method: 'POST',
                headers: myHeaders,
                body: raw,
                redirect: 'follow'
            };

            var fetchUrl = '/api/course/';
            if (this.addRole === 'teacher') {
                fetchUrl += 'delTeacher';
            } else if (this.addRole === 'ta') {
                fetchUrl += 'delTA';
            }

            fetch(fetchUrl, requestOptions)
                .then(response => response.text())
                .then(result => this.solveDeleteTeacherResult(JSON.parse(result)))
                .catch(error => console.log('error', error));
        }
    }
};
</script>

<style scoped src="./info.css"></style>