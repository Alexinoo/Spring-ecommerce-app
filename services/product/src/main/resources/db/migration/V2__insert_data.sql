-- Category Inserts (aligned with the sequence increment)
INSERT INTO category (id, name, description)
VALUES
    (nextval('category_seq'), 'Electronics', 'Devices and gadgets'),
    (nextval('category_seq'), 'Books', 'Printed and digital books'),
    (nextval('category_seq'), 'Clothing', 'Mens and womens clothing');

-- Product Inserts (aligned with the sequence increment)
INSERT INTO product (id, name, description, available_quantity, price, category_id)
VALUES
    (nextval('product_seq'), 'Smartphone', 'Latest model with 5G support', 150, 699.99, 1), -- Electronics
    (nextval('product_seq'), 'Laptop', 'Powerful laptop for work and play', 50, 1200.00, 1), -- Electronics
    (nextval('product_seq'), 'Fiction Book', 'Bestselling fiction novel', 200, 19.99, 51), -- Books
    (nextval('product_seq'), 'Jeans', 'Comfortable blue jeans', 100, 49.99, 101), -- Clothing
    (nextval('product_seq'), 'T-Shirt', 'Casual cotton t-shirt', 300, 14.99, 101); -- Clothing
