package com.example.edukan.controller

import com.example.edukan.model.dto.UserDto
import com.example.edukan.model.exception.NotFoundException
import com.example.edukan.model.request.CategoryRequest
import com.example.edukan.model.request.UserRequest
import com.example.edukan.service.UserService
import org.skyscreamer.jsonassert.JSONAssert
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

class UserControllerTest extends Specification {
    MockMvc mockMvc
    private UserService userService

    void setup() {
        userService = Mock()
        def userController = new UserController(userService)
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new ErrorHandler())
                .build()
    }

    def "GetUser 200"() {
        given:
        def id = 1
        def url = "/v1/users/$id"

        def userDto = new UserDto("hasan","aliyev",
                "hasanaliev1042@gmail.com","0503738070","khirdalan")

        def responseJson = '''
                                    {
                                    "name":"hasan",
                                    "surname":"aliyev",
                                    "email":"hasanaliev1042@gmail.com",
                                    "phone":"0503738070",
                                    "address":"khirdalan"
                                    }
                                  '''

        when:
        def result = mockMvc.perform(MockMvcRequestBuilders.get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().response

        then:
        1 * userService.getUser(id) >> userDto

        result.status == 200
        JSONAssert.assertEquals(responseJson, result.contentAsString, false)
    }

    def "GetUser 404"() {
        given:
        def id = 1
        def url = "/v1/users/$id"

        when:
        def result = mockMvc
                .perform(MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().response

        then:
        1 * userService.getUser(id) >> {
            throw new NotFoundException("THIS ID DOES NOT EXIST!")
        }

        result.status == HttpStatus.NOT_FOUND.value()
        result.contentAsString == '''{"error":"THIS ID DOES NOT EXIST!"}'''
    }

    def "GetAllUsers 200"() {
        given:
        def url = "/v1/users"

        def dto = [
                UserDto.builder()
                .name("hasan")
                .surname("aliyev")
                .email("hasanaliev1042@gmail.com")
                .phone("0503738070")
                .address("khirdalan")
                .build(),
                UserDto.builder()
                .name("musa")
                .surname("amiraslanov")
                .email("musaamiraslanov@gmail.com")
                .phone("0503145545")
                .address("mardakan")
                .build()
        ]

        def dtoJSON = '''
                                [
                                {
                                "name":"hasan",
                                "surname":"aliyev",
                                "email":"hasanaliev1042@gmail.com",
                                "phone":"0503738070",
                                "address":"khirdalan"            
                                },
                                {
                                "name":"musa",
                                "surname":"amiraslanov",
                                "email":"musaamiraslanov@gmail.com",
                                "phone":"0503145545",
                                "address":"mardakan"  
                                }
                                ]
                                '''

        when:
        def result = mockMvc
                .perform(MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().response

        then:
        1 * userService.getUsers() >> dto

        result.status == 200
        JSONAssert.assertEquals(dtoJSON, result.contentAsString, false)
    }

    def "GetAllUsers empty 200"() {
        given:
        def url = "/v1/users"
        def dtoJSON = '''[]'''

        when:
        def result = mockMvc.perform(MockMvcRequestBuilders.get(url)
                .contentType(MediaType.APPLICATION_JSON)).andReturn().response

        then:
        1 * userService.getUsers() >> new ArrayList<UserDto>()

        result.status == HttpStatus.OK.value()
        JSONAssert.assertEquals(dtoJSON, result.contentAsString, false)


    }

    /*def "SignUp 200"(){
        given:
        def url = "/v1/users"
        def userRequest = UserRequest.builder()
                .name("hasan")
                .surname("aliyev")
                .email("hasanaliev1042@gmail.com")
                .phone("0503738070")
                .address("khirdalan")
                .password("Texnoera2022")
                .build()

        when:
        def result = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content('{"name":"hasan","surname":"aliyev","email":"hasanaliev1042@gmail.com",' +
                        '"phone":"0503738070","address":"khirdalan","password":"Texnoera2022"}'))
                .andReturn().response

        then:
        userService.signUp(userRequest)
        result.status == HttpStatus.OK.value()

    }*/
}
