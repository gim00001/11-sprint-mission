package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.FileResponseDto;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.service.FileMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileMessageController {
    private final FileMessageService fileMessageService;

    // 1. 단일 바이너리 파일 조회( ex: /files/{fileId})
    @RequestMapping(value = "/{fileId}", method = RequestMethod.GET)
    public FileResponseDto getFile(@PathVariable UUID fileId) {
        return fileMessageService.findById(fileId);
    }

    // 2. 다중 바이너리 파일 조회( ex: /files?ids=xxx,yyy,zzz)
    @RequestMapping(method = RequestMethod.GET)
    public List<FileResponseDto> getFiles(@RequestParam List<UUID> ids) {
        return fileMessageService.findAllByIds(ids);
    }

    @GetMapping("/find")
    public ResponseEntity<BinaryContent> findBinaryContent(@RequestParam UUID binaryContentId) {
        BinaryContent content = fileMessageService.findBinaryContentById(binaryContentId);
        if (content == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(content);
    }
}
