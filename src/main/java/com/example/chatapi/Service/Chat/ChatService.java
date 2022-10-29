package com.example.chatapi.Service.Chat;

import com.example.chatapi.Repository.Chat.ChatLogRepository;
import com.example.chatapi.Repository.Chat.ChatMBTIRepository;
import com.example.chatapi.Repository.Chat.ChatRoomRepository;
import com.example.chatapi.Repository.Chat.ChatUserRepository;
import com.example.chatapi.Repository.MbtiRepository;
import com.example.chatapi.Repository.User.UserRepository;
import com.example.chatapi.Service.MBTI.MbtiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    protected final UserRepository userRepository;

    protected final MbtiRepository mbtiRepository;

    protected final ChatRoomRepository chatRoomRepository;
    protected final ChatUserRepository chatUserRepository;
    protected final ChatMBTIRepository chatMBTIRepository;

    protected final ChatLogRepository chatLogRepository;

    protected final MbtiService mbtiService;
}
