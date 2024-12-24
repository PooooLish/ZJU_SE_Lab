<template>
  <div>
    <el-main>
    <el-row style="height: 840px;">
      <el-menu
          :default-active="$route.path"
          router
          mode="horizontal"
          background-color="#112e4d"
          text-color="#fff"
          active-text-color="blue"
          style="min-width: 1300px; align-items: center; display: flex"
      >
<!--        <el-menu-item v-for="(item,i) in navList" :key="i" :index="item.name">-->
<!--          {{ item.navItem }}-->
<!--        </el-menu-item>-->
        <!--    <a href="#nowhere" style="color: #ffffff;float: right;padding: 20px;">More</a>-->
        <!--    <i class="el-icon-menu" style="float:right;font-size: 45px;color: #ffffff;padding-top: 8px"></i>-->
        <span style="position: absolute;right: 43%;color:#fff;font-size: 20px;font-weight: bold">
    </span>
        <el-avatar src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"  style="margin-left: auto; margin-right: 10px"></el-avatar>
      </el-menu>
      <search-bar style="margin-top: 10px" :priv="true" @type="teacher"  @onSearch="searchResultTeacher" @onWord="searchWord" ref="searchBar"></search-bar>
      <search-bar style="margin-top: 10px" :priv="true" @type="course"  @onSearch="searchResultCourse" @onWord="searchWord" ref="searchBarcourse"></search-bar>
      <el-card>
      <el-table :data="pagedPosts"  stripe border @selection-change="handleSelectionChange">
        <el-table-column
            type="selection"
            width="55"
        >
        </el-table-column>
        <el-table-column prop="postId" label="Id" :width="calcColumnWidth(6)">
        </el-table-column>
        <el-table-column prop="content" label="内容" :width="calcColumnWidth(40)">
        </el-table-column>
        <el-table-column prop="course" label="课程" :width="calcColumnWidth(10)">
        </el-table-column>
        <el-table-column prop="teacher" label="教师" :width="calcColumnWidth(10)">
        </el-table-column>
        <el-table-column prop="grade" label="评分" :width="calcColumnWidth(10)">
        </el-table-column>
        <el-table-column
            fixed="right"
            label="Operation"
            width="200">
          <template slot="header" slot-scope="scope">
            <el-button @click="batchDelete" type="danger" size="mini">Delete</el-button>
          </template>
          <template slot-scope="scope">
            <el-button @click="topPost(scope.row.postId)" type="text" size="small">置顶</el-button>
            <el-button @click="cancelTopPost(scope.row.postId)" type="text" size="small">取消置顶</el-button>
            <el-button @click="deletePost(scope.row.postId)" type="text" size="small">删除</el-button>
<!--            <el-button @click="editDevice(scope.row)" type="text" size="small">edit</el-button>-->
          </template>
        </el-table-column>
      </el-table>
      </el-card>
    </el-row>
    <el-row>
      <el-pagination
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-size="pagesize"
          :total="totalPosts"
      >
        <!--        :page-size="pagesize">-->
      </el-pagination>
    </el-row>
    </el-main>
  </div>
</template>

