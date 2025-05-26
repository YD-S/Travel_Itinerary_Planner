import { useTheme } from "../../hooks/ThemeContext"
import { Sun, Moon } from "lucide-react"
import clsx from "clsx"

export default function ThemeButton() {
    const { theme, toggleTheme } = useTheme()

    return (
        <button
            onClick={toggleTheme}
            aria-label="Toggle theme"
            className={clsx(
                "fixed bottom-4 right-4 z-50 p-2 rounded-full shadow-md transition-colors duration-300",
                "bg-icon-tonal text-icon-primary",
            )}
        >
            <div className="relative w-6 h-6">
                <Sun
                    className={clsx(
                        "absolute inset-0 transition-all duration-300 transform",
                        theme === "dark"
                            ? "opacity-0 scale-50 rotate-90"
                            : "opacity-100 scale-100 rotate-0"
                    )}
                />
                <Moon
                    className={clsx(
                        "absolute inset-0 transition-all duration-300 transform",
                        theme === "dark"
                            ? "opacity-100 scale-100 rotate-0"
                            : "opacity-0 scale-50 -rotate-90"
                    )}
                />
            </div>
        </button>
    )
}
