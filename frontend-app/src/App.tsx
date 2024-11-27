import React from 'react';
import HealthCheck from './components/HealthCheck';

const App: React.FC = () => {
    return (
        <div className="App">
            <h1>Welcome to My React App with TypeScript</h1>
            <HealthCheck />
        </div>
    );
}

export default App;