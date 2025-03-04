package com.esprit.socialnetwork.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.esprit.socialnetwork.payload.request.PasswordUpdateDTO;
import com.esprit.socialnetwork.payload.request.UpdateUsernameDTO;
import com.esprit.socialnetwork.user.auth.AuthService;
import com.esprit.socialnetwork.Utils.ApiResponse;
import com.esprit.socialnetwork.Utils.Consts;
import com.esprit.socialnetwork.exceptions.InvalidUserException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService implements IUserService {


    private final UserRepository userRepo;
    private final AuthService authS;
    private final PasswordEncoder passwordEncoder;


    @Override
    public User saveUser(User user) {
        user.setPasswd(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }
    @Override
    public User UpdateUser(User user , long id) {

        User userToUpdate = userRepo.findById(id).get();
        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setPasswd(user.getPassword());
        userToUpdate.setEmail(user.getEmail());

        return userRepo.save(userToUpdate);

    }





    @Override
    public void deleteUser(long id) {
        userRepo.deleteById(id);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {

    }

  /*  @Override
    public void addRoleToUser(String username, String roleName) {
        user user = userRepo.find(username);
        Role role = roleRepo.findByName(roleName);
        user.getRoles().add(role);
    }*/

    @Override
    public User getUser(String username) {
        return userRepo.findByEmail(username).orElse(null);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepo.findById(id);
    }

    @Override
    public List<User> getUsers() {
        return userRepo.findAll();
    }

    @Override
    public void sendPassMail(String userEmail) throws UnsupportedEncodingException {

    }

    @Override
    public void verifyPassToken(String token, String newPassword) {

    }



    /*@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        user user = userRepo.findByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException("user not found");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add (new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.user(user.getUsername(),user.getPassword(),authorities);
    }
*/
    /*public void sendPassMail(String userEmail) throws  UnsupportedEncodingException {

        String toAddress = userEmail;
        String fromAddress = "Elbey.pidev@gmail.com";
        String senderName = "Esprit-Groupe";
        String subject = "Your Esprit-Groupe password";
        String content = "Dear Mr/Madame, did you want to reset your password ? "
                + "Someone (hopefully you) has asked us to reset the password for your<br>"
                + "account. Please click the button below to do so. If you didn't<br>"
                + "request this password reset, you can go ahead and ignore this email!"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">RESET PASSWORD</a></h3>"
                + "Thank you,<br>"
                + "Esprit-Groupe.";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        String token = UUID.randomUUID().toString();

        String verifyURL = "http://localhost:8001/oauth/resetPassword/" + token;

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);


        mailSender.send(message);

        Optional<user> user = userRepo.findByEmail(userEmail);


        user.get().setPasswordToken(token);
        userRepo.save(user.get());


    }*/

   /* private void sendVerificationEmail(user user) throws MessagingException, UnsupportedEncodingException {

        String toAddress = user.getEmail();
        String fromAddress = "Elbey.pidev@gmail.com";
        String senderName = "RE-Xpert";
        String subject = "Please verify your registration";
        String content = "Dear Mr/Madame,<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "RE-Xpert.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        String verifyURL = "http://localhost:8085/oauth/verify/" + user.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);

        System.out.println("Email has been sent");
    }
*/

   /* public ResponseEntity<user> verify(String verificationCode) {
        if (verificationCode == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<user> user = userRepo.findByVerificationCode(verificationCode);

        if (user.isPresent()) {
            user.get().setVerificationCode(null);
            user.get().setEnabled(true);
            userRepo.save(user.get());
            return ResponseEntity.ok(user.get());
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }*/
    /*public void verifyPassToken(String token,String newPassword) {

        Optional<user> user = userRepo.findByPasswordToken(token);


        String encodedPassword = passwordEncoder.encode(newPassword);
        user.get().setPassword(encodedPassword);

        userRepo.save(user.get());


    }*/

    @Override
    public Optional<User> getUserByAccessToken(String accessToken) {

            Algorithm algorithm = Algorithm.HMAC256("secret"); // Replace secretKey with your actual secret key
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(accessToken);
            String username = decodedJWT.getSubject(); // Extract the username from the decoded JWT

            // Retrieve the user from the database based on the extracted username


            return Optional.ofNullable(userRepo.findByEmail(username).orElseThrow(() -> new InvalidUserException("a", "user_not_found")));

    }
    @Override
    public ResponseEntity<ApiResponse> disableAccount(Long userId) {
        final User user = userRepo.findById(userId)
                .orElseThrow(() -> new InvalidUserException(userId+"", Consts.USER_NOT_FOUND)
                );

        user.setEnabled(false);

        userRepo.save(user);
        return ResponseEntity.ok(ApiResponse.ofSuccess("Account disabled", 200));
    }
    @Override
    public ResponseEntity<ApiResponse> UpdateTheme(ThemeEnum theme, long id) {
        // Fetch the user from the database
        final User user = userRepo.findById(id)
                .orElseThrow(() -> new InvalidUserException(id + "", Consts.USER_NOT_FOUND));

        // If the provided theme is null, default to LIGHT
        if (theme == null) {
            theme = ThemeEnum.LIGHT;
        }

        // Update the user's theme
        user.setTheme(theme);

        // Save the updated user
        userRepo.save(user);

        // Return a success response
        return ResponseEntity.ok(ApiResponse.ofSuccess("Theme updated successfully to " + theme.name(), 200));
    }
    @Override
    public ResponseEntity<ApiResponse> updatePassword(Long userId,
                                                      PasswordUpdateDTO passwordUpdateDto) {

        final User user = userRepo
                .findById(userId)
                .orElseThrow(() -> new InvalidUserException(""+userId, Consts.USER_NOT_FOUND));

        if (!authS.comparePwd(passwordUpdateDto.getOldPassword(), user.getPassword()))
            return ResponseEntity.badRequest().body(ApiResponse.ofError("Incorrect password", 400));

        if (passwordUpdateDto.getOldPassword().equals(passwordUpdateDto.getNewPassword()))
            return ResponseEntity.badRequest().body(ApiResponse.ofError("You used the same password again", 400));

        user.setPasswd(authS.encodePwd(passwordUpdateDto.getNewPassword()));

        userRepo.save(user);

        return ResponseEntity.ok(ApiResponse.ofSuccess("Password updated successfully", 200));
    }

    @Override
    public ResponseEntity<ApiResponse> updateUsername(Long userId, UpdateUsernameDTO updateUsernameDTO) {

        final User user = userRepo.findById(userId)
                .orElseThrow(() -> new InvalidUserException(userId+"", Consts.USER_NOT_FOUND)
                );

        final boolean exists = userRepo.findUserByUsername(updateUsernameDTO.getNewUsername()).isPresent();

        if (exists)
            throw new InvalidUserException(updateUsernameDTO.getNewUsername(), "Username already in use.");


        user.setUsername(updateUsernameDTO.getNewUsername());

        userRepo.save(user);

        return ResponseEntity.ok(ApiResponse.ofSuccess("Username updated successfully!", 200));
    }


}
