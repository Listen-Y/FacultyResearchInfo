import {createRouter, createWebHistory} from 'vue-router'
import Login from "../components/Login";
import Home from "../components/Home";
import Demo from "../components/Demo";
import Teacher from "../components/Teacher";
import Job from "../components/Job";
import Treatise from "../components/Treatise";

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
  },
  {
    path: '/home',
    name: 'Home',
    component: Home,
    meta: {
      title: '菜单页'
    },
    children: [
      {
        path: '/1',
        component: Demo,
        meta: {
          title: 'Demo'
        }
      },
      {
        path: '/teacher',
        component: Teacher,
        meta: {
          title: 'Teacher'
        }
      },
      {
        path: '/treatise',
        component: Treatise,
        meta: {
          title: 'Treatise'
        }
      },
      {
        path: '/job',
        component: Job,
        meta: {
          title: 'Job'
        }
      }
    ]
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
