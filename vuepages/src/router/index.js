import Vue from 'vue'
import VueRouter from 'vue-router'
import Index from "../views/Index";

Vue.use(VueRouter)

const routes = [

    {
        path: "/home",
        component: () => import("../views/Home"),
        children: [

        ]
    },

    {
        path: "/user",
        component: () => import("../views/account/AccountHome"),
        redirect: '/user/login',
        children: [
            {
                path: '/user/login',
                component: () => import("../views/account/Login")
            },
            {
                path: '/user/register',
                component: () => import("../views/account/Register")
            }
        ]
    },

    {
        path: "/",
        redirect: "/home"
    },

    {
        path: "/index",
        name: "Index",
        component: () => import("../views/Index")
    },

    {
        path: "/test",
        name: "Test",
        component: () => import("../views/Test")
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router
