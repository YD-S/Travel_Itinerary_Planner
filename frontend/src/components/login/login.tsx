function Login() {
    return (
        <div className="login">
            <div className="bg-tonal-a10 justify-center mx-auto my-5 rounded-lg w-fit h-auto px-5 py-5">
                <h1 className="font-bold text-center text-3xl mb-5">Login</h1>
                <p>Username:</p>
                <form>
                    <input name="username" className="mb-2 border rounded-lg"/>
                </form>
                <p>Password:</p>
                <form>
                    <input name="password" className="border rounded-lg"/>
                </form>
                <p className="p-1 text-sm text-right">Forgot password?</p>
                <div className="flex justify-center">
                    <button className="my-5 w-fit p-1 bg-primary-a40 border border-primary-a50 rounded-lg" id="login" type="submit">
                        Sign in
                    </button>
                </div>
                <div>
                    <span className="flex items-center gap-1 text-sm">
                        <p>Don't have an account?</p>
                        <button className="text-gray-500 p-1">Sign up</button>
                    </span>
                </div>
            </div>
        </div>
    )
}

export default Login;