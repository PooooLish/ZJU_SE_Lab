<template>
  <el-main>
    <header-bar></header-bar>

    <el-card v-for="(post, index) in allposts" :key="index" class="posts">
      <el-row :span="18">
        <p>{{ post.content }}</p>
      </el-row>
      <el-row :span="6">
        <el-col :span="20">
          <div class="tight-text">
            <p class = "tight-text" style="color: rgba(84,92,100,0.86); font-size: small ">{{ post.date }}</p>
            <p class = "tight-text" style="color: #545c64; font-size: small">{{ post.course }}</p>
            <p class = "tight-text" style="color: #545c64; font-size: small">{{ post.teacher }}</p>
          </div>
        </el-col>
        <el-col :span="4" class="button-column">
          <el-button size="small" round icon="el-icon-caret-top" @click="handleUp()">{{ post.up }}</el-button>
          <el-button size="small" round icon="el-icon-caret-bottom" @click="handleDown()">{{ post.down }}</el-button>
          <el-button size="small" round icon="el-icon-star-on" @click="handleValue()">{{ post.value }}</el-button>
        </el-col>
      </el-row>
    </el-card>
    <el-backtop target=".page-component__scroll .el-scrollbar__wrap"></el-backtop>
  </el-main>


</template>

<script>

import HeaderBar from "@/components/forum/headerBar.vue";
import axios from "axios";

// export default {
//   components: {HeaderBar},
//   setup() {
//
//   },
//   data() {
//     return {
//       allposts:[
//         {
//           content: '我从不看那些电视剧，因为我办过的案子远比电视剧更曲折离奇',
//           up: 10,
//           down: 2,
//           value: 5,
//           date: '2024-05-17',
//           teacher: '周黎明',
//           course: '经济法理论与实务'
//         },
//         {
//           content: '上课朗读屁屁踢',
//           up: 10,
//           down: 2,
//           value: 5,
//           date: '2024-05-17',
//           teacher: 'xxx',
//           course: '面向对象'
//         },
//         {
//           content: '如果我有两颗子弹',
//           up: 10,
//           down: 2,
//           value: 5,
//           date: '2024-05-17',
//           teacher: 'xxx',
//           course: 'xxx'
//         },
//         {
//           content: '屋子里有墨索里尼',
//           up: 10,
//           down: 2,
//           value: 5,
//           date: '2024-05-17',
//           teacher: 'xxx',
//           course: 'xxx'
//         }
//
//       ]
//     }
//   },
//   methods: {
//     handleUp() {
//       this.post.up += 1;
//       this.sendToBackend('up', this.post.up);
//     },
//     handleDown() {
//       this.post.down += 1;
//       this.sendToBackend('down', this.post.down);
//     },
//     handleValue() {
//       this.post.value += 1;
//       this.sendToBackend('value', this.post.value);
//     },
//     sendToBackend(key, value) {
//       const url = "/post";
//       const data = {
//         up: this.post.up
//       };
//
//       axios.post(url, data)
//           .then(response => {
//             console.log('Response:', response);
//           })
//           .catch(error => {
//             console.log('Error:', error);
//           });
//     }
//   }
// }
export default {
  components: {HeaderBar},
  data() {
    return {
      allposts: [{
        content: '我从不看那些电视剧，因为我办过的案子远比电视剧更曲折离奇',
        up: 10,
        down: 2,
        value: 5,
        date: '2024-05-17',
        teacher: '周黎明',
        course: '经济法理论与实务'
      }]
    };
  },
  created() {
    axios.get('/get')
        .then(response => {
          this.allposts = response.data;
        })
        .catch(error => {
          console.log('Error:', error);
        });
  },
  methods: {
    handleUp(post) {
      post.up += 1;
      this.sendToBackend('up', post.up, post.id);
    },
    handleDown(post) {
      post.down -= 1;
      this.sendToBackend('down', post.down, post.id);
    },
    handleValue(post) {
      post.value += 1;
      this.sendToBackend('value', post.value, post.id);
    },
    sendToBackend(key, value, id) {
      const url = "/post";
      const data = {
        [key]: value,
        id: id
      };

      axios.post(url, data)
          .then(response => {
            console.log('Response:', response);
          })
          .catch(error => {
            console.log('Error:', error);
          });
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