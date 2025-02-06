package kr.or.ddit.yguniv.login.service;

import org.springframework.stereotype.Service;

import kr.or.ddit.yguniv.login.dao.PersonFindLoginMapper;
import kr.or.ddit.yguniv.vo.PersonVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class PersonFindLoginServiceImpl implements PersonFindLoginService {

    private final PersonFindLoginMapper mapper;

    @Override
    public String selectFindForm(String nm, String brdt) {
        try {
            // 파라미터 검증
            if (nm == null || nm.isEmpty() || brdt == null || brdt.isEmpty()) {
                log.warn("잘못된 입력값: 이름={}, 생년월일={}", nm, brdt);
                throw new IllegalArgumentException("이름과 생년월일은 필수 입력값입니다.");
            }

            // 매퍼 호출 로그
            log.info("매퍼 호출: 이름={}, 생년월일={}", nm, brdt);

            // 데이터베이스 쿼리 실행
            String id = mapper.selectFindForm(nm, brdt);

            // 쿼리 결과 로그
            log.info("조회된 학번: {}", id);

            if (id == null) {
                log.warn("해당하는 학번을 찾을 수 없음: 이름={}, 생년월일={}", nm, brdt);
                throw new RuntimeException("해당하는 학번을 찾을 수 없습니다.");
            }

            return id;

        } catch (IllegalArgumentException e) {
            log.error("잘못된 요청 처리", e);
            throw e;
        } catch (Exception e) {
            log.error("학번 조회 중 예외 발생", e);
            throw new RuntimeException("학번 조회 중 오류 발생", e);
        }
    }
}



