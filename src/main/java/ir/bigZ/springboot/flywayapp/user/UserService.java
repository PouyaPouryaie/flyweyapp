package ir.bigZ.springboot.flywayapp.user;

import ir.bigZ.springboot.flywayapp.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getUsers(){
        return userDao.selectUsers();
    }

    public void addNewUser(User user){
        int result = userDao.insertUser(user);

        if(result != 1){
            throw new IllegalStateException("oops something went wrong");
        }
    }


    public void deleteUser(Integer id) {
        Optional<User> user = userDao.selectUserById(id);
        user.ifPresentOrElse(movie -> {
            int result = userDao.deleteUser(id);
            if (result != 1) {
                throw new IllegalStateException("oops could not delete movie");
            }
        }, () -> {
            throw new NotFoundException(String.format("Movie with id %s not found", id));
        });
    }

    public User getUser(int id) {
        return userDao.selectUserById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Movie with id %s not found", id)));
    }
}
