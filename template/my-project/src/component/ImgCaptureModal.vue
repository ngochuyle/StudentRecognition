<template>
  <b-modal
    id="img-capture-modal"
    title="Camera"
    @shown="startCamera"
    @hidden="stopCamera"
    @ok="onModalOk"
  >
    <!-- Khung camera -->
    <div class="camera-container">
      <video id="camera-stream" width="400" height="300"></video>
    </div>

    <!-- Khung hiển thị ảnh đã chụp -->
    <div class="image-preview-container">
      <canvas id="image-preview" width="400" height="300"></canvas>
    </div>

    <!-- Nút chụp ảnh -->
    <b-button variant="primary" @click="captureImage">Chụp Ảnh</b-button>
  </b-modal>
</template>

<script>
import { mapActions } from "vuex";
import { mapState } from "vuex";
export default {
  name: "img-capture-modal",
  data() {
    return {
      capturedBlob: null // Biến lưu trữ dữ liệu blob
    };
  },
  methods: {
    ...mapActions({
      searchIMG: "test/searchIMG"
    }),
    captureImage() {
      const cameraStreamElement = document.getElementById("camera-stream");
      const canvasElement = document.getElementById("image-preview");
      const context = canvasElement.getContext("2d");
      context.drawImage(cameraStreamElement, 0, 0, 400, 300);

      canvasElement.toBlob(
        blob => {
          this.capturedBlob = blob;
        },
        "image/jpeg",
        0.9
      );
    },

    startCamera() {
      navigator.mediaDevices
        .getUserMedia({ video: true })
        .then(stream => {
          const cameraStreamElement = document.getElementById("camera-stream");
          cameraStreamElement.srcObject = stream;
          cameraStreamElement.play();
        })
        .catch(err => {
          console.error("Error accessing camera:", err);
        });
    },

    stopCamera() {
      const cameraStreamElement = document.getElementById("camera-stream");
      if (cameraStreamElement && cameraStreamElement.srcObject) {
        const tracks = cameraStreamElement.srcObject.getTracks();
        tracks.forEach(track => track.stop());
      }
    },
    onModalOk() {
      // Xử lý dữ liệu blob khi nhấn OK
      if (this.capturedBlob !== null) {
        console.log("in Modal", this.capturedBlob);
        this.searchIMG(this.capturedBlob);
      }
      console.log("OK clicked, processing blob...");
      // Gửi dữ liệu blob đến server hoặc xử lý tùy theo nhu cầu
    }
  }
};
</script>

<style>
.camera-container,
.image-preview-container {
  margin-bottom: 15px;
  border: 1px solid #ddd;
  background-color: #f9f9f9;
  text-align: center;
}

/* Định dạng cho khung hiển thị ảnh */
#image-preview {
  display: block;
  margin: auto;
}
</style>
