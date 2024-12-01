

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
    let id = link.getAttribute("data-id")
    console.log(id)
    let a = fetch("http://localhost:8080/rest/giohang/" + id)
  }
  // Xóa sản phẩm cá nhân
function addItem(link) {
  let checked = link.getAttribute("data-id")
  console.log(checked)
  let a = fetch("http://localhost:8080/rest/giohang/update" + checked)
}   

function addVoucher(link) {
    let id = link.getAttribute("data-id");
    let type = link.getAttribute("name"); // "MaGiamDh" hoặc "MaGiamSp"
    console.log("Voucher ID:", id, "Type:", type);

    // Nếu là maGiamDh, chỉ chọn được 1 voucher
    if (type === "MaGiamDh") {
        let maGiamDhButtons = document.querySelectorAll("[name = MaGiamDh]");
        maGiamDhButtons.forEach(button => {
            button.classList.add("btn-outline-primary");
            button.classList.remove("btn-success");
            button.innerText = "Chọn";
        });
    }
    

    // Gửi yêu cầu tới API để cập nhật trạng thái
    fetch(`http://localhost:8080/rest/giohang/updateVoucher/${id}`, { method: 'POST' })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                // Nếu loại là maGiamDh, chỉ cập nhật nút tương ứng
                if (type === "MaGiamDh") {
                    if (data.checked) {
                        link.innerText = "Đã chọn";
                        link.classList.remove("btn-outline-primary");
                        link.classList.add("btn-success");
                    } else {
                        link.innerText = "Chọn";
                        link.classList.remove("btn-success");
                        link.classList.add("btn-outline-primary");
                    }
                }
                
                // Nếu loại là maGiamSp, nhiều voucher có thể chọn cùng lúc
                if (type === "MaGiamSp") {
                    if (data.checked) {
                        link.innerText = "Đã chọn";
                        link.classList.remove("btn-outline-primary");
                        link.classList.add("btn-success");
                    } else {
                        link.innerText = "Chọn";
                        link.classList.remove("btn-success");
                        link.classList.add("btn-outline-primary");
                    }
                }
            } else {
                alert("Lỗi: " + data.message);
            }
        })
        .catch(error => console.error("Lỗi khi gửi yêu cầu:", error));
}






