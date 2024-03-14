<template>
  <div class="camera-capture">
    <b-container>
      <!-- Video Stream and Captured Image -->
      <b-row>
        <b-col>
          <div class="media-container">
            <video id="video" width="320" height="240" autoplay></video>
            <canvas id="canvas" width="320" height="240"></canvas>
          </div>
        </b-col>
      </b-row>

      <!-- Capture Button -->
      <b-row>
        <b-col class="text-center">
          <b-button variant="primary" @click="captureImage">Chụp Ảnh</b-button>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
export default {
  name: "capture-cpm",
  data() {
    return {
      showIMG: true
    };
  },
  methods: {
    captureImage() {
      const video = document.getElementById("video");
      const canvas = document.getElementById("canvas");
      const context = canvas.getContext("2d");
      context.drawImage(video, 0, 0, 320, 240);
    },
    setupCamera() {
      const video = document.getElementById("video");

      navigator.mediaDevices
        .getUserMedia({ video: true })
        .then(stream => {
          video.srcObject = stream;
          video.play();
        })
        .catch(err => {
          console.error("Có lỗi xảy ra khi truy cập camera: ", err);
        });
    }
  },
  mounted() {
    this.setupCamera();
  }
};
</script>

<style scoped>
.media-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

#video,
#canvas {
  border: 1px solid black;
  margin-bottom: 10px; /* Tạo khoảng cách giữa video và canvas */
}
</style>
