# Architektur der Anwendung
## Allgemeine Architektur
Der Code in dieser entwickelten Anwendung unterteilt sich in eine Darstellung im Java Package „ui“, einen Parser im Package „parser“, Datenstrukturen der in dieser Anwendung verarbeiteten Daten im Package „model“ und Persistenz-Schicht und -Logik im Package „persistence“.

Die Darstellung der Anwendung und deren Logik sind nach dem MVVM-Entwurfsmuster realisiert. Das MVVM-Entwurfsmuster steht für „Model – View – View-Model“ und sieht die folgende Architektur vor:

![MVVM-Modell](https://git.nico-dreher.de/University/sw-quality/splitter/raw/master/docs/images/MVVM.png?inline=false "MVVM-Modell")

## Persistenz-Schicht
Zur Speicherung der Konfiguration der in der Anwendung ausgewählten Konfigurationen wird im Verzeichnis des aktuell angemeldeten Benutzers ein Verzeichnis „Kontaktsplitter-Kings“ angelegt und die Konfiguration darin gespeichert. Diese wird im JSON-Format abgelegt. Ist keine Konfiguration vorhanden, so wird eine Standard-Konfiguration erzeugt.