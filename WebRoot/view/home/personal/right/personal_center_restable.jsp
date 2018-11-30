<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE>
<html >
<head>
    <meta charset="UTF-8">
    <title>个人中心页面-右侧下载页面</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <%@ include file="/common/include-home-cut-version.jsp" %>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/personal-center.css${_v}"/>
     <style>
        *{
            margin: 0;
            padding: 0;
        }
        body{
            width: 100vw;
            
        }
        .header{
            width: 100vw;
            padding-top:8vw; 
            padding-bottom: 1vw;
            background-color:#1575c1;
            color: #ffffff;
            text-align: center;
        }
        .header h3{
           font-size: 5vw;
            
        }
        .section{
            padding: 0 1vw;
        }
        .section h5{
           margin: 4vw 0 6vw 0;
           text-align: center;
           color: #6f6b64;
           font-size: 2vw;
           
        }
        .section label{
            line-height: 7vw; 
            font-size: 4vw;
        }
        .section label span{
            color: #ff7f81;
        }
        .section input{
            width: 96vw;
            line-height: 10vw;
            padding-left:2vw;
            outline: none;
            border: 0.1vw solid #ccc;
            font-size: 4vw;
        }
        .section input::-webkit-input-placeholder {
            font-size:4vw;
        }

        .section-info{
            position:relative;
            padding-bottom:24px;
        }
        .section-info span.error{
            display: block;
		    width: 96vw;
		    height: 24px;
		    position: absolute;
		    bottom: 0;
		    left: 0;
		    padding-left: 2vw;
		    color: red;
		    line-height: 24px;   
        }
        .section-info span.error label{
            font-size: 2vw;
        }
        .section button{
            width: 98vw;
            background-color: #52c018;
            line-height: 10vw;
            font-size: 5vw;
            outline: none;
            border: none;
            color: #ffffff;
            border-radius: 5px;
            margin-top: 6vw;
        }
        .footer{
            width: 96vw;
            border: 0.1vw solid #cccccc;
            text-align: center;
            margin-top: 5vw;
            padding-top:2vw;
            padding-bottom: 4vw;
            margin-left: 1.5vw;
        }
        .footer img{
            width: 18vw;
            height: auto;
        }
        .footer p{
            padding: 0 20vw;
            color: #cccccc;
            margin-bottom: 5vw;
            font-size: 2vw;
        }
        .footer a{
            line-height: 5vw;
            text-align: center;
            color: #cccccc;
            font-size:2vw;
            padding:1vw 4vw; 
            border: 0.1vw solid #cccccc; 
            text-decoration: none;
            
        }

    </style>
    <script src="https://cdn.bootcss.com/jquery/1.7/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/jquery-validate/1.17.0/jquery.validate.js"></script>
</head>
<body>
	<header class="header">
            <h3>"免费在线英语口语学习" 申请</h3>
        </header>
        <section class="section">
            <h5>欢迎您添加、咨询、领取资料、申请免费课程~</h4>
                <!-- 1.提交表单的访问地址 -->
            <form method="">
                <!--2.隐藏数据：隐藏返回的参数 -->
                <input type="hidden" name="orgId" value="${orgId}"/>
                <input type="hidden" name="userAccount" value="${userAccount }"/>
                <div class="section-info">
                    <label for="">姓名<span>*</span></label>
                    <input type="text" name="name" placeholder="请输入您的姓名">
                    <span class="error"></span>
                </div>
                <div class="section-info">
                    <label for="">手机<span>*</span></label>
                    <input type="text"  name="mobilephone" placeholder="用来接收资料和上课的手机号">
                    <span class="error"></span>
                </div>
                <div class="section-info">
                    <label for="">微信号<span>*</span></label>
                    <input type="text"  name="qq" placeholder="请输入微信或QQ号">
                    <span class="error"></span>
                </div>
                <button type="submit">点击提交</button>
            </form>
        </section>
      
        <footer class="footer">
            <img src="${ctx}/static/images/logo2.png">
          
            <p>易口说英语官方服务号，学校所有的大事件、促销活动、开班信息、课程介绍、
                名师专题、易家人故事等所有官方信息在这里都有哦~
            </p>
            <a href="https://mp.weixin.qq.com/mp/profile_ext?action=home&__biz=MzIwODM3Mzc3NQ==#wechat_redirect">进入公众号</a>
        </footer>
        <script type="text/javascript">
        $(function(){
            jQuery.validator.addMethod("mobile", function(value, element, param) {
                    var bool = /^(0?1[123456789]\d{9})$/.test(value);
                    return bool;
            },jQuery.validator.format("手机号码格式不正确"));
            
            $("form").submit(function(e){
                e.preventDefault();
                var $this = $(this);
                var submitBtn = $this.find("[type='submit']");
                
                submitBtn.attr("disabled",true)
                
                var data = $this.serialize();

                if($this.valid()){
                    $.ajax({
                        url:ctx+"/popup/saveRes",
                        data:data,
                        method:"post",
                        complete:function(){
                            submitBtn.removeAttr("disabled");
                        },
                        success:function(result){
                            if(result.code == -1){
                                alert(result.msg);
                            }else if(result.code == 0){
                                alert("您好，您的信息已经提交成功，我们课程顾问将在72小时内联系您，请保持电话开机状态！");
                                window.location.href = "http://tg.ikoushuo.com/jingtai/care.html";
                            }
                        }
                    })
                }
            })
            $("form").validate({
                rules:{
                    name:"required",
                    mobilephone:{
                        required:true,
                        mobile:true
                    },
                    qq:"required"
                },
                messages:{
                    name:"姓名不可为空",
                    mobilephone:{
                        required:"手机号码不可为空"
                    },
                    qq:"微信号不可为空"
                },
                errorPlacement:function(error,element){
	               error.appendTo(element.parent().find("span.error"))
	           }
            })
        })
        </script>
</body>
</html>
