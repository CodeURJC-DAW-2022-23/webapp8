package Services;

import Model.RegisteredUser;
import Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProfileService {
    @Autowired
    private UserRepository repository;
    public List<RegisteredUser> findAll(){
        return repository.findAll();
    }

    /*public void createProfile(String username, String nickname, String biography, String mail, String password, Blob[] files) throws IOException {
        RegisteredUser user = new RegisteredUser(username, nickname, biography, mail, password, files);
        repository.save(user);
    }*/

    public Optional<RegisteredUser> findById(UUID id){
        return repository.findById(id);
    }
}
