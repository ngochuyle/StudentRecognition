export default {
  courseOptions(state) {
    return state.courses.map(course => ({
      value: course.id, // giả sử mỗi khóa học có một 'id' duy nhất
      text: course.name // và một 'name' để hiển thị
    }));
  }
};
