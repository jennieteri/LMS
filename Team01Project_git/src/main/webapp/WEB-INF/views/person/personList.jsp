<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item">회원 관리</li>
    <li class="breadcrumb-item active" aria-current="page">사용자 목록 리스트</li>
  </ol>
</nav>


<input type="hidden" id="cp" value="${pageContext.request.contextPath }">


<div class="container-fluid">
<br>
	<!-- 상단 섹션 -->
<div class="row mb-3">
    <!-- 사용자 유형별 통계 -->
<div class="col-md-6" id="userChartDiv" style="height: 320px; border: 1px solid #ddd; margin-bottom: 20px; padding: 10px; padding-bottom: 25px;">
    <h6 class="text-center">유형별 사용자 수</h6>
    <canvas id="userChart"></canvas>
</div>

<!-- 기간별 로그인 수 -->
<div class="col-md-6" id="logChartDiv" style="height: 320px; border: 1px solid #ddd; margin-bottom: 20px; padding: 10px; padding-bottom: 25px;">
    <h6 class="text-center">기간별 로그인 수</h6>
    <canvas id="myChart"></canvas>
</div>

</div>


	<!-- 메인 콘텐츠 영역 -->
	<div class="row">
		<!-- 왼쪽 테이블 섹션 -->
		<div class="col-12" style="padding:0;">
			<div>
				<!-- 기존 검색 영역 -->
				<div class="search-area-container mb-3" style="float: right;">
					<div class="search-area" data-pg-target="#searchForm"
						data-pg-fn-name="fnPaging">
						<form:select path="condition.searchType" class="form-select"
							style="width: auto; display: inline-block;">
							<form:option value="" label="전체" />
							<form:option value="title" label="이름" />
							<form:option value="personType" label="사용자유형" />
						</form:select>
						<form:input path="condition.searchWord" class="form-control"
							placeholder="검색어를 입력하세요"
							style="width: 200px; display: inline-block; margin: 0 5px;" />
						<button id="search-btn" class="btn btn-primary">검색</button>
					</div>
				</div>



				<table class="table table-bordered">
					<thead class="table table-primary"> 
						<tr class="text-center align-middle">
							<th>유형</th>
							<th>아이디</th>
							<th>이름</th>
							<th>생년월일</th>
							<th>마지막<br>접속일자</th>
							<th>비밀번호<br>오류횟수</th>
<!-- 							<th>사용 기록</th> -->
						</tr>
					</thead>
					<tbody>
						<c:if test="${not empty list }">
							<c:forEach items="${list }" var="person">
								<tr class="text-center">
									<td id="role" data-id="${person.id}">
										<a href="javascript:void(0);" onclick="showRole('${person.id}')">
									   <c:forEach items="${person.personType}" var="role" varStatus="status">
										    ${role}<c:if test="${!status.last}">,</c:if></c:forEach>
										</a>
									</td>
									<td id="id">${person.id }</td>
									<td id="id"><a
										href="javascript:void(0);"
										onclick="showPersonDetail('${person.id}')">${person.nm}</a></td>
									<td>${person.brdt }</td>
									<td>${fn:substring(person.lastConectDe, 0, 4) }-${fn:substring(person.lastConectDe, 4, 6) }-${fn:substring(person.lastConectDe, 6, 8) }</td>
									<td>${person.pswdFailrCo }</td>
<%-- 									<td><button class="btn btn-primary" id="logBtn" onclick="getPersonLog('${person.id}')">조회</button></td> --%>
								</tr>
							</c:forEach>
						</c:if>
						<c:if test="${empty list }">
							<tr>
								<td colspan="6" class="text-center">검색된 회원이 없습니다.</td>
							</tr>
						</c:if>
					</tbody>
					<tfoot>
						<!-- 검색 페이징 area -->
						<tr align="center">
							<td colspan="7">
								<div class="paging-area" style="margin-top:10px; margin-bottom:10px;">${pagingHTML }</div>
							</td>
						</tr>
					</tfoot>
				</table>
				<div class="md-3" id="button1" style="float: right; margin-bottom: 10px;">
					<button id="singleinsert-btn" class="btn btn-primary">사용자
						추가</button>
					<a href="${pageContext.request.contextPath}/person/new/upload"
						class="btn btn-primary">일괄 추가</a>
				</div>
			</div>
		</div>
		

	</div>
</div>

<form:form id="searchForm" method="get" modelAttribute="condition"
	style="display:none;">
	<form:input path="searchType" />
	<form:input path="searchWord" />
	<input type="hidden" name="page" />
</form:form>
<script
	src="${pageContext.request.contextPath }/resources/js/utils/paging.js"></script>



<%---------------------모달 라인---------------------------------------------------------------------------%>




