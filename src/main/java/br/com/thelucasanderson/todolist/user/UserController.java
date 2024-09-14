package br.com.thelucasanderson.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserRepository userRepository;


    @PostMapping("/")
    public ResponseEntity create(@RequestBody UserModel userModel) {
        var user = this.userRepository.findByEmail(userModel.getEmail());

        if (user != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email já cadastrado!");
        };

        var encryptedPassword = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());
        userModel.setPassword(encryptedPassword);

        UserModel userCreated;
        userCreated = this.userRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }
}
