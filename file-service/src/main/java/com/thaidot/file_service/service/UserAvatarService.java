package com.thaidot.file_service.service;

import com.thaidot.file_service.enums.FileTypeUpload;
import com.thaidot.file_service.dto.response.AvatarResponse;
import com.thaidot.file_service.entity.Avatar;
import com.thaidot.file_service.exception.AppException;
import com.thaidot.file_service.exception.ErrorCode;
import com.thaidot.file_service.mapper.AvatarMapper;
import com.thaidot.file_service.repository.AvatarRepository;
import com.thaidot.event.dto.UpdateAvatarProfileEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserAvatarService {

    @Value("${file.upload-dir.avatar}")
    private String uploadAvatarDir;
    private final UploadFileService uploadFileService;
    private final AvatarRepository avatarRepository;
    private final AvatarMapper avatarMapper;

    private final KafkaProducer kafkaProducer;


    public AvatarResponse uploadUserAvatar(MultipartFile file) {
        String userID = SecurityContextHolder.getContext().getAuthentication().getName();
        if (isExistAvatar(userID)) {
            deleteAvatar(userID);
        }
        String path = uploadFileService.uploadFile(userID, file, uploadAvatarDir, FileTypeUpload.IMAGE);
        Avatar savedAvatar = avatarRepository.save(Avatar.builder().userID(userID).url(path).build());

        kafkaProducer.sendProfileAvatarUpdateMessage(UpdateAvatarProfileEvent.builder()
                .userId(userID)
                .avatarUrl(savedAvatar.getUrl())
                .build());

        return avatarMapper.toAvatarResponse(savedAvatar);
    }

    private boolean isExistAvatar(String userID) {
        return avatarRepository.findByUserID(userID).orElseThrow(
                () -> new AppException(ErrorCode.AVATAR_NOT_FOUND)
        ) != null;

    }

    private void deleteAvatar(String userID) {
        Avatar savedAvatar = avatarRepository.findByUserID(userID).get();
        uploadFileService.deleteFile(savedAvatar.getUrl());
        avatarRepository.deleteByUserID(userID);
    }
}
