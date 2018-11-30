<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <title>邮件发送记录</title>
    <style type="text/css"></style>
    <%@ include file="/common/include.jsp" %>
    <script src="${ctx}/static/js/view/email/sendlist.js${_v}" type="text/javascript"></script>
    <script type="text/javascript" src="${ctx}/static/js/file-up/html5.js${_v}"></script>
    <script src="${ctx}/static/js/ckeditor/ckeditor.js${_v}"></script>
    <script src="${ctx}/static/js/ckeditor/config.js${_v}"></script>
    <script type="text/javascript" src="${ctx}/static/js/idialog/jquery.idialog.js${_v}" dialog-theme="default"></script><!--可移动弹框插件-->
    <script type="text/javascript" src="${ctx}/static/js/idialog/idialog.common.js${_v}"></script><!--可移动弹框插件公共js-->
    <style type="text/css">
        .cke_chrome{margin-left:100px !important;}
    </style>
    <script language="javascript">
        $(function(){
            //加载ckeditor
            CKEDITOR.config.height = '320';
            CKEDITOR.config.width = '765';
            CKEDITOR.replace('editor');
            /*左边铺满整屏*/
            var winhight=$(window).height();
            $(".hyx-cm-right").height(winhight-30);
            $(".hyx-cfu-timeline").height(winhight-60);
            /*表单优化*/
            /*邮件群发tab*/
            $('.hyx-cfu-tab').find('li').click(function(){
                $(this).addClass('select').siblings('li').removeClass('select');
                var index = $(this).index();
                $(this).parent().parent().parent().find('.hyx-cfu-timeline').hide();
                $(this).parent().parent().parent().find('.hyx-cfu-timeline').eq(index).show();
            });


            $(".bomp-pos").find('.bomp-focus,.lab_hid').click(function(){
                var ipt_a = $(this).parent().find('.bomp-focus');
                ipt_a.focus();
                $(this).parent().find('.lab_hid').hide();
                ipt_a.blur(function(){
                    if($(this).val() == ''){
                        $(this).parent().find('.lab_hid').show();
                    }
                });
            });
            $(".bomp-pos-a").find('.lab_hid').click(function(){
                var ipt_b = $(this).parent().find('.bomp-focus');
                ipt_b.focus();
                $(this).parent().find('.lab_hid').hide();
                ipt_b.blur(function(){
                    if($(this).val() == ''){
                        $(this).parent().find('.lab_hid').show();
                    }
                });
            });
            $(".bomp-pos-a").find('.bomp-focus').click(function(){
                var ipt_c = $(this);
                ipt_c.focus();
                $(this).parent().parent().find('.lab_hid').hide();
                ipt_c.blur(function(){
                    if($(this).val() == ''){
                        $(this).parent().parent().find('.lab_hid').show();
                    }
                });
            });
            $(".lab_icon_a").on("click", function(e){
                $(".lab_icon_b").parent().find('.drop').fadeOut();
                var lab_icon_a = $(this).parent();
                if(lab_icon_a.find('.drop').is(":hidden")){
                    lab_icon_a.find('.drop').fadeIn();
                }else{
                    lab_icon_a.find('.drop').fadeOut();
                }

                $(document).one("click", function(){
                    lab_icon_a.find('.drop').fadeOut();
                });

                e.stopPropagation();
            });
            $(".lab_icon_a").parent().find('.drop').on("click", function(e){
                e.stopPropagation();
            });

            $(".lab_icon_b").on("click", function(e){
                $(".lab_icon_a").parent().find('.drop_a').fadeOut();
                var lab_icon_a = $(this).parent();
                if(lab_icon_a.find('.drop_a').is(":hidden")){
                    lab_icon_a.find('.drop_a').fadeIn();
                }else{
                    lab_icon_a.find('.drop_a').fadeOut();
                }

                $(document).one("click", function(){
                    lab_icon_a.find('.drop_a').fadeOut();
                });

                e.stopPropagation();
            });
            $(".lab_icon_b").parent().find('.drop_a').on("click", function(e){
                e.stopPropagation();
            });

            $(".sign-of-use").find('.select').each(function(){
                var s=$(this);
                var z=parseInt(s.css("z-index"));
                var dt=$(this).children("dt");
                var dd=$(this).children("dd");
                var _show=function(){dd.slideDown(200);dt.addClass("cur");s.css("z-index",z+1);};   //展开效果
                var _hide=function(){dd.slideUp(200);dt.removeClass("cur");s.css("z-index",z);};    //关闭效果
                dt.click(function(){dd.is(":hidden")?_show():_hide();});
                if(dd.find('a').hasClass('diy') == true){
                    dd.find(".diy").parent().siblings().click(function(){dt.html($(this).html());_hide();});     //选择效果（如需要传值，可自定义参数，在此处返回对应的“value”值 ）

                }else{
                    dd.find("a").click(function(){dt.html($(this).html());_hide();});     //选择效果（如需要传值，可自定义参数，在此处返回对应的“value”值 ）
                }
            });
            $(".lab_icon").find('.drop').find('.ipt,.icon').click(function(){
                var ipt = $(this).parent().find('.ipt');
                ipt.focus();
                $(this).parent().find('.icon').hide();
                ipt.blur(function(){
                    if($(this).val() == ''){
                        $(this).parent().find('.icon').show();
                    }
                });
            });

            $('.bomp_mess_right').find('.dd_a').mouseover(function(){
                $(this).find('.drop').fadeIn(1);
            });
            $('.bomp_mess_right').find('.dd_a').mouseleave(function(){
                $(this).find('.drop').fadeOut(1);
            });

            $('.add-sign-qm').click(function(){
                showIframe('alert_new_char_sign','alert_new_char_sign.html','新建签名',630,420);
            });
            $('.perso-sign-edit').click(function(){
                showIframe('alert_new_char_sign','alert_new_char_sign.html','编辑签名',630,420);
            });
            $('.subje-manag-butt').click(function(){
                showIframe('alert_sub_mana_all','alert_sub_mana_all.html','模板管理',1200,580);
            });
            $(".mail-bulk-subje").each(function(){
                $(this).click(function(){
                    var area_val = $(".editor-textarea").html();
                    var span_html = $(this).find("span").html();
                    $(".mail-bulk-subject").attr('value',span_html);

                    $(".editor-textarea").html(area_val+$(this).html());
                    /*alert($(".editor-textarea").html());*/
                });
            });
        });
    </script>


