import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import {Routes, Route, BrowserRouter} from 'react-router-dom';
import App from './App.tsx'
import Login from './components/login/login.tsx'

createRoot(document.getElementById('root')!).render(
  <StrictMode >
      <BrowserRouter>
      <Routes>
          <Route path="/" element={<App />} />
          <Route path="/login" element={<Login />} />
          {/* <Route path="/register" element={<Register />} /> */}
      </Routes>
      </BrowserRouter>
  </StrictMode>,
)
