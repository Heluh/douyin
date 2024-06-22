<template>
  <div class="upload">
    <van-nav-bar
        title="视频上传"
        left-text="返回"
        left-arrow
        @click-left="onClickLeft"
        style="background: linear-gradient(135deg, #6a11cb 0%, #2575fc 100%); color: white;"
    />
    <div class="content">
      <div class="preview-container">
        <video v-if="videoPreview" controls class="video-preview" :src="videoPreview"></video>
        <img v-if="coverPreview" class="cover-preview" :src="coverPreview" alt="封面预览">
      </div>
      <div class="part">
        <div class="label">上传视频文件：</div>
        <input type="file" accept="video/*" @change="handleVideoFile" class="file-input">
      </div>
      <div class="part">
        <div class="label">上传封面图片：</div>
        <input type="file" accept="image/*" @change="handleCoverFile" class="file-input">
      </div>
      <van-field
          v-model="introduction"
          placeholder="输入视频简介"
          required
          clearable
          class="intro-field"
          label="视频简介"
          label-width="80px"
      />
      <van-button type="info" @click="submitUpload" class="confirm-button">确认上传</van-button>
    </div>
  </div>
</template>

<script setup>
import router from '../router';
import { ref } from 'vue';
import { upload } from '@request/api';
import { Toast, Dialog } from 'vant';

const videoFile = ref(null);
const coverFile = ref(null);
const videoPreview = ref(null);
const coverPreview = ref(null);
const introduction = ref('');

// eslint-disable-next-line no-unused-vars
const handleVideoFile = (event) => {
  videoFile.value = event.target.files[0];
  videoPreview.value = URL.createObjectURL(videoFile.value);
};

// eslint-disable-next-line no-unused-vars
const handleCoverFile = (event) => {
  coverFile.value = event.target.files[0];
  coverPreview.value = URL.createObjectURL(coverFile.value);
};

// eslint-disable-next-line no-unused-vars
const submitUpload = async () => {
  if (!videoFile.value || !introduction.value) {
    Toast.fail('请完整填写视频文件和简介');
    return;
  }

  const formData = new FormData();
  formData.append('video', videoFile.value);
  if (coverFile.value) {
    formData.append('cover', coverFile.value);
  }
  formData.append('introduction', introduction.value);

  try {
    const res = await upload(formData);
    if (res.data.code === 0) {
      Toast.success('上传成功');
      router.push('/mine');
    } else {
      Dialog.alert({
        title: '上传失败',
        message: res.data.message || '上传失败，请稍后重试'
      });
    }
  } catch (e) {
    console.error(e);
    Dialog.alert({
      title: '上传失败',
      message: '上传失败，请稍后重试'
    });
  }
};

// eslint-disable-next-line no-unused-vars
const onClickLeft = () => {
  router.back();
};
</script>

<style scoped>
.upload {
  padding-bottom: 64px;
  background: linear-gradient(135deg, #6a11cb 0%, #2575fc 100%);
  min-height: 100vh;
}

.content {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 12% auto;
  width: 90%;
  padding: 20px;
  background-color: rgba(255, 255, 255, 0.9);
  border-radius: 10px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  animation: fadeIn 1s ease-in-out;
}

.preview-container {
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  margin-bottom: 20px;
}

.video-preview {
  max-width: 300px; /* 控制视频显示大小 */
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  margin-bottom: 20px;
  transition: transform 0.3s ease-in-out;
}

.cover-preview {
  max-width: 100%;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  margin-bottom: 20px;
  transition: transform 0.3s ease-in-out;
}

.video-preview:hover, .cover-preview:hover {
  transform: scale(1.05);
}

.part {
  margin: 10px 0;
  width: 100%;
}

.label {
  font-size: 16px;
  color: #333;
  margin-bottom: 5px;
}

.file-input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  background-color: #fff;
  color: #333;
  cursor: pointer;
}

.van-button {
  margin: 10px 0;
  width: 100%;
}

.upload-button {
  background-color: #ff9800;
  border: none;
  border-radius: 8px;
  color: #fff;
  transition: background-color 0.3s ease;
}

.upload-button:hover {
  background-color: #e68900;
}

.intro-field {
  width: 100%;
  margin-bottom: 20px;
}

.confirm-button {
  background-color: #4caf50;
  border: none;
  border-radius: 8px;
  color: #fff;
  transition: background-color 0.3s ease;
}

.confirm-button:hover {
  background-color: #388e3c;
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>





