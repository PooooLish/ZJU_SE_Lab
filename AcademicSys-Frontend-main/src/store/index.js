// 使用cookie存储用户账户信息，以及token来验证用户身份。在向后端请求数据时需要在请求头中携带token，以在后端验证用户身份。
import Vue from 'vue'
import Vuex from 'vuex'
import Cookies from 'js-cookie'
import createPersistedState from "vuex-persistedstate"

Vue.use(Vuex)
const TokenKey='device_front_token'

const store = new Vuex.Store({
  state: {
    username: '',
    password: '',
    token: '',
    
    dev_id:'',
    dev_name:'',
    dev_type:'',
  },//初始值

  mutations: {
    setToken: (state, token) => {
      state.token = token
      Cookies.set(TokenKey, token)
    },
    setUsername: (state, username) => {
      state.username = username
    },
    setPassword: (state, password) => {
      state.password = password
    },
    setDevInfo:(state,row)=>{
      state.dev_id = row.id
      state.dev_name = row.name
      state.dev_type = row.type

      console.log(state.dev_id)
      console.log(state.dev_name)
      console.log(state.dev_type)
    },
    reset:(state)=>{
      state.username = ''
      state.password = ''
      state.token = ''
      state.dev_id = ''
      state.dev_name = ''
      state.dev_type = ''
      Cookies.remove(TokenKey)
    }
  },

  plugins:[createPersistedState()]  //持久化vuex

})

export default store