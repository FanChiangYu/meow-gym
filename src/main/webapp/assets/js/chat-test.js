console.log("into chat-test.js");

const sendButton = document.querySelector(".send-button");

sendButton.addEventListener("click", function () {
  const msg = document.querySelector(".input-message").value.trim();

     fetch("addChat", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        courseId: 1,
        userId: 1,
        coachId: 1,
        content: msg,
      }),
    })
    .then(resp=>{
    	console.log(resp);
    });


 // 測試 Servlet (GET)
    //const response = fetch("addChat");

    //if (!response.ok) {
      //throw new Error("HTTP error " + response.status);
    //}

    // 取得文字或 JSON
    //const text = response.text(); // 如果後端回 JSON，可改 response.json()
    //console.log("GET response:", text);

});



