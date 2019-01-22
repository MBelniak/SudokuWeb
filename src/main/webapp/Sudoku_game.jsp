<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: model
  Date: 09.09.2018
  Time: 17:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sudoku Game</title>
    <link rel="stylesheet" href="resources/style2.css" type="text/css"/>
    <script src="resources/jquery-3.3.1.min.js"></script>
    <script src="resources/Sudoku_game.js"></script>
</head>
<body >
    <div id="board" style="display: table">
        <script>
            var div_contents = "";
            <c:forEach begin="0" end="80" var="i">
            <c:set var="k" value="${i}" />
            <c:choose>
            <c:when test="${((k%9>5||k%9<3)&&((k/9-(k/9 % 1)>2)&&(k/9-(k/9 %1)<6)))||((k%9<6&&k%9>2)&&(k/9-(k/9 %1)<3||(k/9-(k/9 %1))>5))}">
              div_contents += '<input class="gray_field" style="display: table-cell" name="grid" maxlength="1" />';
            </c:when>
            <c:otherwise>
              div_contents += '<input class="field" name="grid" style="display: table-cell" maxlength="1" />';
            </c:otherwise>
            </c:choose>
            <c:choose>
            <c:when test="${k%9==8}">
            div_contents+='<div style="clear: both"></div>';
            </c:when>
            </c:choose>
            </c:forEach>
            document.getElementById("board").innerHTML = div_contents;
        </script>
    </div>
    <div id="clicks">
        <button id='check' style='font-size: 14px; width: 80px;'>Check</button>
        <button onclick="window.location.href='index.jsp'">Quit</button>
    </div>
    <div id="test">

    </div>


</body>
</html>
