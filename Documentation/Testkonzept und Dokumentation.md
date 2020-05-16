# Testkonzept und Dokumentation

 

### UI-Test: 

Die Oberflächentests sollen ungewünschte Verhaltensweisen bei der Bedienung der Oberfläche aufdecken. Hierzu wurde die Funktionalität der einzelnen UI-Elemente getestet und, ob diese zum gewünschten Ergebnis führen (z.B. ein Fenster richtig öffnen). Hierbei werden auch fehlerhafte Eingaben überprüft.

Die Kernfunktionialität der Oberfläche wird über parametrisierte Tests gestaltet. Hierbei soll zum einen geprüft werden, ob die Felder zur manuellen Eingabe mit den richtigen Daten gefüllt wird, wenn eine Eingabe getätigt wird. Hierzu wurden folgende Testfälle definiert, welche alle zum richtigen Ergebnis führen. (Testfälle in Anhang 1)

Neben diesen wurde auch die richtige Generierung der Briefanrede sowie das Anzeigen auf der Benutzeroberfläche gezeigt. In Anhang 2 werden die Testdaten gezeigt, welche alle zum erwarteten Ergebnis führen. Dieser Test bekommen als Parameter den Eingabestring und das erwartete Ergebnis und werden dann ausgewertet.

Testfälle, welche zu einem negativen Ergebnis führen, werden ausgeschlossen, da das Backend immer eine gültige Formatierung versucht.

 

### Unit Test:

 



### Anhang 1

| **Eingabe**                                                  | **Vorname (erwartet)** | **Nachname (erwartet)**          | **Geschlecht** | **Titel (erwartet, mit ; getrennt)**               | **Sprache**   |
| ------------------------------------------------------------ | ---------------------- | -------------------------------- | -------------- | -------------------------------------------------- | ------------- |
| Frau Sandra Berger                                           | "Sandra"               | "Berger"                         | "FEMALE"       |                                                    | "Deutsch"     |
| Herr Dr. Sandro Gutmensch                                    | "Sandro"               | "Gutmensch"                      | "MALE"         | "Dr."                                              | "Deutsch"     |
| Professor Heinrich Freiherr vom Wald                         | "Heinrich"             | "Freiherr vom Wald"              | "MALE"         | "Professor"                                        | "Deutsch"     |
| Mrs. Doreen Faber                                            | "Doreen"               | "Faber"                          | "FEMALE"       |                                                    | "Englisch"    |
| Mme. Charlotte Noir                                          | "Charlotte"            | "Noir"                           | "FEMALE"       |                                                    | "Französisch" |
| Frau Prof. Dr. rer. nat. Maria von  Leuthäuser-Schnarrenberger | "Maria"                | "von Leuthäuser-Schnarrenberger" | "FEMALE"       | "Prof.; Dr. rer. nat"                              | "Deutsch"     |
| Herr Dipl. Ing. Max von Müller                               | "Max"                  | "von Müller"                     | "MALE"         | "Dipl. Ing."                                       | "Deutsch"     |
| Dr. Russwurm, Winfried                                       | "Winfried"             | "Russwurm"                       | "MALE"         | "Dr."                                              | "Deutsch"     |
| Herr Dr.-Ing. Dr. rer. nat. Dr. h.c.  mult. Paul Steffens    | "Paul"                 | "Steffens"                       | "MALE"         | "Dr.-Ing.; Dr. rer. nat.; Dr. h.c.  mult."         | "Deutsch"     |
| Herr Prof. Dr. rer. nat. Dr.-Ing. Dr.  h.c. mult. Fridolin Müller | "Fridolin"             | "Müller"                         | "MALE"         | "Prof.; Dr. rer. nat.; Dr.-Ing.; Dr. h.  c. mult." | "Deutsch"     |
| Señor Salvador Gonzales                                      | "Salvador"             | "Gonzales"                       | "MALE"         |                                                    | "Spanisch"    |
| Signora Marcella Paolini                                     | "Marcella"             | "Paolini"                        | "FEMALE"       |                                                    | "Italienisch" |
| Signora Dr. Vanessa Torez                                    | "Vanessa"              | "Torez"                          | "FEMALE"       | "Dr."                                              | "Italienisch" |
| M Alexandre Renault                                          | "Alexandre"            | "Renault"                        | "MALE"         |                                                    | "Französisch" |
| Mr. Ron Weasley                                              | "Ron"                  | "Weasley"                        | "MALE"         |                                                    | "Englisch"    |
| Mrs. Katy O'Shea                                             | "Katy"                 | "O'Shea"                         | "FEMALE"       |                                                    | "Englisch"    |
| Frau Dr. Caitlyn Jenner                                      | "Caitlyn"              | "Jenner"                         | "FEMALE"       | "Dr."                                              | "Deutsch"     |
| Señora Prof. Kim Torres                                      | "Kim"                  | "Torres"                         | "FEMALE"       | "Prof."                                            | "Spanisch"    |
| Sig. Dr. rer. nat. Dr.-Ing. Dr. h.c.  mult. Antonio Cipolla  | "Antonio"              | "Cipolla"                        | "MALE"         | "Dr.-Ing.; Dr. rer. nat.; Dr. h.c.  mult."         | "Italienisch" |
| Mr Prof. Tobias Rieper                                       | "Tobias"               | "Rieper"                         | "MALE"         | "Prof."                                            | "Englisch"    |
| Frau Anabelle Maria Friedrich                                | "Anabelle Maria"       | "Friedrich"                      | "FEMALE"       |                                                    | "Deutsch"     |
| Herr Tobias Raphael Meier Müller                             | "Tobias Raphael"       | "Meier-Müller"                   | "MALE"         |                                                    | "Deutsch"     |
| Herr Dr. Tobias Max Lukas Max Prinz von  Sachsen Anhalt      | "Tobias Max Lukas Max" | "Prinz von Sachsen-Anhalt"       | "MALE"         | "Dr."                                              | "Deutsch"     |

 



 ### Anhang 2

| Eingabe                                   | Generierte Briefanrede                                 |
| ----------------------------------------- | ------------------------------------------------------ |
| Frau Sandra  Berger                       | "Sehr geehrte Frau Sandra Berger"                      |
| Herr  Professor Winfried Schröder         | "Sehr geehrter Herr Professor Winfried  Schröder"      |
| Luca Meier                                | "Sehr geehrter Herr Luca Meier"                        |
| Herr Dr.  Dipl. Ing. Darius Nightrider    | "Sehr geehrter Herr Dr. Dipl. Ing. Darius  Nightrider" |
| M. Adam  Bernard                          | "Monsieur Adam Bernard"                                |
| Señor  Salvador Gonzales                  | "Estimada Señor Salvador Gonzales"                     |
| Mr. Dr.  Tobias Rieper                    | "Dear Mr Dr. Tobias Rieper"                            |
| Mrs. Doreen  Faber                        | "Dear Mrs Doreen Faber"                                |
| Señora Dr.  Dr. rer. nat. Kimberly Torres | "Estimada Señora Dr. rer. nat. Dr.  Kimberly Torres"   |
| Mme.  Charlotte Bernand                   | "Madame Charlotte Bernand"                             |

 