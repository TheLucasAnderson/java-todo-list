package br.com.thelucasanderson.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserRepository userRepository;


    @PostMapping("/")
    public UserModel create(@RequestBody UserModel userModel) {
        var user = this.userRepository.findByEmail(userModel.getEmail());

        if (user != null) {
            System.out.println("Email já cadastrado!");
            return null;
        };

        UserModel userCreated;
        userCreated = this.userRepository.save(userModel);
        return userCreated;
    }
}
