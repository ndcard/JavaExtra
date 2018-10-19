new Vue({
    el:'#app',
    data:{
        userList:[],
        user:{},
    },
    methods:{
        findAll:function () {
            var url='/user/findAll.do';
            //axios不属于vue框架,不能通用this,必须新建一个变量
            var _this=this;
            axios.get(url).then(function (response) {
                console.log(response);
                _this.userList=response.data;
            }).catch(function (err) {
                console.log(err);
            });
        },
        findById:function (id) {
            var url='/user/findById.do';
            //axios不属于vue框架,不能通用this,必须新建一个变量
            var _this=this;
            axios.get(url,{params:{id:id}}).then(function (response) {
                console.log(response);
                _this.user=response.data;
            }).catch(function (err) {
                console.log(err);
            });
            alert(id);
            $("#myModal").modal("show");
        },
        update:function () {
            var url='/user/update.do';
            //axios不属于vue框架,不能通用this,必须新建一个变量
            var _this=this;
            axios.post(url,_this.user).then(function (response) {
                //console.log(response);
                _this.findAll();
            }).catch(function (err) {
                console.log(err);
            });
        },
    },
    //创建vue实例之前
    created:function () {
        this.findAll();
    }
})