import axios from 'axios';

/**
 * A service class providing methods for player data operations.
 */
export default class PlayerDataService {

    /**
     * Search the data repository for all players found to have names similar to
     * the passed argument.
     *
     * @param {String} playerName  user provided name against which player names
     *                             will be compared
     * @param {Number} limit       a positive integer indicating the maximum number
     *                             of players to return in the result
     * @returns                    A JSON representation of all matched players
     */
    static findPlayersWithNameLike(playerName, limit) {
        let limitParam = (limit > 0) ? `&limit=${limit}` : ``;
        let queryString = `?name=${playerName}${limitParam}`;
        let webservice = `/rosterreview/api/player/withNameLike`;
        let url = `${window.location.origin}${webservice}${queryString}`;

        return axios.get(url);
    }

    /**
     * Search the data repository for a player with a unique id.
     *
     * @param {String} id  The unique id of the player to find
     * @returns            A JSON representation of the player whose id matches
     *                     the passed argument, or null if none exists
     */
    static getPlayerById(id) {
        let queryString = `?id=${id}`;
        let webservice = `/rosterreview/api/player/data`;
        let url = `${window.location.origin}${webservice}${queryString}`;

        return axios.get(url);
    }
}