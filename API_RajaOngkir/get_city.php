<?php
$key = "bb01170af7f1517f8c8e4a5e0ec8a378"; // Ganti dengan API Key RajaOngkir kamu
$province_id = $_GET['province'];

$curl = curl_init();
curl_setopt_array($curl, array(
  CURLOPT_URL => "https://api.rajaongkir.com/starter/city?province=$province_id",
  CURLOPT_RETURNTRANSFER => true,
  CURLOPT_HTTPHEADER => array("key: $key"),
));

$response = curl_exec($curl);
curl_close($curl);

echo $response;
?>
