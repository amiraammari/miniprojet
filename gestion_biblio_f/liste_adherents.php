<?php 
require "modele.html";
?>
	<div id="contenu">
		
	<table width="100%" border="1">
  <tr>
    <th>N&#65533;inscription</th>
    <th>Nom</th>
    <th>Pr&#65533;nom</th>
    <th>Tel</th>
    <th>Adresse</th>
    <th>Email</th>
  </tr>
   
    <?php include 'connexion.php' ;
  
        $query="select * from inscrit";
		
	    $result = $cn->query($query);
						 
	    while($row = $result->fetch_array(MYSQLI_ASSOC)){
  
  ?>
  <tr>
    <td><?php echo $row['num_ins']; ?></td>
    <td><?php echo $row['nom']; ?></td>
    <td><?php echo $row['prenom']; ?></td>
    <td><?php echo $row['tel']; ?></td>
    <td><?php echo $row['adresse']; ?></td>
    <td><?php echo $row['email']; ?></td>
    
    <td><a href="modifier_adherent.php?num_ins=<?php echo $row['num_ins']; ?>">Modifier</a></td>
    <td><a href="supprimer_adherent.php?num_ins=<?php echo $row['num_ins']; ?>">Supprimer</a></td>
  </tr>
  <?php
	}
	
  ?>
</table>
   
	</div>




