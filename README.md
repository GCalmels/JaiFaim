Projet JaiFaim pour le cours IHM/DevMobile pour Télécom Saint-Etienne

L'application Android doit permettre de créer des recettes de cuisine et de pouvoir visualiser celles 
d'autres internautes. Le système derrière cette app est l'api de Github et plus particulièrement les gists qui ont permis de faire une petite base de données pour les recettes

Installation :

Il suffit de télécharger les sources puis de synchroniser gradle 

Emulateur utilisé : Genymotion

Dépendances :

org.eclipse.egit.github.core : l'API de Github pour Java

de.greenrobot:eventbus : Simplifier la communication entre Activité, Fragment, Service...

com.google.code.gson : Passer d'un objet en Json et vice versa

com.koushikdutta.ion : Chargement asynchrone de données

org.roboguice:roboguice : Injection de dépendance (simplifie les relations entre ui et code)

com.lorentzos.swipecards : Affichage de belles cartes pour les étapes des recettes

com.github.castorflex.smoothprogressbar : Barre de progression originale
