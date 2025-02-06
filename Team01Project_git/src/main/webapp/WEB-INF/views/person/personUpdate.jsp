<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


	<table class="table">
		<tr>
			<th>학번</th>
			<td>${person.id}</td>
		</tr>
		
		<tr>
			<th>MEM_IMG</th>
			<td><input type="file" name="memImage" accept="image/*"
				class="form-control" /> <form:errors path="memImg"
					cssClass="text-danger" /></td>
		</tr>
		<tr>
			<th>아이디</th>
			<td><form:input path="memId" cssClass="form-control"
					required="true" /> <form:errors path="memId"
					cssClass="text-danger" /></td>
		</tr>
		<tr>
			<th>비밀번호</th>
			<td><input type="password" name="memPass" required
				class="form-control" /> <form:errors path="memPass"
					cssClass="text-danger" /></td>
		</tr>
		<tr>
			<th>이름</th>
			<td><form:input path="memName" cssClass="form-control"
					required="true" /> <form:errors path="memName"
					cssClass="text-danger" /></td>
		</tr>
		<tr>
			<th>주민번호1</th>
			<td><form:input path="memRegno1" cssClass="form-control" /> <form:errors
					path="memRegno1" cssClass="text-danger" /></td>
		</tr>
		<tr>
			<th>주민번호2</th>
			<td><form:input path="memRegno2" cssClass="form-control" /> <form:errors
					path="memRegno2" cssClass="text-danger" /></td>
		</tr>
		<tr>
			<th>생일</th>
			<td><form:input type="date" path="memBir"
					cssClass="form-control" /> <form:errors path="memBir"
					cssClass="text-danger" /></td>
		</tr>
		<tr>
			<th>우편번호</th>
			<td><form:input path="memZip" cssClass="form-control"
					required="true" /> <form:errors path="memZip"
					cssClass="text-danger" /></td>
		</tr>
		<tr>
			<th>주소1</th>
			<td><form:input path="memAdd1" cssClass="form-control"
					required="true" /> <form:errors path="memAdd1"
					cssClass="text-danger" /></td>
		</tr>
		
		
		
		
		
		<tr>
			<th>주소2</th>
			<td><form:input path="memAdd2" cssClass="form-control"
					required="true" /> <form:errors path="memAdd2"
					cssClass="text-danger" /></td>
		</tr>
		<tr>
			<th>집전번</th>
			<td><form:input path="memHometel" cssClass="form-control" /> <form:errors
					path="memHometel" cssClass="text-danger" /></td>
		</tr>
		<tr>
			<th>회사전번</th>
			<td><form:input path="memComtel" cssClass="form-control" /> <form:errors
					path="memComtel" cssClass="text-danger" /></td>
		</tr>
		<tr>
			<th>휴대폰</th>
			<td><form:input path="memHp" cssClass="form-control" /> <form:errors
					path="memHp" cssClass="text-danger" /></td>
		</tr>
		<tr>
			<th>이메일</th>
			<td><form:input type="email" path="memMail"
					cssClass="form-control" required="true" /> <form:errors
					path="memMail" cssClass="text-danger" /></td>
		</tr>
		<tr>
			<th>직업</th>
			<td><form:input path="memJob" cssClass="form-control" /> <form:errors
					path="memJob" cssClass="text-danger" /></td>
		</tr>
		<tr>
			<th>취미</th>
			<td><form:input path="memLike" cssClass="form-control" /> <form:errors
					path="memLike" cssClass="text-danger" /></td>
		</tr>
		<tr>
			<th>기념일</th>
			<td><form:input path="memMemorial" cssClass="form-control" /> <form:errors
					path="memMemorial" cssClass="text-danger" /></td>
		</tr>
		<tr>
			<th>기념일자</th>
			<td><form:input type="date" path="memMemorialday"
					cssClass="form-control" /> <form:errors path="memMemorialday"
					cssClass="text-danger" /></td>
		</tr>
		<tr>
			<th>마일리지</th>
			<td>${member.memMileage }</td>
		</tr>
		<tr>
			<td colspan="2">
				<button type="submit" class="btn btn-primary">전송</button>
				<button type="reset" class="btn btn-danger">취소</button>
			</td>
		</tr>
	</table>









