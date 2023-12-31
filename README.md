# Notification service

## Задача :book:
Разработать сервис оповещения пользователей о различных событиях.

## Описание функционала: :mag:
- Пользователи должны быть представлены следующей информацией: ФИО и периоды информирования; 
  Например: Иванов Иван Иванович, время информирования: среда-пятница с 8-00 до 13-00, суббота с 9-30 до 10-30,   воскресенье с 16-00 до 20-00 и с 21-00 до 22-00;
- События должны быть представлены сообщением, датой и временем, когда они произошли;
- При возникновении события должна быть осуществлена рассылка оповещений всем пользователям, в зависимости от их периодов   информирования;
- Если событие возникло вне времени информирования конкретного пользователя, то уведомление должно быть ему доставлено во   время наступления ближайшего периода. 

## Требования :heavy_exclamation_mark:

- :heavy_check_mark: Реализовать хранение данных пользователей и событий в Postgres, и чтение/обновление/удаление данных через REST API;
- :heavy_check_mark: Реализовать функционал оповещения пользователей, который должен выводить текст оповещения в лог;
- :heavy_check_mark: Разместить код в GitHub или GitLab.

## Дополнительные требования :bangbang:

- :white_check_mark: Организовать отправку оповещений через Websocket;
- :whale2: Разместить приложение в Docker-контейнере (Docker compose);
- :white_check_mark: Подготовить unit-тесты;
- :white_check_mark: Подготовить тесты REST API.
