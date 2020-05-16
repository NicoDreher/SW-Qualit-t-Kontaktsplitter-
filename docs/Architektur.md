# Architektur der Anwendung
## Allgemeine Architektur
Die Darstellung der Anwendung und deren Logik sind nach dem MVVM-Entwurfsmuster realisiert. Das MVVM-Entwurfsmuster steht für „Model – View – View-Model“ und sieht die folgende Architektur vor:

![MVVM-Modell](images/MVVM.png "MVVM-Modell")

Die erste Komponente des MVVM ist die View. Diese enthält ausschließlich Code zur Darstellung der Anwendung und ist im Rahmen dieses Projekts in Form von fxml-Dateien im Maven-Resources-Verzeichnis zu finden. Diese View wird von den View-Models bedient, die die Datenaufbereitung durchführen, die durch die View angeregten Operationen registrieren und Validierungen der vom Nutzer eingegebenen Daten übernehmen. Diese greifen auf die Model-Schicht zu, die daraufhin den Kern der Geschäftslogik abbilden, die sowohl die Datenverarbeitung als auch -haltung übernimmt. Daher sind die ViewModels in diesem Projekt im Paket „ui“ zu finden, während sich die Anwendungslogik und -daten über die anderen Packages erstrecken.

## Aufbau des Projekts
Der Code in dieser entwickelten Anwendung unterteilt sich gemäß dem MVVM Muster in eine Darstellung (= View) im Verzeichnis "resources" der Apache-Maven-Ordnerstruktur, den View Models im Package "ui" und der Geschäftslogik und den Datenstrukturen (= Model) in den Packages "models", "parser" und "persistence".

Die UI dieses Projekts ist in JavaFX realisiert. Die hierzu entwickelten fxml-Definitionen der Benutzeroberfläche, die im Rahmen dieses Projekts die Funktion der View im MVVM-Muster übernehmen, sind im Verzeichnis "resources" der Maven-Projektstruktur zu finden. Ebenfalls in diesem Verzeichnis finden sich eine Definition internationaler Vornamen und deren Geschlecht im Unterverzeichnis "names", sowie ein CSS-Dokument zur Formattierung der Darstellung im Unterverzeichns "styles".

Das Package "ui" hingegen enthält die vom MVVM-Entwurfsmuster verlangten View Models für die jeweilige View. Des weiteren findet sich hier die Klasse "Startup", die zum Starten der Anwendung und Laden der Hauptansicht dient. Im Subpackage "components" befinden sich mehrfach verwendete Komponenten der Benutzeroberfläche.

Im Package "persistence" wiederum finden sich die nötigen Schnittstellen und Datenstruktur, um die vom Benutzer konfigurierten Titel und Muster der Anreden zu persistieren. Hierzu wurden eine Klasse "ConfigFile" und "Configuration" definiert, die die Definition einer Konfigurationsdatei und Operationen auf diese, bzw. Zugriffe auf Konfigurationen erlauben. 

Das Package "parser" enthält die Klasse "InputParser". Diese ist das Kernelement der Geschäftslogik der Anwendung. Sie dient dazu, vom Benutzer eingegebene Anreden zu erkennen und unter Verwendung der vom Benutzer definierten Konfiguration, eine Briefanrede aus den extrahierten Informationen zu bilden.

Die für die Funktionalität der Anwendung nötigen Datenmodelle finden sich im Package models. Diese umfassen die Definition eines Kontakts in der Klasse "Contact", einer Konfiguration zur Verarbeitung einer Anrede in der Klasse "ContactPattern", sowie Klassen für die verfügbaren Geschlechter ("Genders") und Titel einer Person ("Title"). 

## Persistenz-Schicht
Zur Speicherung der Konfiguration der in der Anwendung ausgewählten Konfigurationen wird im Verzeichnis des aktuell angemeldeten Benutzers ein Verzeichnis „Kontaktsplitter-Kings“ angelegt und die Konfiguration darin gespeichert. Diese wird im JSON-Format abgelegt. Ist keine Konfiguration vorhanden, so wird eine Standard-Konfiguration erzeugt.