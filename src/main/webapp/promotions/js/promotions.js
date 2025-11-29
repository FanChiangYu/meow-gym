//以下自己編寫的js
const courseTitle = document.querySelector('#courseTitle');
const coursePrice = document.querySelector('#coursePrice');
const dateStart = document.querySelector('#date-start');
const dateEnd = document.querySelector('#date-end');
const courseImg = document.querySelector('#course-img');
const promoPrice = document.querySelector('#promo-price');

init();
function init() {
	const title = sessionStorage.getItem('title');
	const price = sessionStorage.getItem('price');
	
	courseTitle.textContent = title;
	coursePrice.value = price;
}

document.querySelector('#apply-btn').addEventListener('click', () => {
    const fr = new FileReader();
    fr.addEventListener('load', e => {
        const imgBase64 = e.target.result.split(',')[1];
        fetch('verify', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            courseId: sessionStorage.getItem('id'),
            promoPrice: promoPrice.value,
            dateStart: dateStart.value.replaceAll('-', '/'),
            dateEnd: dateEnd.value.replaceAll('-', '/'),
            imgBase64,
            filename: courseImg.files[0].name
        })
    })
       .then(resp => resp.json())
       .then(body => {
            if (body.successful) {
                Swal.fire({
                    icon: 'success',
                    title: '完成',
                    text: '促銷活動新增成功。',
                }).then(() => {
                    location.href = 'reviewPomotionsList.html';
                });
            } else {
                Swal.fire({
                    icon: 'error',
                    title: '申請失敗',
                    text: body.message
                });
            }
        });
    });
    fr.readAsDataURL(courseImg.files[0]);
});

document.querySelector('#cancel-btn').addEventListener('click', () => {
  location.href = '/meow-gym/promotions/reviewPomotionsList.html';
});
