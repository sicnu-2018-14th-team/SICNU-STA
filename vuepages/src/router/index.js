import Vue from 'vue'
import VueRouter from 'vue-router'
import Index from "../views/Index";

Vue.use(VueRouter)

// 解决ElementUI导航栏中的vue-router在3.0版本以上重复点菜单报错问题
// const originalPush = Router.prototype.push
// Router.prototype.push = function push(location) {
//   return originalPush.call(this, location).catch(err => err)
// }

const routes = [

    {
        path: "/home",
        component: () => import("../views/admin/Home.vue"),
        children: [
            {
                path: "/list/admin",
                component: ()=> import("../views/admin/userList/adminList.vue")
            },
            {
                path:'/list/teacher',
                component: ()=> import("../views/admin/userList/teacherList.vue")
            },
            {
                path: "/list/student",
                component: ()=> import("../views/admin/userList/studentList.vue")
            },
            {
                path: "/create/contest",
                component: ()=> import("../views/admin/Contest/createContest")

            },
            {
                path: "/list/contest", 
                component: ()=> import("../views/admin/Contest/contestList.vue")
            },
            {
                path: "/create/problem",
                component: ()=> import("../views/admin/Problem/createProblem.vue"),
                redirect: "/create/problem/choice",
                children: [
                    {
                        path: "/create/problem/choice",
                        component: ()=> import("../views/admin/Problem/CreateMultipleChoice.vue")
                    },
                    {
                        path: "/create/problem/trueorfalse",
                        component: ()=> import("../views/admin/Problem/trueOrFalse.vue")
                    },
                    {
                        path: "/create/problem/completion",
                        component: ()=> import("../views/admin/Problem/completion.vue")
                    },
                    {
                        path: "/create/problem/questions",
                        component: ()=> import("../views/admin/Problem/programmingQuestions.vue")
                    }
                ]
            },
            {
                path: "/problem/list",
                component: ()=> import("../views/admin/Problem/problemList.vue"),
                
            },
            {
                path: "/list/role",
                component: ()=> import("../views/admin/auth/ManagementRole.vue")
            },
            {
                path: "/list/authority",
                component: ()=> import("../views/admin/auth/AssignRoles.vue")
            },
            {
                path:"/list/sys-log",
                component: ()=> import("../views/admin/dailyRecord/system.vue")
            },
            {
                path:"/list/op-log",
                component: ()=> import("../views/admin/dailyRecord/operation.vue")
            },
            {
                path: "/list/login-log",
                component: ()=> import("../views/admin/dailyRecord/signIn.vue")
            },
            {
                path:"/create/class",
                component: ()=> import("../views/admin/class/createClass.vue")
            },
            {
                path:"/list/class",
                component: ()=> import("../views/admin/class/classList.vue")
            },
            {
                path:'/my-problem',
                component: ()=> import("../views/admin/Problem/queryusercreateproblem.vue")
            },
            {
                path:"/my-contest",
                component: ()=> import("../views/admin/Contest/userCreateContest.vue")
            },
            {
                path:'/my-class',
                component: ()=> import("../views/admin/class/usercreateclasslist.vue")
            }
        ]
    },
    {
        path: "/homePage",
        component: () => import("../views/homepage/homePage.vue"),
        children:[
            {
                // 公开比赛列表
                path:'/homePage/contest-list',
                component:() => import("../views/homepage/userContestList.vue")
            },
            {
                // 个人题目列表
                path:'/homePage/contest-personal-list',
                component:() => import("../views/homepage/userPersonalContestList.vue")
            },
            {
                // 比赛首页
                path: '/homePaeg/welcome',
                component:() => import("../views/homepage/contestProblemWelcome.vue")
            }
        ]
    },
    {
        path:"/problemhome",
        name:"problemhome",
        component: () => import("../views/homepage/problemHome/problemHome.vue"),
        redirect: '/contest/problemhome',
        children:[
            {
                path:'/contest/problemhome',
                component: ()=> import("../views/homepage/contestProblemWelcome.vue")
            },
            {
                path : "/contest/problemhome/choice",
                component: ()=> import("../views/homepage/problemHome/Choice.vue")
            },
            {
                path : "/contest/problemhome/judge",
                component: ()=> import("../views/homepage/problemHome/trueOrFalseList.vue")
            },
            {
                path : "/contest/problemhome/programmingList",
                component: ()=> import("../views/homepage/problemHome/programmingList.vue")
            },
            {
                path: "/contest/problemhome/programming",
                component: ()=> import("../views/homepage/problemHome/programmingQuestion.vue")
            },
            {
                path: "/contest/problemhome/submit",
                component: ()=> import("../views/homepage/problemHome/submitList.vue")
            },
            {
                path: "/contest/problemhome/rank",
                component: ()=> import("../views/homepage/problemHome/rank.vue")
            }

        ]
    },
    {
        path:"/usercenter",
        component: ()=> import("../views/userCenter/userCenterHome.vue"),
        redirect:'/usercenter/accountmessage',
        children:[
            {
                path:'/usercenter/accountmessage',
                component: ()=> import("../views/userCenter/accountMessage.vue")
            },
            {
                path:'/usercenter/message',
                component: ()=> import("../views/userCenter/usermessage.vue")
            },
            {
                path:'/usercenter/class',
                component: ()=> import("../views/userCenter/userClassList.vue")
            }
        ]
    },

    {
        path:"/welcome",
        component: () => import("../views/homeWelcomePage.vue")
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
            },
            {
                path: '/user/update',
                component: ()=> import("../views/account/updateaccount.vue")
            }
        ]
    },

    {
        path: "/",
        redirect: "/user/login"
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

const originalPush = VueRouter.prototype.push
   VueRouter.prototype.push = function push(location) {
   return originalPush.call(this, location).catch(err => err)
}
