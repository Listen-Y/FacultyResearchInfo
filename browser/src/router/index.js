import {createRouter, createWebHistory} from 'vue-router'
import Login from "../components/Login";

const routes = [
  {
    path: '/',
    // 重定向
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'Login',
    component: Login,
    meta: {
      title: '登陆'
    }
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

// 会话校验
router.beforeEach((to, from, next) => {
  if (to.path === '/login' || to.path === '/register') {
    next()
    return
  }
  // token校验
  let tokenUser = sessionStorage.getItem("user")
  if (!tokenUser) return next('/login')
  next()
})


export default router
