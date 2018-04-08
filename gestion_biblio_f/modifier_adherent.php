<?php
require"modele.html";
include("connexion.php");

$num=$_GET['num_ins'];

$req="select * from inscrit where num_ins='$num'";

$result = $cn->query($req) or die($cn->error);

$row = $result->fetch_array(MYSQLI_ASSOC);

?>
<html>
<head>
<meta charset="utf-8">
</head>
<body>
<h1 align="center"><b><u><font color="#0000FF">Modifier client</font></u></b></h1>
<form  method="post" >
<table>
<tr><td>N°inscription</td><td><input type="text" name="num" value="<?php echo $row['num_ins']; ?>" readonly></td></tr>
<tr><td>Nom</td><td><input type="text" name="nom" value="<?php echo $row['nom']; ?>"></td></tr> 
<tr><td>Prénom</td><td><input type="text" name="prenom" value="<?php echo $row['prenom']; ?>"></td></tr>
<tr><td>Adresse</td><td><input type="text" name="adresse" value="<?php echo $row['adresse']; ?>"></td></tr>
<tr><td>Tél</td><td><input type="text" name="tel" value="<?php echo $row['tel']; ?>"></td></tr>
<tr><td>Email</td><td><input type="text" name="email" value="<?php echo $row['email']; ?>"></td></tr>

<tr><td colspan="2" align="justify"><input type="submit" name="mdfcli" value="Modifier"></td></tr>
</table>
</form>


<?php include('connexion.php');

if(isset($_POST['nom'])&&isset($_POST['prenom'])&&isset($_POST['adresse'])&&isset($_POST['tel'])&&isset($_POST['email'])){
$nom=$_POST['nom'];
$prenom=$_POST['prenom'];
$adresse=$_POST['adresse'];
$tel=$_POST['tel'];
$email=$_POST['email'];

if(preg_match("/^.+@.+\..+$/i",$email))
{
	$req2="update inscrit set nom='$nom',prenom='$prenom',tel='$tel',adresse='$adresse',email='$email'
	     where num_ins='$num'";
			
	$cn->query($req2) or die($cn->error);

    $cn->close();

    require_once("liste_adherents.php");	
	
}elseif(!preg_match("/^.+@.+\..+$/i",$email)){ echo"Email non valide verifier SVP...!";}
}
?>
<?php $cn->close(); ?>
</body>
</html>
