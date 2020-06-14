package com.ffstudio.discordTelegramBot.services;

import com.ffstudio.discordTelegramBot.models.User;
import com.ffstudio.discordTelegramBot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getBestStalkers() {
        int size = userRepository.findAll().size();
        if (size > 3) {
            return userRepository.findAll(PageRequest.of(0,3, Sort.by(Sort.Direction.DESC, "rating"))).getContent();
        } else {
            return userRepository.findAll(PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "rating"))).getContent();
        }
    }

    @Override
    public int getUserRating(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            return userRepository.findById(userId).get().getRating();
        } else return 0;
    }

    public String getUserAnswer(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            return userRepository.findById(userId).get().getCurrentAnswer();
        } else return "";
    }

    public void setUserAnswer(Long userId, String answer) {
        if (userRepository.findById(userId).isPresent()) {
            User user = userRepository.findById(userId).get();
            user.setCurrentAnswer(answer);
            userRepository.save(user);
        }
    }

    public boolean createUser(Long userId, String nickname, String platform) {
        User newUser = new User(userId, null, nickname, null,platform,0);
        if (userRepository.findById(userId).isPresent()) {
            return false;
        } else {
            userRepository.save(newUser);
            return true;
        }
    }

    public boolean userHasAnswer(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            return userRepository.findById(userId).get().getCurrentAnswer() != null;
        }
        return false;
    }

    @Override
    public boolean changeName(Long userId, String newName) {
        if (userRepository.findById(userId).isPresent()) {
            User user = userRepository.findById(userId).get();
            user.setNickname(newName);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public int getCurrentRating(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            return userRepository.findById(userId).get().getRating();
        }
        return 0;
    }

    public void addUserPoint(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            User user = userRepository.findById(userId).get();
            user.setRating(user.getRating()+1);
            userRepository.save(user);
        }
    }
}
