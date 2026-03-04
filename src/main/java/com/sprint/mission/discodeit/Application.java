package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.entity.*;
import com.sprint.mission.discodeit.repository.*;
import com.sprint.mission.discodeit.repository.file.*;
import com.sprint.mission.discodeit.service.*;
import com.sprint.mission.discodeit.service.basic.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Application {

    public static void main(String[] args) {
        // ==============================
        // 1. Repository, Service 준비
        // ==============================
        UserRepository userRepository = new FileUserRepository();
        ChannelRepository channelRepository = new FileChannelRepository();
        MessageRepository messageRepository = new FileMessageRepository();

        UserService userService = new BasicUserService(userRepository);
        ChannelService channelService = new BasicChannelService(channelRepository);

        MessageService messageService = new BasicMessageService(
                messageRepository,
                userRepository,
                channelRepository
        );

        // ==============================
        // 2. "DB 초기화/ 모든 데이터 삭제"
        //===============================
        for (Message m : messageService.findAll()) {
            messageService.delete(m.getId());
        }
        for (User u : userService.findAll()) {
            userService.delete(u.getId());
        }
        for (Channel c : channelService.findAll()) {
            channelService.delete(c.getId());
        }

        // * 예외 발생 테스트(존재 하지 않는 User/Channel로 메시지 생성
        System.out.println("-------------예외 케이스 테스트-------------");
        try {
            messageService.create(
                    "존재하지 않는 데이터로 메시지 생성",
                    UUID.randomUUID(), // 없는 채널ID
                    UUID.randomUUID() // 없는 유저ID
            );
        } catch (IllegalArgumentException e) {
            System.out.println("[예외 발생!] " + e.getMessage());
        }

        //===============================
        // 3.테스트 데이터 생성 및 출력
        //===============================
        User user = setupUser(userService);
        System.out.println("[User] 생성: " + user.getId());

        Channel channel = setupChannel(channelService);
        System.out.println("[Channel] 생성: " + channel.getId());

        Message message = setupMessage(messageService, channel, user);
        System.out.println("[Message] 생성: " + message.getId());

        //============================
        //4. 상세 조회 - Optional
        //============================
        System.out.println("--------------유저 조회------------");
        Optional<User> foundUser = userService.findById(user.getId());
        foundUser.ifPresent(u -> System.out.println("id = " + u.getId() + ", name = " + u.getName()));

        System.out.println("--------------채널 조회------------");
        Optional<Channel> foundChannel = channelService.findById(channel.getId());
        foundChannel.ifPresent(c -> System.out.println("id = " + c.getId() + ", channel = " + c.getName()));

        System.out.println("-------------메시지 조회------------");
        Optional<Message> foundMessage = messageService.findById(message.getId());
        foundMessage.ifPresent(m -> System.out.println("id = " + m.getId() + ", content = " + m.getContent()));

        //===========================
        // 5. 전체 갯수 출력 ( 모두 List로 형 변환 없이)
        //===========================
        List<User> allUsers = userService.findAll();
        List<Channel> allChannels = (List<Channel>) channelService.findAll();
        List<Message> allMessages = messageService.findAll();

        System.out.println("[User] 전체 수: " + allUsers.size());
        System.out.println("[Channel] 전체 수: " + allChannels.size());
        System.out.println("[Message]전체 수: " + allMessages.size());

        //=============================
        //6. 삭제 테스트
        //=============================
        userService.delete(user.getId());
        channelService.delete(channel.getId());
        messageService.delete(message.getId());

        System.out.println("[삭제 후 User] 전체 수: " + userService.findAll().size());
        System.out.println("[삭제 후 Channel] 전체 수: " + ((List<Channel>) channelService.findAll()).size());
        System.out.println("[삭제 후 Message] 전체 수: " + messageService.findAll().size());

    }

    static User setupUser(UserService userService) {
        return userService.create("woody", "woody@codeit.com", "woody1234");
    }

    static Channel setupChannel(ChannelService channelService) {
        return channelService.create("공지", "공지 채널입니다.");
    }

    static Message setupMessage(MessageService messageService, Channel channel, User author) {
        return messageService.create("안녕하세요.", channel.getId(), author.getId());
        
    }
}

