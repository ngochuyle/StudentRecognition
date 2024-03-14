<template>
  <b-modal id="test-add-modal" title="Add New Test" hide-footer>
    <b-form @submit.prevent="onSubmit">
      <b-form-group label="Name" label-for="test-name">
        <b-form-input
          id="test-name"
          v-model="test.name"
          type="text"
          required
          placeholder="Enter test name"
        ></b-form-input>
      </b-form-group>

      <b-form-group label="Description" label-for="test-description">
        <b-form-input
          id="test-description"
          v-model="test.description"
          type="text"
          required
          placeholder="Enter description"
        ></b-form-input>
      </b-form-group>
      <b-form-group label="Select Course" label-for="test-course">
        <b-form-select id="test-course" v-model="test.course" required>
          <option
            v-for="course in courses"
            :key="course.courseId"
            :value="course.courseId"
          >
            {{ course.name }}
          </option>
        </b-form-select>
      </b-form-group>

      <b-button type="submit" variant="primary">Submit</b-button>
      <b-button type="button" variant="danger" @click="closeModal"
        >Cancel</b-button
      >
    </b-form>
  </b-modal>
</template>

<script>
import { mapActions } from "vuex";
import { mapState } from "vuex";

export default {
  name: "test-add-modal",
  computed: {
    ...mapState({
      courses: state => state.course.courses
    })
  },
  data() {
    return {
      test: {
        name: "",
        description: "",
        course: ""
      }
    };
  },
  methods: {
    ...mapActions({
      createTest: "test/createTest"
    }),
    onSubmit() {
      const formData = new FormData();
      formData.append("name", this.test.name);
      formData.append("description", this.test.description);
      formData.append("courseId", this.test.course);
      for (let [key, value] of formData.entries()) {
        console.log(key, value);
      }
      this.createTest(formData);
      this.$bvModal.hide("test-add-modal");
    },
    closeModal() {
      this.$bvModal.hide("test-add-modal");
    }
  }
};
</script>

<style></style>
