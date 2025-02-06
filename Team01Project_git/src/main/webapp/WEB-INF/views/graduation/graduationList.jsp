<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>


<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item">졸업인증제</li>
    <li class="breadcrumb-item active" aria-current="page">졸업 인증제 점수 조회</li>
  </ol>
</nav>



<div class="container mt-5">
    <ul class="nav nav-tabs" id="myTab" role="tablist">
        <li class="nav-item" role="presentation">
            <button class="nav-link active" id="english-tab" data-bs-toggle="tab" data-bs-target="#english" type="button" role="tab" aria-controls="english" aria-selected="true">영어 점수</button>
        </li>
        <li class="nav-item" role="presentation">
            <button class="nav-link" id="volunteer-tab" data-bs-toggle="tab" data-bs-target="#volunteer" type="button" role="tab" aria-controls="volunteer" aria-selected="false">봉사활동</button>
        </li>
    </ul>
    <div class="tab-content" id="myTabContent">
<div class="tab-pane fade show active" id="english" role="tabpanel" aria-labelledby="english-tab">
    <br>
    <h5 class="mt-4 text-center"><security:authentication property="principal.realUser.nm" /> 님의 영어 인증 점수</h5>
    <table class="table table-bordered table-hover mt-3">
        <thead class="table-primary">
            <tr>
                <th scope="col" class="text-center">상태</th>
                <th scope="col" class="text-center">인증서류명</th>
                <th scope="col" class="text-center">인증점수</th>
                <th scope="col" class="text-center">발급기관</th>
                <th scope="col" class="text-center">발급일자</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="item" items="${list}">
                <c:if test="${item.gdtType == 'GT02'}">
                    <tr>
                        <td class="text-center">${item.codeName}</td>
                        <td class="text-center">
						    <button class="btn btn-link text-decoration-none" data-bs-toggle="modal" data-id="${item.gdtCd}">
						        ${item.gdtNm}
						    </button>
						</td>
                        <td class="text-center">${item.gdtScore}</td>
                        <td class="text-center">${item.gdtInst}</td>
                        <td class="text-center">${item.gdtIssu}</td>
                    </tr>
                </c:if>
            </c:forEach>
        </tbody>
    </table>
    <div class="d-flex justify-content-end mt-3">
        <button class="btn btn-primary" id="enginsert">등록</button>
    </div>
</div>
<br>
<div class="tab-pane fade" id="volunteer" role="tabpanel" aria-labelledby="volunteer-tab">
    <!-- 상단 제목 -->
    <h5 class="mt-4 text-center"><security:authentication property="principal.realUser.nm" /> 님의 봉사 인증 점수</h5>

    <!-- 총 점수 강조 -->
    <div class="card bg-light text-center mt-3 mb-4">
    <br>
	            <h3 class="fw-bold" style="font-size: 2rem;">
	              ${totalScore} / 75시간
            </h3>
            <p class="text-muted" style="font-size: 0.8rem;">현재 봉사 점수 &nbsp;/&nbsp; 졸업인증 봉사 점수</p>
    </div>

    <!-- 봉사활동 테이블 -->
    <table class="table table-bordered table-hover mt-3">
        <thead class="table-primary">
            <tr>
                <th scope="col" class="text-center">상태</th>
                <th scope="col" class="text-center">인증서류명</th>
                <th scope="col" class="text-center">인증시간</th>
                <th scope="col" class="text-center">발급기관</th>
                <th scope="col" class="text-center">발급일자</th>
            </tr>
        </thead>
        <tbody>
            <!-- 봉사활동 데이터 -->
            <c:forEach var="item" items="${list}">
                <c:if test="${item.gdtType == 'GT01'}">
                    <tr>
                        <td class="text-center">${item.codeName}</td>
                        <td class="text-center">
						    <button class="btn btn-link text-decoration-none" data-bs-toggle="modal" data-id="${item.gdtCd}">
						        ${item.gdtNm}
						    </button>
						</td>
                        <td class="text-center">${item.gdtScore}</td>
                        <td class="text-center">${item.gdtInst}</td>
                        <td class="text-center">${item.gdtIssu}</td>
                    </tr>
                </c:if>
            </c:forEach>
        </tbody>
    </table>

    <!-- 등록 버튼 -->
    <div class="d-flex justify-content-end mt-3">
        <button class="btn btn-primary" id="volinsert">등록</button>
    </div>
</div>

    </div>
</div>

<!-- 상세보기 모달 -->
<div class="modal fade" id="detailModal" tabindex="-1" aria-labelledby="detailModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
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
                        	<tr>
                        		<td class="bg-light text-center"><strong>인증 상태</strong></td>
                                <td id="modalcodeName"></td>
                        		
                        	</tr>
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

            <!-- Footer Section -->
            <div class="modal-footer">
                <button type="button" class="btn btn-info" id="updateBtn">수정</button>
                <button type="button" class="btn btn-danger" id="deleteBtn">삭제</button>
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
            </div>
        </div>
    </div>
</div>


<!-- 수정 모달 -->
<div class="modal fade" id="graduationUpdateModal" tabindex="-1" aria-labelledby="graduationUpdateModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <!-- Header -->
            <div class="modal-header">
                <h5 class="modal-title" id="graduationUpdateModalLabel">졸업 인증제 수정</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <!-- Body -->
            <div class="modal-body">
                <form id="updateForm">
                <table class="table table-bordered">
                    <tbody>
	                        <tr>
	                            <td class="bg-light text-center"><strong>학번</strong></td>
	                            <td id="update-stuId"></td>
	                            <td class="bg-light text-center"><strong>이름</strong></td>
	                            <td id="update-stuNm"></td>
	                        </tr>
	                        <tr>
	                             <td class="bg-light text-center"><strong>종류</strong></td>
	                             <td id="update-Type" colspan="3"></td>
	                         </tr>
	                        
	                        <tr>
	                            <th class="bg-light text-center"><strong>인증 서류명</strong></th>
	                            <td id="update-gdtNm-container">
	                                <input type="text" name="gdtNm" class="form-control">
	                            </td>
	                             <th class="bg-light text-center fw-bold">점수/시간</th>
	                            <td>
	                                <input type="number" name="gdtScore" class="form-control">
	                            </td>
	                        </tr>
	                        <tr>
	                            <td class="bg-light text-center"><strong>발급 기관</strong></td>
	                            <td>
	                                <input type="text" name="gdtInst" class="form-control">
	                            </td>
	                        
	                            <td class="bg-light text-center"><strong>발급일자</strong></td>
	                            <td>
	                                <input type="date" name="gdtIssu" class="form-control">
	                            </td>
	                        </tr>
	                        <tr>
	                          <td class="bg-light text-center"><strong>첨부파일</strong></td>
	                          <td colspan="3">
	                              <input type="file" name="uploadFiles" class="form-control" >
	                          </td>
	                        </tr>
	                    
                    </tbody>
                </table>
                </form>
            </div>
            <!-- Footer -->
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
                <button type="button" class="btn btn-primary" id="saveUpdateBtn">저장</button>
            </div>
        </div>
    </div>
</div>






<input type="hidden" id="cp" value="${pageContext.request.contextPath }">
<input type="hidden" id="principal" value="${principal}"> 
<script src="${pageContext.request.contextPath }/resources/js/graduation/graduationList.js"></script>
