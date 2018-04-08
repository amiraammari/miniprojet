<?php 
require "modele.html";
?>
	<div id="contenu" align="center">
		
<form method="post" >
<table width="200">
  <tr>
    <th scope="col"><div align="left"><strong>Nom</strong></div></th>
    <th scope="col"><input type="text" name="nom" /></th>
  </tr>
  <tr>
    <td><strong>Prenom</strong></td>
    <td><input type="text" name="prenom" /></td>
  </tr>
  <tr>
    <td><strong>Adresse</strong></td>
    <td><input type="text" name="adresse" /></td>
  </tr>
  <tr>
    <td><strong>E-mail</strong></td>
    <td><input type="text" name="email" /></td>
  </tr>
  <tr>
    <td><strong>Telephone</strong></td>
    <td><input type="text" name="tel" /></td>
  </tr>
</table>
<p>&nbsp;</p>
<p align="center">
  <input name="submit" type="submit" value="Valider" />
</p>
</form>

<?php 
include('connexion.php');
if(isset($_POST['nom'])&&isset($_POST['prenom'])&&isset($_POST['adresse'])&&isset($_POST['tel'])&&isset($_POST['email'])){
$nom=$_POST['nom'];
$prenom=$_POST['prenom'];
$adresse=$_POST['adresse'];
$tel=$_POST['tel'];
$email=$_POST['email'];

if($nom!="" and $adresse!="" and $tel!=""  and $email!="")
{
	if(preg_match("/^.+@.+\..+$/i", $email))
	{
		
        $requete="INSERT INTO inscrit VALUES(null,'$nom','$prenom','$tel','$adresse','$email')";

        $cn->query($requete) or die($cn->error);
  
      
$nbrec=$cn->affected_rows;

        if($nbrec>0)
         {
	
	        echo"Ajout fait avec succes";
         }
		elseif($nbrec==0)
         {
	       echo"Echec d'ajout verifier SVP...!";
         }

        $cn->close();
		
	}elseif(!preg_match("/^.+@.+\..+$/i", $email)){ echo"Email non valide verifier SVP...!";}
}
else
{
	echo "Il faut entrer les donnees adherent....!";
}
}

?>
	