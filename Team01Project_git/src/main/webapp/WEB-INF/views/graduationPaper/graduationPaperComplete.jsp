<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <style>
        .container {
            max-width: 10000px;
            margin: 50px auto;
            font-family: Arial, sans-serif;
        }
        .section-header {
            font-size: 24px;
            font-weight: bold;
            text-align: center;
            margin-bottom: 20px;
        }
        .step-indicator {
            display: flex;
            justify-content: space-between;
            margin-bottom: 20px;
        }
        .step {
            text-align: center;
            flex: 1;
            position: relative;
        }
        .step:not(:last-child)::after {
            content: "→";
            position: absolute;
            top: 50%;
            right: -15px;
            transform: translateY(-50%);
            font-size: 20px;
            color: #ccc;
        }
        .step .icon {
            width: 50px;
            height: 50px;
            margin: 0 auto 10px auto;
            border: 2px solid #ccc;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 20px;
            color: #999;
        }
        .step.active .icon {
            border-color: #007bff;
            color: #007bff;
        }
        .step.active {
            font-weight: bold;
            color: #007bff;
        }
        .info-box {
            background: #f0f4f8;
            border: 1px solid #ccc;
            padding: 20px;
            border-radius: 5px;
            text-align: center;
            font-size: 16px;
            margin-bottom: 20px;
        }
        .details {
            margin-top: 20px;
            padding: 15px;
            background: #f9f9f9;
            border: 1px solid #ddd;
            border-radius: 5px;
        }
        .details p {
            font-size: 16px;
            margin: 10px 0;
        }
        .actions {
            margin-top: 30px;
            text-align: center;
        }
        .btn {
            display: inline-block;
            padding: 10px 20px;
            font-size: 16px;
            border-radius: 5px;
            text-decoration: none;
            margin: 5px;
        }
        .btn-primary {
            background-color: #007bff;
            color: white;
            border: none;
        }
        .btn-secondary {
            background-color: #6c757d;
            color: white;
            border: none;
        }
        .file-list {
            list-style: none;
            padding: 0;
        }
        .file-list li {
            margin: 5px 0;
        }
        .file-list a {
            color: #007bff;
            text-decoration: none;
        }
        .file-list a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="section-header">논문 제출 완료</div>

    <div class="step-indicator">
        <div class="step">
            <div class="icon">1</div>
            제출자 정보 확인
        </div>
        <div class="step">
            <div class="icon">2</div>
            논문 등록
        </div>
        <div class="step active">
            <div class="icon">3</div>
            제출 완료
        </div>
    </div>

    <div class="info-box">
        논문이 성공적으로 제출되었습니다.<br>
        제출된 논문 정보를 확인하세요.
    </div>

    <div>
        <h5 style="text-align: center; margin-bottom: 20px;">논문 상세 정보</h5>
        <table style="width: 100%; border-collapse: collapse; margin-bottom: 20px;">
            <tr>
                <th style="width: 30%; text-align: center; padding: 10px; background-color: #f9f9f9; border: 1px solid #ddd;">제목</th>
                <td style="padding: 10px; border: 1px solid #ddd;">${paper.gpaNm}</td>
            </tr>
            <tr>
                <th style="text-align: center; padding: 10px; background-color: #f9f9f9; border: 1px solid #ddd;">부제목</th>
                <td style="padding: 10px; border: 1px solid #ddd;">${paper.gpaDnm != null ? paper.gpaDnm : "없음"}</td>
            </tr>
            <tr>
                <th style="text-align: center; padding: 10px; background-color: #f9f9f9; border: 1px solid #ddd;">주제</th>
                <td style="padding: 10px; border: 1px solid #ddd;">${paper.gpaSub != null ? paper.gpaSub : "없음"}</td>
            </tr>
            <tr>
                <th style="text-align: center; padding: 10px; background-color: #f9f9f9; border: 1px solid #ddd;">제출 일자</th>
                <td style="padding: 10px; border: 1px solid #ddd;">${paper.gpaDate}</td>
            </tr>
        </table>

        <table style="width: 100%; border-collapse: collapse;">
            <thead>
            <tr>
                <th style="text-align: center; padding: 10px; background-color: #f0f0f0; border: 1px solid #ddd;">파일명</th>
                <th style="text-align: center; padding: 10px; background-color: #f0f0f0; border: 1px solid #ddd;">파일 크기</th>
                <th style="text-align: center; padding: 10px; background-color: #f0f0f0; border: 1px solid #ddd;">다운로드</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${paper.atchFile.fileDetails}" var="file">
                <tr>
                    <td style="padding: 10px; border: 1px solid #ddd;">${file.orignlFileNm}</td>
                    <td style="padding: 10px; border: 1px solid #ddd;">${file.fileFancysize}</td>
                    <td style="padding: 10px; border: 1px solid #ddd;">
                        <a href="${pageContext.request.contextPath}/atch/${file.atchFileId}/${file.fileSn}" target="_blank" style="color: #007bff; text-decoration: none;">다운로드</a>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty paper.atchFile.fileDetails}">
                <tr>
                    <td colspan="3" style="padding: 10px; border: 1px solid #ddd; text-align: center;">첨부파일 없음</td>
                </tr>
            </c:if>
            </tbody>
        </table>
    </div>

    <div class="actions" style="margin-top: 20px; text-align:right;">
        <a href="${pageContext.request.contextPath}/graduationpaper/list/${paper.stuId}" class="btn btn-secondary">목록</a>
    </div>
</div>
</body>
</html>
