<template>
  <div style="margin-bottom: 30px;display: flex;justify-content: center;align-items: center">
    <el-input
        @keyup.enter.native="searchClick"
        placeholder="输入关键词搜索帖子..."
        prefix-icon="el-icon-search"
        size="small"
        style="width: 400px;margin-right: 10px"
        v-model="keywords">
    </el-input>
    <el-button size="small" type="primary" icon="el-icon-search" @click="searchClick">搜索</el-button>
    <el-button v-if="true" size="small" type="warning" @click="wordClick">敏感词</el-button>
  </div>
</template>

<script>
export default {
  name: 'SearchBar',
  data () {
    return {
      keywords: '',
      type: ''
    }
  },
  methods: {
    searchClick () {
      this.$emit('onSearch')
    },
    wordClick () {
      this.$emit( 'onWord')
    }
  }
}
</script>

<style scoped>

</style>
