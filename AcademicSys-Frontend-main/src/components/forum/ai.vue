<script>
import {ref, watch} from "vue";
import axios from "axios";
import SearchBar from "@/components/forum/manager/searchBar.vue";
import HeaderBar from "@/components/forum/headerBar.vue";
import Sidebar from "@/components/forum/sidebar.vue";

export default {
  components: {SearchBar, HeaderBar, Sidebar},
  data() {
    return {
      receivedMessage: "",
      gptai: '',
      messagesGpt: [],
      answerGpt: '',
      message: '',
      messages: []
    }
  },
  methods: {
    gpt(query) {
      axios({
        method: 'post',
        url: 'http://127.0.0.1:3000/api/completions',
        headers: {
          'Content-Type': 'application/json',
        },
        data: {gptai: this.gptai}
      }).then((res) => {
        // 设置答案到 answer.value
        console.log(this.gptai)
        this.$message.success('请稍等...')
        this.answerGpt = res.data.message
        console.log(this.answerGpt)
        // 添加用户的问题和 AI 的回答到 messages 数组
        this.messagesGpt.push({text: this.gptai, sender: 'user'});
        this.messagesGpt.push({text: this.answerGpt, sender: 'ai'});
        // console.log(this.messagesGpt.message)

        // 清空输入框
        this.gptai = '';
      }).catch((error) => {
        console.error(error)
        this.$message.warning('当前用户已欠费')
      })
    },
    watch: {
      message(newValue, oldValue) {
        if (newValue && newValue !== oldValue) {
          this.sendMessage(this.message);
        }
      }
    },
    sendMessage(message) {
      const response = fetch('http://localhost:3000/api/completions', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({messages: [{role: 'user', content: this.message}]}),
      });
      console.log(this.message)
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }

      const data = response.json();
      const receivedMessage = data.receivedMessage;
      console.log('Received Message:', receivedMessage);

      // 在这里可以根据需要处理接收到的消息，比如展示在页面上等操作
      return receivedMessage;
    }
  }
}

</script>

<template>
  <el-main>
    <el-col :span="18">
    <el-card style="flex-grow: 1;">
      <el-row>
        <div class="chat-container">
          <div class="input-area">
            <el-input
                v-model="gptai"
                maxlength="500"
                show-word-limit
                placeholder="Message GPT4..."
                style="width: 80%; "
            >
            </el-input>
            <el-button :type="'info'" style="margin-left: 2%" @click="gpt">发 送</el-button>
          </div>
          <div v-for="(message, index) in messagesGpt" :key="index" class="message"
               :class="{'ai-message': message.sender === 'ai', 'user-message': message.sender === 'user'}">
            <div class="message-content">{{ message.text }}</div>

          </div>
        </div>
      </el-row>
    </el-card>
    </el-col>
    <el-col :span="6">
    <headerBar></headerBar>
    </el-col>
  </el-main>
</template>

<style scoped>
.chat-container {
  display: flex;
  flex-direction: column;
  max-height: 280px;
  max-height: 280px;
  overflow-y: auto;
}

/* 调整聊天气泡的样式，使其更现代化 */
.message {
  padding: 12px 16px;
  margin-bottom: 10px;
  border-radius: 18px;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);
  max-width: 75%;
  word-wrap: break-word;
  text-align: left; /* 设置文本对齐为左对齐 */
}

.ai-message {
  background-color: #d7d6d6;
  align-self: flex-start;
  border: 1px solid #f1f1f1;
}

.user-message {
  background-color: #0078ff;
  color: white;
  align-self: flex-end;
  border: none;
}

/* 滚动条样式 */
.chat-container {
  padding-bottom: 150px; /* 预留输入区域的空间 */
  overflow-y: auto;
}

/* 自定义滚动条（可选） */
.chat-container::-webkit-scrollbar {
  width: 5px;
}

.chat-container::-webkit-scrollbar-thumb {
  border-radius: 10px;
  background-color: #ccc;
}

.chat-container::-webkit-scrollbar-track {
  background-color: #f5f5f5;
}
</style>