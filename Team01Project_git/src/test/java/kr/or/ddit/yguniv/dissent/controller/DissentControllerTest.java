package kr.or.ddit.yguniv.dissent.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import kr.or.ddit.yguniv.annotation.RootContextWebConfig;
import kr.or.ddit.yguniv.dissent.service.DissentService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RootContextWebConfig
class DissentControllerTest {

	@Inject
	DissentService service;
	
	void test() {
		assertDoesNotThrow(()->{
			service.readDissentList();
		});
	}
	

}
