<template>
  <tr>
    <td>{{ index + 1 }}</td>
    <!-- Số thứ tự -->
    <td>{{ test.title }}</td>
    <!-- Tên bài test -->
    <td>{{ test.coursename }}</td>
    <!-- Tên khóa học -->
    <td>{{ test.description }}</td>
    <!-- Mô tả -->
    <td>{{ test.professor }}</td>
    <!-- Tên giáo sư -->
    <td>
      <!-- Các button hoặc actions khác -->
      <button class="btn btn-primary btn-sm" @click="selectAndEditTest()">
        Edit
      </button>
      <button class="btn btn-danger btn-sm" @click="removeTest()">
        Remove
      </button>
      <button class="btn btn-warning btn-sm" @click="openTest()">
        Test
      </button>
    </td>
  </tr>
</template>

<script>
import { mapState, mapMutations, mapActions } from "vuex";

export default {
  name: "test-row-cpm",
  props: {
    test: Object, // Dữ liệu của bài test
    index: Number // Số thứ tự
  },

  methods: {
    ...mapMutations("test", {
      handleSelectTest: "SET_SELECTED_STUDENT"
    }),

    ...mapActions({
      deleteTest: "test/deleteTest"
    }),

    ...mapActions({
      getStudentTestData: "student/getStudentTestData"
    }),
    selectAndEditTest() {
      this.handleSelectTest(this.test);
      console.log(this.selectedTest);
      this.$emit("editTest"); // Mở modal chỉnh sửa
    },
    removeTest() {
      console.log("delete in component: ", this.test.id);
      this.deleteTest(this.test.id);
    },
    openTest() {
      this.handleSelectTest(this.test);
      this.getStudentTestData(this.test.id);
      console.log("selected Test: ", this.selectedTest);
      this.$router.push("/test/detail");
    }
  },
  computed: {
    ...mapState("test", {
      selectedTest: state => state.selectedTest
    })
  }
};
</script>
