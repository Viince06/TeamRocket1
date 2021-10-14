# Les Design Patterns utilisés

Ci-dessous la liste des [patrons de conception](https://fr.wikipedia.org/wiki/Patron_de_conception) que nous implémenterons (ou du moins nous essaierons).



### Facade
Nous allons créer une façade qui calculera les points des joueurs de manière cachée.
La façade appellera des méthodes calculant les points en fonction des golds, des military points, etc...
et il nous suffira d'interroger la façade pour nous renvoyer le résultat global.

<br>

### Factory
Lorsque nous aurons plusieurs bots différents, il faudra utiliser une Factory permettant de renvoyer un objet Player en fonction de la difficulté du bot demandée.

<br>

### Stategy
Nous pensons que le fait d'avoir plusieurs bots ayant des stratégies de jeu différentes et implémentant la même interface
répondra au pattern Strategy.

<br>

### Proxy
Nous réfléchissons actuellement à l'utilisation potentielle du pattern Proxy pour l'inventaire du joueur.
Ainsi nous donnerions le proxy au Player mais l'inventaire lui resterais inaccessible.
