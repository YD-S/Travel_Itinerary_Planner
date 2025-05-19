import { useEffect, useState } from "react"

export function useTheme() {
    const [theme, setTheme] = useState<"light" | "dark">(() => {
        return (localStorage.getItem("theme") as "light" | "dark") || "light"
    })

    useEffect(() => {
        const root = document.documentElement
        if (theme === "dark") {
            root.setAttribute("data-theme", "dark")
        } else {
            root.removeAttribute("data-theme")
        }
        localStorage.setItem("theme", theme)
    }, [theme])

    const toggleTheme = () =>
        setTheme(prev => (prev === "dark" ? "light" : "dark"))

    return { theme, toggleTheme }
}
