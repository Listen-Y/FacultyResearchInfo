import {createRouter, createWebHistory} from 'vue-router'
import Login from "../components/Login";
import Home from "../components/Home";
import Demo from "../components/Demo";
import Teacher from "../components/Teacher";
import Job from "../components/Job";
import Treatise from "../components/Treatise";
import Patent from "../components/Patent";
import Reward from "../components/Reward";
import Project from "../components/Project";
import ProjectAchievement from "../components/ProjectAchievement";
import ProjectAward from "../components/ProjectAward";
import User from "../components/User";
import Register from "../components/Register";

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
    path: '/register',
    name: 'Register',
    component: Register,
    meta: {
      title: '注册'
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
        path: '/patent',
        component: Patent,
        meta: {
          title: 'patent'
        }
      },
      {
        path: '/reward',
        component: Reward,
        meta: {
          title: 'Reward'
        }
      },
      {
        path: '/project',
        component: Project,
        meta: {
          title: 'Project'
        }
      },
      {
        path: '/projectAchievement',
        component: ProjectAchievement,
        meta: {
          title: 'ProjectAchievement'
        }
      },
      {
        path: '/projectAward',
        component: ProjectAward,
        meta: {
          title: 'ProjectAward'
        }
      },
      {
        path: '/user',
        component: User,
        meta: {
          title: 'User'
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
