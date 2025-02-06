<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <meta content="width=device-width, initial-scale=1.0" name="viewport">

  <title>연근대학교</title>
  <meta content="" name="description">
  <meta content="" name="keywords">

  <tiles:insertAttribute name="preScript" />

  <!-- =======================================================
  * Template Name: NiceAdmin
  * Template URL: https://bootstrapmade.com/nice-admin-bootstrap-admin-html-template/
  * Updated: Apr 20 2024 with Bootstrap v5.3.3
  * Author: BootstrapMade.com
  * License: https://bootstrapmade.com/license/
  ======================================================== -->
  <style>
  	#main{
  		border: 0;
  	}
  	#footer{
  		border: 0;
  		padding-bottom: 0;
  	}
  	#sidebar{
  		z-index: 1000;
  	}
  </style>
</head>

<body>

  <!-- ======= Header ======= -->
  <header id="header" class="header fixed-top d-flex align-items-center">

 	<tiles:insertAttribute name="header" />
 	
  </header><!-- End Header -->

  <!-- ======= Sidebar ======= -->
  <aside id="sidebar" class="sidebar">
	
	<tiles:insertAttribute name="sidebar"/>
   	
  </aside><!-- End Sidebar-->

  <main id="main" class="main">

	<tiles:insertAttribute name="content" />

  </main><!-- End #main -->

  <!-- ======= Footer ======= -->
  <footer id="footer" class="footer">
  
    <tiles:insertAttribute name="footer"/>
    
  </footer><!-- End Footer -->

  <a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i class="bi bi-arrow-up-short"></i></a>

  <tiles:insertAttribute name="postScript" />

</body>

</html>