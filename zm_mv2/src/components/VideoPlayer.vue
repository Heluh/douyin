<template>
  <div class="video-player">
    <van-swipe
        style="height: 100vh; width: 100%; overflow: hidden;"
        vertical
        @change="onChange"
        :show-indicators="false"
        :loop="false"
        ref="swipe"
        :initial-swipe="initialSwipe"
    >
      <van-swipe-item v-for="(video, index) in videoList" :key="index" :data-index="index">
        <div class="main">
          <video-player
              class="video-player vjs-custom-skin"
              :playsinline="true"
              :options="playerOptions[index]"
              :ref="'videoPlayer-' + index"
          ></video-player>
          <div class="foot">
            <p class="user">@ {{ video.artistName }}</p>
            <p class="introduction">{{ video.introduction }}</p>
          </div>
          <div class="lk" @click="like(video.id)">
            <van-icon class="like" :class="{ 'liked': video.liked }" name="like" size="30"/>
            <span>{{ video.likeCount }}</span>
          </div>
          <div class="pl" @click="openPl(video.id)">
            <van-icon class="chat" name="chat" size="30" style="margin-top: 10px"/>
          </div>
        </div>
      </van-swipe-item>
    </van-swipe>
  </div>
</template>

<script>
import { videoPlayer } from 'vue-video-player';
import "../video"
import { like } from "@request/api";

export default {
  name: 'VideoPlayer',
  components: {
    videoPlayer,
  },
  data() {
    return {
      initialSwipe: 0,
      currentIndex: 0,
      videoList: [],
      playerOptions: [],
    };
  },
  created() {
    this.videoList = JSON.parse(this.$route.query.videos || '[]');
    this.currentIndex = parseInt(this.$route.query.currentIndex, 10) || 0;
    this.initializePlayerOptions();
  },
  mounted() {
    this.$nextTick(() => {
      this.initializeSwiper();
    });
  },
  methods: {
    like,
    initializePlayerOptions() {
      this.playerOptions = this.videoList.map(video => ({
        playbackRates: [0.5, 1.0, 1.5, 2.0],
        autoplay: false,
        muted: false,
        loop: true,
        preload: 'auto',
        language: 'zh-CN',
        poster: '',
        aspectRatio: '9:16',
        fluid: true,
        width: document.documentElement.clientWidth,
        notSupportedMessage: '此视频暂无法播放，请稍后再试',
        sources: [
          {
            type: 'video/mp4',
            src: video.url,
          },
        ],
        controlBar: {
          timeDivider: true,
          durationDisplay: true,
          remainingTimeDisplay: false,
          fullscreenToggle: false,
        },
      }));
    },
    initializeSwiper() {
      console.log(this.currentIndex)
      this.initialSwipe = this.currentIndex;
    },
    onChange(index) {
      this.currentIndex = index;
      this.stopAllVideos();
      this.playCurrentVideo();
    },
    playCurrentVideo() {
      const videoPlayerRef = this.$refs[`videoPlayer-${this.currentIndex}`];
      if (videoPlayerRef && videoPlayerRef[0]) {
        videoPlayerRef[0].player.play();
      }
    },
    stopAllVideos() {
      this.videoList.forEach((_, index) => {
        const videoPlayerRef = this.$refs[`videoPlayer-${index}`];
        if (videoPlayerRef && videoPlayerRef[0] && !videoPlayerRef[0].player.paused()) {
          videoPlayerRef[0].player.pause();
        }
      });
    }
  },
};
</script>

<style scoped>
.main {
  height: 100%;
}

.user {
  font-size: 14px;
}
.user,
.introduction {
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
  font-size: 17px;
}

.like {
  color: #fff; /* 默认的颜色 */
}

.like.liked {
  color: red; /* 点赞后的颜色 */
}

.foot {
  position: absolute;
  top: 80%;
  left: 5%;
  width: 80%;
  color: #fff;
  font-size: 25px;
  z-index: 10001;
}
.lk {
  position: absolute;
  top: 58%;
  right: 5%;
  width: 10%;
  display: flex;
  align-items: center;
  flex-direction: column;
  justify-content: center;
  color: #fff;
  font-size: 16px;
  z-index: 10001;
}
.pl {
  position: absolute;
  top: 65%;
  right: 5%;
  width: 10%;
  display: flex;
  align-items: center;
  flex-direction: column;
  justify-content: center;
  color: #fff;
  font-size: 16px;
  z-index: 10001;
}
.sheet {
  height: 50vh;
  .pll {
    overflow: hidden;
    padding: 10px;
    border-bottom: 1px solid #f4f4f4;
    .pl-l {
      width: 20%;
      float: left;
    }
    .pl-l img {
      width: 55%;
      border-radius: 50%;
      animation: ds 1s;
    }
    @keyframes ds {
      from {
        opacity: 0;
      }
      to {
        opacity: 1;
      }
    }
    .pl-r {
      width: 78%;
      float: left;
    }
    .name1 {
      font-size: 14px;
      color: #666;
      font-weight: bold;
      margin-bottom: 5px;
    }
    .con {
      width: 100%;
      line-height: 24px;
      color: #333333;
      font-size: 14px;
    }
  }
}
</style>
