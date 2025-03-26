# API de Conversion de Devises
Cette API permet de convertir une somme d'argent d'une devise à une autre en utilisant
les taux de change récupérés dynamiquement depuis une API externe.

# Prérequis
Java 17, 21 (ou supérieur)
Maven (pour la gestion des dépendances)
PostgreSQL (si nécessaire pour la persistance des données)

# Installation
Cloner le dépôt :
git clone https://github.com/loickenmoe/API-Currency-Conversion
cd [NOM_DU_RÉPERTOIRE]

# Configurer l'API de taux de change :
Inscrivez-vous sur Exchangerate-API pour obtenir une clé API.
Ajoutez votre clé API dans le fichier application.properties :
- exchangerate.api.key=VOTRE_CLE_API
- exchangerate.api.base-url=https://v6.exchangerate-api.com/v6/

# Lancer l'application :
mvn spring-boot:run
L'API sera disponible sur http://localhost:9009.

# Utilisation de l'application :
Méthode
Endpoint
Description
POST

# /api/conversion
Convertir une devise en une autre

# Lien Swagger :
http://localhost:9009/swagger-ui/index.html

# Exemples de requêtes :
Convertir une somme (USD -> EUR) :

POST http://localhost:9009/api/conversion
{
"fromCurrency": "USD",
"toCurrency": "EUR",
"amount": 240
}

Réponse attendue :

{
"fromCurrency": "USD",
"toCurrency": "EUR",
"amount": 240.0,
"convertedAmount": 222.24,
"exchangeRate": 0.926
}

# Tester avec Swagger :

Ouvrir http://localhost:9009/swagger-ui/index.html et tester l'API directement.


