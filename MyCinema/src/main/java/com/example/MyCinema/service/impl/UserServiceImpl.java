package com.example.MyCinema.service.impl;

import com.example.MyCinema.dto.request.UserRequestDTO;
import com.example.MyCinema.dto.response.UserDetailResponse;
import com.example.MyCinema.exception.ResourceNotFoundException;
import com.example.MyCinema.model.User;
import com.example.MyCinema.repository.UserRepository;
import com.example.MyCinema.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserDetailResponse getUserDetail(long userId) {
        User user = getUserById(userId);
        return UserDetailResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .dob(user.getDob())
                .point(user.getPoint())
                .level(user.getLevel())
                .build();
    }

    @Override
    public Long createNewAccount(UserRequestDTO userDTO) {
        if(userDTO.getPassword().isBlank()) throw new RuntimeException("Password must be not blank");
        Optional<User> userHasEmail = userRepository.findByEmail(userDTO.getEmail());
        if(userHasEmail.isPresent()) throw new RuntimeException("Email already exists!");
        String hashedPassword = passwordEncoder.encode(userDTO.getPassword());
        User newUser = User.builder()
                .email(userDTO.getEmail())
                .password(hashedPassword)
                .dob(userDTO.getDob())
                .name(userDTO.getName())
                .point(userDTO.getPoint())
                .level(userDTO.getLevel())
                .build();
        Long userId = userRepository.save(newUser).getId();
        log.info("user id={} has saved",userId);
        return userId;
    }


    @Override
    public void updateUserInfo(long userId, UserRequestDTO userDTO) {
        User user = getUserById(userId);
        user.setDob(userDTO.getDob());
        user.setName(userDTO.getName());
        user.setPoint(userDTO.getPoint());
        user.setLevel(userDTO.getLevel());
        userRepository.save(user);
        log.info("user id={} updated successfully",userId);
    }

    @Override
    public boolean changePassword(long userId, String currentPassword,String newPassword) {
        User user = getUserById(userId);
        if(newPassword.equals(currentPassword)){
            throw new RuntimeException("New password cannot be the same as your old password");
        }
        if(!passwordEncoder.matches(currentPassword,user.getPassword())){
            return false;
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        log.info("User id={} changed password successfully",userId);
        return true;
    }

    @Override
    public void deleteUser(long userId) {
        userRepository.deleteById(userId);
        log.info("User id={} has been deleted ",userId);
    }

    public User getUserById(long userId){
        return userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found"));
    }

}
