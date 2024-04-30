
**Дипломный проект Rococo**
#### Cтруктура сервисов:
<img src="service.png" width="600">

#### 1. Установить docker (Если не установлен)

После установки и запуска docker daemon необходимо убедиться в работе команд docker, например `docker -v`:

```posh
Dmitriis-MacBook-Pro ~ % docker -v
Docker version 20.10.14, build a224086
```

#### 2. Спуллить контейнер postgres:15.1, zookeeper и kafka версии 7.3.2

```posh
docker pull postgres:15.1
docker pull confluentinc/cp-zookeeper:7.3.2
docker pull confluentinc/cp-kafka:7.3.2
```

#### 3. Создать volume для сохранения данных из БД в docker на вашем компьютере

```posh
docker volume create pgdata
```

#### 4. Запустить БД, zookeeper и kafka 3-мя последовательными командами:

Запустив скрипт

```posh
Dmitriis-MacBook-Pro  rococo % bash localenv.sh
```

#### 5. Установить одну из программ для визуальной работы с DBeaver

#### 6. Подключиться к БД postgres (host: localhost, port: 5432, user: postgres, pass: secret, database name: postgres) из PgAdmin и создать пустые БД микросервисов

```sql
create
    database "rococo-userdata" with owner postgres;
create
    database "rococo-artist" with owner postgres;
create
    database "rococo-museum" with owner postgres;
create
    database "rococo-painting" with owner postgres;
create
    database "rococo-auth" with owner postgres;
```

#### 7. Установить Java версии 17.

#### 8. Установить пакетый менеджер для сборки front-end npm

# Запуск Rococo локальное в IDE:

#### 1. Запуск фронтенда

```posh
Dmitriis-MacBook-Pro rococo % cd rococo-client
```
```

#### 2. Запустить фронтенд (сначала обновить зависимости)

Для *nix:

```posh
Dmitriis-MacBook-Pro rococo-client % npm i
Dmitriis-MacBook-Pro rococo-client % npm run build:dev
```

#### 3. Прописать run конфигурацию для всех сервисов rococo-* - Active profiles local

Для этого зайти в меню Run -> Edit Configurations -> выбрать main класс -> указать Active profiles: local
[Инструкция](https://stackoverflow.com/questions/39738901/how-do-i-activate-a-spring-boot-profile-when-running-from-intellij).

#### 4 Запустить сервис Rococo-auth c помощью gradle или командой Run в IDE:
- 

- Запустить сервис auth

```posh
Dmitriis-MacBook-Pro niffler % cd rococo-auth
Dmitriis-MacBook-Pro niffler-auth % gradle bootRun --args='--spring.profiles.active=local'
```

Или просто перейдя к main-классу приложения RococoAuthApplication выбрать run в IDEA (предварительно удостовериться что
выполнен предыдущий пункт)

#### 5  Запустить в любой последовательности другие сервисы

# Запуск Rococo в докере:

#### 1. Создать бесплатную учетную запись на https://hub.docker.com/ (если отсутствует)

#### 2. Создать в настройках своей учетной записи access_token

[Инструкция](https://docs.docker.com/docker-hub/access-tokens/).

#### 3. Выполнить docker login с созданным access_token (в инструкции это описано)

#### 4. Прописать в etc/hosts элиас для Docker-имени

```posh
Dmitriis-MacBook-Pro rococo % vi /etc/hosts
```

```posh
##
# Host Database
#
# localhost is used to configure the loopback interface
# when the system is booting.  Do not change this entry.
##
127.0.0.1       localhost
127.0.0.1       client.rococo.dc
127.0.0.1       auth.rococo.dc
127.0.0.1       gateway.rococo.dc
```

#### 5. Перейти в корневой каталог проекта

```posh
Dmitriis-MacBook-Pro rococo % cd rococo
```

#### 6. Запустить все сервисы
для REST:

```posh
Dmitriis-MacBook-Pro  rococo % bash docker-compose-dev.sh
```

Текущая версия docker-compose-dev.sh удалит все старые Docker контейнеры в системе, поэтому если у вас есть созданные
контейнеры для других проектов - отредактируйте строку ```posh docker rm $(docker ps -a -q)```

Переходить напрямую по ссылке http://client.rococo.dc

# Запуск e-2-e тестов в Docker network изолированно Rococo в докере:

#### 1. Перейти в корневой каталог проекта

```posh
Dmitriis-MacBook-Pro rococo % cd rococo
```

#### 2. Запустить все сервисы и тесты:

для REST:

```posh
Dmitriis-MacBook-Pro  niffler % bash docker-compose-e2e.sh
```

#### 3. Selenoid UI доступен по адресу: http://localhost:9090/

#### 4. Allure доступен по адресу: http://localhost:5050/allure-docker-service/projects/niffler-e-2-e-tests/reports/latest/index.html
