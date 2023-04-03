<template>
<!--  <div class="home">-->
<!--    <img alt="Vue logo" src="../assets/logo.png">-->
<!--    <HelloWorld msg="Welcome to Your Vue.js App"/>-->
<!--  </div>-->
  <h1> hello world</h1>
  <p>{{name}}</p>
  <p v-html="info"></p>
  <!-- v-bind：属性名="变量名"绑定动态的标签属性-->
  <p v-bind:data="dataVal">我有属性data</p>
  <!-- class类名绑定-->
  <p :class="{'red':isRed}">我是红色的</p>
  <!-- 判断语句 v-if  false的时候是元素未渲染上在页面-->
  <!-- v-show ： false的时候是样式的隐藏-->
  <p v-if="isTrue">我是if存在</p>
  <p v-show="isTrue">我是show展示</p>
  <!-- for循环-->
  <ul>
    <!-- 循环数组-->
    <!-- v-for="(每一个对象的向量，下标） in 数组" -->
    <li v-for="(item,index) in userList" :key="index">
      学生姓名：{{item.username}}
      学生年龄：{{item.userage}}
    </li>
  </ul>
  请输入用户ID号：<el-input v-model='input1' placeholder="Please input" />
  <el-button @click='select'>发送</el-button>
</template>

<script>
// @ is an alias to /src
// import HelloWorld from '@/components/HelloWorld.vue'
//
// export default {
//   name: 'HomeView',
//   components: {
//     HelloWorld
//   }
// }

import {reactive, toRefs} from "vue";
import {postdata} from "@/http/data";

export default {
  name: "home",

  methods:{

    select(){
      const data1={
        uid:2
      };
      postdata(data1,'/customer/finduserbyId').then(res=>{
        console.log(res);
      })


    }
  },
  setup(){

    const  data = reactive({
      name:"小红",
      age:"20",
      info:"<i>我是斜体字</i>",
      dataVal:20,
      isRed:true,
      isTrue:true,
      isFalse:false,
      userList:[
        {
          username:"小红",
          userage:10
        },
          {
          username:"小明",
          userage:12
        },
          {
          username:"小李",
          userage:13
        }
      ]
    })
    return{
      ...toRefs(data)
    }
  }
}
</script>

<style>
.red{
  color: red;
}
</style>

