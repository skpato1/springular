# Springular [BACK]

C'est une application générée automatiquement par le générateur Springular.

## Développement

Avant de pouvoir déployer cette application sur votre machine vous devez avoir les outils suivants installés en local:

  * Un serveur de base de données MySQL.
  * JDK 1.8 +
### Packaging en jar
 Pour pouvoir lancer le projet suivez les étapes suivantes :
 1. Sous votre serveur MySQL créez une base de données nommée : **db_springular**
 2. modifiez le fichier **[application.properties](http://git.intranet.sifast.com/sifast-project/springular-framework-back/blob/master/springular-framework-web/src/main/resources/application.properties)** convenablement en saisissant votre utilisateur et mot de passe.
 3. Dans un terminal dans la racine du projet lancez la commande suivante:

	> mvnw spring-boot:run
 4. Pour tester le fonctionnement de l'application visitez : [swagger-ui.html](http://localhost:9090/springular-framework/swagger-ui.html#/)
 