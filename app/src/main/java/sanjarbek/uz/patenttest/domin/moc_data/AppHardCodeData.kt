package sanjarbek.uz.patenttest.domin.moc_data

import sanjarbek.uz.patenttest.R
import sanjarbek.uz.patenttest.domin.models.QuestionModel
import sanjarbek.uz.patenttest.domin.models.QuestionType
import sanjarbek.uz.patenttest.domin.models.VariantModel

object AppHardCodeData {

    private val firstTestList = listOf(
        QuestionModel(
            questionId = 1,
            questionType = QuestionType.AUDIO,
            questionTitle = "Прослушайте диалог и определите, где происходит разговор. Выберите правильный ответ",
            questionMedia = R.raw.v1_1,
            questionDescription = "Они говорят",
            answersString = listOf(
                "в магазине",
                "в автомастерской",
                "в центре тестирования"
            ),
            correctAnswerPosition = 2,
            selectedAnswerPosition = -1,
            isAnswerMarked = false
        ),
        QuestionModel(
            questionId = 2,
            questionType = QuestionType.AUDIO,
            questionTitle = "Прослушайте объявление и выберите правильный ответ.",
            questionMedia = R.raw.v1_2,
            questionDescription = "Спросите о потерянных вещах",
            answersString = listOf(
                "у покупателей",
                "у администратора",
                "у директора магазина"
            ),
            correctAnswerPosition = 1,
            selectedAnswerPosition = -1,
            isAnswerMarked = false
        ),
        QuestionModel(
            questionId = 3,
            questionType = QuestionType.TEXT,
            questionTitle = "Прочитайте объявление и выберите правильный ответ.",
            question = "Уважаемые граждане!\n" +
                    "Оплачивайте свой проезд вовремя!",
            questionDescription = "Такое объявление можно прочитать",
            answersString = listOf(
                "в парке",
                "в автобусе",
                "в самолёте"
            ),
            correctAnswerPosition = 1,
            selectedAnswerPosition = -1,
            isAnswerMarked = false
        ),
        QuestionModel(
            questionId = 4,
            questionType = QuestionType.TEXT,
            questionTitle = "Прочитайте текст и выберите правильный ответ",
            question = "Уважаемые гости!\n" +
                    "Приглашаем Вас на автобусную экскурсию по городу.\n" +
                    "Экскурсия будет 12 мая (во вторник) с 10 до 14 часов.\n" +
                    "Ждём Вас около торгового центра «Мир».",
            questionDescription = "Экскурсия начинается",
            answersString = listOf(
                "в 10 часов",
                "в 12 часов",
                "в 14 часов"
            ),
            correctAnswerPosition = 0,
            selectedAnswerPosition = -1,
            isAnswerMarked = false
        ),
        QuestionModel(
            questionId = 5,
            questionType = QuestionType.INPUT,
            questionTitle = "Прочитайте текст и вставьте пропущенное слово.",
            question = "Батыр приехал из Узбекистана. Батыр – строитель.",
            questionDescription = "Впишите нужное слово в анкету.\n" +
                    "Анкета",
            inputQuestions = listOf(
                "1. Как Вас зовут?",
                "2. Откуда Вы приехали?",
                "3. Кто Вы по профессии?"
            ),
            inputAnswers = listOf(
                "Меня зовут Батыр.",
                "Я приехал из Узбекистана.",
                "Я"
            ),
            answerInput = "строитель",
            isAnswerInputCorrect = false,
            isAnswerMarked = false
        ),
        QuestionModel(
            questionId = 6,
            questionType = QuestionType.TEXT,
            question = "Сегодня суббота, а мама приехала вчера, ____________________.",
            answersString = listOf(
                "в среду",
                "в пятницу",
                "во вторник"
            ),
            correctAnswerPosition = 1,
            selectedAnswerPosition = -1,
            isAnswerMarked = false
        ),
        QuestionModel(
            questionId = 7,
            questionType = QuestionType.TEXT,
            question = "В салоне сотовой связи я купил __________.",
            answersString = listOf(
                "молоко",
                "куртку",
                "телефон"
            ),
            correctAnswerPosition = 2,
            selectedAnswerPosition = -1,
            isAnswerMarked = false
        ),
        QuestionModel(
            questionId = 8,
            questionType = QuestionType.TEXT,
            question = "– Как вас зовут?\n" +
                    "– _______________ зовут Иван.",
            answersString = listOf(
                "Мне",
                "Меня",
                "Я"
            ),
            correctAnswerPosition = 1,
            selectedAnswerPosition = -1,
            isAnswerMarked = false
        ),
        QuestionModel(
            questionId = 9,
            questionType = QuestionType.TEXT,
            question = "Мы приехали в Россию, __________ здесь есть работа.",
            answersString = listOf(
                "потому что",
                "поэтому",
                "чтобы"
            ),
            correctAnswerPosition = 0,
            selectedAnswerPosition = -1,
            isAnswerMarked = false
        ),
        QuestionModel(
            questionId = 10,
            questionType = QuestionType.TEXT,
            question = "День Победы в России отмечают",
            answersString = listOf(
                "1 января",
                "9 мая",
                "4 ноября"
            ),
            correctAnswerPosition = 1,
            selectedAnswerPosition = -1,
            isAnswerMarked = false
        ),
        QuestionModel(
            questionId = 11,
            questionType = QuestionType.TEXT,
            question = "Столица Российской Федерации - __________.",
            answersString = listOf(
                "Москва",
                "Екатеринбург",
                "Самара"
            ),
            correctAnswerPosition = 0,
            selectedAnswerPosition = -1,
            isAnswerMarked = false
        ),

        QuestionModel(
            questionId = 12,
            questionType = QuestionType.TEXT,
            question = "Великая Отечественная война закончилась ______________.",
            answersString = listOf(
                "в 1917 г.",
                "в 1945 г.",
                "в 1980 г."
            ),
            correctAnswerPosition = 1,
            selectedAnswerPosition = -1,
            isAnswerMarked = false
        ),

        QuestionModel(
            questionId = 13,
            questionType = QuestionType.TEXT,
            question = "Содружество Независимых Государств (СНГ) было образовано после",
            answersString = listOf(
                "распада СССР в 1991 г.",
                "Великой Отечественной войны в 1941–1945 гг.",
                "Великой российской революции 1917–1922 гг."
            ),
            correctAnswerPosition = 0,
            selectedAnswerPosition = -1,
            isAnswerMarked = false
        ),
        QuestionModel(
            questionId = 14,
            questionType = QuestionType.IMAGE,
            question = "Укажите флаг Российской Федерации.",
            answersImages = listOf(
                R.drawable.v1_a1,
                R.drawable.v1_a2,
                R.drawable.v1_a3
            ),
            correctAnswerPosition = 0,
            selectedAnswerPosition = -1,
            isAnswerMarked = false
        ),

        QuestionModel(
            questionId = 15,
            questionType = QuestionType.TEXT,
            question = "Статья 62 Конституции Российской Федерации определяет статус иностранных граждан на территории Российской Федерации. Согласно этой статье",
            answersString = listOf(
                "иностранные граждане пользуются всеми правами и несут обязанности наравне с гражданами Российской Федерации",
                "иностранные граждане пользуются правами и несут обязанности наравне с гражданами Российской Федерации, кроме случаев, установленных федеральным законом или международным договором Российской Федерации",
                "иностранные граждане не обладают никакими правами на территории Российской Федерации"
            ),
            correctAnswerPosition = 1,
            selectedAnswerPosition = -1,
            isAnswerMarked = false
        ),

        QuestionModel(
            questionId = 16,
            questionType = QuestionType.TEXT,
            question = "Иностранный гражданин в Российской Федерации обязан",
            answersString = listOf(
                "однократно пройти дактилоскопическую регистрацию и фотографирование при получении разрешения на работу",
                "участвовать в выборах органов власти",
                "уметь ориентироваться на территории субъекта Российской Федерации"
            ),
            correctAnswerPosition = 0,
            selectedAnswerPosition = -1,
            isAnswerMarked = false
        ),

        QuestionModel(
            questionId = 17,
            questionType = QuestionType.TEXT,
            question = "На территории Российской Федерации в отношении иностранного гражданина совершено преступление. Куда он должен обратиться за помощью?",
            answersString = listOf(
                "в полицию",
                "в Министерство иностранных дел Российской Федерации",
                "в посольство своей страны"
            ),
            correctAnswerPosition = 0,
            selectedAnswerPosition = -1,
            isAnswerMarked = false
        ),

        QuestionModel(
            questionId = 18,
            questionType = QuestionType.TEXT,
            question = "Для постановки на учёт по месту пребывания иностранный гражданин должен иметь",
            answersString = listOf(
                "только миграционную карту",
                "только паспорт",
                "паспорт и миграционную карту"
            ),
            correctAnswerPosition = 2,
            selectedAnswerPosition = -1,
            isAnswerMarked = false
        ),

        QuestionModel(
            questionId = 19,
            questionType = QuestionType.TEXT,
            question = "Если иностранный гражданин нарушил правила постановки на миграционный учёт, то он привлекается к ____________ ответственности",
            answersString = listOf(
                "дисциплинарной",
                "уголовной",
                "административной"
            ),
            correctAnswerPosition = 2,
            selectedAnswerPosition = -1,
            isAnswerMarked = false
        ),

        QuestionModel(
            questionId = 20,
            questionType = QuestionType.TEXT,
            question = "Административное выдворение за пределы Российской Федерации как мера административного наказания устанавливается в отношении иностранных граждан и назначается судьёй.\nВерно ли это утверждение?",
            answersString = listOf(
                "верно",
                "неверно"
            ),
            correctAnswerPosition = 0,
            selectedAnswerPosition = -1,
            isAnswerMarked = false
        )
    )
    val firstVariant= VariantModel(
        variantNumber = "Вариант 1",
        questionModel = firstTestList,
    )
}