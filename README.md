# Проект по автоматизации тестирования
## Тест-план

В качестве тестируемого сервиса взят сайт-агрегатор калькуляторов [calcus.ru](https://calcus.ru/).
Сайт содержит несколько десятков различных калькуляторов.

Для тестового проекта рассмотрим небольшую часть функционала сайта:
* Авторизацию на сайте;
* Работа калькулятора расчета [страховых взносов](https://calcus.ru/raschet-strahovyh-vznosov);
* Работа калькулятора расчета [авто кредита](https://calcus.ru/kalkulyator-avtokredita);
* Сохранение и удаление результатов расчета в личном кабинете.


## Тест-анализ
Данный тест-анализ проведен на основе анализа готового продукта. Возможны некоторые допущения и неточности.


### Авторизация на сайте.
> Тестовый пользователь: fadeevdeni@gmail.com  

> Тестовый пароль: fiH-bEM-aFZ-Es3


Форма авторизации открывается во всплывающем окне. Ссылка открытия формы авторизации находится в хэдере сайта на каждой странице.

![Login link](https://downloader.disk.yandex.ru/preview/80a0062e8dd48814f95d2ea4c55e6c5f06c543826c76b6b3f2fd89538c2923aa/61428c09/MEhmSNxaa4uhtFBJO5DaAG8PS-_hcJZgPRpZ2x4En3Cbc6M87QgreKx30euV5nVTWp_pRNLGE0_SecCCoA3Pbg%3D%3D?uid=0&filename=2021-09-15_23-12-35.png&disposition=inline&hash=&limit=0&content_type=image%2Fpng&owner_uid=0&tknv=v2&size=1860x907)

![Authorization form](https://downloader.disk.yandex.ru/preview/5edd64a4b439e0862e2a862d0f59fd0c01663c4f5f7348c5e44f49326354e1e3/61428bc4/bpB0U1Af80Zo0ADbkMZT9vhaqn_ES3ZOf7gXL_XoopzS8IMXxBfI3I_SDYMovxMbxenhl3LvRXnuSUHYCxVFwQ%3D%3D?uid=0&filename=2021-09-15_23-10-27.png&disposition=inline&hash=&limit=0&content_type=image%2Fpng&owner_uid=0&tknv=v2&size=2048x2048)


Поле|Обязательность|Требования
---|---|---
Email | Обязательно | Только буквы латинского алфавита, цифры и спецсимволы. Подходит под регулярное выражение **/^[^@]+@[^@.]+\.[^@]+$/**
Пароль | Обязательно | Длина от 6 до 20 символов. Только буквы латинского алфавита, цифры и спецсимволы.