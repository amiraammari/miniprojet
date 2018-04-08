<?php
require"modele.html";
include("connexion.php");

$num=$_GET['num_ins'];


$query="DELETE FROM inscrit WHERE num_ins='$num'";

$cn->query($query) or die($cn->error);

$cn->close();

require_once("liste_adherents.php");



?>