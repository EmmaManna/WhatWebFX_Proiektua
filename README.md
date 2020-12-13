# WhatWebFX_Proiektua
Informazio Sistemen Analisia eta Diseinua ikasgaiko proiektua. Helburua zein teknologia erabiltzen du webgune honek azpitik? galderari eratzutea da.

##Aplikazioa erabiltzeko aurrebaldintzak:
1. WhatWeb instalatuta izatea. Horretarako hurrengo estekan bilatu: https://github.com/urbanadventurer/WhatWeb
2. Windows sistema eragilean Ubuntu aplikazioa instalatu WhatWeb bertan erabili ahal izateko. Hurrengo estekan bilatu: https://docs.microsoft.com/es-es/windows/wsl/install-win10
3. Erabili nahi izatekotan MongoDB instalatu: https://www.mongodb.com/try/download/community

###MongoDB dependencies installation:
```shell
$ sudo apt install gem
$ sudo apt install ruby-dev
$ sudo gem install json
$ sudo gem install rchardet
$ sudo gem install mongo
```

Ondoren aurkitu non dauden _my-plugins_(karpeta) eta **charset.db**:
```shell
$ sudo find / -name "charset.rb" 2> /dev/null
$ sudo find / -iname "my-plugins" 2> /dev/null
```


Orain agertu diren lekuetan komandoa exekutatu, nire kasuan:

```shell
$ sudo cp /usr/share/whatweb/plugins-disabled/charset.rb /usr/share/whatweb/my-plugins
```

Orain mongoDB dependentziak exekutatu daitezke arazo gabe

###Esteka interesgarriak

[Dokumentazioa](https://segurtasunaduxon.xyz) euskaraz eginda ikusi daiteke.
Bestalde, [PowerPointa](https://www.reddit.com/r/orslokx/comments/kb67tf/efe_vol_2/) hemen aurkitu daiteke.

Gainera, [Windows exekutagarria]() eta [Linux exekutagarria]() eskura daude hemen.

##UI Developed by:

[![Website](https://img.shields.io/badge/Duxon900-github-green?style=flat-square)](https://github.com/Duxon900)
[![Website](https://img.shields.io/badge/EmmaManna-github-green?style=flat-square)](https://github.com/EmmaManna)
[![Website](https://img.shields.io/badge/JonGondra-github-green?style=flat-square)](https://github.com/JonGondra)