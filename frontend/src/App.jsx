import { BrowserRouter, Route, Switch } from 'react-router-dom';

import Home from './pages/Home';
import Operators from './pages/Operators';
import Residents from './pages/Residents';
import Deliveries from './pages/Deliveries';

import './App.css';

export default function App() {
  return (
    <BrowserRouter>
      <Switch>
        <Route path="/" exact component={ Home } />
        <Route path="/operators" component={ Operators } />
        <Route path="/residents" component={ Residents } />
        <Route path="/deliveries" component={ Deliveries } />
      </Switch>
    </BrowserRouter>
  );
}
