<template>
  <div class="login">
    <p class="title">登录</p>
    <van-image
        round
        width="6rem"
        height="6rem"
        src="https://img01.yzcdn.cn/vant/cat.jpeg"
    />
    <van-form>
      <van-field
          v-model="username"
          name="用户名"
          label="用户名"
          placeholder="用户名"
          :rules="[{ required: true, message: '请填写用户名' }]"
      />
      <van-field
          v-model="password"
          type="password"
          name="密码"
          label="密码"
          placeholder="密码"
          :rules="[{ required: true, message: '请填写密码' }]"
      />
      <div style="margin: 16px;">
        <van-button round block type="info" native-type="submit" @click="onSubmit">登录</van-button>
      </div>
      <div class="reg" @click="showRegister = true">没有账号？立即注册</div>
    </van-form>

    <!-- 注册组件 -->
    <transition name="slide-up">
      <div v-if="showRegister" class="register-overlay">
        <div class="register-container">
          <Register @close="showRegister = false"></Register>
        </div>
      </div>
    </transition>
  </div>
</template>

<script>
import { Toast } from 'vant';
import { Field, Form, Button, Image } from 'vant';
import Register from '@components/Register.vue';
import { login } from '@request/api';  // 导入 login 方法

export default {
  components: {
    Register,
    [Field.name]: Field,
    [Form.name]: Form,
    [Button.name]: Button,
    [Image.name]: Image,
  },
  name: 'Login',
  data() {
    return {
      username: '',
      password: '',
      showRegister: false,
    };
  },
  methods: {
    async onSubmit() {
      if (!this.username || !this.password) {
        Toast.fail('请填写用户名和密码');
        return;
      }
      try {
        const response = await login(this.username, this.password);
        console.log('Response:', response); // 调试用，查看 response 对象
        console.log(response.data.code);
        if (response.data.code === 0) {
            Toast.success('登录成功');
            const token = response.data.token;
            // 保存 token 到本地存储或状态管理
            localStorage.setItem('token', token);
            this.$emit('login-success');  // 触发 login-success 事件
        } else{
          Toast.fail(response.data.msg || '登录失败');
        }
      } catch (error) {
        Toast.fail('登录失败');
        console.error('Error:', error); // 调试用，查看错误对象

      }
    },
    closeRegister() {
      this.showRegister = false;
    }
  }
}
</script>

<style scoped>
.login {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  background-color: #f5f5f5;
}

.reg {
  color: #20a0ff;
  cursor: pointer;
}

.register-overlay {
  position: fixed;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.register-container {
  background-color: white;
  border-radius: 10px 10px 0 0; /* 圆角顶部 */
  width: 100%;
  box-shadow: 0px -5px 10px rgba(0, 0, 0, 0.2); /* 阴影效果 */
}

.title {
  height: 50px;
  line-height: 50px;
  background-color: #20a0ff;
  color: #fff;
  text-align: center;
}

.slide-up-enter-active,
.slide-up-leave-active {
  transition: transform 0.3s ease-out;
}

.slide-up-enter,
.slide-up-leave-to {
  transform: translateX(100%);
}
</style>


