<template>
  <div class="my-video">
    <div class="top-nav">
      <button class="nav-button1" @click="navigateTo('recommend')">推荐</button>
      <button class="nav-button2" @click="navigateTo('upload')">+</button>
      <button class="nav-button3" @click="navigateTo('my')">我的</button>
    </div>
    <div class="video-grid">
      <router-link v-for="video in videos" :key="video.id" :to="`/video/${video.id}`" class="video-grid-item">
        <img :src="video.cover" alt="Video thumbnail">
        <h2>{{ video.title }}</h2>
      </router-link>
    </div>
  </div>
</template>

<script>
import {mylist} from "@request/api";

export default {
  name: "MyVideo",
  data() {
    return {
      page: 1,
      videos: []
    }
  },

  mounted() {
    this.fetchMyVideos();
  },

  methods: {
    navigateTo(path) {
      this.$router.push(path);
    },

    async fetchMyVideos() {
      // 从服务器获取视频数据
      try{
        const res = await mylist(this.page, 10);
        console.log(res)
        if(res.data.code === 0){
          this.videos = res.data.data.list;
        }else{
          console.error(res.data.msg);
        }
      }catch (e) {
        console.error(e);
      }

    }
  }
}
</script>

<style scoped>
.my-video {
  height: 100vh;
  position: relative;
}
.top-nav {
  display: flex;
  justify-content: space-around;
  align-items: center;
  height: 50px;
  background-color: transparent;
  position: fixed;
  top: 0;
  width: 100%;
  z-index: 999;
}
.nav-button1 {
  background: transparent;
  border: none;
  font-size: 20px;
  color: #ffffff;
  cursor: pointer;
  font-weight: bold;
  margin-left: 40px;
  margin-right: -40px;
}
.nav-button2 {
  background: transparent;
  border-radius: 4px;
  border: 2px solid #eff5ef;
  font-size: 20px;
  color: #ffffff;
  cursor: pointer;
  font-weight: bold;
}
.nav-button3 {
  background: transparent;
  border: none;
  font-size: 20px;
  color: #ffffff;
  cursor: pointer;
  font-weight: bold;
  margin-left: -40px;
  margin-right: 40px;
}

.video-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  gap: 1rem;
}

.video-grid-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
}
</style>