<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item active" aria-current="page">강의 평가 조회</li>
  </ol>
</nav>
<input type="hidden" id="profeId" name="profeId" value="${id}">
<input type="hidden" id="contextPath" name="contextPath" value="${pageContext.request.contextPath}">

<div class="container-fluid mt-4">
	<div class="row">
		<!-- 왼쪽 강의 리스트 영역 -->
		<div class="col-6">
			<div class="card"
				style="border: 1px solid rgba(0, 0, 0, .125); transition: all 0.3s ease; border-radius: 0.25rem; box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);">
				<div class="card-header">
					<h5 class="mb-0">강의 리스트</h5>
					
					<select  id="semBox" class="form-select" style="max-width: 150px; float: right;" >
                        </select>
					
				</div>
				<div class="card-body">
					<table class="table table-hover" style=" border-collapse: collapse;" >
						<thead>
							<tr>
								<th class="text-center" style=" border-right: 1px solid #dee2e6;">년도</th>
								<th class="text-center" style=" border-right: 1px solid #dee2e6;">학기</th>
								<th class="text-center" style=" border-right: 1px solid #dee2e6;">강의명</th>
							</tr>
						</thead>
						<tbody id="tbody">
						</tbody>
					</table>
				</div>
			</div>
		</div>
<!-- style="border: 1px solid rgba(0, 0, 0, .125);" -->
		<!-- 오른쪽 강의평가 영역 -->
<div class="col-6">
    <div class="card shadow-sm" style="border: 1px solid rgba(0, 0, 0, .125); border-radius: 0.25rem; box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);">
        <div class="card-header bg-white" style="border-bottom: 1px solid #dee2e6; padding: 15px 20px;">
            <h5 class="mb-0">강의 평가 목록</h5>
        </div>
        <div class="card-body" style="padding: 15px;">
            <div class="evaluation-container" style="max-height: 700px; overflow-y: auto; border: 1px solid #dee2e6; padding: 10px;" id="evalBox">
            </div>
        </div>
    </div>
</div>

	</div>
</div>

<script src="${pageContext.request.contextPath }/resources/js/attendcoeva/attendCoevaDetailScript.js"></script>

