var citis = document.getElementById("city");
var districts = document.getElementById("district");
var wards = document.getElementById("ward");
var Parameter = {
    url: "https://raw.githubusercontent.com/kenzouno1/DiaGioiHanhChinhVN/master/data.json",
    method: "GET",
    responseType: "application/json",
};
var promise = axios(Parameter);
promise.then(function (result) {
    renderCity(result.data);
});

function renderCity(data) {
    citis.innerHTML = '<option value="">Chọn tỉnh thành</option>';
    for (const x of data) {
        citis.options[citis.options.length] = new Option(x.Name, x.Name);
    }
    citis.onchange = function () {
        districts.length = 1;
        wards.length = 1;
        if (this.value != "") {
            const result = data.filter(n => n.Name === this.value);

            for (const k of result[0].Districts) {
                districts.options[districts.options.length] = new Option(k.Name, k.Name);
            }
        }
    };
    districts.onchange = function () {
        wards.length = 1;
        const dataCity = data.filter((n) => n.Name === citis.value);
        if (this.value != "") {
            const dataWards = dataCity[0].Districts.filter(n => n.Name === this.value)[0].Wards;

            for (const w of dataWards) {
                wards.options[wards.options.length] = new Option(w.Name, w.Name);
            }
        }
    };
}
function deleteSelectedCards() {
    const checkboxes = document.querySelectorAll('.delete-checkbox');
    checkboxes.forEach((checkbox) => {
        if (checkbox.checked) {
            checkbox.closest('.address-card').remove();
        }
    });
}
async function fillModal(editBtn) {
    // Lấy thông tin từ card mà nút "Sửa" thuộc về
    const card = editBtn.closest('.address-card');
    const addressType = card.querySelector('.address-type').innerText.trim();
    const addressName = card.querySelector('.address-name').innerText.trim();
    const addressPhone = card.querySelector('.address-phone').innerText.trim();
    const addressDetailText = card.querySelector('.address-detail').innerText.trim();

    // Tách địa chỉ dựa trên dấu phẩy và chuẩn hóa các chuỗi
    const addressDetailArray = addressDetailText.split(',').map(part => part.trim());

    // Phân tích thông tin chi tiết địa chỉ
    const addressDetail = addressDetailArray[0] || '';
    const ward = addressDetailArray[1] || '';
    const district = addressDetailArray[2] || '';
    const city = addressDetailArray[3] || '';

    // Điền thông tin vào các trường trong modal
    document.getElementById('name').value = addressName;
    document.getElementById('phone').value = addressPhone;
    document.getElementById('addressDetail').value = addressDetail;

    // Kiểm tra xem card có class "active" không để đặt checkbox "Đặt làm địa chỉ mặc định"
    const defaultAddressCheckbox = document.getElementById('defaultAddress');
    defaultAddressCheckbox.checked = card.classList.contains('active');

    try {
        const result = await axios.get("https://raw.githubusercontent.com/kenzouno1/DiaGioiHanhChinhVN/master/data.json");
        const data = result.data;

        // Điền dữ liệu vào dropdown thành phố và chọn thành phố
        fillCityDropdown(data, city).then(() => {
            const cityData = data.find(item => normalizeString(item.Name) === normalizeString(city));
            if (cityData) {
                // Điền dữ liệu quận/huyện vào dropdown và chọn quận/huyện
                fillDistrictDropdown(cityData, district).then(() => {
                    const districtData = cityData.Districts.find(item => normalizeString(item.Name) === normalizeString(district));
                    if (districtData) {
                        // Điền dữ liệu phường/xã vào dropdown và chọn phường/xã
                        fillWardDropdown(districtData, ward);
                    }
                });
            }
        });
    } catch (error) {
        console.error('Không thể lấy dữ liệu thành phố:', error);
    }

    // Đặt loại địa chỉ vào modal
    document.querySelectorAll('.type-btn').forEach(btn => btn.classList.remove('active'));
    document.querySelectorAll('.type-btn').forEach(btn => {
        if (normalizeString(btn.dataset.type) === normalizeString(addressType)) {
            btn.classList.add('active');
        }
    });
}

