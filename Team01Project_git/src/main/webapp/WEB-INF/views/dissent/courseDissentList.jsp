<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<style>
#parentTable{
	background-color: white;
	border-collapse: inherit;
	
}
</style>

<input type="hidden" id="stuId" name="stuId" value="${stuId }">
<input type="hidden" id="lectNo" name="lectNo" value="${lectNo }">
<div id="contextData" data-context-path="${pageContext.request.contextPath}"></div>


<!-- <table id="parentTable" > -->
<!--   <thead> -->
<!--     <tr> -->
<!--       <th>강의번호	</th> -->
<!--       <th>강의명	</th> -->
<!--       <th>학번		</th> -->
<!--       <th>이름		</th> -->
<!--       <th>조회버튼</th> -->
<!--     </tr> -->
<!--   </thead> -->
<!-- </table> -->


<div id="testDiv">
	
</div>
<div id="dissentBox" style="display: flex; gap: 2rem; padding: 1rem;">
    <!-- 왼쪽 테이블 영역 -->
    <div class="left-table-area" style="flex: 1; max-width: 50%;">
        <table class="table table-bordered" style="margin-bottom: 1rem; border: 1px solid #d3d3d3;">
            <thead>
                <tr>
                    <th style="text-align: center; padding: 0.5rem; vertical-align: middle; background-color: #CFE2FF;">학과</th>
                    <th style="text-align: center; padding: 0.5rem; vertical-align: middle; background-color: #CFE2FF;">학년</th>
                    <th style="text-align: center; padding: 0.5rem; vertical-align: middle; background-color: #CFE2FF;">학번</th>
                    <th style="text-align: center; padding: 0.5rem; vertical-align: middle; background-color: #CFE2FF;">이름</th>
                    <th style="text-align: center; padding: 0.5rem; vertical-align: middle; background-color: #CFE2FF;">상세</th>
                </tr>
            </thead>
            <tbody id="tp">
                <c:if test="${not empty dissList }">
                    <c:forEach items="${dissList }" var="diss">
                        <tr>
                            <td style="text-align: center; padding: 0.5rem; vertical-align: middle;">${diss.studentVO.deptCd}</td>
                            <td style="text-align: center; padding: 0.5rem; vertical-align: middle;">${diss.studentVO.gradeCd}</td>
                            <td style="text-align: center; padding: 0.5rem; vertical-align: middle;">${diss.personVO.id }</td>
                            <td style="text-align: center; padding: 0.5rem; vertical-align: middle;">${diss.personVO.nm }</td>
                            <td style="text-align: center; padding: 0.5rem; vertical-align: middle;">
                                <button id="findBtn" class="btn btn-primary btn-sm" 
                                        style="margin: 0; padding: 0.25rem 0.5rem;"
                                        onclick="findOne('${diss.personVO.id }','${diss.lectVO.lectNo }');">
                                    상세
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
            </tbody>
        </table>
        <div class="paging-area">${pagingHTML }</div>
    </div>

    <!-- 오른쪽 상세정보 영역 -->
    <div class="right-detail-area" style="flex: 1; max-width: 50%;">
        <form id="selForm" action="">
            <div id="selDiv">
                <table class="table table-bordered" style="margin-bottom: 1rem; border: 1px solid #d3d3d3;">
                    <tr>
                        <th colspan="4" style="text-align: center; padding: 0.5rem; vertical-align: middle; background-color: #CFE2FF;">상세 정보</th>
                    </tr>
                    <tr>
                        <th style="text-align: center; padding: 0.5rem; vertical-align: middle; background-color: #CFE2FF; width: 20%;">강의명</th>
                        <th style="text-align: center; padding: 0.5rem; vertical-align: middle; background-color: #CFE2FF; width: 20%;">학번</th>
                        <th style="text-align: center; padding: 0.5rem; vertical-align: middle; background-color: #CFE2FF; width: 20%;">이름</th>
                        <th style="text-align: center; padding: 0.5rem; vertical-align: middle; background-color: #CFE2FF; width: 20%;">학점</th>
                    </tr>
                    <tr>
                        <td style="text-align: center; padding: 0.5rem; vertical-align: middle;">미선택</td>
                        <td style="text-align: center; padding: 0.5rem; vertical-align: middle;">미선택</td>
                        <td style="text-align: center; padding: 0.5rem; vertical-align: middle;">미선택</td>
                        <td style="text-align: center; padding: 0.5rem; vertical-align: middle;">미선택</td>
                    </tr>
                    <tr>
                        <th colspan="2" style="text-align: center; padding: 0.5rem; vertical-align: middle; background-color: #CFE2FF;">항목</th>
                        <th colspan="2" style="text-align: center; padding: 0.5rem; vertical-align: middle; background-color: #CFE2FF;">점수</th>
                    </tr>
                    <tr>
                        <th colspan="2" style="text-align: center; padding: 0.5rem; vertical-align: middle;">출석</th>
                        <td colspan="2" style="text-align: center; padding: 0.5rem; vertical-align: middle;">미선택</td>
                    </tr>
                    <tr>
                        <th colspan="2" style="text-align: center; padding: 0.5rem; vertical-align: middle;">과제</th>
                        <td colspan="2" style="text-align: center; padding: 0.5rem; vertical-align: middle;">미선택</td>
                    </tr>
                    <tr>
                        <th colspan="2" style="text-align: center; padding: 0.5rem; vertical-align: middle;">중간고사</th>
                        <td colspan="2" style="text-align: center; padding: 0.5rem; vertical-align: middle;">미선택</td>
                    </tr>
                    <tr>
                        <th colspan="2" style="text-align: center; padding: 0.5rem; vertical-align: middle;">기말고사</th>
                        <td colspan="2" style="text-align: center; padding: 0.5rem; vertical-align: middle;">미선택</td>
                    </tr>
                    <tr>
                        <th colspan="2" style="text-align: center; padding: 0.5rem; vertical-align: middle;">기타</th>
                        <td colspan="2" style="text-align: center; padding: 0.5rem; vertical-align: middle;">미선택</td>
                    </tr>
                    <tr>
                        <th colspan="2" style="text-align: center; padding: 0.5rem; vertical-align: middle;">평균</th>
                        <td colspan="2" style="text-align: center; padding: 0.5rem; vertical-align: middle;">미선택</td>
                    </tr>
                    <tr>
                        <th colspan="2" style="text-align: center; padding: 0.5rem; vertical-align: middle;">성적(A)</th>
                        <td colspan="2" style="text-align: center; padding: 0.5rem; vertical-align: middle;">미선택</td>
                    </tr>
                    <tr>
				        <th colspan="4" style="text-align: center; padding: 0.5rem; vertical-align: middle; background-color: #CFE2FF;">이의 내용</th>
				    </tr>
				    <tr>
				        <td colspan="4" style="padding: 1rem;">미선택</td>
				    </tr>
                    <tr>
                        <th colspan="4" style="text-align: center; padding: 0.5rem; vertical-align: middle; background-color: #CFE2FF;">답변</th>
                    </tr>
                    <tr>
                        <td colspan="4" style="padding: 1rem;">
                            <textarea id="answerCn" name="answerCn" class="form-control" style="width: 100%; min-height: 100px;"></textarea>
                        </td>
                    </tr>
                    <tr>
                    <td colspan="4" style="text-align: right; padding: 0.5rem;">
						<button type="button" class="btn btn-primary" onclick="insertStr();" id="strBtn"><i class="bi bi-pencil-square"></i></button>
					    <button type="button" class="btn btn-primary" onclick="updateForm();" id="upBtn">등록</button>
					</td> 
                    </tr>
                </table>
            </div>
            <div id="textDiv"></div>
        </form>
    </div>
</div>


<script
	src="${pageContext.request.contextPath }/resources/js/dissent/dissentScript.js"></script>

