package com.sakuntswingo.bingo;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class QuizDataInitializer {

    private FirebaseFirestore db;

    public QuizDataInitializer() {
        db = FirebaseFirestore.getInstance();
    }

    public void initializeQuizData() {
        // Check if quizzes collection is empty before initializing
        db.collection("quizzes").get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult().isEmpty()) {
                // Versace Quiz
                initializeVersaceQuiz();
                // Chanel Quiz
                initializeChanelQuiz();
                // Dior Quiz
                initializeDiorQuiz();
                // Valentino Quiz
                initializeValentinoQuiz();
                // Yves Saint Laurent Quiz (Placeholder)
                initializeYvesSaintLaurentQuiz();
                Log.d("QuizDataInitializer", "All quiz data initialized");
            } else if (task.isSuccessful()) {
                Log.d("QuizDataInitializer", "Quiz data already exists, skipping initialization");
            } else {
                Log.e("QuizDataInitializer", "Failed to check quizzes collection: " + task.getException().getMessage());
            }
        });
    }

    private void initializeVersaceQuiz() {
        String[] questions = {
                "Кто основал модный дом Versace?",
                "Как называется культовый логотип бренда Versace?",
                "Как называется коллекция предметов интерьера от бренда Versace?",
                "Как зовут сестру Джанни Версаче, ставшую креативным директором после его смерти?",
                "Какой элемент часто используется в принтах одежды Versace?",
                "В каком городе был основан бренд Versace?",
                "Кому Джанни Версаце посвятил свою последнюю коллекцию?",
                "Какой стиль чаще всего отражает одежда Versace?",
                "Как называется знаменитое зелёное платье, которое Дженнифер Лопес надела на церемонии «Грэмми»?",
                "Кто стал музой Джанни Версаче и получил прозвище «чёрная пантера моды»?",
                "Какой аксессуар часто украшает мужскую одежду Versace?",
                "Какой модный дом считался главным конкурентом Versace в 1990-х?",
                "В каком году был убит Джанни Версаче?",
                "Что символизирует голова Медузы в логотипе Versace?",
                "Кто из знаменитостей был близким другом Джанни и постоянной моделью бренда?"
        };

        String[][] choices = {
                {"Донателла Версаче", "Джанни Версаче", "Сильвия Версаче", "Марко Версаче"},
                {"Афродита", "Голова Медузы", "Греческий орнамент", "Леопард"},
                {"Versace Atelier", "Versace Home", "Versace Edition", "Versace Casa"},
                {"Кьяра Версаче", "Сильвия Версаче", "Донателла Версаче", "Альберта Версаче"},
                {"Греческий ключ", "Цветочные узоры", "Тартан", "Клетка"},
                {"Милан", "Рим", "Неаполь", "Флоренция"},
                {"Принцессе Диане", "Мадонне", "Клаудии Шиффер", "Элтону Джону"},
                {"Минимализм", "Бунтарский гламур", "Гранж", "Панк-рок"},
                {"Jungle Dress", "Medusa Gown", "Golden Flame", "Greek Jungle"},
                {"Наоми Кэмпбелл", "Кейт Мосс", "Синди Кроуфорд", "Клаудия Шиффер"},
                {"Платок", "Брошь-медуза", "Цепочка", "Очки-щит"},
                {"Givenchy", "Dolce & Gabbana", "Chanel", "Gucci"},
                {"1995", "1997", "1999", "2001"},
                {"Привлекательность, которую невозможно забыть", "Опасность", "Скорость", "Греческое наследие"},
                {"Наоми Кэмпбелл", "Мадонна", "Стефани Сеймур", "Шер"}
        };

        String[] correctAnswers = {
                "Джанни Версаче",
                "Голова Медузы",
                "Versace Home",
                "Донателла Версаче",
                "Греческий ключ",
                "Милан",
                "Принцессе Диане",
                "Бунтарский гламур",
                "Jungle Dress",
                "Наоми Кэмпбелл",
                "Брошь-медуза",
                "Dolce & Gabbana",
                "1997",
                "Привлекательность, которую невозможно забыть",
                "Наоми Кэмпбелл"
        };

        for (int i = 0; i < questions.length; i++) {
            Map<String, Object> questionData = new HashMap<>();
            questionData.put("question", questions[i]);
            questionData.put("choices", Arrays.asList(choices[i]));
            questionData.put("correctAnswer", correctAnswers[i]);
            questionData.put("index", i);

            db.collection("quizzes")
                    .document("versace")
                    .collection("questions")
                    .document(String.valueOf(i))
                    .set(questionData);
        }
    }

    private void initializeChanelQuiz() {
        String[] questions = {
                "В каком году была представлена сумка Chanel 2.55?",
                "Почему сумка Chanel 2.55 получила такое название?",
                "Какой элемент дизайна Chanel 2.55 символизирует детство Шанель?",
                "Какой цвет была изначально внутренняя часть сумки 2.55?",
                "Из какого материала сначала планировалось сделать сумку 2.55?",
                "Как называется застёжка на сумке Chanel 2.55?",
                "Что вдохновило на форму кармана в виде волны в Chanel 2.55?",
                "Какое значение имеет цепочка на сумке Chanel 2.55?",
                "Что вдохновило на форму часов Chanel Première?",
                "В каком отеле жила Коко Шанель?",
                "На какую парижскую площадь похож корпус часов Première?",
                "Какой цвет символизирует детство Коко Шанель?",
                "Когда были выпущены первые часы Chanel?",
                "Кто является создателем аромата Chanel №5?",
                "Какой элемент Chanel №5 связан с Вандомской площадью?"
        };

        String[][] choices = {
                {"Февраль 1955", "Март 1956", "Январь 1954", "Апрель 1957"},
                {"Дата рождения Шанель", "Месяц и год выпуска", "Год её смерти", "Сумма инвестиций"},
                {"Цепочка", "Красная подкладка", "Кармашек", "Форма стёжки"},
                {"Красный", "Чёрный", "Серый", "Бежевый"},
                {"Шёлк", "Твид", "Джерси", "Атлас"},
                {"Mademoiselle", "Chanel Lock", "2.55 Original", "Coco Closure"},
                {"Улыбка Моны Лизы", "Овал Вандомской площади", "Форма алмаза", "Форма сердца"},
                {"Детство в приюте", "Элегантность", "Милитаризм", "Ювелирные цепи"},
                {"Форма Вандомской площади", "Коробка для духов", "Письмо Шанель", "Форма сердца"},
                {"Ritz", "Plaza Athénée", "Crillon", "Le Meurice"},
                {"Площадь Вандом", "Площадь Конкорд", "Площадь Бастилии", "Площадь Наций"},
                {"Чёрный", "Золотой", "Красный", "Белый"},
                {"1987", "1990", "1985", "1995"},
                {"Эрнест Бо", "Карл Лагерфельд", "Жак Поль", "Жан Пату"},
                {"Форма флакона", "Цвет жидкости", "Крышка", "Цепочка"}
        };

        String[] correctAnswers = {
                "Февраль 1955",
                "Месяц и год выпуска",
                "Цепочка",
                "Красный",
                "Джерси",
                "Mademoiselle",
                "Улыбка Моны Лизы",
                "Детство в приюте",
                "Форма Вандомской площади",
                "Ritz",
                "Площадь Вандом",
                "Чёрный",
                "1987",
                "Эрнест Бо",
                "Форма флакона"
        };

        for (int i = 0; i < questions.length; i++) {
            Map<String, Object> questionData = new HashMap<>();
            questionData.put("question", questions[i]);
            questionData.put("choices", Arrays.asList(choices[i]));
            questionData.put("correctAnswer", correctAnswers[i]);
            questionData.put("index", i);

            db.collection("quizzes")
                    .document("chanel")
                    .collection("questions")
                    .document(String.valueOf(i))
                    .set(questionData);
        }
    }

    private void initializeDiorQuiz() {
        String[] questions = {
                "Кто был основателем модного дома Dior?",
                "Какой элемент вдохновил на создание узора канаж?",
                "Сколько моделей участвовало в показах Кристиана Диора из-за его суеверий?",
                "Какое число играло мистическую роль в жизни Диора?",
                "Кто был дизайнером культовой сумки Lady Dior?",
                "Почему буквы D-I-O-R на брелоке сумки имеют значение?",
                "Каким цветком особенно дорожил Кристиан Диор?",
                "С каким символом ассоциируется цифра 8 в символике Диора?",
                "Какое мистическое увлечение особенно выделялось у Диора?",
                "Как называется техника плетения, использованная в узоре канаж?",
                "В честь кого была названа сумка Lady Dior?",
                "Где располагался бутик Dior, в котором стоял вдохновивший узор канаж стул?",
                "Что символизирует деревянная фигурка на брелоке Dior?",
                "Какая фигура в Таро, по слухам, вдохновляла Диора?",
                "Какое животное считается одним из символов удачи Dior?"
        };

        String[][] choices = {
                {"Кристиан Диор", "Ив Сен-Лоран", "Жан-Поль Готье", "Карл Лагерфельд"},
                {"Шляпа Диора", "Ваза с лилиями", "Венский стул XVIII века", "Французское окно"},
                {"13", "7", "10", "8"},
                {"8", "3", "13", "5"},
                {"Жан-Франко Ферре", "Габриэль Шанель", "Кристиан Диор", "Раф Симонс"},
                {"Это просто декор", "Они отсылают к имени Dior", "Это амулеты", "Это инициалы модели"},
                {"Роза", "Ландыш", "Орхидея", "Пион"},
                {"Начало", "Вечность", "Судьба", "Тайна"},
                {"Магия", "Гадания", "Астрология", "Все перечисленное"},
                {"Канаж", "Шеврон", "Лозанж", "Кашемир"},
                {"Принцесса Диана", "Мать Диора", "Модель Катрин", "Грейс Келли"},
                {"Версаль", "Париж", "Милан", "Рим"},
                {"Древо жизни", "Удачу (постучать по дереву)", "Бесконечность", "Стойкость"},
                {"Императрица", "Жрица", "Звезда", "Колесо Фортуны"},
                {"Слон", "Птица", "Жаба", "Пчела"}
        };

        String[] correctAnswers = {
                "Кристиан Диор",
                "Венский стул XVIII века",
                "13",
                "8",
                "Жан-Франко Ферре",
                "Это амулеты",
                "Ландыш",
                "Вечность",
                "Все перечисленное",
                "Канаж",
                "Принцесса Диана",
                "Париж",
                "Удачу (постучать по дереву)",
                "Звезда",
                "Пчела"
        };

        for (int i = 0; i < questions.length; i++) {
            Map<String, Object> questionData = new HashMap<>();
            questionData.put("question", questions[i]);
            questionData.put("choices", Arrays.asList(choices[i]));
            questionData.put("correctAnswer", correctAnswers[i]);
            questionData.put("index", i);

            db.collection("quizzes")
                    .document("dior")
                    .collection("questions")
                    .document(String.valueOf(i))
                    .set(questionData);
        }
    }

    private void initializeValentinoQuiz() {
        String[] questions = {
                "Чем вдохновлялся Валентино при создании шипов в коллекции «Rockstud»?",
                "Какой цвет стал визитной карточкой Валентино?",
                "Кому Валентино создал свадебное платье в 1968 году?",
                "С каким ювелиром Валентино имел тесную дружбу?",
                "Кто из знаменитостей была верной поклонницей Валентино?",
                "Что символизирует цвет Valentino Red?",
                "Какой дворец вдохновил Валентино на коллекцию с шипами?",
                "Какой стиль наиболее точно описывает эстетику бренда Valentino?",
                "Что, по словам Валентино, выражает красный цвет?",
                "Какой из этих элементов характерен для коллекции Rockstud?",
                "Кто основал модный дом Valentino?",
                "Где был основан бренд Valentino?",
                "Какой актрисе часто шили наряды Valentino?",
                "Какое изделие Valentino стало символом скрытой роскоши?",
                "В каком десятилетии Валентино достиг международной славы?"
        };

        String[][] choices = {
                {"Архитектурой Палаццо Диаманти", "Рок-культурой", "Средневековыми доспехами", "Панком 80-х"},
                {"Красный", "Чёрный", "Белый", "Золотой"},
                {"Жаклин Кеннеди", "Грейс Келли", "Одри Хепбёрн", "Принцесса Диана"},
                {"Гарри Уинстоном", "Карл Лагерфельдом", "Пьером Карденом", "Джорджио Армани"},
                {"Элизабет Тейлор", "Мэрилин Монро", "Мадонна", "Леди Гага"},
                {"Страсть и элегантность", "Грусть и утончённость", "Свободу и дикость", "Минимализм"},
                {"Палаццо Диаманти", "Палаццо Веккьо", "Вилла Боргезе", "Колизей"},
                {"Романтический и элегантный", "Готический и агрессивный", "Гранж и спорт-шик", "Минималистичный и урбанистичный"},
                {"Мою страсть к моде", "Скорость современной жизни", "Тайну и интригу", "Ностальгию по детству"},
                {"Пирамидальные шипы", "Цветы", "Кожа", "Бархат"},
                {"Валентино Гаравани", "Джорджо Армани", "Джанни Версаче", "Александр Маккуин"},
                {"Рим", "Милан", "Париж", "Флоренция"},
                {"Элизабет Тейлор", "Кейт Мосс", "Бьорк", "Моника Беллуччи"},
                {"Туфли Rockstud", "Шляпы", "Очки", "Перчатки"},
                {"1960-е", "1980-е", "2000-е", "1970-е"}
        };

        String[] correctAnswers = {
                "Архитектурой Палаццо Диаманти",
                "Красный",
                "Жаклин Кеннеди",
                "Гарри Уинстоном",
                "Элизабет Тейлор",
                "Страсть и элегантность",
                "Палаццо Диаманти",
                "Романтический и элегантный",
                "Мою страсть к моде",
                "Пирамидальные шипы",
                "Валентино Гаравани",
                "Рим",
                "Элизабет Тейлор",
                "Туфли Rockstud",
                "1960-е"
        };

        for (int i = 0; i < questions.length; i++) {
            Map<String, Object> questionData = new HashMap<>();
            questionData.put("question", questions[i]);
            questionData.put("choices", Arrays.asList(choices[i]));
            questionData.put("correctAnswer", correctAnswers[i]);
            questionData.put("index", i);

            db.collection("quizzes")
                    .document("valentino")
                    .collection("questions")
                    .document(String.valueOf(i))
                    .set(questionData);
        }
    }

    private void initializeYvesSaintLaurentQuiz() {
        String[] questions = {
                "Кто основал модный дом Yves Saint Laurent?",
                "Какой стиль популяризовал Yves Saint Laurent в 1960-х?",
                "Как называется знаменитый женский смокинг от YSL?"
        };

        String[][] choices = {
                {"Yves Saint Laurent", "Christian Dior", "Pierre Cardin", "Hubert de Givenchy"},
                {"Сафари", "Бохо", "Андрогинный", "Готический"},
                {"Le Smoking", "Tuxedo Chic", "Black Tie", "Formal Femme"}
        };

        String[] correctAnswers = {
                "Yves Saint Laurent",
                "Андрогинный",
                "Le Smoking"
        };

        for (int i = 0; i < questions.length; i++) {
            Map<String, Object> questionData = new HashMap<>();
            questionData.put("question", questions[i]);
            questionData.put("choices", Arrays.asList(choices[i]));
            questionData.put("correctAnswer", correctAnswers[i]);
            questionData.put("index", i);

            db.collection("quizzes")
                    .document("yves_saint_laurent")
                    .collection("questions")
                    .document(String.valueOf(i))
                    .set(questionData);
        }
    }
}