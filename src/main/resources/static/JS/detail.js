//Cái slide chuyển hình sản phẩm
var swiper = new Swiper('.mySwiper', {
    loop: true,
    spaceBetween: 10,
    slidesPerView: 7, //sau 7 hình thì nó sẽ nhảy 100% qua anh có tìm sản phẩm thì bỏ 14 bức vô nha :)))
    freeMode: true,
});

var swiper2 = new Swiper('.mySwiper2', {
    loop: true,
    spaceBetween: 10,
    navigation: {
        nextEl: '.swiper-button-next',
        prevEl: '.swiper-button-prev',
    },
    thumbs: {
        swiper: swiper,
    },
});

//Mấy cái ảnh nhỏ dưới cái slider nhấp vào là chuyển hình lên hình bự
const thumbnailSlides = document.querySelectorAll('.thumbnail-slider .swiper-slide img');
const mainImage = document.querySelector('.mySwiper2 .swiper-slide img');
thumbnailSlides.forEach((thumbnail) => {
    thumbnail.addEventListener('click', () => {
        mainImage.src = thumbnail.src;
    });
});

//Cái bấm sổ xuống của mô tả sản phẩm
function toggleExtraInfo() {
    const extraInfo = document.querySelector('.extra-info');
    const button = document.querySelector('.btn-dropdown');
    extraInfo.classList.toggle('visible');

    if (extraInfo.style.display === 'none' || extraInfo.style.display === '') {
        extraInfo.style.display = 'block';
        button.innerHTML = 'Thu gọn <i class="fa-solid fa-caret-up"></i>';
    } else {
        extraInfo.style.display = 'none';
        button.innerHTML = 'Xem thêm <i class="fa-solid fa-caret-down"></i>';
    }
}

//Cũng nó mà cho cái thông số kỹ thuật em nghĩ dùng chung được mà chưa thử
function toggleSpecificationDetails() {
    const extraInfo = document.querySelector('.extra-info1');
    const button = document.querySelector('.btn-dropdown1');
    extraInfo.classList.toggle('visible');

    if (extraInfo.classList.contains('hidden')) {
        extraInfo.classList.remove('hidden');
        button.innerHTML = 'Thu gọn <i class="fa-solid fa-caret-up"></i>';
    } else {
        extraInfo.classList.add('hidden');
        button.innerHTML = 'Xem thêm <i class="fa-solid fa-caret-down"></i>';
    }
}

//Đánh giá sản phẩm
const stars = document.querySelectorAll('.star');
const averageStars = document.querySelectorAll('.average-star');
//Dữ liệu để test fill lên bar
const ratingCounts = {
    1: 0,
    2: 0,
    3: 0,
    4: 0,
    5: 0
};

//Tính trung bình với tính tổng đánh giá
function calculateTotals() {
    let totalRatings = 0;
    let totalScore = 0;

    for (let i = 1; i <= 5; i++) {
        totalRatings += ratingCounts[i];
        totalScore += i * ratingCounts[i];
    }

    return { totalRatings, totalScore };
}

//Cập nhật
function updateDisplay() {
    const { totalRatings, totalScore } = calculateTotals();
    const averageScore = totalRatings > 0 ? (totalScore / totalRatings).toFixed(1) : 0;

    document.getElementById('avg-score').textContent = averageScore;
    document.getElementById('total-ratings').textContent = totalRatings;

    averageStars.forEach((star, index) => {
        star.classList.remove('selected', 'half');
        if (index < Math.floor(averageScore)) {
            star.classList.add('selected'); 
        } else if (index === Math.floor(averageScore) && averageScore % 1 !== 0) {
            star.classList.add('half');
        }
    });
}

//Cũng cập nhật
function updateRatingCounts() {
    const totalRatings = Object.values(ratingCounts).reduce((a, b) => a + b, 0);
    let totalScore = 0;

    for (let i = 1; i <= 5; i++) {
        const countElement = document.getElementById(`count${i}`);
        const progressBar = document.getElementById(`bar${i}`);
        
        if (countElement) {
            countElement.textContent = ratingCounts[i];
            const percentage = totalRatings > 0 ? (ratingCounts[i] / totalRatings) * 100 : 0;
            progressBar.style.width = `${percentage}%`;
            totalScore += i * ratingCounts[i];
        }
    }

    const averageScore = totalRatings > 0 ? (totalScore / totalRatings).toFixed(2) : 0;
    updateDisplay(); 
}

