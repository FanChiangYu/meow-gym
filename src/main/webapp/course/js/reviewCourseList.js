// document.addEventListener("DOMContentLoaded", function(){
const tbody = document.querySelector('#course-table-body');

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

function roomName (number){
  switch (number) {
    case 1:
      return "教室A";

    case 2:
      return "教室B";

    case 3:
      return "教室C";
  
    default:
      return "場地未定";
  }
}

// ------------ 載入課程審核表單 -----------------
fetch('reviewCourseList')
	.then(resp => resp.json())
	.then(courses => {

		for (let course of courses) {

			tbody.innerHTML += `
      <tr>
        <td>
          <span class="text-heading">${course.courseId}</span>
        </td>
        <td>
          <span class="text-heading">${course.title}</span>
        </td>
        <td>
          <span class="text-truncate d-flex align-items-center text-heading">
            <i class="icon-base ti tabler-user icon-md text-success me-2"></i>
            Maintainer
          </span>
        </td>
        <td>
          <span class="text-heading">${course.category}</span>
        </td>
        <td>
          <span class="text-heading">${course.sessionQuota}堂</span>
        </td>
        <td>
          <span class="text-heading">${course.coursePrice}</span>
        </td>
        <td>
          <span class="badge ${approvalLabel(course.approvalStatus)}">${course.approvalStatus}</span>
        </td>
        <td>
          <button onclick="auditById(${course.courseId})" class="btn rounded-pill btn-primary waves-effect waves-light">審核</button>
        </td>
      </tr>
			`;
		}
	});

  // ------------ 審核課程 -----------------
  function auditById(id) {

    fetch('reviewCourseList', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          courseId: id
        }),
      })
      .then(resp => resp.json())
      .then(course => {

        if(course.successful){

          Swal.fire({
            title: course.title,
            html: `
              <div style="text-align:left">
                <p>課程ID: ${course.courseId}</p>
                <p>教練: 王小美</p>
                <p>類別: ${course.category}</p>
                <p>堂數: ${course.sessionQuota}堂</p>
                <p>教室: ${roomName(course.roomId)}</p>
                <p>開始日期: ${new Date(course.dateStart).toLocaleDateString('zh-TW')}</p>
                <p>結束日期: ${new Date(course.dateEnd).toLocaleDateString('zh-TW')}</p>
                <p>課程介紹:</p>
                <p>${course.description}</p>
                <p>課程規則1: 星期一 9:00 ~ 10:00</p>
                <p>課程規則2: 星期三 17:00 ~ 18:00</p>
                <p>審核狀態: <span class="badge ${approvalLabel(course.approvalStatus)}">${course.approvalStatus}</span></p>
                <p>課程定價: <strong>${course.coursePrice}</strong></p>
              </div>
            `,
            // imageUrl: '/meow-gym/getImg?file=' + course.imgUrl,
            imageUrl: course.imgUrl,
            imageWidth: 500,
            // imageHeight: 500,
            imageAlt: '課程圖片',
            icon: 'info',
            showCancelButton: true,
            showDenyButton: true,
            confirmButtonText: '通過',
            denyButtonText: '不通過',
            cancelButtonText: '取消',
            reverseButtons: true, 
            customClass: {
              confirmButton: 'btn btn-success',
              cancelButton: 'btn btn-gray me-12',
              denyButton: 'btn btn-danger me-12'
            }
          }).then(result => {

            if (result.isConfirmed) {
              console.log('資料已刪除');
              fetch('auditCourse', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                  courseId: id,
                  approvalStatus: '通過'
                }),
              })
              .then(() => location.reload());
            } else if(result.isDenied) {
              console.log('不通過');
              fetch('auditCourse', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                  courseId: id,
                  approvalStatus: '不通過'
                }),
              })
              .then(() => location.reload());
            } else {
              console.log('取消');
            }
      
          });

        }else{

          Swal.fire({
            title: '錯誤',
            text: '載入失敗',
            icon: 'error',
            target: document.body 
          });

        }
        
      });

  }

// });