# Overview

EmPoster is "Spring Boot"-ready email server mock. It allows you to store and view received emails.
Project is modular. You can take only what you need and customize it.

### Disclaimer:
This project is not SMTP server itself. EmPoster uses [SubEthaSMTP](https://github.com/davidmoten/subethasmtp) as SMTP server implementation.

### Status:
Project is early stage and under (lazy) development. Do not expect new features, active maintenance, swift security patches.

# Modules:
- core - minimal required module to run mock email server. By default, emails will be stored in-memory. No Spring integration whatsoever.
- spring - Spring Boot autoconfiguration. Spin ups core components and hooks into Spring Context lifecycle.    
- web - exposes REST API and Web UI to view emails. It does not work on its own, see starter.
- starter - spring + web 
- jpa - provides integration with JPA based backend. By default, uses h2 as storage.
- example - well... and example how to use all above.
