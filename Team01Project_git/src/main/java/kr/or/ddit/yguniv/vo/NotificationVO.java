package kr.or.ddit.yguniv.vo;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.jsonschema.JsonSerializableSchema;

import lombok.Data;


@Data
public class NotificationVO  implements Serializable {
	
	private int notiNo;
	private String notiCd;
	private String notiCdNm;
	private String notiCn;
	private String notiDate;
	private String notiYn;
	private String notiUrl;
	private String recpId;
	private List<String> recpIdList;
	private String sendId;
	private String sendNm;
	private String notiHead;
}
