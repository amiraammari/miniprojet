
<?php 
require "modele.html";
?>
<p align="center" class="Style1">ajouter ouvrage </p>

<body>
<div id="contenu">
<form method="post" >
<table width="474" height="251" >
  <tr>
    <td><span class="Style4">ISBN</div></th>
  
     <td> <input type="text" name="id_ouvrage" /></td>
  </tr>
  <tr>
    <td><span class="Style4">Titre Ouvrage</span></td>
    <td><input type="text" name="titre_ouvrage" /></td>
  </tr>
  <tr>
    <td><span class="Style4">Nombre de Page</span></td>
    <td><input type="text" name="nb_page" /></td>
  </tr>
  
  <tr>
    <td><span class="Style4">Type</span></td>
    <td><select name="t">
    <?php include('connexion.php');
	$req="select * from Type";
	$result = $cn->query($req);
						 
	while($row = $result->fetch_array(MYSQLI_ASSOC)){?>	 
              
              <option value="<?php echo $row['id_type']; ?>">
			  <?php  echo $row['nom_type'] ; ?>
			  </option> 
	
	<?php   } $cn->close(); ?>	   
    
   </select></td>
  </tr>
  <tr>
    <td><span class="Style4">Auteur</span></td>
    <td><select name="aut">
      <?php include('connexion.php');
	$req="select * from auteur";
	$result = $cn->query($req);
						 
	while($row = $result->fetch_array(MYSQLI_ASSOC)){?>
      <option value="<?php echo $row['id_auteur']; ?>">
      <?php  echo $row['nom_a']." ".$row['prenom_a'] ; ?>
      </option>
      <?php   }
	   $cn->close(); ?>
    </select></td>
  </tr>
    <tr>
    <td><span class="Style4">nbre total dans le stock</span></td>
    <td><input type="text" name="nb_total" /></td>
  </tr>
  <tr>
    <td><span class="Style4">nbre disponible</span></td>
    <td><input type="text" name="nb_disponible" /></td>
  </tr>
</table>
<input name="submit" type="submit" value="Valider" />
</form>
<?php include('connexion.php');
if(isset($_POST['isbn'])&&isset($_POST['titre_ouvrage'])&&isset($_POST['nb_page'])&&isset($_POST['t']) &&isset($_POST['aut'])){
$id_ouvrage=$_POST['isbn'];
$Titre_ouvrage=$_POST['titre_ouvrage'];
$nb_page=$_POST['nb_page'];
$id_type=$_POST['t'];
$id_aut=$_POST['aut'];
$nb_total=$_POST['nb_total'];
$nb_disponible=$_POST['nb_disponible'];
if($id_ouvrage!="" and $Titre_ouvrage!="" and $nb_page!=""  and $num_rayon!="" and $id_type!="" and $id_aut!="")
{
	
	
		
        $query="INSERT INTO ouvrage VALUES('$id_ouvrage','$Titre_ouvrage','$nb_page','$id_type','$id_aut','$nb_total','$nb_disponible')";

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
	echo "Il faut entrer les donnees ouvrage....!";
}
}

?>
</div>