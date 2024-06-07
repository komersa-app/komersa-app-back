INSERT INTO public.price (id, amount, change_datetime)
VALUES
    (201, 25000.00, NOW() - INTERVAL '1 month'),
    (202, 27000.00, NOW() - INTERVAL '2 months'),
    (203, 29000.00, NOW() - INTERVAL '3 months'),
    (204, 31000.00, NOW() - INTERVAL '4 months'),
    (205, 33000.00, NOW() - INTERVAL '5 months'),
    (206, 35000.00, NOW() - INTERVAL '6 months'),
    (207, 37000.00, NOW() - INTERVAL '7 months'),
    (208, 39000.00, NOW() - INTERVAL '8 months'),
    (209, 41000.00, NOW() - INTERVAL '9 months'),
    (210, 43000.00, NOW() - INTERVAL '10 months');
