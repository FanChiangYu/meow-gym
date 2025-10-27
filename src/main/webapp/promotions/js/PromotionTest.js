const tbody = document.querySelector('tbody')



fetch('promotionTest')
.then(resp => resp.json())
.then(promotion => {
	for(const promotions of promotion ){
		tbody.innerHTML += `
			<tr>
			 <td>${promotions.courseId}</td>
			 <td>${promotions.promoId}</td>
			 <td>${promotions.promoPrice}</td>
			 <td>${promotions.dateStart}</td>
			 <td>${promotions.dateEnd}</td>
			 <td>${promotions.imgUrl}</td>
			</tr>
		`
	} 
})