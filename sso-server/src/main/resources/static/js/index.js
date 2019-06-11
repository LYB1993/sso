var vm = new Vue({
    el: '#app',
    data: {
        message: 'default message!',
        clientList: []
    },
    methods: {},
    created: function () {
        var _this = this;
        _this.$http.get("clients").then(function (result) {
            if (result.body.code === 200) {
                console.log(result.body.data);
                _this.clientList = result.body.data;
            }
        }, function (reason) {
            console.log("error", reason);
        });

    }
});