<template>
  <b-modal id="error-modal" title="Error" @ok="closeModal">
    <p class="mb-0">{{ errorMessage }}</p>
  </b-modal></template
>

<script>
import { mapActions } from "vuex";
import { mapState } from "vuex";
import { mapMutations } from "vuex";
export default {
  name: "error-modal",
  computed: {
    ...mapState("test", {
      errorMessage: state => state.error
    })
  },
  watch: {
    errorMessage(newValue) {
      if (newValue) {
        this.$bvModal.show("error-modal");
      }
    }
  },
  methods: {
    closeModal() {
      this.$bvModal.hide("error-modal");
    }
  }
};
</script>

<style></style>
