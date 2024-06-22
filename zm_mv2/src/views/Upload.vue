<template>
  <div class = "upload">
    <div>
      <div class="content">
        <video v-if="videoPreview" controls class="video-preview" :src="videoPreview"></video>
        <img v-if="coverPreview" class="cover-preview" :src="coverPreview" alt="封面预览">
        <div class="part">
          <div class="label">上传视频文件：</div>
          <input type="file" accept="video/*" @change="handleVideoFile" placeholder="上传视频文件">
        </div>
        <div class="part">
          <div class="label">上传视频封面：</div>
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
import {upload} from "@request/api";

document.body.style.backgroundColor = '#000000';

const videoFile = ref(null);
const coverFile = ref(null);
const videoPreview = ref(null);
const coverPreview = ref(null);
const introduction = ref('');

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

  try {
    const res = await upload(formData);
    console.log(res);
    if (res.data.code === 0) {
      alert('上传成功');
      router.push('/mine');
    } else {
      alert('上传失败');
    }
  } catch (e) {
    console.error(e);
    alert('上传失败');
  }

};
</script>

<style scoped>
.upload {
  padding-bottom: 64px;
  position: relative;
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
  color: #f4f4f4;
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