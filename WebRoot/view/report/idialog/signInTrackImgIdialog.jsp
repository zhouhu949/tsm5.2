<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/include.jsp"%>
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
      font-size: 18px;
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
    img{
    	height:100%;
    }
  </style>
</head>
<body>
<input type="hidden"  id="params" value=${params }>
  <!-- Swiper -->
  <div class="swiper-container">
    <div class="swiper-wrapper">
     <!--  <div class="swiper-slide">Slide 1</div>
      <div class="swiper-slide">Slide 2</div>
      <div class="swiper-slide">Slide 3</div>
      <div class="swiper-slide">Slide 4</div>
      <div class="swiper-slide">Slide 5</div>
      <div class="swiper-slide">Slide 6</div>
      <div class="swiper-slide">Slide 7</div>
      <div class="swiper-slide">Slide 8</div>
      <div class="swiper-slide">Slide 9</div>
      <div class="swiper-slide">Slide 10</div> -->
    </div>
    <!-- Add Arrows -->
    <div class="swiper-button-next"></div>
    <div class="swiper-button-prev"></div>
  </div>

  <!-- Swiper JS -->
  <script type="text/javascript" src="${ctx}/static/js/base64.js${_v}"></script>
  <script src="${ctx }/static/thirdparty/Swiper-4.0.5/dist/js/swiper.min.js"></script>
  

  <!-- Initialize Swiper -->
  <script>
    $(function(){
    	var params = $("#params").val();
    	var b = new Base64();
    	data= b.decode(params)
    	var strArr = data.split("#");
    	 var html = "";
    	for(var i = 0 , len = strArr.length ; i < len ; i++){
    		html+= ' <div class="swiper-slide"><img src="'+strArr[i]+'"></div>';
    	}
    	console.log(html)
	   	  $(".swiper-wrapper").html(html); 
	  	      var swiper = new Swiper('.swiper-container', {
		      navigation: {
		        nextEl: '.swiper-button-next',
		        prevEl: '.swiper-button-prev',
		      },
	    });
    })
  </script>
</body>
</html>

 

  

	

