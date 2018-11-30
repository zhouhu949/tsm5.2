<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>
<%@ page import="com.qftx.base.shiro.ShiroUtil" %>
<% pageContext.setAttribute("shiroUser", ShiroUtil.getShiroUser());%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>Swiper demo</title>
  <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">

  <!-- Link Swiper's CSS -->
  <link rel="stylesheet" href="${ctx }/static/thirdparty/Swiper-4.0.5/dist/css/swiper.min.css">

  <!-- Demo styles -->
  <style>
    html, body {
      position: relative;
      height: 100%;
    }
    body {
      background: #eee;
      font-family: Helvetica Neue, Helvetica, Arial, sans-serif;
      font-size: 14px;
      color:#000;
      margin: 0;
      padding: 0;
    }
    .swiper-container {
      width: 100%;
      height: 100%;
    }
    .swiper-slide {
      text-align: center;
      font-size: 14px;
      background: #fff;

      /* Center slide text vertically */
      display: -webkit-box;
      display: -ms-flexbox;
      display: -webkit-flex;
      display: flex;
      -webkit-box-pack: center;
      -ms-flex-pack: center;
      -webkit-justify-content: center;
      justify-content: center;
      -webkit-box-align: center;
      -ms-flex-align: center;
      -webkit-align-items: center;
      align-items: center;
    }
    .swiper-pagination-bullet {
      width: 14px;
      height: 14px;
      text-align: center;
      line-height: 14px;
      font-size: 12px;
      /* color:#adc2e0; */
      opacity: 1;
      background: #adc2e0;
    }
    .swiper-pagination-bullet-active {
      color:#fafafa;
      background: #fafafa;
    }
    .swiper-slide>img{
        width:100%;
    }
  </style>
</head>
<body>
  <!-- Swiper -->
  <div class="swiper-container">
    <div class="swiper-wrapper">
      <div class="swiper-slide swiper-no-swiping"><img src="${ctx}/static/images/showVersion1.png"/></div>
      <div class="swiper-slide swiper-no-swiping"><img src="${ctx}/static/images/showVersion2.png"/></div>
      <div class="swiper-slide swiper-no-swiping"><img src="${ctx}/static/images/showVersion3.png"/></div>
      <div class="swiper-slide swiper-no-swiping"><img src="${ctx}/static/images/showVersion4.png"/></div>
      <!-- <div class="swiper-slide swiper-no-swiping"><img src="${ctx}/static/images/showVersion5.png"/></div> -->
    </div>
    <!-- Add Arrows -->
    <!-- <div class="swiper-button-next"></div>
    <div class="swiper-button-prev"></div> -->
    
    <div class="swiper-pagination"></div>
  </div>

  <!-- Swiper JS -->
  <script src="${ctx }/static/thirdparty/Swiper-4.0.5/dist/js/swiper.min.js"></script>
  

  <!-- Initialize Swiper -->
  <script>
    var swiper = new Swiper('.swiper-container', {
      noSwiping:true,
      pagination: {
        el: '.swiper-pagination',
        clickable: true,
        renderBullet: function (index, className) {
          return '<span class="' + className + '"></span>';
        },
      },
    });
  </script>
</body>
</html>
