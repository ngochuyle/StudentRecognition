import axios from "axios";
import axiosInstance from "../../axiosConfig"; // Import axiosInstance đã cấu hình
import queryString from "querystring";

export default {
  async login({ commit }, { username, password, router }) {
    const credentials = btoa(`${username}:${password}`);
    const authHeader = `Basic ${credentials}`;
    const url = "http://localhost:8081/signin";
    console.log(authHeader);
    try {
      const response = await axiosInstance.get(url, {
        headers: { AuthorizationBasic: authHeader }
      });

      if (response.status === 200 || response.status === 202) {
        const token = response.headers["token"];
        commit("SET_TOKEN", token);
        localStorage.setItem("token", token);
        console.log("username", username);
        commit("SET_ADMIN", { username });
        router.push("/home");
      } else {
        console.error(`Server error: ${response.status}`);
      }
    } catch (error) {
      console.error(`Network error: ${error}`);
    }
  },
  logout({ commit, dispatch }) {
    // Thực hiện các bước logout ở đây
    // Ví dụ:

    // Reset giá trị trong state admin và token
    commit("RESET_ADMIN");
    commit("RESET_TOKEN");
    dispatch("course/resetCourses", null, { root: true });
    // Xóa dữ liệu trong local storage (nếu bạn đã lưu thông tin đăng nhập vào local storage)
    localStorage.removeItem("token");

    // Điều hướng về trang login (hoặc trang bạn muốn điều hướng sau khi logout)
  },
  async searchStudents({ commit, state }, searchParams) {
    commit("RESET_SEARCH_RESULTS");
    const token = localStorage.getItem("token");
    if (!token) {
      console.error("No token found");
      return;
    }

    // Tạo header cho request API
    const authHeader = `${token}`;
    console.log("search token", authHeader);
    const queryURl = queryString.stringify(searchParams);
    console.log(queryURl);
    const apiUrl = "http://localhost:8081/student/search?";
    const finalURL = apiUrl + queryURl;
    console.log("URL :", finalURL);
    try {
      // Gọi API với tham số tìm kiếm từ state.searchParams
      const response = await axiosInstance.get(
        finalURL, // Thay đổi URL API tìm kiếm sinh viên
        {
          headers: { AuthorizationToken: authHeader }
        }
      );

      // Cập nhật kết quả tìm kiếm vào state
      commit("SET_SEARCH_RESULTS", response.data);
      console.log("Search results:", response.data);
      console.log("Search results in state:", state.searchResults);
    } catch (error) {
      console.error("Error searching students:", error);
    }
  },
  async deleteStudent({ commit, state }, studentId) {
    const token = localStorage.getItem("token");
    try {
      // Gọi API để xóa học sinh
      const response = await axiosInstance({
        method: "post",
        url: `http://localhost:8081/student/delete/${studentId}`,
        headers: { AuthorizationToken: token }
      });

      console.log("Response remove:", response);
      // Kiểm tra phản hồi và cập nhật state nếu xóa thành công
      if (response.status === 200) {
        const updatedResults = state.searchResults.filter(
          student => student.id !== studentId
        );
        commit("SET_SEARCH_RESULTS", updatedResults);
      }
    } catch (error) {
      console.error("Error deleting student:", error);
    }
  },
  updateStudentInfo({ commit }, student) {
    const formData = new FormData();
    formData.append("id", student.id);
    formData.append("name", student.name);
    formData.append("sNummer", student.snummer);
    const token = localStorage.getItem("token");
    const authHeader = `${token}`;

    axiosInstance
      .post("http://localhost:8081/student/update", formData, {
        headers: {
          "Content-Type": "multipart/form-data",
          AuthorizationToken: authHeader
        }
      })
      .then(response => {
        console.log("Success:", response);
        commit("SET_UPDATED_STUDENT", student);
        // Thêm bất kỳ xử lý thành công nào tại đây
      })
      .catch(error => {
        console.error("Error:", error);
        // Xử lý lỗi tại đây
      });
  },
  async fetchStudentTests({ commit, state }, studentId) {
    const token = localStorage.getItem("token");
    const authHeader = `${token}`;
    const finalURL = "http://localhost:8081/test/" + studentId;
    console.log("URL:  ", finalURL);
    try {
      // Gọi API với tham số tìm kiếm từ state.searchParams
      const response = await axiosInstance.get(
        finalURL, // Thay đổi URL API tìm kiếm sinh viên
        {
          headers: { AuthorizationToken: authHeader }
        }
      );

      // Cập nhật kết quả tìm kiếm vào state
      commit("SET_STUDENT_TESTS", response.data);
      console.log("Search results:", response.data);
      console.log("Search results in state:", state.studentTests);
    } catch (error) {
      console.error("Error searching students test:", error);
    }
  },
  async getStudentTestData({ commit, state }, testId) {
    const token = localStorage.getItem("token");
    const authHeader = `${token}`;
    const finalURL = "http://localhost:8081/testPage/" + testId;
    console.log("URL:  ", finalURL);
    try {
      const response = await axiosInstance.get(finalURL, {
        headers: { AuthorizationToken: authHeader }
      });

      // Cập nhật kết quả tìm kiếm vào state
      console.log("data reponse: ", response.data);
      commit("SET_ALL_STUDENTS", response.data);
      commit("SET_FILTERED_STUDENTS", response.data);
      console.log("all student :", state.allStudents);
      console.log("filter student:", state.filterStudents);
    } catch (error) {
      console.error("Error searching students test:", error);
    }
  }
};
