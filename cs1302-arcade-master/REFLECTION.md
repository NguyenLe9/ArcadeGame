# Reflection

Add to this file to satisfy the "Reflection Updates" non-functional requirement
for this project. Please keep this document organized using Markdown. If you
click on this file in your team's GitHub repository website, then you will see
that the Markdown is transformed into nice looking HTML.

Here is a sample entry (delete this line; also: feel free to copy/paste/modify):

## TUES 2019-04-16 @ 11:55 PM EST

1. **DONE:** Downloaded the skeleton code.
   - Created a menu list window.
   - Made 2048 and SpaceInvaders classes.
   - SpaceInvaders:
	- added alien and player classes
	- made win/lose screen messages
	- displayed images
   - 2048:
	- I created a gridpane that have 4x4 size
	- I created a random variable that everytime the game opens, there are 2 number twos pop up in random places.
	- I create a score to keep track of player's score
	- I use css-theme to match the colors in the original game with the ones in my game
2. **TODO:**
   - SpaceInvaders:
	- need to learn how to move images
	- figure out how to make gun/bomb shots and "destry" images
   - 2048:
	- I'm thinking about switching from gridpane to canvas so that my images will slide
	- Figure out how to keep track of the score when 2 images slide into each other
	- Figure out how to create a thread.

3. **PROB:** We need to rework the start method so it isn't so long.
   - We don't know how to slide the images yet.
   
## TUES 2019-04-23 @ 11:55 PM EST

1. **DONE:** Downloaded the skeleton code.
   - SpaceInvaders:
	- Switched to canvas
	- got player to move with keys
	- got aliens to move in set pattern
	- change win/lose methods
	- added shoot methods
	- added invasion method
	
   - 2048:
	- I switched to canvas!
	- This week I finally figured out how to slide the images to the left using keyframe and timeline.
	- I figured out how to pop a randome 2 after the images slide using sequential transition.
	- I did the first part of the moveRightHelper method.
	- I finally create thread for every method
	
2. **TODO:**	
   - SpaceInvaders:
	- need to figure out how to display shooting
	- need to destory aliens when shot
	- need to display players' 3 lives
	- need to add barriers 
   - 2048:
	- I need to think of a way so that I can check the number in a certain rectangle to see if they can add up.
	- I need to think of a way to slide Images right, up and down with proper collision.

3. **PROB:**
	- To 2048, my problem is that when I try to move the images to the right, they override/cover in layers.
	- When i hit the spacebar, the shooting method is being called but not displaying the bullet


## TUES 2019-04-30 @ 11:55 PM EST
1. **DONE:** Downloaded the skeleton code.
   - SpaceInvaders:
	- Fixed timelines and threads
	- aliens move in set pattern
	- player and aliens can destroy each other
	- display player's lifes
   - 2048:
	- I switched to Group and finally used Translate Transition to move images instead of Canvas.
	- I figured out how to combine the images together when they collide. For ex: a 2 next to a 2 is a 4.
	- I took out some threads and so far my code has only 1 extra thread besides the main thread.
2. **TODO:**
    - SpaceInvaders:
	- need to display bullet
	- add score calculator
	- display lose and win sceens
    - 2048:
	- Since I figured out the mechanism for the keyCode left, I will have to do the same if players hit up, down and right.
	- I will have to make the score increase by the number resulted from collision.
    - Both:
	- javadoc, check style, attribution, do extra credits if possible.
3. **PROB:**
   - Had issues with threads but resolvd them.
   - To 2048, my problem is that there is one case that the image is covered by the blank square.

## TUES 2019-05-07 @ 3:30 PM EST
1. **DONE:**
   - SpaceInvaders:
     - displayed bullets
     - add scoring
     - display lose screen
     - levelUp
   - 2048:
     - fixed scoring
     - fixed slide issues
   - EXTRA CREDIT #1
     - added intro with animated spaceship and moving labels
   - EXTRA CREDIT #2
     - added highscore table button to the menu screen

2. **PROB:**
   - had some troube with lagging but fixed
   - time crunch to finish final touches

