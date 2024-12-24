<template>
  <div class="container">
    <el-form  label-width="4.5vw" :model="user">
          <el-form-item label="">
            <el-input v-model="user.username" placeholder="请输入用户名" class="input"></el-input>
          </el-form-item>
          <el-form-item label="">
            <el-input v-model="user.password" placeholder="请输入密码" show-password class="input"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button @click="loginForum" class="but">登录</el-button>
          </el-form-item>
          <el-form-item>
            <el-button @click="signup" class="but">没有账号？点这里注册</el-button>
          </el-form-item>
    </el-form>
  </div>

</template>

<script>
import axios from 'axios';
import Forum from "@/components/forum/forum.vue";

export default {
  name: 'login',
  components: {Forum},
  data() {
    return {
      user:{
        username: '',
        password: '',
        password: '',
      }
    };
  },
  methods: {
    async login() {
        if(this.username === ''){
            this.$message({
                showClose: true,
                message: '用户名不能为空',
                type: 'error'
                });
        } 
        else if(this.password === ''){
            this.$message({
                showClose: true,
                message: '密码不能为空',
                type: 'error'
                });
            }
        else{
            const response = await axios.get('/login', {
                params: {
                    username: this.user.username,
                    password: this.user.password,
                }
            }).then((response) => {
                      if (response.data.success) {
                        console.log(response.data);
                        this.$store.commit('setToken', response.data.data.token);
                        this.$store.commit('setUsername', this.user.username);
                        this.$store.commit('setPassword', this.user.password);
                        console.log(this.$store.state.token);
                        axios.get("/init");//给后端一个信号，启动模拟终端
                        this.$router.push('/user/Navigation/homePage');//登录成功后跳转到主页
                      }
                  else if(response.data.message === 'User not found') {
                    this.$message({
                      showClose: true,
                      message: '用户不存在',
                      type: 'error'
                      });
                  }
                  else if(response.data.message === 'Wrong password') {
                    this.$message({
                      showClose: true,
                      message: '密码错误',
                      type: 'error'
                      });
                  }
            }).catch((error) => {
                console.log(error);
            });

        }
    },
    signup() {
      this.$router.push('/signup');
    },
  },
};
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh; /* 设置容器的高度为视口的高度 */

  background-image: url('@/assets/login_background.jpg');
  background-size: cover;
  background-position: center;
  height: 100vh;
  width: 100vw;
}

.input {
  width: 18vw; /* 设置输入框的宽度 */
  /*margin-bottom: 2vh; /* 设置输入框的下边距，使输入框之间有一定的间距 */
}

.but {
  width: 18vw; /* 设置按钮的宽度 */
  /* margin-bottom: 2vh; */
  color: black;
  background-color: rgb(191, 225, 238); /* 设置按钮的背景颜色为自定义的蓝色 */
}
</style>