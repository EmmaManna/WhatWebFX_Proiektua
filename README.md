# WhatWebFX_Proiektua
Informazio Sistemen Analisia eta Diseinua ikasgaiko proiektua. Helburua zein teknologia erabiltzen du webgune honek azpitik? galderari eratzutea da.

## Aplikazioa erabiltzeko aurrebaldintzak

1. WhatWeb instalatuta izatea. Horretarako hurrengo estekan bilatu: https://github.com/urbanadventurer/WhatWeb
2. Windows sistema eragilean Ubuntu aplikazioa instalatu WhatWeb bertan erabili ahal izateko. Hurrengo estekan bilatu: https://docs.microsoft.com/es-es/windows/wsl/install-win10
3. Erabili nahi izatekotan MongoDB instalatu: https://www.mongodb.com/try/download/community

#Mongo instalazioa
Mongo instalatzea ez da erreza, beraz hemen agertzen dira guk jarraitutako pausuak
```bash
$ sudo apt-get install -y mongodb-org
$ sudo apt install mongodb-server-core -y
$ sudo apt install mongodb-clients -y
```

## MongoDB dependencies installation
```bash
$ sudo apt install gem
$ sudo apt install ruby-dev
$ sudo apt install make
$ sudo apt install build-essential

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

### Esteka interesgarriak

[Dokumentazioa](https://github.com/UPV-EHU-Bilbao/WhatWebVFX/blob/master/WhatWebFX_JonGondra_EmmaManna_JonQuintano.pdf) euskaraz eginda ikusi daiteke.
Bestalde, [PowerPointa](https://github.com/UPV-EHU-Bilbao/WhatWebVFX/blob/master/WhatWebFX.pptx) edo [Aurkezpena PDF](https://github.com/UPV-EHU-Bilbao/WhatWebVFX/blob/master/WhatWebFX.pdf) eran hemen aurkitu daiteke.


## UI Developed by

[![Website](https://img.shields.io/badge/Duxon900-github-green?style=flat-square)](https://github.com/Duxon900)
[![Website](https://img.shields.io/badge/EmmaManna-github-green?style=flat-square)](https://github.com/EmmaManna)
[![Website](https://img.shields.io/badge/JonGondra-github-green?style=flat-square)](https://github.com/JonGondra)