</head>
<body>
<div class='bulk-sms-box alert_d'>
    <div class="bomp_mess_left fl_l" style="width:920px">
        <div class='bomp-p bomp-p-widtha bomp-pos message'>
            <label class='lab_a fl_l'>收件人：</label>
            <div class="lab_icon" style="width:661px;">
                <input name="txt" type='text' value='' id="txt" class='ipt_a w_d bomp-focus fl_l' style="width:595px !important;"/>
                <label class="lab_hid">请输入接收人</label>
                <i class="lab_icon_a"></i>
                <div class="drop" style="display:none;right:53px;">
                    <label class="arrow"><em>◆</em><span>◆</span></label>
                    <div class="box">
                        <label class="ser"><input type='text' value='' class="ipt" /><span class="icon">搜索</span><i class="icon_i"></i></label>
                        <div class='com-table bomp-table-a fl_l' style="width:97%;height:312px;overflow-y:auto;margin-left:6px;">
                            <table width='100%' cellspacing='0' cellpadding='0' class='sty-borcolor-a sty-borcolor-c com-table-b com-radius' style="border:solid #cbcbcb 1px;">
                                <thead>
                                <tr class='sty-bgcolor-b'>
                                    <th><span class="sp sty-borcolor-b skin-minimal"><input type="checkbox" name="a" class="check" /></span></th>
                                    <th><span class='sp sty-borcolor-b'>企业名称</span></th>
                                    <th><span class='sp sty-borcolor-b'>联系人</span></th>
                                    <th>联系电话</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="checkbox" name="a" /></div></td>
                                    <td><div class='overflow_hidden w90' title='大华科技'>大华科技</div></td>
                                    <td><div class='overflow_hidden w50' title='安安'>安安</div></td>
                                    <td><div class='overflow_hidden w90' title='1546588554'>1546588554</div></td>
                                </tr>
                                <tr class="sty-bgcolor-b">
                                    <td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="checkbox" name="a" /></div></td>
                                    <td><div class='overflow_hidden w90' title='大华科技'>大华科技</div></td>
                                    <td><div class='overflow_hidden w50' title='安安'>安安</div></td>
                                    <td><div class='overflow_hidden w90' title='1546588554'>1546588554</div></td>
                                </tr>
                                <tr>
                                    <td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="checkbox" name="a" /></div></td>
                                    <td><div class='overflow_hidden w90' title='大华科技'>大华科技</div></td>
                                    <td><div class='overflow_hidden w50' title='安安'>安安</div></td>
                                    <td><div class='overflow_hidden w90' title='1546588554'>1546588554</div></td>
                                </tr>
                                <tr class="sty-bgcolor-b">
                                    <td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="checkbox" name="a" /></div></td>
                                    <td><div class='overflow_hidden w90' title='大华科技'>大华科技</div></td>
                                    <td><div class='overflow_hidden w50' title='安安'>安安</div></td>
                                    <td><div class='overflow_hidden w90' title='1546588554'>1546588554</div></td>
                                </tr>
                                <tr>
                                    <td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="checkbox" name="a" /></div></td>
                                    <td><div class='overflow_hidden w90' title='大华科技'>大华科技</div></td>
                                    <td><div class='overflow_hidden w50' title='安安'>安安</div></td>
                                    <td><div class='overflow_hidden w90' title='1546588554'>1546588554</div></td>
                                </tr>
                                <tr class="sty-bgcolor-b">
                                    <td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="checkbox" name="a" /></div></td>
                                    <td><div class='overflow_hidden w90' title='大华科技'>大华科技</div></td>
                                    <td><div class='overflow_hidden w50' title='安安'>安安</div></td>
                                    <td><div class='overflow_hidden w90' title='1546588554'>1546588554</div></td>
                                </tr>
                                <tr>
                                    <td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="checkbox" name="a" /></div></td>
                                    <td><div class='overflow_hidden w90' title='大华科技'>大华科技</div></td>
                                    <td><div class='overflow_hidden w50' title='安安'>安安</div></td>
                                    <td><div class='overflow_hidden w90' title='1546588554'>1546588554</div></td>
                                </tr>
                                <tr class="sty-bgcolor-b">
                                    <td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="checkbox" name="a" /></div></td>
                                    <td><div class='overflow_hidden w90' title='大华科技'>大华科技</div></td>
                                    <td><div class='overflow_hidden w50' title='安安'>安安</div></td>
                                    <td><div class='overflow_hidden w90' title='1546588554'>1546588554</div></td>
                                </tr>
                                <tr>
                                    <td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="checkbox" name="a" /></div></td>
                                    <td><div class='overflow_hidden w90' title='大华科技'>大华科技</div></td>
                                    <td><div class='overflow_hidden w50' title='安安'>安安</div></td>
                                    <td><div class='overflow_hidden w90' title='1546588554'>1546588554</div></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <i class="lab_icon_b"></i>
				<span class="drop_a" style="display:none;right:-45px;">
					<label class="arrow"><em>◆</em><span>◆</span></label>
					<label class="box">
                        <label class="list_right">
                            <i>aa<a href="javascript:;"></a></i>
                            <i>aa<a href="javascript:;"></a></i>
                            <i>aa<a href="javascript:;"></a></i>
                            <i>aa<a href="javascript:;"></a></i>
                            <i>aa<a href="javascript:;"></a></i>
                            <i>aa<a href="javascript:;"></a></i>
                            <i>aa<a href="javascript:;"></a></i>
                            <i>aa<a href="javascript:;"></a></i>
                            <i>aa<a href="javascript:;"></a></i>
                            <i>aa<a href="javascript:;"></a></i>
                            <i>aa<a href="javascript:;"></a></i>
                            <i>aa<a href="javascript:;"></a></i>
                            <i>aa<a href="javascript:;"></a></i>
                            <i>aa<a href="javascript:;"></a></i>
                            <i>aa<a href="javascript:;"></a></i>
                            <i>aa<a href="javascript:;"></a></i>
                        </label>
                    </label>
				</span>
            </div>
            <input type="button" onMouseMove="f.style.pixelLeft=event.x-60;f.style.pixelTop=this.offsetTop;" value="浏览" size="30" onClick="f.click()" class="liulan">
            <input type="file" id="f" onChange="txt.value=this.value" name="f" style="height:26px;" class="files"  size="1" hidefocus>
        </div>
        <p class='bomp-p bomp-p-widtha bomp-pos-a clearfix' style="margin-top:15px;z-index:0;">
            <label class='lab_a fl_l'>主题：</label>
            <input class="mail-bulk-subject overflow_hidden fl_l" type="text" title="">
        </p>
        <div class="mail-bulk-mass fl_l">
            <img class="fl_l" width="30" height="30" src="${ctx}/static/images/fashion.png" alt="">
            <a href="">添加附件</a>
            <span>（最大20MB）</span>
        </div>
        <div class='bomp-p bomp-p-widtha bomp-pos clearfix' style="z-index:0">
            <label class='lab_a fl_l'>正文：</label>
            <textarea id="editor" class="editor-textarea" name="editor" id="" cols="30" rows="10" style="z-index:-1"></textarea>
        </div>
        <div class='bomb-btn' style="margin-top:15px;margin-bottom:10px;">
            <label class='bomb-btn-cen'>
                <a href="javascript:;" class="com-btna bomp-btna com-btna-sty fl_l"><label>发送</label></a>
            </label>
        </div>
    </div>
    <div class="hyx-cm-right hyx-cfu-right fl_l" style="background:#f4f4f4;margin-top:15px;">
        <div class="hyx-cfu-tab">
            <ul class="tab-list clearfix" style="width:170px">
                <li class="select li_first">个性签名</li>
                <li class="li_last">邮件模板</li>
            </ul>
        </div>
        <div class="hyx-wlm-cent-timeline hyx-cfu-timeline" style="height:auto;overflow-y:auto">
            <div class="sign-of-use clearfix">
                <label class="fl_l" for="">选择签名：</label>
                <dl class="select fl_l" style="margin-bottom:4px;">
                    <dt>个性签名</dt>
                    <dd style="left:120px;top:41px;">
                        <ul>
                            <li><a href="###" title="不使用">不使用</a></li>
                            <li><a href="###" title="个性签名1">个性签名1</a></li>
                            <li><a href="###" title="个性签名2">个性签名2</a></li>
                        </ul>
                    </dd>
                </dl>
                <a class="add-sign-qm" href="###">+</a>
            </div>
            <!-- <p class="add-sign"><a class="add-sign-a" href="###">添加个性签名</a></p> -->
            <div class="amil-pers-sign">
                <div class="pers-sign-top">
                    <p class="sign-top-cw">Best Regards!</p>
                    <p class="sign-tip-name">周巍巍</p>
                    <p class="sign-top-sect">产品部</p>
                    <div class="sign-top-contect">
                        <p class="clearfix"><span style="text-indent:20px">企蜂通信技术有限公司</span></p>
                        <p class="clearfix"><label for="">地&nbsp;&nbsp;址：</label><span>浙江省杭州市西湖区翠柏路7号电子商务产品园408室（310012）</span></p>
                        <p class="clearfix"><label for="">总&nbsp;&nbsp;机：</label><span>0571-88970808</span></p>
                        <p class="clearfix"><label for="">手&nbsp;&nbsp;机：</label><span>18624523698</span></p>
                        <p class="clearfix"><label for="">电子传真：</label><span>0571-88970808</span></p>
                        <p class="clearfix"><label for="">电子邮箱：</label><span>88970808@qq.com</span></p>
                        <p class="clearfix"><label for="">客服专线：</label><span>400-8262277</span></p>
                    </div>
                </div>
                <div class="perso-sign-butt">
                    <a class="com-btnc bomp-btna com-btna-sty perso-sign-edit fl_l" href="###"><label>编辑</label></a>
                    <a class="com-btnd bomp-btna perso-sign-dele fl_l" id="close02" href="###"><label>删除</label></a>
                </div>

            </div>
        </div>
        <div class="hyx-wlm-cent-timeline hyx-cfu-timeline com-none" style="overflow-y:auto;">
            <div class="bulk-subje-all">
                <div class="mail-bulk-subje">
                    <h2 class="mail-bulk-title">模版1</h2>
                    <p class="mail-bulk-mould clearfix">
                        <label class="fl_l" for="">主题：</label>
                        <a href="###"><span class="overflow_hidden fl_l">关于房价的问题</span></a>
                    </p>
                </div>
                <div class="mail-bulk-subje">
                    <h2 class="mail-bulk-title">模版2</h2>
                    <p class="mail-bulk-mould clearfix">
                        <label class="fl_l" for="">主题：</label>
                        <a href="###"><span class="overflow_hidden fl_l">关于房价的问题</span></a>
                    </p>
                </div>
                <div class="mail-bulk-subje">
                    <h2 class="mail-bulk-title">模版3</h2>
                    <p class="mail-bulk-mould clearfix">
                        <label class="fl_l" for="">主题：</label>
                        <a href="###"><span class="overflow_hidden fl_l">关于房价的问题关于房价的问题关于房价的问题关于房价的问题关于房价的问题关于房价的问题关于房价的问题</span></a>
                    </p>
                </div>
                <div class="mail-bulk-subje">
                    <h2 class="mail-bulk-title">模版4</h2>
                    <p class="mail-bulk-mould clearfix">
                        <label class="fl_l" for="">主题：</label>
                        <a href="###"><span class="overflow_hidden fl_l">关于房价的问题</span></a>
                    </p>
                </div>
                <div class="mail-bulk-subje">
                    <h2 class="mail-bulk-title">模版5</h2>
                    <p class="mail-bulk-mould clearfix">
                        <label class="fl_l" for="">主题：</label>
                        <a href="###"><span class="overflow_hidden fl_l">关于房价的问题</span></a>
                    </p>
                </div>
            </div>
            <div class="subje-manag">
                <a href="###" class="com-btna subje-manag-butt com-btna-sty fl_l"><label>模板管理</label></a>
            </div>
        </div>
    </div>
</div>

</body>
</html>
