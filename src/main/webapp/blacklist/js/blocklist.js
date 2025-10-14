const userId = document.querySelector('#isbanned');
const email = document.querySelector('#email');

document.querySelector('button').addEventListener('click', function(){
    let len  = userId.value.length


    if( len < 5 || len > 10 ){
        alert('使用者ID必須為5~10碼!!');
        return;
    }
    len  = email.value.length
    if( email.value == "" ){
        alert('請輸入信箱');
        return;
    }


    //後端接收前端
    fetch('showList', {
        method: 'POST',
        headers:{
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            userId: isbanned.value,
            email: email.value
        })
    })
    //拿到後端回來的
    .then(resp => resp.json())
    .then(body => {
        alert(body.message);
    });

});