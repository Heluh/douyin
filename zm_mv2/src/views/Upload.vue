<template>
  <div class = "upload">
    <div class="top-nav">
      <button class="nav-button1" @click="navigateTo('recommend')">推荐</button>
      <button class="nav-button2" @click="navigateTo('upload')">+</button>
      <button class="nav-button3" @click="navigateTo('my')">我的</button>
    </div>
    <div>
      <div class="content">
        <video v-if="videoPreview" controls class="video-preview" :src="videoPreview"></video>
        <img v-if="coverPreview" class="cover-preview" :src="coverPreview" alt="封面预览">
        <div class="part">
          <div class="label">上传视频文件：</div>
          <input type="file" accept="video/*" @change="handleVideoFile" placeholder="上传视频文件">
        </div>
        <div class="part">
          <div class="label">上传视频文件：</div>
          <input type="file" accept="image/*" @change="handleCoverFile" placeholder="上传封面图片">
        </div>
        <input type="text" v-model="introduction" placeholder="输入视频简介">
        <button class="confirm" @click="submitUpload">确认上传</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import router from '../router';
import { ref, reactive } from 'vue';

document.body.style.backgroundColor = '#000000';

const videoFile = ref(null);
const coverFile = ref(null);
const videoPreview = ref(null);
const coverPreview = ref(null);
const introduction = ref('');

const navigateTo = (tab) => {
  const token = localStorage.getItem('token');
  if (tab === 'recommend') {
    this.page = 1;
    this.allVideos = [];
    this.visibleVideos = [];
    this.fetchVideos();
  } else if (tab === 'my' && !token) {
    this.showPopup = true;
    this.afterLogin = () => this.navigateTo(tab);
  } else if (tab === 'my' && token) {
    router.push('/my-videos');
  }else if (tab === 'upload' && !token) {
    this.showPopup = true;
    this.afterLogin = () => this.navigateTo(tab);
  } else {
    router.push('/upload');
  }
};
const handleVideoFile = (event) => {
  videoFile.value = event.target.files[0];
  videoPreview.value = URL.createObjectURL(videoFile.value);
};

const handleCoverFile = (event) => {
  coverFile.value = event.target.files[0];
  coverPreview.value = URL.createObjectURL(coverFile.value);
};

const submitUpload = async () => {
  if (!videoFile.value || !coverFile.value || !introduction.value) {
    alert("请完整填写视频文件、封面图片和简介");
    return;
  }

  const formData = new FormData();
  formData.append('video', videoFile.value);
  formData.append('cover', coverFile.value);
  formData.append('introduction', introduction.value);

  // 模拟上传过程，实际应替换为适当的 API 调用
  console.log('开始上传...');
  await new Promise(resolve => setTimeout(resolve, 2000)); // 模拟上传时间
  console.log('上传完成');
  alert('视频上传成功!');
};
</script>

<style scoped>
.upload {

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

.content {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 12%;
}

.video-preview, .cover-preview {
  max-width: 90%;
  margin-bottom: 20px;
}

input[type="file"], input[type="text"] {
  margin: 10px 0;
  padding: 10px;
  width: 66%;
  background-color: transparent;
  border: none;
}

.confirm{
  padding: 10px;
  width: 66%;
  background-color: #a1d1f5;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
  font-weight: bold;
  margin-top: 20px;
}
.label{
  font-size: 16px;
  color: #f4f4f4;
}
</style>