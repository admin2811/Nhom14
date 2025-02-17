[TRƯỜNG ĐẠI HỌC THỦY LỢI]{.mark}

**[KHOA CÔNG NGHỆ THÔNG TIN]{.mark}**

![](media/image54.png){width="1.9416666666666667in" height="1.3in"}

**BÀI TẬP LỚN**

HỌC PHẦN: PHÁT TRIỂN ỨNG DỤNG CHO CÁC THIẾT BỊ DI ĐỘNG

**ĐỀ TÀI:**

**ỨNG DỤNG HỌC TIẾNG ANH TRÊN ANDROID**

> **Giáo viên hướng dẫn: Nguyễn Văn Nam**
>
> Nhóm sinh viên thực hiện:
>
> 1\. Triệu Thị Hậu, 2151163689 - 63HTTT1
>
> 2\. Nguyễn Đức Minh, 2151163707 - 63HTTT1
>
> 3\. Phùng Bảo Lâm, 2151163700 - 63HTTT1
>
> **Hà Nội, năm 2024**

**MỤC LỤC**

[**LỜI MỞ ĐẦU 4**](#lời-mở-đầu)

[**CHƯƠNG 1: TỔNG QUAN VỀ APP HỌC TIẾNG ANH VÀ NỀN TẢNG XÂY DỰNG ỨNG
DỤNG
5**](#chương-1-tổng-quan-về-app-học-tiếng-anh-và-nền-tảng-xây-dựng-ứng-dụng)

> [1.1. Tổng quan về quản lý app học tiếng anh
> 5](#tổng-quan-về-quản-lý-app-học-tiếng-anh)
>
> [1.2. Nền tảng xây dựng ứng dụng **6**](#)

[**CHƯƠNG II. PHÂN TÍCH HỆ THỐNG 7**](#)

> [2.1. Yêu cầu chức năng 7](#)
>
> [2.2. Biểu đồ Use Case tổng quát 8](#)
>
> [2.3. Thiết kế cơ sở dữ liệu 9](#)
>
> [2.4. Biểu đồ tuần tự Đăng ký 10](#biểu-đồ-tuần-tự-đăng-ký)
>
> [2.5. Biểu đồ quản lý Đăng nhập 12](#biểu-đồ-quản-lý-đăng-nhập)

[**CHƯƠNG III. THIẾT KẾ HỆ THỐNG 14**](#)

> [**3.1: Giao diện người dùng** 14](#giao-diện-người-dùng)
>
> [3.1.1. Welcom 14](#welcom)
>
> [3.1.2. Đăng ký 15](#đăng-ký)
>
> [3.1.3. Đăng nhập tài khoản 16](#đăng-nhập-tài-khoản)
>
> [3.1.4. Đăng nhập bằng Google 17](#đăng-nhập-bằng-google)
>
> [3.1.5. Đăng nhập bằng Facebook 17](#đăng-nhập-bằng-facebook)
>
> [3.1.6. Đăng nhập bằng số điện thoại
> 19](#đăng-nhập-bằng-số-điện-thoại)
>
> [3.1.7. Quên mật khẩu 20](#quên-mật-khẩu)
>
> [3.1.8. Đổi mật khẩu 21](#đổi-mật-khẩu)
>
> [3.1.9. Đổi email 22](#đổi-email)
>
> [3.1.10. Đăng xuất 23](#đăng-xuất)
>
> [3.1.11. Hồ sơ 24](#hồ-sơ)
>
> [3.1.12. Xem thông tin cá nhân 25](#xem-thông-tin-cá-nhân)
>
> [3.1.13.Sửa thông tin cá nhân 26](#sửa-thông-tin-cá-nhân)
>
> [3.1.14.Tìm kiếm thông tin khóa học 27](#tìm-kiếm-thông-tin-khóa-học)
>
> [3.1.15. Dịch từ vựng và văn bản 28](#dịch-từ-vựng-và-văn-bản)
>
> [3.1.16. Dịch từ giọng nói 29](#dịch-từ-giọng-nói)
>
> [3.1.17. Dịch bằng chụp văn bản hoặc ảnh từ thư viện ảnh
> 30](#dịch-bằng-chụp-văn-bản-hoặc-ảnh-từ-thư-viện-ảnh)
>
> [3.1.18. Từ điển học tiếng anh 31](#từ-điển-học-tiếng-anh)
>
> [3.1.19. Liệt kê bài học 31](#liệt-kê-bài-học)
>
> [3.1.20. Tìm kiếm tài liệu 33](#tìm-kiếm-tài-liệu)
>
> [3.1.21. Thêm tài liệu 34](#thêm-tài-liệu)
>
> [3.1.22. Xóa tài liệu 35](#xóa-tài-liệu)
>
> [3.1.23. Sửa tài liệu 36](#sửa-tài-liệu)
>
> [3.1.24. Xem tài liệu 37](#xem-tài-liệu)
>
> [3.1.25. Tải xuống tài liệu 38](#tải-xuống-tài-liệu)
>
> [3.1.26. Upload tài liệu 39](#upload-tài-liệu)
>
> [3.1.27. Bài luyện tập theo chủ đề 40](#bài-luyện-tập-theo-chủ-đề)
>
> [3.1.28. Xem các bài học qua video 42](#xem-các-bài-học-qua-video)
>
> [3.1.29. Các bài kiểm tra trên Quizz 44](#các-bài-kiểm-tra-trên-quizz)
>
> [3.1.30. Thẻ học từ vựng 46](#thẻ-học-từ-vựng)
>
> [3.1.31. Thêm thẻ học 48](#thêm-thẻ-học)
>
> [3.1.32. Sửa thẻ học 49](#sửa-thẻ-học)
>
> [3.1.33. Xóa thẻ học 50](#xóa-thẻ-học)
>
> [3.1.34. Tiến độ học tập 51](#tiến-độ-học-tập)
>
> [3.1.35. Trò chơi 2048 52](#trò-chơi-2048)
>
> [3.1.36. Chat bot 53](#chat-bot)
>
> [3.1.37. Chia sẻ ứng dụng 54](#chia-sẻ-ứng-dụng)
>
> [3.1.38. Thông báo mất mạng, pin yếu 55](#thông-báo-mất-mạng-pin-yếu)
>
> [3.1.39. Trung tâm hỗ trợ 56](#trung-tâm-hỗ-trợ)
>
> [3.1.40. Đánh giá ứng dụng 57](#đánh-giá-ứng-dụng)
>
> [**3.2: Mã nguồn của ứng dụng 57**](#mã-nguồn-của-ứng-dụng)

[**CHƯƠNG IV: KẾT LUẬN 58**](#chương-iv-kết-luận)

[**TÀI LIỆU THAM KHẢO 58**](#)

# LỜI MỞ ĐẦU

Trong thời đại công nghệ số hiện nay, việc học tiếng Anh qua các ứng
dụng di động đã trở thành một phương pháp phổ biến và hiệu quả, đáp ứng
nhu cầu học tập ngày càng cao của người dùng. Chuyển đổi từ các phương
pháp học truyền thống sang sử dụng ứng dụng học tiếng Anh không chỉ giúp
tiết kiệm thời gian mà còn nâng cao hiệu suất học tập và mang lại trải
nghiệm học tập cá nhân hóa cho từng người dùng.

Ứng dụng học tiếng Anh không chỉ giúp lưu trữ và tổ chức các bài học một
cách khoa học mà còn cung cấp các tính năng thông minh như học từ vựng
qua hình ảnh, luyện phát âm qua giọng đọc chuẩn, và kiểm tra kiến thức
qua các bài quiz tương tác. Điều này không chỉ tối ưu hóa quá trình học
tập mà còn tạo ra một môi trường học tiếng Anh thú vị và hiệu quả.

Trong dự án này, nhóm thực hiện đã **"Phát triển một ứng dụng học tiếng
Anh trên Android "** với mục tiêu cung cấp một giải pháp toàn diện, linh
hoạt và dễ sử dụng cho người học ở mọi trình độ. Ứng dụng của nhóm không
chỉ tập trung vào việc cung cấp nội dung học phong phú mà còn tối ưu hóa
trải nghiệm người dùng từ quá trình đăng ký, học tập đến kiểm tra và
đánh giá tiến bộ. Nhóm phát triển hy vọng rằng sản phẩm này sẽ đóng vai
trò quan trọng trong việc nâng cao hiệu quả học tập và mang lại tiện ích
tối đa cho cộng đồng người học tiếng Anh

*Nhóm phát triển xin chân thành cảm ơn!*

## 

**Bố cục báo cáo được trình bày thành 4 chương:**

> **Chương 1:** Tổng quan về học tiếng anh và nền tảng xây dựng ứng
> dụng. Chương này, giải thích tầm quan trọng của thực đơn đối với sức
> khỏe mọi người; giới thiệu hệ điều hành android, cơ sở dữ liệu SQLite
> được sử dụng và đặc tả chức năng của ứng dụng.
>
> **Chương 2:** Phân tích hệ thống. Chương này, tiến hành phân tích và
> thiết kế hệ thống ứng dụng đưa ra các kết quả sử dụng ứng dụng hình
> ảnh demo của ứng dụng sau khi hoàn thành.
>
> **Chương 3:** Thiết kế hệ thống và giao diện.
>
> **Chương 4:** Kết luận và hướng phát triển. Chương này, đi đến kết
> luận hiệu quả của ứng dụng đã làm được. Định hướng trong tương lai sẽ
> phát triển các ứng dụng trên Android hỗ trợ người dùng tính năng nhiều
> hơn nữa.

# 

# 

# [CHƯƠNG 1: TỔNG QUAN VỀ APP HỌC TIẾNG ANH VÀ NỀN TẢNG XÂY DỰNG ỨNG](#_30j0zll) [DỤNG](#_30j0zll)

## 1.1. Tổng quan về quản lý app học tiếng anh

## 

**\* Quản lý App Học Tiếng Anh** là quá trình tổ chức, bảo quản và cung
cấp truy cập vào các tài liệu và thông tin học tập tiếng Anh trong một
ứng dụng di động. Mục tiêu của quản lý ứng dụng là tạo điều kiện thuận
lợi nhất cho người sử dụng trong việc tìm kiếm, truy cập và sử dụng tài
liệu học tiếng Anh một cách hiệu quả. Dưới đây là một số khía cạnh chính
của quản lý app học tiếng Anh**:**

-   **Bảo quản và tổ chức tài liệu:** Công việc chính của quản lý app
    học tiếng Anh là sắp xếp và bảo quản tài liệu học tập, bao gồm từ
    điển, bài học, video hướng dẫn, tài liệu nghe nhìn và nhiều hình
    thức tài liệu khác. Việc này thường bao gồm việc phân loại, đánh số,
    và lưu trữ tài liệu một cách có hệ thống trong cơ sở dữ liệu của ứng
    dụng.

-   **Quản lý thông tin:** Quản lý ứng dụng học tiếng Anh cũng bao gồm
    việc tổ chức và bảo quản các thông tin liên quan đến tài liệu, bao
    gồm thông tin về tác giả, nhà xuất bản, năm phát hành, và mô tả nội
    dung. Điều này giúp người sử dụng dễ dàng tìm kiếm và xác định tài
    liệu mình cần.

-   **Dịch vụ cho người sử dụng:** Quản lý ứng dụng không chỉ liên quan
    đến việc lưu trữ tài liệu mà còn bao gồm cung cấp các dịch vụ hỗ trợ
    cho người sử dụng, như tư vấn học tập, hướng dẫn sử dụng ứng dụng,
    và hỗ trợ tìm kiếm thông tin học tập.

-   **Tăng cường truy cập thông tin:** Mục tiêu của quản lý app học
    tiếng Anh là tạo điều kiện thuận lợi nhất để người sử dụng có thể
    truy cập vào thông tin học tập một cách nhanh chóng và dễ dàng, bất
    kể là thông qua tài liệu văn bản, video hay âm thanh.

-   **Sử dụng công nghệ:** Công nghệ đóng một vai trò quan trọng trong
    quản lý app học tiếng Anh hiện đại. Các hệ thống quản lý ứng dụng
    thường bao gồm các phần mềm đặc biệt được thiết kế để quản lý và tổ
    chức tài liệu, cũng như cung cấp các dịch vụ trực tuyến cho người sử
    dụng.

-   **Phát triển ứng dụng:** Quản lý app học tiếng Anh cũng bao gồm việc
    phát triển các khóa học và hoạt động nhằm tăng cường sự tham gia của
    người dùng ở mọi lứa tuổi có nhu cầu học tiếng Anh một cách dễ dàng.
    Các chức năng như tự học qua app, bộ từ điển, tìm kiếm từ vựng và
    dịch từ vựng dễ dàng, tham gia các bài kiểm tra và lưu lại quá trình
    học tiếng Anh đều là những phần quan trọng của ứng dụng.

## 1.2. Nền tảng xây dựng ứng dụng

\- Hệ điều hành android là hệ điều hành di động phổ biến nhất hiện tại
và phát triển mạnh. Do vậy tiềm năng với app android là rất lớn. Lịch sử
android ra đời vào năm 2005. Android xuất hiện hầu như trong các sản
phẩm của các nhà sản xuất lớn như Samsung, Sony, ...

![](media/image55.png){width="4.25036854768154in"
height="3.5836439195100613in"}

Thành phần chính của Android (gồm 4 thành phần chính):

**+ Activity:** Thành phần tương tác với người dùng, cung cấp giao diện
cho người dùng.

Có 2 phương thức mà gần như mọi lớp con Activity phải thực hiện:
onCreate(Bundle)-nơi tạo activity, nơi người lập trình gọi
setContentView(int) kèm theo layout UI của riêng mình. Đồng thời
findViewById(int) giúp gọi các widget (button, textview,..) để dùng
trong UI; onPause() nơi giải quyết sự kiện người dùng rời khỏi activity,
mọi dữ liệu người dùng tạo ra cần phải lưu vào ContentProvider.

**+ Service:** Là một thành phần của ứng dụng các tác vụ chạy ngầm dưới
hệ thống nhằm thực hiện một nhiệm vụ nào đó.

\+ ***Content Provider:*** Là nơi lưu trữ và cung cấp cách truy cập dữ
liệu do các ứng dụng tạo\
nên.\
***+ Broadcast receive:*** Để nhận bản tin quảng bá cơ chế phát đi các
sự kiện

-  Lưu trữ dữ liệu: Các cách lưu trữ dữ liệu như: Share preferences,
files (bộ nhớ trong, đệm, bộ nhớ ngoài, tài nguyên, SQLite.

# CHƯƠNG II. PHÂN TÍCH HỆ THỐNG

## 2.1. Yêu cầu chức năng 

+----------------------------------------------+-----------------------+
| **Chức năng của Admin**                      | **Người thực hiện**   |
+==============================================+=======================+
| -   Đăng ký tài khoản                        | Nguyễn Đức Minh       |
+----------------------------------------------+-----------------------+
| -   Đăng nhập tài khoản                      | Nguyễn Đức Minh       |
+----------------------------------------------+-----------------------+
| -   Đăng nhập bằng google                    | Phùng Bảo Lâm         |
+----------------------------------------------+-----------------------+
| -   Đăng nhập bằng facebook                  | Triệu Thị Hậu.        |
+----------------------------------------------+-----------------------+
| -   Đăng nhập bằng số điện thoại             | Triệu Thị Hậu         |
+----------------------------------------------+-----------------------+
| -   Quên mật khẩu                            | Triệu Thị Hậu         |
+----------------------------------------------+-----------------------+
| -   Đổi mật khẩu                             | Triệu Thị Hậu         |
+----------------------------------------------+-----------------------+
| -   Đổi email                                | Triệu Thị Hậu         |
+----------------------------------------------+-----------------------+
| -   Đăng xuất                                | Triệu Thị Hậu         |
+----------------------------------------------+-----------------------+
| -   Hồ sơ cá nhân                            | Nguyễn Đức Minh       |
+----------------------------------------------+-----------------------+
| -   Xem thông tin cá nhân                    | Nguyễn Đức Minh       |
+----------------------------------------------+-----------------------+
| -   Sửa thông tin cá nhân                    | Nguyễn Đức Minh       |
+----------------------------------------------+-----------------------+
| -   Tìm kiếm khóa học                        | Phùng Bảo Lâm         |
+----------------------------------------------+-----------------------+
| -   Translate(Dịch)                          | Triệu Thị Hậu         |
+----------------------------------------------+-----------------------+
| -   Dịch từ giọng nói                        | Triệu Thị Hậu         |
+----------------------------------------------+-----------------------+
| -   Dịch bằng chụp văn bản hoặc ảnh từ thư   | Triệu Thị Hậu         |
|     viện                                     |                       |
+----------------------------------------------+-----------------------+
| -   Từ điển Tiếng Anh                        | Triệu Thị Hậu         |
+----------------------------------------------+-----------------------+
| -   Liệt kê bài học                          | Nguyễn Đức Minh       |
+----------------------------------------------+-----------------------+
| -   Tìm kiếm tài liệu                        | Nguyễn Đức Minh       |
+----------------------------------------------+-----------------------+
| -   Thêm tài liệu                            | Nguyễn Đức Minh       |
+----------------------------------------------+-----------------------+
| -   Xóa tài liệu                             | Nguyễn Đức Minh       |
+----------------------------------------------+-----------------------+
| -   Sửa tài liệu                             | Nguyễn Đức Minh       |
+----------------------------------------------+-----------------------+
| -   Xem tài liệu                             | Nguyễn Đức Minh       |
+----------------------------------------------+-----------------------+
| -   Tải xuống tài liệu(Download)             | Triệu Thị Hậu         |
+----------------------------------------------+-----------------------+
| -   Tải lên tài liệu(Upload)                 | Nguyễn Đức Minh       |
+----------------------------------------------+-----------------------+
| -   Bài luyện tập theo chủ đề                | Nguyễn Đức Minh       |
+----------------------------------------------+-----------------------+
| -   Xem các bài học qua video                | Triệu Thị Hậu         |
+----------------------------------------------+-----------------------+
| -   Các bài kiểm tra trên Quizz              | Phùng Bảo Lâm         |
+----------------------------------------------+-----------------------+
| -   Thẻ học từ vựng                          | Phùng Bảo Lâm         |
+----------------------------------------------+-----------------------+
| -   Thêm thẻ học                             | Phùng Bảo Lâm         |
+----------------------------------------------+-----------------------+
| -   Sửa thẻ học                              | Phùng Bảo Lâm         |
+----------------------------------------------+-----------------------+
| -   Xóa thẻ học                              | Phùng Bảo Lâm         |
+----------------------------------------------+-----------------------+
| -   Tiến độ học tập                          | Phùng Bảo Lâm         |
+----------------------------------------------+-----------------------+
| -   Trò chơi 2048                            | Nguyễn Đức Minh       |
+----------------------------------------------+-----------------------+
| -   Chat bot                                 | Phùng Bảo Lâm         |
+----------------------------------------------+-----------------------+
| -   Chia sẻ ứng dụng                         | Triệu Thị Hậu         |
+----------------------------------------------+-----------------------+
| -   Đánh giá ứng dụng                        | Triệu Thị Hậu         |
+----------------------------------------------+-----------------------+
| -   Thông báo mất mạng, pin yếu              | Phùng Bảo Lâm         |
+----------------------------------------------+-----------------------+
| -   Trung tâm hỗ trợ                         | Phùng Bảo Lâm         |
+----------------------------------------------+-----------------------+

## 2.2. Biểu đồ Use Case tổng quát

![](media/image20.png){width="6.777777777777778in"
height="4.333333333333333in"}

## 2.3. Thiết kế cơ sở dữ liệu

## ![](media/image53.png){width="6.777777777777778in" height="4.430555555555555in"}

## 2.4. Biểu đồ tuần tự Đăng ký

![](media/image56.png){width="5.488957786526684in"
height="4.140625546806649in"}

-   Đặc tả đăng ký

+--------------------+-------------------------------------------------+
| **Tiêu chí**       | **Ý Nghĩa**                                     |
+====================+=================================================+
| Tên Use case       | Đăng ký                                         |
+--------------------+-------------------------------------------------+
| Tác nhân chính     | User                                            |
+--------------------+-------------------------------------------------+
| Mức                | Bắt buộc có                                     |
+--------------------+-------------------------------------------------+
| Mục đích           | Cho phép người dùng đăng ký tài khoản           |
+--------------------+-------------------------------------------------+
| Tiền điều kiện     | Người dùng chưa có tài khoản                    |
+--------------------+-------------------------------------------------+
| Đảm kiện thành     | Người dùng đăng ký tài khoản thành công         |
| công               |                                                 |
+--------------------+-------------------------------------------------+
| Điều kiện thất bại | Người dùng đăng ký không thành công             |
+--------------------+-------------------------------------------------+
| Kích hoạt          | Khi người dùng nhấn nút 'Sign up'               |
+--------------------+-------------------------------------------------+
| Luồng sự kiện      | 1.  Người dùng mở app, nhấn vào nút 'Sign up'   |
| chính              |                                                 |
|                    | 2.  Người dùng nhập thông tin vào các ô{"Full   |
|                    |     name" , "Email", "Date of Birth", "Gender", |
|                    |     "Mobile", "Password", "Password"}           |
|                    |                                                 |
|                    | 3.  Người dùng nhấn nút 'Sign up'               |
|                    |                                                 |
|                    | 4.  Hệ thống thông báo đăng ký thành công       |
+--------------------+-------------------------------------------------+
| Luồng sự kiện      | > 2.1 Nếu người dùng nhập sai định dạng hoặc    |
| ngoại lệ           | > thiếu thông tin cần thiết thì thông báo lỗi   |
|                    | >                                               |
|                    | > 2.2 Hệ thống sẽ hiện lên thông báo nếu người  |
|                    | > dùng sử dụng email hoặc số điện thoại đã tồn  |
|                    | > tại                                           |
+--------------------+-------------------------------------------------+

##  2.5. Biểu đồ quản lý Đăng nhập

![](media/image4.png){width="5.465096237970254in"
height="3.2180260279965003in"}

![](media/image5.png){width="5.102431102362205in"
height="3.5113495188101487in"}

\- Đặc tả Đăng nhập

+--------------------+-------------------------------------------------+
| **Tiêu chí**       | **Ý Nghĩa**                                     |
+====================+=================================================+
| Tên Use case       | Đăng nhập                                       |
+--------------------+-------------------------------------------------+
| Tác nhân chính     | User                                            |
+--------------------+-------------------------------------------------+
| Mức                | Bắc buộc có                                     |
+--------------------+-------------------------------------------------+
| Mục đích           | Cho phép người dùng đăng nhập vào hệ thống      |
+--------------------+-------------------------------------------------+
| Tiền điều kiện     | Người dùng đã có tài khoản                      |
+--------------------+-------------------------------------------------+
| Điều kiện thành    | Người dùng đăng nhập thành công                 |
| công               |                                                 |
+--------------------+-------------------------------------------------+
| Điều kiện thất bại | Người dùng đăng nhập thất bại                   |
+--------------------+-------------------------------------------------+
| Kích hoạt          | Khi người dùng nhấn nút 'đăng nhập'             |
+--------------------+-------------------------------------------------+
| Luồng sự kiện      | 1.  Người dùng mở app                           |
| chính              |                                                 |
|                    | 2.  Người dùng nhập 'email' và 'password'       |
|                    |                                                 |
|                    | 3.  Người dùng nhấn nút 'Sign in'               |
|                    |                                                 |
|                    | 4.  Hệ thống thông báo đăng nhập thành công     |
|                    |                                                 |
|                    | 5.  Hệ thống chuyển đến trang chủ app           |
+--------------------+-------------------------------------------------+
| Ngoại lệ           | > 3.1 Hệ thống xác minh tài khoản không tồn tại |
|                    | >                                               |
|                    | > 3.2 Hệ thống từ chối đăng nhập                |
+--------------------+-------------------------------------------------+

#  CHƯƠNG III. THIẾT KẾ HỆ THỐNG

## 3.1: Giao diện người dùng

### 3.1.1. Welcom

![](media/image60.png){width="2.7899311023622047in"
height="4.958009623797025in"}

### 3.1.2. Đăng ký 

![](media/image59.png){width="3.100925196850394in"
height="6.942129265091864in"}

### 3.1.3. Đăng nhập tài khoản

![](media/image1.png){width="2.7560673665791775in"
height="5.307292213473316in"}

### 3.1.4. Đăng nhập bằng Google

### ![](media/image22.png){width="2.4479166666666665in" height="4.742305336832896in"}

### 3.1.5. Đăng nhập bằng Facebook

![](media/image1.png){width="2.4375in" height="4.691037839020122in"}

### 3.1.6. Đăng nhập bằng số điện thoại

![](media/image45.png){width="2.901042213473316in"
height="5.640914260717411in"}![](media/image29.png){width="2.902551399825022in"
height="5.640625546806649in"}

### 3.1.7. Quên mật khẩu 

![](media/image57.png){width="3.437574365704287in"
height="6.684314304461942in"}

### 3.1.8. Đổi mật khẩu

![](media/image58.png){width="2.956597769028871in"
height="5.681486220472441in"}

### 3.1.9. Đổi email

![](media/image23.png){width="3.050347769028871in"
height="5.869150262467191in"}

### 3.1.10. Đăng xuất

![](media/image40.png){width="2.0833333333333335in" height="4.375in"}

### 3.1.11. Hồ sơ 

![](media/image48.png){width="2.9461811023622047in"
height="6.277001312335958in"}![](media/image49.png){width="2.904514435695538in"
height="3.6306419510061243in"}

### 3.1.12. Xem thông tin cá nhân

![](media/image43.png){width="2.779514435695538in"
height="6.563554243219597in"}

### 3.1.13.Sửa thông tin cá nhân

![](media/image10.png){width="3.164834864391951in"
height="7.473958880139983in"}![](media/image8.png){width="3.175347769028871in"
height="7.495056867891513in"}

### 3.1.14.Tìm kiếm thông tin khóa học

![](media/image19.png){width="3.270371828521435in"
height="6.905184820647419in"}

### 3.1.15. Dịch từ vựng và văn bản

![](media/image34.png){width="3.2899311023622047in"
height="6.488727034120735in"}

### 3.1.16. Dịch từ giọng nói

![](media/image32.png){width="3.6817727471566055in"
height="7.265625546806649in"}

### 3.1.17. Dịch bằng chụp văn bản hoặc ảnh từ thư viện ảnh

![](media/image18.png){width="3.6332458442694664in"
height="7.171875546806649in"}

### 3.1.18. Từ điển học tiếng anh

![](media/image52.png){width="2.9495352143482063in"
height="7.598958880139983in"}

### 3.1.19. Liệt kê bài học

![](media/image42.png){width="3.024795494313211in"
height="6.505208880139983in"}![](media/image14.png){width="3.0618864829396326in"
height="6.473958880139983in"}

### 3.1.20. Tìm kiếm tài liệu

![](media/image47.png){width="2.8125in" height="4.182292213473316in"}

### 3.1.21. Thêm tài liệu

![](media/image12.png){width="2.9583333333333335in"
height="5.625683508311461in"}

### 3.1.22. Xóa tài liệu

![](media/image24.png){width="2.3020833333333335in"
height="3.8802088801399823in"}

### 3.1.23. Sửa tài liệu

![](media/image24.png){width="2.3020833333333335in"
height="4.348958880139983in"}

### 3.1.24. Xem tài liệu

![](media/image51.png){width="2.706597769028871in"
height="5.555647419072616in"}

### 3.1.25. Tải xuống tài liệu

![](media/image12.png){width="2.9583333333333335in"
height="5.625683508311461in"}

### 3.1.26. Upload tài liệu

![](media/image6.png){width="3.206597769028871in"
height="4.8989676290463695in"}

### 3.1.27. Bài luyện tập theo chủ đề

![](media/image13.png){width="2.654514435695538in"
height="6.331583552055993in"}![](media/image21.png){width="2.9985575240594926in"
height="6.328125546806649in"}

![](media/image15.png){width="2.800347769028871in"
height="5.956878827646544in"}![](media/image35.png){width="2.8226935695538056in"
height="5.953125546806649in"}

### 3.1.28. Xem các bài học qua video

![](media/image37.png){width="3.144097769028871in"
height="6.691032370953631in"}![](media/image11.png){width="3.1649311023622047in"
height="6.736170166229221in"}

![](media/image7.png){width="2.842014435695538in"
height="6.0486286089238845in"}

### 3.1.29. Các bài kiểm tra trên Quizz

![](media/image41.png){width="3.029514435695538in"
height="7.108859361329833in"}![](media/image17.png){width="3.529514435695538in"
height="7.069563648293963in"}

![](media/image46.png){width="3.206597769028871in"
height="6.422101924759405in"}![](media/image28.png){width="3.2274311023622047in"
height="6.465374015748031in"}

### 3.1.30. Thẻ học từ vựng

![](media/image36.png){width="2.987847769028871in"
height="5.9855883639545056in"}![](media/image25.png){width="2.9774311023622047in"
height="5.96495406824147in"}

![](media/image39.png){width="2.5086811023622047in"
height="5.024329615048119in"}

### 3.1.31. Thêm thẻ học

![](media/image44.png){width="1.8020833333333333in"
height="3.5208333333333335in"}

### 3.1.32. Sửa thẻ học

![](media/image2.png){width="2.9406299212598426in"
height="5.890625546806649in"}

### 3.1.33. Xóa thẻ học

![](media/image38.png){width="2.8593755468066493in"
height="5.728889982502187in"}

### 3.1.34. Tiến độ học tập

![](media/image31.png){width="3.050347769028871in"
height="6.110317147856518in"}

### 3.1.35. Trò chơi 2048

![](media/image26.png){width="2.9149311023622047in"
height="5.839174321959755in"}![](media/image27.png){width="2.9461811023622047in"
height="5.772331583552056in"}

### 3.1.36. Chat bot

![](media/image50.png){width="2.8788998250218722in"
height="6.234375546806649in"}

### 3.1.37. Chia sẻ ứng dụng

![](media/image30.png){width="2.4791666666666665in"
height="5.145833333333333in"}

### 3.1.38. Thông báo mất mạng, pin yếu

![](media/image9.png){width="3.0868055555555554in"
height="5.487654199475066in"}![](media/image16.png){width="3.107871828521435in"
height="5.521652449693788in"}

### 3.1.39. Trung tâm hỗ trợ

![](media/image3.png){width="2.8837970253718286in"
height="7.057292213473316in"}

### 3.1.40. Đánh giá ứng dụng

![](media/image33.png){width="2.988847331583552in"
height="5.646652449693788in"}

## 3.2: Mã nguồn của ứng dụng

Link to github

[[https://github.com/admin2811/Nhom14]{.underline}](https://github.com/admin2811/Nhom14)

Link Figma

[[https://www.figma.com/design/Sok4eyWfoCYBCxSkqJyQ9z/BTL_ANDROID?node-id=227-1331&t=gXXOGt5pXKJC3PIH-0]{.underline}](https://www.figma.com/design/Sok4eyWfoCYBCxSkqJyQ9z/BTL_ANDROID?node-id=227-1331&t=gXXOGt5pXKJC3PIH-0)

# CHƯƠNG IV: KẾT LUẬN

Quá trình xây dựng ứng dụng học tiếng Anh không chỉ là một dự án công
nghệ thông tin, mà còn là một hành trình mang lại nhiều kinh nghiệm và
bài học quý báu cho nhóm phát triển. Từ việc hiểu biết sâu về ngôn ngữ
học đến việc phát triển kỹ năng làm việc nhóm và tương tác với người
dùng, nhóm đã có cơ hội trải nghiệm và học hỏi nhiều điều mới mẻ và thú
vị.

Việc xây dựng ứng dụng này đã đem lại một số kết quả đáng kể, bao gồm
việc cải thiện hiệu quả học tập, tiết kiệm thời gian và công sức, nâng
cao trải nghiệm người dùng, và phát triển kỹ năng và kiến thức chuyên
môn. Những kinh nghiệm này không chỉ có giá trị trong lĩnh vực công nghệ
thông tin mà còn có thể áp dụng trong nhiều lĩnh vực khác.

Nhóm thực hiện đã làm việc một cách sáng tạo và sử dụng tư duy linh hoạt
để giải quyết các thách thức trong quá trình phát triển. Bằng cách làm
việc chăm chỉ và cống hiến, nhóm đã đạt được một sản phẩm có giá trị và
ứng dụng thực tiễn trong việc học tiếng Anh.

Cuối cùng, nhóm thực hiện muốn bày tỏ lòng biết ơn sâu sắc đến thầy
Nguyễn Văn Nam đã tham gia và đóng góp vào dự án này. Hy vọng rằng ứng
dụng học tiếng Anh này sẽ tiếp tục phát triển và mang lại lợi ích cho
cộng đồng người dùng trong tương lai.

# TÀI LIỆU THAM KHẢO

-   Silde bài giảng

-   https://www.tutorialspoint.com/android/index.htm

# 
