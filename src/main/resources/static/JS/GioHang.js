

// Cập nhật tổng số tiền dựa trên các sản phẩm đã chọn
function updateTotalAmount() {
    let totalAmount = 0;
    const items = document.querySelectorAll(".cart-containerItem");

    // Hiển thị thông báo giỏ hàng trống nếu không có sản phẩm nào trong giỏ hàng
    document.querySelector(".empty-cart-message").style.display = items.length === 0 ? 'block' : 'none';

    // Tính tổng số tiền cho các sản phẩm được chọn
    items.forEach(item => {
        const checkbox = item.querySelector(".select-item");
        if (checkbox.checked) {
            const totalPriceText = item.querySelector(".total-price").textContent;
            const totalPrice = parseFloat(totalPriceText.replace(/[^0-9]/g, ''));
            totalAmount += totalPrice;
        }
    });

    // Cập nhật hiển thị tổng số tiền
    document.querySelector(".amount").textContent = totalAmount.toLocaleString() + 'đ';
}



// Các hàm điều khiển số lượng sản phẩm
function increaseQuantity(button) {
    const quantityDisplay = button.previousElementSibling;
    let currentQuantity = parseInt(quantityDisplay.textContent);
    quantityDisplay.textContent = currentQuantity + 1;

    updateTotalPrice(button, currentQuantity + 1);
    updateTotalAmount();
}

function decreaseQuantity(button) {
    const quantityDisplay = button.nextElementSibling;
    let currentQuantity = parseInt(quantityDisplay.textContent);
    if (currentQuantity > 1) {
        quantityDisplay.textContent = currentQuantity - 1;
        updateTotalPrice(button, currentQuantity - 1);
        updateTotalAmount();
    }
}




// Cập nhật tổng giá của từng sản phẩm dựa trên số lượng
function updateTotalPrice(button, newQuantity) {
    const cartItem = button.closest('.cart-containerItem');
    const priceText = cartItem.querySelector('.price').textContent;
    const price = parseFloat(priceText.replace(/[^0-9]/g, ''));

    const totalPriceElement = cartItem.querySelector('.total-price');
    const newTotalPrice = price * newQuantity;
    totalPriceElement.textContent = newTotalPrice.toLocaleString() + 'đ';
}




// Chọn hoặc bỏ chọn tất cả các sản phẩm
function toggleSelectAll() {
    const selectAll = document.getElementById("select-all").checked;
    document.querySelectorAll(".select-item").forEach(item => {
        item.checked = selectAll;
        // Lấy thông tin sản phẩm và khách hàng từ các thuộc tính
        const sanPham = item.getAttribute("data-idsp");
        const khachhang = item.getAttribute("data-idkh"); // ID khách hàng nếu có
        updateProductSelection(sanPham, khachhang, selectAll); // Cập nhật trạng thái trên server
    });
    updateTotalAmount();
}


// Kiểm tra và cập nhật trạng thái checkbox "Chọn tất cả" khi sản phẩm được chọn/bỏ chọn
function checkSelected() {
    document.querySelectorAll(".select-item").forEach(item => {
        const sanPham = item.getAttribute("data-idsp");
        const khachhang = item.getAttribute("data-idkh");
        const isChecked = item.checked;

        if (sanPham && khachhang) {
            updateProductSelection(sanPham, khachhang, isChecked);
        } else {
            console.error('Tham số sanPham hoặc khachhang không hợp lệ:', sanPham, khachhang);
        }
    });
}

// Xóa tất cả sản phẩm được chọn
function deleteAllSelected() {
    document.querySelectorAll(".select-item:checked").forEach(item => {
        const sanPham = item.getAttribute("data-idsp");
        const khachhang = item.getAttribute("data-idkh");
        removeItemFromDB(sanPham, khachhang); // Gửi yêu cầu xóa sản phẩm khỏi DB
        item.closest('.cart-containerItem').remove(); // Xóa sản phẩm khỏi UI
    });
    updateTotalAmount();
}


// Bỏ chọn tất cả sản phẩm
function deselectAll() {
    document.querySelectorAll(".select-item").forEach(item => item.checked = false);
    document.getElementById("select-all").checked = false;
    updateTotalAmount();
}


// Xóa sản phẩm cá nhân
function removeItem(link) {
    const productItem = link.closest('.cart-containerItem');
    if (productItem) {
        const sanPham = productItem.querySelector(".select-item").getAttribute("data-idsp");
        const khachhang = productItem.querySelector(".select-item").getAttribute("data-idkh");
        removeItemFromDB(sanPham, khachhang); // Xóa sản phẩm khỏi DB
        productItem.remove(); // Xóa sản phẩm khỏi UI
        updateTotalAmount();
    }
}
// Kiểm tra tham số trước khi gọi hàm
if (sanPham && khachhang) {
    updateProductSelection(sanPham, khachhang, selectAll);
} else {
    console.error('Tham số sanPham hoặc khachhang không hợp lệ:', sanPham, khachhang);
}
function updateProductSelection(sanPham, khachhang, ischecked) {
    const encodedSanPham = encodeURIComponent(sanPham);
    const encodedKhachhang = encodeURIComponent(khachhang);
    fetch(`/update-product-selection?idSP=${encodedSanPham}&checked=${ischecked}&idKH=${encodedKhachhang}`, {
        method: 'POST'
    })
    .then(response => response.text())
    .then(data => {
        console.log(data); // Hiển thị thông báo kết quả
    })
    .catch(error => console.error('Lỗi:', error));
}

function updateAllProductSelection(khachhang, ischecked) {
    const encodedKhachhang = encodeURIComponent(khachhang);
    fetch(`/update-all-products-selection?checked=${ischecked}&idKH=${encodedKhachhang}`, {
        method: 'POST'
    })
    .then(response => response.text())
    .then(data => {
        console.log(data); // Hiển thị thông báo kết quả
    })
    .catch(error => console.error('Lỗi:', error));
}

function removeItemFromDB(sanPham, khachhang) {
    const encodedSanPham = encodeURIComponent(sanPham);
    const encodedKhachhang = encodeURIComponent(khachhang);
    fetch(`/remove-product-from-cart?idSP=${encodedSanPham}&idKH=${encodedKhachhang}`, {
        method: 'POST'
    })
    .then(response => response.text())
    .then(data => {
        console.log("Sản phẩm đã được xóa khỏi giỏ hàng trong DB.");
    })
    .catch(error => console.error('Lỗi khi xóa sản phẩm khỏi giỏ hàng:', error));
}






