/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.compose.jetsurvey.survey

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


/*
컴퓨터야 나는 지금 이 앱 기능에 대한 테스트 코드를 작성하고 싶어. 일단은 나는 지금 기기나 에뮬레이터에서 실행할 JUnit4
테스트 클래스를 만들고 싶어 그래서 내가 만들고 싶은 클래스 앞에 @RunWith(AndroidJUnit4.class) 주석을 붙였지
그리고 테스트 객체(Object)를 만들었어. 그 제품의 이름은 SurveyViewModelTest 라고 이름을 만들었어,
이 제품의 설계도(Class)를 이제 만들어 볼게
 */

@RunWith(AndroidJUnit4::class)
class SurveyViewModelTest {

    /*
    일단은 viewModel 이라는 변수를 만들고 타입이 SurveyViewModel 클래스로 지정했어 SurveyViewModelTest 에서만
     사용 가능해 그 이유는 private 이라는 접근 지정자를 사용했기 때문이야. 이 변수는 lateinit var을 써서 이름만
     올려놓고 실제로 사용할 때 값을 넣을려고 만들었어 즉 변수 생성은 미리 해두고 초기화는 해당 변수가 필요할 때만 초기화 할꺼야
     */

    /*TODO: viewModel은 변수이고 SurveyViewModel은 클래스인데 클래스를 함수처럼 사용하는건가 ? 왜 이렇게 쓰지? */
    private lateinit var viewModel: SurveyViewModel

    /*
    일단은 @Before이라는 주석을 사용해서 테스트틑 시작하기 전에 기능을 불러올거야 기능의 이름은 setUp(설정)이라고 지었어
    기능 안에서는 viewModel을 불러올 건데 viewModel은 SurveyViewModel 클래스와 같아 그리고 SurveyViewModel
    클래스는 2개의 생성자가 존재해 첫번째 생성자는 TestSurveyRepository를 호출했어. 조사 저장소를 불러오는 것을
    테스트하는 기능이지
    두번째는 PhotoUriManager를 호출했어 .이미지 주소를 관리하는 기능이지. 테스트 안에서 현재
    어플리케이션의 문맥이나 글을 검색하는 기능을 제공하는 ApplicationProvider을 사용했고 그 안에 있는
    어플리케이션 Context를 얻기 위해서 getApplicatioContext 메소드를 호출했지
    */

    /*
    Provides ability to retrieve the current application Context in tests.
    테스트에서 현재 애플리케이션 Context를 검색하는 기능을 제공한다
    This can be useful if you need to access the application assets
    앱 정보에 접근할 필요가 있다면 ApplicationProvider(This)는 유용할 수 있다

    접근 하는 방법은 밑에 3가지가 있다
    getApplicationContext().getAssets())
    preferences (eg getApplicationContext().getSharedPreferences()),
    file system (eg getApplicationContext().getDir())
    one of the many other context APIs in test
    */

    @Before
    fun setUp() {
        viewModel = SurveyViewModel(
            TestSurveyRepository(),
            PhotoUriManager(ApplicationProvider.getApplicationContext())
        )
    }


    /*
    이제는 본격적으로 시험을 만들려고 해. 만들려면 먼저 시험을 만들 것이라는 것을 컴퓨터에게 알려줘야 해
    그래서 맨 앞에다가 @Test를 작성하면 그러면 컴퓨터가 "아! 앞으로 시험을 작성할 거구나 !"라고 이해할 수 있어.
    내가 만들려는 시험은 사용자가 날짜를 선택하면 그 값이 올바르게 저장되어 있는지 확인하는 것을 만들거야
    그래서 그 시험지의 이름은 onDatePicked_storesValueCorrectly라고 지었어
    */

