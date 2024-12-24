<template>
  <div class="container">
    <el-form  label-width="4.5vw" :model="user">
          <el-form-item label="">
            <el-input v-model="user.email" placeholder="请输入邮箱" class="input"></el-input>
          </el-form-item>
          <el-form-item label="">
            <el-input v-model="user.username" placeholder="请输入用户名" class="input"></el-input>
          </el-form-item>
          <el-form-item label="">
            <el-input v-model="user.password" placeholder="请输入密码" show-password class="input"></el-input>
          </el-form-item>
          <el-form-item label="">
            <el-input v-model="user.password_confirmation" placeholder="确认密码" show-password class="input"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button @click="signup" class="but">注册</el-button>
          </el-form-item>
          <el-form-item>
            <el-button @click="login" class="but">已经有账号了？点这里登录</el-button>
          </el-form-item>
    </el-form>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'login',
  data() {
    return {
      user:{
        username: '',
        password: '',
        email: '',
        password_confirmation: '',
      }
      
    };
  },
  computed: {
    emailError() {
      // 使用正则表达式验证邮箱格式
      const pattern = /^[\w-]+(\.[\w-]+)*@([\w-]+\.)+[a-zA-Z]{2,7}$/;
      return this.user.email && !pattern.test(this.user.email);
    },
  },
  methods: {
    async signup() {
        if(this.user.email === ''){
            this.$message({
                showClose: true,
                message: '邮箱不能为空',
                type: 'error'
                });
        }
        else if(this.emailError){
            this.$message({
                showClose: true,
                message: '邮箱格式错误',
                type: 'error'
                });
        }
        else if(this.user.username === ''){
            this.$message({
                showClose: true,
                message: '用户名不能为空',
                type: 'error'
                });
            }
        else if(this.user.password === ''){
            this.$message({
                showClose: true,
                message: '密码不能为空',
                type: 'error'
                });
            }
        else if(this.user.password.length < 6){
            this.$message({
                showClose: true,
                message: '密码至少6位',
                type: 'error'
                });
            }
        else if(this.user.password !== this.user.password_confirmation){
            this.$message({
                showClose: true,
                message: '两次密码不一致',
                type: 'error'
                });
            }
        else{
            const response = await axios.post('/signup', {
              username: this.user.username,
              password: this.user.password,
              email: this.user.email,
            });
            console.log(response.data);
            if (response.data.success) {
                this.$store.commit('setToken', response.data.data.token);
                this.$store.commit('setUsername', this.user.username);
                this.$store.commit('setPassword', this.user.password);

                axios.get("/init");//给后端一个信号，启动模拟终端
                this.$router.push('/user/Navigation/homePage'); //注册成功后跳转到主页
            }
            else if(response.data.message === 'Email already exists') {
            this.$message({
                showClose: true,
                message: '该邮箱已注册',
                type: 'error'
                });
            }
            else if(response.data.message === 'Username repeated') {
            this.$message({
                showClose: true,
                message: '该用户名已被使用',
                type: 'error'
                });
            }
        }
    },

    login() {
      this.$router.push('/login');
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
}

.but {
  width: 18vw; /* 设置按钮的宽度 */
  color: black;
  background-color: rgb(191, 225, 238); /* 设置按钮的背景颜色为自定义的蓝色 */
}
</style>