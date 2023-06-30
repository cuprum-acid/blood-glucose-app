package com.example.databaseinterface

//import java.util.*
import com.example.mytestapp.Tuple6
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.time.LocalDate
import java.time.LocalTime
import kotlin.random.Random

class Database private constructor(private val dbName: String) {
    private var connection: Connection? = null

    init {
        connect()
    }

    companion object {
        @Volatile
        private var INSTANCE: Database? = null

        fun getInstance(dbName: String): Database {
            return INSTANCE ?: synchronized(this) {
                val instance = Database(dbName)
                INSTANCE = instance
                instance
            }
        }
    }

    private fun connect() {
        try {
            Class.forName("org.sqlite.JDBC")
            connection = DriverManager.getConnection("jdbc:sqlite:$dbName")
        } catch (e: ClassNotFoundException) {
            println("SQLite JDBC driver not found")
            e.printStackTrace()
        } catch (e: SQLException) {
            println("Error while getting connection")
            e.printStackTrace()
        }
    }

    // a function for receiving a array of all foods.
    // The foods are sorted according to its category and lexicographical order.
    // The decoding of Tuple6 elements: name, category_name, calories, proteins, fats, carbohydrates
    fun GetAllFoods(): List<Tuple6<String, String, Int, Int, Int, Int>> {
        // SQL requests
        val requestForFoodCategories = "SELECT * FROM food_categories"
        val requestForAllFood = "SELECT * FROM foods"

        val foodArray = mutableListOf<Tuple6<String, String, Int, Int, Int, Int>>()
        val foodCategories = HashMap<Int, String>()


        try {
            connection?.createStatement().use { stmt ->

                // first get know about food categories cipher
                stmt?.executeQuery(requestForFoodCategories).use { rs ->
                    while (rs?.next() == true) {
                        foodCategories[rs.getInt("id")] = rs.getString("name")
                    }
                }

                // then read foods
                stmt?.executeQuery(requestForAllFood).use { rs ->
                    while (rs?.next() == true) {
                        val foodItem = Tuple6<String, String, Int, Int, Int, Int>(
                            rs.getString("name"),
                            foodCategories[rs.getInt("category_id")],
                            rs.getInt("calories"),
                            rs.getInt("proteins"),
                            rs.getInt("fats"),
                            rs.getInt("carbohydrates")
                        )
                        foodArray.add(foodItem)
                    }
                }

            }
        } catch (e: SQLException) {
            println("Error while working with database")
            e.printStackTrace()
        }
        return foodArray
    }


    fun GetFoodsByCategory(categoryName: String): List<Tuple6<String, String, Int, Int, Int, Int>> {
        // SQL requests
        val requestForFoodCategories = "SELECT * FROM food_categories WHERE name = ?"
        val requestForFoodsByCategory = "SELECT * FROM foods WHERE category_id = ?"

        val foodArray = mutableListOf<Tuple6<String, String, Int, Int, Int, Int>>()
        var categoryId: Int? = null

        try {
            connection?.prepareStatement(requestForFoodCategories).use { stmt ->
                stmt?.setString(1, categoryName)
                stmt?.executeQuery().use { rs ->
                    if (rs?.next() == true) {
                        categoryId = rs.getInt("id")
                    }
                }
            }

            categoryId?.let {
                connection?.prepareStatement(requestForFoodsByCategory).use { stmt ->
                    stmt?.setInt(1, it)
                    stmt?.executeQuery().use { rs ->
                        while (rs?.next() == true) {
                            val foodItem = Tuple6<String, String, Int, Int, Int, Int>(
                                rs.getString("name"),
                                categoryName,
                                rs.getInt("calories"),
                                rs.getInt("proteins"),
                                rs.getInt("fats"),
                                rs.getInt("carbohydrates")
                            )
                            foodArray.add(foodItem)
                        }
                    }
                }
            }
        } catch (e: SQLException) {
            println("Error while working with database")
            e.printStackTrace()
        }
        return foodArray
    }


