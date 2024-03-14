import axiosInstance from "../../axiosConfig"; // Import axiosInstance đã cấu hình

export default {
  resetCourses({ commit }) {
    commit("RESET_COURSES");
  },
  async fetchCourses({ commit, state }) {
    const token = localStorage.getItem("token");
    if (!token) {
      console.error("No token found");
      return;
    }
    const authHeader = `${token}`;

    try {
      const response = await axiosInstance.get(
        "http://localhost:8081/course/",
        {
          headers: { AuthorizationToken: authHeader }
        }
      );
      // Giả sử response trả về một object và danh sách các khóa học nằm trong body của object đó
      commit("SET_COURSES", response.data);
      console.log("course from serve", response.data);
      console.log("courses state", state.courses);
      console.log("reponse", response);
    } catch (error) {
      console.error("There was an error fetching the courses:", error);
    }
  },
  async fetchOldCourses({ commit, state }, studentId) {
    const token = localStorage.getItem("token");
    if (!token) {
      console.error("No token found");
      return;
    }
    const authHeader = `${token}`;
    const url = "http://localhost:8081/course/" + studentId;
    try {
      const response = await axiosInstance.get(url, {
        headers: { AuthorizationToken: authHeader }
      });
      // Giả sử response trả về một object và danh sách các khóa học nằm trong body của object đó
      commit("SET_OLD_COURSES", response.data);
      commit("SET_OLD_COURSESID");
      console.log("courses all state", state.courses);
      console.log("courses old state", state.oldCourses);
      console.log("courses old state ID", state.oldCoursesId);
      console.log("reponse", response);
    } catch (error) {
      console.error("There was an error fetching the courses:", error);
    }
  },
  async registerCourseStudent(
    { commit, state },
    { studentID, selectedCourseIds }
  ) {
    console.log(
      "studentID",
      studentID,
      "  selectedCourse: ",
      selectedCourseIds
    );
    const token = localStorage.getItem("token");
    if (!token) {
      console.error("No token found");
      return;
    }
    const authHeader = `${token}`;
    const url = "http://localhost:8081/student/registerCourse";
    const requestData = {
      studentID: studentID,
      selectedCourseIds: selectedCourseIds
    };
    console.log("request", requestData);

    try {
      const response = await axiosInstance.post(url, requestData, {
        headers: { AuthorizationToken: authHeader }
      });

      console.log("reponse from serve", response.data);
      await this.dispatch("course/fetchCourses");

      console.log("courses state", state.courses);
    } catch (error) {
      console.error("There was an error :", error);
    }
  }
};
