package com.example.MyCinema.controller;

import com.example.MyCinema.dto.ApiResponse;
import com.example.MyCinema.dto.request.UserRequestDTO;
import com.example.MyCinema.dto.response.UserDetailResponse;
import com.example.MyCinema.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/users")
@RequiredArgsConstructor
@Validated
@Slf4j
public class UserController {
    private final UserService userService;
    @GetMapping
    public ApiResponse<?> testToken(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("username : {}" , authentication.getName());
        log.info("authorities: {} ", authentication.getAuthorities());
        return new ApiResponse<>(HttpStatus.OK,"ok");
    }
    //get info
    @GetMapping(path = "/{userId}")
    public ApiResponse<?> getUserInfo(@PathVariable("userId") long userId){
        log.info("request get detail information of user id={}",userId);
        UserDetailResponse response = userService.getUserDetail(userId);

        return new ApiResponse<UserDetailResponse>(HttpStatus.OK,"user",response);
    }

    //Update info
    @PutMapping(path = "/{userId}")
    public ApiResponse<?> updateUser(@PathVariable("userId") Long userId,
            @Valid @RequestBody UserRequestDTO userDTO){
        log.info("request update user info id={}",userId);
        userService.updateUserInfo(userId , userDTO);
        return new ApiResponse<>(HttpStatus.ACCEPTED,"user info successfully updated");
    }
    //Change password
    @PatchMapping(path = "/password/{userId}")
    public ApiResponse<?> changeUserPassword(@PathVariable("userId") Long userId,
                                             @NotBlank @RequestBody String currentPassword,
            @NotBlank @RequestBody @Min(1) String newPassword){
        log.info("request change user password id={}",userId);
        if(!userService.changePassword(userId,currentPassword,newPassword)){
            return new ApiResponse<>(HttpStatus.NOT_ACCEPTABLE,"check for the current password again");
        }
        return new ApiResponse<>(HttpStatus.ACCEPTED,"user password has been changed");
    }
    //Delete
    @DeleteMapping(path = "/{userId}")
    public ApiResponse<?> deleteMovie(@PathVariable Long userId){
        log.info("request delete user id:{}",userId);
        userService.deleteUser(userId);
        return new ApiResponse<>(HttpStatus.ACCEPTED,"user "+userId +" successfully deleted ");
    }
}
