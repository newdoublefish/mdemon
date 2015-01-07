<%@ page contentType="text/html; charset=utf-8" language="java"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>loginPage</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="http://twitter.github.com/bootstrap/assets/css/bootstrap.css" rel="stylesheet">
    <link href="http://twitter.github.com/bootstrap/assets/css/bootstrap-responsive.css" rel="stylesheet">  
    <%String path=request.getContextPath(); %>  
  </head>
<body>
<form class="well" action="<%=path%>/handlelogin" method="post">
        <table border="1" style="border-collapse:collapse;">
            <tr align = "center">
                <td colspan="2">用户登入</td>    
            </tr>
            <tr>
                <td>用户名:</td>
                <td>
                    <input type = "text" name = "user.username"/>
                </td>
                
            </tr>
            <tr>
                <td>密码:</td>
                <td>
                    <input type = "text" name = "user.userpassword"/>        
                </td>
            </tr>
            <tr align = "center">
                <td colspan = "2">
                    <!-- type 为 button ，只调用onclick，不会调用action了，commit就会 -->
                    <input type = "button" name="register" value ="注册" onclick="changeR()" />
                    <button type="submit" class="btn">登入</button>
                </td>    
            </tr>
        </table>
    </form>
</body>
</html>