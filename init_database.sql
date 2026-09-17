-- =============================================
-- SQL SCRIPT KHỞI TẠO DATABASE VÀ DỮ LIỆU MẪU
-- Môn: Lập Trình Web - Bài tập 08 (HCMUTE)
-- =============================================

CREATE DATABASE BT08_DB;
GO

USE BT08_DB;
GO

-- 1. Bảng Category
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'categories')
BEGIN
    CREATE TABLE categories (
        categoryId BIGINT IDENTITY(1,1) PRIMARY KEY,
        categoryName NVARCHAR(255) NOT NULL,
        icon NVARCHAR(500) NULL
    );
END
GO

-- 2. Bảng Product
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'products')
BEGIN
    CREATE TABLE products (
        productId BIGINT IDENTITY(1,1) PRIMARY KEY,
        productName NVARCHAR(255) NOT NULL,
        quantity INT NOT NULL DEFAULT 0,
        unitPrice FLOAT NOT NULL DEFAULT 0,
        discount FLOAT NULL DEFAULT 0,
        images NVARCHAR(500) NULL,
        description NVARCHAR(MAX) NULL,
        status SMALLINT DEFAULT 1,
        createDate DATETIME DEFAULT GETDATE(),
        categoryId BIGINT NOT NULL,
        CONSTRAINT FK_Product_Category FOREIGN KEY (categoryId) REFERENCES categories(categoryId) ON DELETE CASCADE
    );
END
GO

-- 3. Chèn dữ liệu mẫu Categories
SET IDENTITY_INSERT categories ON;
INSERT INTO categories (categoryId, categoryName, icon) VALUES
(1, N'Điện thoại thông minh', 'https://cdn-icons-png.flaticon.com/512/0/191.png'),
(2, N'Laptop & Máy tính', 'https://cdn-icons-png.flaticon.com/512/428/428001.png'),
(3, N'Máy tính bảng (Tablet)', 'https://cdn-icons-png.flaticon.com/512/689/689396.png'),
(4, N'Phụ kiện công nghệ', 'https://cdn-icons-png.flaticon.com/512/860/860330.png');
SET IDENTITY_INSERT categories OFF;
GO

-- 4. Chèn dữ liệu mẫu Products
SET IDENTITY_INSERT products ON;
INSERT INTO products (productId, productName, quantity, unitPrice, discount, images, description, status, createDate, categoryId) VALUES
(1, N'iPhone 15 Pro Max 256GB', 35, 31990000, 1000000, 'https://images.unsplash.com/photo-1695048133142-1a20484d2569?w=500', N'Thiết kế khung Titan chuẩn hàng không vũ trụ, chip A17 Pro mạnh mẽ nhất.', 1, GETDATE(), 1),
(2, N'Samsung Galaxy S24 Ultra 512GB', 20, 29990000, 2000000, 'https://images.unsplash.com/photo-1610945265064-0e34e5519bbf?w=500', N'Camera 200MP đột phá cùng quyền năng Galaxy AI đỉnh cao công nghệ.', 1, GETDATE(), 1),
(3, N'MacBook Pro 14 inch M3 Pro', 15, 49990000, 1500000, 'https://images.unsplash.com/photo-1517336714731-489689fd1ca8?w=500', N'Hiệu năng đột phá cho đồ họa và lập trình chuyên sâu với màn hình Liquid Retina XDR.', 1, GETDATE(), 2),
(4, N'Laptop Asus ROG Zephyrus G14', 12, 38990000, 1000000, 'https://images.unsplash.com/photo-1588872657578-7efd1f1555ed?w=500', N'Gaming laptop mỏng nhẹ tối thượng tích hợp RTX 4070 và màn OLED 3K.', 1, GETDATE(), 2),
(5, N'iPad Pro 11 inch M4 256GB Wifi', 25, 27490000, 500000, 'https://images.unsplash.com/photo-1544244015-0df4b3ffc6b0?w=500', N'Mỏng kỷ lục thế giới, màn hình OLED 2 lớp Tandem siêu sắc nét.', 1, GETDATE(), 3),
(6, N'Tai nghe Sony WH-1000XM5', 50, 7990000, 500000, 'https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=500', N'Công nghệ chống ồn đỉnh cao hàng đầu thế giới với âm thanh Hi-Res.', 1, GETDATE(), 4),
(7, N'Bàn phím cơ Keychron K3 Pro', 40, 2390000, 200000, 'https://images.unsplash.com/photo-1587829741301-dc798b83add3?w=500', N'Bàn phím cơ low-profile không dây đa kết nối Mac/Windows.', 1, GETDATE(), 4);
SET IDENTITY_INSERT products OFF;
GO
