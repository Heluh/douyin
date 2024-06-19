<template>
  <div class="register">
    <van-icon name="cross" class="close-icon" @click="closeRegister" />
    <p class="title">注册</p>
    <van-image
        round
        width="6rem"
        height="6rem"
        src="https://img01.yzcdn.cn/vant/cat.jpeg"
    />
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
</template>

<script>
import { Toast } from 'vant';
import {Field, Form, Button, Image} from "vant";
import { register } from "@request/api";

export default {
  components: {
    [Field.name]: Field,
    [Form.name]: Form,
    [Button.name]: Button,
    [Image.name]: Image
  },
  name: "Register",
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
          phone: this.phone
        });

        if (response.code === 0) {
          Toast.success('注册成功');
          this.closeRegister();
        } else {
          Toast.fail(response.message || '注册失败');
        }
      } catch (error) {
        Toast.fail('注册失败');
        console.error(error);
      }
    },
    closeRegister() {
      this.$emit('close'); // 触发父组件的 close 事件，关闭注册组件
    }
  }
}
</script>

<style scoped>
.register{
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  width: 100%;
  background-color: #f5f5f5;
}

.title {
  height: 50px;
  line-height: 50px;
  background-color: #20a0ff;
  color: #fff;
  text-align: center;
  width: 100%;
}

.login {
  margin-top: 16px;
  color: #20a0ff;
  cursor: pointer;
}

.close-icon {
  position: absolute;
  top: 16px;
  left: 16px;
  color: #c8c9cc;
  font-size: 22px;
  cursor: pointer;
}
</style>




