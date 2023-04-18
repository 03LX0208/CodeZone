import $ from 'jquery';

export default {
    state: {
        id: "",
        username: "",
        photo: "",
        token: "",
        is_login: false,
    },
    getters: {

    },
    mutations: {
        updateUser(state, user) {
            state.id = user.id;
            state.username = user.username;
            state.photo = user.photo;
            state.is_login = user.is_login;
        },
        updateToken(state, token) {
            state.token = token;
        },
        logout(state) {
            state.id = "";
            state.username = "";
            state.photo = "";
            state.token = "";
            state.is_login = false;
            localStorage.setItem("jwt_token", state.token);
        },
    },
    actions: {
        login(context, data) {
            $.ajax({
                url: "https://gomoku.lxcode.xyz/api/user/token/",
                type: "post",
                data: {
                    username: data.username,
                    password: data.password,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        context.commit("updateToken", resp.token);
                        localStorage.setItem("jwt_token", resp.token);
                        data.success();
                    } else data.error();
                },
                error(resp) {
                    data.error(resp);
                }
            });
        },
        getInfo(context, data) {
            $.ajax({
                url: "https://gomoku.lxcode.xyz/api/user/info/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + context.state.token,
                },
                success(resp) {
                    context.commit("updateUser", {
                        ...resp,
                        is_login: true,
                    });
                    data.success();
                },
                error() {
                    data.error();
                }
            });
        },
        logout(context) {
            context.commit("logout");
        }
    },
    modules: {

    }
}