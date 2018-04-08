<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Document sans titre</title>
<style type="text/css">
<!--
.Style1 {color: #0000FF}
body,td,th {
	font-size: 36px;
	color: #0000FF;
}
body {
	background-color: #CCCCCC;
}
-->
</style>
</head>

<body>
	<div align="center" class="Style1">PAGE ADMINISTRATEUR </div>
    <p align="center">&nbsp;</p>
	<form method="post">
    <table width="200"  align="center">
      <tr>
        <th scope="col">Pseudo</th>
        <th scope="col"><input type="text" name="textfield" /></th>
      </tr>
      <tr>
        <td>Password</td>
        <td><input type="password" name="textfield2" /></td>
      </tr>
    </table>
    <p align="center">
      <input name="submit" type="submit" value="Valider" />
	  
    </p>
</form>
    <p align="center">&nbsp;</p>
    <p align="center">&nbsp;</p>
	
	<?php
include 'connexion.php';
if(isset($_POST['user'])&&isset($_POST['pwd'])){
$user = $_POST['login'];
$pwd = $_POST['password'];


$query = "select * from user where Login='$user' and Password='$pwd'";

$result = $cn->query($query);

if( $result->fetch_array(MYSQLI_ASSOC)){



     header("location:http://127.0.0.1:8000/gestion_biblio_f/menu.php");
}


    else echo"Compte invalide, verifier SVP...!";

}
$cn->close();
?>
</body>
</html>