    fun GetAllFoodCategories(): List<String> {
        // SQL request
        val requestForFoodCategories = "SELECT name FROM food_categories"

        val categoriesArray = mutableListOf<String>()

        try {
            connection?.createStatement().use { stmt ->
                stmt?.executeQuery(requestForFoodCategories).use { rs ->
                    while (rs?.next() == true) {
                        categoriesArray.add(rs.getString("name"))
                    }
                }
            }
        } catch (e: SQLException) {
            println("Error while working with database")
            e.printStackTrace()
        }
        return categoriesArray
    }


    fun insertSomeFood(
        name: String, category_name: String, calories: Int = 0,
        proteins: Int = 0, fats: Int = 0, carbohydrates: Int = 0
    ) {

        val requestForFoodCategories = "SELECT * FROM food_categories"

        val foodCategories = HashMap<String, Int>()

        try {
            connection?.createStatement().use { stmt ->

                // first get know about food categories cipher
                stmt?.executeQuery(requestForFoodCategories).use { rs ->
                    while (rs?.next() == true) {
                        foodCategories[rs.getString("name")] = rs.getInt("id")
                    }
                }

                val foodCategoryId: Int
                if (foodCategories.containsKey(category_name)) {
                    // if desired category of food already exists:
                    foodCategoryId = foodCategories[category_name]!!

                } else {
                    // in this case we should first create desired category

                    val requestForInsertingNewFoodCategory =
                        "INSERT INTO food_categories(name) VALUES(?)"

                    // insert new category into food_categories
                    connection?.prepareStatement(requestForInsertingNewFoodCategory).use { pstmt ->
                        pstmt?.setString(1, category_name)
                        pstmt?.executeUpdate()
                    }

                    // and receive its id
                    val requestForIdSearchingOfNewFoodCategory =
                        "SELECT id FROM food_categories WHERE name = ?"

                    connection?.prepareStatement(requestForIdSearchingOfNewFoodCategory)
                        .use { pstmt ->
                            pstmt?.setString(1, category_name)
                            pstmt?.executeQuery().use { rs ->
                                rs?.next()
                                foodCategoryId = rs?.getInt("id")!!
                            }
                        }
                }

                val requestForInsertingNewFood =
                    "INSERT INTO foods(name, category_id, " +
                            "calories, proteins, fats, carbohydrates) VALUES(?,?,?,?,?,?)"

                connection?.prepareStatement(requestForInsertingNewFood).use { pstmt ->
                    pstmt?.setString(1, name)
                    pstmt?.setInt(2, foodCategoryId)
                    pstmt?.setInt(3, calories)
                    pstmt?.setInt(4, proteins)
                    pstmt?.setInt(5, fats)
                    pstmt?.setInt(6, carbohydrates)
                    pstmt?.executeUpdate()
                }
            }
        } catch (e: SQLException) {
            println("Error while working with database")
            e.printStackTrace()
        }

    }


    fun GetAllMedicineCategories(): List<String> {
        // SQL request
        val requestForMedicineCategories = "SELECT name FROM medicine_categories"

        val categoriesArray = mutableListOf<String>()

        try {
            connection?.createStatement().use { stmt ->
                stmt?.executeQuery(requestForMedicineCategories).use { rs ->
                    while (rs?.next() == true) {
                        categoriesArray.add(rs.getString("name"))
                    }
                }
            }
        } catch (e: SQLException) {
            println("Error while working with database")
            e.printStackTrace()
        }
        return categoriesArray
    }


