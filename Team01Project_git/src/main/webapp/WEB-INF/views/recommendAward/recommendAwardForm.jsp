<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item">장학금 유형 조회</li>
    <li class="breadcrumb-item active" aria-current="page">장학금 추천서 등록</li>
  </ol>
</nav>

<security:authentication property="principal.username" var="id"/>
<input type="hidden" value="${ask.shapDocNo }" id="shapDocNo" />

<input type="hidden" value="${id }" id="profeId"/>
<input type="hidden" value="${selectedAwardCd }" id="awardCd"/>


<style>
/* 테이블 스타일 */
.table {
    width: 70%;
    margin: 0 auto;
    border-collapse: collapse;
}

th, td {
    padding: 10px;
    text-align: center;
    vertical-align: middle;
    border: 1px solid #ddd;
}

th {
    font-size: 18px;
    font-weight: bold;
}

/* 개인정보 제공동의 영역 스타일 */
.bg-light {
    text-align: center; /* 전체 내용 가운데 정렬 */
}

.bg-light div, .bg-light label {
    display: inline-block; /* 인라인 블록으로 설정 */
    text-align: center; /* 내부 텍스트 정렬 */
    margin-right: 10px; /* 라벨 간 간격 */
}

input[type="radio"] {
    margin-left: 5px; /* 라디오 버튼 간격 */
}

/* 버튼 오른쪽 정렬 */
.bg-light {
    text-align: right; /* 오른쪽 정렬 */
}
</style>




<form:form method="post" enctype="multipart/form-data"
           modelAttribute="ask"
           action="${pageContext.request.contextPath }/recommendAward/createRecAward"
           id="createAward">
	<table class="table table-primary">
			  <tr>
			    <th style="text-align: center">장학 유형 코드</th>
			    <td class="bg-light">
			        <input type="text" name="awardCd" value="${selectedAwardCd}" class="form-control" readonly />
			    </td>
			</tr>
			
			<tr>
			    <th style="text-align: center">장학 유형 이름</th>
			    <td class="bg-light">
			        <input type="text" name="awardNm" value="${selectedAwardNm}" class="form-control" readonly />
			    </td>
			</tr>
			 <tr>
            <th>학기번호(년도/학기)</th>
            <td  class="bg-light">
            
            	<form:select path="semstrNo" Class="form-select" aria-label="Default select example">
            	<option selected>학기 번호를 선택하세요.</option>
            		<form:option value="202501" label="2025-01" />
            		<form:option value="202502" label="2025-02" />
            		<form:option value="202601" label="2026-01" />
            		<form:option value="202602" label="2026-02" />
            	</form:select>
            </td>
            
        </tr>  
        	
        	
        
        	<tr>
		    <th style="text-align: center">추천 학생</th>
		    
		    <td colspan="3" class="bg-light">
		    	<form:input path="stuId" cssClass="form-control" placeholder="추천 학생 입력" list="stuList"/>
		            <datalist id="stuList">
		            	<c:forEach items="${stuList }" var="stu">
		                    <option value="${stu.stuId }" label="${stu.nm }" />
		            	</c:forEach>
		            </datalist>    
		        <form:errors path="stuId" element="span" class="text-danger" />
		    </td>
		</tr>
        	<tr>
		    <th style="text-align: center">추천사유 </th>
		    <td class="bg-light">
	            <form:input type="text" path="shapRecommend" cssClass="form-control" />
	            <form:errors path="shapRecommend" cssClass="text-danger" />
	        </td>
		</tr>
		 <tr>
            <th colspan="1" style="text-align: center">첨부파일</th>
            <td colspan="3" class="bg-light">
             <input type="file" name="uploadFiles" multiple class="form-control"/> 
            </td>

        </tr>
         <!-- 동의 항목: 개인정보 제공동의 -->
		<tr align="center">
		    <th style="text-align: center">개인정보 제공동의</th>
		    <td colspan="3" class="bg-light"  style="text-align: center">
		        <div>개인정보 제공동의서에 동의 하시겠습니까?</div>
		        <br>
		        <label>
		            <div id="result" style="display: none"></div>
		            <input type="radio" name="financialInfoAgreement" value="동의" onclick="getCheck(event)" required> 예, 동의합니다.
		        </label>
		        <label>
		            <input type="radio" name="financialInfoAgreement" value="미동의" onclick="getCheck(event)"> 아니요
		        </label>
		    </td>
		</tr>
		<tr>
		<td colspan="4" align="right" class="bg-light">
		   
		        <button type="submit" class="btn btn-primary">등록</button>
		        <a href="<c:url value='/award' />" class="btn btn-danger">취소</a>
		   
		    </td>
		</tr>
		
        	
 
 </table>

</form:form>
<script src="${pageContext.request.contextPath }/resources/js/awardAsk/check.js"></script> 