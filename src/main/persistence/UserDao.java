package main.persistence;

import main.model.User;

public interface UserDao extends Dao<String, User>{

    default User create(String email){
        return new User(email);
    }

}
