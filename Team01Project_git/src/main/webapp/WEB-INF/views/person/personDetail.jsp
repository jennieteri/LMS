<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>사용자 상세 정보</title>
  
</head>
<body>

            <table class="table detail-table">
                <tbody>
                    <tr>
                        <th>아이디:</th>
                        <td>${person.id}</td>
                    </tr>
                    <tr>
                        <th>이름:</th>
                        <td>${person.nm}</td>
                    </tr>
                    <tr>
                        <th>생년월일:</th>
                        <td>${person.brdt}</td>
                    </tr>
                    <tr>
                        <th>성별:</th>
                        <td>${person.sexdstnCd}</td>
                    </tr>
                    <tr>
                        <th>우편번호:</th>
                        <td>${person.zip}</td>
                    </tr>
                    <tr>
                        <th>도로명주소:</th>
                        <td>${person.rdnmadr}</td>
                    </tr>
                    <tr>
                        <th>상세주소:</th>
                        <td>${person.daddr}</td>
                    </tr>
                    <tr>
                        <th>핸드폰번호:</th>
                        <td>${person.mbtlnum}</td>
                    </tr>
                    <tr>
                        <th>이메일:</th>
                        <td>${person.eml}</td>
                    </tr>
                    <tr>
                        <th>이메일 수신 동의 여부:</th>
                        <td>${person.emlRcptnAgreYn}</td>
                    </tr>
                    <tr>
                        <th>SMS 수신 동의 여부:</th>
                        <td>${person.smsRcptnAgreYn}</td>
                    </tr>
                    <tr>
                        <th>인증수단코드:</th>
                        <td>${person.crtfcMnCd}</td>
                    </tr>
                    <tr>
                        <th>마지막 접속 일시:</th>
                        <td>${person.lastConectDe}</td>
                    </tr>
                    <tr>
                        <th>비밀번호 실패 횟수:</th>
                        <td>${person.pswdFailrCo}</td>
                    </tr>
                    <tr>
                        <th>프로필 사진:</th>
                        <td><img src="${person.proflPhoto}" alt="프로필 사진" class="profile-img"></td>
                    </tr>
                </tbody>
            </table>


<!-- 수정하기 Modal -->
<div class="modal fade" id="personUpdateModal" tabindex="-1" aria-labelledby="updateModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      <div class="modal-header">
        <h1 class="modal-title fs-5" id="updateModalLabel">사용자 수정</h1>
      </div>
      <div class="modal-body" id="personUpdateContent">
        <table class="table detail-table">
          <tbody>
         	 <tr>
		      <th>사용자 유형</th>
		      <td id="update-personType" colspan="3"></td>
    		</tr>
            <tr>
              <th>아이디</th>
              <td id="update-id" ></td>
            </tr>
            <tr>
              <th>이름</th>
              <td id="update-nm"></td>
              <th>생년월일</th>
              <td id="update-brdt"></td>
            </tr>
            <tr>
              <th>성별</th>
              <td id="update-sexdstnCd"></td>
              <th>핸드폰번호</th>
              <td><input class="form-control" type="text" id="update-mbtlnum" name="mbtlnum"></td>
            </tr>
            <tr>
              <th>이메일</th>
              <td><input class="form-control" type="email" id="update-eml" name="eml"></td>
              <th>비밀번호 실패 횟수</th>
              <td id="update-pswdFailrCo"></td>
            </tr>
          </tbody>
        </table>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
        <button type="button" class="btn btn-warning" data-bs-dismiss="modal" id="resetpwBtn">비밀번호 초기화</button>
        <button type="button" class="btn btn-primary" id="saveUpdateBtn">저장</button>
      </div>
    </div>
  </div>
</div>




</body>
</html>
