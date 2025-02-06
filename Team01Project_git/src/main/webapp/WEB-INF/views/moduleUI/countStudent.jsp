<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="card m-0" style="width: 100%; height:100%;">
    <div class="card-body ">
        <canvas id="countStuChart" style="width: 100%; height: 100%;"></canvas>
    </div>
</div>

<c:set var="labels" value=""/>
<c:forEach items="${streCateCdList}" var="streCateCd">
    <c:set var="labels" value="${labels}'${streCateCd.streCateNm}',"/>
</c:forEach>
<c:set var="labels" value="${fn:substring(labels, 0, fn:length(labels) - 1)}" />

<c:set var="data" value=""/>
<c:forEach items="${streCateCdList}" var="streCateCd">
    <c:set var="data" value="${data}${streCateCd.cnt},"/>
</c:forEach>
<c:set var="data" value="${fn:substring(data, 0, fn:length(data) - 1)}" />
<script>
    let countStuChart = document.getElementById('countStuChart').getContext('2d');
    new Chart(countStuChart, {
        type: 'pie',
        data: {
            labels: [${labels }],
            datasets: [{
                data: [${data}],
                backgroundColor: ['#FFB6C1', '#ADD8E6', '#FFFFE0', '#98FB98', '#DDA0DD']
            }]
        },
        options: {
        	maintainAspectRatio: false
        }
    });
</script>