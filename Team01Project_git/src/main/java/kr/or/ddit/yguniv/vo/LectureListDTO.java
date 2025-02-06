package kr.or.ddit.yguniv.vo;

import java.util.List;

import lombok.Data;

@Data
public class LectureListDTO {
    private List<LectureVO> lectureList;
    private List<LectureVO> studentLectureList;
    private List<LectureVO> attendeeStudentLectureList;
}
