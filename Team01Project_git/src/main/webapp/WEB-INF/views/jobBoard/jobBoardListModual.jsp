<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<div class="container">
		<div class="d-flex justify-content-center" style="gap: 0px;">
			<!-- 채용 공고 -->
			<div class="card shadow-sm border-0 text-center m-0"
				style="background-color: #f8f9fa; width: 200px; height: 100%; font-size: 0.9rem;">
				<div class="card-body p-3">
					<p class="card-title text-primary" style="font-size: 0.8rem;">
						<i class="bi bi-briefcase-fill"></i> 진행중인 채용공고
					</p>
					<p class="card-text display-6 text-dark fw-bold"
						style="font-size: 1.5rem;">
						<c:choose>
							<c:when test="${statistics[0].JOB_POSTING != null}">
                            ${statistics[0].JOB_POSTING}
                        </c:when>
							<c:otherwise>0</c:otherwise>
						</c:choose>
					</p>
					<a
						href="${pageContext.request.contextPath}/jobboard?searchType=&searchWord=채용공고&page="
						class="btn btn-outline-primary btn-sm">바로가기</a>
				</div>
			</div>

			<!-- 채용 설명회 -->
			<div class="card shadow-sm border-0 text-center m-0"
				style="background-color: #f8f9fa; width: 200px; height: 100%; font-size: 0.9rem;">
				<div class="card-body p-3">
					<p class="card-title text-success fw" style="font-size: 0.8rem;">
						<i class="bi bi-calendar-event-fill"></i> 진행중인 채용설명회
					</p>
					<p class="card-text display-6 text-dark fw-bold"
						style="font-size: 1.5rem;">
						<c:choose>
							<c:when test="${statistics[0].JOB_BRIEFING != null}">
                            ${statistics[0].JOB_BRIEFING}
                        </c:when>
							<c:otherwise>0</c:otherwise>
						</c:choose>
					</p>
					<a href="${pageContext.request.contextPath}/jobboard?searchType=&searchWord=채용설명회&page="
						class="btn btn-outline-success btn-sm">바로가기</a>
				</div>
			</div>
		</div>
	</div>
