INSERT INTO `fooddelivery`.`ADDRESS` (`address_line`, `city`, `state`, `zip_code`) VALUES
('123 Main St', 'New York', 'NY', 10001),
('456 Market St', 'San Francisco', 'CA', 94105),
('789 Elm St', 'Chicago', 'IL', 60601),
('101 Pine St', 'Houston', 'TX', 77001),
('202 Oak St', 'Seattle', 'WA', 98101);

-----
INSERT INTO `fooddelivery`.`USERS` (name, email, password_hash, phone, role, address_id, created_at) VALUES
('John Doe', 'johndoe@example.com', 'hashedpassword1', '1234567890', 'customer', 1, NOW()),
('Jane Smith', 'janesmith@example.com', 'hashedpassword2', '0987654321', 'restaurant_owner', 2, NOW()),
('Alice Johnson', 'alicej@example.com', 'hashedpassword3', '5555555555', 'delivery_partner', 3, NOW()),
('Bob Brown', 'bobb@example.com', 'hashedpassword4', '6666666666', 'customer', 4, NOW()),
('Charlie Davis', 'charlied@example.com', 'hashedpassword5', '7777777777', 'restaurant_owner', 5, NOW());

-----
INSERT INTO `fooddelivery`.`RESTAURANTS` (name, address_id, owner_id, cuisine_type, hours_of_operation, created_at) VALUES
('Pizza Place', 2, 2, 'Italian', '11:00:00', NOW()),
('Sushi Spot', 1, 5, 'Japanese', '12:00:00', NOW()),
('Burger Joint', 3, 4, 'American', '10:00:00', NOW()),
('Taco Truck', 4, 3, 'Mexican', '09:00:00', NOW()),
('Salad Bar', 5, 1, 'Healthy', '08:00:00', NOW());
