<template>
  <NavBar/>
  <div style="display: flex; bottom: 50%">
    home
    <n-button type="info" @click="test" style="">
      后端给我算
    </n-button>
    <n-input
        v-model:value="a"
        type="textarea"
        placeholder="a"
    />
    <div></div>
  </div>
  <n-input
      v-model:value="b"
      type="textarea"
      placeholder="b"
  />
</template>

<script>
import $ from 'jquery'
import {NButton, NInput} from "naive-ui";
import {ref} from 'vue'
import NavBar from "@/components/NavBar";

export default {
  components: {
    NButton,
    NInput,
    NavBar
  },

  setup() {
    let a = ref(0);
    let b = ref(0);

    const test = () => {
      $.ajax({
        url: "http://localhost:3080/api/test/",
        type: "get",
        data: {
          'a': a.value,
          'b': b.value,
        },
        success(resp) {
          alert(resp);
        }
      });
    };

    return {
      test,
      a,
      b,
    }
  }
}
</script>

<style>

</style>