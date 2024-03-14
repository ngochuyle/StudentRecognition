<template>
  <div id="testDetailPage">
    <b-container class="bg-light p-3">
      <h2>Test Detail</h2>

      <!-- Hàng cho Title -->
      <b-row class="mb-3">
        <b-col md="3" class="text-left">
          <label>Title: </label>
        </b-col>
        <b-col md="3" class="text-left">
          <b-form>{{ selectedTest.title }}</b-form>
        </b-col>
        <b-col md="3" class="text-left">
          <label>Course Name: </label>
        </b-col>
        <b-col md="3" class="text-left">
          <b-form>{{ selectedTest.coursename }}</b-form>
        </b-col>
      </b-row>

      <!-- Hàng cho Course Name và Description -->

      <b-row class="mb-3">
        <b-col md="3" class="text-left">
          <label>Description: </label>
        </b-col>
        <b-col md="3" class="text-left">
          <b-form>{{ selectedTest.description }}</b-form>
        </b-col>
        <b-col md="3 " class="text-left">
          <label>Professor: </label>
        </b-col>
        <b-col md="3" class="text-left">
          <b-form>{{ selectedTest.professor }}</b-form>
        </b-col>
      </b-row>

      <b-row class="mb-3">
        <b-col md="2" class="text-left">
          <label for="input-name">Name</label>
        </b-col>
        <b-col md="4" class="text-left">
          <b-form-input
            id="input-name"
            v-model="searchName"
            placeholder="Enter name"
          ></b-form-input>
        </b-col>

        <b-col md="2" class="text-left">
          <label for="input-snummer">sNummer</label>
        </b-col>
        <b-col md="4" class="text-left">
          <b-form-input
            id="input-snummer"
            v-model="searchSNummer"
            placeholder="Enter sNummer"
          ></b-form-input>
        </b-col>
      </b-row>

      <b-row class="mb-3 justify-content-end">
        <!-- Nút Suchen -->
        <b-button variant="primary" class="mr-2" @click="searchStudents"
          >Suchen</b-button
        >
        <b-button variant="info" class="mr-2" @click="searchStudentImg"
          >Suchen Image</b-button
        >
        <!-- Nút Back -->
        <b-button variant="secondary" @click="onBackClick">Back</b-button>
      </b-row>
    </b-container>
    <b-container class="bg-light p-3">
      <table class="table table-bordered">
        <thead>
          <tr>
            <th>#</th>
            <th>Name</th>
            <th>sNummer</th>
            <th>Score</th>
            <th>Participated</th>
            <th>Funktion</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(student, index) in filteredStudents" :key="student.id">
            <td>{{ index + 1 }}</td>
            <td>{{ student.name }}</td>
            <td>{{ student.snummer }}</td>
            <input type="number" max="10" v-model="student.score" />
            <td>{{ student.participated }}</td>
            <td>
              <!-- Ví dụ về các nút -->
              <b-button variant="primary" @click="handelUpdateScore(student)"
                >Enter Score</b-button
              >
              <b-button variant="danger" @click="onGrade(student)"
                >Grade</b-button
              >
            </td>
          </tr>
        </tbody>
      </table>
    </b-container>
    <grade-modal></grade-modal>
    <img-capture-modal></img-capture-modal>
    <error-modal></error-modal>
  </div>
</template>

<script>
import HeaderCpm from "../component/HeaderCpm";
import { mapActions } from "vuex";
import { mapState } from "vuex";
import { mapMutations } from "vuex";
import GradeModal from "../component/GradeModal";
import ImgCaptureModal from "../component/ImgCaptureModal.vue";
import ErrorModal from "../component/ErrorModal.vue";
export default {
  name: "test-detail-page",
  components: { GradeModal, ImgCaptureModal, ErrorModal },
  computed: {
    ...mapState("test", {
      selectedTest: state => state.selectedTest
    }),
    ...mapState("student", {
      allStudents: state => state.allStudents,
      selectedTestStudent: state => state.selectedTestStudent,
      filteredStudents: state => state.filterStudents
    })
  },
  methods: {
    ...mapActions({
      updateScore: "test/updateScore"
    }),
    ...mapMutations({
      setSelectTestStudent: "student/SET_SELECTED_TEST_STUDENT",
      setFilteredStudents: "student/SET_FILTERED_STUDENTS"
    }),
    searchStudentImg() {
      this.$bvModal.show("img-capture-modal");
    },
    searchStudents() {
      const filtered = this.allStudents.filter(student => {
        return (
          student.name.includes(this.searchName) &&
          student.snummer.includes(this.searchSNummer)
        );
      });
      this.setFilteredStudents(filtered);
    },
    handelUpdateScore(student) {
      const scoreParam = {
        score: student.score,
        id: student.id,
        testRegistrationId: student.testRegistrationId
      };
      console.log("Updated score:", scoreParam);
      this.updateScore(scoreParam);
    },
    onSearchClick() {},
    onBackClick() {
      this.$router.push("/home/test/");
    },
    onGrade(student) {
      this.setSelectTestStudent(student);
      console.log("selectedStudent", this.selectedTestStudent);
      this.$bvModal.show("grade-modal");
    }
  },
  data() {
    return { searchName: "", searchSNummer: "" };
  },
  mounted() {
    // Đặt giá trị ban đầu của filteredStudents bằng với allStudents
  }
};
</script>

<style></style>
