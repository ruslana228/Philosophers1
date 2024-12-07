import kotlin.random.Random

enum class PhilosopherState {
    THINKING, EATING
}

data class Philosopher(
    val name: String,
    var state: PhilosopherState = PhilosopherState.THINKING,
    var leftChopstick: Int? = null,
    var rightChopstick: Int? = null
)

fun main() {
    // Функция для безопасного ввода количества философов
    fun getNumberOfPhilosophers(): Int {
        while (true) {
            println("Введите количество философов: ")
            val input = readLine()
            val number = input?.toIntOrNull()
            if (number != null && number > 0) {
                return number
            } else {
                println("Введите корректное положительное число.")
            }
        }
    }

    // Получаем количество философов
    val numberOfPhilosophers = getNumberOfPhilosophers()

    // Ввод имен философов
    val philosophers = mutableListOf<Philosopher>()
    repeat(numberOfPhilosophers) { index ->
        println("Введите имя философа ${index + 1}: ")
        val name = readLine()?.trim().takeUnless { it.isNullOrEmpty() } ?: "Философ_$index"
        philosophers.add(Philosopher(name))
    }

    // Инициализация палочек
    val chopsticks = MutableList(numberOfPhilosophers) { true }

    // Перемешиваем философов для случайного порядка
    philosophers.shuffle()

    // Попытка философов взять палочки
    for (philosopher in philosophers) {
        val index = philosophers.indexOf(philosopher)
        val left = index
        val right = (index + 1) % numberOfPhilosophers

        if (chopsticks[left] && chopsticks[right]) {
            chopsticks[left] = false
            chopsticks[right] = false
            philosopher.state = PhilosopherState.EATING
            philosopher.leftChopstick = left
            philosopher.rightChopstick = right
        }
    }

    // Вывод результатов
    println("\nРезультаты:")
    for (philosopher in philosophers) {
        if (philosopher.state == PhilosopherState.EATING) {
            println("${philosopher.name} обедает, взяв палочки слева и справа от себя.")
        } else {
            println("${philosopher.name} размышляет.")
        }
    }
}
