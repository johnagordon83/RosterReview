import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import queryString from 'query-string';
import playerDataService from '../service/PlayerDataService';
import '../css/PlayerSearch.css';

/**
 * A React Component that performs searches for players by name and displays results.
 */
class PlayerSearch extends Component {

    constructor(props) {
        super(props);

        this.state = {
            players: [],
            searching: true
        };
    }

    /**
     * Upon successful loading of this component, perform player search.
     */
    componentDidMount() {
        this.searchAndUpdate();
    }

    /**
     * If URL query string changes, perform new player search.
     */
    componentDidUpdate(prevProps) {
        if (this.props.location.search !== prevProps.location.search) {
            this.searchAndUpdate();
        }
    }

    /**
     * Submits a request for data on players with names that are similar
     * to the query string param 'name'.
     *
     * If only one result is returned, automatically navigate to that player's
     * profile page.  If data for zero or multiple players is returned, update
     * the search component state with the result.
     */
    searchAndUpdate = () => {
        const urlParams = queryString.parse(this.props.location.search);
        const searchName = urlParams.name;
        if (searchName && searchName.length > 0) {
            this.setState({ searching: true });
            playerDataService.findPlayersWithNameLike(searchName, 25).then(response => {
                const playerData = response.data;
                if (playerData.length === 1) {
                    let player = playerData[0];

                    this.props.history.push({
                        pathname: `/rosterreview/player/${player.id}`
                    });
                } else {
                    this.setState({
                        players: playerData,
                        searching: false
                    });
                }
            });
        }
    }

    /**
     * Maps data for each player returned by the most recent search into a table row.
     *
     * @returns  an array of table rows populated with player data
     */
    generateTableRows = () => {
        const rows = this.state.players.map(player => {
            return (
                <tr>
                  <td>{player.firstName}</td>
                  <td>{player.lastName}</td>
                  <td>{player.age}</td>
                </tr>
            );
        });

        return rows;
    }

    render() {
        return (
            <div>
              <div className="loader-container" style={{ display: (this.state.searching ? 'flex' : 'none') }}>
                <div className="loader"></div>
              </div>
              <div>
                <table style={{ display: (this.state.players.length > 0 ? 'block' : 'none') }}>
                  <tbody>
                    <th>
                      <td>First Name</td>
                      <td>Last Name</td>
                      <td>Age</td>
                    </th>
                    { this.generateTableRows() }
                  </tbody>
                </table>
              </div>
            </div>
        );
    }
}

export default withRouter(PlayerSearch);