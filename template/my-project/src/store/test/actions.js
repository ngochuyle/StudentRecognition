import axiosInstance from "../../axiosConfig"; // Import axiosInstance đã cấu hình
import queryString from "querystring";

export default {
  async searchTest({ commit, state }, searchParams) {
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
    const apiUrl = "http://localhost:8081/test/search?";
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
      console.error("Error searching test:", error);
    }
  },
  async createTest({ commit, state }, testForm) {
    const token = localStorage.getItem("token");
    if (!token) {
      console.error("No token found");
      return;
    }
    console.log("data in action:");
    for (let [key, value] of testForm.entries()) {
      console.log(key, value);
    }
    // Tạo header cho request API
    const authHeader = `${token}`;
    console.log("search token", authHeader);
    try {
      // Gọi API với tham số tìm kiếm từ state.searchParams
      const response = await axiosInstance.post(
        "http://localhost:8081/test/create",
        testForm, // Thay đổi URL API tìm kiếm sinh viên
        {
          headers: { AuthorizationToken: authHeader }
        }
      );
      console.log("sucess");
    } catch (error) {
      console.error("Error creating test:", error);
    }
  },
  async updateTestInfo({ commit, state }, formData) {
    const token = localStorage.getItem("token");
    if (!token) {
      console.error("No token found");
      return;
    }
    console.log("data in action:");
    for (let [key, value] of formData.entries()) {
      console.log(key, value);
    }
    // Tạo header cho request API
    const authHeader = `${token}`;
    console.log("search token", authHeader);
    try {
      // Gọi API với tham số tìm kiếm từ state.searchParams
      const response = await axiosInstance.post(
        "http://localhost:8081/test/update",
        formData, // Thay đổi URL API tìm kiếm sinh viên
        {
          headers: { AuthorizationToken: authHeader }
        }
      );
      console.log("sucess");
    } catch (error) {
      console.error("Error updating test:", error);
    }
  },
  async deleteTest({ commit, state }, testId) {
    const token = localStorage.getItem("token");
    try {
      const response = await axiosInstance({
        method: "post",
        url: `http://localhost:8081/test/delete/${testId}`,
        headers: { AuthorizationToken: token }
      });

      console.log("Response remove:", response);
      // Kiểm tra phản hồi và cập nhật state nếu xóa thành công
    } catch (error) {
      console.error("Error deleting test:", error);
    }
  },
  async updateScore({ commit, state }, scoreParams) {
    const token = localStorage.getItem("token");
    const queryURl = queryString.stringify(scoreParams);
    const apiUrl = "http://localhost:8081/test/updateScore?";
    const finalURL = apiUrl + queryURl;
    console.log("URL :", finalURL);
    try {
      // Gọi API với tham số tìm kiếm từ state.searchParams
      const response = await axiosInstance.get(
        finalURL, // Thay đổi URL API tìm kiếm sinh viên
        {
          headers: { AuthorizationToken: token }
        }
      );

      // Cập nhật kết quả tìm kiếm vào state
    } catch (error) {
      console.error("Error searching students:", error);
    }
  },
  async updateGrade({ commit, state }, testRegistrationId) {
    const token = localStorage.getItem("token");
    const apiUrl =
      "http://localhost:8081/test/updateGrade/" + testRegistrationId;
    console.log("URL :", apiUrl);
    try {
      // Gọi API với tham số tìm kiếm từ state.searchParams
      const response = await axiosInstance.get(
        apiUrl, // Thay đổi URL API tìm kiếm sinh viên
        {
          headers: { AuthorizationToken: token }
        }
      );
      commit("student/UPDATE_PARTICIPATION_STATUS", testRegistrationId, {
        root: true
      });
      console.log("test: thanh cong");
      // Cập nhật kết quả tìm kiếm vào state
    } catch (error) {
      console.error("Error searching students:", error);
    }
  },
  async searchIMG({ commit, state }, imageBlob) {
    console.log("vaof img", imageBlob);
    const token = localStorage.getItem("token");
    const apiUrl = "http://localhost:8081/searchIMG/";
    console.log("URL :", apiUrl);
    const formData = new FormData();
    formData.append("image", imageBlob, "captured-image.jpg");
    const startTime = Date.now();
    try {
      // Gọi API với tham số tìm kiếm từ state.searchParams
      const response = await axiosInstance.post(
        apiUrl,
        formData, // Thay đổi URL API tìm kiếm sinh viên
        {
          headers: {
            AuthorizationToken: token,
            "Content-Type": "multipart/form-data"
          }
        }
      );
      console.log("Image uploaded successfully:", response.data);
      if (response.data === "None") {
        commit("SET_ERROR", "No matching student found.");
        const endTime = Date.now();
        const duration = endTime - startTime;
        console.log(`Thời gian phản hồi của API là: ${duration} ms`);
      } else {
        const sNummer = "s" + response.data;
        console.log("snummer", sNummer);
        const endTime = Date.now();
        const duration = endTime - startTime;
        console.log(`Thời gian phản hồi của API là: ${duration} ms`);
        commit("student/UPDATE_PARTICIPATION_IMGSTATUS", sNummer, {
          root: true
        });
      }
    } catch (error) {
      console.log("loiloi");
      console.error("Error uploading image:", error);
    }
  }
};
