<template>
  <div>
    <!-- Modal Definition -->
    <b-modal id="student-test-modal" title="Test Details" hide-footer size="lg">
      <!-- Bảng để hiển thị thông tin các bài test -->
      <b-table
        v-if="studentTests.length > 0"
        striped
        hover
        :items="studentTests"
        :fields="fields"
      ></b-table>
      <div v-else>
        <p>No Test.</p>
      </div>
    </b-modal>
  </div>
</template>

<script>
import { mapActions } from "vuex";
import { mapState } from "vuex";
export default {
  name: "student-test-modal",
  data() {
    return {
      // Các trường cho bảng
      fields: [
        { key: "id", label: "ID" },
        { key: "title", label: "Title" },
        { key: "description", label: "Description" },
        { key: "coursename", label: "Course Name" },
        { key: "professor", label: "Professor" },
        { key: "score", label: "Score" },
        { key: "participated", label: "Participated" }
      ]
    };
  },
  computed: {
    ...mapState("student", {
      studentTests: state => state.studentTests // Mập state studentTests từ store
    })
  }
};
</script>

<style></style>
