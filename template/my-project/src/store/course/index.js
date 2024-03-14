import state from "./state";
import getters from "./getters";
import actions from "./actions";
import mutations from "./muations";
export default {
  namespaced: true,
  state: state,
  mutations: mutations,
  getters: getters,
  actions: actions
};