<!-- 상세정보 모달 -->
<div class="modal fade" id="personDetailModal" tabindex="-1"
	aria-labelledby="personDetailLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="personDetailLabel">사용자 상세 정보</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close"></button>
			</div>
			<div class="modal-body" id="personDetailContent">
				<table class="table table-primary m-0">
					<tbody>
						<tr>
							<th class="text-center">아이디</th>
							<td class="table-light" id="detail-id"></td>
							<th class="text-center">이름</th>
							<td class="table-light" id="detail-nm"></td>
						</tr>
						<!-- 생년월일과 성별 -->
						<tr>
							<th class="text-center">생년월일</th>
							<td class="table-light" id="detail-brdt"></td>
							<th class="text-center">성별</th>
							<td class="table-light"  id="detail-sexdstnCd"></td>
						</tr>
						<!-- 마지막 접속 일자와 핸드폰번호 -->
						<tr>
							<th class="text-center">핸드폰번호</th>
							<td class="table-light" id="detail-mbtlnum"></td>
							<th class="text-center">이메일</th>
							<td class="table-light" id="detail-eml"></td>
						</tr>
						<!-- 이메일과 비밀번호 실패 횟수 -->
						<tr>
							<th class="text-center">마지막 접속 일자</th>
							<td class="table-light" id="detail-lastConectDe"></td>
							<th class="text-center">비밀번호 실패 횟수</th>
							<td class="table-light" id="detail-pswdFailrCo"></td>
						</tr>
					</tbody>
				</table>


			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-info" data-bs-dismiss="modal"
					data-id="${person.id}"
					onclick="showPersonUpdate(document.getElementById('detail-id').textContent)">수정</button>
				<button type="button" class="btn btn-danger" data-bs-dismiss="modal"
					id="deleteBtn">삭제</button>
				<button type="button" class="btn btn-secondary"
					data-bs-dismiss="modal">닫기</button>
			</div>
		</div>
	</div>
</div>

<!-- 수정하기 Modal -->
<div class="modal fade" id="personUpdateModal" tabindex="-1"
	aria-labelledby="updateModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<h1 class="modal-title fs-5" id="updateModalLabel">사용자 수정</h1>
			</div>
			<div class="modal-body" id="personUpdateContent">
				<table class="table table-primary detail-table m-0">
					<tbody>
						<tr>
							<th class="text-center">아이디</th>
							<td class="table-light" id="update-id"></td>
							<th class="text-center">이름</th>
							<td class="table-light" id="update-nm"></td>
						</tr>
						<tr>
							
							<th class="text-center">생년월일</th>
							<td class="table-light" id="update-brdt"></td>
							<th class="text-center">성별</th>
							<td class="table-light" id="update-sexdstnCd"></td>
						</tr>
						<tr>
							
							<th class="text-center">핸드폰번호</th>
							<td class="table-light"><input class="form-control" type="text"
								id="update-mbtlnum" name="mbtlnum"></td>
							<th class="text-center">이메일</th>
							<td class="table-light"><input class="form-control" type="email" id="update-eml"
								name="eml"></td>
						</tr>
						<tr>
							<th class="text-center">마지막 접속 일자</th>
							<td class="table-light" id="update-lastConectDe"></td>
							<th class="text-center">비밀번호 실패 횟수</th>
							<td class="table-light" id="update-pswdFailrCo"></td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary"
					data-bs-dismiss="modal">닫기</button>
				<button type="button" class="btn btn-warning"
					data-bs-dismiss="modal" id="resetpwBtn">비밀번호 초기화</button>
				<button type="button" class="btn btn-primary" id="saveUpdateBtn">저장</button>
			</div>
		</div>
	</div>
</div>

<!-- 권한 등록 modal -->

<div class="modal fade" id="roleModal" tabindex="-1" aria-labelledby="roleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="roleModalLabel">권한 설정</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body fw-bold me-4 text-center">
                <form>
                  <div>
				    <form>
				        <label style="margin-right: 20px;">
				            <input type="checkbox" name="personType" value="STUDENT"> 학생
				        </label>
				        <label style="margin-right: 20px;">
				            <input type="checkbox" name="personType" value="ASSISTANT"> 조교
				        </label>
				        <label style="margin-right: 20px;">
				            <input type="checkbox" name="personType" value="EMPLOYEE"> 교직원
				        </label>
				        <label>
				            <input type="checkbox" name="personType" value="PROFESSOR"> 교수
				        </label>
				    </form>
				</div>	
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
                <button type="button" class="btn btn-primary" id ="role">저장</button>
            </div>
        </div>
    </div>
</div>






<script
	src="${pageContext.request.contextPath}/resources/js/person/person.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/person/role.js"></script>
