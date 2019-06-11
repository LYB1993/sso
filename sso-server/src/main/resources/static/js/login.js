var vm = new Vue({
    el: '#app',
    data: {
        message: '',
        redirectUrl: ''
    },
    methods: {},
    created: function () {
        var _this = this;
        _this.redirectUrl = getParameter("redirect_url");
    }

});

function getParameter(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r) {
        return unescape(r[2]);
    }
}