<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/"; %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <base href="<%=basePath%>">
    <script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/bs.css"/>
    <link rel="stylesheet" href="css/book_info.css"/>


    <title>书籍详情</title>
    <script type="application/javascript">
        $(function () {

            $(".book_message li").click(function () {
                $(".book_message li").removeClass("active");
                $(this).addClass("active");
                $(".nav_content").hide();
                var name = $(this).attr("id");
                $("#" + name + "_content").show();
            });

            $("#num_add").click(function () {
                var num = parseInt($("#buy_num").val());
                if (num < 10) {
                    $("#buy_num").val(num + 1);
                }
            });

            $("#num_sub").click(function () {
                var num = parseInt($("#buy_num").val());
                if (num > 1) {
                    $("#buy_num").val(num - 1);
                }
            });
        });

        function buyNow(bookId) {
            location.href =  "<%=basePath%>" + "order/info?bookId=" + bookId + "&buyNum=" + $("#buy_num").val();
        }

        function addCart(bookId) {
            location.href = "<%=basePath%>" + "cart/addition?bookId=" + bookId + "&buyNum=" + $("#buy_num").val();
        }
    </script>
</head>

<body>

<jsp:include page="header.jsp"/>

<!--
    作者：offline
    时间：2018-10-26
    描述：商品详情
-->
<div class="container">
    <div class="row" style="border-bottom:1px dashed #CCCCCC;margin-bottom:15px ;">
        <a href="#">
            <img src="img/1.jpg"/>
        </a>
    </div>
    <div class="row" id="breadcrumb" style="margin-bottom:40px" ;>
        <a href="index" target="_blank">
            <b>图书</b>
        </a>
        <span>&gt;</span>
        <a href="book/list?cateId=${bookInfo.bookCategoryId}" target="_blank">${bookInfo.categoryName}</a>
        <span>&gt;</span>
        <b>${bookInfo.name}</b>
    </div>
    <div class="row">
        <div class="col-lg-3 col-md-3 col-sm-12 col-xs-12" style="height: 400px;">
            <div>
                <a href="book/info/${bookInfo.bookId}">
                    <img src="${bookInfo.imageUrl}" width="290px" height="290px"/>
                </a>
            </div>
        </div>
        <div class="col-lg-9 col-md-6 col-sm-12 col-xs-12">
            <div class="name_info">
                <h1 style="font-weight: bold;">${bookInfo.name}</h1>
                <h2>
                    <span>${bookInfo.outline}</span>
                </h2>
            </div>
            <div class="book_info_base">
						<span class="book_info_span">
								作者<a href="#">${bookInfo.author}</a>著，
								<a href="#">${bookInfo.press}</a>&nbsp;出品
							</span>
                <span class="book_info_span">
								出版社：<a href="" target="_blank">${bookInfo.press}</a>
							</span>
                <span class="book_info_span">
								出版日期：<fmt:formatDate value="${bookInfo.publishDate}" pattern="yyyy-MM-dd"/>
							</span>
            </div>
            <div class="book_price">
                <p class="what_price">特价</p>
                <p class="price_info">
                    <span class="rob" style="font-size: 30px;">￥${bookInfo.price}</span>
                    <span style="color: red;">(${bookInfo.discount}折扣)</span>
                    <span>定价:</span>
                    <span class="oprice">￥${bookInfo.marketPrice}</span>
                </p>
            </div>

            <div class="buy_box">
                <div class="num buy_div">
                    <input type="text" class="text" id="buy_num" disabled="disabled" value="1" width="30px"
                           height="30px"/>
                    <a href="javascript:void(0);" class="num_add" id="num_add"></a>
                    <a href="javascript:void(0);" class="num_sub" id="num_sub"></a>
                </div>
                <div class="buy_div">
                    <div class="cart">
                        <a href="javascript:void(0);" onclick="addCart(${bookInfo.bookId})" class="add_cart">
                            <i class="cart_icon"></i> 加入购物车
                        </a>
                    </div>
                </div>
                <div class="buy_div buy_now_div">
                    <a href="javascript:void(0);" onclick="buyNow(${bookInfo.bookId})" class="buy_now">立即购买</a>
                </div>
                <div class="clear"></div>
                <div class="buy_tip">每账户最多可购买<b>10</b>件</div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-3 col-md-3 col-sm-12 col-xs-12">
            <div class="product_left">
                <h3 style="">浏览此商品的顾客也同时浏览</h3>
                <ul class="product_left_ul">

                    <c:forEach items="${recommendBookList}" var="recommendBook">
                        <c:if test="${recommendBook.bookId != bookInfo.bookId}">
                            <li class="product_li">
                                <p class="pic">
                                    <a href="book/info/${recommendBook.bookId}" class="img" target="_blank">
                                        <img src="${recommendBook.imageUrl}"/>
                                    </a>
                                <p class="price">
                                    <span class="rob">￥${recommendBook.price}</span>
                                    <span class="oprice">￥${recommendBook.marketPrice}</span>
                                </p>
                                <p class="name">
                                    <a href="book/info/${recommendBook.bookId}">${recommendBook.name}</a>
                                </p>
                                <p class="author">${recommendBook.author} 著，${recommendBook.press} 出</p>
                                </p>
                            </li>
                        </c:if>
                    </c:forEach>
                </ul>
            </div>
        </div>
        <div class="col-lg-9 col-md-9 col-sm-12 col-xs-12">

            <ul class="nav nav-tabs nav-justified book_message">
                <li class="active" id="book_detail">
                    <a href="javascript:void(0)">书籍详情</a>
                </li>
                <li id="book_comment">
                    <a href="javascript:void(0)">书籍评论</a>
                </li>
            </ul>
            <div class="content">
                <div id="book_detail_content" class="nav_content">
                    <ul id="key">
                        <li>开 本：${bookInfo.size}</li>
                        <li>纸 张：胶版纸</li>
                        <li>包 装：${bookInfo.packStyle}</li>
                        <li>是否套装：否</li>
                        <li>国际标准书号ISBN：${bookInfo.isbn}</li>
                    </ul>
                    <div id="detail" class="section">
                        <div id="bookDetail" class="section">
                            <div class="title">
                                <span class="title_span">书籍详情</span>
                            </div>
                            <p>
                                ${bookInfo.detail}
                            </p>
                        </div>


                        <div id="feature" class="section">
                            <div class="title">
                                <span class="title_span">产品特色</span>
                            </div>
                            <p>
                                ${empty bookDesc.bookDesc?"<img src='book/book3_1.jpg'>":bookDesc.bookDesc}
                            </p>
                        </div>
                        <div class="abstract" class="section">
                            <div class="title">
                                <span class="title_span">作者简介</span>
                            </div>
                            <blockquote>
                                <pre>${bookInfo.author}</pre>
                            </blockquote>
                        </div>
                        <div class="content" class="section">
                            <div class="title">
                                <span class="title_span">目　　录</span>
                            </div>
                            <blockquote>
                                <pre>${bookInfo.catalog}</pre>
                            </blockquote>
                        </div>
                    </div>
                </div>
                <div id="book_comment_content" style="display: none;" class="nav_content">
                    商品评论
                </div>
            </div>
        </div>

    </div>
</div>
</body>
</html>