package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.entity.*;
import com.sprint.mission.discodeit.repository.*;
import com.sprint.mission.discodeit.repository.file.*;
import com.sprint.mission.discodeit.service.*;
import com.sprint.mission.discodeit.service.basic.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Application {
    static User setupUser(UserService userService) {
        return userService.create("woody", "woody@codeit.com", "woody1234");
    }
    static Channel setupChannel(ChannelService channelService) {
        return channelService.create("공지", "공지 채널입니다.");
    }
    static void messageCreateTest(MessageService messageService, Channel channel, User author) {
        Message message = messageService.create("안녕하세요.", channel.getId(), author.getId());
        System.out.println("메시지 생성: " + message.getId());
    }

    public static void main(String[] args) {
        // ==============================
        // 1. Repository, Service 준비
        // ==============================
        UserRepository userRepository = new FileUserRepository();
        ChannelRepository channelRepository = new FileChannelRepository();
        MessageRepository messageRepository = new FileMessageRepository();

        UserService userService = new BasicUserService(userRepository);
        ChannelService channelService = new BasicChannelService(channelRepository);
        MessageService messageService = new BasicMessageService(messageRepository);

        //============================
        // 2. User 생성 및 조회
        //============================
        User user = setupUser(userService);
        Channel channel = setupChannel(channelService);
        messageCreateTest(messageService, channel, user);

        System.out.println("------ 유저 조회 ------");
        userService.findById(user.getId()).ifPresent(u-> System.out.println("id=" + u.getId() + ", name =" + u.getName()));

        System.out.println("------채널 조회--------");
        channelService.findById(channel.getId()).ifPresent(c-> System.out.println("id=" + c.getId() + ", channel =" + c.getName()));

        //=============================
        //4. Message 생성 및 조회
        //=============================
        Message message = messageService.create("안녕하세요!", channel.getId(), user.getId());
        System.out.println("[Message] 생성: " + message.getId());

        Optional<Message> findMessage = messageService.findById(message.getId());
        findMessage.ifPresent(m-> System.out.println("[Message] 내용: " + m.getContent()));

        //============================
        //5. 전체 조회
        //============================
        List<User> allUsers = userService.findAll();
        List<Channel> allChannels = (List<Channel>) channelService.findAll();
        List<Message> allMessages = messageService.findAll();

        System.out.println("[User] 전체 수: " + allUsers.size());
        System.out.println("[Channel] 전체 수: " + allChannels.size());
        System.out.println("[Message]전체 수: "+ allMessages.size());

        //=============================
        //6. 삭제 테스트
        //=============================
        userService.delete(user.getId());
        channelService.delete(channel.getId());
        messageService.delete(message.getId());

        System.out.println("[삭제 후 User] 전체 수: " + allUsers.size());
        System.out.println("[삭제 후 Channel] 전체 수: " + allChannels.size());
        System.out.println("[삭제 후 Message] 전체 수: " + allMessages.size());


    }
}