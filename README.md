# NastoPhotos

Cette application permet de recuperer toutes les photos sur votre NAS monter via un point de montage, recupere
la geolocalisation de chaque photo et les insere dans la table Photos Dynamo DB

# Comment demarrer

Creer un lecteur reseau Z:\ qui pointe vers la racine de l'emplacement de toutes vos photos.
Le programme va ainsi recuperer toutes les photos, puis filtrer les photos dont la date de capture
a pu etre determinee et disposant de coordonnes gps.

