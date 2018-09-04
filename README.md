# BlueBot - A Java-written Discord bot

<p align="center" >
  <img src="http://i.imgur.com/6ivzOgT.png" alt="BlueBot"/>
  <img height="164" src="https://i.imgur.com/IbZUkkh.png" alt="BlueBot"/><br>
   <b>Join the support server :</b>
</p>

<p align=center>
  <a  href="https://discord.gg/rSekkJv">
    <img src="https://discordapp.com/api/guilds/268853008455041025/widget.png?style=banner2">
  </a><br>
</p>
<p align=center>
You can add the bot to your server by clicking on
<a href="https://discordapp.com/oauth2/authorize?client_id=268420199370194944&scope=bot&permissions=-1">this link</a><br><br>
</p>


You can also contact me on **Steam** : [<img src="https://img.shields.io/badge/Steam-Blue-blue.svg">](http://steamcommunity.com/profiles/76561198206490817)

The **API** can be found [here](https://github.com/DV8FromTheWorld/JDA).
However, the required dependancies for the bot are included in this repository.

**BlueBot** will provide useful features like :
* Moderation & utility features
* Ability to enable & disable features
* Owner-only commands
* Extensible & customizable soundboard
* CleverBot integration (currently disabled)
* GIFs search
* Twitch alerts
* Command prefix customization
* Quick reactions, jokes & other stuff
* Cyanide & Hapiness comics
<!--* Custom memes (soon)-->
* More to come !

## Getting started

<!--If you want a .exe (or something like that), go [here](https://github.com/thibautbessone/DiscordBlueBotReleases) and download the latest version for all the features. Simple instructions are provided in order to make the bot to work.-->

To edit the code, just follow these steps : 
* Create your own app [here](https://discordapp.com/login?redirect_to=/developers/applications/me).
* **Clone the project** into your favorite IDE using GitHub's integration (if you're not downloading a release). 
* On your application page, get your application **token**
* Open **config.blue**
* Replace **YOURTOKENHERE** by your application token
* Replace **YOURIDHERE** by your ID
* Create a directory named soundboard in the bot's root folder. You will place your .mp3 files here.


## Interface

* Double click anywhere on the background to minimize
* Click anywhere on the background to drag the window
* Start buttons launches the bot, Stop button stops it
* Edit button open the config file in your default text editor
* Quit button stops the bot (if running) and closes the window

## Commands

BlueBot provides a lot of commands. Use ```!help``` to display the complete list of them.

By typing ```help``` after each command, you'll get information on how to use the command. 

**Example :** by typing ```!ping help``` you'll get the following response : 
```
The command ping pings the bot (to check if it's online). Usage : !ping
```

## Hosting the bot

You can host your own instance of the bot. To do so, head over to the [Releases](https://github.com/thibautbessone/DiscordBlueBot/releases) page and download the latest one.
To run BlueBot, you'll need Java 8 and OpenJFX. After installing them (Google is your friend, it depends on your OS), you'll be ready to go.

If you are using the bot on several servers, in order to have a dedicated soundboard per server, you'll have to :
* Enable Discord Developer mode (in the app settings, go to Appearance -> Advanced -> Toggle Developer mode on), then right click on your server and click on ```Copy ID```.
* Create a folder named like the server ID (for example, if one of the server has ```264445054966991498``` as ID, you want to name your folder ```264445054966991498```)
* Drop your .mp3 files inside this folder. These sounds will only be available on this particular server.

To toggle between the general soundboard and the dedicated one, remember to use the ```!enable``` and ```!disable``` commands.

## Running BlueBot on a headless device (VPS, Raspberry ...)

Since BlueBot is using JavaFX for the GUI, you'll need to install an additional library to run the app without a display.
The OpenJFX Monocle library can be found [here](https://drive.google.com/uc?id=1U4LGDiNYRvnKDvBPiCt65VKlChBtxQsJ&export=download) (you can find it on the web, but this is the version I'm using on my server).
* Install Java 8 (ex. Debian : ```sudo apt-get install java-8-oracle```)
* Install OpenJFX (ex Debian : ```sudo apt-get install openjfx```)
* Place the downloaded .jar in the ```${JAVA_HOME}/jre/lib/ext folder (for me it's``` ```/usr/lib/jvm/java-8-oracle/jre/lib/ext``` with Debian 8)
* Run the bot with ```java -jar -Xmx512m -Dtestfx.robot=glass -Dglass.platform=Monocle -Dmonocle.platform=Headless -Dprism.order=sw BlueBot.jar cmd```

## License

This project is released under the [MIT License](https://github.com/thibautbessone/DiscordBlueBot/blob/master/LICENSE).
