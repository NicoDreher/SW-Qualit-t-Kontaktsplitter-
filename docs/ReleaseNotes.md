# Release Notes

## Eingegebene Anreden werden in ihre Bestandteile aufgeteilt
Anreden, welche in das Eingabefeld eingegeben werden, werden in die Bestandteile aufgeteilt:
* Anrede
* Standardisierte Breifanrede
* Titel
* Geschlecht
* Vorname
* Nachname

Das Aufteilen wird während der Eingabe durchgeführt. 
Nach dem Eingeben einer Anrede, kann eine standartisierte Briefanrede durch den "Anrede generieren" Knopf generiert werden.
## Die Benutzeroberfläche wurde erweitert, um manuelle Verbesserungen der automatischen Zuweisung vorzunehmen.
Der Benutzer kann die automatische Erkennung zur Berechnung der Briefanrede über manuelle Felder überschreiben. Hierzu stehen alle Teile der Anrede als Felder oder Auswahllisten zur Verfügung.

## Titel sind über eine Menüoption konfigurierbar.
Über eine Option im Menü können neue Titel konfiguriert werden, welche im Programm gespeichert werden, um so eine wachsende Automatisierung des Systems zu erreichen.

## Muster zur automatischen Erkennung sind konfigurierbar. 
Neben der Option zur Konfiguration von Titeln, ist auch eine Konfiguration von Mustern möglich. Dadurch wird ermöglicht, dass das System neue Muster erkennt.

## Schnittstelle zur Überprüfung von Duplikaten zum CRM System wird angeboten (Nicht implementiert)
Eine Schnittstelle, an welche das CRM-System angebunden werden kann, ist nun bereitgestellt. Aktuell wird hierbei der Benutzer informiert, dass keine Verbindung zum CRM-System möglich ist.

## Automatische Erkennung des Geschlechts durch den Vornamen
Automatische Unterteilung der Namen in Vornamen und Nachnamen.
In der Anrede wird das Geschlecht anhand des Vornamens oder der Formulierung automatisch erkannt. Falls kein Geschlecht zugeordnet werden kann, wird kein Geschlecht ausgewählt. Das führt zu einer neutralen Anrede.