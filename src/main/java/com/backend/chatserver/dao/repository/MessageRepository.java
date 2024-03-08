package com.backend.chatserver.dao.repository;

import com.backend.chatserver.dao.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
