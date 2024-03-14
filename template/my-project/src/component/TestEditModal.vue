<template>
  <b-modal
    id="test-edit-modal"
    title="Edit Test Information"
    @show="onModalShow"
    size="lg"
    hide-footer
  >
    <b-card class="mb-2">
      <h5>Update Information</h5>

      <b-form @submit.prevent="confirmEdit">
        <!-- Field cho Name -->
        <b-form-group label="Name" label-for="test-name">
          <b-form-input
            id="test-title"
            v-model="inputTest.title"
          ></b-form-input>
        </b-form-group>

        <!-- Field cho Description -->
        <b-form-group label="Description" label-for="test-description">
          <b-form-textarea
            id="test-description"
            v-model="inputTest.description"
            rows="3"
          ></b-form-textarea>
        </b-form-group>

        <!-- Button Confirm -->
        <b-button type="submit" variant="success">Confirm</b-button>
        <b-button variant="secondary" @click="closeModal">Cancel</b-button>
      </b-form>
    </b-card>
  </b-modal>
</template>

<script>
import { mapState, mapMutations, mapActions } from "vuex";

export default {
  name: "test-edit-modal",
  data() {
    return {
      inputTest: {} // Biến cục bộ để lưu thông tin nhập từ người dùng
    };
  },
  computed: {
    ...mapState("test", {
      selectedTest: state => state.selectedTest
    })
  },
  methods: {
    ...mapMutations(["SET_SELECTED_TEST"]),
    ...mapActions({
      updateTestInfo: "test/updateTestInfo"
    }),
    onModalShow() {
      // Khi modal hiển thị, thiết lập inputTest bằng selectedTest

      if (this.selectedTest) {
        this.inputTest = { ...this.selectedTest };
      }
      console.log("selectted test", this.selectedTest);
      console.log("input test", this.inputTest);
    },
    confirmEdit() {
      const formData = new FormData();
      formData.append("name", this.inputTest.title);
      formData.append("description", this.inputTest.description);
      formData.append("id", this.inputTest.id);
      this.updateTestInfo(formData);

      this.$bvModal.hide("test-edit-modal");
    },
    closeModal() {
      this.$bvModal.hide("test-edit-modal");
    }
  }
};
</script>
