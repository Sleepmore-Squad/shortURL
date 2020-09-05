import React from 'react';
import { Router, Route, Switch, Redirect} from 'react-router-dom';
import{history} from "./history";
import Login from './view/Login';
import Register from './view/Register';
import Home from './view/Home';
import Setting from './view/Setting';

class BasicRoute extends React.Component{

    constructor(props) {
        super(props);

        history.listen((location, action) => {
            // clear alert on location change
            console.log(location,action);
        });
    }

    render=() =>{
        return (
            <Router history={history}>
                <Switch>
                    <Route exact path="/" component={Login} />
                    <Route exact path="/login" component={Login} />
                    <Route exact path="/register" component={Register} />
                    <Route exact path="/home" component={Home} />
                    <Route exact path="/setting" component={Setting} />
                    <Redirect from="/*" to="/" />
                </Switch>
            </Router>
        )
    }
}

export default BasicRoute;