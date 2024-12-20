package com.example.scheduleapp.data

class ScheduleRepository {
    private val api = RetrofitInstance.api

    fun getGroups(onResult: (List<Group>) -> Unit, onError: (Throwable) -> Unit) {
        api.getGroups().enqueue(object : retrofit2.Callback<List<Group>> {
            override fun onResponse(call: retrofit2.Call<List<Group>>, response: retrofit2.Response<List<Group>>) {
                response.body()?.let { onResult(it) }
            }

            override fun onFailure(call: retrofit2.Call<List<Group>>, t: Throwable) {
                onError(t)
            }
        })
    }

    fun getTeachers(onResult: (List<Teacher>) -> Unit, onError: (Throwable) -> Unit) {
        api.getTeachers().enqueue(object : retrofit2.Callback<List<Teacher>> {
            override fun onResponse(call: retrofit2.Call<List<Teacher>>, response: retrofit2.Response<List<Teacher>>) {
                response.body()?.let { onResult(it) }
            }

            override fun onFailure(call: retrofit2.Call<List<Teacher>>, t: Throwable) {
                onError(t)
            }
        })
    }

    fun getRooms(onResult: (List<Room>) -> Unit, onError: (Throwable) -> Unit) {
        api.getRooms().enqueue(object : retrofit2.Callback<List<Room>> {
            override fun onResponse(call: retrofit2.Call<List<Room>>, response: retrofit2.Response<List<Room>>) {
                response.body()?.let { onResult(it) }
            }

            override fun onFailure(call: retrofit2.Call<List<Room>>, t: Throwable) {
                onError(t)
            }
        })
    }

    // Получить расписание на день
    fun getScheduleForDay(date: String, onResult: (List<Lesson>) -> Unit, onError: (Throwable) -> Unit) {
        api.getScheduleForDay(date).enqueue(object : retrofit2.Callback<List<Lesson>> {
            override fun onResponse(call: retrofit2.Call<List<Lesson>>, response: retrofit2.Response<List<Lesson>>) {
                response.body()?.let { onResult(it) }
            }

            override fun onFailure(call: retrofit2.Call<List<Lesson>>, t: Throwable) {
                onError(t)
            }
        })
    }

    // Получить расписание для группы
    fun getScheduleForGroup(groupId: Int, date: String, onResult: (List<Lesson>) -> Unit, onError: (Throwable) -> Unit) {
        api.getScheduleForGroup(groupId, date).enqueue(object : retrofit2.Callback<List<Lesson>> {
            override fun onResponse(call: retrofit2.Call<List<Lesson>>, response: retrofit2.Response<List<Lesson>>) {
                response.body()?.let { onResult(it) }
            }

            override fun onFailure(call: retrofit2.Call<List<Lesson>>, t: Throwable) {
                onError(t)
            }
        })
    }

    // Получить расписание для преподавателя
    fun getScheduleForTeacher(teacherId: Int, date: String, onResult: (List<Lesson>) -> Unit, onError: (Throwable) -> Unit) {
        api.getScheduleForTeacher(teacherId, date).enqueue(object : retrofit2.Callback<List<Lesson>> {
            override fun onResponse(call: retrofit2.Call<List<Lesson>>, response: retrofit2.Response<List<Lesson>>) {
                response.body()?.let { onResult(it) }
            }

            override fun onFailure(call: retrofit2.Call<List<Lesson>>, t: Throwable) {
                onError(t)
            }
        })
    }

    // Получить расписание для кабинета
    fun getScheduleForRoom(roomId: Int, date: String, onResult: (List<Lesson>) -> Unit, onError: (Throwable) -> Unit) {
        api.getScheduleForRoom(roomId, date).enqueue(object : retrofit2.Callback<List<Lesson>> {
            override fun onResponse(call: retrofit2.Call<List<Lesson>>, response: retrofit2.Response<List<Lesson>>) {
                response.body()?.let { onResult(it) }
            }

            override fun onFailure(call: retrofit2.Call<List<Lesson>>, t: Throwable) {
                onError(t)
            }
        })
    }
}
