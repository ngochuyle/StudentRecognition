<template>
  <b-modal
    id="student-course-modal"
    title="Course Selection"
    @show="onModalShow"
    hide-footer
    size="lg"
  >
    <!-- Card cho thông tin sinh viên -->
    <b-card class="mb-3">
      <b-row>
        <!-- Cột cho Name -->
        <b-col cols="6">
          <h5>Name: {{ inputStudent.name }}</h5>
        </b-col>
        <!-- Cột cho sNummer -->
        <b-col cols="6">
          <h5>sNummer: {{ inputStudent.snummer }}</h5>
        </b-col>
      </b-row>
    </b-card>

    <!-- Card cho danh sách khóa học -->
    <b-card>
      <b-form-checkbox-group v-model="selectedCourses">
        <div v-for="course in courses" :key="course.courseId">
          <b-form-checkbox :value="course.courseId">
            {{ course.name }}
          </b-form-checkbox>
        </div>
      </b-form-checkbox-group>
    </b-card>
    <!-- Nút Save và Cancel -->
    <div class="text-right mt-3">
      <b-button variant="secondary" @click="closeModal">
        Cancel
      </b-button>
      <b-button variant="primary" @click="saveChanges">
        Save
      </b-button>
    </div>
  </b-modal>
</template>
<script>
import { mapActions } from "vuex";

import { mapState } from "vuex";
export default {
  name: "student-course-modal",
  data() {
    return {
      inputStudent: {},
      selectedCourses: [] // Biến cục bộ để lưu thông tin nhập từ người dùng
    };
  },
  computed: {
    ...mapState("student", {
      selectedStudent: state => state.selectedStudent
    }),
    ...mapState("course", {
      courses: state => state.courses,
      oldCourses: state => state.oldCourses,
      oldCoursesId: state => state.oldCoursesId
    })
  },
  methods: {
    ...mapActions({
      fetchOldCourses: "course/fetchOldCourses",
      registerCourseStudent: "course/registerCourseStudent"
    }),
    onModalShow() {
      // Khi modal hiển thị, thiết lập inputStudent bằng selectedStudent
      this.fetchOldCourses(this.selectedStudent.id);
      if (this.selectedStudent) {
        this.inputStudent = { ...this.selectedStudent };
      }
      this.selectedCourses = this.oldCourses.map(course => course.courseId);
      console.log("oldcourses:      ", this.oldCourses);
      console.log("selectedCourses:      ", this.selectedCourses);
    },
    saveChanges() {
      // Logic lưu thay đổi
      console.log("Saving changes...");
      console.log(this.selectedStudent.id, this.selectedCourses);
      this.registerCourseStudent({
        studentID: this.selectedStudent.id,
        selectedCourseIds: this.selectedCourses
      });
      this.$bvModal.hide("student-course-modal");
    },
    closeModal() {
      this.$bvModal.hide("student-course-modal");
    }
  },
  watch: {
    selectedCourses: {
      handler(newSelectedCourses, oldSelectedCourses) {
        // In giá trị mới của selectedCourses
        console.log("Selected Courses:", newSelectedCourses);
      },
      deep: true // Theo dõi sâu vào các phần tử bên trong mảng
    }
  },
  mounted() {
    // Trước khi mở Modal, gán giá trị từ oldCoursesId cho selectedOldCoursesId
    console.log("oldCoursesId mounted ", this.oldCourses);
    this.selectedOldCoursesId = this.oldCoursesId;
  }
};
</script>

<style></style>
