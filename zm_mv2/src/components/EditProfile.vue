<template>
  <div class="edit-form">
    <h3>修改用户信息</h3>
    <form @submit.prevent="submitForm">
      <div class="form-group">
        <label for="name">姓名：</label>
        <input id="name" v-model="formData.name" type="text" placeholder="请输入姓名" required>
      </div>
      <div class="form-group">
        <label for="sex">性别：</label>
        <input id="sex" v-model="formData.sex" type="text" placeholder="请输入性别" required>
      </div>
      <div class="form-group">
        <label for="phone">电话：</label>
        <input id="phone" v-model="formData.phone" type="text" placeholder="请输入电话" required>
      </div>
      <div class="form-actions">
        <button type="submit">提交</button>
      </div>
    </form>
  </div>
</template>

<script>
import { updateUser } from '@request/api';

export default {
  data() {
    return {
      formData: {
        name: '',
        sex: '',
        phone: ''
      }
    };
  },
  methods: {
    async submitForm() {
      try {
        const { name, sex, phone } = this.formData;
        await updateUser(name, sex, phone);
        // 更新成功后的处理，可以跳转回到 mine 页面或者其他操作
        this.$router.push('/mine');
      } catch (error) {
        console.error('Error updating user information:', error);
        // 处理错误情况
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

input[type="text"] {
  width: calc(100% - 20px);
  padding: 12px;
  font-size: 16px;
  border: 1px solid #cccccc;
  border-radius: 4px;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.1);
  transition: border-color 0.2s ease;
}

input[type="text"]:focus {
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
</style>


