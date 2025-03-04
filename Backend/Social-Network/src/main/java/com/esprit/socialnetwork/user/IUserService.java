package com.esprit.socialnetwork.user;

import com.esprit.socialnetwork.payload.request.PasswordUpdateDTO;
import com.esprit.socialnetwork.Utils.ApiResponse;
import com.esprit.socialnetwork.payload.request.UpdateUsernameDTO;
import org.springframework.http.ResponseEntity;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

public interface IUserService {


        User UpdateUser(User user , long id);
        User saveUser(User user);



        void deleteUser (long id);

        void addRoleToUser(String username, String roleName);
      User getUser(String username);
        Optional<User> getUserById(Long id);
        List<User> getUsers();

        public void sendPassMail(String userEmail) throws UnsupportedEncodingException;
        public void verifyPassToken(String token,String newPassword);

        Optional<User> getUserByAccessToken(String accessToken);

    ResponseEntity<ApiResponse> UpdateTheme(ThemeEnum theme, long id);

    ResponseEntity<ApiResponse> updatePassword(Long userId,
                                               PasswordUpdateDTO passwordUpdateDto);

    ResponseEntity<ApiResponse> updateUsername(Long userId, UpdateUsernameDTO updateUsernameDTO);

    ResponseEntity<ApiResponse> disableAccount(Long userId);
}

