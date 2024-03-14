<template>
  <b-modal
    id="student-info-modal"
    title="Edit Student Information"
    @show="onModalShow"
    size="lg"
  >
    <b-card class="mb-2">
      <h5>Update Information</h5>

      <b-form @submit.prevent="confirmEdit">
        <!-- Field cho Name -->
        <b-form-group label="Name" label-for="student-name">
          <b-form-input
            id="student-name"
            v-model="inputStudent.name"
          ></b-form-input>
        </b-form-group>

        <!-- Field cho S-Nummer -->
        <b-form-group label="S-Nummer" label-for="student-snummer">
          <b-form-input
            id="student-snummer"
            v-model="inputStudent.snummer"
          ></b-form-input>
        </b-form-group>

        <!-- Button Confirm -->
        <b-button type="submit" variant="success">Confirm</b-button>
      </b-form>
    </b-card>
    <b-card>
      <h5>Upload New Images</h5>

      <b-row class="student-images">
        <b-col
          cols="4"
          class="p-1"
          v-for="(url, index) in (inputStudent.imgURL || []).slice(0, 3)"
          :key="index"
        >
          <b-img :src="url" alt="'Student image ' + (index + 1)" fluid></b-img>
        </b-col>
      </b-row>
      <!-- Form upload ảnh -->
      <div class="upload-images">
        <h5>Upload New Images:</h5>
        <b-row>
          <b-col md="4" v-for="index in 3" :key="index">
            <b-form-file
              v-model="uploadedImages[index]"
              :state="Boolean(uploadedImages[index])"
              placeholder="Choose a file..."
              drop-placeholder="Drop file here..."
              accept="image/*"
            ></b-form-file>
          </b-col>
        </b-row>
      </div>

      <!-- Button Confirm Upload -->
      <b-button variant="success" class="mt-3" @click="confirmUpload"
        >Confirm Upload</b-button
      >
    </b-card>
  </b-modal>
</template>

<script>
import { mapState, mapMutations, mapActions } from "vuex";
import axiosInstance from "../axiosConfig";

export default {
  name: "student-info-modal",
  data() {
    return {
      inputStudent: {}, // Biến cục bộ để lưu thông tin nhập từ người dùng
      uploadedImages: []
    };
  },
  computed: {
    ...mapState("student", {
      selectedStudent: state => state.selectedStudent
    })
  },
  methods: {
    ...mapMutations(["SET_SELECTED_STUDENT"]),
    onModalShow() {
      // Khi modal hiển thị, thiết lập inputStudent bằng selectedStudent
      if (this.selectedStudent) {
        this.inputStudent = { ...this.selectedStudent };
      }
    },
    ...mapActions({
      updateStudentInfo: "student/updateStudentInfo"
    }),
    confirmEdit() {
      this.updateStudentInfo(this.inputStudent);
      // Cập nhật selectedStudent trong Vuex

      // Gọi API để cập nhật thông tin học sinh trên server
      // Ví dụ: this.$store.dispatch('updateStudentInfo', this.inputStudent);

      // Đóng modal sau khi xác nhận
      this.$bvModal.hide("student-info-modal");
    },
    confirmUpload() {
      const formData = new FormData();
      formData.append("id", this.inputStudent.id);
      const token = localStorage.getItem("token");
      const authHeader = `${token}`;

      this.uploadedImages.forEach((image, index) => {
        if (image) {
          formData.append("images", image, image.name);
        }
      });
      axiosInstance
        .post("http://localhost:8081/student/updateIMG", formData, {
          headers: {
            "Content-Type": "multipart/form-data",
            AuthorizationToken: authHeader
          }
        })
        .then(response => {
          console.log("Success:", response);
          // Thêm bất kỳ xử lý thành công nào tại đây
        })
        .catch(error => {
          console.error("Error:", error);
          // Xử lý lỗi tại đây
        });
    }
  }
};
</script>
