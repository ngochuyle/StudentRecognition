<template>
  <b-modal id="grade-modal" title="Grade Student" @ok="handleOk">
    <b-container>
      <b-row>
        <b-col md="6">
          <b-form-group label="Name">
            <b-form-input
              v-model="selectedTestStudent.name"
              readonly
            ></b-form-input>
          </b-form-group>
        </b-col>
        <b-col md="6">
          <b-form-group label="sNummer">
            <b-form-input
              v-model="selectedTestStudent.snummer"
              readonly
            ></b-form-input>
          </b-form-group>
        </b-col>
      </b-row>
      <b-row>
        <b-col
          md="4"
          v-for="(image, index) in selectedTestStudent.imgURL"
          :key="index"
        >
          <b-img :src="image" alt="Student Image" fluid></b-img>
        </b-col>
      </b-row>
    </b-container>
  </b-modal>
</template>

<script>
import { mapActions } from "vuex";
import { mapState } from "vuex";
export default {
  name: "grade-modal",
  computed: {
    ...mapState("student", {
      selectedTestStudent: state => state.selectedTestStudent
    })
  },
  methods: {
    ...mapActions({
      updateGrade: "test/updateGrade"
    }),
    ...mapState("student", {
      filteredStudents: state => state.filterStudents
    }),
    handleOk() {
      this.updateGrade(this.selectedTestStudent.testRegistrationId);
      console.log("filteredStudents after update:", this.filteredStudents);
    }
  }
};
</script>
