<script>

    const URL = "http://127.0.0.1:8848";
    // const URL = "http://192.168.43.100:8848";
    // const URL = "http://sta.free.idcfengye.com";
    // const URL = "http://192.168.1.109:8848";
    // const URL = "http://10.13.65.122:8848";
    // const URL = "http://112.74.79.124:8848";

    // 用户登录 url
    const loginUrl = URL + "/user/login";

    // 退出登录
    const logout = URL + "/user/logout"
    // 测试接口 url
    const testUrl = URL + "/user/test";
    // 发送邮件 url
    const sendEmailUrl = URL + "/email";
    // 用户注册 url
    const registerUrl = URL + "/user/register";

    // 修改密码
    const user_update_password = URL + "/user/update-password"

    const createContestUrl = URL + "/create/contest";

    const contestList = URL + "/query/contest/list";

    const menu = URL + "/user/get/auth";

    const judge = URL + "/create/problem/judgeproblem";
    
    const roleList = URL + "/query/list/role"

    const addAuth = URL + "/add/role"
    
    const deleteRole = URL + "/delete/role/" 

    // 查询角色权限
    const role_auth = URL +"/query/role-auth/"

    // 修改角色权限
    const role_updateauth = URL +"/update/role-auth/"

    //所有用户列表
    const all_user = URL + "/query/all-user"

    //所有用户列表带角色
    const all_user_role = URL + "/query/all-user-role"

    // 更改用户角色
    const update_user_role = URL + "/update/user-role/"

    // 查询比赛题目
    const query_contest_problem = URL + "/query-contest-problem"

    // 模糊搜索标签
    const vague_query_tags = URL + "/vague-query/tags"

    // 模糊搜索用户名
    const user_vague_query_name = URL + "/user/vague-query-name"

    // 搜索题目
    const query_problem_info = URL + "/query-problem-info"

    // 从题库添加题目到比赛
    const add_problem_to_contest = URL + "/add-problem-to-contest"

    // 从比赛删除题目
    const delete_problem_to_contest = URL + "/delete-problem-to-contest"

    // 获取用户比赛题目
    const user_query_contest_problem = URL + "/user/query-contest-problem"

    // 判题
    const save_answers = URL + "/save-answers"

    // 查看比赛信息
    const query_contest_info = URL + "/query-contest-info"

    // 修改题目信息
    const update_contest_info = URL + "/update/contest-info"

    // 获取当前时间
    const user_get_time = URL + "/user/get-time"

    // 获取上一次答案
    const query_last_answer = URL + "/query-last-answer"

    // 获取评判的结果
    const query_result_objective = URL + "/query-result-objective"

    // 创建选择题
    const create_choice_problem = URL + "/create/choice-problem"

    // 登录日志
    const query_logs = URL + "/query/logs"

    // 上传文件
    const upload_test_case = URL + "/upload/test-case"

    // 创建编程题
    const create_program_problem = URL + "/create/program-problem"

    // 提交编程题代码
    const submit_program = URL+ "/submit-program"

    // 获取编程题的题目
    const query_progarm_problem = URL + "/query/program-problem"

    // 获取比赛下所有类型题目的总数
    const query_contest_all_type_problem = URL + "/query/contest/all-type-problem"

    // 查用户信息
    const user_info = URL + "/user/info"

    // 创建班级
    const create_class = URL + "/create/class"

    // 查看班级列表
    const query_class_list = URL + "/query/class-list"

    // 删除班级
    const delete_class = URL + "/delete/class"

    // 退出班级
    const exit_class = URL + "/user/exit-class"

    // 修改时查询班级信息
    const query_class_info = URL + "/query/class-info"

    // 提交修改班级信息
    const update_class_info = URL + "/update/class-info"

    // 查看管理员列表
    const query_admin_list = URL + "/query/admin-list"

    // 查询所有老师 
    const query_teacher_list = URL + "/query/teacher-list"

    // 查询所有学生 
    const query_student_list = URL + "/query/student-list"

    // 查询用户创建的题目
    const query_problem_my_create = URL + "/query/problem/my-create"

    // 查询用户创建的班级
    const query_class_my_create = URL + "/query/class/my-create"

    // 查询用户创建的比赛 
    const query_contest_my_create = URL + "/query/contest/my-create"

    // 查询原题目信息
    const queryproblem_info = URL + "/query/problem-info"

    // 修改判断题
    const update_judge_problem = URL + "/update/judge-problem"

    // 修改选择题
    const update_choice_problem = URL + "/update/choice-problem"

    // 查询编程题测试文件信息
    const query_program_test_file_info = URL + "/query/program-test-file-info"

    // 提交编程题修改信息
    const update_program_problem = URL + "/update/program-problem"

    // 更改用户信息 
    const user_update_info = URL + "/user/update-info"

    // 查询用户班级
    const user_class_info = URL + "/user/class-info"

    // 根据邀请码来查询班级
    const user_query_class_list_code = URL + "/user/query/class-list/code"

    // 加入班级 
    const user_join_class = URL + "/user/join-class"

    // 查看班级下的学生
    const query_class_student_info = URL + "/query/class-student-info"

    // 劝退学生（踢出班级）
    const kick_student_out_class = URL + "/kick-student-out-class"

    // 查询公开题目集
    const query_open_contest = URL + "/query/open-contest"

    // 查询个人题目集
    const query_my_contest = URL + "/query/my-contest"

    // 查询班级题目集
    const query_class_contest = URL + "/query/class-contest"

    // 班级添加题目
    const add_contest_to_class = URL + "/add-contest-to-class"

    // 获取我的提交列表
    const query_my_submit = URL + "/query/my-submit"

    // 获取所有的提交列表
    const query_all_submit = URL + "/query/all-submit"

    // 获取排名
    const query_contest_rank = URL + "/query/contest-rank"

    // 查询主页信息
    const user_query_resource_cnt = URL + "/user/query/resource-cnt"

    // 查看班级比赛结果
    const query_contest_result = URL + "/query/contest-result"


    export default {
        URL,
        loginUrl,
        testUrl,
        sendEmailUrl,
        registerUrl,
        user_update_password,
        createContestUrl,
        contestList,
        menu,
        judge,
        roleList,
        addAuth,
        deleteRole,
        role_auth,
        role_updateauth,
        all_user,
        all_user_role,
        update_user_role,
        query_contest_problem,
        vague_query_tags,
        user_vague_query_name,
        query_problem_info,
        add_problem_to_contest,
        delete_problem_to_contest,
        user_query_contest_problem,
        save_answers,
        query_contest_info,
        user_get_time,
        query_last_answer,
        query_result_objective,
        create_choice_problem,
        query_logs,
        upload_test_case,
        create_program_problem,
        submit_program,
        query_progarm_problem,
        query_contest_all_type_problem,
        logout,
        user_info,
        create_class,
        query_class_list,
        delete_class,
        query_class_info,
        update_class_info,
        query_admin_list,
        query_teacher_list,
        query_student_list,
        update_contest_info,
        query_problem_my_create,
        query_class_my_create,
        query_contest_my_create,
        queryproblem_info,
        update_judge_problem,
        update_choice_problem,
        query_program_test_file_info,
        update_program_problem,
        user_update_info,
        user_class_info,
        user_query_class_list_code,
        user_join_class,
        query_class_student_info,
        kick_student_out_class,
        query_open_contest,
        query_my_contest,
        query_class_contest,
        add_contest_to_class,
        query_my_submit,
        query_all_submit,
        query_contest_rank,
        user_query_resource_cnt,
        query_contest_result,
        exit_class
    }
</script>
