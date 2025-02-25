<template>
  <div>
    <input type="text" v-model="searchQuery" placeholder="식당 이름을 검색하세요" />
    <button @click="onSearch">검색</button>
  </div>
  <div class="map-container">
    <div ref="mapRef" style="width: 80%; height: 400px" @load="onMapLoad" class="map">
      <div
        v-for="data in resList"
        :key="data.id"
        :latitude="data.latitude"
        :longitude="data.longitude"
        @click="moveResDetail"
      >
        <div>
          {{ data.name }}
        </div>
      </div>
    </div>
    <GadiMapSideBar :res="selectedRes" @close="selectedRes = null" />
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { storeToRefs } from 'pinia'
import { useMapstore } from '@/stores/useMapstore'
import GadiMapSideBar from './GadiMapSideBar.vue'

const mapRef = ref(null)
const markers = ref(new Map())
const map = ref(null)
const selectedRes = ref(null)
const searchQuery = ref('')

let markerCluster = null

const store = useMapstore()
const { resList } = storeToRefs(store)

const debounce = (func, delay) => {
  let timer
  return (...args) => {
    clearTimeout(timer) // 기존 타이머 초기화
    timer = setTimeout(() => {
      func(...args) // delay 후 함수 실행
    }, delay)
  }
}

const searchRestaurants = () => {
  const filteredResList = resList.value.filter((res) =>
    res.resName.toLowerCase().includes(searchQuery.value.toLowerCase())
  )

  // 마커 초기화 (기존 마커 제거)
  markers.value.forEach((marker) => marker.setMap(null))
  markers.value.clear()

  // 필터링된 마커만 추가
  filteredResList.forEach((res) => {
    const latlng = new window.naver.maps.LatLng(res.latitude, res.longitude)
    const marker = new window.naver.maps.Marker({
      position: latlng,
      map: map.value
    })

    // 마커 클릭 시 사이드바 표시
    window.naver.maps.Event.addListener(marker, 'click', () => {
      selectedRes.value = res // 사이드바 토글
    })

    markers.value.set(res.resId, marker)
  })
}

// 검색 필드가 바뀔 때마다 마커 업데이트
const onSearch = () => {
  searchRestaurants()
}

const updateMarkers = () => {
  if (!map.value) return

  const bounds = map.value.getBounds()
  const newMarkers = new Map() // 업데이트된 마커 저장

  for (const res of resList.value) {
    const latlng = new window.naver.maps.LatLng(res.latitude, res.longitude)
    if (bounds.hasLatLng(latlng)) {
      if (!markers.value.has(res.resId)) {
        // 기존에 없는 마커만 생성
        const marker = new window.naver.maps.Marker({
          position: latlng,
          map: map.value,
          title: res.resName,
          icon: {
            url: 'https://map.pstatic.net/resource/api/v2/image/maps/selected-marker/222870@2x.png?version=12&mapping=marker-111',
            scaledSize: new window.naver.maps.Size(15, 20)
          }
        })
        window.naver.maps.Event.addListener(marker, 'click', () => {
          selectedRes.value = res // 마커 클릭 시 데이터 전달
        })
        markers.value.set(res.resId, marker) // 마커 저장
      }
      newMarkers.set(res.resId, markers.value.get(res.resId)) // 현재 필요한 마커만 저장
    }
  }

  markers.value.forEach((marker, id) => {
    if (!newMarkers.has(id)) {
      marker.setMap(null)
      markers.value.delete(id) // 메모리에서 삭제
    }
  })

  markerCluster.setMarkers([...markers.value.values()])
}

const debouncedUpdateMarkers = debounce(updateMarkers, 200)

onMounted(() => {
  if (window.naver && window.naver.maps) {
    // 맵 객체 생성
    const mapOptions = {
      center: new window.naver.maps.LatLng(37.481008, 126.8825988), // 지도 중심 좌표
      zoom: 16
    }
    map.value = new window.naver.maps.Map(mapRef.value, mapOptions)
    console.log('맵 로드 완료!', map.value)

    // ✅ 마커 클러스터링 객체 생성 (많은 마커를 효율적으로 관리)
    markerCluster = new MarkerClustering({
      minClusterSize: 2, // 최소 2개부터 클러스터링
      maxZoom: 17,
      map: map.value,
      markers: [], // 초기 마커 없음
      disableClickZoom: false, // 클릭 시 줌 확대 가능
      gridSize: 80 // 클러스터 간 거리
    })

    window.naver.maps.Event.addListener(map.value, 'bounds_changed', debouncedUpdateMarkers)

    fetchResList()
  } else {
    console.error('네이버 맵 API가 로드되지 않았습니다.')
  }
})

const fetchResList = async () => {
  try {
    // 비동기적으로 데이터 로딩
    await store.loadResList()
    updateMarkers()
  } catch (error) {
    console.error('리스트 로딩 중 오류 발생:', error)
  }
}
</script>

<style scoped>
img {
  height: 10px;
  width: 10px;
}

.map-container {
  display: flex;
  position: relative;
  height: 100vh;
}

.map {
  flex: 1;
}
</style>
