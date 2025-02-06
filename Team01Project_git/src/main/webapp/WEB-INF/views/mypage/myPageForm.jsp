<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>

<security:authentication property="principal.username" var="id" />

<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}">
<input type="hidden" id="id" value="${my.id}">
<script>
	const contextPath = "${pageContext.request.contextPath}";
</script>
<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item">마이페이지</li>
    <li class="breadcrumb-item active" aria-current="page">마이페이지</li>
  </ol>
</nav>

<script>
	const studentData = {
		id : "${my.id}",
		name : "${my.nm}",
		phone : "${my.mbtlnum}",
		email : "${my.eml}"
	};
</script>

<style>
/* 공통 스타일 */
.profile .profile-card img {
    max-width: 120px;
}

.profile .profile-card h2 {
    font-size: 24px;
    font-weight: 700;
    color: #2c384e;
    text-align: center;
}

.profile .profile-overview .label {
    font-weight: bold;
    color: #6c757d;
}

.profile .profile-overview .value {
    color: #212529;
}

/* 사이드바 열림/닫힘 상태 */
@media (min-width: 992px) {
    .profile .row {
        flex-direction: row;
    }
}

@media (max-width: 991px) {
    .profile .row {
        flex-direction: column;
    }

    /* 작은 화면에서 프로필 카드와 정보 카드가 상하로 배치 */
    .profile .profile-card {
        margin-bottom: 20px;
    }
}

/* 전체 학생증 스타일 */
.student-card {
    width: 100%;
    max-width: 300px;
    border: 2px solid #012970;
    border-radius: 8px;
    background-color: #ffffff;
    margin: 0 auto;
    text-align: center;
    position: relative;
    box-shadow: none; /* 전체 그림자 제거 또는 조정 */
}

/* 상단 파란색 바 */
.top-bar {
    width: 100%;
    height: 60px;
    background-color: #012970;
    border-radius: 8px 8px 0 0;
    position: relative;
}

.top-bar .slot {
    width: 50px;
    height: 6px;
    background-color: #ffffff;
    border-radius: 3px;
    position: absolute;
    top: 27px;
    left: 50%;
    transform: translateX(-50%);
}

/* 중앙 사진 및 정보 */
.card-body {
    padding: 20px;
}

.profile-photo {
    width: 100px;
    height: 100px;
    object-fit: cover;
    border-radius: 50%;
    margin: 10px auto;
}

.card-body h5 {
    font-size: 1.3rem;
    margin: 10px 0;
    font-weight: bold;
}

.card-body p {
    font-size: 0.9rem;
    margin: 5px 0;
    color: #333333;
}

/* 하단 바 스타일 */
.bottom-bar {
    width: 100%;
    height: 60px;
    background-color: #012970;
    border-radius: 0 0 8px 8px; /* 아래쪽 모서리를 둥글게 */
    position: relative;
}
.bottom-bar .slot {
    width: 50px;
    height: 6px;
    background-color: #ffffff;
    border-radius: 3px;
    position: absolute;
    bottom: 27px; /* 하단에서 27px 위치 */
    left: 50%;
    transform: translateX(-50%);
}


/* 하단 로고 */
.card-footer {
    background-color: #ffffff;
    padding: 5px 0;
    border-radius: 0 0 8px 8px; /* 둥근 모서리 */
    display: flex;
    align-items: center; /* 세로 중앙 정렬 */
    justify-content: center; /* 가로 중앙 정렬 */
    gap: 10px; /* 로고와 텍스트 사이의 간격 */
}

.university-logo {
    width: 50px;
    height: 50px;
    object-fit: contain; /* 이미지 비율 유지 */
}

.university-name {
    font-size: 1.2rem;
    color: black;
    font-weight: bold;
    text-align: left; /* 텍스트를 왼쪽 정렬 */
    line-height: 1.2; /* 줄 간격 */
    margin: 0; /* 불필요한 여백 제거 */
}


/*폰트스타일*/
.university-name {
	font-family: "Bitter", serif;
	font-optical-sizing: auto;
	font-weight: 500;
	font-style: normal;
	color: #000000;
}
</style>


