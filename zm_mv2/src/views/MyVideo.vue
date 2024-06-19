<template>
  <div class="my-video">
    <div class="top-nav">
      <button class="nav-button" @click="navigateTo('recommend')">推荐</button>
      <button class="nav-button" @click="navigateTo('my-videos')">我的</button>
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
        if(res.status === 0){
          this.videos = res.data;
        }else{
          console.error(res.message);
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
.nav-button {
  background: transparent;
  border: none;
  font-size: 20px;
  color: #ffffff;
  cursor: pointer;
  font-weight: bold;
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