//Vue.http.options.emulateHTTP = true;
var vm = new Vue({
        el: '#vm1',
        data: {
            // index
            index:true,
            // login
            login : false,
            loginUser:{'loginName':'','loginPassword':''},
            // register
            register:false,
            registerUser:{'name':'','password':'','password2':'','mobile':''},
            //validateName1:this.validateName,
            registerRule:{
                na: [{ validator: this.validateName1, trigger: 'blur' }],
                password: [{ validator: this.validatePassword, trigger: 'blur' }],
                password2: [{ validator: this.validatePassword2, trigger: 'blur' }],
                mobile: [{ validator: this.checkMobile, trigger: 'blur' }]
            }

        },
        methods:{
            registerOk:function(){
                this.$http.post("/photo/user/register",this.registerUser)
                    .then(function (response) {
                        this.$message({
                            type: 'success',
                            message: '注册成功，赶快去登录吧！'
                        });
                    })
                    .catch(function (response) { console.error(response); });
            },
            registerRest:function(){
                this.registerUser.name = '';
                this.registerUser.password='';
                this.registerUser.mobile=''
            },
            loginOk:function(){
                this.$http.get("/photo/user/login",{params: {name:this.loginUser.loginName,password:this.loginUser.loginPassword}})
                    .then(function (response) {
                        console.log(response);
                        var user = response.body;
                        if(user ==null){
                            this.$message({
                                type: 'success',
                                message: '用户名密码有误，登录失败！'
                            });
                        }else{
                            window.location.href="/photo/index/main?id="+user.id;
                        }
                    })
                    .catch(function (response) { console.error(response); });
            },
            loginRest:function(){
                this.loginName = '';
                this.loginPassword = '';
            },
            // 注册校验方法
            validateName:function(rule, value, callback){
                if (value === '') {
                    callback(new Error('请输入用户名'));
                } else {
                    callback();
                }
            },
            validatePassword:function(rule, value, callback){
                if (value === '') {
                    callback(new Error('请输入密码'));
                } else {
                    if (this.registerUser.password2 !== '') {
                        this.$refs.registerUser.validateField('password2');
                    }
                    callback();
                }
            },
            validatePassword2:function(rule, value, callback){
                if (value === '') {
                    callback(new Error('请再次输入密码'));
                } else {
                    if (this.registerUser.password !== value) {
                        callback(new Error('两次输入密码不一致!'));
                    }else{
                        callback();
                    }
                }
            },
            checkMobile:function(rule, value, callback){
                if (value === '') {
                    callback(new Error('请输入手机号'));
                } else {
                    if(value.length !=11){
                        callback(new Error('手机号不合法'));
                    }else {
                            callback();
                    }
                }
            },

        },
        mounted: function () {
            var registerRule ={
                name: [{ required: true, message: '请输入名称', trigger: 'blur' },{ validator: this.validateName, trigger: 'blur' }],
                password: [{ validator: this.validatePassword, trigger: 'blur' }],
                password2: [{ validator: this.validatePassword2, trigger: 'blur' }],
                mobile: [{ validator: this.checkMobile, trigger: 'blur' }]
            }
            this.registerRule = registerRule;
        },
        watch:{

        },
        updated:function(){
            //this.registerRule.name[0].validator=this.validateName
        },
    })

