import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);
import state from "./state";
import getters from "./getters";
import actions from "./actions";
import mutations from "./muations";
import moduleStudent from "./student";
import moduleTest from "./test";
import moduleCourse from "./course";
// Create a new store instance.
const store = new Vuex.Store({
  state: state,
  mutations: mutations,
  getters: getters,
  actions: actions,
  modules: {
    student: moduleStudent,
    test: moduleTest,
    course: moduleCourse
  }
});

export default store;
