<template>
  <el-container>
    <el-header>
      <h1>Chat with ChatGPT</h1>
    </el-header>
    <el-main>
      <div>
        请先上传文章
      </div>
      <el-card class="message-card" v-for="(message, index) in messages" :key="index">
        <p><strong>{{message.sender}}:</strong> {{message.text}}</p>
      </el-card>

      <el-dialog v-model="dialogVisible" title="欢迎您!" :modal-append-to-body="false">
        <el-input type="text" v-model="inputText" placeholder="请输入..." :disabled="inputOrUpload === 'upload'"></el-input>
        <p v-if="inputOrUpload === 'upload'">已经选择文件</p>
        <el-upload
            ref="upload"
            action="/upload"
            :on-success="handleUploadSuccess"
            :on-error="handleUploadError"
            :disabled="inputOrUpload === 'input'"
            :before-upload="beforeUpload"
        >
          <el-button size="small" type="primary">选取文件</el-button>
        </el-upload>

        <el-button size="small" type="success" @click="submitUpload">上传到服务器</el-button>

        <p v-if="inputOrUpload === 'input' && uploadFile">已经选择文件，请上传或取消选择</p>
        <p v-else-if="inputOrUpload === 'input'">输入框已经有内容</p>
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
import axios from "axios";
import { ElDialog, ElUpload, ElInput, ElButton } from 'element-plus';
import {postdata} from "@/http/data";

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

    onMounted(() => {
      dialogVisible.value = true;
    });

    const beforeUpload = (file) => {
      uploadFile.value = file.raw;
      inputOrUpload.value = 'upload'; // 设置 inputOrUpload 值为 'upload'
      inputText.value = ''; // 清空输入框内容
      return false; // 阻止自动上传
    };


    const handleUploadSuccess = (file) => {
      console.log('File uploaded successfully');
      dialogVisible.value = false;
      uploadFile.value = file.raw;  // update the uploaded file ref
      inputOrUpload.value = 'upload'; // Update inputOrUpload value to 'upload'
    };





    const handleUploadError = () => {
      console.log('File upload error');
      dialogVisible.value = false;
    };

    const submitUpload = async () => {
      if (inputOrUpload.value === 'input') {
        try {
          const response = await postdata('/input', {
            message: inputText.value
          })
          console.log(response.data);
          // 将后端返回的数据存储到sessionStorage中
          sessionStorage.setItem('sessionData', JSON.stringify(response.data));
          sessionData.value = response.data;
        } catch (error) {
          console.error('Error:', error);
        }
      } else if (inputOrUpload.value === 'upload') {
        alert("test")
        // 当已选择上传文件时，向服务器的 /upload 接口发送文件
        const formData = new FormData();
        formData.append('file', uploadFile.value);
        try {
          const response = await postdata(formData,'/upload', );
          console.log(response.data);
          // 将后端返回的数据存储到sessionStorage中
          sessionStorage.setItem('sessionData', JSON.stringify(response.data));
          sessionData.value = response.data;
        } catch (error) {
          console.error('Error:', error);
        }
      }
    };

    async function sendMessage() {
      if (newMessage.value.trim() !== '') {
        messages.value.push({ sender: 'User', text: newMessage.value })
        // 获取sessionStorage中存储的数据
        const storedData = JSON.parse(sessionStorage.getItem('sessionData'));

        if (value.value === 'summary_extraction') {
          try {
            const response = await axios.post('/summary_extraction', {
              message: newMessage.value,
              sessionData: storedData  // 将sessionData包含在请求中
            })
            messages.value.push({ sender: 'ChatGPT', text: response.data })
          } catch (error) {
            console.error('Error:', error)
          }
        }
        if (value.value === 'conversation') {
          try {
            const response = await axios.post('/conversation', {
              message: newMessage.value,
              sessionData: storedData  // 将sessionData包含在请求中
            })

            messages.value.push({ sender: 'ChatGPT', text: response.data })
          } catch (error) {
            console.error('Error:', error)
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
