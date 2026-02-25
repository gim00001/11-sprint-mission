package com.sprint.mission.discodit.service.jcf;

import com.sprint.mission.discodit.entity.Message;
import com.sprint.mission.discodit.service.MessageService;

import java.util.*;

public class JCFMessageService implements MessageService {
     // JCF (예: HashMap, ArrayList 등)로 데이터 보관 & 메소드 구현
    private final Map<UUID, Message>
             messageStore = new HashMap<>();

    //CREATE
    @Override
    public Message createMessage(
            UUID userId,
            UUID channelId,
            String content
    ) {
        Message message =
                new Message(content, userId, channelId);

        messageStore.put(
                message.getId(),
                message
        );
        return message;
    }

    //READ
    @Override
    public Message getMessage(UUID id) {
           return messageStore.get(id);
    }

    //채널별 조회
    @Override
    public List<Message> getAllMessageByChannel(
            UUID channelId
    ) {

        List<Message> result = new ArrayList<>();

        for (Message m : messageStore.values()) {
            if (m.getChannelId().equals(channelId)) {
                result.add(m);
            }
        }

        return result;
    }

    //UPDATE
    @Override
    public void updateMessage(
            UUID id,
            String newContent
    ) {
        Message message = messageStore.get(id);

        if (message != null) {
            message.update(newContent);
        }
    }

    //DELETE
    @Override
    public void deleteMessage(UUID id) {
        messageStore.remove(id);
        }
    }