<script>
import SearchBar from './searchBar'
import axios from "axios";
export default {
  name: 'posts',
  components: {SearchBar},
  data () {
    return {
      posts: [],
      pagedPosts: [],
      selectedPosts: [],
      currentPage: 1,
      pagesize: 10,
      totalPosts: 0,
      editMode: false
    }
  },
  mounted: function () {
    this.loadPosts()
  },
  methods: {
    loadPosts () {
      var _this = this
      // 这里返回所有帖子
      axios.get('/forum/manager/getAll',
        //   {
        // params: {
        //   page: this.currentPage,
        //   pagesize: this.pagesize
        // }}
      )
          .then(resp => {
            if (resp && resp.status === 200) {
              _this.posts = resp.data
              this.totalPosts = this.posts.length
              this.handleCurrentChange(this.currentPage)
            }
          })
    },
    handleCurrentChange (currentPage) {
      const startIndex = (currentPage - 1) * this.pagesize
      const endIndex = startIndex + this.pagesize
      this.pagedPosts = this.posts.slice(startIndex, endIndex)
    },
    handleSelectionChange (val) {
      this.selectedposts = val
    },
    batchDelete () {
      const selectedIds = this.selectedposts.map(post => post.postId)
      console.log('Selected IDs:', selectedIds)
      this.$confirm('确定要删除这些帖子吗?', 'Warning', {
        confirmButtonText: 'Yes',
        cancelButtonText: 'No',
        type: 'warning'
      }).then(() => {
        axios.post('/forum/manager/post/batch-delete', {ids: selectedIds}).then(resp => {
          if (resp && resp.status === 200) {
            this.loadPosts()
            this.$message.success('Successfully delete！')
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: 'cancel deletion'
        })
      })
    },
    calcColumnWidth (percent) {
      // Calculate the column width based on the percentage and the window width
      const windowWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth
      return (percent / 100) * windowWidth
    },
    // handleCurrentChange: function (currentPage) {
    //   this.currentPage = currentPage
    //   console.log(this.currentPage)
    // },
    searchResultTeacher () {
      var _this = this
      console.log(this.$refs.searchBar.keywords)
      axios.get('/forum/search?keyword=' + this.$refs.searchBar.keywords, {params: {type: "teacher"}
      }).then(resp => {
        if (resp && resp.status === 200) {
          _this.posts = resp.data
          _this.pagedposts = resp.data
          _this.handleCurrentChange(this.currentPage)
        }
      })
    },
    searchResultCourse () {
      var _this = this
      console.log(this.$refs.searchBarcourse.keywords)
      axios.get('/forum/search?keyword=' + this.$refs.searchBarcourse.keywords, {params: {type: "course"}
      }).then(resp => {
        if (resp && resp.status === 200) {
          _this.posts = resp.data
          _this.pagedposts = resp.data
          _this.handleCurrentChange(this.currentPage)
        }
      })
    },
    searchWord () {
      var _this = this
      // 这里不确定怎么加敏感词
      axios.get('/forum/manager/sensitivePosts'
      //axios.get('/search?keywords=' + this.$refs.searchBar.keywords, {
      ).then(resp => {
        if (resp && resp.status === 200) {
          _this.posts = resp.data
          _this.pagedposts = resp.data
          _this.handleCurrentChange(this.currentPage)
        }
      })
    },
    // "delete", "top", "cancel_top"
    // adminOperation
    deletePost (id) {
      this.$confirm('Are you sure to delete the post?', 'Warning', {
        confirmButtonText: 'Yes',
        cancelButtonText: 'No',
        type: 'warning'
      }).then(() => {
            axios.post('/forum/manager/adminOperation', {postId: id, operation: "delete"}).then(resp => {
              if (resp && resp.status === 200) {
                this.loadPosts()
              }
            })
          }
      ).catch(() => {
        this.$message({
          type: 'info',
          message: 'cancel deletion'
        })
      })
      // alert(id)
    },
    topPost (id) {
      this.$confirm('Are you sure to top the post?', 'Warning', {
        confirmButtonText: 'Yes',
        cancelButtonText: 'No',
        type: 'warning'
      }).then(() => {
            axios.post('/forum/manager/adminOperation', {postId: id, operation:"top"}).then(resp => {
              if (resp && resp.status === 200) {
                this.loadPosts()
              }
            })
          }
      ).catch(() => {
        this.$message({
          type: 'info',
          message: 'cancel deletion'
        })
      })
      // alert(id)
    },
    cancelTopPost (id) {
      this.$confirm('Are you sure to untop the post?', 'Warning', {
        confirmButtonText: 'Yes',
        cancelButtonText: 'No',
        type: 'warning'
      }).then(() => {
            axios.post('/forum/manager/adminOperation', {postId: id, operation:"cancel_top"}).then(resp => {
              if (resp && resp.status === 200) {
                this.loadPosts()
              }
            })
          }
      ).catch(() => {
        this.$message({
          type: 'info',
          message: 'cancel deletion'
        })
      })
      // alert(id)
    },
    // editDevice (item) {
    //   this.$refs.edit.dialogFormVisible = true
    //   this.selectedposts = [item]
    //   // Set editMode to true when editing
    //   this.$refs.edit.setFormData({
    //     deviceId: item.deviceId,
    //     deviceName: item.deviceName,
    //     infor: item.infor,
    //     type: item.type
    //   })
    // }
  }
}
</script>
<style scoped>

img {
  width: 115px;
  height: 172px;
  /*margin: 0 auto;*/
}

.el-icon-delete {
  cursor: pointer;
  float: right;
}

.switch {
  display: flex;
  position: absolute;
  left: 780px;
  top: 25px;
}

a {
  text-decoration: none;
}

a:link, a:visited, a:focus {
  color: #3377aa;
}

</style>
