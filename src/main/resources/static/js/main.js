Vue.http.options.emulateHTTP = true;
var vm = new Vue({
    el: '#vm2',
    data: {
        // 图片展示
        pictures:[],
        user:{'id':'','name':'','password':'','mobile':'','sex':'','registerDate':'','headUrl':''},
        userBack:{},
        showModel:'',
        pictureDesc:'',
        // 图片上传参数
        dialogImageUrl: '',
        dialogVisible: false,
        // 我的资源
        myImages:[],
        base:'../file/',
        dialogFormVisible:false,
        dialogForm:{'id':'','imageDesc':'','imageName':''},
        formLabelWidth:'120px'
    },
    methods:{
        logout:function(){
            var _this = this;
            this.$confirm('确定要退出当前系统吗?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
                callback: function(id){
                    if(id == 'confirm'){
                        window.location.href="/photo/user/logout";
                    }else{
                        _this.$message({
                            type: 'info',
                            message: '已取消'
                        });
                    }
                }
            });
        },

        carouselChange:function(index){
            this.pictureDesc = this.pictures[index].imageDesc;
        },
        deleteImage:function(){
            this.$http.get("/photo/file/deleteImageById",{params: {id:this.dialogForm.id}})
                .then(function (response) {
                    this.$message({
                        type: 'success',
                        message: '删除成功！'
                    });
                    this.dialogFormVisible = false;
                    this.getMyImage();
                })
                .catch(function (response) { console.error(response); });
        },
        dialogFormOk:function(){
            this.$http.get("/photo/file/editDesc",
                {params: {id:this.dialogForm.id,desc:this.dialogForm.imageDesc,name:this.dialogForm.imageName}})
                .then(function (response) {
                    this.$message({
                        type: 'success',
                        message: '修改成功！'
                    });
                    this.dialogFormVisible = false;
                    //this.getMyImage();
                })
                .catch(function (response) { console.error(response); });
        },
        imageClick:function(id){
            this.dialogFormVisible = true;
            for(index in this.myImages){
                if(this.myImages[index].id == id){
                    this.dialogForm = this.myImages[index];
                    break;
                }
            }
        },
        showModelSelect:function (model) {
            this.getRandomImages(model);
        },
        getRandomImages:function(model){
            this.$http.get("/photo/file/getRandomImages",{params: {userId:this.user.id,model:model}})
                .then(function (response) {
                    console.log(response);
                    this.pictures = response.body;
                })
                .catch(function (response) { console.error(response); });
        },
        tabClick:function(tab){
            if(tab.index==3){
                this.getMyImage();
            }
        },
        getMyImage:function(){
            this.$http.get("/photo/file/getImageByUserId",{params: {userId:this.user.id}})
                .then(function (response) {
                    console.log(response);
                    this.myImages = response.body;
                })
                .catch(function (response) { console.error(response); });
        },
        handleRemove:function(file, fileList) {
            console.log(file, fileList);
        },
        handlePictureCardPreview:function(file) {
            this.dialogImageUrl = file.url;
            this.dialogVisible = true;
        },
        saveUser:function(){
            this.$http.post("/photo/user/saveEdit",this.user)
                .then(function (response) {
                    this.$message({
                        type: 'success',
                        message: '保存成功！'
                    });
                })
                .catch(function (response) { console.error(response); });
        },
        editUserRest:function(){
            for(var key in this.userBack){
                this.user[key] = this.userBack[key];
            }
        }
    },
    mounted: function () {
        // 加载首页随机图片
        this.getRandomImages('SJZS');
    },
    watch:{

    }
})

