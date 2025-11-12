let courses;
(() => {
	const tbody = document.querySelector('tbody');
	fetch('getAll')
		.then(resp => resp.json())
		.then(courseList => {
			courses = courseList;
			for (let c of courseList) {
				const p = c.coursePromos[0];
				tbody.innerHTML += `
					<tr>
						<td>${c.courseId}</<td>
						<td>${c.title}</<td>
						<td>${p ? p.promoPrice : ''}</<td>
						<td>${p ? p.dateStart : ''}~${p ? p.dateEnd : ''}</td>
						<td>${c.coursePrice}</td>
						<td>
							<button id="delete-btn" type="button" class="btn btn-primary" onclick="removePromotion(${c.courseId})">刪除</button>
						</td>
						<td>
							<button id="apply-btn" type="button" class="btn btn-primary" onclick="addPromotion(${c.courseId}, '${c.title}', ${c.coursePrice})">編輯</button>
						</td>
					</tr>
				`;
			}
		});
})();

function addPromotion(id, title, price) {
	if (confirm('新增促銷活動?')) {
		sessionStorage.setItem('id', id);
		sessionStorage.setItem('title', title);
		sessionStorage.setItem('price', price);
		location.href = 'promotions.html';
	}
}

function removePromotion(courseId){
	if(confirm('是否有(確認)資料要刪除?')){
		fetch("delete",{
			method:'POST',
			headers: {'Content-Type': 'application/json'},
			body: JSON.stringify({
				courseId:courseId
			})
		})
		.then(resp => resp.json())
		.then(response => {
			if(response.successful){
				Swal.fire({
	            title: '刪除促銷成功!!',
	            text: response.message,
	            icon: 'success',
	            target: document.body
          });
				setTimeout(function() {
				  location.reload();
				}, 2000);
			}else{
				Swal.fire({
	            title: '新增促銷失敗',
	            text: response.message,
	            icon: 'error',
	            target: document.body
          });
         		setTimeout(function() {
				  location.reload();
				}, 2000);
			}
		})
	}
	
}
