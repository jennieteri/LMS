<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item">수강신청</li>
    <li class="breadcrumb-item active" aria-current="page">예비수강신청</li>
  </ol>
</nav>

<input type="hidden" id="stuId" name="stuId" value="${stuId }">
<input type="hidden" id="cp" value="${pageContext.request.contextPath }">


<div class="d-flex">
    <!-- 왼쪽 영역 (60%) -->
    <div id="parentDiv" class="flex-grow-1 me-2 col-8">
        <div class="d-flex justify-content-between align-items-center mb-3">
			<select id="pageLength" class="form-select" style="width: 80px;">
			    <option value="10">10</option>
			    <option value="25">25</option>
			    <option value="50">50</option>
			    <option value="100">100</option>
			</select>
            <div id="searchContainer">
                <select id="searchColumn" class="form-select d-inline-block w-auto me-2">
                    <option value="lectNm">강의명</option>
                    <option value="professorVO.nm">교수명</option>
                </select>
                <input type="search" id="searchInput" onkeypress="if(event.keyCode==13)fnEnterKeyEvent();" class="form-control d-inline-block w-auto me-2" placeholder="검색어 입력...">
                <button id="searchButton" class="btn btn-primary me-2">검색</button>
                <button id="resetButton" class="btn btn-secondary">전체 보기</button>
            </div>
        </div>
        <table id="parentTable" class="table table-bordered table-primary table-hover">
            <thead>
                <tr>
                    <th class="text-center align-middle">강의명</th>
                    <th class="text-center align-middle">교수명</th>
                    <th class="text-center align-middle">학과</th>
                    <th class="text-center align-middle">학년</th>
                    <th class="text-center align-middle">학점</th>
                    <th class="text-center align-middle">정원</th>
                    <th class="text-center align-middle">시간표</th>
                    <th class="text-center align-middle"></th>
                </tr>
            </thead>
        </table>
    </div>

    <!-- 오른쪽 영역 (40%) -->
    <div class="d-flex flex-column p-2 pt-0 col-4">
    	<div class="card mb-2">
		  <div class="card-body p-2">
	        <div class="cj-schedule mb-2"></div>
	        <h6 class="m-0">수강학점 <span class="badge rounded-pill text-bg-secondary"><span id="total-score">0</span> / 18</span></h6>
		  </div>
		</div>
    	<div class="card">
		  <div class="card-header p-2">
		  	장바구니 
		  </div>
		  <div class="card-body p-2">
		  	<h6 id="noneText" class="text-center my-3">장바구니에 등록된 강의가 없습니다</h6>
        	<div id="cartDiv"></div>
		  </div>
		</div>
    </div>
</div>

<script  src="${pageContext.request.contextPath }/resources/js/lecturecart/lectureCartScript.js"></script>