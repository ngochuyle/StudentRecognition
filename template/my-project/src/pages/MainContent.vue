<template>
  <div>
    <header-cpm></header-cpm>
    <router-view></router-view>
    <!--<capture-cpm></capture-cpm>-->
  </div>
</template>

<script>
import HeaderCpm from "../component/HeaderCpm.vue";
export default {
  name: "main-content",
  components: {
    HeaderCpm
  },
  data() {
    return {};
  }
};
</script>

<style></style>
