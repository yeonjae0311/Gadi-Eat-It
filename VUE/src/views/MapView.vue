<template>
    <naver-map
        style="width: 100%; height: 400px"
        :map-options="mapOptions"
    >
    <naver-marker
      latitude="37.51347"
      longitude="127.041722"
    />
    </naver-map>
  </template>
  
  
  <script setup>
    import { NaverMap } from 'vue3-naver-maps'
    import { NaverMarker } from 'vue3-naver-maps';
    const mapOptions = {
        latitude: 37.51347, // 지도 중앙 위도
        longitude: 127.041722, // 지도 중앙 경도
        zoom: 13,
    }
  </script>
