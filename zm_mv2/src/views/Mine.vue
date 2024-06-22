<template>
  <div class="mine">
    <!-- 头部信息和设置菜单 -->
    <div class="header">
      <!-- 头像和用户信息 -->
      <div class="avatar">
        <img :src="user.avatar || 'https://img01.yzcdn.cn/vant/cat.jpeg'" alt="avatar" />
      </div>
      <div class="user-info">
        <button @click="logout" class="logout-button">退出登录</button>
        <h2>{{ user.username }}</h2>
        <p>姓名：{{ user.name }}</p>
      </div>
    </div>

    <!-- 用户详情信息 -->
    <div class="details">
      <p>电话: {{ user.phone || '点击添加手机号...' }}</p>
      <p>性别：{{ user.sex }}</p>
      <div class="settings" @click="toggleSettingsMenu">
        <img src="../assets/设置.png" alt="settings" />
      </div>
    </div>

    <!-- 我的作品和视频部分 -->
    <div class="works">
      <!-- 选项卡 -->
      <div class="tab">
        <div @click="changeTab('works')" :class="{ 'active': activeTab === 'works' }">作品</div>
        <div @click="changeTab('private')" :class="{ 'active': activeTab === 'private' }">私密</div>
        <div @click="changeTab('recommend')" :class="{ 'active': activeTab === 'recommend' }">推荐</div>
        <div @click="changeTab('favorites')" :class="{ 'active': activeTab === 'favorites' }">收藏</div>
        <div @click="changeTab('likes')" :class="{ 'active': activeTab === 'likes' }">喜欢</div>
      </div>
      <!-- 删除模式按钮 -->
      <button @click="toggleDeleteMode" class="delete-mode-button">
        <van-icon name="delete" size="20" />
        删除模式
      </button>
      <div class="my-works" v-if="activeTab === 'works'">
        <!-- 作品内容 -->
        <div class="content">
          <div v-for="(item, index) in user.works" :key="item.id" class="item" @click="goToVideo(index)">
            <img :src="item.cover" alt="work" />
            <span class="like_count">
              <van-icon class="like" name="like" size="20" />
              <span class="like-number">{{ item.likeCount }}</span>
            </span>
            <van-icon v-if="deleteMode" name="cross" @click.stop="deleteWork(index)" class="delete-button" />
          </div>
        </div>
        <div class="pagination">
          <button @click="prevPage" :disabled="currentPage === 1">上一页</button>
          <span>第 {{ currentPage }} 页，共 {{ totalPages }} 页</span>
          <button @click="nextPage" :disabled="currentPage === totalPages">下一页</button>
        </div>
      </div>

      <!-- 私密内容 -->
      <div class="content" v-else-if="activeTab === 'private'">
        <p>这里是私密内容</p>
      </div>

      <!-- 推荐内容 -->
      <div class="content" v-else-if="activeTab === 'recommend'">
        <p>这里是推荐内容</p>
      </div>

      <!-- 收藏内容 -->
      <div class="content" v-else-if="activeTab === 'favorites'">
        <p>这里是收藏内容</p>
      </div>

      <!-- 喜欢内容 -->
      <div class="content" v-else-if="activeTab === 'likes'">
        <p>这里是喜欢内容</p>
      </div>
    </div>

    <!-- 设置菜单过渡效果 -->
    <transition name="slide">
      <div class="settings-menu" v-if="settingsMenuVisible">
        <div class="close-btn" @click="toggleSettingsMenu">×</div>
        <div class="menu-item" @click="editUserInfo">修改用户信息</div>
        <div class="menu-item" @click="changePassword">修改密码</div>
      </div>
    </transition>
  </div>
</template>

<script>
import { myinfo, mylist, deleteWorkById } from '@request/api';
import { Dialog } from 'vant';

