# BT08 - Spring Boot 3 RESTful API với Swagger 3 & AJAX CRUD

## 1. Giới thiệu
Dự án thực hiện theo yêu cầu **Mục 2, 3, 4** của bài tập Lập trình Web - HCMUTE:
- **Mục 2**: Xây dựng RESTful API CRUD cho bảng `Category` theo tài liệu hướng dẫn.
- **Mục 3**: Cấu hình và tích hợp tài liệu hóa API với **Swagger 3 / OpenAPI 3** (`springdoc-openapi-starter-webmvc-ui:2.3.0`).
- **Mục 4**: Xây dựng đầy đủ RESTful API và giao diện Web render qua **jQuery AJAX** cho chức năng CRUD trên cả 2 bảng: **Category** và **Product** (hỗ trợ upload ảnh/icon).

## 2. Công nghệ sử dụng
- **Java**: 17
- **Framework**: Spring Boot 3.2.5 (Spring Web, Spring Data JPA, Validation)
- **Database**: Microsoft SQL Server
- **API Documentation**: SpringDoc OpenAPI 3 (Swagger 3 UI)
- **Frontend**: HTML5, CSS3, Bootstrap 5, FontAwesome 6, jQuery AJAX, SweetAlert2

## 3. Cấu hình cơ sở dữ liệu
Trong file [application.properties](file:///src/main/resources/application.properties):
```properties
server.port=8080
spring.datasource.url=jdbc:sqlserver://localhost:1434;databaseName=BT08_DB;encrypt=true;trustServerCertificate=true
spring.datasource.username=sa
spring.datasource.password=123
spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver
spring.jpa.hibernate.ddl-auto=update
```

## 4. Danh sách các API Endpoints
### Category API (`/api/category`):
- `GET /api/category`: Lấy danh sách tất cả Category
- `GET /api/category/{id}`: Lấy chi tiết Category theo ID
- `POST /api/category/getCategory?id={id}`: Lấy chi tiết Category (theo chuẩn hướng dẫn PDF)
- `POST /api/category/addCategory`: Thêm mới Category (Multipart FormData: `categoryName`, `icon`)
- `PUT /api/category/updateCategory`: Cập nhật Category (Multipart FormData: `categoryId`, `categoryName`, `icon`)
- `DELETE /api/category/deleteCategory?categoryId={id}`: Xóa Category theo ID

### Product API (`/api/product`):
- `GET /api/product`: Lấy danh sách tất cả Product
- `GET /api/product/{id}`: Lấy chi tiết Product theo ID
- `GET /api/product/category/{categoryId}`: Lấy danh sách Product theo Category
- `GET /api/product/search?name={name}&page={page}&size={size}`: Tìm kiếm và phân trang Product
- `POST /api/product/addProduct`: Thêm mới Product (Multipart FormData: `productName`, `unitPrice`, `discount`, `quantity`, `description`, `status`, `categoryId`, `imageFile`)
- `PUT /api/product/updateProduct`: Cập nhật Product (Multipart FormData)
- `DELETE /api/product/deleteProduct?productId={id}`: Xóa Product theo ID

## 5. Truy cập ứng dụng
- **Giao diện Web AJAX**: [http://localhost:8080/](http://localhost:8080/)
- **Swagger 3 UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **OpenAPI JSON Docs**: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)
