<template>
  <div class="about">
    <h1>This is an about page</h1>
    <h2 id="dom"> {{msg}}</h2>
  </div>
</template>

<script>
import { reactive,toRefs,onBeforeMount,onMounted,onBeforeUpdate,onUpdated} from "vue";
export default {
  name:"about",
  setup(){
    const data = reactive({
      msg:"你好！"
    })

    //数据渲染前
    onBeforeMount(()=>{
      console.log("onBeforeMount",document.querySelector("#dom"))
    })
    //数据渲染后
      onMounted(()=>{
      console.log("onMounted",document.querySelector("#dom"))
    })
    setTimeout(()=>{
      data.msg = "hello"
    },2000)
     //dom更新前
      onBeforeUpdate(()=>{
      console.log("onBeforeUpdate",document.querySelector("#dom"))
    })
     //dom更新后
      onUpdated(()=>{
      console.log("onUpdated",document.querySelector("#dom"))
    })
    return{
      ...toRefs(data)
    }
  }
}
</script>