    fun GetMedicationsByCategory(categoryName: String): List<Triple<String, String, Int>> {
        // SQL requests
        val requestForMedicineCategories = "SELECT * FROM medicine_categories WHERE name = ?"
        val requestForMedicationsByCategory = "SELECT * FROM medications WHERE category_id = ?"

        val medicationArray = mutableListOf<Triple<String, String, Int>>()
        var categoryId: Int? = null

        try {
            connection?.prepareStatement(requestForMedicineCategories).use { stmt ->
                stmt?.setString(1, categoryName)
                stmt?.executeQuery().use { rs ->
                    if (rs?.next() == true) {
                        categoryId = rs.getInt("id")
                    }
                }
            }

            categoryId?.let {
                connection?.prepareStatement(requestForMedicationsByCategory).use { stmt ->
                    stmt?.setInt(1, it)
                    stmt?.executeQuery().use { rs ->
                        while (rs?.next() == true) {
                            val medicationItem = Triple<String, String, Int>(
                                rs.getString("name"),
                                categoryName,
                                rs.getInt("id")
                            )
                            medicationArray.add(medicationItem)
                        }
                    }
                }
            }
        } catch (e: SQLException) {
            println("Error while working with database")
            e.printStackTrace()
        }
        return medicationArray
    }


    fun insertSomeMedication(name: String, category_name: String) {

        val requestForMedicineCategories = "SELECT * FROM medicine_categories"

        val medicineCategories = HashMap<String, Int>()

        try {
            connection?.createStatement().use { stmt ->

                // first get know about medicine categories cipher
                stmt?.executeQuery(requestForMedicineCategories).use { rs ->
                    while (rs?.next() == true) {
                        medicineCategories[rs.getString("name")] = rs.getInt("id")
                    }
                }

                val medicineCategoryId: Int
                if (medicineCategories.containsKey(category_name)) {
                    // if desired category of medicine already exists:
                    medicineCategoryId = medicineCategories[category_name]!!

                } else {
                    // in this case we should first create desired category

                    val requestForInsertingNewMedicineCategory =
                        "INSERT INTO medicine_categories(name) VALUES(?)"

                    // insert new category into medicine_categories
                    connection?.prepareStatement(requestForInsertingNewMedicineCategory)
                        .use { pstmt ->
                            pstmt?.setString(1, category_name)
                            pstmt?.executeUpdate()
                        }

                    // and receive its id
                    val requestForIdSearchingOfNewMedicineCategory =
                        "SELECT id FROM medicine_categories WHERE name = ?"

                    connection?.prepareStatement(requestForIdSearchingOfNewMedicineCategory)
                        .use { pstmt ->
                            pstmt?.setString(1, category_name)
                            pstmt?.executeQuery().use { rs ->
                                rs?.next()
                                medicineCategoryId = rs?.getInt("id")!!
                            }
                        }
                }

                val requestForInsertingNewMedicine =
                    "INSERT INTO medications(name, category_id) VALUES(?,?)"

                connection?.prepareStatement(requestForInsertingNewMedicine).use { pstmt ->
                    pstmt?.setString(1, name)
                    pstmt?.setInt(2, medicineCategoryId)
                    pstmt?.executeUpdate()
                }
            }
        } catch (e: SQLException) {
            println("Error while working with database")
            e.printStackTrace()
        }

    }

    fun getFoodsEatenToday(): List<String> {
        // SQL request

        val requestForFoodsEatenToday =
            "SELECT foods.name FROM foods INNER JOIN daily_food ON foods.id = daily_food.food_id"

        val foodsArray = mutableListOf<String>()

        try {
            connection?.prepareStatement(requestForFoodsEatenToday).use { stmt ->
                stmt?.executeQuery().use { rs ->
                    while (rs?.next() == true) {
                        foodsArray.add(rs.getString("name"))
                    }
                }
            }
        } catch (e: SQLException) {
            println("Error while working with database")
            e.printStackTrace()
        }
        return foodsArray
    }


    fun addFoodEatenToday(foodName: String) {
        // SQL request
        val getIdForFood =
            "SELECT id FROM foods WHERE name = ?"

        val insertIntoDailyFood =
            "INSERT INTO daily_food (food_id) VALUES (?)"

        try {
            connection?.prepareStatement(getIdForFood).use { stmt ->
                stmt?.setString(1, foodName)
                stmt?.executeQuery().use { rs ->
                    if (rs?.next() == true) {
                        val foodId = rs.getInt("id")
                        connection?.prepareStatement(insertIntoDailyFood).use { insertStmt ->
                            insertStmt?.setInt(1, foodId)
                            insertStmt?.executeUpdate()
                        }
                    } else {
                        println("No food found with the given name: $foodName")
                        throw SQLException()
                    }
                }
            }
        } catch (e: SQLException) {
            println("Error while working with database")
            e.printStackTrace()
        }
    }


