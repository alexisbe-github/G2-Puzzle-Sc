# Lance Maven pour générer le fichier WAR
cd "C:/Users/Administrateur/Desktop/L3/Java/Projet/G2-Puzzle-Sc-2023"
mvn package -DskipTests

$cheminWarGenere = "C:/Users/Administrateur/Desktop/L3/Java/Projet/G2-Puzzle-Sc-2023/target/taquin.war"
$cheminWebapps = "C:/Users/Administrateur/Downloads/Apache Tomcat/apache-tomcat-10.0.23/webapps"
$cheminWarExistant = Join-Path $cheminWebapps "taquin.war"

# Si le fichier WAR est généré, le déplace dans le répertoire Tomcat webapps en écrasant le fichier existant
if (Test-Path $cheminWarGenere) {
    Move-Item -Path $cheminWarGenere -Destination $cheminWarExistant -Force
    Write-Host "Le fichier WAR a bien été créé."
} else {
    Write-Host "Le fichier WAR n'a pas pu être généré dans le délai imparti."
}

# Arrête et redémarre Tomcat
cd "C:\Users\Administrateur\Downloads\Apache Tomcat\apache-tomcat-10.0.23\bin"
& .\shutdown.bat
& .\startup.bat

Write-Host "Le serveur a été redémarré avec succès."

cd "C:\Users\Administrateur\Downloads"
