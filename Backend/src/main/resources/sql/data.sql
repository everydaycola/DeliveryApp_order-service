-- Disable constraints for seeding
SET session_replication_role = 'replica';

-- accepted order at Pasta Palace
INSERT INTO orders (status, order_id, restaurant_id)
VALUES (0, '550e8400-e29b-41d4-a716-446655440000', 'dab961f7-5441-4827-a55e-7fcbc86a8fb2');

-- Spaghetti Bolognese
INSERT INTO order_lines (quantity, dish_id, order_id)
VALUES (3, '27756a48-0b85-4499-aadb-c08a97943263', '550e8400-e29b-41d4-a716-446655440000');

-- Penne Arrabbiata
INSERT INTO order_lines (quantity, dish_id, order_id)
VALUES (1, '98be5020-eb67-49e0-815e-3b2f7e1808e0', '550e8400-e29b-41d4-a716-446655440000');


-- accepted order at Sushi world
INSERT INTO orders (status, order_id, restaurant_id)
VALUES (6, '550e8400-e29b-41d4-a716-446655440001', '7f3037c6-a58d-402b-bcfc-def8157fdbfc');

-- California Roll
INSERT INTO order_lines (quantity, dish_id, order_id)
VALUES (30, '18cb4eec-8ccb-4f65-a20a-dced4e1b0e4b', '550e8400-e29b-41d4-a716-446655440001');

-- Enable constraints after seeding
SET session_replication_role = 'origin';