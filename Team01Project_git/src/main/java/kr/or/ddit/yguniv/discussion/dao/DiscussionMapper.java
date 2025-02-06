package kr.or.ddit.yguniv.discussion.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.yguniv.vo.DiscussionVO;

@Mapper
public interface DiscussionMapper {
	/**
	 * R-상담 신청 목록 조회
	 */
	public List<DiscussionVO> selectDiscussionRequestList();
	/**
	 * R-상담 신청 상세 조회
	 */
	public DiscussionVO selectDiscussionRequest(DiscussionVO discussionVO);
	/**
	 * C-상담 신청 추가
	 */
	public int insertDiscussionRequest(DiscussionVO discussionVO);
	/**
	 * U-상담 신청 수정
	 */
	public int updateDiscussionRequest(DiscussionVO discussionVO);
	/**
	 * D-상담 신청 삭제
	 */
	public int deleteDiscussionRequest(DiscussionVO discussionVO);
	/**
	 * U-상담 신청 승인
	 */
	public int consentDiscussionRequest(DiscussionVO discussionVO);
	/**
	 * U-상담 신청 반려
	 */
	public int returnDiscussionRequest(DiscussionVO discussionVO);
	/**
	 * R-상담 일지 목록 조회
	 */
	public List<DiscussionVO> selectDiscussionList();
	/**
	 * R-상담 일지 상세 조회
	 */
	public DiscussionVO selectDiscussion();
	/**
	 * U-상담 일지 작성
	 */
	public int updateDiscussion(DiscussionVO discussionVO);
}
