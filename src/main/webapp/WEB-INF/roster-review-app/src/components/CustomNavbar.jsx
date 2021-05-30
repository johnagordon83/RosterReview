import React, { Component } from 'react';
import { Navbar, NavbarToggler, Nav, NavLink, NavItem,
         InputGroup, InputGroupAddon, Input, Collapse } from 'reactstrap';
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
     * Defines the JSX markup that constitutes this custom navigation bar component.
     * 
     * @returns the navigation bar JSX markup
     */
    render() {
        return (
          <Navbar className="container-xxl navbar-dark navbar-expand-lg bg-dark" aria-label="Main navigation">
            <a id="navbar-logo" href="/rosterreview/">
              <img src={navbarLogo} alt="overdrafted logo" />
            </a>
            <Collapse isOpen={this.state.isOpen} navbar>
              <Nav className="navbar-nav flex-row py-lg-0">
                <NavItem className="col-6 col-lg-auto">
                  <NavLink className="p-2" href="/rosterreview/value-chart/">Value Chart</NavLink>
                </NavItem>
                <NavItem className="col-6 col-lg-auto">
                  <NavLink className="p-2" href="/rosterreview/gm-peformance/">Draft Grades</NavLink>
                </NavItem>
                <NavItem className="col-6 col-lg-auto">
                  <NavLink className="p-2" href="/rosterreview/redrafts/">Redrafts</NavLink>
                </NavItem>
                <NavItem className="col-6 col-lg-auto">
                  <NavLink className="p-2" href="/rosterreview/redrafts/">Historical Trends</NavLink>
                </NavItem>
              </Nav>
            </Collapse>
            <div id="right-navbar-items">
              <NavbarToggler onClick={this.toggle} className="collapsed" 
                             aria-expanded={this.state.isOpen} aria-label="Toggle navigation" />
              <InputGroup>
                <Input placeholder="player search" className="border-end-0 border left-pill"/>
                <InputGroupAddon addonType="append" className="btn-sm bg-white border-bottom-0 border right-pill">
                  <FontAwesomeIcon icon={faSearch} />
                </InputGroupAddon>
              </InputGroup>
            </div>
          </Navbar>
        );
    }
}

export default CustomNavbar;