<template>
  <div class="edit-form">
    <h3>修改密码</h3>
    <form @submit.prevent="submitForm">
      <div class="form-group">
        <label for="oldPassword">旧密码：</label>
        <input id="oldPassword" v-model="formData.oldPassword" type="password" placeholder="请输入旧密码" required>
      </div>
      <div class="form-group">
        <label for="newPassword">新密码：</label>
        <input id="newPassword" v-model="formData.newPassword" type="password" placeholder="请输入新密码" required>
      </div>
      <div class="form-group">
        <label for="confirmPassword">确认新密码：</label>
        <input id="confirmPassword" v-model="formData.confirmPassword" type="password" placeholder="请确认新密码" required>
      </div>
      <div class="form-actions">
        <button type="submit">提交</button>
      </div>
    </form>
    <div v-if="message" :class="{'error-message': isError, 'success-message': !isError}">
      {{ message }}
    </div>
  </div>
</template>

<script>
import { updatePwd } from '@request/api';

export default {
  data() {
    return {
      formData: {
        oldPassword: '',
        newPassword: '',
        confirmPassword: ''
      },
      message: '',
      isError: false
    };
  },
  methods: {
    validatePassword(password) {
      // 检查密码是否符合条件：5-16位的大小写字母和数字
      const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[A-Za-z\d]{5,16}$/;
      return passwordRegex.test(password);
    },
    async submitForm() {
      try {
        const { oldPassword, newPassword, confirmPassword } = this.formData;

        // 检查新密码和确认密码是否一致
        if (newPassword !== confirmPassword) {
          this.message = '新密码和确认密码不一致，请重新输入。';
          this.isError = true;
          return;
        }

        // 检查新密码是否符合条件
        if (!this.validatePassword(newPassword)) {
          this.message = '密码必须包含大小写字母和数字，且长度在5到16位之间。';
          this.isError = true;
          return;
        }

        const response = await updatePwd(oldPassword, newPassword);
        console.log(response)

        if (response.data.code === 0) {
          // 更新成功后的处理，可以跳转回到 mine 页面或者其他操作
          await this.$router.push('/mine');
        } else {
          this.message = response.data.msg || '更新密码失败';
          this.isError = true;
        }
      } catch (error) {
        console.error('Error updating password:', error);
        this.message = '更新密码时发生错误，请稍后再试。';
        this.isError = true;
      }
    }
  }
};
</script>

<style scoped>
.edit-form {
  max-width: 90%;
  margin: auto;
  padding: 20px;
  background-color: #f9f9f9;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.edit-form h3 {
  text-align: center;
  margin-bottom: 20px;
  color: #333333;
}

.form-group {
  margin-bottom: 20px;
}

label {
  font-weight: bold;
  color: #555555;
}

input[type="password"] {
  width: calc(100% - 20px);
  padding: 12px;
  font-size: 16px;
  border: 1px solid #cccccc;
  border-radius: 4px;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.1);
  transition: border-color 0.2s ease;
}

input[type="password"]:focus {
  outline: none;
  border-color: #0070f3;
}

.form-actions {
  text-align: center;
  margin-top: 20px;
}

button {
  padding: 12px 24px;
  background-color: #0070f3;
  color: #ffffff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

button:hover {
  background-color: #0058cc;
}

.error-message {
  color: red;
  text-align: center;
  margin-top: 20px;
}

.success-message {
  color: green;
  text-align: center;
  margin-top: 20px;
}
</style>

