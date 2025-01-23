package com.thaidot.search_service.service;

import com.thaidot.search_service.dto.request.ProfileRequest;
import com.thaidot.search_service.dto.response.ProfileResponse;
import com.thaidot.search_service.exception.AppException;
import com.thaidot.search_service.exception.ErrorCode;
import com.thaidot.search_service.mapper.ProfileMapper;
import com.thaidot.search_service.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    public void saveProfile(ProfileRequest profile) {
        profileRepository.save(profileMapper.toProfile(profile));
    }

    public void deleteProfile(String userId) {
        profileRepository.deleteById(userId);
    }

    public ProfileResponse getProfile(String userID) {
        return profileRepository.findById(userID)
                .map(profileMapper::toProfileResponse)
                .orElseThrow(() -> new AppException(ErrorCode.PROFILE_NOT_FOUND));
    }
}
