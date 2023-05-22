<template>
  <el-container>
    <el-header>
      <h1>Chat with ChatGPT</h1>
    </el-header>
    <el-main>
      <div>
        <el-button type="primary" @click="dialogVisible = true">上传文章</el-button>
      </div>
      <el-card class="message-card" v-for="(message, index) in messages" :key="index">
        <p><strong>{{message.sender}}:</strong> {{message.text}}</p>
      </el-card>

      <el-dialog v-model="dialogVisible" title="欢迎您!" :modal-append-to-body="false">
        <el-input type="textarea" v-model="inputText" placeholder="请输入..." :autosize="{ minRows: 2}"></el-input>

        <el-upload

            :action="'http://localhost:8088/AI/OCR'"
            :on-success="handleUploadSuccess"
            :on-error="handleUploadError"
            :before-upload="beforeUpload"
        >
        <el-button size="small" type="primary">选取文件</el-button>
        </el-upload>

<!--        <el-button size="small" type="success" @click="submitUpload">上传到服务器</el-button>-->
<!--        <p v-if="inputOrUpload === 'input' && uploadFile">已经选择文件，请上传或取消选择</p>-->
<!--        <p v-else-if="inputOrUpload === 'input'">输入框已经有内容</p>-->
<!--        <p>{{ responseData }}</p> &lt;!&ndash; 添加此行以显示 responseData 的值 &ndash;&gt;-->
        <el-button size="small" type="success" @click="confirm">确定</el-button> <!-- 添加此行来创建新的按钮 -->
      </el-dialog>


    </el-main>
    <el-footer class="footer-fixed">
      <div class="footer-row">
        <form @submit.prevent="sendMessage" class="footer-item input-dimensions">
          <el-input v-model="newMessage" placeholder="Type your message..." style="height: 38px">
            <template #append>
              <el-button type="primary" native-type="submit">Send</el-button>
            </template>
          </el-input>
        </form>
        <el-select v-model="value" placeholder="请选择" class="footer-item select-dimensions">
          <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
              :disabled="item.disabled"
              class="option">
          </el-option>
        </el-select>
      </div>
    </el-footer>
  </el-container>
</template>

<script>
import { onMounted, ref, watch} from 'vue'

import  { ElMessage,ElDialog, ElUpload, ElInput, ElButton } from 'element-plus';
import {postdata} from "@/http/data";
//import {postdata} from "@/http/data";

