package com.esprit.socialnetwork.user;

import com.esprit.socialnetwork.user.dto.UserDTO;
import com.esprit.socialnetwork.Utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private ModelMapper modelMapper;
    private final IUserService userService;


    public final static String FOUND = "FOUND";
    public final static String BAD_REQUEST = "BAD_REQUEST";
    public final static String NOT_FOUND = "NOT_FOUND";



    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers(){
        return ResponseEntity.ok().body(userService.getUsers());
    }




    @GetMapping("/user/{id}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(userService.getUserById(id));
    }
    @GetMapping("/profile")
    public ResponseEntity<Optional<User>> getUserProfile(@RequestHeader("Authorization") String accessToken) {
        String jwtToken = accessToken.replace("Bearer ", "");
        Optional<User> user = userService.getUserByAccessToken(jwtToken);

        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    @PostMapping("/user/save")
    public ResponseEntity<User>saveUser(@RequestBody User user){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }
    @DeleteMapping("deleteUser/{id}")
    public void deleteUser(@PathVariable long id){
        userService.deleteUser(id);
    }

    @PutMapping("/user/update/{userId}")
    public User UpdateUser(@PathVariable("userId") long id, @RequestBody User user) {
        return userService.UpdateUser(user,id);
    }
    @PutMapping("/user/updateTheme/{userId}")
    public ResponseEntity<ApiResponse> updateTheme(
            @PathVariable("userId") long id,
            @RequestBody String theme) { // Accept theme as a string in the request body
        // Convert the string theme to ThemeEnum
        ThemeEnum themeEnum;
        try {
            themeEnum = ThemeEnum.valueOf(theme.toUpperCase());
        } catch (IllegalArgumentException e) {
            // Handle invalid theme values
            return ResponseEntity.badRequest().body(ApiResponse.ofError("Invalid theme value", 400));
        }

        // Call the service method
        return userService.UpdateTheme(themeEnum, id);
    }



   /* @PostMapping("/role/addtouser")
    public ResponseEntity<?>saveRole(@RequestBody RoleToUserForm form){
        userService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }*/




    @PostMapping("/oauth/forgetPassword")

    public ResponseEntity<Object> forgetPassword(@RequestBody UserDTO usersDTO) throws UnsupportedEncodingException {

        User userReq = modelMapper.map(usersDTO, User.class);
        userService.sendPassMail(userReq.getEmail());
        return new ResponseEntity<>(FOUND, HttpStatus.OK);
    }

    @PostMapping("/oauth/resetPassword/{token}")

    public ResponseEntity<Object> resetPass(@PathVariable String token, @RequestBody UserDTO usersDTO) {
        User userReq = modelMapper.map(usersDTO, User.class);
        System.out.println(token+ "  "+ userReq.getPassword());
        userService.verifyPassToken(token,userReq.getPassword());
        return new ResponseEntity<>(FOUND, HttpStatus.OK);
    }
}

