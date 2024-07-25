# Haivl clone requirement

## 1. Tóm tắt dự án

Social media project

- name: meme
- domain-test : haivl.blwsmartware.net
- domain release : chua mua :)

----
Mô tả:
Một trang mạng giải trí, giống haivl.com ngày xưa.
----
Ngôn ngữ: Tiếng việt

## 2. Phạm vi dự án

Có 3 sites:

- Site người dùng
    - Dùng servlet/jsp
- Site quản lí
    - Dùng spring boot/thymleaf
- Site chức năng trò chuyện ngẫu nhiên
    - Dùng js flatform: Reactjs + nodejs

## 3. Yêu cầu công nghệ

### 3.1 Trang người dùng

- Jakarta EE 10 Java sdk:22 (17+)
- Maven 3.9.6
- Tomcat 10
- Mysql 9 - com.mysql-connector/j
- Apache tomcat 10
- Bootstrap 5.3.3 - jQuery

### 3.2 Trang quản lí

- Spring boot: ?
-
    - Java sdk: ?
- thymleaf : ?
- Hibernate : ?
- Maven : ?
- Tomcat 10
- Mysql : 9

### 3.3 Trang trò chuyện

- ReactJS : ?
- npm/yarn : ?
- Design patten: ?
- Node Express/ Nestjs : ?
- Mongodb : ?

## 4. Yêu cầu chức năng

Trang người dùng

### 4.1.1. Đăng nhập

- Đăng nhập bằng qua api bên thứ 3: Google + Facebook
    - Yêu cầu: Auth0
    - cookie : token
- /login

### 4.1.2 Xóa tài khoản

- Xóa tất cả thông tin trên hệ thống bằng 1 code có 6 chữ số được gửi tới email
- /remove-user

### 4.1.3 Trang chủ

- Danh sách ngẫu nhiên các bài đăng.
    - "/", /home
- Danh sách có lượt xem cao nhất.
    - /top
- Danh sách có thẻ tab là xu hướng.
    - /trending
- Danh sách thể loại là câu hỏi
    - /ask

### 4.1.4 Tạo bài đăng

- Bài đăng có: ảnh, tiêu đề, nội dung, bình luận, đánh giá, thẻ tag, thể loại, nguồn tham khảo
- Đăng bài với template có sẵn

### 4.1.5 Các chức năng của 1 người dùng với bài đăng

- Bình luận (Bình luận có lượt đánh giá và được trả lời với level 2)
- Đánh giá
- Ẩn/Hiện bài đăng trên danh sách
- Báo cáo vi phạm, nội dung xấu
- Bật/Tắt thông báo về bài đăng

### 4.1.6 Trang thông tin người dùng

- Xem thông tin cá nhân
- Cập nhật thông tin cá nhân, cập nhật ảnh bìa
- Xem, sửa, xóa, xem trạng thái danh sách bài viết đã từng đăng
- Đổi theme sáng/tối

#### 4.1.7 ... `_đang cập nhật_`

Trang quản lí

### 4.2.1 Đăng nhập

### 4.2.2 Xem danh sách, thêm, sửa xóa thành viên quản lí

### 4.2.3

## 5. Yêu cầu không chức năng

- Tốc độ tải trang dưới 2 giây.
- Hỗ trợ các trình duyệt Chrome, Firefox, Safari.

## 6. Giao diện người dùng

- Giao diện hiện đại, dễ sử dụng.
- Màu sắc chủ đạo: Xanh dương và trắng. Có giao diện tối.

