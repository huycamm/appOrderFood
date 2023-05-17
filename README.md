# appOrderFood
**Chức năng chính của ứng dụng**<br>
1. Đăng nhập, đăng ký tài khoản.
2. Hiển thị danh sách Foods Home dưới dạng Slide Images + Auto Run (Realtime).
3. Hiển thị danh sách Foods gợi ý ở màn hình Home (Realtime).
4. Tính năng search Food theo tên.
5. Hiển thị thông tin chi tiết của Food: thông tin cơ bản, miêu tả,...
6. Tính năng thêm Food vào giỏ hàng: có thể chọn số lượng khi thêm.
7. Hiển thị tất cả danh sách Foods người dùng đã thêm vào giỏ hàng: có thể update số lượng hoặc xóa món ăn nếu không thích.
8. Thực hiện Order theo thực đơn đã chọn.
9. Hiện thị danh sách lịch sử đã Order theo từng tài khoản.
10. Tính năng Feedback: gửi phản hồi, đóng góp, đánh giá cho quán ăn.<br>

**Các công nghệ sử dụng trong Source Code**
1. Project Architecture: MVC (Model - View- Controller).
2. View Binding trong Android.
3. Realtime Database from Firebase.
4. Hiển thị data, search, order history: tất cả đều Realtime.
5. SQLite Database(quản lý danh sách giỏ hàng Offline): thêm sản phẩm vào giỏ hàng, update giỏ hàng, xóa khỏi giỏ hàng, check xem đã tồn tại trong giỏ hàng chưa….
6. Sử dụng BottomSheetDialog để thiết kế layout Add to Cart and Order.
7. BottomNavigationView + ViewPager2 + Fragments trong Android.
8. ViewPager2 + CircleIndicator3 + Auto Run Slide Images.
9. Load ảnh với thư viện Picasso.

**Cài đặt**
1. Clone repository về máy tính của bạn bằng cách sử dụng lệnh sau trong dòng lệnh hoặc sử dụng GUI để clone repository:<br>
 ``git clone https://github.com/huycamm/appOrderFood.git``
 2. Mở Android Studio và chọn "Open an existing Android Studio project".
 3. Tìm và chọn thư mục đã clone trong bước 1.
 4. Đợi cho Android Studio build project và tải về các dependencies theo yêu cầu.
 5. Kết nối thiết bị di động hoặc máy tính bảng chạy hệ điều hành Android của bạn vào máy tính và đảm bảo rằng chế độ "Developer options" đã được bật trên thiết bị của bạn.
 6. Chọn thiết bị Android để chạy ứng dụng và chọn "Run app" từ cửa sổ "Run"
 7. Ứng dụng sẽ được cài đặt và chạy trên thiết bị của bạn.<br>


**Sử dụng**<br>
Ứng dụng có thể được chạy trên điện thoại di động hoặc máy tính bảng chạy hệ điều hành Android từ phiên bản 5.0 trở lên.<br>


 
