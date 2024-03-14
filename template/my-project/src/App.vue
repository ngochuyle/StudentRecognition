<template>
  <div id="app">
    <!--<header-cpm></header-cpm>-->
    <router-view></router-view>
    <!--<capture-cpm></capture-cpm>-->
  </div>
</template>

<script>
export default {
  name: "app",
  components: {},
  data() {
    return {};
  }
};
</script>

<style>
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 0px;
}

h1,
h2 {
  font-weight: normal;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  display: inline-block;
  margin: 0 10px;
}

a {
  color: #42b983;
}
</style>
