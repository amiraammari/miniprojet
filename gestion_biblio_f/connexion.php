<?php 

$cn = new mysqli("localhost", "root", "mysql", "biblio");



if ($cn->connect_errno) {
    echo "Echec lors de la connexion a la base de donnee : (" . $cn->connect_errno . ") " . $cn->connect_error;
}

?>