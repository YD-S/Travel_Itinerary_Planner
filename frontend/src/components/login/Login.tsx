import { useState } from "react";
import { FiUser, FiLock, FiEye, FiEyeOff } from "react-icons/fi";
import { Navigate } from "react-router-dom";
import LoginBgLight from "../../assets/png/LoginBgLight.png";
import LoginBgDark from "../../assets/png/LoginBgDark.png";
import { useTheme } from "../../hooks/ThemeContext.tsx";
import { useAuth } from "../../hooks/useAuth.tsx";
import ThemeButton from "../ThemeChanger/ThemeButton.tsx";
import * as React from "react";

function Login() {
    const [showPassword, setShowPassword] = useState(false);
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");
    const { theme } = useTheme();
    const { login, isAuthenticated, isLoading } = useAuth();

    const bgImage = theme === "dark" ? LoginBgDark : LoginBgLight;

    if (isAuthenticated) {
        return <Navigate to="/" replace />;
    }

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setError("");

        if (!username.trim() || !password.trim()) {
            setError("Please fill in all fields");
            return;
        }
        const success = await login(username, password);

        if (!success) {
            setError("Invalid username or password. Please try again.");
        }
    };

    return (
        <div
            className="login bg-tonal-a0 w-screen h-screen flex items-center justify-center"
            style={{
                backgroundImage: `url(${bgImage})`,
                backgroundSize: "cover",
                backgroundPosition: "center",
            }}
        >
            <div className="rounded-lg p-5 w-[28rem] bg-tonal-a0 shadow-2xl backdrop-blur-xl">
                <h1 className="font-bold text-center text-[40px] mb-5">Login</h1>

                {error && (
                    <div className="mb-4 p-3 bg-red-100 border border-red-300 text-red-700 rounded-lg text-center">
                        {error}
                    </div>
                )}

                <form className="flex flex-col gap-4" onSubmit={handleSubmit}>
                    <div className="text-[20px]">
                        <p className="flex items-center gap-2">
                            <FiUser className="text-icon-primary" />
                            Username:
                        </p>
                        <input
                            name="username"
                            className="mb-2 border rounded-lg w-full p-2"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            disabled={isLoading}
                            placeholder="Enter your username"
                            required
                        />
                    </div>

                    <div className="text-[20px]">
                        <p className="flex items-center gap-2">
                            <FiLock className="text-icon-primary" />
                            Password:
                        </p>
                        <div className="relative">
                            <input
                                name="password"
                                type={showPassword ? "text" : "password"}
                                className="border rounded-lg w-full pr-10 p-2"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                disabled={isLoading}
                                placeholder="Enter your password"
                                required
                            />
                            <button
                                type="button"
                                className="absolute right-2 top-1/2 -translate-y-1/2 text-xl text-icon-tonal hover:text-icon-primary transition-colors"
                                onClick={() => setShowPassword((v) => !v)}
                                tabIndex={-1}
                                disabled={isLoading}
                            >
                                {showPassword ? <FiEyeOff /> : <FiEye />}
                            </button>
                        </div>
                    </div>

                    <p className="p-1 text-[18px] text-right cursor-pointer hover:text-primary-a50 transition-colors">
                        Forgot password?
                    </p>

                    <div className="flex justify-center">
                        <button
                            className={`w-fit px-6 py-2 bg-primary-a40 border border-primary-a50 rounded-lg text-[20px] transition-all ${
                                isLoading
                                    ? 'opacity-50 cursor-not-allowed'
                                    : 'hover:bg-primary-a50 hover:shadow-lg'
                            }`}
                            id="login"
                            type="submit"
                            disabled={isLoading}
                        >
                            {isLoading ? "Signing in..." : "Sign in"}
                        </button>
                    </div>

                    <div>
                        <span className="flex items-center gap-1 text-[18px] justify-center">
                            <p>Don't have an account?</p>
                            <button
                                type="button"
                                className="text-gray-500 p-1 hover:text-primary-a50 transition-colors"
                                onClick={() => {
                                    // navigate('/register');
                                    console.log('Navigate to signup');
                                }}
                            >
                                Sign up
                            </button>
                        </span>
                    </div>
                </form>
            </div>
            <ThemeButton />
        </div>
    );
}

export default Login;