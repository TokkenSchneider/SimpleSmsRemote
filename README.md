# Simple sms remote 
Android app for remotely controlling a phone through sms messages.
Install the app on a device, which should be controlled, and send control commands from any messenger to it.

**compatible android versions:** 4.0.3 (Ice Cream Sandwich) and higher

![Logo](https://raw.githubusercontent.com/tranquvis/SimpleSmsRemote/master/.github/logo.png)

#### Will be available in Play Store soon!
**status:** under development

[download alpha](https://raw.githubusercontent.com/tranquvis/SimpleSmsRemote/master/app/app-release-latest.apk)

## Features
* specify which modules are accessable and which phones are granted
* start sms receiver after boot
* send multiple commands sith one message
* reply to sender phone with result message 
* possibility to show permanent notification with reciver status (required in android versions below 5.0 Lollipop)
* view log of sms receiver

### Modules
* Hotspot (enable/disable)
* Mobile Data Connection (enable/disable)

## Security
Granted phones are required to be set for each module, so not everyone can control the device.
However, if someone fakes his phone number he is able to use all enabled modules. (Note that this might be a complicated and illegal procedure. Moreover also common antitheft apps like avast trust the sender's phone number.

***
**License: [MIT](LICENSE)**

