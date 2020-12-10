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


## run springular with docker 

* [Build image with jib plugin] Il faut ajouter cette prortion de code dans le fichier pom.xml :

	>           
                <plugin>
                    <groupId>com.google.cloud.tools</groupId>
                    <artifactId>jib-maven-plugin</artifactId>
                    <version>2.4.0</version>
                    <configuration>
                        <to>
                            <image>springular-buisness-logic</image>
                        </to>
                    </configuration>
                </plugin>


* Il faut lancer la commande suivante dans le terminal  :
	> ./mvnw clean compile jib:dockerBuild

* lancer docker-compose  :
 	> docker-compose up

* pour vérifier tu peux consulter le lien suivant  :
    http://localhost:9099/swagger-ui.html
