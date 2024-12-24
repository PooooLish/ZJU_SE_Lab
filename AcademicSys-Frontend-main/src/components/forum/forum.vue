
<template>
  <div style="background-color: rgba(242,243,245,0);">
  <el-main>
    <el-row>

    <el-col :span="18">
      <el-row :gutter="20" >
          <el-col :span="6">
            <el-card :body-style="{ padding: '0px' }" style="background-color: rgba(139,179,239,0.06)" >
<!--              <el-icon class="el-icon-star-on"></el-icon>-->
              <el-image :src="require('../../assets/view02.png')" class="image" :fit='fill'></el-image>
              <div style="padding: 8px;">
                <span>置顶帖</span>
                <div class="bottom clearfix">
                  <time class="time">{{ currentDate }}</time>
                  <el-button type="text" @click="topPosts('tab2')" class="button">点击查看</el-button>
                </div>
              </div>
            </el-card>
          </el-col>
        <el-col :span="6">
          <el-card :body-style="{ padding: '0px' }" style="background-color: rgba(139,179,239,0.06)" >
            <el-image :src="require('../../assets/view04.jpeg')" class="image"></el-image>
            <div style="padding: 8px;">
              <span>热门教师</span>
              <div class="bottom clearfix">
                <time class="time">{{ currentDate }}</time><el-menu-item index="/forum">
                <template #title>学生入口</template>
              </el-menu-item>
                <el-button type="text" @click="popularTeacher('hotTeacher')" class="button">点击查看</el-button>
              </div>
            </div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card :body-style="{ padding: '0px' }" style="margin-bottom:20px; background-color: rgba(139,179,239,0.06)" >
            <el-image :src="require('../../assets/view03.png')" class="image"></el-image>
            <div style="padding: 8px;">
              <span>热门课程</span>
              <div class="bottom clearfix">
                <time class="time">{{ currentDate }}</time>
                <el-button type="text" @click="popularCourse('hotCourse')" class="button">点击查看</el-button>
              </div>
            </div>
          </el-card>
        </el-col>
        <el-col :span="6" >
          <el-card :body-style="{ padding: '0px' }" style="background-color: rgba(139,179,239,0.06)" >
            <el-image :src="require('../../assets/sea.jpg')" class="image"></el-image>
            <div style="padding: 8px;">
              <span>最新帖子</span>
              <div class="bottom clearfix">
                <time class="time">{{ currentDate }}</time>
                <el-button type="text" @click="loadAllPosts('tab0')" class="button">点击查看</el-button>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
      <el-row :span="10">
        <p style="color: #545c64" class="el-icon-chat-line-round">搜索帖子</p>
        <el-card style=" margin-bottom: 20px; background-color: rgba(139,179,239,0.06); flex: none">
          <p style="font-size: small; color:  #545c64">教师检索</p><forumSearchBar :priv="false" @onSearch="searchResultTeacher" ref="searchBar"></forumSearchBar>
          <p style="font-size: small; color:  #545c64" >课程检索</p><forumSearchBar :priv="false" @onSearch="searchResultCourse" ref="searchBarcourse"></forumSearchBar>
        </el-card>
          <!--        发帖-->
        <p style="color: #545c64" class="el-icon-edit">发布帖子</p>
        <el-card style="margin-bottom: 20px; background-color: rgba(139,179,239,0.06)" >
          <div class="poststyle">

<!--            grade -->
            <el-form>
<!--              @submit.prevent="addPost"-->
<!--              <el-form-item>-->
                <el-autocomplete
                    class="inline-input full-width"
                    v-model="newcourse"
                    :fetch-suggestions="querySearchCourse"
                    placeholder="请输入课程名称"
                    @select="handleSelect"
                    ref="courseAuto"
                ></el-autocomplete>
              <el-form-item></el-form-item>
<!--              </el-form-item>-->
<!--              <el-form-item>-->
                <el-autocomplete
                    class="inline-input full-width"
                    v-model="newteacher"
                    :fetch-suggestions="querySearchTeacher"
                    placeholder="请输入教师名称"
                    @select="handleSelect"
                ></el-autocomplete>
<!--              </el-form-item>-->
<!--              <el-form-item >-->
<!--              <el-form-item></el-form-item>-->
<!--                <el-row>-->
<!--                <span slot="label">评分</span>-->
<!--                </el-row>-->
<!--              <el-form-item></el-form-item>-->
                <el-row>
                <el-slider
                    v-model="newgrade"
                    :min="0" :max="10"
                    :step="1"
                    >
                </el-slider>
                </el-row>
