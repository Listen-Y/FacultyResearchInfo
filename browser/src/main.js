import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus';
import 'element-plus/lib/theme-chalk/index.css';
// 数据可视化图
import * as echarts from 'echarts'


const app = createApp(App)
    .use(router)
    .use(ElementPlus, {size: 'small' })
    .mount('#app')
app.echarts = echarts
