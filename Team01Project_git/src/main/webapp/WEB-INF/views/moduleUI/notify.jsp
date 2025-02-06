<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="notify" style="background-color: silver;width: 100%;height: 100%;padding: 10px;border: 1px solid #ccc;border-radius: 5px;overflow-y: auto;position: relative;">
	<div class="notification-item" style="padding: 10px;border: 1px solid #ddd;margin-bottom: 10px;border-radius: 5px;background-color: #f9f9f9;box-shadow: 0 1px 2px rgba(0, 0, 0, 0.1);">
		<c:if test="${notifyList.size() eq 0 }">
			<p style="text-align: center; color: #6c757d;">새로운 알림이 없습니다.</p>
		</c:if>
		<c:forEach items="${notifyList }" var="module">
		    <div class="card m-0" style="height: 100%; border: none;">
		        <div class="card-body p-2" style="padding: 0.75rem 1rem; background-color: #ffffff; border-radius: 5px;">
		            <div class="ls-notify mb-2" style="font-size: 0.9rem; color: #333;"></div>
		            <h6 class="m-0" style="font-size: 1.1rem; font-weight: bold; color: #007bff; display: flex; justify-content: space-between; align-items: center;">
		                ${empty module.notiHead ? '알림' : module.notiHead}
		                <span class="badge rounded-pill text-bg-secondary" style="background-color: #007bff; font-size: 0.85rem; margin-left: auto;">
		                    <a href="${pageContext.request.contextPath}/${empty module.notiUrl ? '' : module.notiUrl}" onclick="updateOneReadNotfication(${module.notiNo})" 
		                       style="font-size: 0.8rem; color: white; text-decoration: none;">
		                        ${empty module.notiUrl ? '읽음처리' : '바로가기'}
		                    </a>
		                </span>
		            </h6>
		            <p class="m-0" style="font-size: 0.9rem; color: #555;">${empty module.notiCn ? '새 알림이 있습니다.' : module.notiCn}</p>
		            <small style="color: #888; font-size: 0.8rem;">
						${not empty module.notiDate ?  module.notiDate : '방금 전'}
					</small>
		        </div>
		    </div>
		</c:forEach>
	</div>
</div>