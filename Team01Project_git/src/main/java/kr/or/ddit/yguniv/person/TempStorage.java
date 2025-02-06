package kr.or.ddit.yguniv.person;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import kr.or.ddit.yguniv.vo.PersonVO;

@Component
public class TempStorage {
	private List<PersonVO> tempUsers = new ArrayList<>();

    // 데이터를 저장
    public void save(List<PersonVO> users) {
        this.tempUsers = users;
    }

    // 저장된 데이터를 반환
    public List<PersonVO> get() {
        return tempUsers;
    }

    // 저장된 데이터를 초기화
    public void clear() {
        this.tempUsers.clear();
    }
}
