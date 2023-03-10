<h1>Сервис по опросу о любимых вещах</h1>
<h2>Инструкция к запуску:</h2>
Соберем проект через мавен:

- mvn clean package

<i>если на этом этапе не получилось, вероятнее всего постгрес не дает создать пэкеджи, для этого в docker-compose в Idea следует нажать галочку на postgres(запустить постгрес)

![image](https://user-images.githubusercontent.com/71641509/221931949-a5f87cea-ec75-4c1b-9667-436759b16fa3.png)


Сбилдим образ:

- docker build -t fav-things-service.jar .

Поднимем композ файл:

- docker-compose up -d

- зайдем на http://localhost:8090/
  
  

проверяем работоспособность

<h2>Обоснование дополнительны зависимостей</h2>

- <b>springfox(swagger)</b> - для документации апи контроллеров.

- <b>mybatis</b> - использован для маппинга в базу данных логов

- <b>jsonwebtoken</b> - использован для Jwt токена при аутентификации

- <b>lombok</b> - использован чтобы сократить код сущностей

- <b>mustache</b> - использован в качестве шаблонизатора на фронте

<h2>Демонстрация работы программы</h2>

<h3>Окно приветствия<h3>

![image](https://user-images.githubusercontent.com/71641509/221851760-f5a55acb-4255-4ed1-8d66-af684fc16fdb.png)

<h3>Окно авторизации<h3>

![image](https://user-images.githubusercontent.com/71641509/221851869-80caf142-b9a0-4c3b-8f42-8db8e9d57dc3.png)

- валидация полей на пустые значения:

![image](https://user-images.githubusercontent.com/71641509/221851953-cb7e777c-2632-4d7c-9522-3dcd527f9014.png)

<h3>Окно регистрации<h3>

![image](https://user-images.githubusercontent.com/71641509/221879620-27728644-360f-421f-be95-c8b938660beb.png)

- Используя сервис временной почты, можем зарегестрировать аккаунт с ролью пользователя

* На почту пришло письмо с активационным кодом:

![image](https://user-images.githubusercontent.com/71641509/221879885-73482f77-4931-482a-a3d3-4667e810fc02.png)

![image](https://user-images.githubusercontent.com/71641509/221879941-2ad9f659-8a91-4ed3-874a-c15023f4c7cc.png)

* При переходе по ссылке видим: 

![image](https://user-images.githubusercontent.com/71641509/221922099-8b7ae8e4-5d2b-4388-a8df-36888917a24c.png)

* Если еще раз: 

![image](https://user-images.githubusercontent.com/71641509/221922205-db1f37e7-fd57-4bc2-859f-5407b517cdc9.png)


* Зайдя по кредам, можно увидеть окно обычного пользователя:

![image](https://user-images.githubusercontent.com/71641509/221880221-4c6516fd-c307-467c-92d2-f52a8f9a3a64.png)

* Создадим пользователя с ролью админа и посмотрим что будет у него на странице: 

![image](https://user-images.githubusercontent.com/71641509/221880666-41bd1aeb-c5e7-4b10-8d26-5027af1ed0a7.png)

<h3>Как пользователь хранится в БД(уже с подтвержденной почтой)<h3>
  
![image](https://user-images.githubusercontent.com/71641509/222092428-fea5a37d-8f56-4940-8403-119e42b4f0c6.png)


<h3>Логирование</h3>

Использовал аспекты, вот что содержится в БД:

![image](https://user-images.githubusercontent.com/71641509/221922949-52f5bd3f-2c0d-46fd-89cd-187d313c93fc.png)

<h3>Документация на сваггере</h3>
  
  <i> по ссылке http://localhost:8090/swagger-ui.html#/ </i>

![image](https://user-images.githubusercontent.com/71641509/221930854-d900ad1e-1111-4cba-9f36-cc280852f2d7.png)

![image](https://user-images.githubusercontent.com/71641509/221930976-148415a6-aaa4-433f-a4b9-19a0e846fe7a.png)

![image](https://user-images.githubusercontent.com/71641509/221931110-4fe28046-14a8-4ae2-ab00-1d2fbccb2775.png)


<h2>Что не успел</h2>
Не успел обработать ошибки, сделать jтесты
