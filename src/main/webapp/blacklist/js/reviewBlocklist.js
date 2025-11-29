//以下自己編寫的js
const tbody = document.querySelector('tbody');

fetch('reviewBlocklist')
  .then(resp => resp.json())
  .then(users => {
    for (const user of users) {
      const bannedText = user.isBanned ? '**黑名單**' : '白名單';

      var buttonHtml = '';
      if(user.isBanned){
        buttonHtml = `
          <td>
            <button id="apply-btn" type="button" class="btn btn-primary"
                    onclick="addBlockMember(${user.userId})" disabled>
              加入
            </button>
          </td>
          <td>
           <button id="apply-btn" type="button" class="btn btn-primary"
                    onclick="removeBlockMember(${user.userId})">
              移除
            </button>
          </td>
        `;
      }else{
        buttonHtml = `
          <td>
            <button id="apply-btn" type="button" class="btn btn-primary"
                    onclick="addBlockMember(${user.userId})">
              加入
            </button>
          </td>
          <td>
           <button id="apply-btn" type="button" class="btn btn-primary"
                    onclick="removeBlockMember(${user.userId})" disabled>
              移除
            </button>
          </td>
        `;
      }

      if(user.role === 1){
        tbody.innerHTML += `
        <tr>
          <td>${user.userId}</td>
          <td>${user.email}</td>
          <td>${user.createdAt}</td>
          <td>${bannedText}</td>
          ${buttonHtml}
        </tr>
      `;
      }
    }
  });

  function addBlockMember(userId) {
  Swal.fire({
    title: '(是)(否)加入黑名單？',
    icon: 'question',
    showCancelButton: true,
    confirmButtonText: '是',
    cancelButtonText: '否',
  }).then((result) => {
    if (result.isConfirmed) {
      fetch('block',{
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          userId,
         })
      })
      .then(resp => resp.json())
      .then(respbody => {
        if(respbody.successful){
          Swal.fire({
            title: '成功',
            text: '加入成功',
            icon: 'success',
            target: document.body 
          })
          .then(() => location.reload());
        }else{
          Swal.fire({
            title: '錯誤',
            text: '加入失敗',
            icon: 'error',
            target: document.body 
          })
        }
      })
    }
  });
}
function removeBlockMember(userId) {
  Swal.fire({
    title: '(是)(否)移除黑名單？',
    icon: 'question',
    showCancelButton: true,
    confirmButtonText: '是',
    cancelButtonText: '否',
  }).then((result) => {
    if (result.isConfirmed) {
      fetch('unlock',{
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          userId,
         })
      })
      .then(resp => resp.json())
      .then(respbody => {
        if(respbody.successful){
          Swal.fire({
            title: '成功',
            text: '移除成功',
            icon: 'success',
            target: document.body 
          })
          .then(() => location.reload());
        }else{
          Swal.fire({
            title: '錯誤',
            text: '移除失敗',
            icon: 'error',
            target: document.body 
          })
        }
      })
    }
  });
}
