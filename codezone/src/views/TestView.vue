<template>
  <div></div>
</template>

<script>
import {useStore} from "vuex";

export default {
  setup() {
    const store = useStore();
    console.log(store.state.user.id);
    setTimeout(()=>{ console.log(store.state.user.id); }, 5000);
  }
}

</script>

<style>

</style>
