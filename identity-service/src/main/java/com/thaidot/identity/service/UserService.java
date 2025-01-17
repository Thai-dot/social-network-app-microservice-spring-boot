package com.thaidot.identity.service;

import java.util.HashSet;
import java.util.List;

import com.thaidot.event.dto.NotificationEvent;
import com.thaidot.identity.client.ProfileClient;
import com.thaidot.identity.dto.request.UserProfileCreationRequest;
import com.thaidot.identity.mapper.ProfileMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.thaidot.identity.constant.PredefinedRole;
import com.thaidot.identity.dto.request.UserCreationRequest;
import com.thaidot.identity.dto.request.UserUpdateRequest;
import com.thaidot.identity.dto.response.UserResponse;
import com.thaidot.identity.entity.Role;
import com.thaidot.identity.entity.User;
import com.thaidot.identity.exception.AppException;
import com.thaidot.identity.exception.ErrorCode;
import com.thaidot.identity.mapper.UserMapper;
import com.thaidot.identity.repository.RoleRepository;
import com.thaidot.identity.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;
    ProfileMapper profileMapper;
    PasswordEncoder passwordEncoder;
    ProfileClient profileClient;
    KafkaTemplate<String, Object> kafkaTemplate;

    @Transactional
    public UserResponse createUser(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) throw new AppException(ErrorCode.USER_EXISTED);

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<Role> roles = new HashSet<>();
        roleRepository.findById(PredefinedRole.USER_ROLE).ifPresent(roles::add);

        user.setRoles(roles);

        User savedUser = userRepository.save(user);
        UserProfileCreationRequest requestProfile = profileMapper.toUserProfileCreationRequest(request);
        requestProfile.setUserID(savedUser.getId());

        profileClient.createUserProfile(requestProfile);

        NotificationEvent notificationEvent = NotificationEvent.builder().channel("EMAIL").recipient(savedUser.getEmail()).subject("Welcome to our social network").body("Hello " + savedUser.getUsername()).build();

        // Public kafka message
        kafkaTemplate.send("notification-delivery", notificationEvent);
        return userMapper.toUserResponse(savedUser);
    }

    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserResponse(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers() {
        log.info("In method get Users");
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse getUser(String id) {
        return userMapper.toUserResponse(
                userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }
}
