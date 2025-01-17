package com.thaidot.file_service.controller;

import com.thaidot.file_service.dto.ApiResponse;
import com.thaidot.file_service.dto.response.AvatarResponse;
import com.thaidot.file_service.service.UserAvatarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class UserAvatarController {

    private final UserAvatarService userAvatarService;

    @PostMapping("/avatar")
    public ApiResponse<AvatarResponse> uploadUserAvatar(
            @RequestParam("file") MultipartFile file) {
        return ApiResponse.<AvatarResponse>builder().result(userAvatarService.uploadUserAvatar(file)).build();

    }
}