    fun getExercisesDoneToday(): List<String> {
        // SQL request
        val requestForExercisesDoneToday =
            "SELECT exercises.name FROM exercises INNER JOIN daily_exercises ON exercises.id = daily_exercises.exercise_id"

        val exercisesArray = mutableListOf<String>()

        try {
            connection?.prepareStatement(requestForExercisesDoneToday).use { stmt ->
                stmt?.executeQuery().use { rs ->
                    while (rs?.next() == true) {
                        exercisesArray.add(rs.getString("name"))
                    }
                }
            }
        } catch (e: SQLException) {
            println("Error while working with database")
            e.printStackTrace()
        }
        return exercisesArray
    }


    fun addExerciseDoneToday(exerciseName: String) {
        // SQL request
        val getIdForExercise =
            "SELECT id FROM exercises WHERE name = ?"

        val insertIntoDailyExercises =
            "INSERT INTO daily_exercises (exercise_id) VALUES (?)"

        try {
            connection?.prepareStatement(getIdForExercise).use { stmt ->
                stmt?.setString(1, exerciseName)
                stmt?.executeQuery().use { rs ->
                    if (rs?.next() == true) {
                        val exerciseId = rs.getInt("id")
                        connection?.prepareStatement(insertIntoDailyExercises).use { insertStmt ->
                            insertStmt?.setInt(1, exerciseId)
                            insertStmt?.executeUpdate()
                        }
                    } else {
                        println("No exercise found with the given name: $exerciseName")
                        throw SQLException()
                    }
                }
            }
        } catch (e: SQLException) {
            println("Error while working with database")
            e.printStackTrace()
        }
    }

    fun getMedicationDoneToday(): List<String> {
        // SQL request
        val requestForMedicationDoneToday =
            "SELECT medications.name FROM medications INNER JOIN daily_medications ON medications.id = daily_medications.medication_id"

        val medicationArray = mutableListOf<String>()

        try {
            connection?.prepareStatement(requestForMedicationDoneToday).use { stmt ->
                stmt?.executeQuery().use { rs ->
                    while (rs?.next() == true) {
                        medicationArray.add(rs.getString("name"))
                    }
                }
            }
        } catch (e: SQLException) {
            println("Error while working with database")
            e.printStackTrace()
        }
        return medicationArray
    }


    fun addMedicationDoneToday(medicationName: String) {
        // SQL request
        val getIdForMedication =
            "SELECT id FROM medications WHERE name = ?"

        val insertIntoDailyMedications =
            "INSERT INTO daily_medications (medication_id) VALUES (?)"

        try {
            connection?.prepareStatement(getIdForMedication).use { stmt ->
                stmt?.setString(1, medicationName)
                stmt?.executeQuery().use { rs ->
                    if (rs?.next() == true) {
                        val exerciseId = rs.getInt("id")
                        connection?.prepareStatement(insertIntoDailyMedications).use { insertStmt ->
                            insertStmt?.setInt(1, exerciseId)
                            insertStmt?.executeUpdate()
                        }
                    } else {
                        println("No medication found with the given name: $medicationName")
                        throw SQLException()
                    }
                }
            }
        } catch (e: SQLException) {
            println("Error while working with database")
            e.printStackTrace()
        }
    }


    fun getAllExerciseCategories(): List<String> {
        val requestForExCategories = "SELECT name FROM exercise_categories"
        val categoriesArray = mutableListOf<String>()
        try {
            connection?.createStatement().use { stmt ->
                stmt?.executeQuery(requestForExCategories).use { rs ->
                    while (rs?.next() == true) {
                        categoriesArray.add(rs.getString("name"))
                    }
                }
            }

        } catch (e: SQLException) {
            println("Error while working with database")
            e.printStackTrace()
        }


        return categoriesArray
    }

