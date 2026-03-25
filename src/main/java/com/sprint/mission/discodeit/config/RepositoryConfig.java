package com.sprint.mission.discodeit.config;

import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.file.FileChannelRepository;
import com.sprint.mission.discodeit.repository.file.FileMessageRepository;
import com.sprint.mission.discodeit.repository.file.FileUserRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFChannelRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFMessageRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {

    private final RepositoryProperties properties;

    public RepositoryConfig(RepositoryProperties properties) {
        this.properties = properties;
    }

    @Bean
    public UserRepository userRepository() {
        String type = properties.getType();
        if ("file".equalsIgnoreCase(type)) {
            return new FileUserRepository(properties.getFileDirectory());
        } else {
            return new JCFUserRepository();
        }
    }

    @Bean
    public ChannelRepository channelRepository() {
        String type = properties.getType();
        if ("file".equalsIgnoreCase(type)) {
            return new FileChannelRepository(properties.getFileDirectory());
        } else {
            return new JCFChannelRepository();
        }
    }

    @Bean
    public MessageRepository messageRepository() {
        String type = properties.getType();
        if ("file".equalsIgnoreCase(type)) {
            return new FileMessageRepository(properties.getFileDirectory());
        } else {
            return new JCFMessageRepository();
        }
    }

}
