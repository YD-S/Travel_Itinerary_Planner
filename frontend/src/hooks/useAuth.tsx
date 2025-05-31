import { createContext, useContext, useState, useEffect, type ReactNode } from 'react';
import {type AxiosResponse} from "axios";
import api from '../api/axios.ts';


interface User {
    id: string;
    email: string;
    username: string;
}

interface Auth {
    token: string;
}

interface AuthResponse {
    user: User;
    auth: Auth;
    message?: string;
}

interface AuthContextType {
    user: User | null;
    token: string | null;
    isAuthenticated: boolean;
    isLoading: boolean;
    login: (userName: string, password: string) => Promise<boolean>;
    logout: () => void;
    signup: (email: string, password: string, userName: string) => Promise<boolean>;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider = ({ children } : { children: ReactNode }) => {
    const [user, setUser] = useState<User | null>(null);
    const [token, setToken] = useState<string | null>(null);
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        const savedToken = localStorage.getItem('authToken');
        const savedUser = localStorage.getItem('authUser');

        if (savedToken && savedUser) {
            setToken(savedToken);
            setUser(JSON.parse(savedUser));
        }

        setIsLoading(false);
    }, []);

    const checkResponse = async (response: AxiosResponse<AuthResponse>) => {
        const { user, auth } = response.data;
        const authToken = auth?.token;

        if (!user || !authToken) {
            throw new Error('Invalid response: missing user or token');
        }

        setUser(user);
        setToken(authToken);

        localStorage.setItem('authToken', authToken);
        localStorage.setItem('authUser', JSON.stringify(user));

        return true;
    }


    const login = async (username: string, password: string): Promise<boolean> => {
        setIsLoading(true);
        try {
            const response = await api.post('/api/auth/login', {
                username,
                password,
            })
            await checkResponse(response);
        } catch (error) {
            console.error('Login error:', error);
            setIsLoading(false);
            return false;
        }
        setIsLoading(false);
        return true;
    };


    const signup = async (email: string, password: string, userName: string): Promise<boolean> => {
        setIsLoading(true);
        try {
            const response = await api.post('/api/auth/signup', {
                email,
                password,
                userName,
            })
            await checkResponse(response);
        } catch (error) {
            console.error('Signup error:', error);
            setIsLoading(false);
            return false;
        }
        setIsLoading(false);
        return true;
    };


    const logout = () => {
        setUser(null);
        setToken(null);
        localStorage.removeItem('authToken');
        localStorage.removeItem('authUser');
    };

    const value: AuthContextType = {
        user,
        token,
        isAuthenticated: !!user && !!token,
        isLoading,
        login,
        logout,
        signup,
    };

    return (
        <AuthContext.Provider value={value}>
            {children}
        </AuthContext.Provider>
    );
};


export const useAuth = (): AuthContextType => {
    const context = useContext(AuthContext);

    if (context === undefined) {
        throw new Error('useAuth must be used within an AuthProvider');
    }

    return context;
};
