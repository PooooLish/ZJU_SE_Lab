// 用于检查用户权限，进行路由拦截

import router from './router'
import store from './store'
import Cookies from 'js-cookie'

const TokenKey = 'device_front_token'
const whiteList = ['/login','/signup'] // no redirect whitelist

router.beforeEach(async(to, from, next) => {
    const hasToken = store.state.token;

    if(hasToken){
        // if(to.path === '/login'){   //已登录时若前往登录页，则重定向到首页
        //     next({path:'/user/Navigation/myDevice/devInformation'})
        // }
        // else{
        //     next()
        // }
        next();
    }
    else{   //未登录
        if(whiteList.indexOf(to.path) !== -1){  //白名单中的路由直接放行
            next()
        }
        else{
            next('/forum')  //若不在白名单中，则重定向到登录页
        }
    }
})