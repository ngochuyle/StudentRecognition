export default {
  SET_COURSES(state, courses) {
    state.courses = courses; // Cập nhật state với danh sách khóa học mới
  },
  RESET_COURSES(state) {
    state.courses = []; // Reset danh sách khóa học về mảng rỗng
  },
  SET_OLD_COURSES(state, oldCourses) {
    state.oldCourses = oldCourses; // Cập nhật state với danh sách khóa học mới
  },
  SET_OLD_COURSESID(state) {
    state.oldCoursesId = state.oldCourses.map(oldCourse => oldCourse.courseId); // Cập nhật state với danh sách khóa học mới
  }
};
