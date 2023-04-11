<template>
  <NMessageProvider>
    <router-view/>
  </NMessageProvider>
</template>

<script>
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap/dist/js/bootstrap"
import "@/assets/font/font.css"
import "@/assets/css/tailwind.css"
import {NMessageProvider} from 'naive-ui'

export default {
  components: {
    NMessageProvider,
  }
}

</script>

<style>

</style>