<!--              </el-form-item>-->
<!--              <el-form-item>-->
                <el-input class="full-width" style="width: 100%" type="textarea" autosize v-model="newcontent" placeholder="在这里，一切都是匿名的..." required></el-input>
<!--              </el-form-item>-->
<!--              <el-form-item>-->
              <el-form-item></el-form-item>
                <el-button type="primary" size="mini" @click="postPub" icon="el-icon-circle-plus-outline">Post</el-button>
<!--              </el-form-item>-->
            </el-form>
          </div>
        </el-card>
      </el-row>
<!--      热门教师-->
      <el-card v-show="currentTab === 'hotTeacher'" v-for="(teacher, index) in teachers" :key="index" class="posts" style="background-color: rgba(139,239,186,0.06)" >
        <el-row :span="18">
          <p>{{ teacher.teacher_name }}</p>
          <el-button type="text" @click="showThisTeacher('showTeacher', teacher.teacher_name)" class="button">查看详情</el-button>
        </el-row>
      </el-card>
      <el-card v-show="currentTab === 'showTeacher'" v-for="(post, index) in hotteacherposts" :key="index" class="posts" style="background-color: rgba(139,239,186,0.06)">
        <el-row :span="18">
          <p>{{ post.content }}</p>
        </el-row>
        <el-row :span="6">
          <el-col :span="20">
            <div class="tight-text">
<!--              <p class = "tight-text" style="color: rgb(37,124,210); font-size: small; ">{{ post.post_time }}</p>-->
              <p class = "tight-text" style="color: rgba(84,92,100,0.86); font-size: small; ">{{ post.post_time }}</p>
              <p class = "tight-text" style="color: #545c64; font-size: small">{{ post.course }}</p>
              <p class = "tight-text" style="color: #545c64; font-size: small">{{ post.teacher }}</p>
              <p class = "tight-text" style="color: #545c64; font-size: small">{{ post.grade }}</p>
            </div>
          </el-col>
      <el-col :span="4" class="button-column">
      <el-button  size="small" round @click="handleUp(post)">
        <img :src="post.liked ? require('../../assets/dianzan.png') : require('../../assets/dianzan-2.png')" alt="点赞图标" style="width: 20px; height: 20px">
        {{ post.like_count }}
      </el-button>
      <el-button size="small" round @click="handleDown(post)">
        <img :src="post.disliked ? require('../../assets/diancai.png') : require('../../assets/diancai-3.png')" alt="图标" style="width: 20px; height: 20px">
        {{ post.dislike_count}}
      </el-button>
      <el-button size="small" round @click="handleFavor(post)">
        <img :src="post.favored ? require('../../assets/shoucang.png') : require('../../assets/shoucang-2.png')" alt="图标" style="width: 20px; height: 20px">
        {{ post.favor_count }}
      </el-button>
    </el-col>
        </el-row>
      </el-card>
<!--      精华-->
<!--      <el-card v-show="currentTab === 'tab2'">精华</el-card>-->
      <el-card v-show="currentTab === 'tab2'" v-for="(post, index) in topposts" :key="index" class="posts" style="background-color: rgba(239,219,139,0.09)">
        <el-row :span="18">
          <p>{{ post.content }}</p>
        </el-row>
        <el-row :span="6">
          <el-col :span="20">
            <div class="tight-text">
              <p class = "tight-text" style="color: rgba(84,92,100,0.86); font-size: small; ">{{ post.post_time }}</p>
              <p class = "tight-text" style="color: #545c64; font-size: small">{{ post.course }}</p>
              <p class = "tight-text" style="color: #545c64; font-size: small">{{ post.teacher }}</p>
            </div>
          </el-col>
          <el-col :span="4" class="button-column">
            <el-button  size="small" round @click="handleUp(post)">
              <img :src="post.liked ? require('../../assets/dianzan.png') : require('../../assets/dianzan-2.png')" alt="点赞图标" style="width: 20px; height: 20px">
              {{ post.like_count }}
            </el-button>
            <el-button size="small" round @click="handleDown(post)">
              <img :src="post.disliked ? require('../../assets/diancai.png') : require('../../assets/diancai-3.png')" alt="图标" style="width: 20px; height: 20px">
              {{ post.dislike_count}}
            </el-button>
            <el-button size="small" round @click="handleFavor(post)">
              <img :src="post.favored ? require('../../assets/shoucang.png') : require('../../assets/shoucang-2.png')" alt="图标" style="width: 20px; height: 20px">
              {{ post.favor_count }}
            </el-button>
          </el-col>
        </el-row>
      </el-card>
