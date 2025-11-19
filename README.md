# POMProject2

Автоматизированное тестирование веб-приложения для поиска авиабилетов с использованием паттерна Page Object Model (POM).

## Описание проекта

Проект содержит автоматизированные тесты для веб-приложения поиска авиабилетов. Тесты написаны с использованием паттерна Page Object Model, что обеспечивает удобство поддержки и переиспользования кода.

## Технологии

- **Java** - язык программирования
- **Gradle** - система сборки
- **Selenide** - фреймворк для автоматизации веб-тестирования
- **JUnit 5** - фреймворк для написания и запуска тестов
- **Allure** - фреймворк для генерации отчетов о тестировании

## Структура проекта

```
src/
├── main/
│   ├── java/
│   └── resources/
└── test/
    ├── java/
    │   ├── pages/              # Page Object классы
    │   │   ├── LoginPage.java
    │   │   ├── SearchPage.java
    │   │   ├── FlightsListPage.java
    │   │   └── RegistrationPage.java
    │   ├── POMFlightsTests.java    # Тесты с использованием POM
    │   └── SimpleFlightsTests.java # Простые тесты без POM
    └── resources/
```

## Тестовые сценарии

### POMFlightsTests
1. Неуспешный логин
2. Не задана дата вылета
3. Не найдены рейсы
4. Неправильный номер паспорта
5. Успешная регистрация
6. Сложный сценарий (комбинация нескольких проверок)

### SimpleFlightsTests
Аналогичные тесты, написанные без использования Page Object Model.

## Запуск тестов

### Запуск всех тестов
```bash
./gradlew test
```

### Запуск конкретного тестового класса
```bash
./gradlew test --tests POMFlightsTests
./gradlew test --tests SimpleFlightsTests
```

### Запуск конкретного теста
```bash
./gradlew test --tests POMFlightsTests.test01WrongPassword
```

## Генерация Allure отчетов

### Генерация отчета
```bash
./gradlew allureReport
```

### Просмотр отчета
```bash
./gradlew allureServe
```

Отчет будет открыт автоматически в браузере.

## Требования

- Java 21 или выше
- Gradle (используется Gradle Wrapper)
- Chrome браузер (для запуска тестов)

## Настройка

Тесты настроены на работу с приложением по адресу: `https://slqa.ru/cases/DeepSeekFlights/`

## Page Object Model

Проект использует паттерн Page Object Model, где каждый класс страницы (`LoginPage`, `SearchPage`, `FlightsListPage`, `RegistrationPage`) инкапсулирует элементы и действия, связанные с конкретной страницей веб-приложения.

## Allure Steps

Все тесты используют аннотации `@Step` для создания детальных отчетов в Allure. Каждый шаг теста отображается в отчете с описанием на русском языке.

## Лицензия

Этот проект создан в учебных целях.

