# Projet Seven Wonders

Les règles du jeu sont accessibles [ici](https://www.jeuxavolonte.asso.fr/regles/7_wonders.pdf)

Le travail prévu pour chacune des itérations peut être trouvé [ici](documentation/Iterations.md)
<br>

## Lancement du jeu
Le jeu peut être exécuté en tapant la commande `mvn exec:java`


## Sonarqube
link: `http://sonarqube.7wonders.lucy-chan.fr/dashboard?id=fr.unice%3ASeven-Wonders` <br>
user: `MasterInfo` <br>
mdp : `Master2020` <br>

# Projet TER

## Règles conservées

Les règles qui seront utilisées par les IA :<br>
La rotation des cartes, les récompenses simples, les récompenses multiples ne sont choisies qu'une seule fois à l'acquisition de la carte,
le sacrifice des cartes, acheter une carte ou une étape de merveille, chaîner des cartes, acheter des ressources à ses voisins.

Les cartes implémentées : Les cartes scientifiques, les cartes bleues, les cartes militaires, les cartes marron et grises.
Les merveilles : il y a 3 merveilles implémentées, les récompenses sur ces merveilles sont celles qui ont été implémentées.

Ce qui n'est pas ajouté : Les cartes dorées et violettes, certaines merveilles, les cartes qui ont des récompenses non implémentées
ne sont pas dans notre jeu.

## IA garantie

Nous allons faire une IA qui utilisera des stratégies composites.
Une IA pourra choisir dans une liste de stratégies pour chaque âge, selon le contexte elle alternera entre les stratégies.

Une IA pourra toujours faire les stratégies Chaînage et Bloquer le joueur après elle.
Chaque âge aura après ses stratégies spécifiques :
- À l'âge 1 : Ressources ou autre cartes
    L'IA cherchera en priorité à acheter des ressources, une fois qu'elle aura atteint un certain quota de ressources,
    elle prendra les autres types de carte. Si elle ne peut pas prendre de ressources, elle choisira alors les autres
    types de carte.
- À l'âge 2 : On choisira entre deux types de carte en priorité.
- À l'âge 3 : On choisira entre deux types de carte en priorité.
Les types de carte à choisir seront étudiés pour trouver les combinaisons qui rapportent le plus de points.

## IA Ambitieuse

Nous allons reprendre l'IA garantie, et cette fois-ci elle ne sera plus limitée à 2 priorités de types de carte,
elle aura la liberté de prendre tous les types de carte mais selon les choix qu'elle fera elle adaptera sa priorité à un 
ou deux types de carte en particulier.<br>
Par exemple si elle est forcée de prendre 2 cartes vertes rapidement, alors les cartes vertes deviendront une de ses priorités.

Une fois cette IA faite, nous allons l'optimiser à travers un algorithme de Monte Carlo
