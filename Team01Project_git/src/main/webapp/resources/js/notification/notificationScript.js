
var notifiCationUserId = document.querySelector("#notifiCationUserId").value;
var notifiCationContextPath = document.querySelector("#headContextPath").value;
document.addEventListener("DOMContentLoaded", async () => {
	
	
	console.log(notifiCationUserId)
	await load(notifiCationUserId, notifiCationContextPath);
});
function load(notifiCationUserId) {
	return new Promise((resolve, reject) => {
		const eventSource = new EventSource(`${notifiCationContextPath}/api/notifications/stream/${notifiCationUserId}`);

		eventSource.onopen = function(event) {
		//	console.log('SSE 연결 수립됨');
			resolve(eventSource);
		};

		eventSource.onmessage = function(event) {
			const notification = JSON.parse(event.data);
			// 기존 알림 UI 업데이트
			displayNotification(notification);

			// notifyDiv가 있는지 확인 후 실행
			const notifyDiv = document.querySelector(".notify");
			if (notifyDiv) {
				//console.log(notification.moduleList);
				// 메인화면 UI에 알림 표시
				if (notification.moduleList && notification.moduleList.length > 0) {
					displayMainNotifications(notification.moduleList);
				}
			} else {
				console.warn("notifyDiv가 화면에 존재하지 않습니다.");
			}

			const notificationsUl = document.querySelector('.notifications');
			const currentCount = notificationsUl.querySelectorAll('li.notification-item:not(.dropdown-header)').length;
			updateNotificationCount(currentCount);
		};

		eventSource.onerror = function(error) {
			console.error('SSE 에러:', error);
			eventSource.close();
			reject(error);
		};
	});
}

function displayMainNotifications(moduleList) {
	const notifyDiv = document.querySelector(".notify");
	notifyDiv.innerHTML = '';

	notifyDiv.style = `
        background-color: silver;
        width: 100%;
        height: 100%;
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 5px;
        overflow-y: auto;
        max-height: 500px;
        position: relative;
    `;

	// 새 알림 항목 동적 추가
	moduleList.forEach(module => {
		const notificationItem = document.createElement('div');
		notificationItem.className = 'notification-item';

		// 알림 카드 스타일
		notificationItem.style = `
            padding: 10px;
            border: 1px solid #ddd;
            margin-bottom: 10px;
            border-radius: 5px;
            background-color: #f9f9f9;
            box-shadow: 0 1px 2px rgba(0, 0, 0, 0.1);
        `;

		// 'direct' 및 'dataUrl' 결정
		var direct = "바로가기";
		var dataUrl = String(module.notiUrl); // 문자열로 명시적 변환
		if (dataUrl === " ") {
			direct = "메인화면으로";
		}

		// 알림 항목의 HTML 및 스타일 정의
		notificationItem.innerHTML = `
    <div class="card m-0" style="height: 100%; border: none;">
        <div class="card-body p-2" style="padding: 0.75rem 1rem; background-color: #ffffff; border-radius: 5px;">
            <div class="ls-notify mb-2" style="font-size: 0.9rem; color: #333;"></div>
            <h6 class="m-0" style="font-size: 1.1rem; font-weight: bold; color: #007bff; display: flex; justify-content: space-between; align-items: center;">
                ${module.notiHead || '알림'}
                <span class="badge rounded-pill text-bg-secondary" style="background-color: #007bff; font-size: 0.85rem; margin-left: auto;">
                    <a href="${notifiCationContextPath}/${dataUrl}" onclick="updateOneReadNotfication(${module.notiNo})" 
                       style="font-size: 0.8rem; color: white; text-decoration: none;">
                        ${direct}
                    </a>
                </span>
            </h6>
            <p class="m-0" style="font-size: 0.9rem; color: #555;">${module.notiCn || '새 알림이 있습니다.'}</p>
            <small style="color: #888; font-size: 0.8rem;">${module.notiDate ? formatDate(module.notiDate) : '방금 전'}</small>
        </div>
    </div>
`;


		// 알림 항목 추가
		notifyDiv.appendChild(notificationItem);
	});

	// 알림이 없을 경우 메시지 표시
	if (moduleList.length === 0) {
		notifyDiv.innerHTML = '<p style="text-align: center; color: #6c757d;">새로운 알림이 없습니다.</p>';
	}
}


