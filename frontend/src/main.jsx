import React, { useEffect, useState } from 'react';
import { createRoot } from 'react-dom/client';
import './styles.css';

const apiBaseUrl = (import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080').replace(/\/$/, '');

const navigation = [
  ['⌂', 'Home'],
  ['▣', 'Profile'],
  ['◫', 'Projects'],
  ['▤', 'Learning'],
  ['⌕', 'AI Mentor'],
  ['♧', 'Notifications'],
  ['⚙', 'Settings'],
];

function App() {
  const [active, setActive] = useState('Home');
  const [apiState, setApiState] = useState('checking');
  const [dashboard, setDashboard] = useState(null);

  useEffect(() => {
    Promise.all([
      fetch(`${apiBaseUrl}/api/status`),
      fetch(`${apiBaseUrl}/api/dashboard`),
    ])
      .then(async ([statusResponse, dashboardResponse]) => {
        if (!statusResponse.ok || !dashboardResponse.ok) throw new Error('API request failed');
        return dashboardResponse.json();
      })
      .then((data) => {
        setDashboard(data);
        setApiState('connected');
      })
      .catch(() => setApiState('offline'));
  }, []);

  return (
    <div className="app">
      <aside className="sidebar">
        <div className="brand">
          <div className="brand-mark">S</div>
          <div>
            <strong>SkillBridge</strong>
            <span>Learn · Build · Grow</span>
          </div>
        </div>
        <nav>
          <p className="nav-label">Main menu</p>
          {navigation.map(([icon, label]) => (
            <button
              className={`nav-item ${active === label ? 'active' : ''}`}
              key={label}
              onClick={() => setActive(label)}
            >
              <span>{icon}</span>{label}
              {label === 'Notifications' && <b className="nav-badge">3</b>}
            </button>
          ))}
        </nav>
        <div className="sidebar-help">
          <div className="help-icon">?</div>
          <strong>Need help?</strong>
          <p>Find answers and guides in our support center.</p>
          <button>Visit Help Center <span>→</span></button>
        </div>
        <div className="sidebar-bottom">
          <span className={`connection-dot ${apiState}`} />
          {apiState === 'connected' ? 'API connected' : apiState === 'offline' ? 'API offline' : 'Connecting…'}
        </div>
      </aside>

      <main className="content">
        <header className="topbar">
          <div>
            <p className="breadcrumb">SkillBridge <span>/</span> {active}</p>
            <h1>{active === 'Home' ? 'Welcome back, John Doe!' : active}</h1>
          </div>
          <div className="top-actions">
            <label className="search"><span>⌕</span><input placeholder="Search anything..." /></label>
            <button className="round-button">♧</button>
            <button className="user-menu"><span className="user-avatar">JD</span><span>John Doe</span><span>⌄</span></button>
          </div>
        </header>

        {active !== 'Home' ? (
          <section className="empty-panel glass-card">
            <div className="empty-illustration">✦</div>
            <h2>{active}</h2>
            <p>This workspace is ready for live data from the SkillBridge API.</p>
            <button className="primary-button">Get started <span>→</span></button>
          </section>
        ) : (
          <Dashboard data={dashboard} />
        )}
      </main>
    </div>
  );
}

function Dashboard({ data }) {
  const activities = data?.activities ?? [];

  return (
    <div className="dashboard">
      <section className="welcome-card glass-card">
        <div>
          <span className="eyebrow">YOUR LEARNING JOURNEY</span>
          <h2>Learn. Build. <em>Grow.</em></h2>
          <p>Turn your skills into opportunities. Keep learning, work on real projects, and build the future you want.</p>
          <button className="primary-button">Explore projects <span>→</span></button>
        </div>
        <div className="welcome-art">
          <div className="sun" />
          <div className="hill hill-one" />
          <div className="hill hill-two" />
          <div className="person-art">♟</div>
          <div className="plant">♧</div>
        </div>
      </section>

      <div className="stats-grid">
        <Stat icon="◌" label="Students" value={data?.students ?? '—'} note="Registered learners" color="blue" />
        <Stat icon="▣" label="Recruiting projects" value={data?.recruitingProjects ?? '—'} note="Open opportunities" color="purple" />
        <Stat icon="✓" label="Learning modules" value={data?.learningModules ?? '—'} note="Available to learn" color="green" />
        <Stat icon="✦" label="Skill catalogue" value={data?.skills ?? '—'} note="Mapped capabilities" color="orange" />
      </div>

      <div className="section-heading">
        <div><h2>Quick actions</h2><p>Jump right back into your learning journey.</p></div>
        <button className="text-button">View all <span>→</span></button>
      </div>
      <div className="quick-grid">
        <Quick icon="▤" title="Explore Projects" text="Find a project that matches your skills" color="blue" />
        <Quick icon="◎" title="Start Learning" text="Continue where you left off" color="purple" />
        <Quick icon="▣" title="View Jobs" text="Explore opportunities made for you" color="green" />
        <Quick icon="⌁" title="Ask Mentor" text="Get guidance from AI Mentor" color="orange" />
      </div>

      <div className="lower-grid">
        <section className="glass-card activity-card">
          <div className="card-heading"><div><h2>Recent activity</h2><p>Stay on top of your progress.</p></div><button className="more-button">•••</button></div>
          <div className="activity-list">
            {activities.length === 0 && <p className="empty-copy">No activity has been recorded yet.</p>}
            {activities.map((activity) => (
              <div className="activity" key={activity.id ?? `${activity.title}-${activity.occurredAt}`}>
                <div className={`activity-icon ${activity.accent}`}>{activity.type === 'PROJECT' ? '◈' : activity.type === 'MODULE' ? '▣' : activity.type === 'MENTOR' ? '✓' : '✦'}</div>
                <div><strong>{activity.title}</strong><p>{activity.detail}</p></div>
                <time>{new Date(activity.occurredAt).toLocaleDateString()}</time>
              </div>
            ))}
          </div>
        </section>
        <section className="glass-card tip-card">
          <div className="card-heading"><div><h2>Today's tip</h2><p>Small steps, big progress.</p></div><span className="tip-bulb">✦</span></div>
          <div className="tip-graphic">✦</div>
          <h3>Consistency beats intensity.</h3>
          <p>Spend just 20 minutes learning today. Your future self will thank you.</p>
          <button className="outline-button">Continue learning <span>→</span></button>
        </section>
      </div>
    </div>
  );
}

function Stat({ icon, label, value, note, color, progress }) {
  return <article className="stat-card glass-card">
    <div className={`stat-icon ${color}`}>{icon}</div>
    <div className="stat-copy"><span>{label}</span><strong>{value}</strong><small>{note}</small></div>
    {progress && <div className="progress-ring"><b>70%</b></div>}
  </article>;
}

function Quick({ icon, title, text, color }) {
  return <button className="quick-card glass-card"><div className={`quick-icon ${color}`}>{icon}</div><div><strong>{title}</strong><p>{text}</p></div><span className="arrow">→</span></button>;
}

createRoot(document.getElementById('root')).render(<App />);
