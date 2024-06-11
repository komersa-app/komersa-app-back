INSERT INTO car (id, color, description, motor_type, name, power, status, type, details_id, price_id)
VALUES
(1, 'Red', 'A classic sports car.', 'Gasoline', 'Sports Car', 200, 'pinned', 'Sedan', 101, 201),
(2, 'Blue', 'A luxury sedan.', 'Diesel', 'Luxury Sedan', 150, 'not_pinned', 'Sedan', 112, 202),
(3, 'Green', 'An eco-friendly hatchback.', 'Electric', 'Hatchback', 100, 'not_pinned', 'Hatchback', 113, 203),
(4, 'Black', 'A high-performance SUV.', 'Hybrid', 'SUV', 300, 'pinned', 'SUV', 114, 204),
(5, 'White', 'A compact crossover.', 'Gasoline', 'Crossover', 175, 'not_pinned', 'Crossover', 115, 205),

-- Cars with same brand but different
(6, 'Silver', 'A sleek sedan.', 'Gasoline', 'Sedan', 180, 'New', 'Sedan', 116, 206),
(7, 'Gray', 'A rugged pickup truck.', 'Diesel', 'Pickup Truck', 250, 'not_pinned', 'Pickup Truck', 117, 207),

-- Cars with same model but different
(8, 'Yellow', 'A fast sports car.', 'Gasoline', 'Sports Car', 220, 'not_pinned', 'Coupe', 118, 208),
(9, 'Purple', 'A luxurious sedan.', 'Diesel', 'Sedan', 160, 'not_pinned', 'Sedan', 119, 209),

-- Cars with same type but different
(10, 'Orange', 'An efficient hatchback.', 'Electric', 'Hatchback', 90, 'not_pinned', 'Hatchback', 120, 210),
(11, 'Pink', 'A powerful SUV.', 'Hybrid', 'SUV', 320, 'Used', 'SUV', 121, 211),

-- Cars with same motor_type but different
(12, 'Brown', 'A stylish coupe.', 'Gasoline', 'Coupe', 210, 'not_pinned', 'Coupe', 122, 200),
(13, 'Gold', 'A fuel-efficient sedan.', 'Diesel', 'Sedan', 140, 'not_pinned', 'Sedan', 110, 212),

-- Continuing from ID 14
(14, 'Silver', 'A sporty convertible.', 'Gasoline', 'Convertible', 190, 'not_pinned', 'Convertible', 123, 213),
(15, 'Gray', 'A luxury van.', 'Diesel', 'Van', 150, 'Used', 'Van', 124, 214),
(16, 'Blue', 'An electric city car.', 'Electric', 'City Car', 85, 'New', 'City Car', 125, 216),
(17, 'Gold', 'A powerful diesel pickup.', 'Diesel', 'Pickup Truck', 300, 'not_pinned', 'Pickup Truck', 126, 217),
(18, 'White', 'A fuel-efficient sedan.', 'Diesel', 'Sedan', 140, 'Used', 'Sedan', 110, 218),
(19, 'Black', 'A sleek hybrid crossover.', 'Hybrid', 'Crossover', 220, 'not_pinned', 'Crossover', 127, 219),
(20, 'Green', 'A fast electric sports car.', 'Electric', 'Sports Car', 250, 'not_pinned', 'Coupe', 118, 220);
