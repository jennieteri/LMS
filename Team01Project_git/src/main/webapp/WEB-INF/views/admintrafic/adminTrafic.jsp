<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}">

<div class="container-fluid">
    <div class="row">
       <div class="col-md-8 mb-4">
		    <div class="card" style="height: 400px; border: 2px solid #000;">
		        <div class="d-flex align-items-center p-2" style="border-bottom: 1px solid #dee2e6;">
		            <input type="date" class="form-control w-25" id="logData" style="height: 35px; font-size: 14px;">
		        </div>
		        <div class="card-body" style="position: relative; padding-top: 0; ">
		            <canvas id="trafficChart" style="width: 100%; height: 350px;"></canvas>
		        </div>
		    </div>
	</div>
        <!-- 오른쪽 상단 박스 -->
      <div class="col-md-4 mb-4">
          <div class="card" style="height: 400px; border: 2px solid #000;">
              <div class="card-body">
                  <!-- 오른쪽 상단 내용 -->
              	<canvas id="circleChart"></canvas>
              </div>
          </div>
      </div>
  </div>
</div>   
    <!-- 하단 박스 -->
<div class="row" style="margin: 0;">
    <div style="display: flex; width: 100%; gap: 10px;">
        <!-- 첫 번째 칸 -->
        <div style="flex: 1; height: 350px; border: 2px solid #000; border-radius: 15px; padding: 15px; position: relative; margin-right: 10px; display: flex; flex-direction: column;">
            <div class="d-flex justify-content-between mb-3" style="font-weight: bold; font-size: 1.5rem;">
                수강신청
                <button class="btn btn-primary" onclick="bucket()" style="width: 50px;"><i class="bi bi-tools"></i></button>
            </div>
            
            <!-- 표시 필드들 -->
            <div style="flex: 1; min-height: 0; display: flex; flex-direction: column;">
                <div class="input-group mb-3">
				    <input type="number" id="secondDisplay" class="form-control" value="5" min="1" max="60">
				    <span class="input-group-text">분</span>
				</div>
				<div class="input-group mb-3">
				    <input type="number" id="tokenDisplay" class="form-control" value="3" min="1" max="100">
				    <span class="input-group-text">건</span>
				</div>
                <hr>
                <!-- Range 슬라이더 -->
                <div class="">
                    <label class="form-label">분 설정</label>
                    <input type="range" class="form-range" id="secondRange" min="1" max="60" value="5">
                </div>
                <div class="">
                    <label class="form-label">건수 설정</label>
                    <input type="range" class="form-range" id="tokenRange" min="1" max="100" value="3">
                </div>
            </div>
        </div>
        <div style="flex: 1; height: 350px; border: 2px solid #000; border-radius: 15px; padding: 15px; position: relative; margin-right: 10px; display: flex; flex-direction: column;">
            <div class="d-flex justify-content-between mb-3" style="font-weight: bold; font-size: 1.5rem;">
                강의
                <button class="btn btn-primary" style="width: 50px;"><i class="bi bi-tools"></i></button>
            </div>
            
            <!-- 표시 필드들 -->
            <div style="flex: 1; min-height: 0; display: flex; flex-direction: column;">
                <div class="input-group mb-3">
                    <input type="text" id="secondDisplay2" class="form-control" value="10" readonly>
                    <span class="input-group-text">분</span>
                </div>
                <div class="input-group mb-3">
                    <input type="text" id="tokenDisplay2" class="form-control" value="100" readonly>
                    <span class="input-group-text">건</span>
                </div>
                <hr>
                <!-- Range 슬라이더 -->
                <div class="">
                    <label class="form-label">분 설정</label>
                    <input type="range" class="form-range" id="secondRange2" min="1" max="60" value="10">
                </div>
                <div class="">
                    <label class="form-label">건수 설정</label>
                    <input type="range" class="form-range" id="tokenRange2" min="1" max="100" value="100">
                </div>
            </div>
        </div>
        <div style="flex: 1; height: 350px; border: 2px solid #000; border-radius: 15px; padding: 15px; position: relative; margin-right: 10px; display: flex; flex-direction: column;">
            <div class="d-flex justify-content-between mb-3" style="font-weight: bold; font-size: 1.5rem;">
                증명서
                <button class="btn btn-primary" style="width: 50px;"><i class="bi bi-tools"></i></button>
            </div>
            
            <!-- 표시 필드들 -->
            <div style="flex: 1; min-height: 0; display: flex; flex-direction: column;">
                <div class="input-group mb-3">
                    <input type="text" id="secondDisplay3" class="form-control" value="10" readonly>
                    <span class="input-group-text">분</span>
                </div>
                <div class="input-group mb-3">
                    <input type="text" id="tokenDisplay3" class="form-control" value="50" readonly>
                    <span class="input-group-text">건</span>
                </div>
                <hr>
                <!-- Range 슬라이더 -->
                <div class="">
                    <label class="form-label">분 설정</label>
                    <input type="range" class="form-range" id="secondRange3" min="1" max="60" value="10">
                </div>
                <div class="">
                    <label class="form-label">건수 설정</label>
                    <input type="range" class="form-range" id="tokenRange3" min="1" max="100" value="50">
                </div>
            </div>
        </div>
        <div style="flex: 1; height: 350px; border: 2px solid #000; border-radius: 15px; padding: 15px; position: relative; margin-right: 10px; display: flex; flex-direction: column;">
            <div class="d-flex justify-content-between mb-3" style="font-weight: bold; font-size: 1.5rem;">
                장학금
                <button class="btn btn-primary"  style="width: 50px;"><i class="bi bi-tools"></i></button>
            </div>
            
            <!-- 표시 필드들 -->
            <div style="flex: 1; min-height: 0; display: flex; flex-direction: column;">
                <div class="input-group mb-3">
                    <input type="text" id="secondDisplay4" class="form-control" value="30" readonly>
                    <span class="input-group-text">분</span>
                </div>
                <div class="input-group mb-3">
                    <input type="text" id="tokenDisplay4" class="form-control" value="100" readonly>
                    <span class="input-group-text">건</span>
                </div>
                <hr>
                <!-- Range 슬라이더 -->
                <div class="">
                    <label class="form-label">분 설정</label>
                    <input type="range" class="form-range" id="secondRange4" min="1" max="60" value="30">
                </div>
                <div class="">
                    <label class="form-label">건수 설정</label>
                    <input type="range" class="form-range" id="tokenRange4" min="1" max="100" value="100">
                </div>
            </div>
        </div>
        <div style="flex: 1; height: 350px; border: 2px solid #000; border-radius: 15px; padding: 15px; position: relative; margin-right: 10px; display: flex; flex-direction: column;">
            <div class="d-flex justify-content-between mb-3" style="font-weight: bold; font-size: 1.5rem;">
                졸업
                <button class="btn btn-primary" style="width: 50px;"><i class="bi bi-tools"></i></button>
            </div>
            
            <!-- 표시 필드들 -->
            <div style="flex: 1; min-height: 0; display: flex; flex-direction: column;">
                <div class="input-group mb-3">
                    <input type="text" id="secondDisplay5" class="form-control" value="15" readonly>
                    <span class="input-group-text">분</span>
                </div>
                <div class="input-group mb-3">
                    <input type="text" id="tokenDisplay5" class="form-control" value="70" readonly>
                    <span class="input-group-text">건</span>
                </div>
                <hr>
                <!-- Range 슬라이더 -->
                <div class="">
                    <label class="form-label">분 설정</label>
                    <input type="range" class="form-range" id="secondRange5" min="1" max="60" value="15">
                </div>
                <div class="">
                    <label class="form-label">건수 설정</label>
                    <input type="range" class="form-range" id="tokenRange5" min="1" max="100" value="70">
                </div>
            </div>
        </div>
    </div>
</div>




<script src="${pageContext.request.contextPath}/resources/js/admintrafic/adminTraficScript.js"></script>
