<template>
  <tr>
    <td>{{ index + 1 }}</td>
    <td>{{ student.name }}</td>
    <td>{{ student.snummer }}</td>
    <td>
      <!-- Thêm các nút hoặc hành động tùy ý ở đây -->
      <button class="btn btn-primary btn-sm" @click="selectAndEditStudent()">
        Edit
      </button>
      <button class="btn btn-danger btn-sm" @click="removeStudent(student)">
        Remove
      </button>
      <button class="btn btn-warning btn-sm" @click="openCourseModal(student)">
        Course
      </button>
      <button class="btn btn-info btn-sm" @click="showTest(student)">
        Test
      </button>
    </td>
  </tr>
</template>

<script>
import { mapState, mapMutations, mapActions } from "vuex";

export default {
  name: "student-row-cpm",
  props: {
    student: Object,
    index: Number
  },
  methods: {
    ...mapMutations({
      setSelectedStudent: "student/SET_SELECTED_STUDENT"
    }),
    ...mapActions({
      deleteStudent: "student/deleteStudent"
    }),
    selectAndEditStudent() {
      this.setSelectedStudent(this.student); // Commit mutation để cập nhật selectedStudent
      this.$emit("edit"); // Mở modal chỉnh sửa
    },
    removeStudent(student) {
      this.deleteStudent(student.id);
    },
    showTest(student) {
      this.$emit("viewTestDetails", student.id);
    },
    openCourseModal(student) {
      console.log("emit");
      this.setSelectedStudent(student);
      this.$emit("openCourseModalEmit", student);
    }
  }
};
</script>

<style></style>
