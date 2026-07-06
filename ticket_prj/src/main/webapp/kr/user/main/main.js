(function() {
			var slider = document.getElementById('rankSlider');
			var track = document.getElementById('rankSliderTrack');
			if (!slider || !track) {
				return;
			}

			var slides = track.querySelectorAll('.rank-slide');
			var teamSelects = slider.querySelectorAll('.rank-team-select');
			if (slides.length === 0) {
				return;
			}

			var current = 0;
			var timer = null;

			function showRank(index) {
				current = (index + slides.length) % slides.length;
				track.style.transform = 'translateY(-'
						+ (current * slider.clientHeight) + 'px)';
			}

			function stopAutoSlide() {
				if (timer !== null) {
					clearInterval(timer);
					timer = null;
				}
			}

			function startAutoSlide() {
				stopAutoSlide();
				if (slides.length <= 1) {
					return;
				}
				timer = setInterval(function() {
					showRank(current + 1);
				}, 3200);
			}

			for (var i = 0; i < teamSelects.length; i++) {
				teamSelects[i].addEventListener('change', function() {
					showRank(parseInt(this.value, 10));
				});
			}

			slider.addEventListener('mouseenter', stopAutoSlide);
			slider.addEventListener('mouseleave', startAutoSlide);
			window.addEventListener('resize', function() {
				showRank(current);
			});

			showRank(0);
			startAutoSlide();
		})();

window.addEventListener("DOMContentLoaded", function() {

		const slides = document.querySelectorAll(".hero-slider > .hero-slide");
		const dots = document.querySelectorAll(".hero-dot");
		const slider = document.getElementById("heroSlider");

		let currentSlide = 0;
		let slideTimer = null;
		let isSliding = false;

		function showSlide(index) {
			if (slides.length === 0 || isSliding || index === currentSlide) {
				return;
			}

			isSliding = true;
			const previousSlide = slides[currentSlide];
			const nextSlide = slides[index];

			previousSlide.classList.remove("active");
			previousSlide.classList.add("leaving");
			nextSlide.classList.remove("leaving");
			nextSlide.classList.add("active");

			if (dots[currentSlide]) {
				dots[currentSlide].classList.remove("active");
			}

			if (dots[index]) {
				dots[index].classList.add("active");
			}

			currentSlide = index;

			window.setTimeout(function() {
				previousSlide.classList.add("resetting");
				previousSlide.classList.remove("leaving");
				void previousSlide.offsetWidth;
				previousSlide.classList.remove("resetting");
				isSliding = false;
			}, 760);
		}

		function nextSlide() {
			if (slides.length <= 1) {
				return;
			}

			let nextIndex = currentSlide + 1;

			if (nextIndex >= slides.length) {
				nextIndex = 0;
			}

			showSlide(nextIndex);
		}

		for (let i = 0; i < dots.length; i++) {
			dots[i].addEventListener("click", function() {
				showSlide(i);
				restartAutoSlide();
			});
		}

		function stopAutoSlide() {
			if (slideTimer !== null) {
				clearInterval(slideTimer);
				slideTimer = null;
			}
		}

		function startAutoSlide() {
			stopAutoSlide();
			if (slides.length > 1) {
				slideTimer = setInterval(nextSlide, 5000);
			}
		}

		function restartAutoSlide() {
			startAutoSlide();
		}

		if (slider) {
			slider.addEventListener("mouseenter", stopAutoSlide);
			slider.addEventListener("mouseleave", startAutoSlide);
		}

		startAutoSlide();
	});
