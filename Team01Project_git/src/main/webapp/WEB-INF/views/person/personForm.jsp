<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!-- 아이디 중복확인 -->
<input id="cp" type="hidden" value="${pageContext.request.contextPath }">
    
<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item">회원 관리</li>
    <li class="breadcrumb-item active" aria-current="page">단일 사용자 추가</li>
  </ol>
</nav>

<br>
<br>
<form:form modelAttribute="person" id="personForm">
    <table class="table table-borded">
        <!-- 공통 정보 입력 필드 -->
      <tr>
    <th class="table-primary text-center align-middle">ID</th>
    <td>
        <input 
        
            type="text" 
            name="id" 
            id="idInput" 
            class="form-control" 
            required 
            value="${person.id}" 
            placeholder="아이디를 입력하세요" 
        />
        <small id="idFeedback" class="text-danger" style="display: none;"></small>
        <span class="text-danger">${errors.id}</span>
    </td>
</tr>

        <tr>
            <th class="table-primary text-center align-middle">이름</th>
            <td><input type="text" name="nm" class="form-control" required value="${person.nm}" /><span class="text-danger">${errors.nm}</span></td>
        </tr>
        <tr>
            <th class="table-primary text-center align-middle">생년월일</th>
            <td><input type="date" name="brdt" class="form-control" required value="${person.brdt}" /><span class="text-danger">${errors.brdt}</span></td>
        </tr>
       <tr>
    <th class="table-primary text-center align-middle">성별</th>
    <td>
        <label>
            <input type="radio" name="sexdstnCd" value="M" 
                <c:if test="${person.sexdstnCd == 'M'}">checked</c:if> /> 남성
        </label>
        <label>
            <input type="radio" name="sexdstnCd" value="F" 
                <c:if test="${person.sexdstnCd == 'F'}">checked</c:if> /> 여성
        </label>
        <span class="text-danger">${errors.sexdstnCd}</span>
    </td>
</tr>


    </table>

    <!-- 사용자 유형 선택 -->
    <select class="form-select" id="user-type" onchange="usertype(event)">
        <option value="">사용자 유형을 선택하세요.</option>
        <option value="professor">교수</option>
        <option value="student">학생</option>
        <option value="employee">교직원</option>
    </select>


    <!-- 교수 정보 입력 필드 -->
    <div id="professor-table" style="display:none;">
        <table class="table">
            <tr>
                <th> 학과
                    <select class="form-select" id="department-type" name="deptNo" >
                        <option value="">학과를 선택하세요</option>
                        <option value="D001">컴퓨터공학과</option>
                        <option value="D002">기계공학과</option>
                        <option value="D003">전자공학과</option>
                    </select>
                </th>
            </tr>
            <tr>
                <th> 구분
                    <select class="form-select" id="professor-type" name="profeType">
                        <option value="">구분을 선택하세요</option>
                        <option value="정교수">정교수</option>
                        <option value="부교수">부교수</option>
                        <option value="기간제">기간제</option>
                    </select>
                </th>
            </tr>
        </table>
    </div>

 <!-- 교직원 입력 폼 -->
    <div id="employee-table" style="display:none;">
        <table class="table">
            <tr>
                <th> 부서
	                <select class="form-select"  id="emp-dept" name="empDept">
	                	<option value="">부서를 선택하세요</option>
	                	<option value="인사과">인사과</option>
	                	<option value="총무과">총무과</option>
	                	<option value="교무과">교무과</option>
	                </select>
                </th>
            </tr>
            
              <tr>
                <th> 직급
	                <select class="form-select"  id="emp-jbgd" name="empJbgd"> 
	                	<option value="">직급을 선택하세요</option>
	                	<option value="행정직">행정직</option>
	                	<option value="주임">주임</option>
	                	<option value="조교">조교</option>
	                </select>
                </th>
            </tr>
        </table>
    </div>



 	<!-- 학생 입력 폼 -->
<div id="student-table" style="display:none;">
    <table class="table">
        <tr>
            <th>학년
                <select class="form-select" id="grade-cd" name="gradeCd">
                    <option value="">학년을 선택하세요</option>
                    <option value="1001">1학년</option>
                    <option value="1002">2학년</option>
                </select>
            </th>
        </tr>
        <tr>
            <th>학적상태
                <select class="form-select" id="stre-cate-cd" name="streCateCd"> 
                    <option value="">학적상태를 선택하세요</option>
                    <option value="SC06">신입생</option>
                    <option value="SC01">재학</option>
                </select>
            </th>
        </tr>
        <tr>
            <th>학과
                <select class="form-select" id="department-type" name="deptCd">
                    <option value="">학과를 선택하세요</option>
                    <option value="D001">컴퓨터공학과</option>
                    <option value="D002">기계공학과</option>
                    <option value="D003">전자공학과</option>
                </select>
            </th>
        </tr>
    </table>
</div>
<br>

   <div style="text-align: right;">
    <button class="btn btn-primary" type="submit">저장</button>
</div>

</form:form>




<script>
function usertype(e) {
    const selectedUserType = e.target.value;  

    //교수 교직원꺼 일단 숨겨
    document.getElementById("professor-table").style.display = "none";
    document.getElementById("employee-table").style.display = "none";
    document.getElementById("student-table").style.display = "none";

    
    if (selectedUserType === "professor") {
        document.getElementById("professor-table").style.display = "block";
    } else if (selectedUserType === "employee") {
        document.getElementById("employee-table").style.display = "block";
    }else if (selectedUserType === "student"){
    	document.getElementById("student-table").style.display = "block";
    }
}
</script>
<script src="${pageContext.request.contextPath}/resources/js/person/personForm.js"></script>
