
console.log("chatroom.js loaded");

//not concern for websocket
const input = document.querySelector("#chat-message");
const sendbutton = document.querySelector("#send-button");

const chatplace = document.querySelector("#chat-place");
const classlist = document.querySelector(".class-list");
const chatlink = document.querySelector(".chat-link");

const courselink = document.querySelector(".course-link");


let loginUser = null;//提前宣告，載入資料後，要把會員資料儲存在這裡
let currentCourseId = null;
let recentchats = null;
let ws = null;


//載入頁面就開始從後端拿資料

//取得login資料
fetch('/meow-gym/chat/userinfo', {
	method: 'GET'
}).then(resp => resp.json())
	.then(body => {
		loginUser = body.loginUser;
		console.log(loginUser); //代表
		// username.innerText = `${loginUser.name}，你好！`;
		console.log("loginUser.avatarUrl" + loginUser.avatarUrl);
		console.log("loginUser.role" + loginUser.role);
	});


//取得該user所有的courseId >> 搜尋session_users表格 >> 待改
//應該要放在ws onopen內做
fetch('/meow-gym/chat/getusercourseid', {
	method: 'GET'
}).then(resp => resp.json())
	.then(body => {
		console.log(body);
		//console.log(body.usercourseid[0].courseId);
		console.log(body.usercourseid);

		currentCourseId = body.usercourseid; // [CHANGED] 更新目前選擇的課程 ID	
		console.log("載入課程:", currentCourseId);
		connectChat(currentCourseId);

		//列出現在課程 (可刪除)
		// classlist.innerHTML += `
		// 	<li class="chat-contact-list-item mb-0 course-link">
		// 	        <a class="d-flex align-items-center chat-link"  data-courseid="${body.usercourseid}">
		// 	          <div class="flex-shrink-0 avatar avatar-busy">
		// 	            <span class="avatar-initial rounded-circle bg-label-success">CM</span>
		// 	          </div>
		// 	          <div class="chat-contact-info flex-grow-1 ms-4">
		// 	            <div class="d-flex justify-content-between align-items-center">
		// 	              <h6 class="chat-contact-name text-truncate fw-normal m-0">${body.usercourseid}</h6>
		// 	              <small class="chat-contact-list-item-time">1 Day</small>
		// 	            </div>
		// 	            <small class="chat-contact-status text-truncate">If it takes long you can mail inbox
		// 	              user</small>
		// 	          </div>
		// 	        </a>
		// 	      </li>`;

	});

// 分房間發fetch

// classlist.addEventListener("click", function (e) {
// 	const link = e.target.closest(".chat-link");
// 	const li = e.target.closest("li");

// 	// 1) 先清掉所有 active（包含先前選到的）
// 	classlist.querySelectorAll(".chat-contact-list-item.active").forEach(item => { item.classList.remove("active") });

// 	if (link) {
// 		e.preventDefault(); // 阻止 a 連結的跳轉
// 		console.log(e.target);
// 		console.log(link);

// 		//background become purple
// 		console.log(li);

// 		li.classList.add("active");

// 		const courseId = link.dataset.courseid;
// 		currentCourseId = Number(courseId); // [CHANGED] 更新目前選擇的課程 ID	
// 		console.log("載入課程:", courseId);
// 		connectChat(courseId);

// 	}
// });



