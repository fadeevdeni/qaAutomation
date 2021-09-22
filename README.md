# Проект по автоматизации тестирования
## Тест-план

В качестве тестируемого сервиса взят сайт-агрегатор калькуляторов [calcus.ru](https://calcus.ru/).
Сайт содержит несколько десятков различных калькуляторов.

Для тестового проекта рассмотрим небольшую часть функционала сайта:
* Авторизацию на сайте;
* Работа калькулятора расчета [авто кредита](https://calcus.ru/kalkulyator-avtokredita).

> Автоматизацию тестирования выполним с помощью скриптов на языке программирования **Java** и программного продукта **Selenium**.

> Для автоматизации сборки проекта будем использовать фреймворк **Apache Maven**.

> Тестирование реализуем на фреймворке для тестирования **TestNG**.


## Тест-анализ
Данный тест-анализ проведен на основе анализа готового продукта. Возможны некоторые допущения и неточности.


### Авторизация на сайте


Форма авторизации открывается во всплывающем окне. Ссылка открытия формы авторизации находится в хэдере сайта на каждой странице.

![Login link](https://downloader.disk.yandex.ru/preview/80a0062e8dd48814f95d2ea4c55e6c5f06c543826c76b6b3f2fd89538c2923aa/61428c09/MEhmSNxaa4uhtFBJO5DaAG8PS-_hcJZgPRpZ2x4En3Cbc6M87QgreKx30euV5nVTWp_pRNLGE0_SecCCoA3Pbg%3D%3D?uid=0&filename=2021-09-15_23-12-35.png&disposition=inline&hash=&limit=0&content_type=image%2Fpng&owner_uid=0&tknv=v2&size=1860x907)

![Authorization form](https://downloader.disk.yandex.ru/preview/5edd64a4b439e0862e2a862d0f59fd0c01663c4f5f7348c5e44f49326354e1e3/61428bc4/bpB0U1Af80Zo0ADbkMZT9vhaqn_ES3ZOf7gXL_XoopzS8IMXxBfI3I_SDYMovxMbxenhl3LvRXnuSUHYCxVFwQ%3D%3D?uid=0&filename=2021-09-15_23-10-27.png&disposition=inline&hash=&limit=0&content_type=image%2Fpng&owner_uid=0&tknv=v2&size=2048x2048)


Поле|Обязательность|Требования
---|---|---
Email | Обязательное | Только буквы латинского алфавита, цифры и спецсимволы. Строка обязательно содержит знак @. Перед и после знака как минимум один символ.
Пароль | Обязательное | Длина от 6 до 20 символов. Только буквы латинского алфавита, цифры и спецсимволы.
Кнопка входа | | Текст кнопки "Войти". Первая буква заглавная, остальные строчные. По нажатию на кнопку происходит первичная валидация введенных данных. Если валидация прошла успешно данные отправляются на сервер, если валидация не проходит выводятся сообщения об ошибках под каждым полем ввода во всплывающих tooltips соответственно. Сервер получая данные либо авторизует пользователя на сайте при нахождении в базе пары логин/пароль и обновляет страницу, либо возвращает соответствующую ошибку. Текст ошибки выводится под формой. Страница при этом не перезагружается, данные приходят в фоном режиме.


Форма авторизации имеет функционал регистрации/входа через социальные сети, ссылки на форму регистрации и форму восстановления пароля. В данной работе рассматриваться не будут. 


### Калькулятор автокредита

Адрес входа в приложение:
> https://calcus.ru/kalkulyator-avtokredita


Калькулятор имеет возможность расчета по стоимости автомобиля и по сумме кредита. Каждый вариант это свой набор полей и тестируется отдельно. Для данного учебного проекта будет протестирован только вариант расчета по сумме кредита.


### Внешний вид формы калькулятора


![Car loan by loan cost](https://downloader.disk.yandex.ru/preview/864e21a74679e177fdd7498821f9e755817cb54a4e042e28801b82decf433b1f/61447005/M6AV8iyKeOnv4PTZgXJcXpL2tb8-f0KU7WSkdKQ0JjWIuNrN1jWHIbCneJBaEKqjL3wrXgQoinpI_XMj26KdRg%3D%3D?uid=0&filename=2021-09-17_09-37-53.png&disposition=inline&hash=&limit=0&content_type=image%2Fpng&owner_uid=0&tknv=v2&size=2048x2048)


**Описание полей**


Поле|Обязательность|Требования
---|:---:|---
Сумма кредита | Обязательное | Число с плавающей точкой до 2х знаков после запятой. Диапазон от 0,01 до 10 000 000 000,00.
Срок кредита | Обязательное | Целое натуральное число. Может быть выражено в годах и в месяцах. Тип выбирается рядом с полем ввода. При выборе срока выражения в годах диапазон значений ограничен от 1 до 50. При выборе выражения в месяцах диапазон значений ограничен от 1 до 600.
Процентная ставка | Обязательное | Число с плавающей точкой. Диапазон от 0,01 до 999,99.
Тип ежемесяных платежей | Обязательное | Выбор варианта расчета кредита. Аннуитетными платежами или Дифференцированными.
Кнопка расчета | — | Текст "РАССЧИТАТЬ". Все буквы заглавные. При нажатии происходит валидация полей формы. При успешном прохождении валидации данные из полей формы передаются в скрипт на Java Script для расчета. Вывод результата расчетов выводится на странице без обновления или перехода на другую страницу, модицикацией DOM структуры текущей страницы.


### Подбор тестовых значений


На основе собранных в процессе анализа данных была составлена [таблица тестовых значений](https://docs.google.com/spreadsheets/d/1ObiDpnMG2El1m_t4u9aFTRSNN1GMV94aHXn7NbqvsSI/edit?usp=sharing).


![Test data](https://downloader.disk.yandex.ru/preview/d9d66692302869bb46471f4fa279351c0606d37087e84a44cec304cd780445e4/6149dd32/MB4MJYKkhC8SVGi70VrdsZYLZqf3g3Ls2L1px8X3GS_Xo32XfMLKkHizvrfWGTcZgpboTt-_X7fbIbx_XQzTPQ%3D%3D?uid=0&filename=2021-09-21_12-24-59.png&disposition=inline&hash=&limit=0&content_type=image%2Fpng&owner_uid=0&tknv=v2&size=2048x2048)


По каждому полю ввода определили условия. Для кадого условия определены класса и типы эквивалентности. В соответсвии с полученными данными подобраны тестовые значения.


В тестировании существует гипотеза что если система верно обработала одно значение внутри класса, она обратает верно и отсальные значения.
Так же есть статистически подтвержденное утверждение что в системе чаще всего возникают ошибки при обработке граничных значений диапазонов.
Тестовые данные в таблице подобраны в соответсвии с этими утверждениями.


### Оптимизация тестовых значений


После сбора всех необходимых тестовых данных что бы проверить каждое условия для каждого поля имеем много дублирующих значений.
Оптимизируем таблицу и убираем повторяющиеся значения. Результат переносим в другую [таблицу](https://docs.google.com/spreadsheets/d/1O1Ddav-YUVHTQTCn3H3eL8Vq82Nz2_jRjfHWKhf52g0/edit?usp=sharing) для наглядности.


![Optimized test data](https://downloader.disk.yandex.ru/preview/af51ba4a3f726a2b535c6f1bde8df9ecf15f27ed57518b4f2033d69a66e29c73/614a703a/fRkI-4NoOU1Hemy8T1zR8OhrnzdAUyc7O1TL5TzzlrN6zld7guTK19vgReRojqnGJYV9ylfkGGNA17OJgXokCg%3D%3D?uid=0&filename=2021-09-21_22-52-16.png&disposition=inline&hash=&limit=0&content_type=image%2Fpng&owner_uid=0&tknv=v2&size=2048x2048)


### Финальный тестовый набор данных


Выбираем все полученные уникальные значения для каждого поля и переносим в другую [таблицу](https://docs.google.com/spreadsheets/d/19JYhOZvg3IUy6F1vktuP0XWFU1kQkEDs8nx5M4nAMtc/edit?usp=sharing).


![Final data set](https://downloader.disk.yandex.ru/preview/16bad0e979549600a1092054134bf6c4dffac162737991710bf8d3595e250652/614a7be4/0W59DtL7BDPr8MSUqn0pa496-bzakS9WMg4GZnAxSNnqN5J5KS_sUIF2gvQUcFNo0n8BOrTsBmh3v6dkcQ72Uw%3D%3D?uid=0&filename=2021-09-21_23-42-06.png&disposition=inline&hash=&limit=0&content_type=image%2Fpng&owner_uid=0&tknv=v2&size=2048x2048)


Для тестирования полей формы авторазации получаем 28 комбинаций пар Логин/Пароль.
Для тестирования полей формы калькулятора получаем 15 120 комбинаций.
* Поле "Сумма кредита" - 20 вариантов
* Поле "Срок кредита" - 36 вариантов
* Поле "Процентная ставка" - 21 вариант

Умножаем 20 * 36 * 21 = 15 120

Сокращаем количество возможных комбинаций методом парного тестирования.

Для составления таблицы воспользуемся сервисом [Pairwise](https://pairwise.teremokgames.com/)

![Pairwise method](https://downloader.disk.yandex.ru/preview/e73e233455cfdbb1136e633e047332bdb14fb05e7ddaae32aa4ca78923379622/614b22ce/SZRWDTUlGPFRXP2UUNftMkC8QmT3KKtEtVaWbj1lgqNV9OCuas_6o3bVZL4TThA3fHY1vR4CAyIztSPaDaTM4w%3D%3D?uid=0&filename=2021-09-22_11-34-15.png&disposition=inline&hash=&limit=0&content_type=image%2Fpng&owner_uid=0&tknv=v2&size=2048x2048)

Генерировать таблицу будем в три этапа.

* Для первого прохода скомбинируем валидные данные для генерации позитивных сценариев.
* Для второго возьмем валидные данные на границах диапазонов.
* Затем все остальные данные для генерации негативных сценариев.

Аналогично поступаем с тестовыми данными для формы авторизации.



После оптимизации получаем 718 комбинации.

**Сокращение количества тестовых комбинаций более чем в 21 раз!**

Данный метод позволяет кратно сокращать количество тест кейсов и времени на тестирование сохраняя полное покрытие системы тестами.

Заносим их в отдельную [таблицу комбинированных тестовых значений](https://docs.google.com/spreadsheets/d/1O3_RfBnd_T2vQa9wMEx2THqiiHVyKnUaj_-HS7tXpyE/edit?usp=sharing)

![Generated pairwise table](https://downloader.disk.yandex.ru/preview/17a5c043aa8f2a9932e9213807a10392b7b2dfb9b15c04d1d4e54153df9d3fd0/614bc708/BNZvTO8spSh1qWBmSsXNXCKODjft2_epuN5bT0Iv_M8Fl8lJjX8xT2SYoTTdxKawzEmxnFV_5hrkbU2YIgXOGA%3D%3D?uid=0&filename=2021-09-22_23-14-45.png&disposition=inline&hash=&limit=0&content_type=image%2Fpng&owner_uid=0&tknv=v2&size=2048x2048)