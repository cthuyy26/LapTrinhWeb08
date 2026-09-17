# BT08 - Spring Boot 3 GraphQL API với Thymeleaf & AJAX

## 1. Giới thiệu
Dự án thực hiện theo yêu cầu **Mục 5** của bài tập Lập trình Web - HCMUTE:
- Xây dựng API với **Spring for GraphQL** trên nền Spring Boot 3.
- Giao diện người dùng sử dụng **Thymeleaf** kết hợp **AJAX** gửi truy vấn GraphQL đến endpoint `/graphql`:
  - **Trang chủ (`/`)**: 
    - Hiển thị tất cả sản phẩm có giá từ thấp đến cao (mặc định ASC) với nút chuyển đổi linh hoạt.
    - Lọc và hiển thị tất cả sản phẩm của 01 Category bất kỳ được chọn.
    - Bộ Inspector hiển thị trực quan GraphQL Query và Response JSON nhận về trong thời gian thực.
  - **Trang quản trị (`/admin`)**:
    - CRUD đầy đủ cho bảng **Product** qua GraphQL Mutation.
    - CRUD đầy đủ cho bảng **Category** qua GraphQL Mutation.
    - Tìm kiếm và phân trang theo thời gian thực cho cả **Product** và **Category**.
  - **GraphiQL IDE (`/graphiql`)**: Công cụ test query và mutation trực quan.

## 2. Công nghệ sử dụng
- **Java**: 17
- **Framework**: Spring Boot 3.2.5
- **GraphQL Engine**: Spring for GraphQL (`spring-boot-starter-graphql`)
- **Template Engine**: Thymeleaf (`spring-boot-starter-thymeleaf`)
- **Database**: Microsoft SQL Server (Spring Data JPA)
- **Frontend**: HTML5, Bootstrap 5, FontAwesome 6, jQuery AJAX, SweetAlert2

## 3. Cấu hình cơ sở dữ liệu
Trong file [application.properties](file:///src/main/resources/application.properties):
```properties
server.port=8081
spring.datasource.url=jdbc:sqlserver://localhost:1434;databaseName=BT08_DB;encrypt=true;trustServerCertificate=true
spring.datasource.username=sa
spring.datasource.password=123
spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver
spring.jpa.hibernate.ddl-auto=update
spring.graphql.graphiql.enabled=true
spring.graphql.graphiql.path=/graphiql
```

## 4. GraphQL Schema & Operations

### Queries:
```graphql
# 1. Hiển thị tất cả product có price từ thấp đến cao (order: "ASC" | "DESC")
allProductsSortedByPrice(order: String = "ASC"): [Product]!

# 2. Lấy tất cả product của 01 category
productsByCategory(categoryId: ID!): [Product]!

# 3. Phân trang & tìm kiếm Product
productsPage(name: String, page: Int = 0, size: Int = 6): ProductPage!

# 4. Phân trang & tìm kiếm Category
categoriesPage(name: String, page: Int = 0, size: Int = 5): CategoryPage!
```

### Mutations:
```graphql
# Category Mutations
createCategory(input: CategoryInput!): Category!
updateCategory(id: ID!, input: CategoryInput!): Category!
deleteCategory(id: ID!): Boolean!

# Product Mutations
createProduct(input: ProductInput!): Product!
updateProduct(id: ID!, input: ProductInput!): Product!
deleteProduct(id: ID!): Boolean!
```

## 5. Truy cập ứng dụng
- **Trang chủ Storefront**: [http://localhost:8081/](http://localhost:8081/)
- **Trang Quản trị CRUD & Phân trang**: [http://localhost:8081/admin](http://localhost:8081/admin)
- **GraphiQL Playground**: [http://localhost:8081/graphiql](http://localhost:8081/graphiql)
- **GraphQL Endpoint**: `POST http://localhost:8081/graphql`
