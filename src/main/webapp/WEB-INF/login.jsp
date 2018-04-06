<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>

<form action="/servlet/login" method="post">

    <div class="field">
        <label>Enter your login:</label>
        <div class="input"><input type="text" name="email" /></div>
    </div>

    <div class="field">
        <a href="#">Forgot your password?</a>
        <label>Enter your password:</label>
        <div class="input"><input type="password" name="email" /></div>
    </div>

    <div class="submit">
        <button type="submit">Enter</button>
        <label id="remember"><input name="email" /> Remember me</label>
    </div>

</form>

</body>
</html>
