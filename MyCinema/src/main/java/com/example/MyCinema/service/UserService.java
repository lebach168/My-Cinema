package com.example.MyCinema.service;

import com.example.MyCinema.dto.request.UserRequestDTO;
import com.example.MyCinema.dto.response.UserDetailResponse;
import com.example.MyCinema.model.User;

public interface UserService {
    UserDetailResponse getUserDetail(long userId);
    Long createNewAccount(UserRequestDTO userDTO);
    void updateUserInfo(long userId ,UserRequestDTO userDTO);
    void changePassword(long userId,String newPassword);
    void deleteUser(long userId);
    User getUserById(long userId);
}
