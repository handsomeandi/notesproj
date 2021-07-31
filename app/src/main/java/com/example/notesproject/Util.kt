package com.example.notesproject

import com.example.notesproject.data.model.Note

object Util {
    fun sampleData() : ArrayList<Note>{
        return arrayListOf(
            Note(1,"Title1", "Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum ", "30.05.2002", "15.07.2002", "none"),
            Note(2,"Title2", "Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum ", "30.05.2002", "15.07.2002", "none"),
            Note(3,"Title3", "Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum ", "30.05.2002", "15.07.2002", "none"),
            Note(4,"Title4", "Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum ", "30.05.2002", "15.07.2002", "none"),
            Note(5,"Title5", "Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum ", "30.05.2002", "15.07.2002", "none"),
            Note(6,"Title6", "Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum ", "30.05.2002", "15.07.2002", "none")
        )
    }
}
