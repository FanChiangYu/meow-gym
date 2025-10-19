const tbody = document.querySelector('tbody')



fetch('showList')
.then(resp => resp.json())
.then(users => {
	for(const user of users ){
		tbody.innerHTML += `
			<tr>
			 <td>${user.userId}</td>
			 <td>${user.email}</td>
			 <td>${user.createdAt}</td>
			</tr>
		`
	}
})