<!-- 학생증 신청 모달 -->
<div class="modal fade" id="studentCardModal" tabindex="-1" aria-labelledby="studentCardModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="studentCardModalLabel">학생증 신청</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body text-center">
                <!-- 학생증 카드 -->
                <div class="student-card">
                    <!-- 상단 파란색 바 -->
                    <div class="top-bar"></div>
                    <!-- 중앙 사진 및 정보 -->
                    <div class="card-body">
                    
                    <c:choose>
			                <c:when test="${not empty my.proflPhoto}">
			                    <img id="profilePreview" src="data:image/jpeg;base64,${my.proflPhoto}" 
			                         alt="Profile Photo" style="height: 200px;">
			                </c:when>
			                <c:otherwise>
			                    <img id="profilePreview" src="${pageContext.request.contextPath }/resources/NiceAdmin/assets/img/user.png" 
			                         alt="Default Profile Photo" style="width: 150px; height: 150px; object-fit: cover;">
			                </c:otherwise>
			            </c:choose>
                    
                    
                    
                    
                        
                        <h5>${my.nm}</h5>
                        <p style="font-size: 20px;">${my.id}</p>
                        <p style="font-size: 15px;">${my.departmentVO.deptNm }</p>
                    </div>
                    <!-- 하단 로고 -->
                    <div class="card-footer d-flex align-items-center justify-content-center">
                        <img src="${pageContext.request.contextPath }/resources/NiceAdmin/assets/img/yglogo.png" alt="University Logo" class="university-logo">
                         <p class="university-name ms-3">YOUNGUN<br>UNIVERSITY</p>
                    </div>
                     <div class="bottom-bar">
			    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
                <button type="button" class="btn btn-primary" id="confirmApplication">신청</button>
            </div>
        </div>
    </div>
</div>


<!-- 비밀번호 인증 받기  -->
<!-- Modal -->
<div
  class="modal fade"
  id="passwordModal"
  tabindex="-1"
  aria-labelledby="passwordModalLabel"
  aria-hidden="true"
  data-bs-backdrop="static"
>
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="passwordModalLabel">비밀번호 확인</h5>
        <button
          type="button"
          class="btn-close"
          data-bs-dismiss="modal"
          aria-label="Close"
        ></button>
      </div>
      <div class="modal-body">
        <form id="authForm">
          <label for="pswd">비밀번호:</label>
          <input type="password"  name="pswd" id="pswd" class="form-control"  required   />
          <div align="right">
          <button
            type="submit"
            id="authSubmit"
            class="btn btn-primary mt-3"
          >
            확인
          </button>
          </div>
        </form>
        <div id="authMessage" class="text-danger mt-3"></div>
          
    
      </div>
    </div>
  </div>
</div>




