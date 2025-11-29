
//以下自己編寫的js
let courses;
(() => {
	const tbody = document.querySelector('tbody');
	fetch('getAll')
		.then(resp => resp.json())
		.then(courseList => {
			courses = courseList;
			for (let c of courseList) {

        if(c.approvalStatus != '通過'){
          continue;
        }

				const p = c.coursePromos[0];
        var buttonHtml = '';
        if(p){
          buttonHtml = `
            <td>
							<button id="delete-btn" type="button" class="btn btn-primary" onclick="removePromotion(${c.courseId})">刪除</button>
						</td>
						<td>
							<button id="apply-btn" type="button" class="btn btn-primary" onclick="addPromotion(${c.courseId}, '${c.title}', ${c.coursePrice})" disabled>新增</button>
						</td>
          `;
        }else{
          buttonHtml = `
            <td>
							<button id="delete-btn" type="button" class="btn btn-primary" onclick="removePromotion(${c.courseId})" disabled>刪除</button>
						</td>
						<td>
							<button id="apply-btn" type="button" class="btn btn-primary" onclick="addPromotion(${c.courseId}, '${c.title}', ${c.coursePrice})">新增</button>
						</td>
          `;
        }
        
				tbody.innerHTML += `
					<tr>
						<td>${c.courseId}</<td>
						<td>${c.title}</<td>
						<td>${p ? p.promoPrice : ''}</<td>
						<td>${p ? p.dateStart : ''}~${p ? p.dateEnd : ''}</td>
						<td>${c.coursePrice}</td>
						${buttonHtml}
					</tr>
				`;
			}
		});
})();

function addPromotion(id, title, price) {
	Swal.fire({
    title: '(是)(否)新增促銷活動？',
    icon: 'question',
    showCancelButton: true,
    confirmButtonText: '是',
    cancelButtonText: '否',
  }).then((result) => {
    if (result.isConfirmed) {
      sessionStorage.setItem('id', id);
      sessionStorage.setItem('title', title);
      sessionStorage.setItem('price', price);
      location.href = 'promotions.html';
    }
  });
}

function removePromotion(courseId) {
    Swal.fire({
        title: "確定刪除促銷？",
        text: "請確認是否要刪除資料？",
        icon: "warning",
        showCancelButton: true,
        confirmButtonText: "是",
        cancelButtonText: "否"
    }).then(result => {
        if (!result.isConfirmed) return; 

        fetch("delete", {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({ courseId: courseId })
        })
        .then(resp => {
            if (!resp.ok) {
                return resp.json().then(err => ({
                    successful: false,
                    message: err.message || "系統錯誤"
                }));
            }
            return resp.json();
        })
        .then(response => {
            if (response.successful) {
                Swal.fire({
                    title: '刪除促銷成功!!',
                    text: response.message,
                    icon: 'success',
                    target: document.body
                })
                .then(() => location.reload());;

            } else {
                Swal.fire({
                    title: '刪除促銷失敗',
                    text: response.message,
                    icon: 'error',
                    target: document.body
                })
                .then(() => location.reload());;
            }
        });
    });
}

