<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="card m-0" style="width: 100%; height:100%;">
    <div class="card-body m-0">
        <canvas id="genderChart" style="width: 100%; height: 100%;"></canvas>
    </div>
</div>

    <script>
        const boyCount = ${boyCount != null ? boyCount : 0};
        const girlCount = ${girlCount != null ? girlCount : 0};

        const ctx = document.getElementById('genderChart').getContext('2d');
        new Chart(ctx, {
            type: 'pie',
            data: {
                labels: ['남자', '여자'],
                datasets: [{
                    data: [boyCount, girlCount],
                    backgroundColor: ['skyblue', 'pink']
                }]
            },
            options: {
            	maintainAspectRatio: false
            }
        });
    </script>