// Hàm để chuẩn hóa chuỗi để loại bỏ khoảng trắng và dấu không mong muốn
function normalizeString(str) {
    return str.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase().replace(/đ/g, 'd');
}

// Hàm để điền thành phố vào dropdown
function fillCityDropdown(data, selectedCity) {
    return new Promise((resolve) => {
        const citySelect = document.getElementById('city');
        citySelect.innerHTML = '<option value="">Chọn tỉnh thành</option>';
        data.forEach(cityItem => {
            const option = new Option(cityItem.Name, cityItem.Name);
            citySelect.add(option);
        });

        // Đặt giá trị thành phố sau khi thêm option
        const normalizedCity = normalizeString(selectedCity);
        for (let i = 0; i < citySelect.options.length; i++) {
            if (normalizeString(citySelect.options[i].value) === normalizedCity) {
                citySelect.selectedIndex = i;
                break;
            }
        }
        resolve();
    });
}

// Hàm để điền quận/huyện vào dropdown
function fillDistrictDropdown(cityData, selectedDistrict) {
    return new Promise((resolve) => {
        const districtSelect = document.getElementById('district');
        districtSelect.innerHTML = '<option value="">Chọn quận huyện</option>';
        cityData.Districts.forEach(districtItem => {
            const option = new Option(districtItem.Name, districtItem.Name);
            districtSelect.add(option);
        });

        // Đặt giá trị quận/huyện sau khi thêm option
        const normalizedDistrict = normalizeString(selectedDistrict);
        for (let i = 0; i < districtSelect.options.length; i++) {
            if (normalizeString(districtSelect.options[i].value) === normalizedDistrict) {
                districtSelect.selectedIndex = i;
                break;
            }
        }
        resolve();
    });
}

// Hàm để điền phường/xã vào dropdown
function fillWardDropdown(districtData, selectedWard) {
    const wardSelect = document.getElementById('ward');
    wardSelect.innerHTML = '<option value="">Chọn phường xã</option>';
    districtData.Wards.forEach(wardItem => {
        const option = new Option(wardItem.Name, wardItem.Name);
        wardSelect.add(option);
    });

    // Đặt giá trị phường/xã sau khi thêm option
    const normalizedWard = normalizeString(selectedWard);
    for (let i = 0; i < wardSelect.options.length; i++) {
        if (normalizeString(wardSelect.options[i].value) === normalizedWard) {
            wardSelect.selectedIndex = i;
            break;
        }
    }
}

// Hàm để chuyển trạng thái "active" cho loại địa chỉ
function toggleType(button) {
    document.querySelectorAll('.type-btn').forEach(btn => btn.classList.remove('active'));
    button.classList.add('active');
}

function clearModal() {
    // Làm trống các trường trong modal khi nhấn vào "Thêm địa chỉ mới"
    document.getElementById('name').value = '';
    document.getElementById('phone').value = '';
    document.getElementById('city').value = '';
    document.getElementById('district').length = 1;
    document.getElementById('ward').length = 1;
    document.getElementById('addressDetail').value = '';
    document.querySelectorAll('.type-btn').forEach(btn => btn.classList.remove('active'));
}

function toggleType(button) {
    document.querySelectorAll('.type-btn').forEach(btn => btn.classList.remove('active'));
    button.classList.add('active');
}



function clearModal() {
    // Làm trống các trường trong modal khi nhấn vào "Thêm địa chỉ mới"
    document.getElementById('name').value = '';
    document.getElementById('phone').value = '';
    document.getElementById('city').value = '';
    document.getElementById('district').length = 1;
    document.getElementById('ward').length = 1;
    document.getElementById('addressDetail').value = '';
    document.querySelectorAll('.type-btn').forEach(btn => btn.classList.remove('active'));
}

function toggleType(button) {
    document.querySelectorAll('.type-btn').forEach(btn => btn.classList.remove('active'));
    button.classList.add('active');
}