    fun GetExercisesByCategory(categoryName: String): List<Triple<String, Int, String>> {
        // SQL requests
        val requestForExerciseCategories = "SELECT * FROM exercise_categories WHERE name = ?"
        val requestForExercisesByCategory = "SELECT * FROM exercises WHERE exercise_category_id = ?"

        val exerciseArray = mutableListOf<Triple<String, Int, String>>()
        var categoryId: Int? = null

        try {
            connection?.prepareStatement(requestForExerciseCategories).use { stmt ->
                stmt?.setString(1, categoryName)
                stmt?.executeQuery().use { rs ->
                    if (rs?.next() == true) {
                        categoryId = rs.getInt("id")
                    }
                }
            }

            categoryId?.let {
                connection?.prepareStatement(requestForExercisesByCategory).use { stmt ->
                    stmt?.setInt(1, it)
                    stmt?.executeQuery().use { rs ->
                        while (rs?.next() == true) {
                            val exerciseItem = Triple<String, Int, String>(
                                rs.getString("name"),
                                rs.getInt("calories"),
                                categoryName

                            )
                            exerciseArray.add(exerciseItem)
                        }
                    }
                }
            }
        } catch (e: SQLException) {
            println("Error while working with database")
            e.printStackTrace()
        }
        return exerciseArray
    }

    fun insertSomeExercise(name: String, exercise_category_name: String, calories: Int) {

        val requestForExerciseCategories = "SELECT * FROM exercise_categories"

        val exerciseCategories = HashMap<String, Int>()

        try {
            connection?.createStatement().use { stmt ->


                stmt?.executeQuery(requestForExerciseCategories).use { rs ->
                    while (rs?.next() == true) {
                        exerciseCategories[rs.getString("name")] = rs.getInt("id")
                    }
                }

                val exerciseCategoryId: Int
                if (exerciseCategories.containsKey(exercise_category_name)) {
                    // if desired category of exercise already exists:
                    exerciseCategoryId = exerciseCategories[exercise_category_name]!!

                } else {
                    // in this case we should first create desired category

                    val requestForInsertingNewExerciseCategory =
                        "INSERT INTO exercise_categories(name) VALUES(?)"

                    // insert new category into exercise_categories
                    connection?.prepareStatement(requestForInsertingNewExerciseCategory)
                        .use { pstmt ->
                            pstmt?.setString(1, exercise_category_name)
                            pstmt?.executeUpdate()
                        }

                    // and receive its id
                    val requestForIdSearchingOfNewExerciseCategory =
                        "SELECT id FROM exercise_categories WHERE name = ?"

                    connection?.prepareStatement(requestForIdSearchingOfNewExerciseCategory)
                        .use { pstmt ->
                            pstmt?.setString(1, exercise_category_name)
                            pstmt?.executeQuery().use { rs ->
                                rs?.next()
                                exerciseCategoryId = rs?.getInt("id")!!
                            }
                        }
                }

                val requestForInsertingNewExercise =
                    "INSERT INTO exercises(name, calories, exercise_category_id) VALUES(?,?, ?)"

                connection?.prepareStatement(requestForInsertingNewExercise).use { pstmt ->
                    pstmt?.setString(1, name)
                    pstmt?.setInt(2, calories)
                    pstmt?.setInt(3, exerciseCategoryId)
                    pstmt?.executeUpdate()
                }
            }
        } catch (e: SQLException) {
            println("Error while working with database")
            e.printStackTrace()
        }
    }

