import React, { useState, useEffect } from 'react';

interface HealthStatus {
    message: string;
    status: string;
}

const HealthCheck: React.FC = () => {
    const [health, setHealth] = useState<HealthStatus | null>(null);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        fetch('http://localhost:8080/api/health')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                setHealth(data);
                setLoading(false);
            })
            .catch(error => {
                setError('Failed to fetch health status');
                setLoading(false);
            });
    }, []);

    if (loading) return <div>Loading...</div>;
    if (error) return <div>Error: {error}</div>;
    if (!health) return <div>No health data available</div>;

    return (
        <div>
            <h2>Health Status</h2>
            <p>Message: {health.message}</p>
            <p>Status: {health.status}</p>
        </div>
    );
};

export default HealthCheck;