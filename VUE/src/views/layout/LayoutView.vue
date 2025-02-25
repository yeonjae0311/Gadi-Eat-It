
<template>
    <div>
        <header><HeaderComponent></HeaderComponent></header>
        <RouterView></RouterView>
        <footer></footer>
    </div>

</template>

<script setup>
import HeaderComponent from '@/components/HeaderComponent.vue';
</script>

<style lang="scss" scoped>

</style>