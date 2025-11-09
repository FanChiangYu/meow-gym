
console.log("chatroom.js loaded");

//not concern for websocket
//const input = document.querySelector(".chat-message"); //歷史訊息
const input = document.querySelector("#chat-message");
const username = document.querySelector(".username");

//const sendbutton = document.querySelector(".send-button");
const sendbutton = document.querySelector("#send-button");

const chatplace = document.querySelector(".chat-place");
const classlist = document.querySelector(".class-list");
const chatlink = document.querySelector(".chat-link");

const courselink = document.querySelector(".course-link");


let loginUser = null;//提前宣告，載入資料後，要把會員資料儲存在這裡
let currentCourseId = null;
let recentchats = null;
let ws = null;


//載入頁面就開始從後端servlet抓資料

//取得login資料
fetch('/meow-gym/chat/userinfo', {
	method: 'GET'
}).then(resp => resp.json())
	.then(body => {
		loginUser = body.loginUser;
		console.log(loginUser); //代表
		username.innerText = `${loginUser.name}，你好！`;
	});


//取得該user所有的courseId >> 搜尋session_users表格 >> 待改
//應該要放在ws onopen內做
fetch('/meow-gym/chat/getusercourseid', {
	method: 'GET'
}).then(resp => resp.json())
	.then(body => {
		console.log(body);
		console.log(body.usercourseid[0].courseId);
		console.log(body.usercourseid.length);

		//新增多種課程......
		for (let i = 0; i < body.usercourseid.length; i++) {
			console.log(body.usercourseid[i].courseId);
			classlist.innerHTML += `
			<li class="chat-contact-list-item mb-0 course-link">
			        <a class="d-flex align-items-center chat-link"  data-courseid="${body.usercourseid[i].courseId}">
			          <div class="flex-shrink-0 avatar avatar-busy">
			            <span class="avatar-initial rounded-circle bg-label-success">CM</span>
			          </div>
			          <div class="chat-contact-info flex-grow-1 ms-4">
			            <div class="d-flex justify-content-between align-items-center">
			              <h6 class="chat-contact-name text-truncate fw-normal m-0">${body.usercourseid[i].courseId}</h6>
			              <small class="chat-contact-list-item-time">1 Day</small>
			            </div>
			            <small class="chat-contact-status text-truncate">If it takes long you can mail inbox
			              user</small>
			          </div>
			        </a>
			      </li>`;

		}

	});

// 分房間發fetch
classlist.addEventListener("click", function (e) {
	const link = e.target.closest(".chat-link");
	const li = e.target.closest("li");

	// 1) 先清掉所有 active（包含先前選到的）
	classlist.querySelectorAll(".chat-contact-list-item.active").forEach(item => { item.classList.remove("active") });

	if (link) {
		e.preventDefault(); // 阻止 a 連結的跳轉
		console.log(e.target);
		console.log(link);

		//background become purple
		console.log(li);

		li.classList.add("active");

		const courseId = link.dataset.courseid;
		currentCourseId = Number(courseId); // [CHANGED] 更新目前選擇的課程 ID	
		console.log("載入課程:", courseId);
		connectChat(courseId);

	}
});

//websocket 處理點擊 courseId 後的聊天紀錄載入
function connectChat(courseId) {
	if (ws) {
		ws.close();
	};
	chatplace.innerHTML = '';
	currentCourseId = courseId; // global to store the currentCourseId.
	console.log(currentCourseId); // show correctly

	ws = new WebSocket(`ws://localhost:8080/meow-gym/chat?courseId=${courseId}`);

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
			chatplace.innerHTML += `
			   <li class="chat-message chat-message-right chat-contact-list-item">
                          <div class="d-flex overflow-hidden">
                            <div class="chat-message-wrapper flex-grow-1">
                              <div class="chat-message-text">
                                <p class="mb-0">${allMessages[i].text}</p>
                              </div>
                              <div class="text-end text-body-secondary mt-1">
                                <i class="icon-base ti tabler-checks icon-16px text-success me-1"></i>
                                <small>10:00 AM</small>
                              </div>
                            </div>
                            <div class="user-avatar flex-shrink-0 ms-4">
                              <div class="avatar avatar-sm">
                                <img src="../assets/img/avatars/1.png" alt="Avatar" class="rounded-circle" />
                              </div>
                            </div>
                          </div>
                        </li>`;


		}
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




