## Utilisation de JWT pour l'authentification

### Notre table 'student' est vide au début
<img src="https://github.com/user-attachments/assets/e0d8e077-7bb9-4c9c-92f8-5a9cf946281b">

### On exécute la requête suivante pour enregistrer un nouveau utilisateur (méthode Post)
<img src="https://github.com/user-attachments/assets/37b65aba-015d-42d5-9baf-3d20803cda07">

Dans le cas de 'Register', le filtre 'AppFilter' s'applique également<br>
(voir la requête INSERT -register-)
<img src="https://github.com/user-attachments/assets/beb8ef33-d6e7-4fd4-9045-25ee031a0be4">


### L'utilisateur s'ajoute dans la table de la base de données
<img src="https://github.com/user-attachments/assets/fc07cba2-6a19-421e-9f64-1e1776241f95">

### Connexion
Lorsqu'on se connecte, on a toujours ce filtre 'AppFilter' qui s'exécute <br>
Dans le cas de connexion, on ne voit aucun token d'où le résultat suivant<br>
(voir la requête SELECT -login-)
<img src="https://github.com/user-attachments/assets/48279ca2-71bd-4d45-ab6d-a0701aab892b">

Si la connexion échoue, on envoie un message d'erreur dans le body de la réponse
<img src="https://github.com/user-attachments/assets/0b2b7a44-2786-4ebe-9a38-0b00a83de9a8">

SI la connexion réussie, on envoie le token généré<br>
Le token qu'on doit envoyé avec les requêtes à venir
<img src="https://github.com/user-attachments/assets/7c3d28a0-b4f1-4f63-989a-61c8e8af9696">

### Accès à une autres API après 'login'
**cas : No Auth**
<img src="https://github.com/user-attachments/assets/f17626da-a63a-4f78-9bc1-df4d45febb08">
Le filter s'applique également, et puisque on envoit aucun token, alors on obtient le résultat suivant
<img src="https://github.com/user-attachments/assets/e284d4b1-e966-4054-a6f3-862063f73604">
<br>
**cas : with Bearer Token -Expired token**
<img src="https://github.com/user-attachments/assets/3e041e06-0c87-4f1a-8d50-cbd4421e528f">
<img src="https://github.com/user-attachments/assets/698924d6-27da-42d1-864c-d58db20852c6">
<br>
**cas : with Bearer Token -invalid token**
<img src="https://github.com/user-attachments/assets/284d0769-05d8-41e7-9d48-a5009d1bb6c8">
<br>
**cas : with Bearer Token -valid token**
<img src="https://github.com/user-attachments/assets/89b625e9-4089-4e10-8226-3ee10809a409">
<img src="https://github.com/user-attachments/assets/f30c0b47-0a0e-4106-9f0c-c6ff9a5ebd42">
<img src="https://github.com/user-attachments/assets/11a9e128-5444-457b-a5a6-ea1e3f73a134">