export default {
  name: 'Mine',
  components: {
    [Dialog.Component.name]: Dialog.Component,
  },
  data() {
    return {
      pageSize: 9, // 每页显示的作品数量
      currentPage: 1, // 当前页码
      totalPages: 1, // 总页数
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
      settingsMenuVisible: false,
      activeTab: 'works', // 默认选中的选项卡
      deleteMode: false, // 删除模式开关
    };
  },
  async created() {
    const token = localStorage.getItem('token');
    if (token) {
      try {
        const response = await myinfo();
        this.user = response.data.data; // 根据API返回的数据结构
        // 默认选中作品选项卡并加载作品数据
        await this.changeTab('works');
      } catch (error) {
        console.error('Error fetching user data:', error);
      }
    } else {
      await this.$router.push({ name: 'Login' });
    }
  },

  methods: {
    // 切换选项卡
    async changeTab(tabName) {
      this.activeTab = tabName;
      if (tabName === 'works') {
        await this.fetchWorks();
      }
    },

    // 获取作品数据
    async fetchWorks() {
      try {
        const worksResponse = await mylist(this.currentPage, this.pageSize);
        this.$set(this.user, 'works', worksResponse.data.data.list); // 使用 Vue.set
        this.totalPages = worksResponse.data.data.totalPage; // 计算总页数
      } catch (error) {
        console.error('Error fetching works data:', error);
      }
    },

    // 分页按钮方法
    async prevPage() {
      if (this.currentPage > 1) {
        this.currentPage--;
        await this.fetchWorks();
      }
    },
    async nextPage() {
      if (this.currentPage < this.totalPages) {
        this.currentPage++;
        await this.fetchWorks();
      }
    },

    goToVideo(index) {
      this.$router.push({
        name: 'VideoPlayer',
        params: { id: String(this.user.works[index].id) },
        query: {
          videos: JSON.stringify(this.user.works),
          currentIndex: index
        }
      });
    },

    toggleSettingsMenu() {
      this.settingsMenuVisible = !this.settingsMenuVisible;
    },

    editUserInfo() {
      console.log('Edit user info clicked');
      this.$router.push('/user/edit');
    },

    changePassword() {
      console.log('Change password clicked');
      this.$router.push('/user/editPwd')
    },

    logout() {
      localStorage.removeItem('token');
      this.$router.push('/');
    },

    toggleDeleteMode() {
      this.deleteMode = !this.deleteMode;
    },

    async deleteWork(index) {
      const workId = this.user.works[index].id;
      Dialog.confirm({
        title: '确认删除',
        message: '确定要删除这个作品吗？',
      })
          .then(async () => {
            // 确认删除
            try {
              await deleteWorkById(workId);
              this.user.works.splice(index, 1);
              console.log('Work deleted successfully');
            } catch (error) {
              console.error('Error deleting work:', error);
            }
          })
          .catch(() => {
            // 取消删除
            console.log('删除已取消');
          });
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
  position: relative;
}

.header {
  display: flex;
  align-items: center;
  background: linear-gradient(135deg, #6db3f2, #1e69de);
  padding: 16px;
  border-radius: 8px;
  color: white;
  position: relative;
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
  right: 0;
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
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.details p {
  margin: 8px 0;
  font-size: 16px;
  flex: 1;
}

.works {
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  flex-grow: 1;
  display: flex;
  flex-direction: column;
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
.my-works {
  height: calc(100vh - 400px);
  display: flex;
  flex-direction: column;
  position: relative;
}

.content {
  display: flex;
  flex-wrap: wrap;
  padding: 16px;
}

.item {
  width: 33.33%; /* 每行三个，移除间距 */
  margin: 0; /* 移除底部间距 */
  box-sizing: border-box;
  position: relative;
}

.item img {
  width: 100%;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}


.like_count {
  position: absolute;
  bottom: 4px;
  right: 10px;
  color: #d32f2f;
  display: flex;
  align-items: center;
}

.like-number {
  position: absolute;
  bottom: 4px;
  right: 2px;
  color: white;
  padding: 2px 4px;
  border-radius: 4px;
  font-size: 8px;
}


.pagination {
  width: 100%;
  position: absolute;
  bottom: 20px;
  display: flex;
  justify-content: center;
}

.settings {
  cursor: pointer;
  flex-shrink: 0;
}

.settings img {
  width: 33px;
  height: 20px;
}

.settings-menu {
  position: fixed;
  top: 0;
  right: 0;
  width: 300px;
  height: 100%;
  background: linear-gradient(135deg, #fff, #f7f7f7);
  box-shadow: -2px 0 8px rgba(0, 0, 0, 0.2);
  z-index: 1000;
  padding: 16px;
  transition: transform 0.3s ease;
}

.close-btn {
  position: absolute;
  top: 16px;
  right: 16px;
  font-size: 24px;
  cursor: pointer;
}

.menu-item {
  padding: 12px 0;
  border-bottom: 1px solid #ccc;
  cursor: pointer;
  transition: background-color 0.3s ease;
  color: #333;
  font-size: 18px;
}

.menu-item:hover {
  background-color: #e0e0e0;
}

.slide-enter-active, .slide-leave-active {
  transition: transform 0.3s ease;
}

.slide-enter, .slide-leave-to {
  transform: translateX(100%);
}

.grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  gap: 16px;
}

.video-item {
  text-align: center;
}

.video-item video {
  width: 100%;
  max-width: 100%;
  height: auto;
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

.tab div.active {
  background-color: #f0f0f0;
}


.delete-mode-button {
  margin: 16px;
  padding: 8px 16px;
  border: 1px solid #ccc;
  background-color: #f0f0f0;
  cursor: pointer;
  border-radius: 4px;
}

.delete-button {
  position: absolute;
  top: 8px;
  right: 8px;
  background-color: #f44336;
  color: white;
  padding: 4px;
  border-radius: 50%;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.delete-button:hover {
  background-color: #d32f2f;
}
</style>
