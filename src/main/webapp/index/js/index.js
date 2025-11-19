swiperReviews = document.getElementById('swiper-reviews');
ReviewsPreviousBtn = document.getElementById('reviews-previous-btn');
ReviewsNextBtn = document.getElementById('reviews-next-btn');
ReviewsSliderPrev = document.querySelector('.swiper-button-prev');
ReviewsSliderNext = document.querySelector('.swiper-button-next');
promotionsContent = document.querySelector('#promotions-content');
cartBtn = document.querySelector('#cart-btn');
loginBtn = document.querySelector('#login-btn');
registerBtn = document.querySelector('#register-btn');
userName = document.querySelector('#user-name');
userAvatar = document.querySelector('#user-avatar');
userInfo = document.querySelector('#user-info');
coachContainer = document.querySelector('#coach-container');

fetch('/meow-gym/index/loginData')
.then(resp => resp.json())
.then(respbody => {
  if(respbody.successful){
    userName.textContent = `您好! ${respbody.user.name}`; // 修改標籤內使用者名稱
    userAvatar.src = respbody.user.avatarUrl; // 更換img標籤圖片
    userInfo.classList.remove('d-none');
    if(respbody.user.role === 1){
      cartBtn.classList.remove('d-none');
    }
  }else{
    loginBtn.classList.remove('d-none');
    registerBtn.classList.remove('d-none');
  }
});

fetch('getPromotions')
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

fetch('coachInfo')
.then(resp => resp.json())
.then(profileList => {
  profileList.forEach(profile => {
    coachContainer.innerHTML += `
      <div class="col-lg-4 col-md-6 col-12">
        <div class="card h-100">
          <img class="card-img-top" src="${profile.avatarUrl}" alt="Card image cap">
          <div class="card-body text-center"">
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

