<template>
  <div class="home">
    <div class="top-nav">
      <button class="nav-button" @click="navigateTo('recommend')">推荐</button>
      <button class="nav-button" @click="navigateTo('my')">我的</button>
    </div>
    <van-swipe
        style="height: 100vh"
        vertical
        @change="onChange"
        :show-indicators="false"
        :loop="false"
        ref="swipe"
        :initial-swipe="initialSwipe"
    >
      <van-swipe-item v-for="(video, index) in visibleVideos" :key="start + index" :data-index="start + index">
        <div class="main">
          <video-player
              v-if="currentIndex === start + index"
              class="video-player vjs-custom-skin"
              ref="videoPlayer"
              :playsinline="true"
              :options="playerOptions"
          ></video-player>
          <div class="foot">
            <p class="user">@ {{ video.artistName }}</p>
            <p class="introduction">{{ video.introduction }}</p>
          </div>
          <div class="lk" @click="like(video.id)">
            <van-icon class="like" name="like" size="30"/>
            <span>{{ video.likeCount }}</span>
          </div>
          <div class="pl" @click="openPl(video.id)">
            <van-icon class="chat" name="chat" size="30"/>
          </div>
        </div>
      </van-swipe-item>
    </van-swipe>
<!--    <van-action-sheet v-model="show" class="sheet">-->
<!--      <van-list-->
<!--          v-model="loading"-->
<!--          @load="onLoad"-->
<!--          :offset="1"-->
<!--          :immediate-check="false"-->
<!--      >-->
<!--        <div v-for="(item, index) in plist" :key="index" class="ovf pll">-->
<!--          <div class="pl-l"><img :src="item.user.avatarUrl" alt="" /></div>-->
<!--          <div class="pl-r">-->
<!--            <div class="name1">{{ item.user.nickname }}</div>-->
<!--            <div class="con">{{ item.content }}</div>-->
<!--          </div>-->
<!--        </div>-->
<!--      </van-list>-->
<!--    </van-action-sheet>-->
    <!-- 自定义图标 -->
    <van-popup
        v-model="show"
        closeable
        close-icon="close"
        position="bottom"
        :style="{ height: '100%' }"
    >
      <login/>
    </van-popup>
  </div>
</template>

<script>
import {like, list, pl} from "@request/api";
import "../video/index";
import { videoPlayer } from "vue-video-player";
import {Popup} from "vant";
import Login from "@components/Login.vue";
import router from '../router'; // Import the router

export default {
  components: {
    vanPopup: Popup,
    videoPlayer,
    Login
  },
  name: "home",
  data() {
    return {
      allVideos: [], // 所有的视频
      visibleVideos: [], // 储存五条要加载的视频
      currentIndex: 0, // 当前浏览的视频索引
      start: 0, // 当前加载的视频的起始索引
      show: false,
      plist: [],
      loading: false,
      page: 1,
      initialSwipe: 0,
      playerOptions: {
        autoplay: true,
        muted: false,
        loop: false,
        preload: "auto",
        language: "zh-CN",
        poster: "",
        aspectRatio: "9:16",
        fluid: true,
        sources: [{ type: "video/mp4", src: "" }],
        width: document.documentElement.clientWidth,
        notSupportedMessage: "此视频暂无法播放，请稍后再试",
        controlBar: {
          timeDivider: true,
          durationDisplay: true,
          remainingTimeDisplay: false,
          fullscreenToggle: false,
        },
      },
    };
  },
  mounted() {
    this.fetchVideos();
  },
  methods: {
    async fetchVideos() {
      try {
        const res = await list(this.page, 10); // Fetch 10 videos initially
        console.log(res);
        this.allVideos = this.allVideos.concat(res.data.list);
        this.updateVisibleVideos();
      } catch (error) {
        console.error(error);
      }
    },
    loadVideo(index) {
      if (index >= 0 && index < this.allVideos.length) {
        const video = this.allVideos[index];
        this.$set(this.playerOptions, "poster", video.cover);
        this.$set(this.playerOptions.sources[0], "src", "http://localhost:8080/springbootb9wc1" + video.url);
      }
    },
    updateVisibleVideos() {
      this.loadVideo(this.currentIndex);

      let start = Math.max(0, this.currentIndex - 2);
      let end = Math.min(this.allVideos.length, start + 5);
      this.start = start;
      this.visibleVideos = this.allVideos.slice(start, end);
      console.log(this.visibleVideos);
      this.initialSwipe = this.currentIndex - start;
      this.$refs.swipe.resize();
    },

    async onChange(index) {
      this.currentIndex = this.start + index;

      if (this.currentIndex >= this.allVideos.length - 5) {
        this.page += 1;
        list(this.page, 10).then(res => {
          console.log(res.data.list);
          this.allVideos = this.allVideos.concat(res.data.list);
        }).catch(error => {
          console.error(error);
        });
      }

      this.updateVisibleVideos();
    },

    //点赞
    async like(videoId) {
      try {
        const res = await like(videoId);

        if(res.code === 0) {
          this.visibleVideos[this.currentIndex - this.start].likeCount += 1;
        }
      } catch (error) {
        console.error(error);
      }
    },

    //打开评论
    openPl(videoId) {
      this.show = true;
      // this.loadComments(videoId);
    },
    async loadComments(videoId) {
      try {
        const res = await pl(videoId, this.page);
        if (document.querySelector(".van-action-sheet__content")) {
          document.querySelector(".van-action-sheet__content").scrollTop = 0;
        }
        this.plist = res.comments;
      } catch (error) {
        console.error(error);
      }
    },
    onLoad() {
      setTimeout(() => {
        this.page += 10;
        this.loadComments(this.visibleVideos[this.currentIndex - this.start].id);
        this.loading = false;
      }, 500);
    },
    navigateTo(tab) {
      if (tab === 'recommend') {
        this.page = 1;
        this.allVideos = [];
        this.visibleVideos = [];
        this.fetchVideos();
      } else if (tab === 'my') {
        router.push('/my-videos');
      }
    },
  },
};
</script>

<style scoped>
.home {
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
  z-index: 10002;
}
.nav-button {
  background: transparent;
  border: none;
  font-size: 20px;
  color: #ffffff;
  cursor: pointer;
  font-weight: bold;
}
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
