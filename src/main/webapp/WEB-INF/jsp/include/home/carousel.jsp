<%--
  Created by IntelliJ IDEA.
  User: ouhikoshin
  Date: 2017/11/19
  Time: 下午9:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html;charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>

<div id="carousel-of-product"  class="carousel-of-product carousel slide1" data-ride="carousel">
    <!-- Indicators -->
    <ol class="carousel-indicators">
        <li data-target="#carousel-of-product" data-slide-to="0" class="active"></li>
        <li data-target="#carousel-of-product" data-slide-to="1"></li>
        <li data-target="#carousel-of-product" data-slide-to="2"></li>
        <li data-target="#carousel-of-product" data-slide-to="3"></li>
    </ol>

    <!-- Wrapper for slides -->
    <div class="carousel-inner" role="listbox">
        <div class="item active">
            <img class="carousel carouselImage" src="img/lunbo/1.jpg" >
        </div>
        <div class="item">
            <img  class="carouselImage" src="img/lunbo/2.jpg" >
        </div>
        <div class="item">
            <img  class="carouselImage" src="img/lunbo/3.jpg" >
        </div>

        <div class="item">
            <img  class="carouselImage" src="img/lunbo/4.jpg" >
        </div>

    </div>

</div>
