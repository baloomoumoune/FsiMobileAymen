<?php

header("Content-Type: application/json");

$pdo = new PDO("mysql:host=" . DB_HOST . ";dbname=" . DB_NAME, DB_USER, DB_PASS);
$pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

function login($logUti, $mdpUti) {
    global $pdo;
    $stmt = $pdo->prepare("SELECT idUti FROM Utilisateur WHERE logUti = ? AND mdpUti = ?");
    $stmt->execute([$logUti, $mdpUti]);
    $result = $stmt->fetch(PDO::FETCH_ASSOC);

    if ($result) {
        return [
            "success" => true,
            "idUti" => $result['idUti']
        ];
    } else {
        return [
            "success" => false,
            "idUti" => 0
        ];
    }
}

function getUserInfo($idUti) {
    global $pdo;
    $stmt = $pdo->prepare("SELECT nomUti, preUti, mailUti, telUti, adrUti, vilUti, cpUti FROM Utilisateur WHERE idUti = ?");
    $stmt->execute([$idUti]);
    return $stmt->fetch(PDO::FETCH_ASSOC);
}

function getEntrepriseMaitre($idUti) {
    global $pdo;
    $stmt = $pdo->prepare("SELECT e.nomEnt, m.nomMaitapp FROM Utilisateur u 
                           JOIN Entreprise e ON u.idEnt = e.idEnt 
                           JOIN Maitre_d_apprentissage m ON u.idMaitapp = m.idMaitapp 
                           WHERE u.idUti = ?");
    $stmt->execute([$idUti]);
    return $stmt->fetch(PDO::FETCH_ASSOC);
}

function getTuteur($idUti) {
    global $pdo;
    $stmt = $pdo->prepare("SELECT t.nomTut FROM Utilisateur u 
                           JOIN Tuteur t ON u.idTut = t.idTut 
                           WHERE u.idUti = ?");
    $stmt->execute([$idUti]);
    return $stmt->fetch(PDO::FETCH_ASSOC);
}

$action = $_GET['action'] ?? '';
$idUti = $_GET['idUti'] ?? 0;

if ($action === "login") {
    if (isset($_POST['logUti'], $_POST['mdpUti'])) {
        echo json_encode(login($_POST['logUti'], $_POST['mdpUti']));
    } else {
        echo json_encode(["success" => false, "idUti" => 0, "error" => "Paramètres manquants"]);
    }
} elseif ($action === "getUserInfo") {
    echo json_encode(getUserInfo($idUti));
} elseif ($action === "getEntrepriseMaitre") {
    echo json_encode(getEntrepriseMaitre($idUti));
} elseif ($action === "getTuteur") {
    echo json_encode(getTuteur($idUti));
} else {
    echo json_encode(["error" => "Action non valide"]);
}

?>
