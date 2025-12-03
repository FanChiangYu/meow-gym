
console.log("chatroom.js loaded");

//not concern for websocket
const input = document.querySelector("#chat-message");
const sendbutton = document.querySelector("#send-button");

const chatplace = document.querySelector("#chat-place");
const classlist = document.querySelector(".class-list");
const chatlink = document.querySelector(".chat-link");

const courselink = document.querySelector(".course-link");



const userMenu = document.querySelector('#user-menu');
const coachMenu = document.querySelector('#coach-menu');
const adminMenu = document.querySelector('#admin-menu');
const userName = document.querySelector('#user-name');
const avatarImg = document.querySelector('#user-avatar');
const shoppingCart = document.querySelector('#shopping-cart');
const logoutBtn = document.querySelector('#logout-btn');
console.log(logoutBtn);

let loginUser = null;//提前宣告，載入資料後，要把會員資料儲存在這裡
let currentCourseId = null;
let currentCourseTitle = null;
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
		currentCourseTitle = body.coursetitle;
		$('#coursetitle').text(currentCourseTitle); // add courseTitle to frontend

		console.log("載入課程:", currentCourseId);
		connectChat(currentCourseId);

	});


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
								<div class="user-detail" data-role="${roleText}" id="user_detail_id" style="color:white;">${allMessages[i].name}</div>
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
								<div class="user-detail" data-role="${roleText}" id="user_detail_id">${allMessages[i].name}</div>
										<div class="avatar ${caochrole}">
											<img src="${allMessages[i].avatarUrl}" alt="User Avatar" class="rounded-circle" id="user-avatar" />
										</div>
								</div>
									 <div class="chat-message-text">
			                   			 <p class="mb-0">${allMessages[i].text}</p>
			                  		</div>
							</div>

			                  <div class="text-end text-body-secondary mt-1">
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
	console.log("text", text);
	if (!text) return;

	//加入背景效果
	// 👉 加入背景「碰」效果（打出關鍵字時）
	checkAndTriggerEffects(text);

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

// Log out
logoutBtn.addEventListener('click', e => {
	e.preventDefault();
	fetch('/meow-gym/user/logout')
		.then(() => location.href = '/meow-gym/index/index.html');
});


// 背景效果
function boomEffect() {
	confetti({
		particleCount: 160,
		spread: 70,
		origin: { y: 0.8 }
	});
	confetti({
		particleCount: 120,
		spread: 100,
		angle: 60,
		origin: { x: 0, y: 0.9 }
	});
	confetti({
		particleCount: 120,
		spread: 100,
		angle: 120,
		origin: { x: 1, y: 0.9 }
	});
};


const keywordEffects = [
	{
		keys: ['生日快樂', 'happy birthday', 'HBD'],
		fn: boomEffect
	},
	{
		keys: ['恭喜', 'congrats'],
		fn: boomEffect
	},
	{
		keys: ['歡迎新同學', 'welcome'],
		fn: boomEffect
	},
	{
		keys: ['歡迎', 'welcome'],
		fn: boomEffect
	}
];


let lastEffectTime = 0;
const EFFECT_COOLDOWN = 3000; // 3 秒

function checkAndTriggerEffects(text) {
	const now = Date.now();
	if (now - lastEffectTime < EFFECT_COOLDOWN) {
		return; // 冷卻中，不觸發
	}

	const lower = text.toLowerCase();

	for (const group of keywordEffects) {
		const hit = group.keys.some(k => lower.includes(k.toLowerCase()));
		if (hit) {
			lastEffectTime = now;
			group.fn(); // 執行對應效果
			break;      // 找到一組就不再往下檢查
		}
	}
}


tsParticles.load("tsparticles", {
	fullScreen: { enable: false },
	background: { color: "transparent" },
	fpsLimit: 60,
	detectRetina: true,
	particles: {
		number: {
			value: 80,
			density: {
				enable: true,
				area: 800
			}
		},
		color: {
			value: "#ffffff"
		},
		shape: {
			type: "circle"
		},
		opacity: {
			value: 0.9,
			random: true,
			animation: {
				enable: true,
				speed: 0.5,
				minimumValue: 0.3,
				sync: false
			}
		},
		size: {
			value: { min: 2, max: 6 },
			random: true
		},
		move: {
			enable: true,
			direction: "bottom",
			speed: 1.5,
			straight: false,
			outModes: {
				default: "out"
			},
			random: false
		},
		wobble: {
			enable: true,
			distance: 5,
			speed: 3
		}
	}
});
