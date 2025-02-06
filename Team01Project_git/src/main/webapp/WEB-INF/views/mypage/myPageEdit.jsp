           <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>

<security:authentication property="principal.username" var="id"/>
<input type="hidden" id="id" name="id" value="${my.id }">
<script>
	const contextPath = "${pageContext.request.contextPath}";
</script>


<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}">
<input type="hidden" id="contextPath" value="/yguniv">
<input type="hidden" name="streCateCd" value ="${person.streCateCd }">
<input type="hidden" name="crtfcMnCd" value="${my.crtfcMnCd}" />
<input type="hidden" name="lastConectDe" value="${my.lastConectDe}" />


<style>

.profile .profile-card img {
  max-width: 120px;
}

.profile .profile-card h2 {
  font-size: 24px;
  font-weight: 700;
  color: #2c384e;
  margin: 10px 0 0 0;
}

.profile .profile-card h3 {
  font-size: 18px;
}

.profile .profile-card .social-links a {
  font-size: 20px;
  display: inline-block;
  color: rgba(1, 41, 112, 0.5);
  line-height: 0;
  margin-right: 10px;
  transition: 0.3s;
}

.profile .profile-card .social-links a:hover {
  color: #012970;
}

.profile .profile-overview .row {
  margin-bottom: 20px;
  font-size: 15px;
}

.profile .profile-overview .card-title {
  color: #012970;
}

.profile .profile-overview .label {
  font-weight: 600;
  color: rgba(1, 41, 112, 0.6);
}

.profile .profile-edit label {
  font-weight: 600;
  color: rgba(1, 41, 112, 0.6);
}

.profile .profile-edit img {
  max-width: 120px;
}

</style>
       
           
<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item">마이페이지</li>
    <li class="breadcrumb-item active" aria-current="page">개인정보수정</li>
  </ol>
</nav>

  <input type="hidden" name="crtfcMnCd" value="${my.crtfcMnCd}" />