export default {
  name: "index",
  components: {
    ElDialog,
    ElUpload,
    ElInput,
    ElButton
  },
  setup() {
    const newMessage = ref('')
    // 弹窗状态和输入框内容
    const dialogVisible = ref(false);
    const inputText = ref('');
    const inputOrUpload = ref(''); // New reactive reference
    const uploadFile = ref(null);
    const sessionData = ref('');
    const messages = ref([
      {sender: 'ChatGPT', text: '请先上传文章'}
    ])
    const options = ref([
      { value: 'summary_extraction', label: '概要抽取', disabled: false },
      { value: 'conversation', label: '对话', disabled: false }
    ])
    const value = ref('summary_extraction')
    const responseData = ref(''); // 新的响应式引用，用于存储返回的数据
    const img=ref('')
    onMounted(() => {
      // dialogVisible.value = true;
    });

    //点击确定按钮将弹窗取消
    const closeDialog = () => {
      dialogVisible.value = false; // 将 dialogVisible 的值设为 false 来关闭对话框
    };


    const beforeUpload = (file) => {
      uploadFile.value = file.raw;
      console.log(uploadFile)
      inputOrUpload.value = 'upload'; // 设置 inputOrUpload 值为 'upload'
      inputText.value = ''; // 清空输入框内容
      return true; // 阻止自动上传
    };


    const handleUploadSuccess = (res) => {
      console.log('File uploaded successfully');
      // dialogVisible.value = false;
        // update the uploaded file ref
      console.log(res)
      inputText.value=res["data"]
      inputOrUpload.value = 'upload'; // Update inputOrUpload value to 'upload'
    };


    const handleUploadError = () => {
      console.log('File upload error');
      dialogVisible.value = false;
    };



    //点击上传服务器按钮当输入框内有内容向input接口发送数据，当有文件上传时向upload发送文件并将发挥的数据存储在session中
    const submitUpload = async () => {
      if (inputOrUpload.value === 'input') {
        try {
          // const response = await postdata('/input', {
          //   article: inputText.value
          // })
          var responsedata={
            article: inputText.value
          }
          // 将后端返回的数据存储到sessionStorage中
          sessionStorage.setItem('sessionData', JSON.stringify(responsedata));
          sessionData.value = responsedata;
          responseData.value = responsedata; // 将返回的数据存储到 responseData 中
        } catch (error) {
          console.error('Error:', error);
        }
      } else if (inputOrUpload.value === 'upload') {
        //alert("test")
        // 当已选择上传文件时，向服务器的 /upload 接口发送文件
        const formData = new FormData();
        formData.append('file', img);
        console.log(img)
        try {
          const response = postdata(img.value,"/AI/OCR")

          console.log(response.data);
          // 将后端返回的数据存储到sessionStorage中
          sessionStorage.setItem('sessionData', JSON.stringify(response.data));
          sessionData.value = response.data;
          responseData.value = response.data; // 将返回的数据存储到 responseData 中
        } catch (error) {
          console.error('Error:', error);
        }
      }
    };

    //确认文章内容无误，将文章存入session中
    const confirm=()=>{
      try {
        // const response = await postdata('/input', {
        //   article: inputText.value
        // })
        var responsedata = {
          article: inputText.value
        }
        // 将后端返回的数据存储到sessionStorage中
        sessionStorage.setItem('sessionData', JSON.stringify(responsedata));
        sessionData.value = responsedata;
        responseData.value = responsedata;
      }
      catch (error) {
        console.error('Error:', error);
      }
      finally {
        closeDialog();
      }
   };



    //在用户问问题之前先检测session中是否有数据没有则提示用户先上传文章。
    // 当用户选择概要抽取向summary_extraction接口发送数据以及session中的数据
    //当用户选择对话向conversation接口发送数据以及session中的数据
    async function sendMessage() {
      // 获取sessionStorage中存储的数据
      const storedData = JSON.parse(sessionStorage.getItem('sessionData'));
      // 如果没有存储的数据，提醒用户先上传文章
      if (!storedData) {
        // 使用 ElMessage 显示提醒信息
        ElMessage.warning({
          message: '请先上传文章',
          type: 'warning',
        });
        return;  // 提前退出函数
      }
      if (newMessage.value.trim() !== '') {
        messages.value.push({ sender: 'User', text: newMessage.value })
        // 获取sessionStorage中存储的数据
        const storedData = JSON.parse(sessionStorage.getItem('sessionData'));
        if(newMessage.value.trim() === '查看当前文章')
          messages.value.push({ sender: 'ChatGPT',text:  storedData.article })
        if (value.value === 'conversation') {
          try {

            const response = await postdata( {
              "question":newMessage.value.trim(),
              "context": storedData.article  // 将sessionData包含在请求中
            },'/AI/MRC')
            console.log(response)
            messages.value.push({ sender: 'ChatGPT', text: response.data })
          } catch (error) {
            console.error('Error:', error)
          }
        }
        if (value.value === 'summary_extraction') {
          if(newMessage.value.trim() === "概括这篇文章") {
            try {
              const response = await postdata({
                "article": storedData.article,
                "summary": ""// 将sessionData包含在请求中
              }, "/AI/summary")

              messages.value.push({sender: 'ChatGPT', text: response.data.summary})
            } catch (error) {
              console.error('Error:', error)
            }
          }
        }
        newMessage.value = ''
      }
    }

    // 监听 value 的变化，当 value 变化时更新对应选项的 selected 属性
    watch(value, (newValue) => {
      for (const option of options.value) {
        if (option.value === newValue) {
          option.disabled = true
        } else {
          option.disabled = false
        }
      }
    })

    // Update inputOrUpload when inputText changes

    watch(inputText, (newValue) => {
      if (newValue !== '') {
        inputOrUpload.value = 'input';
        uploadFile.value = null;
      } else {
        inputOrUpload.value = '';
      }
    });

    watch(uploadFile, (newValue) => {
      if (newValue !== null) {
        inputText.value = '';
      }
    });

    return {
      newMessage,
      messages,
      sendMessage,
      options,
      value,
      dialogVisible,
      inputText,
      handleUploadSuccess,
      handleUploadError,
      submitUpload,
      inputOrUpload,
      uploadFile,
      beforeUpload,
      closeDialog,
      confirm
    };
  }
}

</script>

<style scoped>
.message-card {
  margin-bottom: 20px;
}

.footer-row {
  display: flex;
  align-items: flex-start;
}

.footer-item {
  margin-right: 20px;
  height: 38px;
}

.input-dimensions {
  width: 550px;
}

.select-dimensions {
  width: 100px;
  height: 38px;
}

.option {
  height: 38px;
}

.footer-fixed {
  position: fixed;
  bottom: 50px;
  left: 0;
  right: 0;
  display: flex;
  justify-content: center;
}
</style>
