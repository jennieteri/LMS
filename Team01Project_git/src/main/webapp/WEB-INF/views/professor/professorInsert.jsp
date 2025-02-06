<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<table>
		<tr>
			<th>PROFE_ID</th>
			<td><input type="text" name="profeId" class="form-control"
				required value="${professor.profeId}" /><span class="text-danger">${errors.profeId}</span></td>
		</tr>
		<tr>
			<th>DEPT_NO</th>
			<td><input type="text" name="deptNo" class="form-control"
				required value="${professor.deptNo}" /><span class="text-danger">${errors.deptNo}</span></td>
		</tr>
		<tr>
			<th>정교수, 기간제</th>
			<td><input type="text" name="profeType" class="form-control"
				required value="${professor.profeType}" /><span class="text-danger">${errors.profeType}</span></td>
		</tr>
	</table>
</body>
</html>