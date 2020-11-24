# WhatWebFX_Proiektua
Informazio Sistemen Analisia eta Diseinua ikasgaiko proiektua. Helburua zein teknologia erabiltzen du webgune honek azpitik? galderari eratzutea da.

MongoDB dependencies installation:

sudo apt install gem

sudo apt install ruby-dev

sudo gem install json

sudo gem install rchardet

sudo gem install mongo

Aurkitu non dauden my-plugins eta charset.db

sudo find / -name "charset.rb" 2> /dev/null

sudo find / -iname "my-plugins" 2> /dev/null

orain agertu diren lekuetan komandoa exekutatu

sudo cp /usr/share/whatweb/plugins-disabled/charset.rb /usr/share/whatweb/my-plugins

Orain mongoDB dependentziak exekutatu daitezke arazo gabe

