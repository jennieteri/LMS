<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item">학생 관리</li>
    <li class="breadcrumb-item active" aria-current="page">졸업인증제 리스트</li>
  </ol>
</nav>
    
		<div class="input-group mb-3 float-end search-area" data-pg-target="#searchForm" data-pg-fn-name="fnPaging" style="width: 350px;">
					<form:select path="condition.searchType" class="form-select" >
						<form:option value="" label="전체" />
						<form:option value="number" label="학번" />
						<form:option value="name" label="종류" />
						</form:select>
					<form:input path="condition.searchWord" class="form-control" placeholder="검색어를 입력하세요" style="width: 150px;"/>
						<button id="search-btn" class="btn btn-primary">검색</button>
	 		</div>

        <table class="table table-bordered table-hover">
        
            <thead class="table-primary text-center">
                <tr>
                    <th scope="col">번호</th>
                    <th scope="col">학번</th>
                    <th scope="col">종류</th>
                    <th scope="col">인증 서류명</th>
                    <th scope="col">발급 기관</th>
                    <th scope="col">발급일자</th>
                    <th scope="col">시간/점수</th>
                    <th scope="col">상태</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${list}">
                    <tr>
                        <td class="text-center">${item.rnum}</td>
                        <td class="text-center">${item.stuId}</td>
                        <td class="text-center">
                            <c:choose>
                                <c:when test="${item.gdtType == 'GT01'}">봉사활동</c:when>
                                <c:when test="${item.gdtType == 'GT02'}">영어인증</c:when>
                                <c:otherwise>기타</c:otherwise>
                            </c:choose>
                        </td>
                		<td class="text-center">
						    <button class="btn btn-link text-decoration-none" data-bs-toggle="modal" data-id="${item.gdtCd}">
						        ${item.gdtNm}
						    </button>
						</td>

                        <td class="text-center">${item.gdtInst}</td>
                        <td class="text-center">${item.gdtIssu}</td>
                        <td class="text-center">${item.gdtScore}</td>
                        <td class="text-center">
                            <c:choose>
                                <c:when test="${item.cocoCd == 'G003'}">
                                    <span class="badge bg-warning text-dark">대기</span>
                                </c:when>
                                <c:when test="${item.cocoCd == 'G001'}">
                                    <span class="badge bg-success">승인</span>
                                </c:when>
                                <c:when test="${item.cocoCd == 'G002'}">
                                    <span class="badge bg-danger">반려</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge bg-secondary">알 수 없음</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
            
            
			<tfoot>
				<tr>
					<td colspan="8">
						<div class="paging-area">
						${pagingHTML }
						
						</div>
					</td>
				</tr>
			</tfoot>
        </table>
        
        
        
<div class="modal fade" id="detailModal" tabindex="-1" aria-labelledby="detailModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-xl">
        <div class="modal-content">
            <!-- Header Section -->
            <div class="modal-header" style="background-color: #003399; color: white;">
                <h5 class="modal-title" id="detailModalLabel">졸업인증제 상세 보기</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>

            <!-- Body Section -->
            <div class="modal-body">
                <!-- Styled Information Section -->
                <div class="container">

                    <!-- Information Table -->
                    <table class="table table-bordered">
                        <tbody>
                            <!-- 학번 및 이름 -->
                            <tr>
                                <td class="bg-light text-center" style="width: 20%;"><strong>학번</strong></td>
                                <td id="modalStuId" style="width: 30%;"></td>
                                <td class="bg-light text-center" style="width: 20%;"><strong>이름</strong></td>
                                <td id="modalStuName" style="width: 30%;"></td>
                            </tr>

                            <!-- 종류 -->
                            <tr>
                                <td class="bg-light text-center"><strong>종류</strong></td>
                                <td id="modalType" colspan="3"></td>
                            </tr>

                            <!-- 발급 기관 및 발급일자 -->
                            <tr>
                                <td class="bg-light text-center"><strong>발급기관</strong></td>
                                <td id="modalInst"></td>
                                <td class="bg-light text-center"><strong>발급일자</strong></td>
                                <td id="modalIssu"></td>
                            </tr>
							 <!-- 인증 서류명 -->
                              <tr>
                                <td class="bg-light text-center"><strong>서류명</strong></td>
                                <td id="modalNm"></td>
                                <td class="bg-light text-center"><strong>점수/시간</strong></td>
                                <td id="modalScore"></td>
                            </tr>
                            <!-- 첨부 파일 -->
                            <tr>
                                <td class="bg-light text-center"><strong>첨부 파일</strong></td>
                                <td id="fileContainer" colspan="3">
                                    <p class="text-muted">첨부 파일 없음</p>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>

            <!-- Additional Notes -->
            <div class="mt-3 text-center">
                <p class="text-muted">위 내용은 졸업 인증 서류와 관련된 세부 정보입니다. 
                <br> 필요한 경우, 첨부 파일을 다운로드하거나 추가 작업을 수행할 수 있습니다.</p>
            </div>

            <!-- Footer Section -->
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="approveBtn">승인</button>
                <button type="button" class="btn btn-warning" id="rejectBtn">반려</button>
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
            </div>
        </div>
    </div>
</div>


<input type="hidden" id="cp" value="${pageContext.request.contextPath}">
<script src="${pageContext.request.contextPath}/resources/js/graduation/graduationListEmp.js"></script>