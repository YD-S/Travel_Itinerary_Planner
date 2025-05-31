import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import {Routes, Route, BrowserRouter} from 'react-router-dom';
import App from './App.tsx'
import Login from './components/login/login.tsx'
import {ThemeProvider} from "./hooks/ThemeContext.tsx";
import {AuthProvider} from "./hooks/useAuth.tsx";
import {PrivateRoute} from "./components/PrivateRoute.tsx";

createRoot(document.getElementById('root')!).render(
  <StrictMode >
      <ThemeProvider>
          <AuthProvider>
          <BrowserRouter>
          <Routes>
              <Route path="/" element={
                  <PrivateRoute>
                    <App />
                  </PrivateRoute>
              } />
              <Route path="/login" element={<Login />} />
              {/* <Route path="/register" element={<Register />} /> */}
          </Routes>
          </BrowserRouter>
          </AuthProvider>
      </ThemeProvider>
  </StrictMode>,
)
