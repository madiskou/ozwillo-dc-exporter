import React from 'react'
import { render } from 'react-dom'

import { Router, Route, IndexRoute, hashHistory } from 'react-router'

import Navbar from './Navbar'
import Dashboard from './Dashboard'
import Dataset from './Dataset'

const App = React.createClass({
    getInitialState() {
        return {
            csrfToken: '',
            csrfTokenHeaderName: ''
        }
    },
    childContextTypes: {
        csrfToken: React.PropTypes.string,
        csrfTokenHeaderName: React.PropTypes.string
    },
    getChildContext() {
        return {
            csrfToken: this.state.csrfToken,
            csrfTokenHeaderName: this.state.csrfTokenHeaderName
        };
    },
    componentDidMount() {
        fetch('/api/csrf-token', { credentials: 'same-origin' })
            .then(response => response.headers)
            .then(headers =>
                this.setState({ csrfToken : headers.get('X-CSRF-TOKEN'), csrfTokenHeaderName: headers.get('X-CSRF-HEADER') }))
    },
    render() {
        return (
            <div>
                <Navbar />
                {this.props.children}
            </div>
        )
    }
})

render((
    <Router history={hashHistory}>
        <Route path="/" component={App}>
            <IndexRoute component={Dashboard} />
            <Route path="dataset" component={Dataset} />
            <Route path="dataset/:id" component={Dataset}/>
        </Route>
    </Router>
), document.getElementById('app'))
