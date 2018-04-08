<?php
require ("modele.html");
include("connexion.php");
?>
	<div id="contenu">


    <form action="<?php $_SERVER['PHP_SELF'] ?>" method="post">
        <fieldset>
            Rechercher un ouvrage <input type="text" name="motcle" size="30"
                                         maxlength="256" />
            Type :
            <select name="type" size="1">
                <option value=""> Toutes</option>
                <option value="livre"> livre</option>
				<option value=" manuel"> manuel</option>
                <option value="dectionnair"> dectionnair</option>
                <option value="revue"> revue </option>
            </select>
            Trier par : disponibilit&eacute; 
            <input type="checkbox" name="nb_disponible" value="nb_disponible"
                                      checked="checked" />
            <input type="submit" name="envoi" value=" O K " />
        </fieldset>
    </form>
    <div>
        <?php
        if (isset($_POST['envoi'])) {
            //Récupération des saisies
            $motcle = $_POST['motcle'];
            $type = $_POST['type'];
            $tri = $_POST['nb_disponible'];
            //Création de la requête
            $requete = "SELECT  DISTINCT ouvrage.isbn,titre_ouvrage,photo_couverture,nom_a, nom_type, nb_disponible FROM `ouvrage`, exemplaire, auteur,type  WHERE 
(ouvrage.isbn=exemplaire.isbn) and
(ouvrage.id_type=type.id_type) and
(ouvrage.id_aut=auteur.id_auteur) and ";
            if ($motcle)
                $requete.=" titre_ouvrage LIKE '%$motcle%' ";
            if ($type != "")
                $requete.=" AND nom_type='$type' ";
            $requete.=" ORDER BY $tri";
           $result=$cn->query($requete) or die($cn->error);
           $nbexp=$cn->affected_rows;

            echo "<h3>Il y a $nbexp ouvrages répondant à votre recherche</h3>";
            while ($tab = $result->fetch_array(MYSQLI_ASSOC)) {
                echo"<form action=\"emprunter.php\" method=\"post\">
               	
 <div class=\"bord\" float=\"left\"><img src=\"", $tab['photo_couverture'],"\"/> </div>
			    <div class=\"bord\"><b>", $tab['isbn'], "</b><br /> 
                    Titre ouvrage : ", $tab['titre_ouvrage'], "  <br /> 
                    Auteur :", $tab['nom_a'], " <br /> 
            nombre disponible dans le stock : ", $tab['nb_disponible'], "  
                    

       <input type=\"hidden\" name=\"isbn\" value=\"", $tab['isbn'], "\" /> 
      <input type=\"hidden\" name=\"nb_disponible\" value=\"", $tab['nb_disponible'], "\" />
       <input type=\"hidden\" name=\"titre_ouvrage\" value=\"", $tab['titre_ouvrege'], "\" />
                     <input type=\"submit\" value=\"emprunter\" /> </div>";
                echo"</form>";
				
            }
        }
        ?>
    </div>
</div>

