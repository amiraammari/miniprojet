 <?php
$isbn = $_POST['isbn'];
$nb_disponible = $_POST['nb_disponible'];

include_once("connexion.php");
?>
<div class="bord">
<h3>Entrez un numero d'inscription</h3>
    <form method="post" action="<?php $_SERVER['PHP_SELF'] ?>">
        <fieldset>
            N°inscription<input type="text" name="num_ins" size="16"
                                  maxlength="16" />
            <input type="submit" name="envoi" value=" Emprunter " />
        </fieldset>
    </form>
</div>


<?php
if (isset($_POST['num_ins'])) {
//Récupération des saisies
    $num_ins = $_POST['num_ins'];
	echo $isbn;
	
	  $requetExp = "select ref_exp from exemplaire where isbn='$isbn' and etat='1'";
        $res1=$cn->query($requetExp) or die($cn->error);
$nbrec=$cn->affected_rows;
echo $nbrec;		
		 while ($tab = $res1->fetch_array(MYSQLI_ASSOC)) {
		 echo"je suis la";
		 
	 $date = date("Y-m-d");
//Insertion dans la table emprunt
$ref=$tab["ref_exp"];
        $requete = "INSERT INTO emprunt VALUES(null,'$date_emp','$num_ins','$ref')";
        $cn->query($requete) or die($cn->error);
  
  
   $requete2 = "update  ouvrage set (nb_disponible='$nb_disponible-1'";
        $cn->query($requete2) or die($cn->error);
  
	   
	 require "recherche_ouvrage.php";
	
	}
	
	
}	
	
?>