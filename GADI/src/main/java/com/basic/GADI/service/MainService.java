package com.basic.GADI.service;

import com.basic.GADI.dto.response.MarkerListResponseDto;
import com.basic.GADI.entity.Restaurants;
import com.basic.GADI.repository.ResRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MainService {

    private final ResRepository resRepository;

    @Transactional
    public List<MarkerListResponseDto> selectMarkerList() {
        List<Restaurants> res = resRepository.findAll();
        List<MarkerListResponseDto> list = new ArrayList<>();

        for(Restaurants r : res) {
            list.add(new MarkerListResponseDto(r));
        }
        return list;
    }

}
