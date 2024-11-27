
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

   
    document.getElementById('name').value = addressName;
    document.getElementById('phone').value = addressPhone;
    document.getElementById('addressDetail').value = addressDetailText;

    // Kiểm tra xem card có class "active" không để đặt checkbox "Đặt làm địa chỉ mặc định"
    const defaultAddressCheckbox = document.getElementById('defaultAddress');
    defaultAddressCheckbox.checked = card.classList.contains('active');

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


// Hàm để chuyển trạng thái "active" cho loại địa chỉ
function toggleType(button) {
    document.querySelectorAll('.type-btn').forEach(btn => btn.classList.remove('active'));
    button.classList.add('active');
}

function clearModal() {
    // Làm trống các trường trong modal khi nhấn vào "Thêm địa chỉ mới"
    document.getElementById('name').value = '';
    document.getElementById('phone').value = '';
    document.getElementById('addressDetail').value = '';
    document.querySelectorAll('.type-btn').forEach(btn => btn.classList.remove('active'));
}




function clearModal() {
    // Làm trống các trường trong modal khi nhấn vào "Thêm địa chỉ mới"
    document.getElementById('name').value = '';
    document.getElementById('phone').value = '';
    document.getElementById('addressDetail').value = '';
    document.querySelectorAll('.type-btn').forEach(btn => btn.classList.remove('active'));
}
