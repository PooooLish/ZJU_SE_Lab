import StudentExam from '@/components/exam/StudentExam.vue'
import TeacherExam from '@/components/exam/TeacherExam.vue'
import Ranking from '@/components/scores/Ranking.vue'
import StudentScore from '@/components/scores/StudentScore.vue'
import TeacherScore from '@/components/scores/TeacherScore.vue'
import Bookinfo from '@/components/courseinfo/Bookinfo.vue'
import Courseinfo from '@/components/courseinfo/Courseinfo.vue'
import Getinfo from '@/components/courseinfo/Getinfo.vue'
// import StudentExam from '@/components/exam/StudentExam.vue'
// import TeacherExam from '@/components/exam/TeacherExam.vue'
import AdminQue from '@/components/questionair/admin_main.vue'
import StudentQue from '@/components/questionair/stu_sub.vue'
import TeacherQue from '@/components/questionair/tech_main.vue'
// import Ranking from '@/components/scores/Ranking.vue'
// import StudentScore from '@/components/scores/StudentScore.vue'
// import TeacherScore from '@/components/scores/TeacherScore.vue'

// import AdminQue from '@/components/questionair/admin_main.vue'
import manager from "@/components/forum/manager/manager.vue";
import postsLike from "@/components/forum/postsLike.vue";
import postsDislike from "@/components/forum/postsDislike.vue";
import postsFavor from "@/components/forum/postsFavor.vue";
import postsPub from "@/components/forum/postsPub.vue";
import ai from "@/components/forum/ai.vue";
import forum from "@/components/forum/forum.vue";
import AllCoursesComponent from "@/components/courseSelects/AllCoursesComponent.vue";
import bySelectionComponent from "@/components/courseSelects/bySelectionComponent.vue";
import SearchComponent from "@/components/courseSelects/SearchComponent.vue";
import ShowComponent from "@/components/courseSelects/ShowComponent.vue";
import teacherselectset from "@/components/courseSelects/teacherselectset.vue";
import teacherbyelection from "@/components/courseSelects/teacherbyelection.vue";
import forumlogin from "@/components/forum/forumLogin.vue";
export default [
    {
        path: '/rank',
        component: Ranking  
    },
    {
        path: '/student-score',
        component: StudentScore
    },
    {
        path: '/teacher-score',
        component: TeacherScore
    },
    {
        path: '/student-exam',
        component: StudentExam
    },
    {
        path: '/teacher-exam',
        component: TeacherExam
    }
    ,
    {
        path: '/bookinfo',
        component: Bookinfo
    }
    ,
    {
        path: '/courseinfo',
        component: Courseinfo
    }
    ,
    {
        path: '/getinfo',
        component: Getinfo
    },
    {
        path: '/student-que',
        component: StudentQue
    },
    {
        path: '/teacher-que',
        component: TeacherQue
    },
    {
        path: '/admin-que',
        component: AdminQue
    }
    ,
    {
        path: '/AllCoursesComponent',
        component: AllCoursesComponent
    }
    ,
    {
        path: '/bySelectionComponent',
        component: bySelectionComponent
    }
    ,
    {
        path: '/SearchComponent',
        component: SearchComponent
    }
    ,
    {
        path: '/ShowComponent',
        component: ShowComponent
    },
    {
        path: '/teacherbyelection',
        component: teacherbyelection
    } ,{
        path: '/teacherselectset',
        component: teacherselectset
    },
    { path: '/forum/login', component: forumlogin},
    { path: '/forum', component: forum},
    { path: '/forum/manager', component: manager}, //判断管理员跳转
    // { path: '/forum/login', component: loginForum},
    { path: '/forum/like', component: postsLike},
    { path: '/forum/dislike', component: postsDislike},
    { path: '/forum/favor', component: postsFavor},
    { path: '/forum/pub', component: postsPub},
    { path: '/forum/ai', component: ai },

]

//在APP.vue中创建超链接
//在index.js中作映射