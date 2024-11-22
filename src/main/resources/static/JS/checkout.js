function selectPaymentMethod(element) {
    document.querySelectorAll('.payment-method').forEach(el => el.classList.remove('selected'));
    element.classList.add('selected');
  }
  document.addEventListener("DOMContentLoaded", function () {
    // Hiển thị modal
    const paymentModal = new bootstrap.Modal(document.getElementById("paymentModal"));
  
    // Cài đặt đồng hồ đếm ngược (5 phút = 300 giây)
    let timeRemaining = 300; // 5 phút = 300 giây
    const timerElement = document.getElementById("timer");
    const successMessage = document.getElementById("successMessage");
  
    // Hàm cập nhật đồng hồ
    function updateTimer() {
      const minutes = Math.floor(timeRemaining / 60);
      const seconds = timeRemaining % 60;
  
      // Cập nhật hiển thị đồng hồ
      timerElement.textContent = `${minutes}:${seconds < 10 ? '0' : ''}${seconds}`;
      
      // Giảm thời gian còn lại
      timeRemaining--;
  
      // Nếu đã 10 giây, hiển thị thông báo "Giao dịch thành công!"
      if (timeRemaining === 290) { // 10 giây đã trôi qua (300 - 10 = 290)
        successMessage.style.display = "block";
      }
  
      // Nếu đồng hồ đã hết thời gian
      if (timeRemaining < 0) {
        clearInterval(timerInterval);
      }
    }
  
    // Cập nhật đồng hồ mỗi giây
    const timerInterval = setInterval(updateTimer, 1000);
  });
  