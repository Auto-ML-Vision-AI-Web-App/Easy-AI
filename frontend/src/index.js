import React from "react";
import ReactDOM from "react-dom";
import { createBrowserHistory } from "history";
import { Router, Route, Switch } from "react-router-dom";

import "assets/scss/material-kit-react.scss?v=1.10.0";

// pages for this product
import Home from "pages/Home.js";
import LandingPage from "pages/LandingPage/LandingPage.js";
import ProfilePage from "pages/ProfilePage/ProfilePage.js";
import LoginPage from "pages/LoginPage/LoginPage.js";

// pages h01010
import Admin from "pages/AdminPage/Admin.js";
var hist = createBrowserHistory();

ReactDOM.render(
  <Router history={hist}>
    <Switch>
      <Route path="/landing-page" component={LandingPage} />
      <Route path="/profile-page" component={ProfilePage} />
      <Route path="/login-page" component={LoginPage} />
      <Route path="/admin" component={Admin} />
      <Route path="/" component={Home} />
    </Switch>
  </Router>,
  document.getElementById("root")
);