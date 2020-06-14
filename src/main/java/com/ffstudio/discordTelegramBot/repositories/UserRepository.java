package com.ffstudio.discordTelegramBot.repositories;

import com.ffstudio.discordTelegramBot.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
