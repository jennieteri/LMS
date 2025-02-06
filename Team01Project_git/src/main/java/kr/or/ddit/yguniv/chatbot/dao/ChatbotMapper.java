package kr.or.ddit.yguniv.chatbot.dao;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.yguniv.vo.ChatbotVO;

@Mapper
public interface ChatbotMapper {
	
	
	/** 키워드를 입력하면 이에 해당하는 uri를 반환
	 * @param [키워드] word
	 * @return ChatbotVO [URI]
	 */
	public ChatbotVO selectWord(String word);

}
