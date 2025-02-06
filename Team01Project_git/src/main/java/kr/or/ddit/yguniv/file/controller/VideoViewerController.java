package kr.or.ddit.yguniv.file.controller;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.yguniv.atch.dao.AtchFileMapper;
import kr.or.ddit.yguniv.vo.AtchFileDetailVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/video/{atchFileId}/{fileSn}")
public class VideoViewerController {
	@Autowired
	private AtchFileMapper mapper;
	
	@GetMapping
    public ResponseEntity<ResourceRegion> getVideo(
    	@RequestHeader HttpHeaders headers
    	, @PathVariable Integer atchFileId
    	, @PathVariable Integer fileSn
    ) throws IOException {
		AtchFileDetailVO atchFileDetailVO = mapper.selectAtchFileDetail(atchFileId, fileSn);
		
        Resource video = new FileSystemResource(String.format("%s", atchFileDetailVO.getFileStreCours()));
        ResourceRegion resourceRegion;
        
        if(!video.exists()) {
        	log.info("{} 존재안함", video.getFilename());
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        
        final long chunkSize = 1000000L;
        long contentLength = video.contentLength();

        Optional<HttpRange> optional = headers.getRange().stream().findFirst();
        HttpRange httpRange;
        if (optional.isPresent()) {
            httpRange = optional.get();
            long start = httpRange.getRangeStart(contentLength);
            long end = httpRange.getRangeEnd(contentLength);
            long rangeLength = Long.min(chunkSize, end - start + 1);
            resourceRegion = new ResourceRegion(video, start, rangeLength);
        } else {
            long rangeLength = Long.min(chunkSize, contentLength);
            resourceRegion = new ResourceRegion(video, 0, rangeLength);
        }

        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .contentType(MediaTypeFactory.getMediaType(video).orElse(MediaType.APPLICATION_OCTET_STREAM))
                .body(resourceRegion);
    }
}
