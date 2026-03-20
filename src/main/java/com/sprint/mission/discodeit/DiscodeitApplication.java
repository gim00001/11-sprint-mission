package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.dto.*;
import com.sprint.mission.discodeit.service.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@SpringBootApplication
public class DiscodeitApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DiscodeitApplication.class, args);

        // ==============================
        // 1. 서비스/리포지토리 가져오기
        // ==============================
        UserService userService = context.getBean(UserService.class);
        ChannelService channelService = context.getBean(ChannelService.class);
        MessageService messageService = context.getBean(MessageService.class);
        ReadStatusService readStatusService = context.getBean(ReadStatusService.class);
        UserStatusService userStatusService = context.getBean(UserStatusService.class);
        BinaryContentService binaryContentService = context.getBean(BinaryContentService.class);

        // ==============================
        // 2. "모든 데이터 삭제" (테스트 전 초기화)
        // ==============================
        // 유저, 채널, 메시지 등 구체 서비스에 findAll(), delete() 등 호출해서 각 데이터 전체 삭제하세요.
        // 예: findAll/getAll 등 기능 이름에 따라 맞게 어댑트
        for (UserResponseDto user : userService.findAll()) {
            userService.delete(user.getId());
        }
        for (ChannelResponseDto channel : channelService.findAllByUserId(null)) { // 모든 채널 반환하도록 구현 필요
            channelService.delete(channel.getId());
        }

        // ==============================
        // 3. 테스트용 데이터 생성
        // ==============================

        // 3-1. User 생성
        UserCreateRequestDto userDto = new UserCreateRequestDto();
        userDto.setUsername("testuser");
        userDto.setEmail("test@email.com");
        userDto.setPassword("secret");
        UserResponseDto savedUser = userService.create(userDto);
        System.out.println("유저 등록 결과: " + savedUser.getUsername() + " (ID: " + savedUser.getId() + ")");

        // 3-2. 채널(PUBLIC) 생성
        PublicChannelCreateRequestDto pubChDto = new PublicChannelCreateRequestDto();
        pubChDto.setName("public-ch");
        pubChDto.setDescription("공개 채널");
        ChannelResponseDto savedPubCh = channelService.createPublic(pubChDto);
        System.out.println("채널 등록 결과: " + savedPubCh.getName());

        // 3-3. 메시지 + 첨부파일 생성
        MessageCreateRequestDto msgCreateDto = new MessageCreateRequestDto();
        msgCreateDto.setChannelId(savedPubCh.getId());
        msgCreateDto.setAuthorId(savedUser.getId());
        msgCreateDto.setContent("첫번째 메시지! Hi Codeit!");
        // 첨부파일 (예시)
        BinaryContentCreateRequestDto attachDto = new BinaryContentCreateRequestDto();
        attachDto.setContent("HelloFile".getBytes());
        attachDto.setContentType("text/plain");
        msgCreateDto.setAttachments(List.of(attachDto));
        MessageResponseDto savedMsg = messageService.create(msgCreateDto);
        System.out.println("메시지 등록 결과: " + savedMsg.getId());

        // 3-4. 메시지 수정 (내용 변경)
        MessageUpdateRequestDto updateMsgDto = new MessageUpdateRequestDto();
        updateMsgDto.setId(savedMsg.getId());
        updateMsgDto.setContent("수정된 메시지!");
        messageService.update(updateMsgDto);
        System.out.println("메시지 수정 완료!");

        // 3-5. 메시지 전체 조회
        List<MessageResponseDto> allMsgs = messageService.findAllByChannelId(savedPubCh.getId());
        System.out.println("[모든 메시지]");
        allMsgs.forEach(m -> System.out.println(m.getId() + " : " + m.getContent()));

        // 3-6. 메시지/첨부파일 삭제 (최종 상태 점검)
        messageService.delete(savedMsg.getId());
        System.out.println("메시지 삭제 완료!");
        // BinaryContentService 등도 원한다면 findAllByIdIn로 확인/삭제

        // 기타 ReadStatusService, UserStatusService 등 동작 확인도 위 패턴대로 테스트 가능

        System.exit(0); // (테스트 후, 서버 자동 종료)
    }
}