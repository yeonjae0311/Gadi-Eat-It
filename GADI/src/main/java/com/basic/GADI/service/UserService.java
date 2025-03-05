package com.basic.GADI.service;

import com.basic.GADI.dto.request.MyInfoRequestDto;
import com.basic.GADI.dto.request.MyRestaurantRequestDto;
import com.basic.GADI.dto.response.MyInfoResponseDto;
import com.basic.GADI.dto.response.MyRestaurantResponseDto;
import com.basic.GADI.dto.response.PageResponseDto;
import com.basic.GADI.dto.response.ResDetailResponseDto;
import com.basic.GADI.entity.Favorites;
import com.basic.GADI.entity.Restaurants;
import com.basic.GADI.entity.User;
import com.basic.GADI.exception.BusinessException;
import com.basic.GADI.repository.FavoriteRepository;
import com.basic.GADI.repository.ResRepository;
import com.basic.GADI.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;
    private final ResRepository resRepository;
    private final FavoriteRepository favoriteRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${file.upload.path}")
    private String fileUploadPath;

    public MyInfoResponseDto findMyInfo(Long userId) {
        User findOneUser = userRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException("해당 사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
        return MyInfoResponseDto.fromUser(findOneUser);
    }

    @Transactional
    public void updateMyInfo(Long userId, MyInfoRequestDto requestDto, MultipartFile file) {
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new BusinessException("해당 사용자를 찾을 수 없습니다."));

        user.updateMyInfo(
                requestDto.getUserName(),
                requestDto.getUserPhone(),
                requestDto.getUserBirth()
        );

        if (file != null && !file.isEmpty()) {
            // 원본 파일 확장자 유지하기 위한 코드
            String originalFileName = file.getOriginalFilename(); // 원본 파일명
            String extension = (originalFileName != null && originalFileName.contains(".")) ? originalFileName.substring(originalFileName.lastIndexOf(".")) : ""; // 확장자 추출

            // 업로드 폴더 확인 및 생성
            Path uploadFolder = Paths.get(fileUploadPath);
            if (!Files.exists(uploadFolder)) {
                try {
                    Files.createDirectories(uploadFolder);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            // 파일 경로 설정
            UUID uuid = UUID.randomUUID();
            Path filePath = uploadFolder.resolve(uuid + extension);

            // 파일 저장
            try {
                Files.write(filePath, file.getBytes());
            } catch (IOException e) {
                throw new BusinessException("이미지 저장 실패 !", HttpStatus.NOT_FOUND);
            }
            user.setUserFile(String.valueOf(Path.of(uuid + extension)));
        }
    }

   /* public PageResponseDto<ResDetailResponseDto> favoriteRestaurantsList(Long userId, Pageable pageable) {

        Page<Restaurants> myFavoriteResList = resRepository.findByFavoritesUserUserId(userId, pageable);

        List<ResDetailResponseDto> resList = new ArrayList<>();
        for (Restaurants restaurant : myFavoriteResList.getContent()) {
            resList.add(new ResDetailResponseDto(restaurant));
        }
        Page<ResDetailResponseDto> page = new PageImpl<>(resList, pageable, myFavoriteResList.getTotalElements());

        return new PageResponseDto<>(page);
    }*/

    public PageResponseDto<ResDetailResponseDto> myFavoriteRestaurantsList(Long userId, Pageable pageable) {
        Page<Favorites> myFavoriteList = favoriteRepository.findByUserUserId(userId, pageable);
        List<Long> myFavoriteResIds = new ArrayList<>();
        for (Favorites favorite : myFavoriteList) {
            myFavoriteResIds.add(favorite.getRestaurants().getResId());
        }
        Page<Restaurants> myRestaurants = resRepository.findByResIdIn(myFavoriteResIds, pageable);

        List<ResDetailResponseDto> myRestaurantList = new ArrayList<>();
        for (Restaurants restaurant : myRestaurants.getContent()) {
            myRestaurantList.add(new ResDetailResponseDto(restaurant));
        }
        Page<ResDetailResponseDto> page = new PageImpl<>(myRestaurantList, pageable, myRestaurants.getTotalElements());

        return new PageResponseDto<>(page);
    }


    public void checkMyPw(Long userId, String userPw) {
        // userId로 해당 유저 찾아오기
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new BusinessException("해당 사용자를 찾을 수 없습니다."));
        // 찾아온 유저 정보의 비밀번호와 userPw가 일치하는지 확인
        String savedPw = user.getUserPw();
        if (!passwordEncoder.matches(userPw, savedPw)) {
            throw new BusinessException("기존 비밀번호가 일치하지 않습니다.");
        }
    }


    @Transactional
    public void resetUserPw(Long userId, String newPassword) {
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new BusinessException("해당 사용자를 찾을 수 없습니다."));
        user.resetUserPw(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }


    @Transactional
    public void addMyRestaurant(Long userId, Long resId) {
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new BusinessException("해당 사용자를 찾을 수 없습니다."));
        Restaurants restaurants = resRepository.findByResId(resId).orElseThrow(()-> new BusinessException("해당 식당을 찾을 수 없습니다."));

        if (favoriteRepository.existsByUserAndRestaurants(user, restaurants)) {
            throw new BusinessException("이미 즐겨찾기에 추가된 식당입니다 !");
        }

        Favorites favorites = MyRestaurantRequestDto.toEntity(user, restaurants);
        favoriteRepository.save(favorites);

    }

    public MyRestaurantResponseDto getMyRestaurant(Long userId, Long resId) {
        Favorites myFavorite = favoriteRepository.findByUser_UserIdAndRestaurants_resId(userId, resId);
        return new MyRestaurantResponseDto(myFavorite.getFavId());
    }
}
