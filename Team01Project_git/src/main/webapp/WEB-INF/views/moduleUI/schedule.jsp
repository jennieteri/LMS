<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="card m-0" style="width: 100%; height: 100%;">
	<div class="card-body p-2">
		<div class="mb-2" data-lect="true">
			<table class="table table-bordered table-primary text-center align-middle mb-0">
				<thead>
					<tr>
						<th style="width: 50px;">교시</th>
						<c:forEach items="${dotwList }" var="dotw">
							<c:if test="${dotw.cocoCd ne 'SUN' }">
								<th>${dotw.cocoStts}</th>
							</c:if>
						</c:forEach>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${etimeList }" var="etime">
						<tr class="table-light">
							<th style="font-size: 0.8rem;" class="${etime.edcTimeCd}"
								data-bs-toggle="tooltip" data-bs-placement="left" title="${fn:substring(etime.beginTime,0,2)} ~ ${fn:substring(etime.endTime,0,2)}">
								${etime.commonCodeVO.cocoStts}<br/>
							</th>
							<c:forEach items="${dotwList }" var="dotw">
								<c:if test="${dotw.cocoCd ne 'SUN' }">
									<td data-etime="${etime.edcTimeCd }" data-dotw="${dotw.cocoCd }"
										data-schedule="${dotw.cocoCd }-${etime.edcTimeCd }"/>
								</c:if>
							</c:forEach>
						</tr>
					</c:forEach>			
				</tbody>
			</table>
		</div>
		<c:set var="total" value="0"/>
		<c:forEach items="${lectList }" var="lect">
			<c:set var="total" value="${total + lect.lectScore }"/>
		</c:forEach>
		<h6 class="my-2">수강학점 <span class="badge rounded-pill text-bg-secondary">${total } / 18</span></h6>
		<div id="lectCarousel" class="carousel slide">
			<div class="carousel-inner">
				<c:forEach items="${lectList }" var="lect" varStatus="index">
					<div class="carousel-item ${index.first ? 'active' : ''}">
						<div class="card mb-0 lect-cart" data-lect-no="${lect.lectNo}">
							<div class="card-header py-3 card-${lect.lectNo}">
								<c:if test="${lect.lectStatusCd eq 'L03' }">
									<a href="${pageContext.request.contextPath}/lecture/${lect.lectNo}/materials">
										<h5 class="card-title fw-bold p-0 m-0" style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">[${lect.subjectVO.subFicdCdCommonCodeVO.cocoStts}] ${lect.lectNm}</h5>
									</a>
								</c:if>
								<c:if test="${lect.lectStatusCd ne 'L03' }">
									<h5 class="card-title fw-bold p-0 m-0" style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">[${lect.subjectVO.subFicdCdCommonCodeVO.cocoStts}] ${lect.lectNm}</h5>
								</c:if>
							</div>
							<div class="card-body pb-2 px-4">
								<h6 class="card-title p-0 mt-3">${lect.professorVO.nm} 교수</h6>
								<span class="card-text p-0">${lect.lectScore}학점</span>
								<p class="card-text p-0 float-end">${not empty lect.scheduleVO[0] ? lect.scheduleVO[0].classRoomVO.croomPstn : "온라인"}</p>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
			<button class="carousel-control-prev" type="button" data-bs-target="#lectCarousel" data-bs-slide="prev" style="justify-content: start;">
				<i class="bi bi-caret-left-fill" style="color: darkBlue;"></i>
			</button>
			<button class="carousel-control-next" type="button" data-bs-target="#lectCarousel" data-bs-slide="next" style="justify-content: end;">
				<i class="bi bi-caret-right-fill" style="color: darkBlue;"></i>
			</button>
		</div>
	</div>
</div>
<script>
	let tooltipList = $('[data-bs-toggle="tooltip"]');
	[...tooltipList].map(el => new bootstrap.Tooltip(el));

	const fnBgColor = function(){
		const r = Math.floor(Math.random() * 56) + 180;
	    const g = Math.floor(Math.random() * 56) + 180;
	    const b = Math.floor(Math.random() * 56) + 180;
		return `rgb(\${r}, \${g}, \${b})`;
	}
	<c:forEach items="${lectList }" var="lect">
		var bgColor = fnBgColor();
		$('.card-${lect.lectNo}').css('background', bgColor);
		<c:forEach items="${lect.scheduleVO }" var="schedule">
			$('td[data-schedule="${schedule.dateCd }-${schedule.edcTimeCd }"]').css('background', bgColor);
		</c:forEach>
	</c:forEach>
</script>