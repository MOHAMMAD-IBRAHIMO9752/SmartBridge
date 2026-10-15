import React, { useEffect, useState } from 'react';
import { createRoot } from 'react-dom/client';
import { apiError, authApi, pageApi } from './api';
import './styles.css';

const nav = [['⌂', 'Home'], ['◫', 'Projects'], ['▤', 'Learning'], ['◇', 'Skills'], ['⌕', 'AI Mentor'], ['♧', 'Notifications'], ['⚙', 'Settings']];

function App() {
  const [view, setView] = useState(localStorage.getItem('skillbridge_user') ? 'app' : 'landing');
  const [authMode, setAuthMode] = useState('signin');
  const [user, setUser] = useState(JSON.parse(localStorage.getItem('skillbridge_user') || 'null'));
  const [active, setActive] = useState('Home');
  const [dashboard, setDashboard] = useState(null);
  const [pageData, setPageData] = useState([]);
  const [apiState, setApiState] = useState('checking');
  const [error, setError] = useState('');

  useEffect(() => {
    pageApi.status().then(() => setApiState('connected')).catch(() => setApiState('offline'));
  }, []);

  useEffect(() => {
    if (view !== 'app') return;
    const request = active === 'Home' ? pageApi.dashboard() : active === 'Projects' ? pageApi.projects()
      : active === 'Learning' ? pageApi.modules() : active === 'Skills' ? pageApi.skills() : null;
    if (!request) return;
    request.then((response) => active === 'Home' ? setDashboard(response.data) : setPageData(response.data))
      .catch((reason) => setError(apiError(reason)));
  }, [view, active]);

  function handleAuth(nextUser) {
    localStorage.setItem('skillbridge_user', JSON.stringify(nextUser));
    setUser(nextUser); setView('app'); setError('');
  }

  if (view === 'landing') return <Landing onAuth={(mode) => { setAuthMode(mode); setView('auth'); }} apiState={apiState} />;
  if (view === 'auth') return <Auth mode={authMode} onMode={setAuthMode} onBack={() => setView('landing')} onSuccess={handleAuth} />;
  return <Shell user={user} active={active} setActive={(next) => { setActive(next); setPageData([]); setError(''); }} apiState={apiState} dashboard={dashboard} data={pageData} error={error} onLogout={() => { localStorage.removeItem('skillbridge_user'); setUser(null); setView('landing'); }} />;
}

function Landing({ onAuth, apiState }) {
  return <div className="public-page"><header className="public-nav"><Logo /><div><button className="link-button" onClick={() => onAuth('signin')}>Sign in</button><button className="primary-button" onClick={() => onAuth('signup')}>Create account <span>→</span></button></div></header><main className="landing-main"><section className="landing-copy"><span className="eyebrow">THE JOYFUL SKILL ECOSYSTEM</span><h1>Learn with purpose.<br /><em>Build with people.</em></h1><p>SkillBridge helps students discover their strengths, join meaningful projects, and turn every learning step into a real opportunity.</p><div className="landing-actions"><button className="primary-button" onClick={() => onAuth('signup')}>Start your journey <span>→</span></button><button className="outline-button" onClick={() => onAuth('signin')}>I already have an account</button></div><small className={`api-note ${apiState}`}>● {apiState === 'connected' ? 'Platform online' : apiState === 'offline' ? 'Connect backend to continue' : 'Checking platform'}</small></section><div className="landing-orbit"><div className="orbit-card card-a">✦<b>Learn</b><small>Build capabilities</small></div><div className="orbit-card card-b">◫<b>Build</b><small>Ship real projects</small></div><div className="orbit-card card-c">♧<b>Grow</b><small>Meet your people</small></div><div className="clay-planet">S</div></div></main><section className="feature-row"><Feature icon="◇" title="Map your skills" text="See what you know and what to learn next." /><Feature icon="◫" title="Find your fit" text="Join projects that match your direction." /><Feature icon="✦" title="Get guidance" text="Learn with a mentor that understands context." /></section></div>;
}

function Auth({ mode, onMode, onBack, onSuccess }) {
  const [form, setForm] = useState({ name: '', email: '', password: '' }); const [error, setError] = useState(''); const [busy, setBusy] = useState(false);
  async function submit(event) { event.preventDefault(); setError(''); if (form.password.length < 4) { setError('Password must be at least 4 characters.'); return; } setBusy(true); try { const response = mode === 'signup' ? await authApi.signup(form) : await authApi.signin({ email: form.email, password: form.password }); onSuccess(response.data); } catch (reason) { setError(apiError(reason)); } finally { setBusy(false); } }
  return <div className="auth-page"><button className="back-button" onClick={onBack}>← Back to SkillBridge</button><div className="auth-card clay-card"><Logo /><h1>{mode === 'signup' ? 'Create your account' : 'Welcome back'}</h1><p>{mode === 'signup' ? 'Start building your path with a community that gets it.' : 'Continue where your learning journey left off.'}</p><form onSubmit={submit}>{mode === 'signup' && <label>Full name<input required value={form.name} onChange={(e) => setForm({ ...form, name: e.target.value })} placeholder="Your name" /></label>}<label>Email address<input required type="email" value={form.email} onChange={(e) => setForm({ ...form, email: e.target.value })} placeholder="you@example.com" /></label><label>Password<input required minLength="4" type="password" value={form.password} onChange={(e) => setForm({ ...form, password: e.target.value })} placeholder="At least 4 characters" /></label>{error && <div className="form-error">{error}</div>}<button className="primary-button full" disabled={busy}>{busy ? 'Please wait…' : mode === 'signup' ? 'Create account →' : 'Sign in →'}</button></form><button className="switch-auth" onClick={() => { setError(''); onMode(mode === 'signup' ? 'signin' : 'signup'); }}>{mode === 'signup' ? 'Already have an account? Sign in' : 'New here? Create an account'}</button></div></div>;
}

