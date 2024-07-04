package com.example.MyCinema.service;

import com.example.MyCinema.dto.request.UserRequestDTO;
import com.example.MyCinema.dto.response.UserDetailResponse;
import com.example.MyCinema.model.User;

public interface UserService {
    UserDetailResponse getUserDetail(String userId);
    String createNewAccount(UserRequestDTO userDTO);
    void updateUserInfo(String userId ,UserRequestDTO userDTO);
    boolean changePassword(String userId,String currentPassword,String newPassword);
    void deleteUser(String userId);
    User getUserById(String userId);
}
