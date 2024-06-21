<template>
  <div class="container">
    <header class="header">
      <p class="title">注册</p>
    </header>
    <div class="register">
      <van-form @submit="onSubmit">
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
        <van-field
            v-model="name"
            name="姓名"
            label="姓名"
            placeholder="姓名"
            :rules="[{ required: true, message: '请填写姓名' }]"
        />
        <van-field
            v-model="sex"
            name="性别"
            label="性别"
            placeholder="性别"
            :rules="[{ required: true, message: '请填写性别' }]"
        />
        <van-field
            v-model="phone"
            name="手机"
            label="手机"
            placeholder="手机"
            :rules="[{ required: true, message: '请填写手机' }]"
        />
        <div style="margin: 16px;">
          <van-button round block type="info" native-type="submit">注册</van-button>
        </div>
        <div class="login" @click="closeRegister">已有账号？立即登录</div>
      </van-form>
    </div>
    <footer class="footer"></footer>
  </div>
</template>

<script>
import { Toast } from 'vant';
import { Field, Form, Button } from 'vant';
import { register } from '@request/api';

export default {
  components: {
    [Field.name]: Field,
    [Form.name]: Form,
    [Button.name]: Button,
  },
  name: 'Register',
  data() {
    return {
      username: '',
      password: '',
      name: '',
      sex: '',
      phone: '',
    };
  },
  methods: {
    async onSubmit() {
      try {
        const response = await register({
          username: this.username,
          password: this.password,
          name: this.name,
          sex: this.sex,
          avatar: null,
          phone: this.phone,
        });

        if (response.data.code === 0) {
          Toast.success('注册成功');
          this.closeRegister();
        } else {
          Toast.fail(response.data.msg || '注册失败');
        }
      } catch (error) {
        Toast.fail('注册失败');
        console.error(error);
      }
    },
    closeRegister() {
      this.$emit('close'); // 触发父组件的 close 事件，关闭注册组件
    },
  },
};
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  height: 100vh;
  background-color: #f5f5f5;
}

.header, .footer {
  flex-shrink: 0;
  background-color: #20a0ff;
  color: #fff;
  text-align: center;
  padding: 16px;
}

.header .title {
  margin: 0;
  font-size: 1.5rem;
}

.register {
  flex-grow: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 16px;
}

.login {
  margin-top: 16px;
  color: #20a0ff;
  cursor: pointer;
}
</style>





