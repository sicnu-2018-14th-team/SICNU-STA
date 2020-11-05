<div id="app" style="width: 100%; height: 100%;">
    <div id="nav">
        <div class="logo">
            <span><el-image :src="programUrl" style="width: 40px; height: 37px;"></el-image></span>
            <span style="font-size: 48px; font-weight: 900">STA</span>
            <span style="font-weight: 700; margin-top: 14px;">
                    <p style="font-size: 16px; line-height: 18px;">程序设计类实验辅助教学平台</p>
                    <p style="font-size: 9px; line-height: 18px;">SICNU TEACHING ASSISTANT</p>
                </span>
        </div>
        <div class="btn-menu">
            <el-row>
                <el-button type="primary" @click="goLogin">登录</el-button>
                <el-button plain @click="goRegister">注册</el-button>
            </el-row>
        </div>
    </div>
    <el-container>
        <el-aside width="200px" v-if="isShowAside">Aside</el-aside>
        <router-view :key="$route.fullPath"></router-view>
    </el-container>
</div>