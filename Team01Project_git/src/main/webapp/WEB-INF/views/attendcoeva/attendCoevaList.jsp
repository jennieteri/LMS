


<!-- /Team01Project/src/main/webapp/resources/js/attendcoeva/attendCoevaScript.js -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item">강의</li>
    <li class="breadcrumb-item active" aria-current="page">강의 평가 및 성적 조회</li>
  </ol>
</nav>

<input type="hidden" id="id" name="id" value="${id }">
<input type="hidden" id="contextPath" name="contextPath" value="${pageContext.request.contextPath }">
<div class="container-fluid" style="margin-top: 20px;">
    <div class="card" style="max-width: 1200px; margin: 0 auto;">
	    <div class="card-header bg-primary text-white" style="margin-bottom: 20px;">
	        <h1 class="card-title text-center mb-0 text-white" style="font-size: 2.0rem;">강의평가 목록</h1>
	    </div>


        <div class="card-body">
            <!-- 검색 영역 -->
            <div class="row mb-4">
                <div class="col-md-8">
                    <div class="input-group">
                        <select id="semBox" class="form-select" style="max-width: 150px;">
                        </select>
                    </div>
                </div>
            </div>

            <!-- 테이블 영역 -->
            <div class="table-responsive">
                <table class="table table-bordered table-primary" style="min-width: 800px;">
                
                    <!-- GPA 표시 영역 (테이블 상단 가운데) -->
                  <thead>
				    <tr>
				       <th colspan="8" class="text-center table-light" style="font-size: 18px; height: 60px; line-height: 60px; vertical-align: middle;">
				   		 <div id="semScoreDiv"></div>
					   </th>
				    </tr>
				</thead>


                    <!-- 기존 테이블 헤더 -->
                    <thead class="">
                        <tr class="text-center">
                            <th class=""style="width: 5%">년도</th>
                            <th class=""style="width: 5%">학기</th>
                            <th class=""style="width: 10%">종류</th>
                            <th class=""style="width: 10%">강의명</th>
                            <th class=""style="width: 5%">학점</th>
                            <th class=""style="width: 10%">강의평가</th>
                            <th class=""style="width: 10%">성적</th>
                            <th class=""style="width: 8%">이의신청</th>
                        </tr>
                    </thead>

                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>



<!-- 상세보기 모달 -->
<div class="modal fade" id="detailModal" tabindex="-1">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header bg-primary text-white">
			    <h5 class="modal-title w-100 text-center" style="font-size: 2.0rem; font-weight: 600;">강의 평가</h5>
			    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
			</div>	

            <div class="modal-body">
                <div class="mb-3">
                    <label class="form-label fw-bold" style="font-size: 1.5rem;">강의 정보</label>
                    <div class="p-2 border rounded">
                        <div class="row">
                        </div>
                    </div>
                </div>
                <div class="mb-3">
                    <label class="form-label fw-bold" style="font-size: 1.5rem;">평가 내용</label>
                    <div class="p-2 border rounded">
                        <textarea class="form-control" id="coevaBox" rows="5" placeholder="여기에 평가 내용을 입력하세요."></textarea>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="saveEvaluationBtn" onclick="saveEvaluation()">저장</button>
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
            </div>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath }/resources/js/attendcoeva/attendCoevaScript.js"></script>


