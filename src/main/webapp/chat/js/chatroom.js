
console.log("chatroom.js loaded");

//not concern for websocket
const input = document.querySelector(".chat-message");
const username = document.querySelector(".username");

const sendbutton = document.querySelector(".send-button");
const chatplace = document.querySelector(".chat-place");
const classlist = document.querySelector(".class-list");

const courselink = document.querySelector(".course-link");


let loginUser = null;//提前宣告，載入資料後，要把會員資料儲存在這裡
let currentCourseId = null;
let recentchats = null; //add
let ws = null; //add

// console.log(loginUser);

//不可用相對路徑
//const ws = new WebSocket("ws://localhost:8080/meow-gym/chat");

// ws.addEventListener("open", function () {
// 	//自己送一個訊息到後端
// 	ws.send("Hello from browser!");
// })

//把匿名函式指派給 ws 的 onmessage 屬性
//ws.onmessage = function(e){
//console.log("Server:", e.data);
//};

//也可以寫成
// ws.addEventListener("message", function (e) {
// 	console.log("Server:", e.data);
// });

// ws.addEventListener('close', e => alert('連線已關閉'));
// ws.addEventListener('error', e => alert('連線發生錯誤'));


//載入頁面就開始從後端servlet抓資料

//取得login資料
fetch('/meow-gym/user/info', {
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
			//classlist.innerHTML += `<a href="getchat?courseId=${body.usercourseid[i].courseId}" class="m-4 px-4 text-center border">課程${body.usercourseid[i].courseId}</a>`;
			classlist.innerHTML += `
			    <a href="#" data-courseid="${body.usercourseid[i].courseId}" 
			       class="m-4 px-4 text-center border course-link">
			       課程編號 ${body.usercourseid[i].courseId}
			    </a>`;
		}

	});

// 分房間發fetch
classlist.addEventListener("click", function (e) {
	if (e.target.classList.contains("course-link")) {
		e.preventDefault(); // 阻止 a 連結的跳轉

		const courseId = e.target.dataset.courseid;
		currentCourseId = Number(courseId); // [CHANGED] 更新目前選擇的課程 ID	
		console.log("載入課程:", courseId);
		connectChat(courseId);

		// 發出 fetch 請求抓聊天紀錄
		// fetch(`/meow-gym/chat/chat?courseId=${courseId}`)
		// 	//下面5改用ws處理
		// 	.then(resp => resp.json())
		// 	.then(body => {
		// 		console.log(body);
		// 		chatplace.innerHTML = ""; // 清空輸入過的內容
		// 		console.log(body.chats);
		// 	}).catch(err => console.error("載入聊天紀錄錯誤:", err));

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
				<div>
				<span data-userid="${allMessages[i].userId}" data-name="${allMessages[i].name}"
			       class="m-4 px-4 border">
			       ${allMessages[i].name}:
			    </span>
			    <span data-courseid="${allMessages.courseId}" 
			       class="m-4 px-4 border">
			     ${allMessages[i].text}
			    </span></div>`;
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




