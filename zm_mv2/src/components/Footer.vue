<!-- src/components/Footer.vue -->
<template>
  <div class="footer">
    <div class="footer-content">
      <div class="footer-item" @click="navigateTo('Home')">首页</div>
      <div class="footer-item" @click="navigateTo('friends')">朋友</div>
      <div class="footer-item" @click="navigateTo('Upload')">
        <span class="upload-button">+</span>
      </div>
      <div class="footer-item" @click="navigateTo('messages')">消息</div>
      <div class="footer-item" @click="navigateTo('Mine')">我</div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Footer',
  methods: {
    navigateTo(route) {
      this.$router.push({ name: route });
    }
  }
};
</script>

<style scoped>
.footer {
  width: 100%;
  height: 5vh;
  background-color: #262626;
  color: #fff;
  text-align: center;
  padding: 10px 0;
  position: fixed;
  bottom: 0;
  left: 0;
  display: flex;
  justify-content: space-around;
}

.footer-content {
  display: flex;
  justify-content: space-around;
  width: 100%;
}

.footer-item {
  flex: 1;
  cursor: pointer;
  padding: 10px;
  font-size: 18px;
}

.upload-button{
  padding: 3px 15px;
  background: transparent;
  border-radius: 8px;
  border: 2px solid #eff5ef;
  font-size: 20px;
  color: #ffffff;
  cursor: pointer;
  font-weight: bold;
}

</style>
