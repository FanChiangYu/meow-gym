swiperReviews = document.getElementById('swiper-reviews');
ReviewsPreviousBtn = document.getElementById('reviews-previous-btn');
ReviewsNextBtn = document.getElementById('reviews-next-btn');
ReviewsSliderPrev = document.querySelector('.swiper-button-prev');
ReviewsSliderNext = document.querySelector('.swiper-button-next');
promotionsContent = document.querySelector('#promotions-content');


fetch('getPromotions')
.then(resp => resp.json())
.then(cpList => {
  
  cpList.forEach(cp => {
    promotionsContent.innerHTML += `
      <div class="swiper-slide">
        <div class="card h-100">
          <div class="card-body text-body d-flex flex-column justify-content-between h-100">
            <div class="mb-4">
              <a href="#">
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


