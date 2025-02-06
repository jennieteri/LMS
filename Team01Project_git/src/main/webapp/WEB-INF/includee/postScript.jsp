<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!-- 스크립트에서 사용할 변수 저장용 tag -->
<input type="hidden" id="postScriptCP" value="${pageContext.request.contextPath }"/>

<!--    페이지 하단의 스크립트 로딩 관리 페이지 -->
<!-- Vendor JS Files -->
  <script src="${pageContext.request.contextPath }/resources/js/jquery-3.7.1.min.js"></script>
  <script src="${pageContext.request.contextPath }/resources/js/jquery-ui.min.js"></script>
  <script src="${pageContext.request.contextPath }/resources/NiceAdmin/assets/vendor/apexcharts/apexcharts.min.js"></script>
  <script src="${pageContext.request.contextPath }/resources/NiceAdmin/assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
  <script src="${pageContext.request.contextPath }/resources/NiceAdmin/assets/vendor/chart.js/chart.umd.js"></script>
  <script src="${pageContext.request.contextPath }/resources/NiceAdmin/assets/vendor/echarts/echarts.min.js"></script>
  <script src="${pageContext.request.contextPath }/resources/NiceAdmin/assets/vendor/quill/quill.js"></script>
  <script src="${pageContext.request.contextPath }/resources/NiceAdmin/assets/vendor/simple-datatables/simple-datatables.js"></script>
  <script src="${pageContext.request.contextPath }/resources/NiceAdmin/assets/vendor/tinymce/tinymce.min.js"></script>
  <script src="${pageContext.request.contextPath }/resources/NiceAdmin/assets/vendor/php-email-form/validate.js"></script>
	
  <!-- dataTable -->
  <script src="https://cdn.datatables.net/2.1.8/js/dataTables.js"></script>
<!--   <script src="https://cdn.datatables.net/2.1.8/js/	dataTables.bootstrap5.js"></script> -->
<!--   <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script> -->

  <!-- axios -->
  <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
  
  <!-- sweetalert -->
  <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>

  <!-- fullcalendar -->
  <script src='https://cdn.jsdelivr.net/npm/fullcalendar@6.1.8/index.global.min.js'></script>
  
  <!-- ckEditor 로딩 -->
  <script type="importmap">
	{
		"imports": {
			"ckeditor5": "${pageContext.request.contextPath }/resources/js/ckeditor5/ckeditor5.js"
			, "ckeditor5/": "${pageContext.request.contextPath }/resources/js/ckeditor5/translations/ko.js/"
		}
	}
  </script>
  
  <script src="${pageContext.request.contextPath }/resources/js/utils/ckEditorMaker.js" type="module"></script>
	
  <!-- Template Main JS File -->
  <script src="${pageContext.request.contextPath }/resources/NiceAdmin/assets/js/main.js"></script>
  
  <!-- sidebar lecture list -->
  <script src="${pageContext.request.contextPath }/resources/js/lecture/sidebarLectureList.js"></script>
 
  <!-- header 알람 수신 전용 -->
  <script src="${pageContext.request.contextPath }/resources/js/notification/notificationScript.js" defer="defer"></script>
  
  <!-- 모듈UI 스크립트 -->
  <script  src="${pageContext.request.contextPath }/resources/js/moduleUI/schedule.js" defer="defer"></script>
  
  
  
  
  
  
  
  
  
  
  
  