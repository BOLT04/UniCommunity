package isel.pt.unicommunity.testing.fragmentTesting.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import isel.pt.unicommunity.model.to_refactor.built.User
import isel.pt.unicommunity.model.to_refactor.linker.NavSupplier

/*public class TestingModel(val id : Int = 0,
                          val name : String = "",
                          val salary : Int = 0,
                          val age : Int = 0)*/

public class TestingModel(val navSupplier: NavSupplier): ViewModel(){

    val user : MutableLiveData<User> = MutableLiveData()


    init {
        /*Handler().postDelayed({
            user.value = UserMapper(navSupplier).dtoToModel(UserDto(

                "name",
                "email",
                "githubId",

                test = HalStructure(
                    "testUrl",
                    TestDto::class.java,
                    null,
                    null
                )

            ))

        }, 5000)*/
    }

}
/*
{
    "page": 2,
    "per_page": 3,
    "total": 12,
    "total_pages": 4,
    "data": [
    {
        "id": 4,
        "first_name": "Eve",
        "last_name": "Holt",
        "avatar": "https://s3.amazonaws.com/uifaces/faces/twitter/marcoramires/128.jpg"
    },
    {
        "id": 5,
        "first_name": "Charles",
        "last_name": "Morris",
        "avatar": "https://s3.amazonaws.com/uifaces/faces/twitter/stephenmoon/128.jpg"
    },
    {
        "id": 6,
        "first_name": "Tracey",
        "last_name": "Ramos",
        "avatar": "https://s3.amazonaws.com/uifaces/faces/twitter/bigmancho/128.jpg"
    }
    ]
}*/