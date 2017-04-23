PaniniCMS
-------------
<p align="center">
<img src="https://vignette3.wikia.nocookie.net/chowder/images/5/55/PaniniCookieChowder.png"></img>
<p align="center">
<a href="https://circleci.com/gh/PaniniCMS/Panini"><img src="https://circleci.com/gh/PaniniCMS/Panini.svg?style=shield"></img></a>
</p>
</p>

# This is currently in-development!

## So you probably don't want to use it unless if you want to get your hands dirty!

### After all, I made this for my own personal use, but hey! If you want a cool simple & nice blogging CMS in Java, hey! You could use PaniniCMS!

PaniniCMS (or "Panini") is a simple blogging CMS made in Java, using [Jooby](http://jooby.org/) + [MongoDB](https://www.mongodb.com/) + [Pebble Template Engine](http://www.mitchellbosecke.com/pebble/home), PaniniCMS was created due to the lack of a good and **customizable** blogging CMS. (yes, there is a bunch of good blogging CMS like Wordpress, *insert your favourite blogging CMS here*, etc... but none are easy to customize)... also all of them are made in PHP and I don't like PHP.

PaniniCMS also has a plugin API, so you can create your own plugins for PaniniCMS so you don't need to get your hands too dirty by editing PaniniCMS's source code! (if you need to add plugins like page view count or custom dynamic pages, using plugins is your way to go!), here's an example plugin to get you started: https://github.com/PaniniCMS/PaniniExamplePlugin

# Installation
0. Install MongoDB and Java 8 if you don't have it already.
1. Download PaniniCMS from [CircleCI](https://circleci.com/gh/PaniniCMS/Panini) (you need to login @ CircleCI before downloading the artifacts!) or compile it from the source by using Maven
2. Start PaniniCMS by using `java -Xmx128M -Xms128M -jar Panini.jar -f YourFrontendFolder -p YourPaniniCMSPort -w YourWebsiteUrlWithAnSlashAtTheEnd -m YourMongoDBDatabaseName
`
3. Have fun!

(Tip: Use Apache or nginx to redirect your users from port 80 to your PaniniCMS port!)
