package com.example.scheduleapp.data

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// Модели данных
data class Group(val id: Int, val group_name: String)
data class Teacher(val id: Int, val Name: String)
data class Room(val id: Int, val RoomNumber: String)
data class Lesson(
    val pairNumber: Int,
    val SubjectName: String,
    val lesson_type: String,
    val Name: String,
    val RoomNumber: String,
    val group_name: String
)

// Интерфейс для работы с API
interface ScheduleApi {
    @GET("/groups")
    fun getGroups(): Call<List<Group>>

    @GET("/teachers")
    fun getTeachers(): Call<List<Teacher>>

    @GET("/rooms")
    fun getRooms(): Call<List<Room>>

    @GET("/schedule/full_day")
    fun getScheduleForDay(
        @Query("date") date: String
    ): Call<List<Lesson>>

    @GET("/schedule/full_day")
    fun getScheduleForGroup(
        @Query("group_id") groupId: Int,
        @Query("date") date: String
    ): Call<List<Lesson>>

    @GET("/schedule/full_day")
    fun getScheduleForTeacher(
        @Query("teacher_id") teacherId: Int,
        @Query("date") date: String
    ): Call<List<Lesson>>

    @GET("/schedule/full_day")
    fun getScheduleForRoom(
        @Query("room_id") roomId: Int,
        @Query("date") date: String
    ): Call<List<Lesson>>
}

// Объект RetrofitInstance
object RetrofitInstance {
    private const val BASE_URL = "http://10.0.2.2:5000"

    val api: ScheduleApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ScheduleApi::class.java)
    }
}
