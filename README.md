# Проект по автоматизации тестирования
## Тест-план

В качестве тестируемого сервиса взят сайт-агрегатор калькуляторов [calcus.ru](https://calcus.ru/).
Сайт содержит несколько десятков различных калькуляторов.

Для тестового проекта рассмотрим небольшую часть функционала сайта:
* Авторизацию на сайте;
* Работа калькулятора расчета [авто кредита](https://calcus.ru/kalkulyator-avtokredita).

> Автоматизацию тестирования выполним с помощью скриптов на языке программирования **Java**.

> Для автоматизации сборки проекта будем использовать фреймворк **Apache Maven**.

> Тестирование реализуем на фреймворке для тестирования **TestNG**.


## Тест-анализ
Данный тест-анализ проведен на основе анализа готового продукта. Возможны некоторые допущения и неточности.


### Авторизация на сайте
> Тестовый пользователь: fadeevdeni@gmail.com  

> Тестовый пароль: fiH-bEM-aFZ-Es3


Форма авторизации открывается во всплывающем окне. Ссылка открытия формы авторизации находится в хэдере сайта на каждой странице.

![Login link](https://downloader.disk.yandex.ru/preview/80a0062e8dd48814f95d2ea4c55e6c5f06c543826c76b6b3f2fd89538c2923aa/61428c09/MEhmSNxaa4uhtFBJO5DaAG8PS-_hcJZgPRpZ2x4En3Cbc6M87QgreKx30euV5nVTWp_pRNLGE0_SecCCoA3Pbg%3D%3D?uid=0&filename=2021-09-15_23-12-35.png&disposition=inline&hash=&limit=0&content_type=image%2Fpng&owner_uid=0&tknv=v2&size=1860x907)

![Authorization form](https://downloader.disk.yandex.ru/preview/5edd64a4b439e0862e2a862d0f59fd0c01663c4f5f7348c5e44f49326354e1e3/61428bc4/bpB0U1Af80Zo0ADbkMZT9vhaqn_ES3ZOf7gXL_XoopzS8IMXxBfI3I_SDYMovxMbxenhl3LvRXnuSUHYCxVFwQ%3D%3D?uid=0&filename=2021-09-15_23-10-27.png&disposition=inline&hash=&limit=0&content_type=image%2Fpng&owner_uid=0&tknv=v2&size=2048x2048)


Поле|Обязательность|Требования
---|---|---
Email | Обязательное | Только буквы латинского алфавита, цифры и спецсимволы. Подходит под регулярное выражение **/^[^@]+@[^@.]+\.[^@]+$/**
Пароль | Обязательное | Длина от 6 до 20 символов. Только буквы латинского алфавита, цифры и спецсимволы.


Форма авторизации имеет функционал регистрации/входа через социальные сети, ссылки на форму регистрации и форму восстановления пароля. В данной работе рассматриваться не будут. 


### Калькулятор автокредита

Адрес входа в приложение:
> https://calcus.ru/kalkulyator-avtokredita


Калькулятор имеет возможность расчета по стоимости автомобиля и по сумме кредита. Каждый вариант это свой набор полей и теструется отдельно.

#### По стоимости автомобиля


**Внешний вид формы калькулятора**


![Car loan by car cost](https://downloader.disk.yandex.ru/preview/4989156369ef1a34ca33280d63850f9264ecdc3737f3fefd18582a850c4b9f34/61436481/7g82ceNyWtusGBxoQeF4IORIcWSsxCGhYix_aBhhEQs8vV39hGXAhkyT0sFCOPtJ_MYANVZpyWMZ5vNsFwdrpw%3D%3D?uid=0&filename=2021-09-16_14-36-23.png&disposition=inline&hash=&limit=0&content_type=image%2Fpng&owner_uid=0&tknv=v2&size=2048x2048)


**Описание полей**


Поле|Обязательность|Требования
---|:---:|---
Стоимость автомобиля | Обязательное | Число с плавающей точкой до 2х знаков после запятой. Диапазон от 0,01 до 10 000 000 000,00.
Первоначальный взнос | Обязательное | Число с плавающей точкой до 2х знаков после запятой. Число может выражать как процент от указанной стоимости автомобиля так и конкретную сумму в рублях. Проценты или рубли выбираются в выпадающем списке рядом с полем ввода. При выборе варианта в процентах диапазон ограничивается от 0,00 до 99,99. При выборе варианта в рублях диапазон ограничивается от 0,01 до 10 000 000 000,00. Первоначальный взнос не может быть больше или равен числу указанному в поле "Стоимость автомобиля".
Сумма кредита | Обязательное | Число с плавающей точкой до 2х знаков после запятой. Расчитывается автоматически на основе данных из полей "Стоимость автомобиля" и "Первоначальный взнос". Формула расчета зависит от выбранного типа ввода первоначального взноса. При выборе выражения в рублях расчитывается = "Стоимость автомобиля" – "Первоначальный взнос". При выборе выражения через проценты расчитывается = "Стоимость автомобиля" – ("Стоимость автомобиля" * ("Первоначальный взнос"/100))
Срок кредита | Обязательное | Целое натуральное число. Может быть выражено в годах и в месяцах. Тип выбирается рядом с полем ввода. При выборе срока выражения в годах диапазон значений ограничен от 1 до 50. При выборе выражения в месяцах диапазон значений ограничен от 1 до 600.
Процентная ставка | Обязательное | Число с плавающей точкой. Диапазон от 0,01 до 999,99.
Тип ежемесяных платежей | Обязательное | Выбор варианта расчета кредита. Аннуитетными платежами или Дифференцированными.
Кнопка расчета | — | Текст "РАССЧИТАТЬ". Все буквы заглавные. При нажатии происходит валидация полей формы. При успешном прохождении валидации данные из полей формы передаются в скрипт на Java Script для расчета. Вывод результата расчетов выводится на странице без обновления или перехода на другую страницу, модицикацией DOM структуры текущей страницы.


#### По сумме кредита


**Внешний вид формы калькулятора**


![Car loan by loan cost](https://downloader.disk.yandex.ru/preview/864e21a74679e177fdd7498821f9e755817cb54a4e042e28801b82decf433b1f/61447005/M6AV8iyKeOnv4PTZgXJcXpL2tb8-f0KU7WSkdKQ0JjWIuNrN1jWHIbCneJBaEKqjL3wrXgQoinpI_XMj26KdRg%3D%3D?uid=0&filename=2021-09-17_09-37-53.png&disposition=inline&hash=&limit=0&content_type=image%2Fpng&owner_uid=0&tknv=v2&size=2048x2048)


**Описание полей**


Поле|Обязательность|Требования
---|:---:|---
Сумма кредита | Обязательное | Число с плавающей точкой до 2х знаков после запятой. Диапазон от 0,01 до 10 000 000 000,00.
Срок кредита | Обязательное | Целое натуральное число. Может быть выражено в годах и в месяцах. Тип выбирается рядом с полем ввода. При выборе срока выражения в годах диапазон значений ограничен от 1 до 50. При выборе выражения в месяцах диапазон значений ограничен от 1 до 600.
Процентная ставка | Обязательное | Число с плавающей точкой. Диапазон от 0,01 до 999,99.
Тип ежемесяных платежей | Обязательное | Выбор варианта расчета кредита. Аннуитетными платежами или Дифференцированными.
Кнопка расчета | — | Текст "РАССЧИТАТЬ". Все буквы заглавные. При нажатии происходит валидация полей формы. При успешном прохождении валидации данные из полей формы передаются в скрипт на Java Script для расчета. Вывод результата расчетов выводится на странице без обновления или перехода на другую страницу, модицикацией DOM структуры текущей страницы.