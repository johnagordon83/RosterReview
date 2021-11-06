import React, { Component } from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import CustomNavbar from './CustomNavbar';
import Home from './Home';
import ValueChart from './ValueChart';
import PlayerProfile from './PlayerProfile';
import PlayerSearch from './PlayerSearch';

/**
 * The application's root Component
 */
export default class App extends Component {

    render() {
        return (
          <div className="App">
            <Router>
              <CustomNavbar />
              <Switch>
                <Route path="/rosterreview/" exact component={Home} />
                <Route path="/rosterreview/value-chart/" exact component={ValueChart} />
                <Route path="/rosterreview/player/:id" component={PlayerProfile} />
                <Route path="/rosterreview/player-search" component={PlayerSearch} />
              </Switch>
            </Router>
          </div>
        );
    }
}