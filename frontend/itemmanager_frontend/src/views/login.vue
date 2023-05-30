<template>
  <div class="box">
    <el-form :model="form" ref="formRef" class="form">
      <h3 class="login-title">欢迎登录</h3>
      <el-form-item label="Username" class="inputBox">
        <el-input v-model="form.uid" @blur="validateUsername" placeholder="请输入账号"></el-input>
        <p v-if="usernameError" class="error-text">Username should only contain letters and numbers</p>
      </el-form-item>
      <el-form-item label="Password" class="inputBox">
        <el-input v-model="form.upwd" type="password" @blur="validatePassword" placeholder="请输入密码"></el-input>
        <p v-if="passwordError" class="error-text">Password should only contain letters and numbers</p>
      </el-form-item>
      <div class="button-container">
        <el-button @click="submitForm">Login</el-button>
        <el-button @click="sign">sign up</el-button>
      </div>
    </el-form>
  </div>
</template>

<script>
import { ref } from 'vue';
import { useRouter } from 'vue-router';

import { ElMessage } from 'element-plus';



//import axios from "axios";
import {postdata} from "@/http/data";


export default {
  setup() {
    const router = useRouter();
    const form = ref({ uid: '', upwd: '' });
    const formRef = ref(null);
    const usernameError = ref(false);
    const passwordError = ref(false);

    const validateUsername = () => {
      const pattern = /^[A-Za-z0-9]*$/;
      if (!pattern.test(form.value.uid)) {
        usernameError.value = true;
        form.value.username = '';
      } else {
        usernameError.value = false;
      }
    };

    const validatePassword = () => {
      const pattern = /^[A-Za-z0-9]*$/;
      if (!pattern.test(form.value.upwd)) {
        passwordError.value = true;
        form.value.password = '';
      } else {
        passwordError.value = false;
      }
    };

    const submitForm = async () => {
      try {

        const response = await postdata(form.value,'/customer/login');

        if (response["code"] === 200) {
          console.log(response)
          sessionStorage.setItem('user', response["data"].uname)
           router.push({name: 'index'});
        } else {
          formRef.value.resetFields();
          ElMessage.error('Invalid username or password');
        }
      } catch (error) {
        console.log(error);
        ElMessage.error('An error occurred');
      }
    };

    const sign = () =>{
      router.push({name:'signUp'})
    }

    return {
      form,
      formRef,
      submitForm,
      validateUsername,
      validatePassword,
      usernameError,
      passwordError,
      sign
    };
  },
};
</script>

<style lang="scss" scoped>

.form {
  border: 1px solid #DCDFE6;
  width: 350px;
  margin: 180px auto;
  padding: 35px 35px 15px 35px;
  border-radius: 5px;
  -webkit-border-radius: 5px;
  -moz-border-radius: 5px;
  box-shadow: 0 0 25px #909399;
}

.login-title {
  text-align: center;
  margin: 0 auto 40px auto;
  color: #303133;
}

.button-container {
  display: flex;
  justify-content: space-between;
}

.error-text {
  color: red;
}
</style>