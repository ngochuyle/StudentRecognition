<template>
  <b-modal id="student-add-modal" title="Add New Student" hide-footer>
    <b-form @submit.prevent="onSubmit">
      <b-form-group label="Name" label-for="student-name">
        <b-form-input
          id="student-name"
          v-model="student.name"
          type="text"
          required
          placeholder="Enter student name"
        ></b-form-input>
      </b-form-group>

      <b-form-group label="sNummer" label-for="student-snummer">
        <b-form-input
          id="student-snummer"
          v-model="student.sNummer"
          type="text"
          required
          placeholder="Enter sNummer"
        ></b-form-input>
      </b-form-group>

      <div class="mb-3" v-for="(image, index) in images" :key="index">
        <b-form-group
          :label="'Upload Image ' + (index + 1)"
          label-for="'student-file-' + index"
        >
          <b-form-file
            :id="'student-file-' + index"
            v-model="images[index]"
            :state="Boolean(images[index])"
            accept="image/*"
            placeholder="Choose an image or drop it here..."
            drop-placeholder="Drop image here..."
          ></b-form-file>
        </b-form-group>
      </div>

      <b-button type="submit" variant="primary" @click="closeModal"
        >Submit</b-button
      >
      <b-button type="button" variant="danger" @click="closeModal"
        >Cancel</b-button
      >
    </b-form>
  </b-modal>
</template>

<script>
import axiosInstance from "../axiosConfig";

export default {
  name: "student-add-modal",
  data() {
    return {
      student: {
        name: "",
        sNummer: ""
      },
      images: [null, null, null]
    };
  },
  methods: {
    onSubmit() {
      const formData = new FormData();
      formData.append("name", this.student.name);
      formData.append("sNummer", this.student.sNummer);
      const token = localStorage.getItem("token");
      const authHeader = `${token}`;

      this.images.forEach((image, index) => {
        if (image) {
          formData.append("images", image, image.name);
        }
      });
      const startTime = Date.now();
      axiosInstance
        .post("http://localhost:8081/student/create", formData, {
          headers: {
            "Content-Type": "multipart/form-data",
            AuthorizationToken: authHeader
          }
        })
        .then(response => {
          console.log("Success:", response);
          const endTime = Date.now();
          const duration = endTime - startTime;
          console.log("Thời gian phản hồi API: ", duration, "ms");
          this.closeModal();
          // Thêm bất kỳ xử lý thành công nào tại đây
        })
        .catch(error => {
          console.error("Error:", error);
          // Xử lý lỗi tại đây
        });
    },
    closeModal() {
      // Đóng modal sau khi thực hiện xong
      this.$bvModal.hide("student-add-modal");
    }
  }
};
</script>

<style></style>
