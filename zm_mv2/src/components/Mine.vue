<template>
  <div class="mine">
    <div class="header">
      <div class="avatar">
        <img :src="user.avatar || 'https://img01.yzcdn.cn/vant/cat.jpeg'" alt="avatar" />
      </div>
      <div class="user-info">
        <button @click="logout" class="logout-button">退出登录</button>
        <h2>{{ user.username }}</h2>
        <p>姓名：{{ user.name }}</p>
      </div>
    </div>
    <div class="details">
      <p>电话: {{ user.phone || '点击添加手机号...' }}</p>
      <p>性别：{{ user.sex }}</p>
    </div>
    <div class="works">
      <div class="tab">
        <div>作品</div>
        <div>私密</div>
        <div>推荐</div>
        <div>收藏</div>
        <div>喜欢</div>
      </div>
      <div class="content">
        <div v-for="item in user.works" :key="item.id" class="item">
          <img :src="item.image" alt="work" />
          <span>{{ item.views }}</span>
        </div>
      </div>
    </div>
    <div class="footer">
      <div @click="backToHome">首页</div>
      <div>朋友</div>
      <div @click="triggerFileUpload">+</div>
      <div>消息</div>
      <div>我</div>
      <input type="file" ref="fileInput" style="display: none;" @change="handleFileUpload" accept="video/*"/>
    </div>
  </div>
</template>

<script>
import { myinfo } from '@request/api';

export default {
  name: 'Mine',
  data() {
    return {
      user: {
        id: '',
        username: '',
        name: '',
        sex: '',
        avatar: '',
        phone: '',
        addtime: '',
        works: [],
      },
    };
  },
  async created() {
    const token = localStorage.getItem('token');
    if (token) {
      try {
        const response = await myinfo();
        console.log(response.data);
        this.user = response.data.data; // 根据API返回的数据结构
      } catch (error) {
        console.error('Error fetching user data:', error);
      }
    } else {
      await this.$router.push({name: 'Login'});
    }
  },
  methods: {
    triggerFileUpload() {
      this.$refs.fileInput.click();
    },
    handleFileUpload(event) {
      const file = event.target.files[0];
      if (file) {
        console.log('Selected video file:', file);
        // 在这里处理文件上传逻辑
      }
    },
    backToHome() {
      this.$router.push('/');
    },
    logout() {
      localStorage.removeItem('token');
      this.$router.push('/');
    },
  },
};
</script>

<style scoped>
.mine {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  background-color: #f8f8f8;
}

.header {
  display: flex;
  align-items: center;
  background-color: #2196f3;
  padding: 16px;
  border-radius: 8px;
  color: white;
}

.avatar img {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  border: 2px solid #fff;
}

.user-info {
  margin-left: 16px;
  position: relative;
  flex: 1;
}

.user-info h2 {
  margin: 0;
  font-size: 24px;
}

.user-info p {
  margin: 0;
  font-size: 16px;
}

.logout-button {
  position: absolute;
  right: 0; /* 调整按钮在右侧 */
  top: 50%;
  transform: translateY(-50%);
  background-color: #f44336;
  color: white;
  border: none;
  padding: 8px 16px;
  cursor: pointer;
  border-radius: 4px;
  transition: background-color 0.3s ease;
}

.logout-button:hover {
  background-color: #d32f2f;
}

.details {
  background-color: white;
  padding: 16px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  margin: 16px 0;
}

.details p {
  margin: 8px 0;
  font-size: 16px;
}

.works {
  background-color: white;
  padding: 16px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  flex-grow: 1;
}

.tab {
  display: flex;
  justify-content: space-around;
  border-bottom: 1px solid #ccc;
  padding-bottom: 8px;
  font-weight: bold;
}

.tab div {
  cursor: pointer;
  padding: 8px;
  transition: background-color 0.3s ease;
}

.tab div:hover {
  background-color: #f0f0f0;
}

.content {
  display: flex;
  flex-wrap: wrap;
  margin-top: 16px;
}

.item {
  width: calc(50% - 8px);
  margin: 4px;
  position: relative;
}

.item img {
  width: 100%;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.item span {
  position: absolute;
  bottom: 8px;
  right: 8px;
  background-color: rgba(0, 0, 0, 0.5);
  color: white;
  padding: 4px 8px;
  border-radius: 4px;
}

.footer {
  display: flex;
  justify-content: space-around;
  padding: 16px;
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 -2px 4px rgba(0, 0, 0, 0.1);
}

.footer div {
  cursor: pointer;
  padding: 8px;
  transition: background-color 0.3s ease;
}

.footer div:hover {
  background-color: #f0f0f0;
}
</style>