<!--      热门课程-->
      <el-card v-show="currentTab === 'hotCourse'" v-for="(course, index) in courses" :key="index" class="posts" style="background-color: rgba(239,139,169,0.06)">
        <el-row :span="18">
          <p>{{ course.course_name }}</p>
          <el-button type="text" @click="showThisCourse('showCourse', course.course_name)" class="button">查看详情</el-button>
        </el-row>
      </el-card>
      <el-card v-show="currentTab === 'showCourse'" v-for="(post, index) in hotcourseposts" :key="index" class="posts" style="background-color: rgba(239,139,169,0.06)" >
        <el-row :span="18">
          <p>{{ post.content }}</p>
        </el-row>
        <el-row :span="6">
          <el-col :span="20">
            <div class="tight-text">
              <p class = "tight-text" style="color: rgba(84,92,100,0.86); font-size: small; ">{{ post.post_time }}</p>
              <p class = "tight-text" style="color: #545c64; font-size: small">{{ post.course }}</p>
              <p class = "tight-text" style="color: #545c64; font-size: small">{{ post.teacher }}</p>
            </div>
          </el-col>
          <el-col :span="4" class="button-column">
            <el-button  size="small" round @click="handleUp(post)">
              <img :src="post.liked ? require('../../assets/dianzan.png') : require('../../assets/dianzan-2.png')" alt="点赞图标" style="width: 20px; height: 20px">
              {{ post.like_count }}
            </el-button>
            <el-button size="small" round @click="handleDown(post)">
              <img :src="post.disliked ? require('../../assets/diancai.png') : require('../../assets/diancai-3.png')" alt="图标" style="width: 20px; height: 20px">
              {{ post.dislike_count}}
            </el-button>
            <el-button size="small" round @click="handleFavor(post)">
              <img :src="post.favored ? require('../../assets/shoucang.png') : require('../../assets/shoucang-2.png')" alt="图标" style="width: 20px; height: 20px">
              {{ post.favor_count }}
            </el-button>
          </el-col>
        </el-row>
      </el-card>

      <el-card v-show="currentTab === 'tab0'" v-for="(post, index) in allposts" :key="index" class="posts" :style="getPostStyle(post)">
        <el-row :span="18">
          <p>{{ post.content }}</p>
        </el-row>
        <el-row :span="6">
          <el-col :span="20">
            <div class="tight-text">
              <p class = "tight-text" style="color: rgba(84,92,100,0.86); font-size: small; ">{{ post.post_time }}</p>
              <p class = "tight-text" style="color: #545c64; font-size: small">{{ post.course }}</p>
              <p class = "tight-text" style="color: #545c64; font-size: small">{{ post.teacher }}</p>
            </div>
          </el-col>
          <el-col :span="4" class="button-column">
            <el-button  size="small" round @click="handleUp(post)">
              <img :src="post.liked ? require('../../assets/dianzan.png') : require('../../assets/dianzan-2.png')" alt="点赞图标" style="width: 20px; height: 20px">
              {{ post.like_count }}
            </el-button>
            <el-button size="small" round @click="handleDown(post)">
              <img :src="post.disliked ? require('../../assets/diancai.png') : require('../../assets/diancai-3.png')" alt="图标" style="width: 20px; height: 20px">
              {{ post.dislike_count}}
            </el-button>
            <el-button size="small" round @click="handleFavor(post)">
              <img :src="post.favored ? require('../../assets/shoucang.png') : require('../../assets/shoucang-2.png')" alt="图标" style="width: 20px; height: 20px">
              {{ post.favor_count }}
            </el-button>
          </el-col>
        </el-row>
      </el-card>
    </el-col>
    <el-col :span="6">
<!--      <span style="font-family: 微软雅黑; color: #545c64; font-size: small">总帖数：{{this.allposts.length}}</span>-->
      <header-bar></header-bar>
    </el-col>
    </el-row>

  </el-main>
    <el-backtop target=".page-component__scroll .el-scrollbar__wrap"></el-backtop>
  </div>
