const swiperReviews = document.getElementById('swiper-reviews');
const ReviewsPreviousBtn = document.getElementById('reviews-previous-btn');
const ReviewsNextBtn = document.getElementById('reviews-next-btn');
const ReviewsSliderPrev = document.querySelector('.swiper-button-prev');
const ReviewsSliderNext = document.querySelector('.swiper-button-next');
const promotionsContent = document.querySelector('#promotions-content');
const cartBtn = document.querySelector('#cart-btn');
const loginBtn = document.querySelector('#login-btn');
const registerBtn = document.querySelector('#register-btn');
const userName = document.querySelector('#user-name');
const userAvatar = document.querySelector('#user-avatar');
const userInfo = document.querySelector('#user-info');
const coachContainer = document.querySelector('#coach-container');
const userCenterBtn = document.querySelector('#user-center-btn');
const logoutBtn = document.querySelector('#logout-btn');
const userCenter = document.querySelector('#user-center');

fetch('/meow-gym/index/loginData')
.then(resp => resp.json())
.then(respbody => {
  if(respbody.successful){
    userName.textContent = `您好! ${respbody.user.name}`; // 修改標籤內使用者名稱
    userAvatar.src = respbody.user.avatarUrl; // 更換img標籤圖片
    userInfo.classList.remove('d-none');
    userCenterBtn.classList.remove('d-none');
    if(respbody.user.role === 1){
      cartBtn.classList.remove('d-none');
    }
  }else{
    loginBtn.classList.remove('d-none');
    registerBtn.classList.remove('d-none');
  }
});

fetch('/meow-gym/index/getPromotions')
.then(resp => resp.json())
.then(cpList => {
  
  cpList.forEach(cp => {
    promotionsContent.innerHTML += `
      <div class="swiper-slide">
        <div class="card h-100">
          <div class="card-body text-body d-flex flex-column justify-content-between h-100">
            <div class="mb-4">
              <a href="/meow-gym/course/browseCourse.html">
                <img
                  src="${cp.imgUrl}"
                  alt="client logo"
                  class="promotions-img img-fluid" />
              </a>
            </div>
          </div>
        </div>
      </div>
    `;
  });
})
.then(() => {
  // swiper carousel
  // Customers reviews
  // -----------------------------------
  if (swiperReviews) {
    new Swiper(swiperReviews, {
      slidesPerView: 1,
      spaceBetween: 5,
      grabCursor: true,
      autoplay: {
        delay: 3000,
        disableOnInteraction: false
      },
      loop: true,
      loopAdditionalSlides: 1,
      navigation: {
        nextEl: '.swiper-button-next',
        prevEl: '.swiper-button-prev'
      },
      pagination: {
        el: ".swiper-pagination",
        clickable: true,
      },
      breakpoints: {
        1200: {
          slidesPerView: 1,
          spaceBetween: 26
        },
        992: {
          slidesPerView: 1,
          spaceBetween: 20
        }
      }
    });
  }
  
  // Reviews slider next and previous
  // -----------------------------------
  // Add click event listener to next button
  ReviewsNextBtn.addEventListener('click', function () {
    ReviewsSliderNext.click();
  });
  ReviewsPreviousBtn.addEventListener('click', function () {
    ReviewsSliderPrev.click();
  });

});

fetch('/meow-gym/index/coachInfo')
.then(resp => resp.json())
.then(profileList => {
  profileList.forEach(profile => {
    coachContainer.innerHTML += `
      <div class="col-lg-4 col-md-6 col-12">
        <div class="card h-100">
          <img class="card-img-top" src="${profile.avatarUrl}" alt="Card image cap">
          <div class="card-body text-center">
            <h5 class="card-title">${profile.coachName}</h5>
            <p class="card-text">
              ${profile.bio}
            </p>
          </div>
        </div>
      </div>
    `;
  });
});


userCenterBtn.addEventListener('click', e => {
  e.preventDefault();
  fetch('/meow-gym/index/userCenter')
  .then(resp => resp.json())
  .then(respbody => {
    location.href = respbody.url;
  });
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