function Shell({ user, active, setActive, apiState, dashboard, data, error, onLogout }) {
  return <div className="app"><aside className="sidebar"><Logo /><nav><p className="nav-label">Workspace</p>{nav.map(([icon, label]) => <button className={`nav-item ${active === label ? 'active' : ''}`} key={label} onClick={() => setActive(label)}><span>{icon}</span>{label}</button>)}</nav><div className="sidebar-help"><strong>Build something joyful.</strong><p>Your next opportunity can start with one small step.</p><button onClick={() => setActive('Projects')}>Explore projects →</button></div><div className="sidebar-bottom"><span className={`connection-dot ${apiState}`} />{apiState === 'connected' ? 'API connected' : 'API offline'}</div></aside><main className="content"><header className="topbar"><div><p className="breadcrumb">SkillBridge <span>/</span> {active}</p><h1>{active === 'Home' ? `Welcome back, ${user?.name || 'learner'}!` : active}</h1></div><div className="top-actions"><label className="search"><span>⌕</span><input placeholder="Search anything..." /></label><button className="user-menu" onClick={onLogout}><span className="user-avatar">{(user?.name || 'U').slice(0, 2).toUpperCase()}</span><span>{user?.name || 'Learner'}</span><span>⌄</span></button></div></header>{error && <div className="form-error page-error">{error}</div>}{active === 'Home' ? <Dashboard data={dashboard} /> : <DataPage title={active} data={data} />}</main></div>;
}

function Dashboard({ data }) { return <div className="dashboard"><section className="welcome-card clay-card"><div><span className="eyebrow">YOUR LEARNING JOURNEY</span><h2>Learn. Build. <em>Grow.</em></h2><p>Turn your skills into opportunities. Keep learning, work on real projects, and build the future you want.</p><button className="primary-button">Explore projects <span>→</span></button></div><div className="welcome-art"><div className="sun" /><div className="hill hill-one" /><div className="hill hill-two" /><div className="person-art">♟</div><div className="plant">♧</div></div></section><div className="stats-grid"><Stat icon="◌" label="Students" value={data?.students ?? '—'} note="Registered learners" color="blue" /><Stat icon="▣" label="Recruiting projects" value={data?.recruitingProjects ?? '—'} note="Open opportunities" color="purple" /><Stat icon="✓" label="Learning modules" value={data?.learningModules ?? '—'} note="Available to learn" color="green" /><Stat icon="✦" label="Skill catalogue" value={data?.skills ?? '—'} note="Mapped capabilities" color="orange" /></div><div className="section-heading"><div><h2>Recent activity</h2><p>Live events from your SkillBridge workspace.</p></div></div><section className="clay-card activity-card"><div className="activity-list">{(data?.activities || []).map((activity) => <div className="activity" key={`${activity.title}-${activity.occurredAt}`}><div className={`activity-icon ${activity.accent}`}>✦</div><div><strong>{activity.title}</strong><p>{activity.detail}</p></div><time>{new Date(activity.occurredAt).toLocaleDateString()}</time></div>)}</div></section></div>; }
function DataPage({ title, data }) { return <section className="data-page"><div className="section-heading"><div><h2>{title} collection</h2><p>Live records managed by the SkillBridge backend.</p></div><span className="record-count">{data.length} records</span></div><div className="data-grid">{data.length === 0 ? <div className="clay-card empty-panel">No records available yet.</div> : data.map((item, index) => <article className="clay-card data-card" key={item.id || index}><span className="data-number">{String(index + 1).padStart(2, '0')}</span><h3>{item.title || item.name || item.email || 'Record'}</h3><p>{item.description || item.category || item.role || item.status || 'Available in the collection.'}</p></article>)}</div></section>; }
function Logo() { return <div className="brand"><div className="brand-mark">S</div><div><strong>SkillBridge</strong><span>Learn · Build · Grow</span></div></div>; }
function Feature({ icon, title, text }) { return <article className="feature clay-card"><div>{icon}</div><h3>{title}</h3><p>{text}</p></article>; }
function Stat({ icon, label, value, note, color }) { return <article className="stat-card clay-card"><div className={`stat-icon ${color}`}>{icon}</div><div className="stat-copy"><span>{label}</span><strong>{value}</strong><small>{note}</small></div></article>; }

createRoot(document.getElementById('root')).render(<App />);
