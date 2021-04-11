package pdp.uz.cardtransferwithoutdbsecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MyAuthService implements UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        List<User> userList = new ArrayList<>(Arrays.asList(
                new User("Men", passwordEncoder.encode("men123"), new ArrayList<>()),
                new User("Sen", passwordEncoder.encode("sen123"), new ArrayList<>()),
                new User("Biz", passwordEncoder.encode("biz123"), new ArrayList<>()),
                new User("Ular", passwordEncoder.encode("ular123"), new ArrayList<>())
        ));

        for (User user : userList) {
            if (user.getUsername().equals(s)) return user;
        }
        throw new UsernameNotFoundException("Username not found !");
    }
}