//Nhấn vô sao là nó sáng lên
stars.forEach((star, index) => {
    star.addEventListener('click', () => {
        const value = index + 1;
        ratingCounts[value]++;
        stars.forEach(s => {
            s.classList.remove('selected');
        });
        for (let i = 0; i < value; i++) {
            stars[i].classList.add('selected');
        }
        updateRatingCounts();
    });

    //Di chuột vô là sao sáng lên
    star.addEventListener('mouseover', () => {
        stars.forEach((s, i) => {
            if (i <= index) {
                s.classList.add('hover');
            } else {
                s.classList.remove('hover');
            }
        });
    });

    //DI ra hết sáng
    star.addEventListener('mouseout', () => {
        stars.forEach(s => {
            s.classList.remove('hover');
        });
        const selectedValue = Object.keys(ratingCounts).find(key => ratingCounts[key] > 0);
        if (selectedValue) {
            for (let i = 0; i < selectedValue; i++) {
                stars[i].classList.add('selected');
            }
        }
    });
});

updateRatingCounts(); 

//Cái upload file
const form = document.querySelector("form"),
fileInput = document.querySelector(".file-input"),
progressArea = document.querySelector(".progress-area"),
uploadedArea = document.querySelector(".uploaded-area");
form.addEventListener("click", () =>{
  fileInput.click();
});
fileInput.onchange = ({target})=>{
  let file = target.files[0];
  if(file){
    let fileName = file.name;
    if(fileName.length >= 12){
      let splitName = fileName.split('.');
      fileName = splitName[0].substring(0, 13) + "... ." + splitName[1];
    }
    uploadFile(fileName);
  }
}
function uploadFile(name){
  let xhr = new XMLHttpRequest();
  xhr.open("POST", "php/upload.php");
  xhr.upload.addEventListener("progress", ({loaded, total}) =>{
    let fileLoaded = Math.floor((loaded / total) * 100);
    let fileTotal = Math.floor(total / 1000);
    let fileSize;
    (fileTotal < 1024) ? fileSize = fileTotal + " KB" : fileSize = (loaded / (1024*1024)).toFixed(2) + " MB";
    let progressHTML = `<li class="row">
                          <i class="fas fa-file-alt"></i>
                          <div class="content">
                            <div class="details">
                              <span class="name">${name} • Uploading</span>
                              <span class="percent">${fileLoaded}%</span>
                            </div>
                            <div class="progress-bar">
                              <div class="progress" style="width: ${fileLoaded}%"></div>
                            </div>
                          </div>
                        </li>`;
    uploadedArea.classList.add("onprogress");
    progressArea.innerHTML = progressHTML;
    if(loaded == total){
      progressArea.innerHTML = "";
      let uploadedHTML = `<li class="row upload-success">
                            <div class="content upload">
                              <i class="fas fa-file-alt"></i>
                              <div class="details">
                                <span class="name">${name} • Uploaded</span>
                                <span class="size">${fileSize}</span>
                                <i class="fas fa-check"></i>
                              </div>
                            </div>
                          </li>`;
      uploadedArea.classList.remove("onprogress");
      uploadedArea.insertAdjacentHTML("afterbegin", uploadedHTML);
    }
  });
  let data = new FormData(form);
  xhr.send(data);
}

const buttons = document.querySelectorAll('.btn-custom2');

buttons.forEach(button => {
    button.addEventListener('click', () => {
        buttons.forEach(btn => btn.classList.remove('active'));
        button.classList.add('active');
    });
});

document.addEventListener('DOMContentLoaded', function() {
    const commentButton = document.querySelector('.modal-footer .btn-primary');
    const commentTextarea = document.getElementById('floatingTextarea');
    const commentList = document.getElementById('commentList');
    const fileInput = document.querySelector('.file-input');
    const uploadedArea = document.querySelector('.uploaded-area');

    let uploadedImage = null; // Chỉ lưu một hình ảnh
    fileInput.addEventListener('change', function() {
        const files = fileInput.files;
        uploadedImage = null; 
        uploadedArea.innerHTML = '';

        if (files.length > 0) {
            const file = files[0]; // Chỉ lấy hình ảnh đầu tiên
            const reader = new FileReader();

            reader.onload = function(event) {
                const img = document.createElement('img');
                img.src = event.target.result;
                img.alt = 'Uploaded Image';
                img.style.width = '100px';
                img.style.marginRight = '5px';
                uploadedArea.appendChild(img);
                uploadedImage = event.target.result; // Lưu hình ảnh vào biến
            };

            reader.readAsDataURL(file); // Đọc file và tạo URL
        }
    });
});
function selectColor(element) {
    const selectedColor = element.value;
    console.log('Màu sản phẩm đã chọn:', selectedColor);
}
  
  
  
function selectCapacity(element) {
    document.querySelectorAll('.product-capacity').forEach((capacity) => {
        capacity.classList.remove('selected');
    });

    const selectedElement = element.closest('.product-capacity');
    if (selectedElement) {
        selectedElement.classList.add('selected');
    }
    console.log('Dung lượng được chọn:', element.value);
}
