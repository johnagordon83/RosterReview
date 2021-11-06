import React, { Component } from 'react';
import { withRouter, Link, NavLink } from 'react-router-dom';
import { Navbar, NavbarToggler, Nav, NavItem, Collapse,
         Form, Input, InputGroup, Button } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSearch } from '@fortawesome/free-solid-svg-icons';
import navbarLogo from '../media/navbar-logo.png';
import '../css/CustomNavbar.css';

/**
 * A customized webpage navigation bar similar to Bootstrap's Navbar.
 */
class CustomNavbar extends Component {

    constructor(props) {
        super(props);

        this.state = {
            isOpen: false
        };
    }

    /**
     * Toggle (expand or collapse) the navbar links menu.
     *
     * This function is called when a user clicks on the navbar toggler icon.
     * The toggler icon is only available when the browser window is not wide
     * enough to show all navigation links.
     */
    toggle = () => {
        this.setState({
            isOpen: !this.state.isOpen
        });
    }

    /**
    * Navigate to the player search component and pass the submitted text to
    * the component.
    *
    * This function is called by the player search input field located within
    * the navigation bar on submit.
    */
    submitPlayerSearch = (e) => {
        e.preventDefault();
        const searchName = e.target.elements.search.value;
        if (searchName.length > 0) {
            this.props.history.push({
                pathname: `/rosterreview/player-search`,
                search: `?name=${searchName}`
            });
        }
    }

    /**
     * Defines the JSX markup that constitutes this custom navigation bar component.
     *
     * @returns the navigation bar JSX markup
     */
    render() {
        return (
          <Navbar className="container-xxl navbar-dark navbar-expand-lg bg-dark" aria-label="Main navigation">
            <Link id="navbar-logo" to="/rosterreview/">
              <img src={navbarLogo} alt="overdrafted logo" />
            </Link>
            <Collapse isOpen={this.state.isOpen} navbar>
              <Nav className="navbar-nav flex-row py-lg-0">
                <NavItem className="col-6 col-lg-auto">
                  <NavLink className="nav-link p-2" to="/rosterreview/value-chart/">Value Chart</NavLink>
                </NavItem>
                <NavItem className="col-6 col-lg-auto">
                  <NavLink className="nav-link p-2" to="/rosterreview/draft-grades/">Draft Grades</NavLink>
                </NavItem>
                <NavItem className="col-6 col-lg-auto">
                  <NavLink className="nav-link p-2" to="/rosterreview/redrafts/">Redrafts</NavLink>
                </NavItem>
                <NavItem className="col-6 col-lg-auto">
                  <NavLink className="nav-link p-2" to="/rosterreview/history/">Historical Trends</NavLink>
                </NavItem>
              </Nav>
            </Collapse>
            <div id="right-navbar-items">
              <NavbarToggler onClick={this.toggle} className="collapsed"
                             aria-expanded={this.state.isOpen} aria-label="Toggle navigation" />
              <Form onSubmit={this.submitPlayerSearch}>
                <InputGroup>
                  <Input name="search" placeholder="player search" className="border-0 left-pill"/>
                  <Button className="btn-sm bg-white text-muted border-0 right-pill">
                    <FontAwesomeIcon icon={faSearch} />
                  </Button>
                </InputGroup>
              </Form>
            </div>
          </Navbar>
        );
    }
}

export default withRouter(CustomNavbar);