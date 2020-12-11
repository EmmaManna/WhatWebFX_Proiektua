# WhatWebFX_Proiektua
Informazio Sistemen Analisia eta Diseinua ikasgaiko proiektua. Helburua zein teknologia erabiltzen du webgune honek azpitik? galderari eratzutea da.

##MongoDB dependencies installation:
```bash
sudo apt install gem
sudo apt install ruby-dev
sudo gem install json
sudo gem install rchardet
sudo gem install mongo
```

Ondoren aurkitu non dauden _my-plugins_(karpeta) eta **charset.db**:
```bash
sudo find / -name "charset.rb" 2> /dev/null
sudo find / -iname "my-plugins" 2> /dev/null
```


Orain agertu diren lekuetan komandoa exekutatu, nire kasuan:

```bash
sudo cp /usr/share/whatweb/plugins-disabled/charset.rb /usr/share/whatweb/my-plugins
```

Orain mongoDB dependentziak exekutatu daitezke arazo gabe

[Dokumentazioa](https://segurtasunaduxon.xyz) euskaraz eginda ikusi daiteke.
Bestalde, [PowerPointa](https://www.reddit.com/r/orslokx/comments/kb67tf/efe_vol_2/) hemen aurkitu daiteke.

UI Developed by:
[@Duxon900](https://github.com/Duxon900)
[@EmmaManna](https://github.com/EmmaManna)
[@JonGondra](https://github.com/JonGondra)
