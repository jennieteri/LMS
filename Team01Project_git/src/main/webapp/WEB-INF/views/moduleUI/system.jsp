<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<div class="recent-notices-container" style="height:100%; width: 100%; border: 1px solid #ddd; border-radius: 8px; background-color: #f8f9fa; padding: 20px; box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);">
    <h5 class="text-center">📢 최근 공지사항</h5>
    <table class="table table-hover rounded-table" style="width:100%; margin-top: 25px; box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);">
        <thead class="table-primary text-center" style="background-color: #007bff; color: #fff;">
            <tr>
                <th style="width: 70%; font-size: 1rem;">제목</th>
                <th style="width: 30%; font-size: 1rem;">작성일자</th>
            </tr>
        </thead>
        <tbody>
            <c:choose>
                <c:when test="${not empty systemBoard}">
                    <c:forEach items="${systemBoard}" var="system" varStatus="status">
                        <tr onclick="location.href='<c:url value='/systemBoard/${system.snbNo}'/>'" style="width:100%; cursor: pointer; transition: background-color 0.2s;">
                            <td style="width:70%; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; padding: 15px; font-size: 1rem; color: #333;">
                                ${system.snbTtl}
                            </td>
                            <td class="text-center" style="width:30%; padding: 15px; font-size: 0.9rem; color: #555;">
                                ${fn:split(system.snbDt, 'T')[0]}
                            </td>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr>
                        <td colspan="2" class="text-center text-muted" style="padding: 20px; font-size: 1rem;">📭 최근 공지사항이 없습니다.</td>
                    </tr>
                </c:otherwise>
            </c:choose>
        </tbody>
    </table>
</div>