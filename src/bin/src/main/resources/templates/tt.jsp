<%@ page pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Index</title>
</head>
<body>
    <h1>Thanh toán</h1>
    <form action="/search" method="post">
        Số tiền: <input type="text" name="sotien" id="sotien">
        Nội dung: <input type="text" name="noidung" id="noidung">
        <button type="button" id="btn_tt" onclick="btntt()">Submit</button>
        <img alt="" src="" id="img">
    </form>
</body>
<script type="text/javascript">
    var sotien = document.getElementById('sotien');
    var noidung = document.getElementById('noidung');
    var img = document.getElementById('img');

    function btntt() {
        img.src = 'https://qr.sepay.vn/img?acc=0865854002&bank=MB&amount=' + sotien.value + '&des=' + noidung.value;
        let i = 0;
        // Tạo bản sao của console.error để phục hồi sau này
        const originalConsoleError = console.error;
        let currentDate = new Date();
        
        let timesubmit = currentDate.getFullYear().toString() + "-" + (currentDate.getMonth() + 1).toString() + "-" + currentDate.getDate().toString() + " " + currentDate.getHours().toString() +":" +currentDate.getMinutes().toString()+":"+currentDate.getSeconds().toString();
        console.log("Time submit:", timesubmit);
        let myInterval = setInterval(async () => {
            try {
                const response = await fetch("http://localhost:8080/checktttc?sotien="+sotien.value + "&noidung=" + noidung.value +"&timesubmit=" + timesubmit);
                const data = await response.json(); // Chuyển đổi phản hồi thành JSON
				console.log(data)
                if (data == true) {
                    clearInterval(myInterval);  // Dừng Interval khi thanh toán thành công
                    alert("Thanh toán thành công");
                    window.location.href = "http://localhost:8080/main";  // Điều hướng đến trang chính
                } else {
                    i++;
                }
            } catch (error) {
                // Bỏ qua lỗi mà không log ra console
            } finally {
                // Khôi phục lại console.error sau khi fetch hoàn thành
                console.error = originalConsoleError;
            }

            // Dừng gọi API nếu vượt quá số lần thử cho phép (sau 5 phút)
            if (i > 59) {
                clearInterval(myInterval);
                alert("Thanh toán không thành công. Vui lòng thử lại sau.");
            }
        }, 5000);
    }
</script>
</html>