<input type="hidden" name="lastConectDe" value="${my.lastConectDe}" />
  
        
          <form action="${pageContext.request.contextPath}/mypage/${my.id}/UpdateMyPage" method="post" enctype="multipart/form-data" onsubmit="return validatePasswords()">
            <!-- 프로필 사진 -->
			 <!-- 프로필 사진 -->
			    <div class="row mb-3">
			        <label for="proflPhoto" class="col-md-4 col-lg-3 col-form-label">프로필 사진</label>
			        <div class="col-md-8 col-lg-9">
			            <c:choose>
			                <c:when test="${not empty my.proflPhoto}">
			                    <img id="profilePreview" src="data:image/jpeg;base64,${my.proflPhoto}" 
			                         alt="Profile Photo" style="width: 150px; height: 150px; object-fit: cover;">
			                </c:when>
			                <c:otherwise>
			                    <img id="profilePreview" src="${pageContext.request.contextPath }/resources/NiceAdmin/assets/img/user.png" 
			                         alt="Default Profile Photo" style="width: 150px; height: 150px; object-fit: cover;">
			                </c:otherwise>
			            </c:choose>
			            <div class="pt-2">
			                <input type="file" id="profileImage" name="proflImage" accept="image/*" style="display: none;">
			                <button type="button" class="btn btn-primary btn-sm" onclick="document.getElementById('profileImage').click()">
			                    <i class="bi bi-upload"></i> 업로드
			                </button>
			                <button type="button" class="btn btn-danger btn-sm">
			                    <i class="bi bi-trash"></i> 삭제
			                </button>
			            </div>
			        </div>
			    </div>



            <div class="row mb-3">
              <label for="nm" class="col-md-4 col-lg-3 col-form-label">이름</label>
              <div class="col-md-8 col-lg-9">
                <input name="nm" type="text" class="form-control" id="nm" value="${my.nm }" readonly>
              </div>
            </div>
            
            <div class="row mb-3">
              <label for="id" class="col-md-4 col-lg-3 col-form-label">학번</label>
              <div class="col-md-8 col-lg-9">
                <input name="id" type="text" class="form-control" id="id" value="${my.id }" readonly>
              </div>
            </div>

             <!-- 비밀번호 -->
		    <div class="row mb-3">
		        <label for="pswd" class="col-md-4 col-lg-3 col-form-label">비밀번호</label>
		        <div class="col-md-8 col-lg-9">
		            <input name="pswd" type="password" class="form-control" id="pswd" required>
		        </div>
		    </div>
		    <!-- 비밀번호 확인 -->
		    <div class="row mb-3">
		        <label for="confirmPswd" class="col-md-4 col-lg-3 col-form-label">비밀번호 확인</label>
		        <div class="col-md-8 col-lg-9">
		            <input name="confirmPswd" type="password" class="form-control" id="confirmPswd" required>
		        </div>
		        <c:if test="${not empty successMessage}">
				    <div class="alert alert-success">${successMessage}</div>
				</c:if>
				<c:if test="${not empty errorMessage}">
				    <div class="alert alert-danger">${errorMessage}</div>
				</c:if>
		    </div>
            <!-- 전화번호 -->
            <div class="row mb-3">
              <label for="mbtlnum" class="col-md-4 col-lg-3 col-form-label">전화번호</label>
              <div class="col-md-8 col-lg-9">
                <input name="mbtlnum" type="text" class="form-control" id="mbtlnum" value="${my.mbtlnum }">
              </div>
            </div>
            <!-- 생년월일 -->
            <div class="row mb-3">
              <label for="brdt" class="col-md-4 col-lg-3 col-form-label">생년월일</label>
              <div class="col-md-8 col-lg-9">
                <input name="brdt" type="DATE" class="form-control" id="brdt" value="${my.brdt }">
              </div>
            </div>
            <!-- 이메일 -->
            <div class="row mb-3">
              <label for="eml" class="col-md-4 col-lg-3 col-form-label">이메일</label>
              <div class="col-md-8 col-lg-9">
                <input name="eml" type="text" class="form-control" id="eml" value="${my.eml }">
              </div>
            </div>

           <!-- 우편번호 -->
				<div class="row mb-3">
				  <label for="zip" class="col-md-4 col-lg-3 col-form-label">우편번호</label>
				  <div class="col-md-8 col-lg-9 d-flex align-items-center">
				    <input name="zip" type="text" class="form-control me-2" id="zip" style="width: 150px;" value="${my.zip }">
				  
				  </div>
				</div>



            <!-- 주소 -->
            <div class="row mb-3">
              <label for="rdnmadr" class="col-md-4 col-lg-3 col-form-label">주소</label>
              <div class="col-md-8 col-lg-9">
                <input name="rdnmadr" type="text" class="form-control" id="rdnmadr"  value="${my.rdnmadr }">
              </div>
            </div>

            <!-- 상세주소 -->
            <div class="row mb-3">
              <label for="daddr" class="col-md-4 col-lg-3 col-form-label">상세주소</label>
              <div class="col-md-8 col-lg-9">
                <input name="daddr" type="text" class="form-control" id="daddr"  value="${my.daddr }">
              </div>
            </div>

            <!-- 수신 여부 -->
            <div class="row mb-3">
			    <label class="col-md-4 col-lg-3 col-form-label">수신 여부</label>
			    <div class="col-md-8 col-lg-9 d-flex align-items-center">
			        <!-- 이메일 수신 -->
			        <div class="form-check form-check-inline">
			            <input class="form-check-input" type="checkbox" id="emlRcptnAgreYn" name="emlRcptnAgreYn" value="Y" 
			                   ${my.emlRcptnAgreYn == 'Y' ? 'checked' : ''}>
			            <label class="form-check-label" for="emlRcptnAgreYn">이메일 수신</label>
			        </div>
			        <!-- SMS 수신 -->
			        <div class="form-check form-check-inline">
			            <input class="form-check-input" type="checkbox" id="smsRcptnAgreYn" name="smsRcptnAgreYn" value="Y" 
			                   ${my.smsRcptnAgreYn == 'Y' ? 'checked' : ''}>
			            <label class="form-check-label" for="smsRcptnAgreYn">SMS 수신</label>
			        </div>
			    </div>
			</div>


          
			  <div class="text-center">
			  <button type="button" class="btn btn-secondary" onclick="history.back()">취소</button>
				<button type="submit" class="btn btn-primary">저장</button>

			   

			  
			  </div>
		
          
<script src="${pageContext.request.contextPath }/resources/js/mypage/mypageEditForm.js"></script>
		
              </form>
<!-- 신입생 확인 -->
<c:if test="${not empty param.fresh}">
    <script>
        document.addEventListener("DOMContentLoaded", function(){
            swal({
                title: "기본 학적정보 등록",
                text: "신입생은 최초 학적정보를 등록해야 합니다.",
                icon: "info",
                button: "확인"
            });
        });
    </script>

</c:if>



  



  
 