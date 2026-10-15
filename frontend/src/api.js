import axios from 'axios';

const api = axios.create({
  baseURL: (import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080').replace(/\/$/, ''),
  headers: { 'Content-Type': 'application/json' },
});

export const authApi = {
  signup: (payload) => api.post('/api/auth/signup', payload),
  signin: (payload) => api.post('/api/auth/signin', payload),
};

export const pageApi = {
  status: () => api.get('/api/status'),
  dashboard: () => api.get('/api/dashboard'),
  projects: () => api.get('/api/projects'),
  modules: () => api.get('/api/learning/modules'),
  skills: () => api.get('/api/skills'),
  users: () => api.get('/api/users'),
};

export function apiError(error) {
  return error.response?.data?.detail || error.response?.data?.message || error.message || 'Request failed';
}

export default api;
