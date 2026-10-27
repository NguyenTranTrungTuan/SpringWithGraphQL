-- ==========================================================
-- 1. TẠO CẤU TRÚC BẢNG (DDL)
-- ==========================================================

-- Bảng Tác giả (authors)
CREATE TABLE authors (
    author_id BIGSERIAL PRIMARY KEY,
    author_name VARCHAR(100) NOT NULL,
    bio TEXT
);

-- Bảng Sách (books)
CREATE TABLE books (
    book_id BIGSERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    price NUMERIC(10, 2) NOT NULL CHECK (price >= 0),
    stock_quantity INT DEFAULT 0 CHECK (stock_quantity >= 0),
    published_year INT,
    author_id BIGINT REFERENCES authors(author_id) ON DELETE SET NULL
);

-- Bảng Khách hàng (customers)
CREATE TABLE customers (
    customer_id BIGSERIAL PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(15),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Bảng Đơn hàng (orders)
CREATE TABLE orders (
    order_id BIGSERIAL PRIMARY KEY,
    customer_id BIGINT REFERENCES customers(customer_id) ON DELETE CASCADE,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount NUMERIC(10, 2) DEFAULT 0
);

-- Bảng Chi tiết đơn hàng (order_items)
CREATE TABLE order_items (
    item_id BIGSERIAL PRIMARY KEY,
    order_id BIGINT REFERENCES orders(order_id) ON DELETE CASCADE,
    book_id BIGINT REFERENCES books(book_id) ON DELETE RESTRICT,
    quantity INT NOT NULL CHECK (quantity > 0),
    price NUMERIC(10, 2) NOT NULL
);


-- ==========================================================
-- 2. DỮ LIỆU MẪU
-- ==========================================================

-- Thêm danh sách tác giả
INSERT INTO authors (author_name, bio) VALUES
    ('Nguyễn Nhật Ánh', 'Nhà văn chuyên viết truyện cho thanh thiếu niên, nổi tiếng với phong cách nhẹ nhàng, hoài niệm.'),
    ('J.K. Rowling', 'Tác giả người Anh, nổi tiếng toàn cầu với series tiểu thuyết kỳ ảo Harry Potter.'),
    ('Dale Carnegie', 'Chuyên gia huấn luyện kỹ năng giao tiếp, tác giả cuốn sách kinh điển Đắc Nhân Tâm.'),
    ('Dan Brown', 'Nhà văn Mỹ nổi tiếng với các tiểu thuyết trinh thám giả tưởng và mật mã lịch sử.');

-- Thêm danh sách sách
INSERT INTO books (title, price, stock_quantity, published_year, author_id) VALUES
    ('Mắt Biếc', 110000, 45, 1990, 1),
    ('Cho Tôi Xin Một Vé Đi Tuổi Thơ', 95000, 30, 2008, 1),
    ('Harry Potter và Hòn Đá Phù Thủy', 180000, 25, 1997, 2),
    ('Harry Potter và Phòng Bí Mật', 190000, 20, 1998, 2),
    ('Đắc Nhân Tâm', 86000, 60, 1936, 3),
    ('Mật Mã Da Vinci', 150000, 15, 2003, 4);

-- Thêm danh sách khách hàng
INSERT INTO customers (full_name, email, phone) VALUES
    ('Nguyễn Văn An', 'an.nguyen@example.com', '0901112233'),
    ('Trần Thị Bình', 'binh.tran@example.com', '0912223344'),
    ('Lê Hoàng Cường', 'cuong.le@example.com', '0983334455');

-- Thêm đơn hàng
INSERT INTO orders (customer_id, total_amount) VALUES (1, 290000);
INSERT INTO orders (customer_id, total_amount) VALUES (2, 276000);
INSERT INTO orders (customer_id, total_amount) VALUES (3, 190000);

-- Thêm chi tiết cho từng đơn hàng
INSERT INTO order_items (order_id, book_id, quantity, price) VALUES
    (1, 1, 1, 110000),
    (1, 3, 1, 180000);

INSERT INTO order_items (order_id, book_id, quantity, price) VALUES
    (2, 2, 1, 95000),
    (2, 5, 2, 86000);

INSERT INTO order_items (order_id, book_id, quantity, price) VALUES
    (3, 4, 1, 190000);