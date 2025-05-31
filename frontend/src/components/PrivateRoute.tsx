import {Navigate} from "react-router-dom";
import type {ReactNode} from "react";
import {useAuth} from "../hooks/useAuth.tsx";


export const PrivateRoute = ({ children }: { children: ReactNode }) => {
    const { isAuthenticated, isLoading } = useAuth();

    if (isLoading) {
        return <div>Loading...</div>;
    }

    if (isAuthenticated ) {
        return <>{children}</>;
    }

    return <Navigate to="/login" />
}