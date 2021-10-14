# Release 06

#### Tag: [v6]
<br>


### Bilan de l'itération des fonctionnalités implémentées
Le pattern Factory pour la création des joueurs a été implémenté
(les joueurs sont maintenant crées à l'extérieur de Game), le sacrifice des cartes lors des échanges a été rétabli,
les poms ont été nettoyés 

SonarQube a été fait et il est accessible sur [ce lien](http://sonarqube.7wonders.lucy-chan.fr/dashboard?id=fr.unice%3ASeven-Wonders)

Le jeu envoi plus d'informations au serveur pour les statistiques (la liste des ressources et les cartes jouées de chaque joueur).
Avec ces statistiques le serveur génère un fichier .csv.
Les échanges entre joueurs ont été implémentés et les merveilles peuvent être jouées et leurs étapes achetées. 


<br>

### Bilan de l'itération sur les tests
Des tests ont été réalisés (Player, PointsCalulator, Referee, Card) ils passent tous. Un mock a aussi été fait pour tester la classe Controller,
Plusieurs tests cucumber sont aussi présent (inventory, player et referee)

<br>

### Prévision pour la prochaine itération
Nous planifions de :
Solidifier le serveur de statistiques, rajouter un second bot basique et lancer une partie avec deux bots différents, 
rajouter des plateaux de merveilles, permettre d'utiliser des récompenses à choix (carte où on peut choisir à chaque tour une ressource différente),
finir l'implémentation du chaînage.

Rendre une version stable en prévision du TER

Augmenter la couverture des tests, mock la classe Game pour la tester.