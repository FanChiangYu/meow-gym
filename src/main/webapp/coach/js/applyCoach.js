'use strict';

const userMenu = document.querySelector('#user-menu');
const coachMenu = document.querySelector('#coach-menu');
const adminMenu = document.querySelector('#admin-menu');
const userName = document.querySelector('#user-name');
const avatarImg = document.querySelector('#user-avatar');
const shoppingCart = document.querySelector('#shopping-cart');
const description = document.querySelector('#description');
const school = document.querySelector('#school');
const degree = document.querySelector('#degree');
const company = document.querySelector('#company');
const title = document.querySelector('#title');
const startDate = document.querySelector('#start-date');
const endDate = document.querySelector('#end-date');
const certificateName = document.querySelector('#certificate');
const certImg = document.querySelector('#cert-img');
const changeBtn = document.querySelector('#change-btn');
const approvalStatus = document.querySelector('#status');
const imgDownload = document.querySelector('#img-download');
const courseManage = document.querySelector('#course-manage');
const courseApply = document.querySelector('#course-apply');
const logoutBtn = document.querySelector('#logout-btn');
const userCenter = document.querySelector('#user-center');
var coachId; 

function switchMenu (role) {
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
  if(respbody.successful){
    if(respbody.profile.approvalStatus == '通過'){
      courseManage.classList.remove('d-none'); 
      courseApply.classList.remove('d-none'); 
    }
    switchMenu(respbody.user.role); // 切換側邊欄: 1 -> 一般會員、2 -> 教練、3 -> 管理者
    userName.textContent = respbody.user.name; // 修改標籤內使用者名稱
    avatarImg.src = respbody.user.avatarUrl; // 更換img標籤圖片
  }else{
    Swal.fire({
      title: '錯誤',
      text: '請先登入',
      icon: 'error',
      target: document.body 
    })
    .then(() => location.href = '/meow-gym/user/login.html');
  }
});

function approvalLabel (status) {
  switch(status){
    case "待審核":
      return "bg-label-info";

    case "通過":
      return "bg-label-success";

    case "不通過":
      return "bg-label-danger";

    default:
      return "bg-label-secondary"
  }
}

fetch('apply')
.then(resp => resp.json())
.then(respbody => {
  approvalStatus.textContent = respbody.profile.approvalStatus;
  approvalStatus.classList.add(approvalLabel(approvalStatus.textContent));
  description.value = respbody.profile.bio;
  school.value = respbody.education.school;
  degree.value = respbody.education.degree;
  company.value = respbody.experience.company;
  title.value = respbody.experience.title;
  startDate.value = respbody.experience.startDate;
  endDate.value = respbody.experience.endDate;
  certificateName.value = respbody.certificate.name;
  coachId = respbody.profile.coachId;


  if(respbody.certificate.fileUrl != ''){
    const fileName = respbody.certificate.fileUrl.split('/').pop();
    imgDownload.innerHTML = `
    <a href="${respbody.certificate.fileUrl}" class="card-link" download>${fileName}</a>
    `;
  }

  if(respbody.profile.approvalStatus === '通過'){
    description.disabled = true;
    school.disabled = true;
    degree.disabled = true;
    company.disabled = true;
    title.disabled = true;
    startDate.disabled = true;
    endDate.disabled = true;
    certificateName.disabled = true;
    certImg.disabled = true;
    changeBtn.disabled = true;
  }

});

changeBtn.addEventListener('click', function(){

  if(!startDate.value || !endDate.value){
    Swal.fire({
      title: '錯誤',
      text: '請選擇任職日期',
      icon: 'error',
      target: document.body 
    });
    return;
  }

  const profile = {
    coachId,
    bio: description.value
  };

  const education = {
    coachId,
    school: school.value,
    degree: degree.value
  };

  const experience = {
    coachId,
    company: company.value,
    title: title.value,
    startDate: startDate.value.replaceAll('-', '/'),
    endDate: endDate.value.replaceAll('-', '/')
  };

  const certificate = {
    coachId,
    name: certificateName.value
  };

  if(!certImg.files || certImg.files.length === 0){

    Swal.fire({
      title: '確認',
      text: '資料變更？',
      icon: 'info',
      showCancelButton: true,
      reverseButtons: true, 
      confirmButtonText: '確定',
      cancelButtonText: '取消',
      target: document.body,
      customClass: {
        confirmButton: 'btn btn-success',
        cancelButton: 'btn btn-danger me-12',
      }
    })
    .then(result => {
      if (result.isConfirmed) {
        
        fetch('apply', {
          method: 'PUT',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({
            profile,
            education,
            experience,
            certificate
          })
        })
        .then(resp => resp.json())
        .then(respbody => {
          if(respbody.successful){
            Swal.fire({
              title: '成功',
              text: '資料變更成功',
              icon: 'success',
              target: document.body 
            })
            .then(() => location.reload());
          }else{
            Swal.fire({
              title: '錯誤',
              text: '資料變更失敗',
              icon: 'error',
              target: document.body 
            });
          }
        });
      }
    });

  }else{

    const fr = new FileReader();
    fr.addEventListener('load', e => {
      const imgBase64 = e.target.result.split(',')[1];

      Swal.fire({
       title: '確認',
       text: '資料變更？',
       icon: 'info',
       showCancelButton: true,
       reverseButtons: true, 
       confirmButtonText: '確定',
       cancelButtonText: '取消',
       target: document.body,
       customClass: {
         confirmButton: 'btn btn-success',
         cancelButton: 'btn btn-danger me-12',
       }
     })
     .then(result => {
       if (result.isConfirmed) {
         
         fetch('apply', {
           method: 'PUT',
           headers: { 'Content-Type': 'application/json' },
           body: JSON.stringify({
             profile,
             education,
             experience,
             certificate,
             imgBase64,
             fileName: certImg.files[0].name
           })
         })
         .then(resp => resp.json())
         .then(respbody => {
           if(respbody.successful){
             Swal.fire({
               title: '成功',
               text: '資料變更成功',
               icon: 'success',
               target: document.body 
             })
             .then(() => location.reload());
           }else{
             Swal.fire({
               title: '錯誤',
               text: '資料變更失敗',
               icon: 'error',
               target: document.body 
             });
           }
         });
       }
     });

    });
    fr.readAsDataURL(certImg.files[0]);

  }

});


logoutBtn.addEventListener('click', e => {
  e.preventDefault();
  fetch('/meow-gym/user/logout')
  .then(()=>location.href = '/meow-gym/index/index.html');
});

userCenter.addEventListener('click', e => {
  e.preventDefault();
  fetch('/meow-gym/index/userCenter')
  .then(resp => resp.json())
  .then(respbody => {
    location.href = respbody.url;
  });
});