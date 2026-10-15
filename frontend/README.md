# SkillBridge frontend

React/Vite shell for the supplied SkillBridge Spatial Control Surface prototype.
The original 30-screen UI is preserved at `public/spatial-ui.html` and rendered
by the React application without visual changes.

## Run locally

Install dependencies and start Vite:

```powershell
npm install
npm run dev
```

Set `VITE_API_BASE_URL` in `frontend/.env` when the backend is not running at
`http://localhost:8080`. The frontend checks `GET /api/status` and displays the
connection state in the lower-right corner.
