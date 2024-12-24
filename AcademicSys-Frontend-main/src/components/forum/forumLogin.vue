<template>
  <el-main>
  </el-main>

</template>

<script >
import axios from "axios";

export default {
  data() {
    return {
      identity: '',
    }
  },
  mounted: function () {
    this.identityDetect();
    this.jump();
  },
  methods: {
    identityDetect () {
      var _this = this
      axios.post('/forum/login' , { id: "330721199901011111"
      }).then(response => {
            _this.identity = response.data
          }
        )
    },
    jump () {
      console.log(this.identity)
      if (this.identity === 1) {
        this.$router.push('/forum').catch(error => {
          if (error.name != 'NavigationDuplicated') {
            throw error;
          };
        }); // 假设使用 Vue Router 进行跳转
      } else if (this.identity === 0) {
        this.$router.push('/forum/manager').catch(error => {
          if (error.name != 'NavigationDuplicated') {
            throw error;
          };
        }); // 假设使用 Vue Router 进行跳转
      }
      console.log('identity clear');
    }
  }
}

</script>

<style scoped>

</style>