    @Test
    fun onDatePicked_storesValueCorrectly() {

        /*
        날짜를 선택하기
        처음에 값을 넣으면 변경할 수 없게 만들어주는 저장공간을 만들건데 사용하려면 앞에 val을 사용해 줘야해
        그리고 저장공간의 이름은 처음 날짜를 초 단위로 변경 했다라고 알 수 있게 이름을 지어야 해 그래서 initialDateMilliseconds라
        지었어 그래서 처음의 날짜 시작은 0이므로 0L이라고 했어. 0L은 명시적으로 long형 0이란 의미야.
         int와 long은 둘다 우리가 알고 있는 일반적인 정수 숫자를 쓰는데 다른 점은 long이 더 큰 숫자 범위를 가진다는
         차이가 있어 그래서 날짜를 초로 바꾸면 무지막지한 큰 숫자가 나오기 때문에 long형을 사용했어.
         viewModel은 ServeyViewModel 제품을 사용하는데. 제품 안에 있는 기능을 어떻게 불러야 할까 ?!?
        그것은 바로 ( . )을 사용하면 원하는 제품의 기능을 마음대로 부를 수 있다. 그래서 ServeyViewModel에 날짜 선택
         기능(onDatePicked)을 부를거야. 그런데 날짜를 선택하는 기능을 사용하기 위해서는 2가지 조건이 필요해.
         첫째는 Id를 물어보는 Int형 정수(questionId: Int), 둘째는 사용자가 선택한 날짜의 밀리초 Long형 정수형(pickerSelection: Long)을 받아야 해.
         그래서 사용자가 날짜를 선택할 때 날짜의 ID를 물어보고 값을 넣었고(dateQuestionId),처음의 날짜 시작을 지정(initialDateMilliseconds=0L)했어

        */

        // Select a date
        val initialDateMilliseconds = 0L
        viewModel.onDatePicked(dateQuestionId, initialDateMilliseconds)

        /*
         저장된 날짜 가져오기
         처음에 값을 지정하면 변경할 수 없게 만드는 저장공간을 val로 지정했고 저장공간 이름은 새로운 날짜를 초 단위로 바꿨다는
         의미를 전달 할 수 있게 newDateMillisecond로 지었습니다.
         viewModel은 SurveyViewModel을 사용하는데 viewModel에 있는 현재 날짜를 가져오는 기능(getCurrentDate)을 불러오려고 한다.
         getCurrentDate는 정수형 Int로 Id를 물어보고 얻은 값을 정수형 Long인 선택된 날짜로 변경하는 기능을 가졌다.
         즉 이 기능을 실행 시키기 위해서는 Id를 물어보는 Int형 정수(questionId: Int)가 필요하다 그래서 날짜의 ID 값을 넣었다(dateQuestionId)
         */

        // Get the stored date
        val newDateMilliseconds = viewModel.getCurrentDate(dateQuestionId)

        /*
        처음 날짜와 새로운 날짜가 동일한지 확인하기

         사용자 한테 받은 새로운 날짜의 초단위 변경 값과, 처음의 날짜 초단위 변경 값이 같다라는 것을 확인하기 위해서는
         두 값이 같다라는 것을 주장해야한다. 어떤 값을 주장하기 위해서는 assertThat A를 사용한다. assert라는 것은 주장하다
         라는 뜻이고 That은 지정한 값을 의미힌다. 그래서 지정한 값(That)과 같다는 기능을 불러오려면 ( . )을 사용하면 된다.
         그래서 A와 B가 같다는 것을 쓰기 위해서는 A isEqualTo B를 사용하면 된다.
         결국엔 newDateMilliseconds가 initialDateMilliseconds와 같다는 것을 주장하는 것이다.
         */
        // Verify they're identical
        assertThat(newDateMilliseconds).isEqualTo(initialDateMilliseconds)
    }
}

/*
 변경 불가능하고 변하지 않는 값인 날짜의 Id를 물어보는 저장공간을 만들 것인데 그 이름을 dateQuestionId라고 지었어
 나는 변경 불가능하고 변하지 않는 값을 정의하고 싶을 때는 const val을 사용해, 이것은 인간이 이해할 수 있는 언어를
   컴퓨터가 이해할 수 있는 언어로 변경하는 시점에서 값이 넣어지는 것이다. 그 말은 함수나 클래스 생성자로 넣어질 수 없고
   기본 자료형만(정수, 실수, 등등..) 넣어질 수 있다. 결론적으로 dateQuestionId를 변경 불가능하고
   변하지 앟는 값 1로 지정하는 것이다.*/

const val dateQuestionId = 1


/*
 컴퓨터야 나는 지금부터 TestSurveyRepository 제품을 만들려고 해, 이 제품의 기능은 Survey 저장소를 시험해, 그리고
 ( : )를 사용해서 SurveyRepository 상위 제품으로 부터 기능을 물려 받았어*/

class TestSurveyRepository : SurveyRepository {

    /*
     나는 이 제품 안에서만 사용할 수 있고 초기에 값을 지정하면 나중에 값을 변경할 수 없는 저장 공간 testSurvey를 만들었어
     즉 Survey 시험 장소를 만든 셈이지 그리고 Suervey라는 시험 장소에서 컴퓨터에게 Survey라는 시험지를 나누어 주었어
      */

    /*
     @StringRes
     Denotes that an integer parameter,
     field or method return value is expected to be a String resource reference*/

    private val testSurvey = Survey(
        title = -1,
        questions = listOf(
            Question(
                id = dateQuestionId,
                questionText = -1,
                answer = PossibleAnswer.Action(label = -1, SurveyActionType.PICK_DATE)
            )
        )
    )

    override fun getSurvey() = testSurvey

    override fun getSurveyResult(answers: List<Answer<*>>): SurveyResult {
        TODO("Not yet implemented")
    }
}
