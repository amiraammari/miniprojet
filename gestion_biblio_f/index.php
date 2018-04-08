<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="styles.css">
<title>Positionnement CSS</title>
</head>
<body>

	<div id="bandeau"> <h1>Gestion de bibliotheque</h1>
	
	
	</div>
	<div id="contenu">
	<form method="post">
    <table width="200"  align="center">
      <tr>
        <th scope="col">Pseudo</th>
        <th scope="col"><input type="text" name="login" /></th>
      </tr>
      <tr>
        <td>Password</td>
        <td><input type="password" name="password" /></td>
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
if(isset($_POST['login'])&&isset($_POST['password'])){
$user = $_POST['login'];
$pwd = $_POST['password'];


$query = "select * from user where Login='$user' and Password='$pwd'";

$result = $cn->query($query);

if( $result->fetch_array(MYSQLI_ASSOC)){



     header("location:http://127.0.0.1/gestion_biblio_f/recherche_ouvrage.php");
}


    else echo"Compte invalide, verifier SVP...!";

}
$cn->close();
?>
	</div>
    <div id="pied_page"></div>
</body>
</html>
