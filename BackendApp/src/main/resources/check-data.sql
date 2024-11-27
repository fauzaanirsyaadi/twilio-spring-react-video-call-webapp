SELECT
    (SELECT COUNT(*) FROM users) AS user_count,
    (SELECT COUNT(*) FROM queues) AS queue_count,
    (SELECT COUNT(*) FROM admins) AS admin_count,
    (SELECT COUNT(*) FROM calls) AS call_count,
    (SELECT COUNT(*) FROM logs) AS log_count;