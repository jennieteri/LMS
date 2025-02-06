package kr.or.ddit.yguniv.mypage.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.yguniv.board.answerBoard.exception.BoardException;
import kr.or.ddit.yguniv.commons.enumpkg.ServiceResult;
import kr.or.ddit.yguniv.mypage.dao.MypageMapper;
import kr.or.ddit.yguniv.vo.PersonVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MypageServiceImpl implements MypageService {

    @Autowired
    private final MypageMapper mapper;

    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Override
    public PersonVO selectPerson(String id) {
        PersonVO person = mapper.selectMyPage(id);
        log.info("id가 있는지 없는지 확인 {}", person);

        if (person == null) {
            throw new BoardException(String.format("%s 번 글이 없음.", id));
        }
        return person;
    }

    @Override
    @Transactional
    public ServiceResult updatePerson(PersonVO person) {
        log.info("업데이트 요청된 PersonVO: {}", person);

        ServiceResult result;
        AuthenticationManager authService = authenticationManagerBuilder.getObject();
        UsernamePasswordAuthenticationToken inputData =
                new UsernamePasswordAuthenticationToken(person.getId(), person.getPswd());

        try {
            // 인증 단계
            authService.authenticate(inputData);
            log.info("Authentication 성공: ID={}, Password=****", person.getId());

            // 첫 번째 업데이트 실행
            int updateCount = mapper.updateMyPage(person);
            log.info("UpdateMyPage 실행 결과: {} rows updated", updateCount);

            if (updateCount > 0) {
                // 상태 변경 시도
                int rowsUpdated = updateStudentCategory(person);
                if (rowsUpdated > 0) {
                    log.info("학생 상태 변경 및 MERGE 성공적으로 실행됨");
                } else {
                    log.warn("MERGE 실패: 조건에 일치하는 데이터가 없음");
                }

                // 인증 객체 갱신 (상태 변경 여부와 상관없이 실행)
                changeAuthentication(person);
                result = ServiceResult.OK;
            } else {
                log.warn("Update 실패: 조건에 맞는 레코드 없음");
                result = ServiceResult.FAIL;
            }
        } catch (AuthenticationException e) {
            log.warn("Authentication 실패: 비밀번호가 올바르지 않음. 오류 메시지: {}", e.getMessage());
            result = ServiceResult.INVALIDPASSWORD;
        } catch (Exception e) {
            log.error("업데이트 중 예외 발생: {}", e.getMessage(), e);
            result = ServiceResult.FAIL;
        }
        return result;
    }


    /**
     * 신입생 상태 변경을 처리하는 메서드
     */
    private int updateStudentCategory(PersonVO person) {
        log.info("학생 상태 변경 작업 실행");
        int rowsUpdated = mapper.updateStudentCategoryWithMerge(person);
        log.info("MERGE 실행 결과: {} rows updated", rowsUpdated);
        return rowsUpdated;
    }

    private void changeAuthentication(PersonVO person) {
        Authentication oldAuthentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("기존 인증 객체: {}", oldAuthentication);

        UsernamePasswordAuthenticationToken newAuthToken =
                new UsernamePasswordAuthenticationToken(person.getId(), person.getPswd());

        AuthenticationManager authService = authenticationManagerBuilder.getObject();
        Authentication newAuthentication = authService.authenticate(newAuthToken);

        SecurityContext newContext = SecurityContextHolder.createEmptyContext();
        newContext.setAuthentication(newAuthentication);
        SecurityContextHolder.setContext(newContext);

        log.info("새로운 인증 객체 설정 완료: {}", newAuthentication);
    }

    @Override
    public PersonVO authenticateUser(String id, String pswd) {
        try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(id, pswd);
            AuthenticationManager authService = authenticationManagerBuilder.getObject();
            authService.authenticate(token);
        } catch (AuthenticationException e) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        return mapper.selectMyPage(id);
    }
}




