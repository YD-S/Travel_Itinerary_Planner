import { useAuth } from './hooks/useAuth';
import { useTheme } from './hooks/ThemeContext';
import DisplayTrips from "./components/trips.tsx";

function App() {
    const { user, logout } = useAuth();
    const { theme, toggleTheme } = useTheme();

    return (
        <div className={`app ${theme}`}>
            <header>
                <h1>Welcome, {user?.username}!</h1>
                <button onClick={toggleTheme}>
                    Switch to {theme === 'light' ? 'dark' : 'light'} mode
                </button>
                <button onClick={logout}>Logout</button>
            </header>

            <main>
                <p>You are successfully logged in!</p>
                <DisplayTrips />
            </main>
        </div>
    );
}

export default App;