function ddd() {
	console.log('ddd')
}


async function displayNotification(notification) {
	const notificationsUl = document.querySelector('.notifications');
	var notifiCationContextPath = document.querySelector("#headContextPath").value;

	// 알림 리스트가 있는 경우 처리
	if (notification.list && notification.list.length > 0) {
		// 기존 알림 제거
		const existingItems = notificationsUl.querySelectorAll('li.notification-item:not(.dropdown-header)');
		existingItems.forEach(item => item.remove());

		// 새로운 알림 추가
		notification.list.forEach(noti => {
			const li = createNotificationListItem(noti, notifiCationContextPath);
			notificationsUl.appendChild(li);
		});

		// 알림 개수 업데이트
		updateNotificationCount(notification.list.length);

	} else if (notification.one && notification.one != null) {
		// 단건 알림 처리
		const noNotificationItem = notificationsUl.querySelector('li.notification-item:not(.dropdown-header)');
		if (noNotificationItem && noNotificationItem.textContent === '새로운 알림이 없습니다.') {
			noNotificationItem.remove();
		}

		// 단건 알림 추가
		const li = createNotificationListItem(notification.one, notifiCationContextPath);
		notificationsUl.appendChild(li);

		// 현재 알림 개수 업데이트
		const currentCount = notificationsUl.querySelectorAll('li.notification-item:not(.dropdown-header)').length;
		updateNotificationCount(currentCount);

		// 새 알림 팝업 표시
		showNewNotificationPopup(notification.one);

	} else {
		// 알림이 없을 때 메시지 표시
		const existingItems = notificationsUl.querySelectorAll('li.notification-item:not(.dropdown-header)');
		if (existingItems.length === 0) {
			const li = document.createElement('li');
			li.className = 'no-notification-item';
			li.style = "padding: 10px; text-align: center; color: #6c757d;";
			li.textContent = '새로운 알림이 없습니다.';
			notificationsUl.appendChild(li);
		}

		// 알림 개수 0으로 설정
		updateNotificationCount(0);
	}
}

function formatDate(dateString) {
	const date = new Date(dateString);
	return date.toLocaleString('ko-KR', {
		year: 'numeric',
		month: '2-digit',
		day: '2-digit',
		hour: '2-digit',
		minute: '2-digit'
	});
}
function updateNotificationCount(count) {
	const badge = document.querySelector('.nav-link.nav-icon .badge');
	if (badge) {
		if (count > 0) {
			badge.textContent = count;
			badge.style.display = 'inline-block'; // 뱃지를 보이게 설정
		} else {
			badge.style.display = 'none'; // 알림이 없으면 뱃지를 숨김
		}
	}
}



async function updateOneReadNotfication(notiNo) {
	//단건 업데이트 

	var notifiCationContextPath = document.querySelector("#headContextPath").value;
	fetch(`${notifiCationContextPath}/api/notifications/test/${notiNo}`, { method: 'get' })

}

//전체 읽음 표시로 전체 제거
async function updateAllReadNotfication() {
	var notifiCationContextPath = document.querySelector("#headContextPath").value;
	try {
		const response = await fetch(`${notifiCationContextPath}/api/notifications/test/all`, { method: 'get' });
		if (response.ok) {
			const result = await response.json();
			//console.log(result.message);

			// UI 직접 업데이트
			updateNotificationUI();
		} else {
			console.error('알림 처리 실패');
		}
	} catch (error) {
		console.error('알림 처리 중 오류 발생:', error);
	}
}

