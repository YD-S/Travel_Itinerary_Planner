import { useState } from "react";
import { FiUser, FiLock, FiEye, FiEyeOff } from "react-icons/fi";
import LoginBgLight from "../../assets/png/LoginBgLight.png";
import LoginBgDark from "../../assets/png/LoginBgDark.png";
import {useTheme} from "../../hooks/ThemeContext.tsx";
import ThemeButton from "../ThemeChanger/ThemeButton.tsx";

function Login() {
    const [showPassword, setShowPassword] = useState(false);
    const { theme } = useTheme();

    const bgImage = theme === "dark" ? LoginBgDark : LoginBgLight;

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
                <form className="flex flex-col gap-4">
                    <div className="text-[20px]">
                        <p className="flex items-center gap-2">
                            <FiUser className="text-icon-primary" />
                            Username:
                        </p>
                        <input name="username" className="mb-2 border rounded-lg w-full" />
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
                                className="border rounded-lg w-full pr-10"
                            />
                            <button
                                type="button"
                                className="absolute right-2 top-1/2 -translate-y-1/2 text-xl text-icon-tonal"
                                onClick={() => setShowPassword((v) => !v)}
                                tabIndex={-1}
                            >
                                {showPassword ? <FiEyeOff /> : <FiEye />}
                            </button>
                        </div>
                    </div>
                    <p className="p-1 text-[18px] text-right">Forgot password?</p>
                    <div className="flex justify-center">
                        <button className="w-fit px-2 py-1 bg-primary-a40 border border-primary-a50 rounded-lg text-[20px]" id="login" type="submit">
                            Sign in
                        </button>
                    </div>
                    <div>
                        <span className="flex items-center gap-1 text-[18px] justify-center">
                            <p>Don't have an account?</p>
                            <button className="text-gray-500 p-1">Sign up</button>
                        </span>
                    </div>
                </form>
            </div>
            <ThemeButton />
        </div>
    );
}

export default Login;