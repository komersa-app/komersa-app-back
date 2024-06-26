INSERT INTO car (id, color, description, motor_type, name, power, status, type, model, brand_id, price_id)
VALUES
(1, 'Red', 'A classic sports car.', 'Gasoline', 'Sports Car', 200, 'pinned', 'Sedan','Corolla', 101, 201),
(2, 'Blue', 'A luxury sedan.', 'Diesel', 'Luxury Sedan', 150, 'not_pinned', 'Sedan', '3 series', 102, 202),
(3, 'Green', 'An eco-friendly hatchback.', 'Electric', 'Hatchback', 100, 'not_pinned', 'Hatchback', 'Model S', 103, 203),
(4, 'Black', 'A high-performance SUV.', 'Hybrid', 'SUV', 300, 'pinned', 'SUV', 'CR-V', 104, 204),
(5, 'White', 'A compact crossover.', 'Gasoline', 'Crossover', 175, 'not_pinned', 'Crossover', 'Escape', 105, 205),

-- Cars with same brand but different
(6, 'Silver', 'A sleek sedan.', 'Gasoline', 'Sedan', 180, 'pinned', 'Sedan', 'Camry', 101, 206),
(7, 'Gray', 'A rugged pickup truck.', 'Diesel', 'Pickup Truck', 250, 'not_pinned', 'Pickup Truck', 'Silverado', 106, 207),

-- Cars with same model but different
(8, 'Yellow', 'A fast sports car.', 'Gasoline', 'Sports Car', 220, 'not_pinned', 'Coupe', '488 GTB', 107, 208),
(9, 'Purple', 'A luxurious sedan.', 'Diesel', 'Sedan', 160, 'not_pinned', 'Sedan', 'E-Class', 108, 209),

-- Cars with same type but different
(10, 'Orange', 'An efficient hatchback.', 'Electric', 'Hatchback', 90, 'not_pinned', 'Hatchback', 'Leaf', 109, 210),
(11, 'Pink', 'A powerful SUV.', 'Hybrid', 'SUV', 320, 'not_pinned', 'SUV', 'Ascent', 110, 211),

-- Cars with same motor_type but different
(12, 'Brown', 'A stylish coupe.', 'Gasoline', 'Coupe', 210, 'not_pinned', 'Coupe', '911 Carrera', 112, 200),
(13, 'Gold', 'A fuel-efficient sedan.', 'Diesel', 'Sedan', 140, 'not_pinned', 'Sedan', 'Passat', 110, 212),

-- Continuing from ID 14
(14, 'Silver', 'A sporty convertible.', 'Gasoline', 'Convertible', 190, 'not_pinned', 'Convertible', 'TT', 113, 213),
(15, 'Gray', 'A luxury van.', 'Diesel', 'Van', 150, 'not_pinned', 'Van', 'Vito', 108, 214),
(16, 'Blue', 'An electric city car.', 'Electric', 'City Car', 85, 'not_pinned', 'City Car', 'Zoe', 114, 216),
(17, 'Gold', 'A powerful diesel pickup.', 'Diesel', 'Pickup Truck', 300, 'not_pinned', 'Pickup Truck', 'Tahoe', 106, 217),
(18, 'White', 'A fuel-efficient sedan.', 'Diesel', 'Sedan', 140, 'not_pinned', 'Sedan', 'Passat', 115, 218),
(19, 'Black', 'A sleek hybrid crossover.', 'Hybrid', 'Crossover', 220, 'not_pinned', 'Crossover', 'RAV4', 101, 219),
(20, 'Green', 'A fast electric sports car.', 'Electric', 'Sports Car', 250, 'not_pinned', 'Coupe', '488 GTB', 107, 220);