</template>
<script>
import axios from 'axios';
import Sidebar from "@/components/forum/sidebar.vue";
import HeaderBar from "@/components/forum/headerBar.vue";
import SearchBar from "@/components/forum/manager/searchBar.vue";
import forumSearchBar from "@/components/forum/forumSearchBar.vue";
// 这些是从后端拿的
export default {
  //DONE 点赞点踩变色以及与后端交互
  components: {forumSearchBar, HeaderBar, Sidebar},
  data() {
    return {
      currentTab: 'tab0',
      newcourse: '',
      newteacher: '',
      newcontent: '',
      newgrade: '',
      courses: this.loadAllCourses,
      teachers: this.loadAllTeachers,
      searchInfoTeacher: '',
      fits: 'cover',
      url: 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg',
      selectedinfo:{
        // 老师或者课程
        name: "邹强",
        course: "软件工程" // 这里是个列表
      },
      // 按时间排序
      topposts:[],
      allposts:[],
      hotteacherposts: [],
      hotcourseposts: [],
      newPost: {
        postId: 0,
        content: '',
        course: '',
        teacher: '',
        grade: 0,
        like_count: 0,
        dislike_count: 0,
        favor_count: 0,
        post_time: new Date().toLocaleString(),
        is_top: '',
        liked: false,
        disliked: false,
        favored: false
      },
    }
  },
  computed: {
    url() {
      return url
    }
  },
  mounted: function () {
    this.loadAllPosts('tab0')
    this.loadAllCourses()
    this.loadAllTeachers()
  },
  methods: {
    getPostStyle(post) {
      return {
        backgroundColor: post.is_top === 1 ? 'rgba(239,219,139,0.07)' : 'rgba(139,179,239,0.08)'
      };
    },
    likeIcon(post) {
      // 根据liked变量的状态返回不同的图标路径
      return post.liked ? require('../../assets/dianzan-2.png') : require('../../assets/dianzan.png');
    },
    loadAllPosts (tabName) {
      this.currentTab=tabName
      var _this = this
      // 这里返回按时间排序的帖子
      axios.get('/forum/getAll')
          .then(resp => {
            if (resp && resp.status === 200) {
              _this.allposts = resp.data
              this.totalPosts = this.allposts.length
            }
          })
    },
    loadAllCourses () {
      var _this = this
      // 这里返回按时间排序的帖子
      axios.get('/forum/allcourse', {
      }).then(resp => {
        if (resp && resp.status === 200) {
          // _this.posts = resp.data
          _this.courses = resp.data
          console.log("return data for course:")
          console.log(_this.courses)
          // _this.pagedposts = resp.data
          // _this.handleCurrentChange(this.currentPage)
        }
      })

    },
    loadAllTeachers () {
      var _this = this
      axios.get('/forum/allteacher', {
      }).then(resp => {
        if (resp && resp.status === 200) {
          // _this.posts = resp.data
          _this.teachers = resp.data
          console.log("return data for teacher:")
          console.log(_this.teachers)
          // _this.pagedposts = resp.data
          // _this.handleCurrentChange(this.currentPage)
        }
      })
    },
    // TODO 根据教师name返回相应帖子
    showThisTeacher (tabName, name) {
      this.currentTab = tabName
      // 这里返回按时间排序的帖子
      console.log("step1")
      console.log(name)
      axios.get('/forum/getpostbyteacher', { params: { teacher: name } })
          .then(resp => {
            if (resp && resp.status === 200) {
              this.hotteacherposts = resp.data
              console.log("get post by teacher")
              this.totalPosts = this.allposts.length
            }
          })
          .catch(error => {
            console.error("Error fetching posts by teacher:", error);
          });
    },
    showThisCourse (tabName, name) {
      this.currentTab = tabName
      var _this = this
      // 这里返回按时间排序的帖子
      axios.get('/forum/getpostbycourse' , { params: { course: name } })
          .then(resp => {
            if (resp && resp.status === 200) {
              _this.hotcourseposts = resp.data
              this.totalPosts = this.allposts.length
            }
          })
          .catch(error => {
            console.error("Error fetching posts by teacher:", error);
          });
    },
    formatDate(dateString) {
      return moment(dateString).format('YYYY-MM-DD');
    },
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    },
    handleUp(post) {
      var dir = post.liked ? "down" : "up";
      this.likeIcon(post);
      post.liked = !post.liked;
      axios.post('/forum/operation', { postId: post.postId, direction: dir, operation: "like"})
          .then(response => {
            if(dir == "up") {
              // this.loadAllPosts("tab0")
              post.like_count = response.data.like_count
              post.liked = response.data.liked;
              dir= post.liked ? "down" : "up";
            }else {
              // this.loadAllPosts("tab0")
              post.like_count = response.data.like_count;
              post.liked = response.data.liked;
              dir= post.liked ? "down" : "up";
            }
          })
          .catch(error => {
            console.error("There was an error upgrade the post!", error);
          });
      // this.sendToBackend('up', post.like_count, postId);
    },
    handleDown(post) {
      var dir = post.disliked ? "down" : "up";
      this.likeIcon(post);
      post.disliked = !post.disliked;
      axios.post('/forum/operation', {postId: post.postId, direction: dir, operation: "dislike"})
          .then(response => {
            if(dir == "up") {
              post.dislike_count = response.data.dislike_count;
              post.disliked = response.data.disliked;
              dir= post.disliked ? "down" : "up";
            }else {
              post.dislike_count = response.data.dislike_count;
              post.disliked = response.data.disliked;
              dir= post.disliked ? "down" : "up";
            }
          })
          .catch(error => {
            console.error("There was an error upgrade the post!", error);
          });
    },
    handleFavor(post) {
      var dir = post.favored ? "down" : "up";
      this.likeIcon(post);
      post.favored = !post.favored;
      axios.post('/forum/operation', {postId: post.postId, direction: dir, operation: "favor"})
          .then(response => {
            if(dir == "up") {
              post.favor_count = response.data.favor_count;
              post.favored = response.data.favored;
              dir= post.favored ? "down" : "up";
            }else {
              post.favor_count = response.data.favor_count;
              post.favored = response.data.favored;
              dir= post.favored ? "down" : "up";
            }
          })
          .catch(error => {
            console.error("There was an error upgrade the post!", error);
          });
    },
    sendToBackend(key, favor, id) {
      const url = "/post";
      const data = {
        [key]: favor,
        id: id
      };
  // operation direction "up" "down"  post_id "like" "dislike" "favor"
      axios.post(url, data)
          .then(response => {
            console.log('Response:', response);
          })
          .catch(error => {
            console.log('Error:', error);
          });
      var _this = this
      // 这里返回按时间排序的帖子
      axios.get('/date')
          .then(resp => {
            if (resp && resp.status === 200) {
              _this.allposts = resp.data
              this.totalPosts = this.allposts.length
            }
          })
    },
    // TODO 发帖测试
    /*
    newPost: {
        postId: 0,
        content: '',
        course: '',
        teacher: '',
        grade: 0,
        like_count: 0,
        dislike_count: 0,
        favor_count: 0,
        post_time: new Date().toLocaleString(),
        is_top: '',
        liked: false,
        disliked: false,
        favored: false
      },
    * */
    postPub() {
      var _this = this
      // 这里向后端返回帖子
      this.newPost.course = this.newcourse
      this.newPost.teacher = this.newteacher
      this.newPost.content = this.newcontent
      this.newPost.grade = this.newgrade
      axios.post('/forum/post', {content: this.newcontent, course: this.newcourse, teacher: this.newteacher, grade: this.newgrade})
          .then(resp => {
            if(resp.data == "ok"){

            }
            this.loadAllPosts()
          })
    },
    searchResultTeacher () {
      var _this = this
      console.log(this.$refs.searchBar.keywords)
      axios.get('/forum/search?keyword=' + this.$refs.searchBar.keywords, {params: {type: "teacher"}
          }).then(resp => {
        if (resp && resp.status === 200) {
          _this.allposts = resp.data
          _this.pagedposts = resp.data
        }
      })
    },
    searchResultCourse () {
      var _this = this
      console.log(this.$refs.searchBarcourse.keywords)
      axios.get('/forum/search?keyword=' + this.$refs.searchBarcourse.keywords, {params: {type: "course"}
      }).then(resp => {
        if (resp && resp.status === 200) {
          _this.allposts = resp.data
          _this.pagedposts = resp.data
        }
      })
    },
    searchAllCourse(){
      var _this = this
      console.log(this.$refs.searchBarcourse.keywords)
      axios.get('/forum/allcourse' + this.$refs.searchBarcourse.keywords, {
      }).then(resp => {
        if (resp && resp.status === 200) {
          _this.posts = resp.data
          _this.pagedposts = resp.data
          // _this.handleCurrentChange(this.currentPage)
        }
      })
    },
    createFilter(queryString) {
      return (course) => {
        return (course.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
      };
    },
    createFilterTeacher(queryString) {
      return (teacher) => {
        return (teacher.teacher_name.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
      };
    },
    querySearchCourse(queryString, cb) {
      // this.searchAllCourse()
      var _this = this
      // axios.get('/forum/allcourse', {
      // }).then(resp => {
      //   if (resp && resp.status === 200) {
      //     // _this.posts = resp.data
      //     _this.courses = resp.data
      //     console.log("return data for course:")
      //     console.log(_this.courses)
      //     // _this.pagedposts = resp.data
      //     // _this.handleCurrentChange(this.currentPage)
      //   }
      // })
      var courses = this.courses;
      var results = queryString ? courses.filter(this.createFilter(queryString)) : courses;
      // 调用 callback 返回建议列表的数据
      cb(results);
    },
    querySearchTeacher(queryString, cb) {
      var _this = this

      var teachers = this.teachers;
      var results = queryString ? teachers.filter(this.createFilterTeacher(queryString)) : teachers;
      // 调用 callback 返回建议列表的数据
      cb(results);
    },
    addPost() {
      axios.post('/add', this.newPost)
          .then(response => {
            this.posts.push(response.data);
            this.newPost.course = '';
            this.newPost.content = '';
            this.newPost.teacher = '';
            this.newPost.up = 0;
            this.newPost.down = 0;
            this.newPost.favor = 0;
          })
          .catch(error => {
            console.error("There was an error adding the post!", error);
          });
    },
    // 精华置顶从这里拿
    topPosts(tabName){
      this.currentTab = tabName;
      var _this = this
      // 这里返回所有帖子
      axios.get('/forum/top', {
        params: {
          page: this.currentPage,
          pagesize: this.pagesize
        }})
          .then(resp => {
            if (resp && resp.status === 200) {
              _this.topposts = resp.data
              this.totalPosts = this.allposts.length
              console.log(resp.data)
              // this.handleCurrentChange(this.currentPage)
            }
          })
    },
    popularTeacher(tabName) {
      this.currentTab = tabName;
      console.log(this.currentTab)
      var _this = this
      // 这里返回所有帖子
      axios.get('/forum/hotTeacher')
          .then(resp => {
            if (resp && resp.status === 200) {
              _this.teachers = resp.data
              // this.totalPosts = this.allposts.length
              // this.teacher = this.allposts[0].teacher
              // this.handleCurrentChange(this.currentPage)
            }
          })
    },
    popularCourse(tabName){
      this.currentTab = tabName;
      var _this = this
      // 这里返回所有帖子
      axios.get('/forum/hotCourse', {
        params: {
          page: this.currentPage,
          pagesize: this.pagesize
        }})
          .then(resp => {
            if (resp && resp.status === 200) {
              _this.courses = resp.data
              this.totalPosts = this.allposts.length
              this.teacher = this.allposts[0].teacher
              // this.handleCurrentChange(this.currentPage)
            }
          })
    },
    handleSelect(item) {
      console.log(item);
    }
  }

}
</script>

<style>
.sidebar {
  flex: 0 0 200px; /* 设置侧边栏宽度为固定值，不随内容而变化 */
  background-color: #f0f2f5;
}
.side-menu {
  background-color: rgb(17, 46, 77);
  text-color: #fff;
  active-text-color: #ffd04b;
}
.custom-col {
  background-color: #f0f2f5; /* 设置背景色 */
}
.reply-item {
  color: #ff0000; /* 设置文本颜色 */
}
.centered-text {
  text-align: left; /* 设置文本居中 */
  font-size: 40px; /* 设置大字的字体大小 */
  color: #0f2d4b;
  font-family: "微软雅黑";
}
.info-text{
  text-align: left;
  font-size: 15px; /* 设置大字的字体大小 */
  color: rgba(12, 13, 16, 0.48);
  font-family: "微软雅黑";
}
.button-column {
  display: flex;
  flex-direction: row;
  justify-content: flex-end;
}
.posts {
  //display: flex;
  //flex-direction: row;
  margin-bottom: 20px;
}
.section {
  margin-bottom: 20px;
  margin-top: 10px;
}
.image{
  height: 200px;
}
.poststyle{
  display: grid;
  align-items: center;
  margin-left: 10px;
  color: white;
  align-self: flex-end;
  border: #545c64;
}
.full-width {
  width: 100%;
}
</style>