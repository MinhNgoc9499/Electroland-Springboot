let index = 0;
const imgNumber = document.querySelectorAll('.slider-content-left-top img');
const imgNumberLi = document.querySelectorAll('.slider-content-left-bottom li');
let autoSlide; 

document.addEventListener('DOMContentLoaded', () => {
    const leftbtn = document.querySelector('.fa-solid.fa-arrow-left');
    const rightbtn = document.querySelector('.fa-solid.fa-arrow-right');
    
    function startAutoSlide() {
        autoSlide = setInterval(() => {
            index = (index + 1) % imgNumber.length;
            changeImage();
        }, 5000);
    }

    function stopAutoSlide() {
        clearInterval(autoSlide);
    }

    function pauseAutoSlide() {
        stopAutoSlide();  
        setTimeout(startAutoSlide, 5000); 
    }

    rightbtn.addEventListener('click', function() {
        index = (index + 1) % imgNumber.length; 
        changeImage();
        pauseAutoSlide();
    });

    leftbtn.addEventListener('click', function() {
        index = (index - 1 + imgNumber.length) % imgNumber.length; 
        changeImage();
        pauseAutoSlide();
    });

    imgNumberLi.forEach(function(image, liIndex) {
        image.addEventListener('click', function() {
            if (liIndex < imgNumber.length) { 
                index = liIndex;
                changeImage();
                pauseAutoSlide();
            }
        });
    });

    startAutoSlide();
});

function changeImage() {
    document.querySelector('.slider-content-left-top').style.right = index * 105 + "%";
    removeactive();
    if (imgNumberLi[index]) {
        imgNumberLi[index].classList.add("active");
    }
}

function removeactive() {
    const imgactive = document.querySelector('.active');
    if (imgactive) {
        imgactive.classList.remove("active");
    }
}

function countdownTimer() {
    const countDownDate = new Date("Nov 15, 2024 18:00:00").getTime();

    const timer = setInterval(function() {
        const now = new Date().getTime();
        const distance = countDownDate - now;

        const hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
        const minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
        const seconds = Math.floor((distance % (1000 * 60)) / 1000);

        document.getElementById("hours").innerHTML = hours < 10 ? '0' + hours : hours;
        document.getElementById("minutes").innerHTML = minutes < 10 ? '0' + minutes : minutes;
        document.getElementById("seconds").innerHTML = seconds < 10 ? '0' + seconds : seconds;

        if (distance < 0) {
            clearInterval(timer);
            document.getElementById("hours").innerHTML = "00";
            document.getElementById("minutes").innerHTML = "00";
            document.getElementById("seconds").innerHTML = "00";
            document.querySelector('.timer-title').innerHTML = "Sự kiện đã kết thúc!";
        }
    }, 1000);
}

window.onload = countdownTimer;
