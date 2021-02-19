package viewmodels;

import androidx.lifecycle.ViewModel;

import com.example.todoandroid.repositories.UserRepository;

import models.User;

public class UserViewModel extends ViewModel {

    private static final UserViewModel ourInstance = new UserViewModel();
    private final UserRepository userRepository = new UserRepository();

    public static UserViewModel getInstance(){
        return ourInstance;
    }

    private UserViewModel(){
    }

    public void addUser(User user){
        this.userRepository.addUser(user);
    }
    public UserRepository getUserRepository(){
        return userRepository;
    }



    public void validateUser(String email, String password){
        if (!email.isEmpty()){
            if (!password.isEmpty()){
                this.userRepository.getUser(email, password);
            }
        }
    }
}
