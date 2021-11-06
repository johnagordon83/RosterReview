import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import PlayerDataService from '../service/PlayerDataService';

/**
 * A React Component that searches for players by id and displays their
 * demographic and statistical data.
 */
class PlayerProfile extends Component {

    constructor(props) {
        super(props);

        this.state = {
            playerData: null,
            loading: true
        };
    }

    /**
     * Upon successful loading of this component, retrieve and update
     * player data.
     */
    componentDidMount() {
        this.searchAndUpdate();
    }

    /**
     * On Component state change, if the player id in url has changed,
     * perform player search and update.
     *
     * @param {*} prevProps
     */
    componentDidUpdate(prevProps) {
        if (this.props.match.params.id !== prevProps.match.params.id) {
            this.searchAndUpdate();
        }
    }

    /**
     * Submits a data request for a player who's id matches the 'id'
     * url parameter.
     */
    searchAndUpdate = () => {
        this.setState({loading: true});
        PlayerDataService.getPlayerById(this.props.match.params.id).then(response => {
            this.setState({
                playerData: response.data,
                loading: false
            });
        });
    }

    render() {
        return (
            <div>
              <div className="loader-container" style={{ display: (this.state.loading ? 'flex' : 'none') }}>
                <div className="loader"></div>
              </div>
              <p>This is the player profile for { this.state.playerData ? this.state.playerData.nickname : ""}&nbsp;
                  { this.state.playerData ? this.state.playerData.lastName : ""}.
              </p>
            </div>
        );
    }
}

export default withRouter(PlayerProfile);