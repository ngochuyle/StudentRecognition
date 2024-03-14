import axios from "axios";
import { Alert } from "bootstrap";
import router from "./router"; // Import router từ Vue Router

const axiosInstance = axios.create({
  // Cấu hình cơ bản...
});

axiosInstance.interceptors.response.use(
  response => response,
  error => {
    if (error.response) {
      // Xử lý dựa trên mã lỗi từ phản hồi
      switch (error.response.status) {
        case 401:
          alert("Benutzername oder Passwort ist falsch");
          break;
        // Thêm xử lý cho các mã lỗi khác nếu cần
        default:
        // Xử lý lỗi mặc định
      }
    } else if (error.request) {
      // Yêu cầu đã được thực hiện nhưng không nhận được phản hồi
      // Xử lý lỗi mạng, timeout, v.v.
    } else {
      // Đã xảy ra lỗi trong việc thiết lập yêu cầu
      // Xử lý lỗi khác
    }
    return Promise.reject(error);
  }
);

export default axiosInstance;
