package Services;

import Model.User;
import Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Blob;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProfileService {
    @Autowired
    private UserRepository repository;
    public List<User> findAll(){
        return repository.findAll();
    }

    public void createProfile(String username, String nickname, String biography, String mail, String password, Blob[] files) throws IOException {
        User user = new User(username, nickname, biography, mail, password, files);
        repository.save(user);
    }

    public Optional<User> findById(UUID id){
        return repository.findById(id);
    }
}
