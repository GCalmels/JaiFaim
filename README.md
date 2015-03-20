# Projet JaiFaim pour le cours IHM/DevMobile pour Télécom Saint-Etienne

L'application Android doit permettre de créer des recettes de cuisine et de pouvoir visualiser celles
d'autres internautes. Le système derrière cette app est l'api de Github et plus particulièrement les gists qui ont permis de faire une petite base de données pour les recettes

## Installation :

1. Créer un projet Android Studio ayant pour nom "JaiFaim"
2. Copier les fichiers du git dans ce nouveau projet en remplaçant tous les fichier existants
3. Dans votre interface GitHub Gist, créer un Gist comme ceci

```json
{
    "title":"liste des recettes",
    "organisation":"JaiFaim",
    "recipeList":
        []
}
```

ATTENTION : bien nommer le fichier de ce gist  "recipe_list_gist"

4. A la ligne 42 du fichier GistController.java; changer l'id du Gist présent par celui que vous venez de créer. La ligne à changer est la suivante :

```java
private final static String ID_MAIN_GIST = "58d7dde389d53b4cef9c";
```





Emulateur utilisé : Genymotion

## Dépendances :

- org.eclipse.egit.github.core : l'API de Github pour Java

- de.greenrobot:eventbus : Simplifier la communication entre Activité, Fragment, Service...

- com.google.code.gson : Passer d'un objet en Json et vice versa

- com.koushikdutta.ion : Chargement asynchrone de données

- org.roboguice:roboguice : Injection de dépendance (simplifie les relations entre ui et code)

- com.lorentzos.swipecards : Affichage de belles cartes pour les étapes des recettes

- com.github.castorflex.smoothprogressbar : Barre de progression originale


## Améliorations :

- Supprimer des recettes
- Rechercher des chefs
- Ajouter des actions sur chaque bouton dans le détail d'une recette
- Améliorer l'affichage des étapes d'une recette
- Ajouter un Gist commun à tous les chefs pour que tout le monde puisse voir toutes les recettes publiques
- Forker une recette
- Liker une recette
- Modifier une recette
- Ajouter un commentaire à une recette
- Se souvenir des identifiants de connexion d'un chef
- ...
