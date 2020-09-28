#  Springular-Framework-Business-Logic

C'est une application spring boot qui contient le logique métier de notre générateur.

## Développement

Avant de pouvoir déployer cette application sur votre machine vous devez avoir les outils suivants installés en local:

  * JDK 1.8 +
  * JHipster

 Pour pouvoir lancer le projet suivez les étapes suivantes :
 1. Dans un terminal dans la racine du projet lancez la commande suivante:

	> mvnw spring-boot:run
  
      
##Properties
Il faut modifier le fichier application.properties :

* path.generated-project : Path où le nouveau projet va être generé.
* app.path : Path où le projet généré avec JHipster va être placé.
    il faut creer le dossier **testjdl** et mettre son path dans la propriété app.path.exemple app.path=/home/debian/Bureau/testjdl/ .
* file.generate.path : Path du fichier qui va être généré et utilisé par JHipster pour la génération du projet .
* variable.environment :  il faut lancher la commande **echo $PATH** sur un terminal et coller la valeur de l'output dans variable.environment.

NB: Si vous avez utiliser YARN pour installer JHipster il faut lancer la commande **export PATH="$PATH:`yarn global bin`:$HOME/.config/yarn/global/node_modules/.bin"** sur un terminal. 

