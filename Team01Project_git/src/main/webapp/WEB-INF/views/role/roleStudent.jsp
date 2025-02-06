<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<script>
    const contextPath = "${pageContext.request.contextPath}";
</script>

<head>

    <title>역할 선택</title>
    </head>
<body>
    <form action="${pageContext.request.contextPath }/role/allRole" method="post">


    <label for ="roleSelect">역할:</label>
    	<select id="roleSelect" class="form-select" onchange="onRoleChange()">
    		<option value="" disabled="disabled"> 역할 선택</option>
    		<option value="학생" > 학생</option>
    		<option value="교수" > 교수</option>
    		<option value="교직원" > 교직원</option>    	
    	</select>
    	<br><br>
    	
    	<!--학생  -->
    	<div id="studentSection" style="display: none;">
    	
    		<label for ="studentGradeSelect" >학년: </label>
	    	<select id="studentGradeSelect" name ="studentGrade"  class="form-select" onchange="onStudentGradeChange()" >
		    	<option value="" disabled selected> 학년선택 </option>
		    	<c:forEach items ="${gradeList}" var="grade">
		    		<option value="${grade.cocoStts}">${grade.cocoStts}</option>
	    		</c:forEach>
	    	</select>
	    	</div>
	    	<br><br>
	    	<!-- 교수 -->
        <div id="professorSection"  style="display:none;">
            <label for="professorDeptSelect">학과:</label>
            <select id="professorDeptSelect" name="professorDept"  class="form-select" >
                <option value="" disabled selected>학과 선택</option>
                <c:forEach var="departmentList" items="${departmentList}">
                    <option value="${departmentList.deptNm}">${departmentList.deptNm}</option>
                </c:forEach>
            </select>
        </div>

       <!-- 교직원  -->
        <div id="staffSection" style="display:none;">
            <label for="staffDeptSelect">부서:</label>
            <select id="staffDeptSelect" name="staffDept"  class="form-select">
                <option value="" disabled selected>부서 선택</option>
                <c:forEach var="onlyDept" items="${onlyDept}">
                    <option value="${onlyDept}">${onlyDept}</option>
                </c:forEach>
            </select>
        </div>
      
        <!-- 학생 학과 선태 ㄱ -->
        
        <div id="studentDeptSection" style="display:none;">
		    <label for="studentDeptSelect">학생 학과 선택</label>
		    <select id="studentDeptSelect" name="studentDept" class="form-select">
		        <option value="" disabled selected>학과 선택</option>
		        <c:forEach var="departmentList" items="${departmentList}">
		            <option value="${departmentList.deptNm}">${departmentList.deptNm}</option>
		        </c:forEach>
		    </select>
		</div>

		<!-- 학과 까지 선택하면 학생도 이름 출력  -->
        <!-- 교직원이 부서를 선택 직급 하려고 했는데 그냥 이릅 출력  -->
        <!-- 교수 학과 선택하면 그냥 이릅 출력  -->
        
        
        <!-- 여기다가 결과 출력할거임  -->
        
        <div id ="resultSection" style="display: none">
        	<h3>결과출력</h3>
        	<table>
        		<thead>
        			<tr>
        				<th>이름</th>
        				<th>학년/ 부서</th>
        				<th>학과/ 부서</th>
        			</tr>
        		
        		</thead>
        		<tbody id="result">
        		
        		</tbody>
        	
        	</table>
        
        
        </div>
       
    	
        <br><br>
        <button type="submit">확인</button>
        
    </form>
    	
   
<script src="${pageContext.request.contextPath }/resources/js/role/studentRole.js"></script>
<%-- <script src="${pageContext.request.contextPath }/resources/js/role/resultRole.js"></script> --%>
</body>
