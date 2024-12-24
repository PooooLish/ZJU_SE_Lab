<template>
  <el-main>
    <!--    <el-col :span="4"></el-col>-->
    <!--    <el-col :span="16">-->

    <el-col :span="18">
    <el-card v-for="(post, index) in allposts" :key="index" class="posts" style="background-color: rgba(139,179,239,0.09)">
      <el-row :span="18">
        <p>{{ post.content }}</p>
      </el-row>
      <el-row :span="6">
        <el-col :span="20">
          <div class="tight-text">
            <p class = "tight-text" style="color: rgba(84,92,100,0.86); font-size: small ">{{ post.post_time }}</p>
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
    <el-backtop target=".page-component__scroll .el-scrollbar__wrap"></el-backtop>
    <!--    </el-col>-->
    <!--    <el-col :span="4"></el-col>-->
    </el-col>
    <el-col :span="6">
      <header-bar></header-bar>
    </el-col>
  </el-main>


</template>

<script>

import HeaderBar from "@/components/forum/headerBar.vue";
import axios from "axios";

export default {
  components: {HeaderBar},
  data() {
    return {
      allposts: []
    };
  },
  mounted: function () {
    this.loadCollectPosts()
  },
  created() {
    axios.get('/forum/dislike')
        .then(response => {
          this.allposts = response.data;
        })
        .catch(error => {
          console.log('Error:', error);
        });
  },
  methods: {
    likeIcon(post) {
      // 根据liked变量的状态返回不同的图标路径
      return post.liked ? require('../../assets/dianzan-2.png') : require('../../assets/dianzan.png');
    },
    // post
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
    sendToBackend(key, value, id) {
      var _this = this
      // 这里返回收藏的帖子
      axios.post('/collect', {
        posts: {
          page: this.currentPage,
          pagesize: this.pagesize
        }})
          .then(resp => {
            if (resp && resp.status === 200) {
              _this.allposts = resp.data
              this.totalPosts = this.allposts.length
            }
          })
    }
  }
}

</script>

<style scoped>
/* 添加一些样式以便更好地展示列表 */
ul {
  list-style-type: none;
  padding: 0;
}
li {
  margin-bottom: 20px;
  border: 1px solid #ccc;
  padding: 10px;
  border-radius: 5px;
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
.tight-text {
  margin: 5px;
  color: #409EFF;
}
</style>