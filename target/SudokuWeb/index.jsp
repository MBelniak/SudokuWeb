<%--
  Created by IntelliJ IDEA.
  User: model
  Date: 07.09.2018
  Time: 16:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>

<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/style.css" type="text/css"/>
    <title>Sudoku Game</title>
</head>
<body>
<div>
    <form action="showSudoku">
        <select name="difficulty">
            <option value="0">Łatwy</option>
            <option value="1">Średni</option>
            <option value="2">Trudny</option>
        </select>
        <input type="submit" value="Start"/>
    </form>
</div>
</body>
</html>