<div class="container profile">
    <!-- 사이드바 상태에 따라 레이아웃 변경 -->
    <div class="row">
        <!-- 프로필 카드 -->
        <div class="col-lg-4 col-md-12 mb-4">
            <div class="card">
                <div class="card-body profile-card pt-4 d-flex flex-column align-items-center">
                    <c:choose>
                        <c:when test="${not empty my.proflPhoto}">
                            <img src="data:image/jpeg;base64,${fn:escapeXml(my.proflPhoto)}"
                                 alt="Profile Photo"
                                 class="rounded-circle"
                                 style="width: 120px; height: 120px; object-fit: cover;" />
                        </c:when>
                        <c:otherwise>
                            <img src="${pageContext.request.contextPath}/resources/NiceAdmin/assets/img/user.png"
                                 alt="Default Profile Photo"
                                 class="rounded-circle"
                                 style="width: 120px; height: 120px; object-fit: cover;" />
                        </c:otherwise>
                    </c:choose>
                    <h2>${my.nm}</h2>
                    <h3>${my.id}</h3>
                </div>
            </div>
        </div>

        <!-- 정보 카드 -->
        <div class="col-lg-8 col-md-12">
            <div class="card">
                <div class="card-body pt-3">
                    <!-- 탭 -->
                    <ul class="nav nav-tabs nav-tabs-bordered">
                        <li class="nav-item">
                            <button class="nav-link active" data-bs-toggle="tab" data-bs-target="#profile-overview">
                                기본 정보
                            </button>
                        </li>
                        <li class="nav-item">
                            <button class="nav-link" data-bs-toggle="tab" data-bs-target="#profile-additional">
                                전체 성적
                            </button>
                        </li>
                    </ul>
                    <br>
                    <div class="tab-content pt-2">
                        <!-- 기본 정보 -->
                        <div class="tab-pane fade show active" id="profile-overview">
                            <div class="row">
                                <div class="col-4 label">이름</div>
                                <div class="col-8 value">${my.nm}</div>
                            </div>
                            <br>
                            <div class="row">
                                <div class="col-4 label">학번/사번</div>
                                <div class="col-8 value">${my.id}</div>
                            </div>
                            <br>
                            <div class="row">
							    <div class="col-4 label">학과</div>
							    <security:authorize access="hasRole('STUDENT')">
							        <div class="col-8 value">${my.departmentVO.deptNm}</div>
							    </security:authorize>
							    <security:authorize access="!hasRole('STUDENT')">
							        <div class="col-8 value">-</div> <!-- 학생이 아닐 경우 대체 값 표시 -->
							    </security:authorize>
							</div>

                            <br>
                            <div class="row">
                                <div class="col-4 label">생년월일</div>
                                <div class="col-8 value">${my.brdt}</div>
                               </div>
							<br>
							<div class="row">
								<div class="col-4 label">주소</div>
								<div class="col-8 value">(${my.zip}) &nbsp ${my.rdnmadr } &nbsp  ${my.daddr }</div>
							</div>
							<br>
							<div class="row">
								<div class="col-4 label">전화번호</div>
								<div class="col-8 value">${my.mbtlnum}</div>
							</div>
							<br>
							<div class="row">
								<div class="col-4 label">이메일</div>
								<div class="col-8 value">${my.eml}</div>
							</div>
							<br>
							<div class="row">
								<div class="col-4 label">수신 여부</div>
								<div  class="col-8 value">
									<!-- 이메일 수신 여부 -->
									<div class="form-check d-inline-block me-4">
										<input type="checkbox" class="form-check-input"
											id="emailSubscription" name="emlRcptnAgreYn" value="Y"
											${my.emlRcptnAgreYn == 'Y' ? 'checked' : ''} disabled>
										<label class="form-check-label" for="emailSubscription">이메일
											수신</label>
									</div>

									<div class="form-check d-inline-block">
										<input type="checkbox" class="form-check-input"
											id="smsRcptnAgreYn" name="smsRcptnAgreYn" value="Y"
											${my.smsRcptnAgreYn == 'Y' ? 'checked' : ''} disabled>
										<label class="form-check-label" for="smsRcptnAgreYn">SMS
											수신</label>
									</div>

								</div>
							</div>
							<div class="row mt-3">
								<div class="col-lg-12 d-flex justify-content-end gap-2">

								

									<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#passwordModal" >
									수정
									</button>

									<security:authorize access="hasRole('STUDENT')">
										<button type="button" class="btn btn-outline-primary"
											data-bs-toggle="modal" data-bs-target="#studentCardModal">학생증
											신청</button>
									</security:authorize>
								</div>
							</div>
						</div>
						<!-- 성적 조회 -->
						<div class="tab-pane fade" id="profile-additional">
						
							<!-- 추가 정보 내용 -->
							<div>
								<canvas id="myChart"></canvas>
							</div>
						</div>
						<!-- 기타 정보 사용시 활성화 -->
						<!--           <div class="tab-pane fade" id="profile-other"> -->
						<!--             <h5 class="card-title">Other Information</h5> -->
						<!--             기타 정보 내용 -->
						<!--           </div> -->
						<!--           </div> -->

						<!-- End 탭 콘텐츠 -->
					</div>
				</div>
			</div>
		</div>
	</div>
                               

	<!--  학생증 카드 -->
	<script
		src="${pageContext.request.contextPath }/resources/js/mypage/myPageStudentCardForm.js"></script>
	
	<!-- 차트 -->
	<script
		src="${pageContext.request.contextPath }/resources/js/mypage/myPageScoreScript.js"></script>

	<%-- 
	밑에꺼랑 동일
	<script
		src="${pageContext.request.contextPath }/resources/js/mypage/myPageEdit.js"></script>  --%>
	<!-- 인증 할 때에  enter -->
	<script
		src="${pageContext.request.contextPath }/resources/js/login/passWordEvent.js"></script> 