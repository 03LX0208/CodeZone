// import $ from 'jquery';

export default {
    state: {
        status: "matching",
        socket: null,
        opponent_username: "",
        opponent_photo: "",
        game_map: null,
        a_id: 0, // a是黑旗 b是白旗
        b_id: 0,
        my_turn: null, // true表示当前是我的回合
        is_bot: false, // 是不是bot执行
        gameObject: null,
    },
    getters: {

    },
    mutations: {
        updateSocket(state, socket) {
            state.socket = socket;
        },
        updateOpponent(state, opponent) {
            state.opponent_username = opponent.username;
            state.opponent_photo = opponent.photo;
        },
        updateStatus(state, status) {
            state.status = status;
        },
        updateGame(state, game) {
            state.game_map = game.map;
            state.a_id = game.a_id;
            state.b_id = game.b_id;
        },
        updateGameObject(state, gameObject) {
            state.gameObject = gameObject;
        },
        updateMyTurn(state, turn) {
            state.my_turn = turn;
        },
        updateIsBot(state, is_bot) {
            state.is_bot = is_bot;
        }
    },
    actions: {
    },
    modules: {

    }
}