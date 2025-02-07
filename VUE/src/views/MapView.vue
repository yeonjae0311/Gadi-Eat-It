<template>
    <naver-map
        ref="refMap"
        style="width: 100%; height: 400px"
        :map-options="mapOptions"
    >
      <naver-marker
        v-for="(data) in datas"
        :key="data.id"
        :latitude="data.latitude"
        :longitude="data.longitude"
      >
        <img src="https://map.pstatic.net/resource/api/v2/image/maps/selected-marker/222870@2x.png?version=12&mapping=marker-111"/>
        <div>
          {{ data.name }}
        </div>
    </naver-marker>
    </naver-map>
  </template>
  
  
  <script setup>
    import { onMounted, ref } from 'vue';
    import { NaverMap, NaverMarker } from 'vue3-naver-maps'
    import axios from 'axios';

    const mapOptions = {
        latitude: 37.481008, // 지도 중앙 위도
        longitude: 126.8825988, // 지도 중앙 경도
        zoom: 16,
    }

    const datas = ref([]);

    const refMap = ref();

    const mapInstance = ref();

    onMounted (() => {
      const map = refMap.value;
      mapInstance.value = map;
      console.log(map);
      
      axios
      .get('http://localhost:8080/api/main/list')
      .then((res) => {
        const allData = Object.values(res.data).flat();
      
        if(Array.isArray(allData)) {
          datas.value = allData.map(item => ({
            latitude: parseFloat(item.latitude),
            longitude: parseFloat(item.longitude),
            address: item.address,
            name: item.resName,
            id: item.resId
          }))

          console.log(datas);

        }})
      .catch((error) => {
        console.error('Error fetching data:', error);
      })
    })
  </script>

  <style>
    img{
      height: 40px;
      width: 40px;
    }
  </style>
  