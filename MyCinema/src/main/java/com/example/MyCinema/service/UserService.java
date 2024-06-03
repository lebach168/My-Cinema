package com.example.MyCinema.service;

import com.example.MyCinema.dto.request.UserRequestDTO;
import com.example.MyCinema.dto.response.UserDetailResponse;

public interface UserService {
    public UserDetailResponse getUserDetail(long userId);
    public Long createNewAccount(UserRequestDTO userDTO);
    public void updateUserInfo(long userId ,UserRequestDTO userDTO);
    public void changePassword(long userId,String newPassword);
    public void deleteUser(long userId);

}
