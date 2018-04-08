                     
<?php 
require "modele.html";
?>
        
 
	<div id="contenu">
		
<form method="post" >
<table width="200">

  
  <tr>
    <td><strong>Nom_Auteur</strong></td>
    <td><input type="text" name="nom_a" /></td>
  </tr>
  <tr>
    <td><strong>Prenom_Auteur</strong></td>
    <td><input type="text" name="prenom_a" /></td>
  </tr>
 
  
  </table>
<p>&nbsp;</p>
<p align="center">
  <input name="submit" type="submit" value="Valider" />
</p>
</form>

<?php include('connexion.php');
if(isset($_POST['nom_a'])&& isset($_POST['prenom_a'])){

$Nom_Auteur=$_POST['nom_a'];
$Prenom_Auteur=$_POST['prenom_a'];



if($Nom_Auteur!="" and $Prenom_Auteur!="" )
{
		
        $query="INSERT INTO auteur VALUES(null,'$Nom_Auteur','$Prenom_Auteur')";

        $cn->query($query) or die($cn->error);
  
      
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
		
	
}
else
{
	echo "Il faut entrer les donnees adherent....!";
}

}
?>
	</div>
	<div id="pied_page">Ceci est le pied de page</div>
</body>
</html>


