<template>
  This is sign up page
  <div class="box">
    <el-form :model="form" ref="formRef" class="form">
      <h3 class="login-title">欢迎注册</h3>
      <el-form-item label="Username" class="inputBox">
        <el-input v-model="form.uame" @blur="validateUsername" placeholder="请输入账号"></el-input>
        <p v-if="usernameError" class="error-text">Username should only contain letters and numbers</p>
      </el-form-item>
      <el-form-item label="Password" class="inputBox">
        <el-input v-model="form.upwd" type="password" @blur="validatePassword" placeholder="请输入密码"></el-input>
        <p v-if="passwordError" class="error-text">Password should only contain letters and numbers</p>
      </el-form-item>
      <el-form-item label="Confirm Password" class="inputBox">
        <el-input v-model="form.confirmPassword" type="password" @blur="validateConfirmPassword" placeholder="请再次输入密码"></el-input>
        <p v-if="confirmPasswordError" class="error-text">两次密码输入不一致</p>
      </el-form-item>
      <el-form-item label="telephone" class="inputBox">
        <el-input v-model="form.utele"  @blur="validateTelephone" placeholder="telephone"></el-input>
        <p v-if="passwordError" class="error-text">Telephone should only contain  numbers</p>
      </el-form-item>
      <div class="button-container">
        <el-button @click="signForm">sign up</el-button>
        <el-button @click="backLogin">back</el-button>
      </div>
    </el-form>
  </div>
</template>

<script>
import { ref } from 'vue';
import { useRouter } from 'vue-router';

import { ElMessage } from 'element-plus';
import {postdata} from "@/http/data";


export default {
  setup() {
    const router = useRouter();
    const form = ref({ uname: '', upwd: '', confirmPassword: '',utele:'' });
    const formRef = ref(null);
    const usernameError = ref(false);
    const passwordError = ref(false);
    const confirmPasswordError = ref(false);

    const validateUsername = () => {
      const pattern = /^[A-Za-z0-9]*$/;
      if (!pattern.test(form.value.uname)) {
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

    const validateConfirmPassword = () => {
      if (form.value.upwd !== form.value.confirmPassword) {
        confirmPasswordError.value = true;
        form.value.confirmPassword = '';
      } else {
        confirmPasswordError.value = false;
      }
    };

    const backLogin = () => {
      router.push({name:'home'})
    };

    const signForm = async () => {
      try {
        const response = await postdata(form.value,"/customer/adduser");
        if (response["code"] === 200) {
          ElMessage.success('恭喜你注册成功');
        } else {
          ElMessage.error('注册失败');
        }
      } catch (error) {
        console.log(error);
        ElMessage.error('An error occurred');
      }
    }

    return {
      form,
      formRef,
      backLogin,
      validateUsername,
      validatePassword,
      usernameError,
      passwordError,
      signForm,
      validateConfirmPassword,
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