//websocket 處理點擊 courseId 後的聊天紀錄載入
function connectChat(currentCourseId) {

	console.log("Login user inside connectChat:", loginUser);
	if (ws) {
		ws.close();
	};
	chatplace.innerHTML = '';

	ws = new WebSocket(`ws://localhost:8080/meow-gym/chat?courseId=${currentCourseId}`);

	ws.addEventListener("open", function () {
		//送一個訊息到後端 >> 不送了，免得又說不是JSON炸掉
		//ws.send("Hello from browser!");
	})

	ws.onmessage = function (e) {
		console.log("Server:", e);
		let data;
		//chatplace.innerHTML = "";
		//console.log(JSON.parse(e.data));
		const allMessages = JSON.parse(e.data);
		console.log(allMessages);


		for (let i = 0; i < allMessages.length; i++) {

			const isSelf = loginUser.name === `${allMessages[i].name}`;
			const messageSelf = isSelf ? "chat-message-right" : "chat-message-left";

			//聊天角色判斷
			let roleText = "";
			if (allMessages[i].role == 1) {
				roleText = "會員";
			} else if (allMessages[i].role == 2) {
				roleText = "教練";
			} else if (allMessages[i].role == 3) {
				roleText = "管理者";
			}

			//教練頭像加上紫色邊框
			const caochrole = roleText === "教練" ? "coach-border" : "";

			if (loginUser.role === 3) {
				chatplace.innerHTML += `
			   <li class="chat-message chat-contact-list-item ${messageSelf}">
			              <div class="d-flex overflow-hidden">
			                <div class="chat-message-wrapper flex-grow-1">
							<div class="d-flex align-items-end flex-grow-1">
								<div class="user-avatar ms-4">
								<div class="user-detail" data-role="${roleText}">${allMessages[i].name}</div>
										<div class="avatar ${caochrole}">
											<img src="${allMessages[i].avatarUrl}" alt="User Avatar" class="rounded-circle" id="user-avatar" />
										</div>
								</div>
									 <div class="chat-message-text">
									 <span class="material-symbols-outlined">close</span>
			                   			 <p class="mb-0">${allMessages[i].text}</p>
			                  		</div>
							</div>

			                  <div class="text-end text-body-secondary mt-1">
			                    <i class="icon-base ti tabler-checks icon-16px text-success me-1"></i>
			                    <small>${allMessages[i].time.slice(0, 16)}</small>
			                  </div>
			                </div>

			              </div>
			            </li>`;
			} else {
				chatplace.innerHTML += `
			   <li class="chat-message chat-contact-list-item ${messageSelf}">
			              <div class="d-flex overflow-hidden">
			                <div class="chat-message-wrapper flex-grow-1">
							<div class="d-flex align-items-end flex-grow-1">
								<div class="user-avatar ms-4">
								<div class="user-detail" data-role="${roleText}">${allMessages[i].name}</div>
										<div class="avatar ${caochrole}">
											<img src="${allMessages[i].avatarUrl}" alt="User Avatar" class="rounded-circle" id="user-avatar" />
										</div>
								</div>
									 <div class="chat-message-text">
			                   			 <p class="mb-0">${allMessages[i].text}</p>
			                  		</div>
							</div>

			                  <div class="text-end text-body-secondary mt-1">
			                    <i class="icon-base ti tabler-checks icon-16px text-success me-1"></i>
			                    <small>${allMessages[i].time.slice(0, 16)}</small>
			                  </div>
			                </div>

			              </div>
			            </li>`;
			}

		};

		// 歷史訊息載入完，捲到最底
		const chatHistoryBody = document.querySelector('.chat-history-body');
		chatHistoryBody.scrollTop = chatHistoryBody.scrollHeight;


	};

	ws.addEventListener('close', e => alert('連線已關閉'));
	ws.addEventListener('error', e => alert('連線發生錯誤'));

}

// ===== 發送訊息 =====
sendbutton.addEventListener("click", function () {

	if (!loginUser) return alert('尚未登入，請重新登入');
	if (!currentCourseId) return alert('請先選擇課程');

	if (!ws || ws.readyState !== WebSocket.OPEN) {
		alert('尚未連線聊天室');
		return;
	}

	const text = input.value.trim();
	if (!text) return;

	// 後端 @OnMessage 會解析並存 DB、廣播
	ws.send(JSON.stringify({ type: 'chat', text: text }));// 送訊息到後端 ChatEndpoint.java
	input.value = '';

});

//============ add header settings==============

function switchMenu(role) {

	switch (role) {
		// 顯示會員列表
		case 1:
			userMenu.classList.remove('d-none');
			shoppingCart.classList.remove('d-none');  // 顯示購物車按鍵
			break;

		// 顯示教練列表  
		case 2:
			coachMenu.classList.remove('d-none');
			break;

		// 顯示管理者列表  
		case 3:
			adminMenu.classList.remove('d-none');
			break;

		// 預設顯示會員列表
		default:
			userMenu.classList.remove('d-none');
			shoppingCart.classList.remove('d-none');  // 顯示購物車按鍵
			break;
	}
}

fetch('/meow-gym/index/loginData')
	.then(resp => resp.json())
	.then(respbody => {
		if (respbody.successful) {
			switchMenu(respbody.user.role); // 切換側邊欄: 1 -> 一般會員、2 -> 教練、3 -> 管理者
			userName.textContent = respbody.user.name; // 修改標籤內使用者名稱
			avatarImg.src = respbody.user.avatarUrl; // 更換img標籤圖片
		} else {
			Swal.fire({
				title: '錯誤',
				text: '請先登入',
				icon: 'error',
				target: document.body
			})
				.then(() => location.href = '/meow-gym/index/login');
		}
	});







