<template>
  <div class="video-grid">
    <router-link v-for="video in videos" :key="video.id" :to="`/video/${video.id}`" class="video-grid-item">
      <img :src="video.thumbnail" alt="Video thumbnail">
      <h2>{{ video.title }}</h2>
    </router-link>
  </div>
</template>

<script>
export default {
  name: "MyVideo",
  data() {
    return {
      videos: [
        // 你的视频数据
      ]
    }
  }
}
</script>

<style scoped>
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