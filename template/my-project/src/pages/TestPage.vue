<template>
  <div id="testPage">
    <b-container class="bg-light p-3 search">
      <h2>Test Verwaltung</h2>
      <b-row>
        <!-- Trường nhập liệu cho 'Name' -->
        <b-col cols="12" md="6" class="mb-3">
          <b-form-group
            label="Name"
            label-for="input-name"
            label-cols-md="4"
            label-cols-lg="3"
            label-align-sm="left"
          >
            <b-form-input
              id="input-name"
              v-model="name"
              placeholder="Enter name"
            ></b-form-input>
          </b-form-group>
        </b-col>

        <!-- Trường nhập liệu cho 'Professor Name' -->
        <b-col cols="12" md="6" class="mb-3">
          <b-form-group
            label="Professor Name"
            label-for="input-professor-name"
            label-cols-md="4"
            label-cols-lg="3"
            label-align-sm="left"
          >
            <b-form-input
              id="input-professor-name"
              v-model="professorName"
              placeholder="Enter professor name"
            ></b-form-input>
          </b-form-group>
        </b-col>
      </b-row>

      <b-row>
        <!-- Chọn khóa học -->
        <b-col cols="12" md="12" class="mb-3">
          <b-form-group
            label="Course:"
            label-cols-md="1"
            label-cols-lg="1"
            label-align-sm="left"
          >
            <b-form-checkbox-group
              v-model="selectedCourses"
              class="mb-3  text-left"
            >
              <b-form-checkbox
                v-for="course in courses"
                :key="course.courseId"
                :value="course.courseId"
                class="mb-2"
              >
                {{ course.name }}
              </b-form-checkbox>
            </b-form-checkbox-group>
          </b-form-group>
        </b-col>
      </b-row>

      <b-row>
        <!-- Nút tìm kiếm và thêm mới -->
        <b-col cols="12" class="mb-3 d-flex justify-content-end">
          <b-button variant="primary" class="mr-2" @click="search"
            >Suchen</b-button
          >
          <b-button variant="success" @click="openTestAddModal">Neu</b-button>
        </b-col>
      </b-row>
    </b-container>
    <b-container class="bg-light p-3 resultsearch">
      <!-- Bảng để hiển thị kết quả -->
      <table class="table table-striped table-bordered">
        <!-- Định nghĩa header của bảng -->
        <thead>
          <tr>
            <th>Nr</th>
            <th>Name</th>
            <th>Course</th>
            <th>Description</th>
            <th>Professor</th>
            <th>Funktionen</th>
          </tr>
        </thead>
        <!-- Nội dung bảng -->
        <tbody>
          <test-row-cpm
            v-for="(test, index) in testResults"
            :key="index"
            :test="test"
            :index="index"
            @editTest="openEditTestModal"
          ></test-row-cpm>
        </tbody>
      </table>
    </b-container>
    <test-add-modal></test-add-modal>
    <test-edit-modal></test-edit-modal>
  </div>
</template>
<script>
import { mapActions } from "vuex";
import { mapState } from "vuex";
import TestAddModal from "../component/TestAddModal";
import TestRowCpm from "../component/TestRowCpm";
import TestEditModal from "../component/TestEditModal.vue";

export default {
  name: "test-page",
  components: {
    TestRowCpm,
    TestAddModal,
    TestEditModal
  },
  methods: {
    ...mapActions({
      fetchCourses: "course/fetchCourses",
      searchTest: "test/searchTest"
    }),
    search() {
      // Thêm logic tìm kiếm ở đây
      console.log(
        "Searching with",
        this.name,
        this.professorName,
        this.selectedCourses
      );
      const searchParams = {
        name: this.name,
        professorName: this.professorName,
        courseIds: this.selectedCourses
      };
      this.searchTest(searchParams);
    },
    openTestAddModal() {
      // Thêm logic mở modal thêm mới ở đây
      this.$bvModal.show("test-add-modal");
    },
    openEditTestModal() {
      this.$bvModal.show("test-edit-modal");
    }
  },
  data() {
    return {
      name: "",
      professorName: "",
      selectedCourses: []
    };
  },
  computed: {
    ...mapState("course", {
      courses: state => state.courses
    }),
    ...mapState("student", {}),
    ...mapState("test", {
      testResults: state => state.searchResults
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

<style></style>
