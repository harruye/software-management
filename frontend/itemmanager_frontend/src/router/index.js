import { createRouter, createWebHashHistory } from 'vue-router'
import loginView from '../views/login.vue'
import signView from '../views/signUp.vue'
import indexView from '../views/index.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: loginView
  },
  {
   path: '/sign',
   name: 'signUp',
   component: signView
  },
    {
        path: '/index',
        name: 'index',
        component: indexView
    }


]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
