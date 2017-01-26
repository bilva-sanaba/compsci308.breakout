*General Design Overview*

The programs’ Main method creates an instance of the class GameSettings which stores the information on the gamestate that will be needed throughout the program. 

The GameSettings constructor utilizes a Layout Reader to read a text file storing level and brick data and create a matrix representation of this data which then is translated into an array of brick objects stored as a private variable in GameSettings. 

The Main method then modifies the GameSettings to setup the game with paddles, bricks, and balls, as well as create several different scenes stored in the GameSettings. 

Changing scenes comes through an event handler in main which uses a method from the scene currently being displayed. 

Once the main game scene is reached, the Timeline in GameSettings is updated by the step method in main. The step position updates ball positions, powerups, scores, text display, and determines if the next level or end screen should be reacher. In the step position methods from various classes, such as Ball, Brick, and Powerup are called. 

As for important features, ball speed updates are conducted by methods in either Brick or Paddle depending on which one is contacted. Powerups are generated through the makePowerup method in PowerupSettings, and Levels are determined through a text file read by the LayoutReader object. The text file is written in the form of lines either containing the string  "New Level" or (xLoc,yLoc,Strength) which determines a bricks location and strength. 

*Feature Additions: *

To add a new level, edit the text file Levels, enter the String "New Level" at the end and then in each subsequent line write (int xLoc, int yLoc, int BrickStrength) which will determine where a brick will be in the level and what strength it will have. 

In order to create new powerups, a new class must be created that extends PowerupSettings and implements Powerup. Then in PowerupSettings, the new class must be added as a possible powerup by if statement in the method makePowerup();

Additional screens can also be created by making new classes that extend screen. The scene created by the screen must be stored by GameSettings. Then other screens can be modified with methods that set stage to the newly created scene given certain inputs. 

*Design Choices: *

(Post Refactoring)

One major design choice was storing all needed gamestate data in the class GameSettings. This allows for all state dependent methods to receive information through a single object. Additionally, this allows for no need for public static variables which would be another way for state dependent variables to get and change gamestates. The use of the GameSettings class is preferable because it prevents methods away from those listed in the GameSettings class to change important game related variables. However, it may have been more beneficial to have multiple classes containing state dependent information since most methods used by other classes with a GameSettings parameter do not need more than a single encapsulated variable from the class. 

	Another important design choice was the use of various abstract extendable classes. These were important as there were many classes with very similar features but distinct enough to entail separate classes. In order to avoid duplicate code with these, extendable abstract classes were utilized (in particular with screens and powerups). There is no major downside to  this due to the classes being similar. If they were not similar and did not need many of the same methods, then the creation of an additional abstract class may have been unneccessary and confusing. 

*Assumptions for Project Functionality*

	One major decision that detract from the applicability of my projects code in other settings is due to the size and images. The field is an image and goals are calculated based on a fixed pixel length equivalent to the goals on the image. Due to this, if the game were to be played at different sizes, while some settings would work, such as paddle warping, the goal area would be inaccurate. Additionally, if the field image, or ball were changed, final values like LEFTPOST, RIGHTPOST, or BALLADJUSTMENT would have to  be changed. 

