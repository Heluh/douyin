<template>
  <div class="my-video">
    <div class="top-nav">
      <button class="nav-button1" @click="navigateTo('recommend')">推荐</button>
      <button class="nav-button2" @click="navigateTo('my')">我的</button>
    </div>
    <div class="swiper-container">
      <swiper
          ref="mySwiper"
          :direction="'vertical'"
          :slides-per-view="1"
          :space-between="30"
          :mousewheel="true"
          :grabCursor="true"
          :observer="true"
          :observeParents="true"
          :options = "swiperOptions"
          v-if="visibleVideos.length > 0"
      >
        <swiper-slide v-for="(video, index) in visibleVideos" :key="index">
          <video-player
              :options="playerOptions[index]"
              class="video-player vjs-custom-skin"
              ref="videoPlayer"
              :playsinline="true"
          ></video-player>
        </swiper-slide>
      </swiper>
    </div>
  </div>
</template>

<script>
import { swiper, swiperSlide } from "vue-awesome-swiper";
import "swiper/dist/css/swiper.css";
import { videoPlayer } from 'vue-video-player';
import '../video'
import {mylist } from "@request/api";
import router from '../router';

export default {
  name: 'MyVideo',
  components: {
    swiper,
    swiperSlide,
    videoPlayer,
  },
  data() {
    return {
      videos: [
        { id: 1, title: "视频1", src: "http://vjs.zencdn.net/v/oceans.mp4" },
        { id: 2, title: "视频2", src: "https://media.w3.org/2010/05/sintel/trailer.mp4" },
        { id: 3, title: "视频3", src: "http://mirror.aarnet.edu.au/pub/TED-talks/911Mothers_2010W-480p.mp4" },
        { id: 4, title: "视频4", src: "https://media.w3.org/2010/05/sintel/trailer.mp4" }
      ],
      visibleVideos: [],
      playerOptions: [],
      swiperOptions: {
        direction: 'vertical',
        speed: 800,
        loop: false,
        on: {
          slideChange: function () {
            console.log('slide change');
          }
        }
      },
      index: 0,
      currentIndex: 0,
    };
  },

  mounted() {
    this.updateVisibleVideos();
    console.log(this.visibleVideos);  // 检查是否更新正确
  },

  methods: {
    navigateTo(path) {
      this.$router.push(path);
    },

    async fetchMyVideos() {
      // 从服务器获取视频数据
      try {
        const res = await mylist(this.page, 10);
        console.log(res);
        if (res.data.code === 0) {
          this.videos = res.data.data.list;
        } else {
          console.error(res.data.msg);
        }
      } catch (e) {
        console.error(e);
      }
    },

    nextVideo() {
      this.index = (this.index + 1) % this.videos.length;
      this.currentIndex = (this.currentIndex + 1) % 3;
      this.updateVisibleVideos();
    },

    prevVideo() {
      this.index = (this.index - 1 + this.videos.length) % this.videos.length;
      this.currentIndex = (this.currentIndex - 1 + 3) % 3;
      this.updateVisibleVideos();
    },

    updateVisibleVideos() {
      this.visibleVideos = [];
      this.playerOptions = [];
      this.visibleVideos[this.currentIndex] = this.videos[this.index];
      this.visibleVideos[(this.currentIndex + 1 === 3) ? 0 : this.currentIndex + 1] = this.videos[(this.index + 1 === this.videos.length) ? 0 : this.index + 1];
      this.visibleVideos[(this.currentIndex - 1 === -1) ? 2 : this.currentIndex - 1] = this.videos[(this.index - 1 === -1) ? this.videos.length - 1 : this.index - 1];
      for (let i of this.visibleVideos){
        let arrStr = {
              playbackRates: [0.5, 1.0, 1.5, 2.0],
              autoplay: true,
              muted: false,
              loop: true,
              preload: "auto",
              language: "zh-CN",
              poster: "",
              aspectRatio: "9:16",
              fluid: true,
              width: document.documentElement.clientWidth,
              notSupportedMessage: "此视频暂无法播放，请稍后再试",
              sources: [
                  {
                    type: 'video/mp4',
                    src: i.src
                  }
              ],
              controlBar: {
                timeDivider: true,
                durationDisplay: true,
                remainingTimeDisplay: false,
                fullscreenToggle: false,
              },
            };
        this.playerOptions.push(arrStr)
      }


      console.log('Visible videos updated:', this.visibleVideos);
    },

    handleSlideChange() {
      console.log('Slide changed:', this.$refs.mySwiper.swiper.realIndex);
      // 获取当前滑动的索引
      const swiper = this.$refs.mySwiper.swiper;
      const newIndex = swiper.realIndex;

      console.log('Slide changed:', newIndex);
      // 更新视频索引
      if (newIndex > this.currentIndex) {
        this.nextVideo();
      } else if (newIndex < this.currentIndex) {
        this.prevVideo();
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
  border: none;
  font-size: 20px;
  color: #ffffff;
  cursor: pointer;
  font-weight: bold;
  margin-left: -40px;
  margin-right: 40px;
}

.swiper-container {
  height: calc(100vh - 64px);
  width: 100%;
  overflow: hidden;
}
</style>
