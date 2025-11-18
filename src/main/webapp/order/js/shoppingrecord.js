const tbody = document.querySelector('tbody');

fetch('manage')
	.then(resp => resp.json())
	.then(members => {
		for (let member of members) {
			tbody.innerHTML += `
				<tr>
					<td>${member.id}</td>
					<td>${member.username}</td>
					<td>${member.password}</td>
					<td>${member.nickname}</td>
					<td>${member.pass}</td>
					<td>${member.creator}</td>
					<td>${member.createdAt}</td>
					<td>${member.updater}</td>
					<td>${member.lastUpdatedAt}</td>
					<td>
						<button onclick="removeById(${member.id})">刪除</button>
					</td>
				</tr>
			`;
		}
	});


function removeById(id) {
	if (confirm(`確認刪除${id}號會員？`)) {
		fetch('manage', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({ id })
		})
			.then(resp => location.reload());
	}
}