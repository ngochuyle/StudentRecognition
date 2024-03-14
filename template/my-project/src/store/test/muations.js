export default {
  SET_SEARCH_RESULTS(state, searchResults) {
    state.searchResults = searchResults; // Cập nhật giá trị searchResults
  },
  SET_SELECTED_STUDENT(state, test) {
    state.selectedTest = test;
  },
  SET_ERROR(state, error) {
    state.error = error;
  },
  CLEAR_ERROR(state) {
    state.error = null; // hoặc reset về chuỗi rỗng ''
  }
};