function updateNotificationUI() {
	const notificationsUl = document.querySelector('.notifications');
	// 기존 알림 항목들 제거 (헤더는 유지)
	const existingItems = notificationsUl.querySelectorAll('li:not(.dropdown-header)');
	existingItems.forEach(item => item.remove());

	// "모든 알림을 읽었습니다" 메시지 추가
	const li = document.createElement('li');
	li.className = 'notification-item';
	li.innerHTML = `
        <i class="bi bi-check-circle text-success"></i>
        <div>
            <h4>알림</h4>
            <p>모든 알림을 읽었습니다.</p>
        </div>
    `;
	notificationsUl.appendChild(li);

	// 알림 카운트 업데이트
	updateNotificationCount(0);
}

var toastContainer;

function showNewNotificationPopup(noti) {
	if (!toastContainer) {
		toastContainer = document.createElement('div');
		toastContainer.className = 'toast-container position-fixed end-0 p-3';
		toastContainer.style.cssText = `
        z-index: 9999;
        bottom: 70px; 
    `;
		document.body.appendChild(toastContainer);
	}
	var direct = "바로가기";
	var dataUrl = noti.notiUrl;
	if (dataUrl == " ") { direct = "메인화면으로" }
	const toastHtml = `
    <div class="toast" role="alert" aria-live="assertive" aria-atomic="true">
        <div class="toast-header" style="background-color:#003399; color: white;">
            <i class="bi bi-bell me-2"></i>
            <strong class="me-auto">${noti.notiHead || '알림'}</strong>
            <small>방금 전</small>
            <button type="button" data-bs-dismiss="toast"  class="btn-close btn-close-white" aria-label="Close"></button>
        </div>
        <div class="toast-body">
            ${noti.notiCn || '새 알림이 있습니다.'}
            <a href="${notifiCationContextPath}/${noti.notiUrl}" onclick="updateOneReadNotfication(${noti.notiNo})" style="font-size: 0.8rem;">${direct}</a>
        </div>
    </div>
`;

	//	background-color: #003399; 
	//	color: white;

	toastContainer.insertAdjacentHTML('beforeend', toastHtml);
	const toast = toastContainer.lastElementChild;

	const bsToast = new bootstrap.Toast(toast, {
		autohide: true,
		delay: 3000
	});
	bsToast.show();

	toast.addEventListener('hidden.bs.toast', () => {
		toast.remove();
		if (toastContainer.children.length === 0) {
			toastContainer.remove();
			toastContainer = null;
		}
	});
}


function createNotificationListItem(noti) {
	const li = document.createElement('li');
	li.className = 'notification-item';
	li.style = "padding: 10px; border-bottom: 1px solid #e9ecef;";


	var direct = "바로가기";
	let iconClass = 'bi-exclamation-circle text-warning';
	if (noti.notiCd === 'NO01') iconClass = 'bi-check-circle text-success';
	if (noti.notiCd === 'NO02') iconClass = 'bi-info-circle text-primary';
	if (noti.notiCd === 'NO04') iconClass = 'bi-exclamation-diamond-fill text-danger';

	var dataUrl = String(noti.notiUrl); // 문자열로 명시적 변환
	if (dataUrl === " ") {
		direct = "메인화면으로";
	}
	console.log(notifiCationContextPath)

	li.innerHTML = `
        <div style="display: flex; align-items: center;">
            <div style="display: flex; justify-content: center; align-items: center; width: 60px; height: 60px;">
                <i class="bi ${iconClass}" style="font-size: 1.5rem;"></i>
            </div>
            <div style="flex: 1;">
                <h6 style="margin: 0; font-weight: bold;">${noti.notiHead}</h6>
                <p style="margin: 5px 0; font-size: 0.9rem;">${noti.notiCn}</p>
                <p style="margin: 0; font-size: 0.8rem; color: #6c757d;">${noti.sendNm}</p>
                <p style="margin: 0; font-size: 0.8rem; color: #6c757d;">${noti.notiDate}</p>
                <a href="${notifiCationContextPath}/${dataUrl}" onclick="updateOneReadNotfication(${noti.notiNo})" style="font-size: 0.8rem;">${direct}</a>
            </div>
        </div>
    `;

	return li;
}













