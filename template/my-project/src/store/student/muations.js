import Vue from "vue";
export default {
  SET_TOKEN(state, token) {
    state.token = token;
  },
  SET_ADMIN(state, { username }) {
    state.admin.username = username;
  },
  RESET_ADMIN(state) {
    state.admin.username = null; // Đặt giá trị username trong admin về null
  },
  RESET_TOKEN(state) {
    state.token = null; // Đặt giá trị token về null
  },
  SET_SEARCH_PARAMS(state, params) {
    state.searchParams = params;
  },
  SET_SEARCH_RESULTS(state, searchResults) {
    state.searchResults = searchResults; // Cập nhật giá trị searchResults
  },
  RESET_SEARCH_RESULTS(state) {
    state.admin.searchResults = []; // Đặt giá trị username trong admin về null
  },
  SET_UPDATED_STUDENT(state, updatedStudent) {
    // Cập nhật thông tin học sinh trong state.searchResults
    const index = state.searchResults.findIndex(
      s => s.id === updatedStudent.id
    );
    if (index !== -1) {
      Vue.set(state.searchResults, index, updatedStudent);
    }
  },
  SET_SELECTED_STUDENT(state, student) {
    state.selectedStudent = student;
  },
  SET_STUDENT_TESTS(state, tests) {
    state.studentTests = tests; // Cập nhật state với dữ liệu mới
  },

  SET_ALL_STUDENTS(state, students) {
    state.allStudents = students;
  },
  SET_FILTERED_STUDENTS(state, students) {
    state.filterStudents = students;
  },
  SET_SELECTED_TEST_STUDENT(state, student) {
    state.selectedTestStudent = student;
  },
  UPDATE_PARTICIPATION_STATUS(state, testRegistrationId) {
    // Tìm chỉ số của học sinh cần cập nhật trong mảng
    const studentIndex = state.filterStudents.findIndex(
      s => s.testRegistrationId === testRegistrationId
    );

    // Nếu tìm thấy học sinh, cập nhật participated của học sinh đó
    if (studentIndex !== -1) {
      Vue.set(state.filterStudents[studentIndex], "participated", "T");
    }
  },
  UPDATE_PARTICIPATION_IMGSTATUS(state, sNummer) {
    // Tìm chỉ số của học sinh có sNummer cần cập nhật trong mảng
    const studentIndex = state.filterStudents.findIndex(
      student => student.snummer === sNummer
    );

    // Nếu tìm thấy học sinh, thực hiện cập nhật
    if (studentIndex !== -1) {
      // Cập nhật filterStudents để chỉ chứa học sinh đã cập nhật
      state.filterStudents = [state.filterStudents[studentIndex]];
    }
  }
};
