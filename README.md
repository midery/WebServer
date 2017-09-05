# WebServer
Сервер на **Apache Tomcat**, обрабатывающий запросы, поступающие от приложения Courier Service. Содержит логику работы с базой данных.
## Использованные библиотеки
* ORM Lite
* Gson
## Установка сервера на примере среды Intellij IDEA
1. Скачать и установить postgresql, запустить сервер и создать базу данных "courierservice", расположенную по стандартному адресу "localhost:5432/courierservice" с пользователем postgres и паролем postgres.
1. В intellij idea (или другой IDE, в которой происходит работа), в скаченном из репозитория проекте установить параметры запуска (Run -> Edit Configurations -> Add New Configuration -> Tomcat Server -> Local). [Скриншот 1]
1. Добавить artifact на вкладке Server (Add -> Artifact) 
1. Для создания всех таблиц (User, Package, Person), необходимых для работы БД, нужно запустить метод main из класса Main.
1. Для запуска сервера, необходимо выбрать созданную конфигурацию с Tomcat и нажать "Run".
