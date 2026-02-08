package com.example.woordup.data

data class DiscoveryWord(
    val english: String,
    val russian: String,
    val level: String,
    val category: String
)

object WordProvider {
    
    val allDiscoveryWords = listOf(
        DiscoveryWord("Access", "Доступ / Проход", "B1", "General"),
        DiscoveryWord("Advice", "Совет / Консультация", "B1", "General"),
        DiscoveryWord("Alive", "Живой / Существующий", "B1", "General"),
        DiscoveryWord("Alone", "Один / Одинокий", "B1", "General"),
        DiscoveryWord("Answer", "Ответ / Решение", "B1", "General"),
        DiscoveryWord("Beautiful", "Красивый / Прекрасный", "B1", "General"),
        DiscoveryWord("Believe", "Верить / Полагать", "B1", "General"),
        DiscoveryWord("Change", "Менять / Изменение", "B1", "General"),
        
        DiscoveryWord("Arrival", "Прибытие / Приезд", "B1", "Travel"),
        DiscoveryWord("Luggage", "Багаж / Чемоданы", "B1", "Travel"),
        DiscoveryWord("Customs", "Таможня", "B1", "Travel"),
        DiscoveryWord("Departure", "Отправление / Вылет", "B1", "Travel"),
        DiscoveryWord("Passenger", "Пассажир", "B1", "Travel"),
        DiscoveryWord("Passport", "Паспорт", "B1", "Travel"),
        DiscoveryWord("Ticket", "Билет / Проездной", "B1", "Travel"),
        
        DiscoveryWord("Account", "Счет / Аккаунт / Отчет", "B1", "Business"),
        DiscoveryWord("Agree", "Соглашаться / Договариваться", "B1", "Business"),
        DiscoveryWord("Offer", "Предложение / Оферта", "B1", "Business"),
        DiscoveryWord("Company", "Компания / Фирма", "B1", "Business"),
        DiscoveryWord("Boss", "Босс / Начальник", "B1", "Business"),
        DiscoveryWord("Career", "Карьера / Профессия", "B1", "Business"),

        DiscoveryWord("Click", "Кликнуть / Нажать", "B1", "Tech"),
        DiscoveryWord("File", "Файл / Документ", "B1", "Tech"),
        DiscoveryWord("Screen", "Экран / Монитор", "B1", "Tech"),
        DiscoveryWord("Keyboard", "Клавиатура", "B1", "Tech"),
        DiscoveryWord("Mouse", "Мышь (компьютерная)", "B1", "Tech"),
        
        DiscoveryWord("Atom", "Атом", "B1", "Science"),
        DiscoveryWord("Energy", "Энергия / Сила", "B1", "Science"),
        DiscoveryWord("Planet", "Планета", "B1", "Science"),
        DiscoveryWord("Space", "Космос / Пространство", "B1", "Science"),

        DiscoveryWord("Color", "Цвет", "B1", "Art"),
        DiscoveryWord("Draw", "Рисовать / Чертить", "B1", "Art"),
        DiscoveryWord("Music", "Музыка", "B1", "Art"),
        DiscoveryWord("Photo", "Фотография / Снимок", "B1", "Art"),

        DiscoveryWord("Game", "Игра / Матч", "B1", "Sport"),
        DiscoveryWord("Team", "Команда / Сборная", "B1", "Sport"),
        DiscoveryWord("Ball", "Мяч", "B1", "Sport"),
        DiscoveryWord("Win", "Побеждать / Выигрывать", "B1", "Sport"),

        DiscoveryWord("Vote", "Голосовать / Голос", "B1", "Politics"),
        DiscoveryWord("Law", "Закон / Право", "B1", "Politics"),
        DiscoveryWord("Leader", "Лидер / Руководитель", "B1", "Politics"),
        DiscoveryWord("City", "Город", "B1", "Politics"),


        DiscoveryWord("Abandon", "Покидать / Оставлять / Бросать", "B2", "General"),
        DiscoveryWord("Absolute", "Абсолютный / Полный", "B2", "General"),
        DiscoveryWord("Accurate", "Точный / Правильный", "B2", "General"),
        DiscoveryWord("Achieve", "Достигать / Добиваться", "B2", "General"),
        DiscoveryWord("Active", "Активный / Деятельный", "B2", "General"),
        
        DiscoveryWord("Database", "База данных", "B2", "Tech"),
        DiscoveryWord("Network", "Сеть / Сообщество", "B2", "Tech"),
        DiscoveryWord("Software", "Программное обеспечение / ПО", "B2", "Tech"),
        DiscoveryWord("Hardware", "Аппаратное обеспечение / Железо", "B2", "Tech"),
        DiscoveryWord("Browser", "Браузер / Обозреватель", "B2", "Tech"),
        
        DiscoveryWord("Negotiate", "Вести переговоры / Договариваться", "B2", "Business"),
        DiscoveryWord("Strategy", "Стратегия / План действий", "B2", "Business"),
        DiscoveryWord("Deadline", "Крайний срок / Дедлайн", "B2", "Business"),
        DiscoveryWord("Budget", "Бюджет / Смета", "B2", "Business"),
        DiscoveryWord("Client", "Клиент / Заказчик", "B2", "Business"),
        
        DiscoveryWord("Destination", "Пункт назначения / Место назначения", "B2", "Travel"),
        DiscoveryWord("Accommodation", "Жилье / Размещение / Ночлег", "B2", "Travel"),
        DiscoveryWord("Itinerary", "Маршрут / Программа поездки", "B2", "Travel"),
        DiscoveryWord("Reservation", "Бронирование / Резервация", "B2", "Travel"),
        DiscoveryWord("Guide", "Гид / Путеводитель / Экскурсовод", "B2", "Travel"),

        DiscoveryWord("Biology", "Биология", "B2", "Science"),
        DiscoveryWord("Chemistry", "Химия", "B2", "Science"),
        DiscoveryWord("Experiment", "Эксперимент / Опыт", "B2", "Science"),
        DiscoveryWord("Gravity", "Гравитация / Притяжение", "B2", "Science"),

        DiscoveryWord("Design", "Дизайн / Проектирование", "B2", "Art"),
        DiscoveryWord("Gallery", "Галерея / Выставка", "B2", "Art"),
        DiscoveryWord("Painting", "Живопись / Картина", "B2", "Art"),
        DiscoveryWord("Studio", "Студия / Мастерская", "B2", "Art"),

        DiscoveryWord("Champion", "Чемпион / Победитель", "B2", "Sport"),
        DiscoveryWord("Coach", "Тренер / Наставник", "B2", "Sport"),
        DiscoveryWord("Match", "Матч / Состязание", "B2", "Sport"),
        DiscoveryWord("Score", "Счет (в игре) / Результат", "B2", "Sport"),

        DiscoveryWord("Campaign", "Кампания / Агитация", "B2", "Politics"),
        DiscoveryWord("Citizen", "Гражданин / Житель", "B2", "Politics"),
        DiscoveryWord("Election", "Выборы / Избрание", "B2", "Politics"),
        DiscoveryWord("Government", "Правительство / Власть", "B2", "Politics"),


        DiscoveryWord("Abolish", "Отменять / Упразднять / Уничтожать", "C1", "General"),
        DiscoveryWord("Absence", "Отсутствие / Недостаток", "C1", "General"),
        DiscoveryWord("Absurd", "Абсурдный / Нелепый", "C1", "General"),
        DiscoveryWord("Abundance", "Изобилие / Избыток", "C1", "General"),
        DiscoveryWord("Accelerate", "Ускорять / Разгонять", "C1", "General"),

        DiscoveryWord("Algorithm", "Алгоритм / Порядок действий", "C1", "Tech"),
        DiscoveryWord("Encryption", "Шифрование / Кодирование", "C1", "Tech"),
        DiscoveryWord("Latency", "Задержка / Латентность", "C1", "Tech"),
        DiscoveryWord("Bandwidth", "Пропускная способность", "C1", "Tech"),
        DiscoveryWord("Cloud Computing", "Облачные вычисления", "C1", "Tech"),

        DiscoveryWord("Acquisition", "Приобретение / Покупка (бизнеса)", "C1", "Business"),
        DiscoveryWord("Merger", "Слияние / Объединение", "C1", "Business"),
        DiscoveryWord("Stakeholder", "Заинтересованная сторона / Акционер", "C1", "Business"),
        DiscoveryWord("Revenue", "Выручка / Доход", "C1", "Business"),
        DiscoveryWord("Dividend", "Дивиденд / Прибыль", "C1", "Business"),

        DiscoveryWord("Embark", "Посадка / Начинать", "C1", "Travel"),
        DiscoveryWord("Excursion", "Экскурсия / Поездка", "C1", "Travel"),
        DiscoveryWord("Layover", "Пересадка / Остановка в пути", "C1", "Travel"),
        DiscoveryWord("Itinerary", "Маршрут / План путешествия", "C1", "Travel"),
        DiscoveryWord("Visa", "Виза / Разрешение на въезд", "C1", "Travel"),

        DiscoveryWord("Evolution", "Эволюция / Развитие", "C1", "Science"),
        DiscoveryWord("Hypothesis", "Гипотеза / Предположение", "C1", "Science"),
        DiscoveryWord("Laboratory", "Лаборатория", "C1", "Science"),
        DiscoveryWord("Microscope", "Микроскоп", "C1", "Science"),

        DiscoveryWord("Abstract", "Абстрактный / Отвлеченный", "C1", "Art"),
        DiscoveryWord("Exhibition", "Выставка / Экспозиция", "C1", "Art"),
        DiscoveryWord("Masterpiece", "Шедевр / Произведение искусства", "C1", "Art"),
        DiscoveryWord("Portrait", "Портрет / Изображение", "C1", "Art"),

        DiscoveryWord("Athlete", "Атлет / Спортсмен", "C1", "Sport"),
        DiscoveryWord("Stadium", "Стадион / Арена", "C1", "Sport"),
        DiscoveryWord("Tournament", "Турнир / Чемпионат", "C1", "Sport"),
        DiscoveryWord("Victory", "Победа / Триумф", "C1", "Sport"),

        DiscoveryWord("Candidate", "Кандидат / Претендент", "C1", "Politics"),
        DiscoveryWord("Debate", "Дебаты / Дискуссия", "C1", "Politics"),
        DiscoveryWord("Democracy", "Демократия / Народовластие", "C1", "Politics"),
        DiscoveryWord("Policy", "Политика / Стратегия", "C1", "Politics"),


        DiscoveryWord("Abbreviation", "Аббревиатура / Сокращение", "C2", "General"),
        DiscoveryWord("Abhor", "Ненавидеть / Питать отвращение", "C2", "General"),
        DiscoveryWord("Abject", "Жалкий / Униженный", "C2", "General"),
        DiscoveryWord("Anomaly", "Аномалия / Отклонение", "C2", "General"),
        DiscoveryWord("Antipathy", "Антипатия / Неприязнь", "C2", "General"),

        DiscoveryWord("Artificial Intelligence", "Искусственный интеллект / ИИ", "C2", "Tech"),
        DiscoveryWord("Blockchain", "Блокчейн / Цепочка блоков", "C2", "Tech"),
        DiscoveryWord("Quantum Computing", "Квантовые вычисления", "C2", "Tech"),
        DiscoveryWord("Neural Network", "Нейронная сеть", "C2", "Tech"),
        DiscoveryWord("Virtual Reality", "Виртуальная реальность / VR", "C2", "Tech"),

        DiscoveryWord("Bankruptcy", "Банкротство / Несостоятельность", "C2", "Business"),
        DiscoveryWord("Monopoly", "Монополия", "C2", "Business"),
        DiscoveryWord("Subsidiary", "Дочерняя компания / Филиал", "C2", "Business"),
        DiscoveryWord("Conglomerate", "Конгломерат / Корпорация", "C2", "Business"),
        DiscoveryWord("Fiscal", "Фискальный / Финансовый", "C2", "Business"),

        DiscoveryWord("Circumnavigate", "Оплыть вокруг света", "C2", "Travel"),
        DiscoveryWord("Sabbatical", "Творческий отпуск / Длительный отпуск", "C2", "Travel"),
        DiscoveryWord("Wanderlust", "Страсть к путешествиям", "C2", "Travel"),
        DiscoveryWord("Ecotourism", "Экотуризм", "C2", "Travel"),
        DiscoveryWord("Globetrotter", "Путешественник", "C2", "Travel"),
         
        DiscoveryWord("Astrophysics", "Астрофизика", "C2", "Science"),
        DiscoveryWord("Genetics", "Генетика", "C2", "Science"),
        DiscoveryWord("Neuroscience", "Нейробиология", "C2", "Science"),
        DiscoveryWord("Thermodynamics", "Термодинамика", "C2", "Science"),

        DiscoveryWord("Aesthetic", "Эстетический / Эстетика", "C2", "Art"),
        DiscoveryWord("Avant-garde", "Авангард / Передовой", "C2", "Art"),
        DiscoveryWord("Curator", "Куратор (выставки)", "C2", "Art"),
        DiscoveryWord("Renaissance", "Ренессанс / Возрождение", "C2", "Art"),

        DiscoveryWord("Amateur", "Любитель / Непрофессионал", "C2", "Sport"),
        DiscoveryWord("Referee", "Судья / Рефери", "C2", "Sport"),
        DiscoveryWord("Spectator", "Зритель", "C2", "Sport"),
        DiscoveryWord("Endurance", "Выносливость / Стойкость", "C2", "Sport"),

        DiscoveryWord("Coalition", "Коалиция / Союз", "C2", "Politics"),
        DiscoveryWord("Diplomacy", "Дипломатия", "C2", "Politics"),
        DiscoveryWord("Minister", "Министр", "C2", "Politics"),
        DiscoveryWord("Referendum", "Референдум / Всенародный опрос", "C2", "Politics")
    )

    val allCategories = listOf(
        "General", "Business", "Travel", "Tech",
        "Science", "Art", "Sport", "Politics"
    )

    fun getWords(level: String, category: String): List<DiscoveryWord> {
        return allDiscoveryWords.filter { 
            it.level == level && (category == "All" || it.category == category)
        }
    }
}
