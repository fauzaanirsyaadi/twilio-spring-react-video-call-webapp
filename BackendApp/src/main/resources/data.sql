-- Insert dummy users
INSERT INTO users (id, name, email, phone_number, identity_number) VALUES
(gen_random_uuid(), 'John Doe', 'john@example.com', '+62123456789', 'ID123456'),
(gen_random_uuid(), 'Jane Smith', 'jane@example.com', '+62987654321', 'ID654321'),
(gen_random_uuid(), 'Alice Johnson', 'alice@example.com', '+62111222333', 'ID111222');

-- Insert dummy admins
INSERT INTO admins (id, name, email, role) VALUES
(gen_random_uuid(), 'Admin One', 'admin1@example.com', 'kyc_officer'),
(gen_random_uuid(), 'Admin Two', 'admin2@example.com', 'supervisor');

-- Insert dummy queues
INSERT INTO queues (id, user_id, status, joined_at, processed_at, completed_at)
SELECT gen_random_uuid(), id, 'waiting', CURRENT_TIMESTAMP, NULL, NULL
FROM users
WHERE email IN ('john@example.com', 'jane@example.com');

-- Insert dummy calls
INSERT INTO calls (id, user_id, admin_id, room_name, start_time, end_time, call_status)
SELECT gen_random_uuid(), u.id, a.id, 'Room1', CURRENT_TIMESTAMP - INTERVAL '1 hour', CURRENT_TIMESTAMP, 'success'
FROM users u, admins a
WHERE u.email = 'john@example.com' AND a.email = 'admin1@example.com';

-- Insert dummy logs
INSERT INTO logs (id, user_id, admin_id, action)
SELECT gen_random_uuid(), u.id, a.id, 'Completed KYC Process'
FROM users u, admins a
WHERE u.email = 'john@example.com' AND a.email = 'admin1@example.com';

INSERT INTO logs (id, user_id, action)
SELECT gen_random_uuid(), id, 'Joined Queue'
FROM users
WHERE email = 'jane@example.com';