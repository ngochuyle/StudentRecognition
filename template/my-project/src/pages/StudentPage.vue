<template>
  <div id="studentPage">
    <b-container class="bg-light p-3 search">
      <h2>Student Verwaltung</h2>
      <!-- Thêm màu nền và padding -->
      <b-row>
        <!-- Label và input field cho 'Name' -->
        <b-col cols="12" md="6" class="mb-3">
          <b-form-group
            label="Name"
            label-cols-md="4"
            label-cols-lg="3"
            label-align-sm="left"
            label-for="input-name"
          >
            <b-form-input
              id="input-name"
              v-model="name"
              placeholder="Enter name"
            ></b-form-input>
          </b-form-group>
        </b-col>

        <!-- Label và input field cho 'sNummer' -->
        <b-col cols="12" md="6" class="mb-3">
          <b-form-group
            label="sNummer"
            label-cols-md="4"
            label-cols-lg="3"
            label-align-sm="left"
            label-for="input-snummer"
          >
            <b-form-input
              id="input-snummer"
              v-model="sNummer"
              placeholder="Enter sNummer"
            ></b-form-input>
          </b-form-group>
        </b-col>
      </b-row>
      <b-row>
        <b-col cols="12" md="12" class="mb-3">
          <b-form-group
            label="Course:"
            label-cols-md="1"
            label-cols-lg="1"
            label-align-sm="left"
          >
            <b-form-checkbox-group
              v-model="selectedCourses"
              class="mb-3 text-left"
            >
              <!-- Sử dụng v-for để lặp qua mảng courses và tạo một checkbox cho mỗi khóa học -->
              <b-form-checkbox
                v-for="course in courses"
                :key="course.courseId"
                :value="course.courseId"
                class="mb-2"
              >
                {{ course.name }}
              </b-form-checkbox>
            </b-form-checkbox-group>
          </b-form-group></b-col
        >
      </b-row>
      <b-row>
        <b-col cols="12" class="mb-3 d-flex justify-content-end">
          <b-button variant="primary" class="mr-2" @click="search"
            >Suchen</b-button
          >
          <b-button variant="success" @click="openAddModal">Neu</b-button>
        </b-col>
      </b-row>
      <!-- Thêm các row và col khác tương tự nếu cần -->
    </b-container>
    <b-container class="bg-light p-3 resultsearch">
      <!-- Bảng để hiển thị kết quả -->
      <table class="table table-striped table-bordered">
        <!-- Định nghĩa header của bảng -->
        <thead>
          <tr>
            <th>Nr</th>
            <th>Name</th>
            <th>sNummer</th>
            <th>Funktion</th>
          </tr>
        </thead>
        <!-- Nội dung bảng -->
        <tbody>
          <student-row-cpm
            v-for="(item, index) in searchResults"
            :key="index"
            :student="item"
            :index="index"
            @onRemoveStudent="handleRemoveStudent"
            @edit="openEditModal"
            @viewTestDetails="handleViewTestDetails"
            @openCourseModalEmit="handleCourseModal"
          ></student-row-cpm>
        </tbody>
      </table>
    </b-container>
    <student-add-modal></student-add-modal>
    <student-info-modal></student-info-modal>
    <student-test-modal></student-test-modal>
    <student-course-modal></student-course-modal>
  </div>
</template>
<script>
import { mapActions } from "vuex";
import { mapState } from "vuex";
import StudentAddModal from "../component/StudentAddModal";
import StudentRowCpm from "../component/StudentRowCpm";
import StudentInfoModal from "../component/StudentInfoModal";
import StudentTestModal from "../component/StudentTestModal";
import StudentCourseModal from "../component/StudentCourseModal";
export default {
  name: "student-page",
  components: {
    StudentRowCpm,
    StudentAddModal,
    StudentInfoModal,
    StudentTestModal,
    StudentCourseModal
  },
  data() {
    return {
      name: "",
      sNummer: "",
      selectedCourses: []
    };
  },
  methods: {
    ...mapActions({
      fetchCourses: "course/fetchCourses",
      searchStudents: "student/searchStudents", // Nếu bạn đang sử dụng namespaced trong Vuex
      deleteStudent: "student/deleteStudent",
      fetchStudentTests: "student/fetchStudentTests",
      fetchOldCourses: "course/fetchOldCourses"
    }),
    openAddModal() {
      this.$bvModal.show("student-add-modal");
    },
    search() {
      // Tạo object tìm kiếm
      const searchParams = {
        name: this.name,
        sNummer: this.sNummer,
        CourseIDs: this.selectedCourses
      };
      this.searchStudents(searchParams);
      this.name = "";
      this.sNummer = "";
    },
    handleRemoveStudent(id) {
      this.deleteStudent(id);
      // Thêm bất kỳ logic cập nhật UI nào ở đây nếu cần
    },
    openEditModal() {
      this.$bvModal.show("student-info-modal");
    },
    handleViewTestDetails(studentId) {
      console.log("studentID view:" + studentId);
      this.fetchStudentTests(studentId);
      this.$bvModal.show("student-test-modal");
    },
    handleCourseModal(student) {
      console.log("call Course Modal");
      this.fetchOldCourses(student.id)
        .then(() => {
          console.log(
            "Cha oldcourses:",
            this.oldCourses,
            "ID: ",
            this.oldCoursesID
          );
          this.$bvModal.show("student-course-modal"); // Hiển thị modal sau khi dữ liệu đã được tải
        })
        .catch(error => {
          console.error("Error fetching old courses:", error);
        });
    }
  },
  computed: {
    ...mapState("course", {
      courses: state => state.courses,
      oldCourses: state => state.oldCourses
    }),
    ...mapState("student", {
      searchResults: state => state.searchResults,
      selectedStudent: state => state.selectedStudent
    })
  },
  mounted() {
    // Gọi action được map khi component được mount
    this.fetchCourses();
  },
  watch: {
    selectedCourses: {
      handler(newSelectedCourses, oldSelectedCourses) {
        console.log("Selected Courses:", newSelectedCourses);
      },
      deep: true // Sử dụng deep để theo dõi các thay đổi bên trong mảng
    }
  }
};
</script>

<style>
/* Add your CSS styles for the form here */
.custom {
  text-align: left;
}
</style>