    fun getTipImage(id: Int): String? {
        val requestForTip = "SELECT tip_image FROM tips WHERE id = ?"
        var tipImage: String? = null
        try {
            connection?.prepareStatement(requestForTip).use { ps ->
                ps?.setInt(1, id)
                val rs = ps?.executeQuery()
                if (rs?.next() == true) {
                    tipImage = rs?.getString("tip_image")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return tipImage

    }

    fun getDailyTip(): Triple<Int, String, String> {
        var dailyTip: Triple<Int, String, String> = Triple(0, "", "")
        val requestForLastRow = "SELECT id FROM tips ORDER BY id DESC LIMIT 1"
        var maxID: Int = 1
        try {
            connection?.prepareStatement(requestForLastRow).use { stmt ->
                stmt?.executeQuery().use { rs ->
                    if (rs?.next() == true) {
                        maxID += rs.getInt("id")
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val randomID = (1 until maxID).random()
        val requestForTip = "SELECT * FROM tips WHERE id = ?"

        try {
            connection?.prepareStatement(requestForTip).use { stmt ->
                stmt?.setInt(1, randomID)
                stmt?.executeQuery().use { rs ->
                    if (rs?.next() == true) {
                        dailyTip = dailyTip.copy(
                            first = randomID,
                            second = rs.getString("tip_text"),
                            third = rs.getString("tip_image")
                        )

                    }
                }

            }
        } catch (e: SQLException) {
            println("Error while working with database")
            e.printStackTrace()

        }
        return dailyTip

    }

    //function to submit glucose
    fun submitGlucose(level: Double) {
        try {
            connection?.createStatement().use { stmt ->
                val requestForInsertingNewRecord =
                    "INSERT INTO glucose_records(date, time, level) VALUES(?,?, ?)"

                connection?.prepareStatement(requestForInsertingNewRecord).use { pstmt ->
                    pstmt?.setString(1, LocalDate.now().toString())
                    pstmt?.setString(2, LocalTime.now().toString())
                    pstmt?.setDouble(3, level)
                    pstmt?.executeUpdate()
                }
            }
        } catch (e: SQLException) {
            println("Error while working with database")
            e.printStackTrace()
        }
    }

    fun clearTodayLog() {
        val sqlForClearingTodayFood = "DELETE FROM daily_food"
        val sqlForClearingTodayExercises = "DELETE FROM daily_exercises"
        val sqlForClearingTodayMedications = "DELETE FROM daily_medications"
        try {
            connection?.prepareStatement(sqlForClearingTodayFood).use { pstmt ->
                pstmt?.executeUpdate()
            }
            connection?.prepareStatement(sqlForClearingTodayExercises).use { pstmt ->
                pstmt?.executeUpdate()
            }
            connection?.prepareStatement(sqlForClearingTodayMedications).use { pstmt ->
                pstmt?.executeUpdate()
            }
        } catch (e: SQLException) {
            println("Error while working with database")
            e.printStackTrace()
        }
    }


    fun exportGlucose(): List<Triple<String, String, Double>> {
        val glucose = mutableListOf<Triple<String, String, Double>>()
        val date: String = LocalDate.now().toString()
        val requestTodayGlucose = "SELECT * FROM glucose_records WHERE date = ?"
        try {
            connection?.prepareStatement(requestTodayGlucose).use { stmt ->
                stmt?.executeQuery().use { rs ->
                    while (rs?.next() == true) {
                        val glucoseItem = Triple<String, String, Double>(
                            date,
                            rs.getString("time"),
                            rs.getDouble("level")

                        )
                        glucose.add(glucoseItem)
                    }
                }
            }
        } catch (e: SQLException) {
            println("Error while working with database")
            e.printStackTrace()
        }
        return glucose

    }

    fun close() {
        try {
            connection?.close()
            println("Database is closed")
        } catch (e: SQLException) {
            println("Error while closing connection")
            e.printStackTrace()
        }
    }

    @Throws(Throwable::class)
    protected fun finalize() {
        close()

    }
}


fun main() {
    val url = "jdbc:sqlite:DataBase.db"
    val db = Database.getInstance("app/src/main/java/com/example/bloodglucose/DataBase.db")
    val res = db.getExercisesDoneToday()
    //val res  = db.getTipImage(2)
    //val res = db.insertSomeExercise("Crunches", "Power", 500)
    db.close()
}

