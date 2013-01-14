# BombGameSE

## Purpose of the project

This project was part of the subject "Software Engineering" at the HTWG Konstanz. The exercise should teach the students
the benefits of agile softwaredevelopement and the usage of patterns. The goal of this project was to develope a simple
desktop game in a group of 2, while using the model-view-controller pattern, J-Unit tests and as much design patterns as
possible (reasonable scope). We used a Jenkins server for continuos integration, which built the projects with maven and
put the results on a Sonar server to examine the changed features.
Overall the students should get in touch with agile developement tools and experience the agile methods on their own.

## Specifications

### Tools
* game written in **Java**
* use **Eclipse** as IDE
* use **GIT/EGIT** as sourcecode management system
* use a continoues integration tool ( **Jenkins** )
* use **Sonar** as quality management platform
* use **J-Unit** for testing

### Final Qualities
The game should...
* provide a model-view-controller structure
* have a code coverage of 100% (except GUI / TUI)
* be able to run GUI and TUI simultaneously
* provide 0% code duplication
* provide 0% tangle index
* use interfaces and components

## Developement

### Beginnig

The developement started in mid-october and in this early period the team did not know much about design patterns, usage
of interfaces and dependency injection. We only knew the model-view-controller pattern and were ambitious to create our
own game. This fact leaded to many redesigns in the following developemnet stages(see also **Redesigns**).
The team discussed the fundamental structure of the software, defined the main components and classes and their functions
and created a simple UML diagram.

[UML]

## Idea

The idea was to create a Bombermanesque game without destructable boxes. However the field should not be like the standard
Bomberman field, but be like a maze and random generated. It should also provide an AI to provide opponents.

[STANDARD BM FELD]

### Early developement

In this period the team mostly worked on the controller layer. This layer's most important class was the **GameHandler**
,which did (at that time) most of the logics like moving objects, letting **Bombs** explode and check if any **Man** was
hit. It also managed the objects on the game field in a two dimensional array and the lists of bombs, men and explosions.

### TUI

The next step after the logics worked, was to create a Textual User Interface (TUI) to test game in action. Its purpose
was to provide a simple interface for testing the game logics even in bigger scopes. 
This period also covers the first implementation of a **Player** class. This class reacted on Scanner input and referenced
a **Man-Object** to change its direction or tell it to place a bomb. The Player-class only changed states of the Man-object,
the actual movement was done by the GameHandler.

Direction change by the Player of its referenced Man-object ( **man** ):
```java
if (c == 'w') {
  	man.setDirection(Man.UP);
} else if (c == 's') {
		man.setDirection(Man.DOWN);
} else if (c == 'd') {
		man.setDirection(Man.RIGHT);
} else if (c == 'a') {
		man.setDirection(Man.LEFT);
} else if (c == 'j') {
		man.setPlaceBomb(true);	
		man.setDirection(Man.NO_DIR);
} else {
		man.setDirection(Man.NO_DIR);
}
```

Movement done by the GameHandler:
```java
switch(man.getDirection()) {
  	
		case Man.NO_DIR:
      //no movement
    case Man.UP:
      //move if possible
    case Man.DOWN:
      //move if possible
    case Man.RIGHT:
      //move if possible
    case Man.LEFT:
```

The TUI draws the field on the console. The symbol's meanings are the following:
* '#' is a Wall
* '-' is a free space
* 'M' is a Man
* 'B' is a Bomb
* 'x' is an Explosion

[TUI BILD]


The developement also leaded to our first implementation of a **Design Pattern**: the Observer Pattern. The TUI should
only redraw the field, if anything has changed. So the TUI got the role of the **observer** and the GameHandler served as
**observable**. The GameHandler only notifies the TUI, if anything has changed to redraw the field.

### Redesigns

### GUI

## Final Product

## How to install

## Open Issues
* the game does not catch highscores
* the game does not provide any soundeffects
* the AI is not that intelligent
* the movement of the objects could be smoother
