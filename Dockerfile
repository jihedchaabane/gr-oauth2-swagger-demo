FROM openjdk:21

############################ Définir des arguments pour le nom, la version et le port
ARG MODULE_NAME
ARG JAR_FILE
ARG JAR_VERSION
ARG PORT

############################ Définir le répertoire de travail
WORKDIR /var/lib/jenkins/workspace/jars
############################ # Copier le fichier JAR en utilisant les arguments
#COPY ${JAR_FILE} ${MODULE_NAME}-${JAR_VERSION}.jar
COPY ${JAR_FILE} app.jar
############################ Exposer le port dynamique
EXPOSE ${PORT}
############################ Définir la commande d'entrée avec le nom et la version du JAR
#ENTRYPOINT ["java", "-jar", "${MODULE_NAME}-${JAR_VERSION}.jar"] ==> did not work : docker exec does not handle VARIABLE SUBSTITUTION.
# OR use the shell form, which runs the command in a shell and supports VARIABLE SUBSTITUTION : without []:
#ENTRYPOINT java -jar ${MODULE_NAME}-${JAR_VERSION}.jar
# Use exec Form with Shell for VARIABLE SUBSTITUTION.
#ENTRYPOINT ["/bin/sh", "-c", "java -jar ${MODULE_NAME}-${JAR_VERSION}.jar"]

ENTRYPOINT ["java", "-jar", "app.jar"]