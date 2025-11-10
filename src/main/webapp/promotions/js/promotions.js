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
                location.href = 'reviewPomotionsList.html';
            } else {
            	alert(body.message);
            }
        });
    });
    fr.readAsDataURL(courseImg.files[0]);
});